/* $Name:  $ */
/* $Id: TipData.java,v 1.12 2010/10/27 19:24:57 ajokela Exp $ */
/**
 *
 * $Header: /opt/UMN-src/portfolio/src/org/portfolio/model/TipData.java,v 1.12 2010/10/27 19:24:57 ajokela Exp $
 * $Revision: 1.12 $
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
 * This class encapsulates TipData element object.
 *
 * @author	John Bush
 * @since	0.0
 * @version	$Revision: 1.12 $
 */
public class TipData {

    public TipData() {
        super();
    }

    //--------------------------------------------------------------------------
    // Bean accessors and mutators
    public java.lang.String getPageName() {
        return (pageName);
    }

    public void setPageName(java.lang.String pageName) {
        this.pageName = pageName;
    }

    public java.lang.String getSeqNumber() {
        return (seqNumber);
    }

    public void setSeqNumber(java.lang.String seqNumber) {
        this.seqNumber = seqNumber;
    }

    public java.lang.String getTipText() {
        return (tipText);
    }

    public void setTipText(java.lang.String tipText) {
        this.tipText = tipText;
    }

    public java.lang.String getTitle() {
        return (title);
    }

    public void setTitle(java.lang.String title) {
        this.title = title;
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
        buff.append("pageName=").append(getPageName()).append(",");
        buff.append("seqNumber=").append(getSeqNumber()).append(",");
        buff.append("tipText=").append(getTipText()).append(",");
        buff.append("title=").append(getTitle()).append(",");
        return buff.toString();
    }

    public boolean equals(Object obj) {
        TipData in = (TipData) obj;
        if (this.getPageName() == null && in.getPageName() != null)
            return false;
        else if (this.getPageName() != null && in.getPageName() == null)
            return false;
        else if (this.getPageName() != null && in.getPageName() != null &&
                !this.getPageName().equals(in.getPageName()))
            return false;
        if (this.getSeqNumber() == null && in.getSeqNumber() != null)
            return false;
        else if (this.getSeqNumber() != null && in.getSeqNumber() == null)
            return false;
        else if (this.getSeqNumber() != null && in.getSeqNumber() != null &&
                !this.getSeqNumber().equals(in.getSeqNumber()))
            return false;
        if (this.getTipText() == null && in.getTipText() != null)
            return false;
        else if (this.getTipText() != null && in.getTipText() == null)
            return false;
        else if (this.getTipText() != null && in.getTipText() != null &&
                !this.getTipText().equals(in.getTipText()))
            return false;
        if (this.getTitle() == null && in.getTitle() != null)
            return false;
        else if (this.getTitle() != null && in.getTitle() == null)
            return false;
        else if (this.getTitle() != null && in.getTitle() != null &&
                !this.getTitle().equals(in.getTitle()))
            return false;
        return super.equals(obj);
    }

    protected java.lang.String pageName = null;
    protected java.lang.String seqNumber = null;
    protected java.lang.String tipText = null;
    protected java.lang.String title = null;
}
