/* $Name:  $ */
/* $Id: AdditionalSkills.java,v 1.8 2010/10/27 19:24:57 ajokela Exp $ */
/*
 * $Header: /opt/UMN-src/portfolio/src/org/portfolio/model/AdditionalSkills.java,v 1.8 2010/10/27 19:24:57 ajokela Exp $
 * $Revision: 1.8 $
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
 * This class encapsulates AdditionalSkills element object.
 *
 * @author	John Bush
 * @author      Jack Brown
 * @since	0.0
 * @version	$Revision: 1.8 $
 */
public class AdditionalSkills extends ElementBase {

    private static final long serialVersionUID = -7384615388103543018L;
	
    public java.lang.String getEntryName() {
        return (entryName != null ? entryName : EMPTY_STRING);
    }

    public void setEntryName(java.lang.String entryName) {
        this.entryName = entryName;
    }

    public java.lang.String getDescription() {
        return (description != null ? description : EMPTY_STRING);
    }

    public void setDescription(java.lang.String description) {
        this.description = description;
    }

    public java.lang.String getExperience() {
        return (experience != null ? experience : EMPTY_STRING);
    }

    public void setExperience(java.lang.String experience) {
        this.experience = experience;
    }

    //--------------------------------------------------------------------------
    // Methods dictated by implemented interfaces

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        if (request.getParameter("viewfile.x") != null) return errors;

        if ((entryName == null) || (entryName.trim().length() == 0)) {
            errors.add( "Skill.area.missing", new ActionMessage( "error.required.missing", "Skill area" ));
        } else if(entryName.trim().length() > PortfolioConstants.FIFTY_CHARS) {
            entryName = entryName.trim().substring(0, PortfolioConstants.FIFTY_CHARS);
            errors.add( "skill.area.tooLong", new ActionMessage("error.lengthTooLong", "Skill area", PortfolioConstants.FIFTY_CHARS_DESC));
        }

        if ((description != null) && (description.length() > PortfolioConstants.EIGHT_HUNDRED_WORDS)) {
            description = description.substring(0, PortfolioConstants.EIGHT_HUNDRED_WORDS);
            errors.add("description", new ActionMessage("error.lengthTooLong","Description", PortfolioConstants.EIGHT_HUNDRED_WORDS_DESC));
        }
        if ((experience != null) && (experience.length() > PortfolioConstants.EIGHT_HUNDRED_WORDS)) {
            experience = experience.substring(0, PortfolioConstants.EIGHT_HUNDRED_WORDS);
            errors.add("experience", new ActionMessage("error.lengthTooLong", "Experience", PortfolioConstants.EIGHT_HUNDRED_WORDS_DESC));
        }
        return errors;
    }

    public void setIsRemote(boolean isRemote) {
        this.isRemote = isRemote;
    }

    public boolean isRemote() {
        return isRemote;
    }

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
        buff.append("personId=").append(getPersonId()).append(",");
        buff.append("entryId=").append(getEntryId()).append(",");
        buff.append("dateCreated=").append(getDateCreated()).append(",");
        buff.append("modifiedDate=").append(getModifiedDate()).append(",");
        buff.append("entryName=").append(getEntryName()).append(",");
        buff.append("description=").append(getDescription()).append(",");
        buff.append("experience=").append(getExperience()).append(",");
        buff.append("is new? =>" + isNew());
        return buff.toString();
    }

    protected java.lang.String description = null;
    protected java.lang.String experience = null;
    protected boolean isRemote = false;
}
