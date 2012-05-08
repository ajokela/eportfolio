/* $Name: $ */
/* $Id: $ */
package org.portfolio.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;


import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;
import org.portfolio.bus.AttachmentManager;
import org.portfolio.bus.TagManager;
import org.portfolio.util.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

@Configurable
abstract public class ElementBase extends ValidatorForm implements ElementDataObject {

    /**
	 * 
	 */
	private static final long serialVersionUID = 18675309L;

	protected LogService logService = new LogService(this.getClass());

    @Autowired
    private TagManager tagManager;
    @Autowired
    private AttachmentManager attachmentManager;

    private String elementName;
    protected String personId;
    protected BigDecimal entryId;
    protected String entryName;
    protected Date modifiedDate;
    protected Date dateCreated;
    protected String elementType;
    protected String elementTitle;
    
    public static final String ELEMENT_TYPE_LINK = "link";
    
    private ElementDefinition elementDefinition;

    protected List<? extends ElementDataObject> attachments;

    protected List<String> tags;

    protected static final String EMPTY_STRING = "";
    
    public ElementBase() {
    	super();
    	elementType = "element";
    	elementTitle = null;
    	elementName  = null;
    }
    
    public String getElementType() {
    	return elementType;
    }
    
    public void setElementType(String elementType) {
    	this.elementType = elementType;
    }

    public String getPersonId() {
        if (personId == null)
            return EMPTY_STRING;
        else
            return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public BigDecimal getEntryId() {
        return entryId;
    }

    public String getEntryIdString() {
        if (entryId != null)
            return entryId.toString();
        else
            return EMPTY_STRING;
    }

    public void setEntryId(String entryId) {
        this.entryId = new BigDecimal(entryId);
    }

    public void setEntryId(BigDecimal entryId) {
        this.entryId = entryId;
    }

    public String getEntryName() {
        return (entryName != null ? entryName : EMPTY_STRING);
    }

    public void setEntryName(String entryName) {
        this.entryName = entryName;
    }

    public Date getModifiedDate() {
        if (modifiedDate == null) {
            return dateCreated;
        }
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public boolean isNew() {
        return getEntryId() == null;
    }
    
    public void setElementName(String eN) {
    	elementName = eN;
    }

    public String getElementName() {
    	
    	if ( elementName == null ) {
    		elementName = getElementDefinition().getName();
    	}

        return elementName;
    }

    private Object[] getEqualityIdentifiers() {
        return new Object[] { this.getEntryId(), this.getPersonId() };
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof ElementBase && Arrays.equals(this.getEqualityIdentifiers(), ((ElementBase) obj).getEqualityIdentifiers());
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(this.getEqualityIdentifiers());
    }

    public List<String> getTags() {
        return tagManager.getTagList(this);
    }

    public String getElementTitle() {
    	
    	if( elementTitle == null) {
    		elementTitle = getElementDefinition().getName();
    	}
    	
        return elementTitle;
    }
    
    public void setElementTitle(String eT) {
    	elementTitle = eT;
    }

    public String getNodeId() {
        return getElementDefinition().getId();
    }

    public boolean isMaterial() {
        return this instanceof UploadedMaterial;
    }

    public String getShortClassName() {
        String longName = getClass().getName();
        int index = longName.lastIndexOf(".");
        return longName.substring(index + 1);
    }

    protected boolean isEmpty(String s) {
        return s == null || s.trim().length() == 0;
    }

    public ElementDefinition getElementDefinition() {
        return elementDefinition;
    }

    public void setElementDefinition(ElementDefinition elementDefinition) {
        this.elementDefinition = elementDefinition;
    }

    @Override
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        return new ActionErrors();
    }

    public EntryKey getEntryKey() {
        return new EntryKey(getPersonId(), getElementDefinition().getId(), getEntryId());
    }

    public String getEntryKeyId() {
        return getEntryKey().getId();
    }

    public List<? extends ElementDataObject> getAttachments() {
        if (attachments == null) {
            attachments = attachmentManager.findByEntry(getEntryKey());
            Collections.sort(attachments, ElementDataObject.NAME_ORDER);
        }
        return attachments;
    }

    public List<? extends ElementDataObject> getFileAttachments() {
        List<? extends ElementDataObject> allAttachments = getAttachments();
        List<ElementDataObject> files = new ArrayList<ElementDataObject>();
        for (ElementDataObject elementDataObject : allAttachments) {
            if (elementDataObject instanceof FileElement && !((FileElement) elementDataObject).isImage()) {
                                
            	files.add(elementDataObject);
            	// ((FileElement) elementDataObject).getDateCreated().toLocaleString()
            }
        }
        return files;
    }

    public List<? extends ElementDataObject> getPhotoAttachments() {
        List<? extends ElementDataObject> allAttachments = getAttachments();
        List<ElementDataObject> photos = new ArrayList<ElementDataObject>();
        for (ElementDataObject elementDataObject : allAttachments) {
            if (elementDataObject instanceof FileElement && ((FileElement) elementDataObject).isImage()) {
                photos.add(elementDataObject);
            }
        }
        return photos;
    }

    public List<? extends ElementDataObject> getLinkAttachments() {
        List<? extends ElementDataObject> allAttachments = getAttachments();
        List<ElementDataObject> links = new ArrayList<ElementDataObject>();
        for (ElementDataObject elementDataObject : allAttachments) {
            if (elementDataObject instanceof Link) {
                links.add(elementDataObject);
            }
        }
        return links;
    }

    public boolean isAttachmentsAllowed() {
        return false;
    }
    
    public long getSize() {
    	return 0;
    }
    
    public String getFormattedSize() {
    	return "0 bytes";
    }
    
    public String getFriendlyMIMEType() {
    	return "";
    }
    
    public String getFileName() {
    	return "";
    }
    
    public String getDescription() {
    	return "";
    }
    
}
