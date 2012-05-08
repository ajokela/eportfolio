/* $Name:  $ */
/* $Id: ProfSkills.java,v 1.7 2010/10/27 19:24:57 ajokela Exp $ */
/*
 * $Header: /opt/UMN-src/portfolio/src/org/portfolio/model/ProfSkills.java,v 1.7 2010/10/27 19:24:57 ajokela Exp $
 * $Revision: 1.7 $
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
 * This class encapsulates ProfSkills element object.
 *
 * @author	John Bush
 * @since	0.0
 * @version	$Revision: 1.7 $
 */
public class ProfSkills extends ElementBase {

    private static final long serialVersionUID = -5032657956389205854L;
	
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

    public java.lang.String getReflection() {
        return (reflection != null ? reflection : EMPTY_STRING);
    }

    public void setReflection(java.lang.String reflection) {
        this.reflection = reflection;
    }

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        if (request.getParameter("viewfile.x") != null) return errors;

        if ((entryName == null) || (entryName.trim().length() == 0)) {
            errors.add("entryName", new ActionMessage("error.required.missing", "Name of skill"));
        } else if(entryName.trim().length() > PortfolioConstants.FIFTY_CHARS) {
            entryName = entryName.trim().substring(0, PortfolioConstants.FIFTY_CHARS);
            errors.add("entryName", new ActionMessage("error.lengthTooLong", "Name of skill", PortfolioConstants.FIFTY_CHARS_DESC));
        }
        if ( ( description != null ) && ( description.length() > PortfolioConstants.EIGHT_HUNDRED_WORDS ) ) {
            description = description.substring( 0, PortfolioConstants.EIGHT_HUNDRED_WORDS );
            errors.add( "description", new ActionMessage( "error.lengthTooLong", "Description", PortfolioConstants.EIGHT_HUNDRED_WORDS_DESC ) );
        }

        if ((experience != null) && (experience.length() > PortfolioConstants.EIGHT_HUNDRED_WORDS)) {
            experience = experience.substring(0, PortfolioConstants.EIGHT_HUNDRED_WORDS);
            errors.add("experience", new ActionMessage("error.lengthTooLong", "Experience", PortfolioConstants.EIGHT_HUNDRED_WORDS_DESC));
        }


        if ((reflection != null) && (reflection.length() > PortfolioConstants.EIGHT_HUNDRED_WORDS)) {
            reflection = reflection.substring(0, PortfolioConstants.EIGHT_HUNDRED_WORDS);
            errors.add("reflection", new ActionMessage("error.lengthTooLong", "Reflection", PortfolioConstants.EIGHT_HUNDRED_WORDS_DESC));
        }

        return errors;
    }

    protected java.lang.String description = null;
    protected java.lang.String experience = null;
    protected java.lang.String reflection = null;
}
