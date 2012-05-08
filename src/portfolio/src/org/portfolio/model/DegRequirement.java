/* $Name:  $ */
/* $Id: DegRequirement.java,v 1.13 2010/10/27 19:24:57 ajokela Exp $ */
/*
 * $Header: /opt/UMN-src/portfolio/src/org/portfolio/model/DegRequirement.java,v 1.13 2010/10/27 19:24:57 ajokela Exp $
 * $Revision: 1.13 $
 * $Date: 2010/10/27 19:24:57 $
 *
 * ============================================================================
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
 */

package org.portfolio.model;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.portfolio.util.PortfolioConstants;

/**
 * This class encapsulates DegRequirement element object.
 *
 * @author	John Bush
 * @since	0.0
 * @version	$Revision: 1.13 $
 */
public class DegRequirement extends ElementBase {

    private static final long serialVersionUID = 3558754071379572854L;

    public java.lang.String getEntryName() {
        return (entryName != null ? entryName : EMPTY_STRING);
    }

    public void setEntryName(java.lang.String entryName) {
        this.entryName = entryName;
    }

    public java.lang.String getBulletinYear() {
        return (bulletinYear != null ? bulletinYear : EMPTY_STRING);
    }

    public void setBulletinYear(java.lang.String bulletinYear) {
        this.bulletinYear = bulletinYear;
    }

    public java.lang.String getRequirements() {
        return (requirements != null ? requirements : EMPTY_STRING);
    }

    public void setRequirements(java.lang.String requirements) {
        this.requirements = requirements;
    }

    //--------------------------------------------------------------------------
    // Methods dictated by implemented interfaces

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        if (request.getParameter("viewfile.x") != null) return errors;

        if ((entryName == null) || (entryName.trim().length() == 0)) {
            errors.add("entryName", new ActionMessage("error.required.missing", "Entry Name"));
        } else if(entryName.trim().length() > PortfolioConstants.FIFTY_CHARS) {
            entryName = entryName.trim().substring(0, PortfolioConstants.FIFTY_CHARS);
            errors.add("entryName", new ActionMessage("error.lengthTooLong", "Entry Name", PortfolioConstants.FIFTY_CHARS_DESC));
        }

        //Year length validation
        if(bulletinYear != null && bulletinYear.trim().length() > PortfolioConstants.TWENTY_FIVE_CHARS) {
            bulletinYear = bulletinYear.trim().substring(0, PortfolioConstants.TWENTY_FIVE_CHARS);
            errors.add("bulletinYear", new ActionMessage("error.lengthTooLong", "Bulletin year", PortfolioConstants.TWENTY_FIVE_CHARS_DESC ));
        }

        if ((requirements != null) && (requirements.length() > PortfolioConstants.TWO_FIFTY_WORDS)) {
            requirements = requirements.substring(0, PortfolioConstants.TWO_FIFTY_WORDS);
            errors.add("requirements", new ActionMessage("error.lengthTooLong", "Requirements", PortfolioConstants.TWO_FIFTY_WORDS_DESC));
        }

        return errors;
    }


    public void setIsRemote(boolean isRemote) {
        this.isRemote = isRemote;
    }

    public boolean isRemote() {
        return isRemote;
    }

    public String toString() {
        StringBuffer buff = new StringBuffer();
        buff.append(super.toString()).append("\n");

        // for this instance.
        buff.append("personId=").append(getPersonId()).append(",");
        buff.append("entryId=").append(getEntryId()).append(",");
        buff.append("dateCreated=").append(getDateCreated()).append(",");
        buff.append("modifiedDate=").append(getModifiedDate()).append(",");
        buff.append("entryName=").append(getEntryName()).append(",");
        buff.append("bulletinYear=").append(getBulletinYear()).append(",");
        buff.append("requirements=").append(getRequirements()).append(",");
        buff.append("is new? =>" + isNew());
        return buff.toString();
    }

    protected java.lang.String bulletinYear = null;
    protected java.lang.String requirements = null;
    protected boolean isRemote = false;
}
