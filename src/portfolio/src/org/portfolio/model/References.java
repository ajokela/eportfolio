/* $Name:  $ */
/* $Id: References.java,v 1.7 2010/10/27 19:24:57 ajokela Exp $ */
/*
 * $Header: /opt/UMN-src/portfolio/src/org/portfolio/model/References.java,v 1.7 2010/10/27 19:24:57 ajokela Exp $
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
 * This class encapsulates References element object.
 *
 * @author	John Bush
 * @since	0.0
 * @version	$Revision: 1.7 $
 */
public class References extends ElementBase {

    private static final long serialVersionUID = -8681193898948582801L;
	
    public java.lang.String getEntryName() {
        return (entryName != null ? entryName : EMPTY_STRING);
    }

    public void setEntryName(java.lang.String entryName) {
        this.entryName = entryName;
    }

    public java.lang.String getPosition() {
        return (position != null ? position : EMPTY_STRING);
    }

    public void setPosition(java.lang.String position) {
        this.position = position;
    }

    public java.lang.String getOrganization() {
        return (organization != null ? organization : EMPTY_STRING);
    }

    public void setOrganization(java.lang.String organization) {
        this.organization = organization;
    }

    public java.lang.String getAddress1() {
        return (address1 != null ? address1 : EMPTY_STRING);
    }

    public void setAddress1(java.lang.String address1) {
        this.address1 = address1;
    }

    public java.lang.String getAddress2() {
        return (address2 != null ? address2 : EMPTY_STRING);
    }

    public void setAddress2(java.lang.String address2) {
        this.address2 = address2;
    }

    public java.lang.String getCity() {
        return (city != null ? city : EMPTY_STRING);
    }

    public void setCity(java.lang.String city) {
        this.city = city;
    }

    public java.lang.String getState() {
        return (state != null ? state : EMPTY_STRING);
    }

    public void setState(java.lang.String state) {
        this.state = state;
    }

    public java.lang.String getZip() {
        return (zip != null ? zip : EMPTY_STRING);
    }

    public void setZip(java.lang.String zip) {
        this.zip = zip;
    }

    public java.lang.String getCountry() {
        return (country != null ? country : EMPTY_STRING);
    }

    public void setCountry(java.lang.String country) {
        this.country = country;
    }

    public java.lang.String getPhone() {
        return (phone != null ? phone : EMPTY_STRING);
    }

    public void setPhone(java.lang.String phone) {
        this.phone = phone;
    }

    public java.lang.String getEmail() {
        return (email != null ? email : EMPTY_STRING);
    }

    public void setEmail(java.lang.String email) {
        this.email = email;
    }

    public java.lang.String getMailPref() {
        return (mailPref != null ? mailPref : EMPTY_STRING);
    }

    public void setMailPref(java.lang.String mailPref) {
        this.mailPref = mailPref;
    }

    public java.lang.String getPhonePref() {
        return (phonePref != null ? phonePref : EMPTY_STRING);
    }

    public void setPhonePref(java.lang.String phonePref) {
        this.phonePref = phonePref;
    }

    public java.lang.String getEmailPref() {
        return (emailPref != null ? emailPref : EMPTY_STRING);
    }

    public void setEmailPref(java.lang.String emailPref) {
        this.emailPref = emailPref;
    }

    //--------------------------------------------------------------------------
    // Methods dictated by implemented interfaces

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        if (request.getParameter("viewfile.x") != null) return errors;

        if ((entryName == null) || (entryName.trim().length() == 0)) {
            errors.add("entryName", new ActionMessage("error.required.missing", "Name"));
        } else if(entryName.trim().length() > PortfolioConstants.SIXTY_CHARS) {
            entryName = entryName.trim().substring(0, PortfolioConstants.SIXTY_CHARS);
            errors.add("entryName", new ActionMessage("error.lengthTooLong", "Name", PortfolioConstants.SIXTY_CHARS_DESC));
        }

        //Position length
        if(position != null && position.trim().length() > PortfolioConstants.FIFTY_CHARS) {
            position = position.trim().substring(0, PortfolioConstants.FIFTY_CHARS);
            errors.add("position", new ActionMessage("error.lengthTooLong", "Position", PortfolioConstants.FIFTY_CHARS_DESC));
        }

        //Organization length
        if(organization != null && organization.trim().length() > PortfolioConstants.SIXTY_CHARS) {
            organization = organization.trim().substring(0, PortfolioConstants.SIXTY_CHARS );
            errors.add("organization", new ActionMessage("error.lengthTooLong", "Institution / Organization", PortfolioConstants.SIXTY_CHARS_DESC));
        }

        //Address1 length
        if(address1 != null && address1.trim().length() > PortfolioConstants.FIFTY_CHARS) {
            address1 = address1.trim().substring(0, PortfolioConstants.FIFTY_CHARS);
            errors.add("address1", new ActionMessage("error.lengthTooLong", "Street address 1", PortfolioConstants.FIFTY_CHARS_DESC));
        }

        //Address2 length
        if(address2 != null && address2.trim().length() > PortfolioConstants.FIFTY_CHARS) {
            address2 = address2.trim().substring(0, PortfolioConstants.FIFTY_CHARS);
            errors.add("address2", new ActionMessage("error.lengthTooLong", "Street address 2", PortfolioConstants.FIFTY_CHARS_DESC));
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

        //Phone length
        if(phone != null && phone.trim().length() > PortfolioConstants.TWENTY_FIVE_CHARS) {
            phone = phone.trim().substring(0, PortfolioConstants.TWENTY_FIVE_CHARS);
            errors.add("phone", new ActionMessage("error.lengthTooLong", "Phone", PortfolioConstants.TWENTY_FIVE_CHARS_DESC));
        }

        //Email length
        if(email != null && email.trim().length() > PortfolioConstants.FIFTY_CHARS) {
            email = email.trim().substring(0, PortfolioConstants.FIFTY_CHARS);
            errors.add("email", new ActionMessage("error.lengthTooLong", "Email address", PortfolioConstants.FIFTY_CHARS_DESC));
        }

        return errors;
    }

    public void reset(ActionMapping mapping, HttpServletRequest request) {
        setMailPref(null);
        setPhonePref(null);
        setEmailPref(null);
    }

    protected java.lang.String position = null;
    protected java.lang.String organization = null;
    protected java.lang.String address1 = null;
    protected java.lang.String address2 = null;
    protected java.lang.String city = null;
    protected java.lang.String state = null;
    protected java.lang.String zip = null;
    protected java.lang.String country = null;
    protected java.lang.String phone = null;
    protected java.lang.String email = null;
    protected java.lang.String mailPref = null;
    protected java.lang.String phonePref = null;
    protected java.lang.String emailPref = null;
    protected boolean isRemote = false;
}
