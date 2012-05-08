/* $Name:  $ */
/* $Id: ProfInterests.java,v 1.7 2010/10/27 19:24:57 ajokela Exp $ */
/*
 * $Header: /opt/UMN-src/portfolio/src/org/portfolio/model/ProfInterests.java,v 1.7 2010/10/27 19:24:57 ajokela Exp $
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
 * This class encapsulates ProfInterests element object.
 *
 * @author	John Bush
 * @since	0.0
 * @version	$Revision: 1.7 $
 */
public class ProfInterests extends ElementBase {

    private static final long serialVersionUID = -2486800753831460654L;
	
    public java.lang.String getEntryName() {
        return (entryName != null ? entryName : EMPTY_STRING);
    }

    public void setEntryName(java.lang.String entryName) {
        this.entryName = entryName;
    }

    public java.lang.String getInterestActivity() {
        return (interestActivity != null ? interestActivity : EMPTY_STRING);
    }

    public void setInterestActivity(java.lang.String interestActivity) {
        this.interestActivity = interestActivity;
    }

    public java.lang.String getInterestSkills() {
        return (interestSkills != null ? interestSkills : EMPTY_STRING);
    }

    public void setInterestSkills(java.lang.String interestSkills) {
        this.interestSkills = interestSkills;
    }

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        if (request.getParameter("viewfile.x") != null) return errors;

        if ((entryName == null) || (entryName.trim().length() == 0)) {
            errors.add("entryName", new ActionMessage("error.required.missing", "Name of interest", PortfolioConstants.FIFTY_CHARS_DESC));
        } else if(entryName.trim().length() > PortfolioConstants.FIFTY_CHARS) {
            entryName = entryName.trim().substring(0, PortfolioConstants.FIFTY_CHARS);
            errors.add("entryName", new ActionMessage("error.lengthTooLong", "Name of interest", PortfolioConstants.FIFTY_CHARS_DESC));
        }

        if ((interestActivity != null) && (interestActivity.length() > PortfolioConstants.EIGHT_HUNDRED_WORDS)) {
            interestActivity = interestActivity.substring(0, PortfolioConstants.EIGHT_HUNDRED_WORDS);
            errors.add("interestActivity", new ActionMessage("error.lengthTooLong", "Relevant activities", PortfolioConstants.EIGHT_HUNDRED_WORDS_DESC));
        }

        if ((interestSkills != null) && (interestSkills.length() > PortfolioConstants.EIGHT_HUNDRED_WORDS)) {
            interestSkills = interestSkills.substring(0, PortfolioConstants.EIGHT_HUNDRED_WORDS);
            errors.add("interestSkills", new ActionMessage("error.lengthTooLong", "Skills", PortfolioConstants.EIGHT_HUNDRED_WORDS_DESC));
        }

        return errors;
    }

    protected java.lang.String interestActivity = null;
    protected java.lang.String interestSkills = null;
}
