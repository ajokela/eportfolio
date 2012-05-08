/* $Name:  $ */
/* $Id: Service.java,v 1.13 2010/10/27 19:24:57 ajokela Exp $ */
/*
 * $Header: /opt/UMN-src/portfolio/src/org/portfolio/model/Service.java,v 1.13 2010/10/27 19:24:57 ajokela Exp $
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

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.portfolio.client.tags.PortfolioTagConstants;
import org.portfolio.util.DateValidation;
import org.portfolio.util.PortfolioConstants;

/**
 * This class encapsulates Service element object.
 *
 * @author	John Bush
 * @author      Jack Brown, University of Minnesota
 * @since	1.0
 * @version	$Revision: 1.13 $
 */
public class Service extends ElementBase {

    private static final long serialVersionUID = -4990711140767051676L;
	
    public java.lang.String getEntryName() {
        return (entryName != null ? entryName : EMPTY_STRING);
    }

    public void setEntryName(java.lang.String entryName) {
        this.entryName =entryName;
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
        this.street2 = street2;
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
        this.phone =  phone;
    }

    public java.lang.String getEmail() {
        return (email != null ? email : EMPTY_STRING);
    }

    public void setEmail(java.lang.String email) {
        this.email = email;
    }

    public java.lang.String getFax() {
        return (fax != null ? fax : EMPTY_STRING);
    }

    public void setFax(java.lang.String fax) {
        this.fax = fax;
    }


    public java.lang.String getHours() {
        return (hours != null ? hours : EMPTY_STRING);
    }

    public void setHours(java.lang.String hours) {
        this.hours =  hours;
    }

    public java.lang.String getDescription() {
        return (description != null ? description : EMPTY_STRING);
    }

    public void setDescription(java.lang.String description) {
        this.description = description;
    }

    public java.lang.String getComments() {
        return (comments != null ? comments : EMPTY_STRING);
    }

    public void setComments(java.lang.String comments) {
        this.comments = comments;
    }

    public java.util.Date getStartDate() {
        Date startDate = null;
        if ( ( !getStartDateMonth().equals( PortfolioTagConstants.MONTH_DEFAULT ) ) &&
                ( !getStartDateYear().equals( PortfolioTagConstants.YEAR_DEFAULT ) ) &&
                ( !getStartDateDay().equals( PortfolioTagConstants.DAY_DEFAULT ) ) ) {
            try {
                // logService.debug( "getting month and year" );
                int day = Integer.parseInt( getStartDateDay() );
                int month = Integer.parseInt( getStartDateMonth() );
                int year = Integer.parseInt( getStartDateYear() );

                Calendar cal = new GregorianCalendar( year, month, day );
                cal.setLenient(false);
                startDate = new org.portfolio.util.DateForXSL(cal.getTime().getTime(),false);
            } catch ( NumberFormatException e ) {
                logService.debug( "Error parsing date ", e );
            }   catch ( IllegalArgumentException iae ) {
                logService.debug( "Invalid date caught", iae );
            }
        }
        return startDate;
    }

    public void setStartDate( java.util.Date startDate ) {
        if ( startDate != null  ) {
            GregorianCalendar cal = new GregorianCalendar();
            cal.setTime( startDate );

            setStartDateDay( String.valueOf( cal.get( Calendar.DAY_OF_MONTH ) ) );
            setStartDateMonth( String.valueOf( cal.get( Calendar.MONTH ) ) );
            setStartDateYear( String.valueOf( cal.get( Calendar.YEAR ) ) );
        }
    }

    public java.util.Date getEndDate() {
        Date endDate = null;
        if ( ( !getEndDateMonth().equals( PortfolioTagConstants.MONTH_DEFAULT ) ) &&
                ( !getEndDateYear().equals( PortfolioTagConstants.YEAR_DEFAULT ) ) &&
                ( !getEndDateDay().equals( PortfolioTagConstants.DAY_DEFAULT ) ) ) {
            try {
                // logService.debug( "getting month and year" );
                 int day = Integer.parseInt( getEndDateDay() );
               int month = Integer.parseInt( getEndDateMonth() );
               int year = Integer.parseInt( getEndDateYear() );

                Calendar cal = new GregorianCalendar( year, month, day );
                cal.setLenient(false);
                endDate = new org.portfolio.util.DateForXSL(cal.getTime().getTime(),false);
            } catch ( NumberFormatException e ) {
                logService.debug( "Error parsing date ", e );
            }      catch ( IllegalArgumentException iae ) {
                logService.debug( "Invalid date caught", iae );
            }
        }
        return endDate;
    }

    public void setEndDate( java.util.Date endDate ) {
        if ( endDate != null ) {
            GregorianCalendar cal = new GregorianCalendar();
            cal.setTime( endDate );

            setEndDateDay( String.valueOf( cal.get( Calendar.DAY_OF_MONTH ) ) );
            setEndDateMonth( String.valueOf( cal.get( Calendar.MONTH ) ) );
            setEndDateYear( String.valueOf( cal.get( Calendar.YEAR ) ) );
        }
    }

	 public String getStartDateDay() {
        return ( startDateDay != null ? startDateDay : PortfolioTagConstants.DAY_DEFAULT );
    }

    public void setStartDateDay( String startDateDay ) {
        this.startDateDay = ( startDateDay != null ? startDateDay : PortfolioTagConstants.DAY_DEFAULT );
    }

    public String getStartDateMonth() {
        return ( startDateMonth != null ? startDateMonth : PortfolioTagConstants.MONTH_DEFAULT );
    }

    public void setStartDateMonth(String startDateMonth) {
        this.startDateMonth = ( startDateMonth != null ? startDateMonth : PortfolioTagConstants.MONTH_DEFAULT );
    }

    public String getStartDateYear() {
        return ( startDateYear != null ? startDateYear : PortfolioTagConstants.YEAR_DEFAULT );
    }

    public void setStartDateYear(String startDateYear) {
        this.startDateYear = ( startDateYear != null ? startDateYear : PortfolioTagConstants.YEAR_DEFAULT );
    }

    public String getEndDateDay() {
        return ( endDateDay != null ? endDateDay : PortfolioTagConstants.DAY_DEFAULT );
    }

    public void setEndDateDay( String endDateDay ) {
        this.endDateDay = ( endDateDay != null ? endDateDay : PortfolioTagConstants.DAY_DEFAULT );
    }

    public String getEndDateMonth() {
        return ( endDateMonth != null ? endDateMonth : PortfolioTagConstants.MONTH_DEFAULT );
    }

    public void setEndDateMonth(String endDateMonth) {
        this.endDateMonth = ( endDateMonth != null ? endDateMonth : PortfolioTagConstants.MONTH_DEFAULT );
    }

    public String getEndDateYear() {
        return ( endDateYear != null ? endDateYear : PortfolioTagConstants.YEAR_DEFAULT );
    }

    public void setEndDateYear(String endDateYear) {
        this.endDateYear = ( endDateYear != null ? endDateYear : PortfolioTagConstants.YEAR_DEFAULT );
    }

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        if (request.getParameter("viewfile.x") != null) return errors;

        if ((entryName == null) || (entryName.trim().length() == 0)) {
            errors.add("entryName", new ActionMessage("error.required.missing", "Name of service activity"));
        } else if(entryName.trim().length() > PortfolioConstants.FIFTY_CHARS) {
            entryName = entryName.trim().substring(0, PortfolioConstants.FIFTY_CHARS);
            errors.add("entryName", new ActionMessage("error.lengthTooLong", "Name of service activity", PortfolioConstants.FIFTY_CHARS_DESC));
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

        //Street1 length
        if(street1 != null && street1.trim().length() > PortfolioConstants.FIFTY_CHARS) {
            street1 = street1.trim().substring(0, PortfolioConstants.FIFTY_CHARS);
            errors.add("street1", new ActionMessage("error.lengthTooLong", "Street address 1", PortfolioConstants.FIFTY_CHARS_DESC));
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

        //Phone length
        if(phone != null && phone.trim().length() > PortfolioConstants.TWENTY_FIVE_CHARS) {
            phone = phone.trim().substring(0, PortfolioConstants.TWENTY_FIVE_CHARS);
            errors.add("phone", new ActionMessage("error.lengthTooLong", "Phone", PortfolioConstants.TWENTY_FIVE_CHARS_DESC));
        }

        //Fax length
        if(fax != null && fax.trim().length() > PortfolioConstants.TWENTY_FIVE_CHARS) {
            fax = fax.trim().substring(0, PortfolioConstants.TWENTY_FIVE_CHARS);
            errors.add("fax", new ActionMessage("error.lengthTooLong", "Fax", PortfolioConstants.TWENTY_FIVE_CHARS_DESC));
        }

        //Email length
        if(email != null && email.trim().length() > PortfolioConstants.FIFTY_CHARS) {
            email = email.trim().substring(0, PortfolioConstants.FIFTY_CHARS);
            errors.add("email", new ActionMessage("error.lengthTooLong", "Email address", PortfolioConstants.FIFTY_CHARS_DESC));
        }
        
        //Start date
        if ( DateValidation.checkDate( getStartDateMonth(), getStartDateDay(), getStartDateYear() ) == DateValidation.FAILURE ) {
            errors.add( "startDate", new ActionMessage( "error.date.invalid", "Start date" ) );
        }
        
        //End date
        if ( DateValidation.checkDate( getEndDateMonth(), getEndDateDay(), getEndDateYear() ) == DateValidation.FAILURE ) {
            errors.add( "endDate", new ActionMessage( "error.date.invalid", "End date" ) );
        }
        
        //Hours length
        if(hours != null && hours.trim().length() > PortfolioConstants.TWENTY_FIVE_CHARS) {
            hours = hours.trim().substring(0, PortfolioConstants.TWENTY_FIVE_CHARS);
            errors.add("hours", new ActionMessage("error.lengthTooLong", "Hours per week", PortfolioConstants.TWENTY_FIVE_CHARS_DESC));
        }

        //Desc
        if ((description != null) && (description.length() > PortfolioConstants.EIGHT_HUNDRED_WORDS)) {
            description = description.substring(0, PortfolioConstants.EIGHT_HUNDRED_WORDS);
            errors.add("description", new ActionMessage("error.lengthTooLong", "Description", PortfolioConstants.EIGHT_HUNDRED_WORDS_DESC));
        }

        //Comments
        if ((comments != null) && (comments.length() > PortfolioConstants.EIGHT_HUNDRED_WORDS)) {
            comments = comments.substring(0, PortfolioConstants.EIGHT_HUNDRED_WORDS);
            errors.add("comments", new ActionMessage("error.lengthTooLong", "Additional comments", PortfolioConstants.EIGHT_HUNDRED_WORDS_DESC));
        }

        return errors;
    }

    private String startDateDay = PortfolioTagConstants.DAY_DEFAULT;
    private String startDateMonth = PortfolioTagConstants.MONTH_DEFAULT;
    private String startDateYear = PortfolioTagConstants.YEAR_DEFAULT;
    private String endDateDay = PortfolioTagConstants.DAY_DEFAULT;
    private String endDateMonth = PortfolioTagConstants.MONTH_DEFAULT;
    private String endDateYear = PortfolioTagConstants.YEAR_DEFAULT;
    protected java.lang.String organization = null;
    protected java.lang.String supervisor = null;
    protected java.lang.String street1 = null;
    protected java.lang.String street2 = null;
    protected java.lang.String city = null;
    protected java.lang.String state = null;
    protected java.lang.String zip = null;
    protected java.lang.String country = null;
    protected java.lang.String phone = null;
    protected java.lang.String email = null;
    protected java.lang.String fax = null;
    protected java.lang.String hours = null;
    protected java.lang.String description = null;
    protected java.lang.String comments = null;
}
