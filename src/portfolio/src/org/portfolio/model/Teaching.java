/* $Name:  $ */
/* $Id: Teaching.java,v 1.13 2010/10/27 19:24:57 ajokela Exp $ */
/*
 * $Header: /opt/UMN-src/portfolio/src/org/portfolio/model/Teaching.java,v 1.13 2010/10/27 19:24:57 ajokela Exp $
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
 * This class encapsulates Teaching element object.
 *
 * @author	John Bush
 * @author      Jack Brown, University of Minnesota
 * @since	1.0
 * @version	$Revision: 1.13 $
 */
public class Teaching extends ElementBase {

    private static final long serialVersionUID = 9109567561603587287L;
	
    public java.lang.String getEntryName() {
        return (entryName != null ? entryName : EMPTY_STRING);
    }

    public void setEntryName(java.lang.String entryName) {
        this.entryName = entryName;
    }

    public java.lang.String getCourses() {
        return (courses != null ? courses : EMPTY_STRING);
    }

    public void setCourses(java.lang.String courses) {
        this.courses = courses;
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

    public java.lang.String getComments() {
        return (comments != null ? comments : EMPTY_STRING);
    }

    public void setComments(java.lang.String comments) {
        this.comments = comments;
    }
    public java.util.Date getFromDate() {
        Date fromDate = null;
        if ( ( !getFromDateMonth().equals( PortfolioTagConstants.MONTH_DEFAULT ) ) &&
                ( !getFromDateYear().equals( PortfolioTagConstants.YEAR_DEFAULT ) ) &&
                ( !getFromDateDay().equals( PortfolioTagConstants.DAY_DEFAULT ) ) ) {
            try {
                // logService.debug( "getting month and year" );
                int day = Integer.parseInt( getFromDateDay() );
                int month = Integer.parseInt( getFromDateMonth() );
                int year = Integer.parseInt( getFromDateYear() );

                Calendar cal = new GregorianCalendar( year, month, day );
                cal.setLenient(false);
                fromDate = new org.portfolio.util.DateForXSL(cal.getTime().getTime(),false);
            } catch ( NumberFormatException e ) {
					e.printStackTrace() ;
                logService.debug( "Error parsing date ", e );
            }  catch ( IllegalArgumentException iae ) {
					iae.printStackTrace() ;
                logService.debug( "Invalid date caught", iae );
            }
        }
        return fromDate;
    }

    public void setFromDate( java.util.Date fromDate ) {
        if ( fromDate != null ) {
            GregorianCalendar cal = new GregorianCalendar();
            cal.setTime( fromDate );

            setFromDateDay( String.valueOf( cal.get( Calendar.DAY_OF_MONTH ) ) );
            setFromDateMonth( String.valueOf( cal.get( Calendar.MONTH ) ) );
            setFromDateYear( String.valueOf( cal.get( Calendar.YEAR ) ) );
        }
    }

    public java.util.Date getToDate() {
        Date toDate = null;
        if ( ( !getToDateMonth().equals( PortfolioTagConstants.MONTH_DEFAULT ) ) &&
                ( !getToDateYear().equals( PortfolioTagConstants.YEAR_DEFAULT ) ) &&
                ( !getToDateDay().equals( PortfolioTagConstants.DAY_DEFAULT ) ) ) {
            try {
                // logService.debug( "getting month and year" );
                int day = Integer.parseInt( getToDateDay() );
                int month = Integer.parseInt( getToDateMonth() );
                int year = Integer.parseInt( getToDateYear() );

                Calendar cal = new GregorianCalendar( year, month, day );
                cal.setLenient(false);
                toDate = new org.portfolio.util.DateForXSL(cal.getTime().getTime(),false);
            } catch ( NumberFormatException e ) {
                logService.debug( "Error parsing date ", e );
            } catch ( IllegalArgumentException iae ) {
                logService.debug( "Invalid date caught", iae );
            }
        }
        return toDate;
    }

    public void setToDate( java.util.Date toDate ) {
        if ( toDate != null ) {
            GregorianCalendar cal = new GregorianCalendar();
            cal.setTime( toDate );

            setToDateDay( String.valueOf( cal.get( Calendar.DAY_OF_MONTH ) ) );
            setToDateMonth( String.valueOf( cal.get( Calendar.MONTH ) ) );
            setToDateYear( String.valueOf( cal.get( Calendar.YEAR ) ) );
        }
    }

    public String getFromDateDay() {
        return ( fromDateDay != null ? fromDateDay : PortfolioTagConstants.DAY_DEFAULT );
    }

    public void setFromDateDay( String fromDateDay ) {
        this.fromDateDay = ( fromDateDay != null ? fromDateDay : PortfolioTagConstants.DAY_DEFAULT );
    }

    public String getFromDateMonth() {
        return ( fromDateMonth != null ? fromDateMonth : PortfolioTagConstants.MONTH_DEFAULT );
    }

    public void setFromDateMonth( String fromDateMonth ) {
        this.fromDateMonth = ( fromDateMonth != null ? fromDateMonth : PortfolioTagConstants.MONTH_DEFAULT );
    }

    public String getFromDateYear() {
        return ( fromDateYear != null ? fromDateYear : PortfolioTagConstants.YEAR_DEFAULT );
    }

    public void setFromDateYear( String fromDateYear ) {
        this.fromDateYear = ( fromDateYear != null ? fromDateYear : PortfolioTagConstants.YEAR_DEFAULT );
    }

    public String getToDateDay() {
        return ( toDateDay != null ? toDateDay : PortfolioTagConstants.DAY_DEFAULT );
    }

    public void setToDateDay( String toDateDay ) {
        this.toDateDay = ( toDateDay != null ? toDateDay : PortfolioTagConstants.DAY_DEFAULT );
    }

    public String getToDateMonth() {
        return ( toDateMonth != null ? toDateMonth : PortfolioTagConstants.MONTH_DEFAULT );
    }

    public void setToDateMonth( String toDateMonth ) {
        this.toDateMonth = ( toDateMonth != null ? toDateMonth : PortfolioTagConstants.MONTH_DEFAULT );
    }

    public String getToDateYear() {
        return ( toDateYear != null ? toDateYear : PortfolioTagConstants.YEAR_DEFAULT );
    }

    public void setToDateYear( String toDateYear ) {
        this.toDateYear = ( toDateYear != null ? toDateYear : PortfolioTagConstants.YEAR_DEFAULT );
    }

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        if (request.getParameter("viewfile.x") != null) return errors;

        if ((entryName == null) || (entryName.trim().length() == 0)) {
            errors.add("entryName", new ActionMessage("error.required.missing", "Teaching position"));
        } else if(entryName.trim().length() > PortfolioConstants.FIFTY_CHARS) {
            entryName = entryName.trim().substring(0, PortfolioConstants.FIFTY_CHARS);
            errors.add("entryName", new ActionMessage("error.lengthTooLong", "Teaching position", PortfolioConstants.FIFTY_CHARS_DESC));
        }

        //Organization length
        if(organization != null && organization.trim().length() > PortfolioConstants.FIFTY_CHARS) {
            organization = organization.trim().substring(0, PortfolioConstants.FIFTY_CHARS);
            errors.add("organization", new ActionMessage("error.lengthTooLong", "Institution / Organization", PortfolioConstants.FIFTY_CHARS_DESC));
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

        //Course / Subjects
        if ((courses != null) && (courses.length() > PortfolioConstants.ONE_HUNDRED_WORDS)) {
            courses = courses.substring(0, PortfolioConstants.ONE_HUNDRED_WORDS);
            errors.add("courses", new ActionMessage("error.lengthTooLong", "Course / Subjects", PortfolioConstants.ONE_HUNDRED_WORDS_DESC));
        }
        // fromDate
        if ( DateValidation.checkDate( getFromDateMonth(), getFromDateDay(), getFromDateYear() ) == DateValidation.FAILURE ) {
            errors.add( "fromDate", new ActionMessage( "error.date.invalid", "From date" ) );
        }

        // toDate
        if ( DateValidation.checkDate( getToDateMonth(), getToDateDay(), getToDateYear() ) == DateValidation.FAILURE ) {
            errors.add( "toDate", new ActionMessage( "error.date.invalid", "To date" ) );
        }

        //Comments
        if ((comments != null) && (comments.length() > PortfolioConstants.EIGHT_HUNDRED_WORDS)) {
            comments = comments.substring(0, PortfolioConstants.EIGHT_HUNDRED_WORDS);
            errors.add("comments", new ActionMessage("error.lengthTooLong", "Additional comments", PortfolioConstants.EIGHT_HUNDRED_WORDS_DESC));
        }

        return errors;
    }

    private String fromDateDay = PortfolioTagConstants.DAY_DEFAULT;
    private String fromDateMonth = PortfolioTagConstants.MONTH_DEFAULT;
    private String fromDateYear = PortfolioTagConstants.YEAR_DEFAULT;
    private String toDateDay = PortfolioTagConstants.DAY_DEFAULT;
    private String toDateMonth = PortfolioTagConstants.MONTH_DEFAULT;
    private String toDateYear = PortfolioTagConstants.YEAR_DEFAULT;
    protected java.lang.String courses = null;
    protected java.lang.String organization = null;
    protected java.lang.String street1 = null;
    protected java.lang.String street2 = null;
    protected java.lang.String city = null;
    protected java.lang.String state = null;
    protected java.lang.String zip = null;
    protected java.lang.String country = null;
    protected java.lang.String comments = null;
}
