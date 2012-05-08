/* $Name:  $ */
/* $Id: ElementSource.java,v 1.6 2010/11/23 14:27:17 ajokela Exp $ */
package org.portfolio.model;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.portfolio.util.LogService;


public class ElementSource {

    private String id;
    private String name;

    @SuppressWarnings("unused")
	private LogService logService = new LogService(this.getClass());
    
    private List<ElementDefinition> elementDefinitions;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
    	
        return name;
    }

    public void setName(String name) {
		
        this.name = name;
    }

    public List<ElementDefinition> getElementDefinitions() {
        return elementDefinitions;
    }

    public ElementDefinition getElementDefinition(String elementDefId) {
    	
        for (ElementDefinition elementDefinition : elementDefinitions) {
            if (elementDefinition.getId().equals(elementDefId)) {
            	
                return elementDefinition;
            }
        }
        return null;
        // TODO
    }

    public void setElementDefinitions(List<ElementDefinition> elementDefinitions) {
    	// logService.debug("----------> setElementDefinitions(List<ElementDefinition> elementDefinitions)");
    	boolean isSet = false;
    	for(Iterator<ElementDefinition> i = elementDefinitions.iterator(); i.hasNext();) {
    		ElementDefinition e = i.next();
    		
    		// logService.debug("e.getElementName(): " + e.getElementName() + " e.getClassName(): " + e.getClassName());
    		
    		// logService.debug("e.getId(): " + e.getId() + " | e.getName(): " + e.getName());
    		
    		if (e.getId().contentEquals("01010108") && e.getClassName().contentEquals("org.portfolio.model.UploadedMaterial")) {
    			
    			// logService.debug("e.getId(): " + e.getId());
    			e.setName("UCard Photo");
    			isSet = true;
    		}
    		
    		if (e.getId().contentEquals("01010109") ) {
    			e.setName("Photo");
    			isSet = true;
    		}
    		
    		if(e.getId().contentEquals("020101") && e.getClassName().contentEquals("org.portfolio.model.UploadedMaterial")) {
    			// logService.debug("e.getId(): " + e.getId());
    			e.setName("Uploaded Material");
    			isSet = true;
    		}

    		if(isSet) {
        		// logService.debug("e.getName(): " + e.getName() + " e.getClassName(): " + e.getClassName());
        		isSet = false;
    		}
  		
    	} 	
    	
    	Collections.sort(elementDefinitions);
    	
        this.elementDefinitions = elementDefinitions;
    }
}
