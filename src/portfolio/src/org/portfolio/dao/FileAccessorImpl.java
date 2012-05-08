/* $Name:  $ */
/* $Id: FileAccessorImpl.java,v 1.8 2011/03/14 19:37:44 ajokela Exp $ */
package org.portfolio.dao;

import java.io.ByteArrayInputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.portfolio.model.FileElement;
import org.portfolio.model.Person;
import org.portfolio.util.FileSaveException;
import org.portfolio.util.LogService;
import org.portfolio.util.RequiredInjection;

public class FileAccessorImpl implements FileAccessor {

    private String repositoryPath;

    private LogService logService = new LogService(this.getClass());
    
    public FileElement saveFile(FileElement edo, byte[] bytes) throws FileSaveException {
        return saveFile(edo.getFileName(), new ByteArrayInputStream(bytes), edo.getPersonId(), edo);
    }

    public FileElement saveFile(FileElement edo, InputStream is) throws FileSaveException {
        return saveFile(edo.getFileName(), is, edo.getPersonId(), edo);
    }
    
    public boolean fileExist(String fileName, String personId) {
    	File permFile = new File(getPersonalStorageDirectory(personId), fileName);
    	
    	return permFile.exists();
    }

    private FileElement saveFile(String filename, InputStream is, String personId, FileElement edo) throws FileSaveException {
    	    	
    	File permFile = new File(getPersonalStorageDirectory(personId), filename);
    	int  file_idx = 0;
    	String name = filename;
    	
    	while( permFile.exists() ) {
    		
    		edo.setNameChanged();
    		
    		name = new File(filename).getName();
    		String ext = (name.lastIndexOf(".") == -1) ? "" : name.substring(name.lastIndexOf(".")+1, name.length());
    		
    		name = name.replaceAll( "\\." + ext + "$", "");
    		
    		name = name + String.valueOf(file_idx);
    		
    		if (!ext.equals("")) {
    			name = name + "." + ext;
    		}
    		
    		permFile = new File(getPersonalStorageDirectory(personId), name);
    		
    		file_idx++;
    	}
    	
    	edo.setFileName(name);
    	
        try {
            
        	IOUtils.copy(is, new FileOutputStream(permFile));
            
        } catch (IOException e) {
        	
        	logService.debug(e);
        	
            // throw new FileSaveException(REASON_EXCEPTION, e);
        }
        
        return edo;
    }
    
    public long getFileSize(FileElement edo) {
    	if(edo != null) {
    		File fullFile = new File(getPersonalStorageDirectory(edo.getPersonId()), edo.getFileName());
    		
    		if(fullFile != null) {
    			return fullFile.length();
    		}
    	}
    	
    	return -1l;
    }

    public InputStream getFile(FileElement edo) throws FileNotFoundException, IOException {
    	/*
        File fullFile = new File(getPersonalStorageDirectory(edo.getPersonId()), edo.getFileName());
        if (!fullFile.exists()) {
            throw new FileNotFoundException();
        }
        return new FileInputStream(fullFile);
        */
    	
    	return getFile(edo.getPersonId(), edo.getFileName());
    }
    
    public InputStream getFile(String personId, String fileName) throws FileNotFoundException, IOException {
    	File fullFile = new File(getPersonalStorageDirectory(personId), fileName);
    	
        if (!fullFile.exists()) {
            // throw new FileNotFoundException();
        	return null;
        }
        
        return new FileInputStream(fullFile);
    }

    public void deleteFile(FileElement edo) {
    	
        File deleteFile = new File(getPersonalStorageDirectory(edo.getPersonId()), edo.getFileName());
        
        if (deleteFile.exists()) {
            deleteFile.delete();
        }
        
    }

    private File getPersonalStorageDirectory(String personId) {
        File dir = new File(this.repositoryPath, "" + (Integer.parseInt(personId) % 10) + File.separator + personId);
        
        if (!dir.exists()) {
            dir.mkdir();
        }
        return dir;
    }

    public long getFreeSpace(Person person) {
        return person.getMaxStorageSize() - FileUtils.sizeOfDirectory(getPersonalStorageDirectory(person.getPersonId()));
    }

    public boolean fileExists(FileElement fileElement) {
        return new File(getPersonalStorageDirectory(fileElement.getPersonId()), fileElement.getFileName()).exists();
    }

    @RequiredInjection
    public void setRepositoryPath(String repositoryPath) {
        // make sure the trailing slash exists
        if ((!repositoryPath.endsWith("/")) && (!repositoryPath.endsWith(File.separator))) {
            repositoryPath = repositoryPath + File.separator;
        }
        this.repositoryPath = repositoryPath;
    }
}
