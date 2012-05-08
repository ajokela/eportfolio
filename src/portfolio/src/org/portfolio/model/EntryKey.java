/* $Name:  $ */
/* $Id: EntryKey.java,v 1.24 2010/11/04 21:08:53 ajokela Exp $ */
package org.portfolio.model;

import java.math.BigDecimal;

// import org.apache.commons.codec.binary.Base64;
import org.portfolio.client.util.*;
import org.portfolio.bus.ElementDefinitionManager;
import org.portfolio.util.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

/**
 * Entries are keyed using either the combination of their entryId and elementId or the combination of their entryId and their className.
 * This class aims to simplify all that.
 * 
 * @author Matt Sheehan
 */
@Configurable
public class EntryKey {

    @Autowired
    private ElementDefinitionManager elementDefinitionManager;

    private static final String ENTRY_KEY_INVALID = "invalid_key";
    
    private BigDecimal entryId;
    private String personId = "";
    private String elementDefinitionId = "";
    private ElementDefinition elementDefinition;
    private int hasBeenAdded;
    private boolean is_public = false;
    protected LogService logService = new LogService(this.getClass());
    
    public EntryKey(String personId, String elementDefinitionId, BigDecimal entryId) {
        this.personId = personId;
        this.elementDefinitionId = elementDefinitionId;
        this.entryId = entryId;
        hasBeenAdded = 0;
    }
    
    public void setIsPublic(boolean is_public) {
    	this.is_public = is_public;
    }
    
    public boolean isPublic() {
    	return is_public;
    }
    
    public int getHasBeenAdded() {
    	return hasBeenAdded;
    }
    
    public void setHasBeenAdded() {
    	hasBeenAdded++;
    }

    public EntryKey(String encodedString) {
    	
    	try {
	    	// encodedString = encodedString.replaceAll("$", "=");
	        // String decodedString = new String(Base64.decodeBase64(encodedString));
	    	
    		String decodedString = "";
    		
    		if(encodedString.contains("-")) {
    			// not encoded
    			decodedString = encodedString;
    		}
    		else {
    			decodedString = Base64.decodeString(encodedString);
    		}
	    	
	    	// logService.debug("EntryKey(" + encodedString + ") => decodedString ==> " + decodedString );
	    	
	        String[] tokens = decodedString.split("-");
	        
	        if ( tokens.length >= 3 ) {
		        
		        this.personId = tokens[0];
		        this.elementDefinitionId = tokens[1];
		        this.entryId = new BigDecimal(tokens[2]);
	        
	        }
	        
    	}
    	catch (Exception e) {
    		logService.debug(e.getMessage());
    	}
    }

    /**
     * Encodes this key into a single decodable id.
     */
    public String getId() {
    	
    	try {
	        String string = this.personId + "-" + this.elementDefinitionId + "-" + this.entryId;
	        
	        String encodedString = Base64.encodeString(string);
	        
	        // logService.debug("getId: encodedString ==> " + encodedString + " | string: " + string);
	        
	        return encodedString;
    	}
    	catch (Exception e) {
    		logService.debug(e.getMessage());
    	}
    	
    	return ENTRY_KEY_INVALID;
    }

    public final String getElementId() {
        return getElementDefinition().getId();
    }

    public final BigDecimal getEntryId() {
        return entryId;
    }

    public ElementDefinition getElementDefinition() {
    	
        if (elementDefinition == null && elementDefinitionId != null) {
            elementDefinition = elementDefinitionManager.findByElementId(elementDefinitionId);
        }
        else if (elementDefinitionId == null) {
        	elementDefinition = null;
        }
        
        return elementDefinition;
    }

    public String getPersonId() {
        return personId;
    }

    @Override
    public String toString() {
        return this.personId + "-" + this.elementDefinitionId + "-" + this.entryId;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof EntryKey && obj.toString().equals(this.toString());
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }
    
 
}
