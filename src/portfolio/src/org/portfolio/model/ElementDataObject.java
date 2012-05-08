/* $Name:  $ */
/* $Id: ElementDataObject.java,v 1.39 2010/10/27 19:24:57 ajokela Exp $ */
package org.portfolio.model;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * This interface defines a data object for element data. It has the various accessors/mutators needed for any element data.
 * 
 * @author Jack Brown, University of Minnesota
 */
public interface ElementDataObject {

    static final Comparator<ElementDataObject> NAME_ORDER = new Comparator<ElementDataObject>() {
        public int compare(ElementDataObject o1, ElementDataObject o2) {
            return o1.getEntryName() == null ? -1 : o1.getEntryName().compareToIgnoreCase(o2.getEntryName());
        }
    };
    static final Comparator<ElementDataObject> TYPE_NAME_ORDER = new Comparator<ElementDataObject>() {
        public int compare(ElementDataObject o1, ElementDataObject o2) {
            int result = o1.getElementTitle() == null ? -1 : o1.getElementTitle().compareTo(o2.getElementTitle());
            if (result == 0) {
                result = o1.getEntryName() == null ? -1 : o1.getEntryName().compareTo(o2.getEntryName());
            }
            return result;
        }
    };
    static final Comparator<ElementDataObject> DATE_MODIFIED_ORDER = new Comparator<ElementDataObject>() {
        public int compare(ElementDataObject o1, ElementDataObject o2) {
            if (o1.getModifiedDate() == null) {
                return 1;
            }
            if (o2.getModifiedDate() == null) {
                return -1;
            }
            return o2.getModifiedDate().compareTo(o1.getModifiedDate());
        }
    };
    static final Comparator<ElementDataObject> PLACE_ORDER = new Comparator<ElementDataObject>() {
        public int compare(ElementDataObject o1, ElementDataObject o2) {
            if(((Link) o1).getPlace() == ((Link) o2).getPlace())
                return 0;
            return ((Link) o1).getPlace() < ((Link) o2).getPlace() ? -1 : 1;
        }
    };

    /**
     * @return true if the object is new (has not yet been persisted).
     */
    boolean isNew();
    
	String getElementName();
    
    String getNodeId();

    String getPersonId();

    BigDecimal getEntryId();

    String getEntryIdString();

    Date getModifiedDate();

    Date getDateCreated();

    String getEntryName();

    void setPersonId(String personId);

    void setEntryId(BigDecimal entryId);

    void setEntryId(String entryId);

    void setElementTitle(String eT);
    
    void setElementName(String eN);
    
    void setElementType(String elementType);
    
    void setModifiedDate(Date modifiedDate);

    void setDateCreated(Date dateCreated);

    void setEntryName(String entryName);
    
    List<? extends ElementDataObject> getAttachments();

    List<? extends ElementDataObject> getFileAttachments();

    List<? extends ElementDataObject> getPhotoAttachments();

    List<? extends ElementDataObject> getLinkAttachments();

    List<String> getTags();

    String getElementTitle();

    boolean isMaterial();

    String getShortClassName();

    ElementDefinition getElementDefinition();

    void setElementDefinition(ElementDefinition elementDefinition);

    EntryKey getEntryKey();
    
    String getEntryKeyId();
    
    boolean isAttachmentsAllowed();
    
    long getSize();
    
    String getFormattedSize();
    
    String getFriendlyMIMEType();
    
    String getFileName();
    
    String getDescription();
    
}
