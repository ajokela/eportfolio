/* $Name:  $ */
/* $Id: FileElementBase.java,v 1.11 2011/03/14 19:37:44 ajokela Exp $ */
package org.portfolio.model;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.apache.bsf.*;
import org.portfolio.bus.FileExtManager;
import org.portfolio.util.LogService;
import org.springframework.beans.factory.annotation.Autowired;


public abstract class FileElementBase extends ElementBase implements FileElement {

    /**
	 * 
	 */
	
	private final LogService logService = new LogService(this.getClass());
	
	private static final long serialVersionUID = 86753098675309L;
	protected String fileName;
    protected long size;
    protected boolean is_clean = false;
    private   boolean didNameChange = false;
    
    private static final int UM_SHORTNAME_LENGTH = 24;
    
    private String sha2;
    private boolean file_exists;
    
	// private BSFManager _manager;
	
	@Autowired
	private FileExtManager fileExtManager;
	
	/*
	private static final String rubyUTF = "class UTFConvert\n" + 
    "  def remove_non_ascii(string, replacement=\"\")\n" + 
   	"    string.gsub!(/[\\x80-\\xff]/,replacement)\n" +
	"    return string;\n" + 
	"  end\n" + 
	"end\n" +
	"\n" + 
	"utf = UTFConvert.new\n" +
	"return utf.remove_non_ascii($name)\n";
  	*/
	
	public FileElementBase() throws BSFException {
		super();
		this.file_exists = false;
		
		/*
		try {
	        BSFManager.registerScriptingEngine("ruby", "org.jruby.javasupport.bsf.JRubyEngine", new String[] { "rb" });
	        _manager = new BSFManager();
		}
		catch (Exception e) {
			logService.debug(e);
		}
		*/
	}
    
    public InputStream getFile() throws IOException {
    	try {
    		if(getElementDefinition() != null && getElementDefinition().getFileAccessor() != null) { 
    			return getElementDefinition().getFileAccessor().getFile(this);
    		}
    	}
    	catch (Exception e) {
    		logService.debug(e);
    	}
    	
    	return null;
    	
    }

    public String getFileName() {
    	
    	try {
	    	if (!is_clean) {
	    		cleanFileName();
	    	}
    	}
	    catch (Exception e) {
	    	logService.debug(e);
	    }
	    
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    public long getSize() {
        return size;
    }
    
    public String getFormattedSize() {
    	
    	String unit = "";
    	Double value = 0.0;
    	NumberFormat nf = new DecimalFormat("0.0");
    	
    	if(size < 1024) {
    		unit = "bytes";
    		value = (double)size;	
    	}
    	else if(size >= 1024 && size < (Math.pow(2, 20) * 0.5)) {
    		unit = "KB";
    		value = ((double)size / 1024);
    	}
    	else if(this.size >= (Math.pow(2, 20) * 0.5) && this.size < (Math.pow(2, 30) * 0.5)) {
    		unit = "MB";
    		value = ((double)this.size / Math.pow(2, 20));
    	}
    	else  {
    		unit = "GB";
    		value = ((double)this.size / Math.pow(2, 30));
    	}
    	
    	return nf.format(value) + " " + unit;
    }
    
    public String getFriendlyMIMEType() {
    	
    	String filename = getFileName();
    	String desc = "File";
    	
    	if(filename != null) {
	    	String[] parts = filename.split(".");
	    	
	    	if(parts.length > 0) {
	    		
	    		FileExt fileExt = fileExtManager.findByExt(parts[parts.length - 1]);
	    		
	    		if (fileExt != null) {
	    			desc = fileExt.getDescription();
	    		}
	    		else {
	    			desc = parts[parts.length - 1].toUpperCase() + " File";
	    		}
	    	
	    		return desc;
	    	}
    	}
    	
    	return desc;
    }

    public void setSize(long size) {
        this.size = size;
    }
    
    public String getSHA2() {
    	return sha2;
    }
    
    public void setSHA2(String sha2) {
    	this.sha2 = sha2;
    }
   
    public boolean getFileExists() {
    	return file_exists;
    }
    
    public void setFileExists(boolean file_exists) {
    	this.file_exists = file_exists;
    }
    
	public String getShortFileName() {

		String name = getFileName();
		
		if(name != null) {
			
			if(name.length() > UM_SHORTNAME_LENGTH) {
				
				StringBuffer buff = new StringBuffer(name);
				
				int lastIndex = buff.lastIndexOf(".");
				String extension = "";
				
				if( lastIndex > 0) {
					extension = name.substring(lastIndex + 1, name.length());
				}
			
				return name.substring(0, UM_SHORTNAME_LENGTH - 1) + "..." + extension;
			}
			
			return name;
			
		}
		
		return "";
				
	}
    
    public boolean nameChanged() {
    	return didNameChange;
    }
    
    public void setNameChanged() {
    	didNameChange = true;
    }
    
    private void cleanFileName() throws BSFException {
    	
    	try {
    		
			String oldName = this.fileName;
			// _manager.declareBean("name", this.fileName, String.class);
			// this.fileName = (String)_eval(rubyUTF);
			
			if(this.fileName != null && !oldName.equals(this.fileName)) {
				didNameChange = true;
			}
			
			is_clean = true;
    	}
    	catch (Exception e) {
    		logService.debug(e);
    	}
    }
    /*
    private String _eval(String script) throws BSFException {
    	try {
    		return (String)_manager.eval("ruby", "(java)", 1, 1, script);
    	}
    	catch (BSFException e) {
    		logService.debug(e.getLocalizedMessage());
    	}
    	
    	return null;
    }
    */
}
