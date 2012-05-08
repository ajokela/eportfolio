/* $Name:  $ */
/* $Id: FileAccessor.java,v 1.5 2011/03/14 19:37:44 ajokela Exp $ */
package org.portfolio.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.portfolio.model.FileElement;
import org.portfolio.model.Person;
import org.portfolio.util.FileSaveException;

public interface FileAccessor {

    static final String REASON_VIRUS_DELETED = "Virus found, file deleted.";
    static final String REASON_VIRUS_DISINFECTED = "Virus found. File disinfected.";
    static final String REASON_EXCEPTION = "Exception caught.";

    FileElement saveFile(FileElement edo, byte[] bytes) throws FileSaveException;
    FileElement saveFile(FileElement edo, InputStream is) throws FileSaveException;

    void deleteFile(FileElement edo);

    InputStream getFile(FileElement edo) throws FileNotFoundException, IOException;
    InputStream getFile(String personId, String fileName) throws FileNotFoundException, IOException;
    
    boolean fileExist(String fileName, String personId);
    
    long getFileSize(FileElement edo);
    
    long getFreeSpace(Person person);

    boolean fileExists(FileElement edo);

}
