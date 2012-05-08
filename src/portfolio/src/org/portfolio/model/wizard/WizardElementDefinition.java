/* $Name:  $ */
/* $Id: WizardElementDefinition.java,v 1.20 2010/10/27 19:24:57 ajokela Exp $ */
package org.portfolio.model.wizard;

import java.util.ArrayList;
import java.util.List;

import org.portfolio.bus.ElementDefinitionManager;
import org.portfolio.model.ElementDefinition;
import org.portfolio.util.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

/**
 * This class represents an element definition mapping for wizard elements.
 */
@Configurable
public class WizardElementDefinition {
    
    private LogService logService = new LogService(this.getClass());
    
    @Autowired
    private ElementDefinitionManager elementDefinitionManager;

	private Integer id;
	private String title;
	private String description;
	private boolean isRequired;
	private List<String> keywords;
	private ElementDefinition elementDefinition;
	private Integer wizardId;
	private Integer categoryId;
	private boolean autoImport;
	private Integer sortOrder;
	private String elementDefintionId;

	private static final String UNKNOWN_ELEMENT_NAME = "Unknown Element";
	
	public String getTitle() {
		return this.title;
	}

	public String getDescription() {
		return this.description != null ? this.description : " ";
	}

	public String getElementName() {
		if (getElementDefinition() != null) {
			return getElementDefinition().getName();
		}
		
		return UNKNOWN_ELEMENT_NAME;
	}

	public boolean isRequired() {
		
		return this.isRequired;
	}

	public boolean isViewOnly() {
		if (getElementDefinition() != null) {
			return !getElementDefinition().isUpdatable();
		}
		
		return true;
	}

	public Integer getId() {
	    return id;
	}

	public ElementDefinition getElementDefinition() {
	    if (elementDefinition == null) {
	        // logService.debug("getting elementDef: " + getElementDefintionId());
	    	
	    	// logService.debug("------> getElementDefintionId(): " + getElementDefintionId());
	    	
	        elementDefinition = elementDefinitionManager.findByElementId(getElementDefintionId());
	        
	        if (elementDefinition == null) {
	        	logService.debug("-----> elementDefinition[" + getElementDefintionId() +  "] is null");
	        
	        
	    		StackTraceElement[] stack = Thread.currentThread().getStackTrace();
	    		
	    		for(int i=0; i<stack.length; ++i) {
	    			
	    			StackTraceElement t = stack[i];
	    			logService.debug("Line " + t.getLineNumber() + ": " + t.getClassName() + " - Method: " + t.getMethodName() );
	    			
	    		}
	    		
	    		logService.debug("----------------------------------------------------\n\n\n");
	        	
	        }
	        
	    }
		return elementDefinition;
	}

    public final List<String> getKeywords() {
        return keywords == null ? new ArrayList<String>() : keywords;
    }

    public final List<String> getCategories() {
        return getKeywords();
    }

	public final Integer getWizardId() {
		return wizardId;
	}

	public final Integer getCategoryId() {
		return categoryId;
	}

	public boolean isAutoImport() {
		return autoImport;
	}

    public void setId(Integer id) {
        this.id = id;
    }

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setRequired(boolean isRequired) {
		this.isRequired = isRequired;
	}

	public void setKeywords(List<String> keywords) {
		this.keywords = keywords;
	}

	public void setWizardId(Integer wizardId) {
		this.wizardId = wizardId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public void setAutoImport(boolean autoImport) {
		this.autoImport = autoImport;
	}

	public Integer getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

    public String getElementDefintionId() {
        return elementDefintionId;
    }

    public void setElementDefinitionId(String elementDefintionId) {
        // logService.debug("setting elementDefId: " + elementDefintionId);
        this.elementDefintionId = elementDefintionId;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof WizardElementDefinition && ((WizardElementDefinition) obj).id.equals(this.id);
    }

    @Override
    public int hashCode() {
        return new Integer(id).hashCode();
    }
}
