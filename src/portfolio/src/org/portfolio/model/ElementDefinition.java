/* $Name:  $ */
/* $Id: ElementDefinition.java,v 1.37 2010/11/04 21:08:53 ajokela Exp $ */
package org.portfolio.model;

import org.portfolio.dao.ElementHome;
import org.portfolio.dao.FileAccessor;
import org.portfolio.util.LogService;

/**
 * Basically the combination of an ElementType and a source for instances of that ElementType.
 * 
 * @author Matt Sheehan
 */
public class ElementDefinition implements Comparable<ElementDefinition> {

    private String id;
    private boolean publiclySharable = true;
    private boolean creatable;
    private boolean readable;
    private boolean updatable;
    private boolean deletable;
    private String iconPath;
    private String sourceName;

    private FileAccessor fileAccessor;
    private ElementHome elementHome;
    private ElementType elementType;
    
    @SuppressWarnings("unused")
	private LogService logService = new LogService(this.getClass());

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassName() {
        return elementType.getModelClassName();
    }
    
    public Class<?> getClassType() {
    	return elementType.getClass();
    }

    @Deprecated
    public boolean isPublicView() {
        return publiclySharable;
    }

    public ElementHome getElementHome() {
        return elementHome;
    }

    public void setElementHome(ElementHome elementHome) {
        this.elementHome = elementHome;
    }

    @Deprecated
    public String getElementId() {
        return getId();
    }

    @Deprecated
    public String getElementName() {
        return getName();
    }

    public int compareTo(ElementDefinition o) {
        return getName().compareToIgnoreCase(o.getName());
    }

    public String getName() {
    	
    	// logService.debug("elementType.getId(): " + elementType.getId());
    	// 
    	
    	String str = elementType.getName();
    	
    	if (elementType.getId().contentEquals("020101") || elementType.getId().startsWith("file")) {
    		
    		str = "Uploaded Material";
    		
    	}
    	else if (elementType.getId().contentEquals("additionalPhoto")) {
    		str = "Photo";
    	}
    	else if (elementType.getId().contentEquals("01010108")) {
    		
    		str = "UCard Photo";
    	}
    	
    	elementType.setName(str);
    	
    	// `logService.debug(elementType.toString());
    	
        return str;
    }
    
    public void setName(String n) {
    	elementType.setName(n);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof ElementDefinition && ((ElementDefinition) obj).getId().equals(getId());
    }

    @Override
    public int hashCode() {
        return id == null ? 0 : id.hashCode();
    }

    public boolean isPubliclySharable() {
        return publiclySharable;
    }

    public void setPubliclySharable(boolean publiclySharable) {
        this.publiclySharable = publiclySharable;
    }

    public ElementType getElementType() {
        return elementType;
    }

    public void setElementType(ElementType elementType) {
        this.elementType = elementType;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public boolean isCreatable() {
        return creatable;
    }

    public void setCreatable(boolean creatable) {
        this.creatable = creatable;
    }

    public boolean isReadable() {
        return readable;
    }

    public void setReadable(boolean readable) {
        this.readable = readable;
    }

    public boolean isUpdatable() {
        return updatable;
    }

    public void setUpdatable(boolean updatable) {
        this.updatable = updatable;
    }

    public boolean isDeletable() {
        return deletable;
    }

    public void setDeletable(boolean deletable) {
        this.deletable = deletable;
    }

    public FileAccessor getFileAccessor() {
        return fileAccessor;
    }

    public void setFileAccessor(FileAccessor fileAccessor) {
        this.fileAccessor = fileAccessor;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }
}
