/* $Name:  $ */
/* $Id: Mentors.java,v 1.9 2010/11/04 21:08:53 ajokela Exp $ */
/*
 * $Header: /opt/UMN-src/portfolio/src/org/portfolio/model/Mentors.java,v 1.9 2010/11/04 21:08:53 ajokela Exp $
 * $Revision: 1.9 $
 * $Date: 2010/11/04 21:08:53 $
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
import org.portfolio.util.LogService;
import org.portfolio.util.PortfolioConstants;

/**
 * This class encapsulates Mentors element object.
 *
 * @author	John Bush
 * @since	0.0
 * @version	$Revision: 1.9 $
 */
public class Mentors extends ElementBase {

    private static final long serialVersionUID = -2407944672314600570L;
    @SuppressWarnings("unused")
	private final LogService logService = new LogService(this.getClass());
	
    public java.lang.String getEntryName() {
    	
    	// logService.debug("entryName = " + entryName);
    	
        return (entryName != null ? entryName : EMPTY_STRING);
    }

    public void setEntryName(java.lang.String entryName) {
        this.entryName = entryName;
    }

    public java.lang.String getFirstName() {
    	
    	// logService.debug("firstName = " + firstName);
    	
        return (firstName != null ? firstName : EMPTY_STRING);
    }

    public void setFirstName(java.lang.String firstName) {
        this.firstName =  firstName;
    }

    public java.lang.String getTitle() {
    	
    	// logService.debug("title = " + title);
    	
        return (title != null ? title : EMPTY_STRING);
    }

    public void setTitle(java.lang.String title) {
        this.title = title;
    }

    public java.lang.String getOrganization() {
        return (organization != null ? organization : EMPTY_STRING);
    }

    public void setOrganization(java.lang.String organization) {
        this.organization = organization;
    }

    public java.lang.String getStreet1() {
        return (street1 != null ? street1 : EMPTY_STRING);
    }

    public void setStreet1(java.lang.String street1) {
        this.street1 = street1;
    }

    public java.lang.String getStreet2() {
        return (street2 != null ? street2 : EMPTY_STRING);
    }

    public void setStreet2(java.lang.String street2) {
        this.street2 =  street2;
    }

    public java.lang.String getCity() {
        return (city != null ? city : EMPTY_STRING);
    }

    public void setCity(java.lang.String city) {
        this.city =  city;
    }

    public java.lang.String getState() {
        return (state != null ? state : EMPTY_STRING);
    }

    public void setState(java.lang.String state) {
        this.state =  state;
    }

    public java.lang.String getZip() {
        return (zip != null ? zip : EMPTY_STRING);
    }

    public void setZip(java.lang.String zip) {
        this.zip =  zip;
    }

    public java.lang.String getCountry() {
        return (country != null ? country : EMPTY_STRING);
    }

    public void setCountry(java.lang.String country) {
        this.country =  country;
    }

    public java.lang.String getDescription() {
        return (description != null ? description : EMPTY_STRING);
    }

    public void setDescription(java.lang.String description) {
        // Bug 109 - always accept, truncate ONLY on validate()
        this.description = description;
    }

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        if (request.getParameter("viewfile.x") != null) return errors;

        //Last name validation (as entryName)
        if ((entryName == null) || (entryName.trim().length() == 0)) {
            errors.add("entryName", new ActionMessage("error.required.missing", "Last name"));
        } else if(entryName.trim().length() > PortfolioConstants.FIFTY_CHARS) {
            entryName = entryName.trim().substring(0, PortfolioConstants.FIFTY_CHARS);
            errors.add("entryName", new ActionMessage("error.lengthTooLong" , "Last name", PortfolioConstants.FIFTY_CHARS_DESC));
        }

        //First name length
        if(firstName != null && firstName.trim().length() > PortfolioConstants.FIFTY_CHARS) {
            firstName = firstName.trim().substring(0, PortfolioConstants.FIFTY_CHARS);
            errors.add("firstName", new ActionMessage("error.lengthTooLong", "First name", PortfolioConstants.FIFTY_CHARS_DESC));

        }

        //Title length
        if(title != null && title.trim().length() > PortfolioConstants.FIFTY_CHARS) {
            title = title.trim().substring(0, PortfolioConstants.FIFTY_CHARS);
            errors.add("title", new ActionMessage("error.lengthTooLong", "Title", PortfolioConstants.FIFTY_CHARS_DESC));
        }
        
        //Organization length
        if(organization != null && organization.trim().length() > PortfolioConstants.FIFTY_CHARS) {
            organization = organization.trim().substring(0, PortfolioConstants.FIFTY_CHARS);
            errors.add("organization", new ActionMessage("error.lengthTooLong", "Institution / Organization", PortfolioConstants.FIFTY_CHARS_DESC));
        }
        
        //Street1 length
        if(street1 != null && street1.trim().length() > PortfolioConstants.FIFTY_CHARS) {
            street1 = street1.trim().substring(0, PortfolioConstants.FIFTY_CHARS);
            errors.add("street1", new ActionMessage("error.lengthTooLong" , "Street address 1", PortfolioConstants.FIFTY_CHARS_DESC));
        }

        //Street2 length
        if(street2 != null && street2.trim().length() > PortfolioConstants.FIFTY_CHARS) {
            street2 = street2.trim().substring(0, PortfolioConstants.FIFTY_CHARS);
            errors.add("street2", new ActionMessage("error.lengthTooLong", "Street address 2", PortfolioConstants.FIFTY_CHARS_DESC));
        }

        //City length
        if(city != null && city.trim().length() > PortfolioConstants.FIFTY_CHARS) {
            city = city.trim().substring(0, PortfolioConstants.FIFTY_CHARS);
            errors.add("city", new ActionMessage("error.lengthTooLong", "City", PortfolioConstants.FIFTY_CHARS_DESC));
        }

        //State length
        if(state != null && state.trim().length() > PortfolioConstants.TWENTY_FIVE_CHARS) {
            state = state.trim().substring(0, PortfolioConstants.TWENTY_FIVE_CHARS);
            errors.add("state", new ActionMessage("error.lengthTooLong", "State", PortfolioConstants.TWENTY_FIVE_CHARS_DESC));
        }

        //Zip code length
        if(zip != null && zip.trim().length() > PortfolioConstants.TEN_CHARS) {
            zip = zip.trim().substring(0, PortfolioConstants.TEN_CHARS);
            errors.add("zip", new ActionMessage("error.lengthTooLong", "Zip / Postal code", PortfolioConstants.TEN_CHARS_DESC));
        }

        //Country length
        if(country != null && country.trim().length() > PortfolioConstants.TWENTY_FIVE_CHARS) {
            country = country.trim().substring(0, PortfolioConstants.TWENTY_FIVE_CHARS);
            errors.add("country", new ActionMessage("error.lengthTooLong", "Country", PortfolioConstants.TWENTY_FIVE_CHARS_DESC));
        }
        
        if ( ( description != null) && (description.length() > PortfolioConstants.EIGHT_HUNDRED_WORDS)) {
            description = description.substring(0, PortfolioConstants.EIGHT_HUNDRED_WORDS);
            errors.add("description", new ActionMessage("error.lengthTooLong", "Description", PortfolioConstants.EIGHT_HUNDRED_WORDS_DESC));
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
        buff.append("firstName=").append(getFirstName()).append(",");
        buff.append("title=").append(getTitle()).append(",");
        buff.append("organization=").append(getOrganization()).append(",");
        buff.append("street1=").append(getStreet1()).append(",");
        buff.append("street2=").append(getStreet2()).append(",");
        buff.append("city=").append(getCity()).append(",");
        buff.append("state=").append(getState()).append(",");
        buff.append("zip=").append(getZip()).append(",");
        buff.append("country=").append(getCountry()).append(",");
        buff.append("description=").append(getDescription()).append(",");
        buff.append("is new? =>" + isNew());
        return buff.toString();
    }

    protected java.lang.String firstName = null;
    protected java.lang.String title = null;
    protected java.lang.String organization = null;
    protected java.lang.String street1 = null;
    protected java.lang.String street2 = null;
    protected java.lang.String city = null;
    protected java.lang.String state = null;
    protected java.lang.String zip = null;
    protected java.lang.String country = null;
    protected java.lang.String description = null;
    protected boolean isRemote = false;
}
