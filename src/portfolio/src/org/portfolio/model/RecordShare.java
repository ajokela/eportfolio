/* $Name:  $ */
/* $Id: RecordShare.java,v 1.13 2010/10/27 19:24:57 ajokela Exp $ */
/**
 *
 * $Header: /opt/UMN-src/portfolio/src/org/portfolio/model/RecordShare.java,v 1.13 2010/10/27 19:24:57 ajokela Exp $
 * $Revision: 1.13 $
 * $Date: 2010/10/27 19:24:57 $
 *
 * =====================================================================
 *
 * The contents of this file are subject to the OSPI License Version 1.0 (the
 * License).  You may not copy or use this file, in either source code or
 * executable form, except in compliance with the License.  You may obtain a
 * copy of the License at http://www.theospi.org/.
 * 
 * Software distributed under the License is distributed on an AS IS basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied.  See the License
 * for the specific language governing rights and limitations under the
 * License.
 * 
 * Copyrights:
 * 
 * Portions created by or assigned to The University of Minnesota are Copyright
 * (c) 2003 The University of Minnesota.  All Rights Reserved.  Contact
 * information for OSPI is available at http://www.theospi.org/.
 * 
 * Portions Copyright (c) 2003 the r-smart group, inc.
 * 
 * Portions Copyright (c) 2003 The University of Delaware.
 * 
 * Acknowledgements
 * 
 * Special thanks to the OSPI Users and Contributors for their suggestions and
 * support.
 *
 */

package org.portfolio.model;


/**
 * This class encapsulates RecordShare element object.
 *
 * @author	John Bush
 * @since	0.0
 * @version	$Revision: 1.13 $
 */
public class RecordShare extends ElementBase {
    /**
	 * 
	 */
	private static final long serialVersionUID = 7194586246863185732L;
	private final static String EMPTY_STRING = "";

    public RecordShare() {
        super();
    }

    //--------------------------------------------------------------------------
    // Bean accessors and mutators
    public java.math.BigDecimal getEntryId() {
        return entryId;
    }

    public void setEntryId(java.math.BigDecimal entryId) {
        this.entryId = entryId;
    }

    public boolean isNew() {
        return (recordId == null);
    }

    //--------------------------------------------------------------------------
    // Bean accessors and mutators
    public java.lang.String getElementId() {
        return (elementId != null ? elementId : EMPTY_STRING);
    }

    public void setElementId(java.lang.String elementId) {
        this.elementId = elementId;
    }

    public java.lang.String getShareId() {
        return (shareId != null ? shareId : EMPTY_STRING);
    }

    public void setShareId(java.lang.String shareId) {
        this.shareId = shareId;
    }

    public java.math.BigDecimal getRecordId() {
        return (recordId != null ? recordId : null);
    }

    public void setRecordId(java.math.BigDecimal recordId) {
        this.recordId = recordId;
    }

    //--------------------------------------------------------------------------
    // Methods dictated by implemented interfaces

    /**
     * <p>Returns a string representation of the object. In general, the
     * <code>toString</code> method returns a string that
     * "textually represents" this object. The result should
     * be a concise but informative representation that is easy for a
     * person to read.</p>
     * <p>In the case of this object, it will show a conglomeration of the defining fields.</p>
     *
     * @return  a string representation of the object.
     */
    public String toString() {
        StringBuffer buff = new StringBuffer();
        buff.append(super.toString()).append("\n");

        // for this instance.
        buff.append("elementId=").append(getElementId()).append(",");
        buff.append("shareId=").append(getShareId()).append(",");
        buff.append("entryId=").append(getEntryId()).append(",");
        buff.append("recordId=").append(getRecordId()).append(",");
        return buff.toString();
    }

    public boolean equals(Object obj) {
        RecordShare in = (RecordShare) obj;
        if (this.getElementId() == null && in.getElementId() != null)
            return false;
        else if (this.getElementId() != null && in.getElementId() == null)
            return false;
        else if (this.getElementId() != null && in.getElementId() != null &&
                !this.getElementId().equals(in.getElementId()))
            return false;
        if (this.getShareId() == null && in.getShareId() != null)
            return false;
        else if (this.getShareId() != null && in.getShareId() == null)
            return false;
        else if (this.getShareId() != null && in.getShareId() != null &&
                !this.getShareId().equals(in.getShareId()))
            return false;
        if (this.getRecordId() == null && in.getRecordId() != null)
            return false;
        else if (this.getRecordId() != null && in.getRecordId() == null)
            return false;
        else if (this.getRecordId() != null && in.getRecordId() != null &&
                !this.getRecordId().equals(in.getRecordId()))
            return false;
        if (this.getRecordId() == null && in.getRecordId() != null)
            return false;
        else if (this.getEntryId() != null && in.getEntryId() == null)
            return false;
        else if (this.getEntryId() != null && in.getEntryId() != null &&
                !this.getEntryId().equals(in.getEntryId()))
            return false;

        return super.equals(obj);
    }

    //--------------------------------------------------------------------------
    // helper methods


    protected java.lang.String elementId = null;
    protected java.lang.String shareId = null;
    protected java.math.BigDecimal recordId = null;
    protected java.math.BigDecimal entryId = null;
}
