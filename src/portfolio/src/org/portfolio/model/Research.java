/* $Name:  $ */
/* $Id: Research.java,v 1.13 2010/10/27 19:24:57 ajokela Exp $ */
/*
 * $Header: /opt/UMN-src/portfolio/src/org/portfolio/model/Research.java,v 1.13 2010/10/27 19:24:57 ajokela Exp $
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
 * This class encapsulates Research element object.
 *
 * @author	John Bush
 * @author      Jack Brown, University of Minnesota
 * @since	1.0
 * @version	$Revision: 1.13 $
 */
public class Research extends ElementBase {

    private static final long serialVersionUID = -1386605042826890649L;
	
    public java.lang.String getEntryName() {
        return (entryName != null ? entryName : EMPTY_STRING);
    }

    public void setEntryName(java.lang.String entryName) {
        this.entryName = entryName;
    }

    public java.lang.String getOrganization() {
        return (organization != null ? organization : EMPTY_STRING);
    }

    public void setOrganization(java.lang.String organization) {
        this.organization = organization;
    }

    public java.lang.String getSupervisor() {
        return (supervisor != null ? supervisor : EMPTY_STRING);
    }

    public void setSupervisor(java.lang.String supervisor) {
        this.supervisor = supervisor;
    }

    public java.lang.String getDescription() {
        return (description != null ? description : EMPTY_STRING);
    }

    public void setDescription(java.lang.String description) {
        this.description = description;
    }

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        if (request.getParameter("viewfile.x") != null) return errors;

        if ((entryName == null) || (entryName.trim().length() == 0)) {
            errors.add("entryName", new ActionMessage("error.required.missing", "Name of activity"));
        } else if(entryName.trim().length() > PortfolioConstants.FIFTY_CHARS) {
            entryName = entryName.trim().substring(0, PortfolioConstants.FIFTY_CHARS);
            errors.add("entryName", new ActionMessage("error.lengthTooLong", "Name of activity", PortfolioConstants.FIFTY_CHARS_DESC));
        }
        
        //Organization
        if(organization != null && organization.trim().length() > PortfolioConstants.FIFTY_CHARS) {
            organization = organization.trim().substring(0, PortfolioConstants.FIFTY_CHARS);
            errors.add("organization", new ActionMessage("error.lengthTooLong", "Institution / Organization", PortfolioConstants.FIFTY_CHARS_DESC));
        }
        
        //Supervisor
        if(supervisor != null && supervisor.trim().length() > PortfolioConstants.FIFTY_CHARS) {
            supervisor = supervisor.trim().substring(0, PortfolioConstants.FIFTY_CHARS);
            errors.add("supervisor", new ActionMessage("error.lengthTooLong", "Supervisor", PortfolioConstants.FIFTY_CHARS_DESC));
        }

        if ((description != null) && (description.length() > PortfolioConstants.EIGHT_HUNDRED_WORDS)) {
            description = description.substring(0, PortfolioConstants.EIGHT_HUNDRED_WORDS);
            errors.add("description", new ActionMessage("error.lengthTooLong", "Description", PortfolioConstants.EIGHT_HUNDRED_WORDS_DESC));
        }

        return errors;
    }

    protected java.lang.String organization = null;
    protected java.lang.String supervisor = null;
    protected java.lang.String description = null;
}
