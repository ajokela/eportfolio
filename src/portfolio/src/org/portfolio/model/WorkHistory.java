/* $Name:  $ */
/* $Id: WorkHistory.java,v 1.13 2010/10/27 19:24:57 ajokela Exp $ */
/*
 * $Header: /opt/UMN-src/portfolio/src/org/portfolio/model/WorkHistory.java,v 1.13 2010/10/27 19:24:57 ajokela Exp $
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
 * This class encapsulates WorkHistory element object.
 *
 * @author	John Bush
 * @since	0.0
 * @version	$Revision: 1.13 $
 */
public class WorkHistory extends ElementBase {

    private static final long serialVersionUID = 2208868497332904688L;
	
    public java.lang.String getEntryName() {
        return (entryName != null ? entryName : EMPTY_STRING);
    }

    public void setEntryName(java.lang.String entryName) {
        this.entryName = entryName;
    }

    public java.lang.String getInstitution() {
        return (institution != null ? institution : EMPTY_STRING);
    }

    public void setInstitution(java.lang.String institution) {
        this.institution = institution;
    }

    public java.lang.String getSupervisor() {
        return (supervisor != null ? supervisor : EMPTY_STRING);
    }

    public void setSupervisor(java.lang.String supervisor) {
        this.supervisor = supervisor;
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

    public java.lang.String getTelephone() {
        return (telephone != null ? telephone : EMPTY_STRING);
    }

    public void setTelephone(java.lang.String telephone) {
        this.telephone = telephone;
    }

    public java.lang.String getFax() {
        return (fax != null ? fax : EMPTY_STRING);
    }

    public void setFax(java.lang.String fax) {
        this.fax =  fax;
    }


    public java.lang.String getPresentlyEmployed() {
        return (presentlyEmployed != null ? presentlyEmployed : EMPTY_STRING);
    }

    public void setPresentlyEmployed(java.lang.String presentlyEmployed) {
        this.presentlyEmployed = presentlyEmployed;
    }

    public java.lang.String getDescription() {
        return (description != null ? description : EMPTY_STRING);
    }

    public void setDescription(java.lang.String description) {
        this.description = description;
    }

    public java.lang.String getAccomplishments() {
        return (accomplishments != null ? accomplishments : EMPTY_STRING);
    }

    public void setAccomplishments(java.lang.String accomplishments) {
        this.accomplishments = accomplishments;
    }

    public java.util.Date getStartDate() {
        Date startDate = null;
        if ( ( !getStartDateMonth().equals( PortfolioTagConstants.MONTH_DEFAULT ) ) &&
                ( !getStartDateYear().equals( PortfolioTagConstants.YEAR_DEFAULT ) ) ) {
            try {
                // logService.debug( "getting month and year" );
                int month = Integer.parseInt( getStartDateMonth() );
                int year = Integer.parseInt( getStartDateYear() );

                Calendar cal = new GregorianCalendar( year, month, 1 );
                cal.setLenient(false);
                startDate = new org.portfolio.util.DateForXSL(cal.getTime().getTime(),true);
            } catch ( NumberFormatException e ) {
                logService.debug( "Error parsing date ", e );
            } catch ( IllegalArgumentException iae ) {
                logService.debug( "Invalid date caught", iae );
            }
        }
        return startDate;
    }

    public void setStartDate( java.util.Date startDate ) {
        if ( startDate != null ) {
            GregorianCalendar cal = new GregorianCalendar();
            cal.setTime( startDate );

            setStartDateMonth( String.valueOf( cal.get( Calendar.MONTH ) ) );
            setStartDateYear( String.valueOf( cal.get( Calendar.YEAR ) ) );
        }
    }

    public java.util.Date getEndDate() {
        Date endDate = null;
        if ( ( !getEndDateMonth().equals( PortfolioTagConstants.MONTH_DEFAULT ) ) &&
                ( !getEndDateYear().equals( PortfolioTagConstants.YEAR_DEFAULT ) ) ) {
            try {
                // logService.debug( "getting month and year" );
                int month = Integer.parseInt( getEndDateMonth() );
                int year = Integer.parseInt( getEndDateYear() );

                Calendar cal = new GregorianCalendar( year, month, 1 );
                cal.setLenient(false);
                endDate = new org.portfolio.util.DateForXSL(cal.getTime().getTime(),true);
            } catch ( NumberFormatException e ) {
                logService.debug( "Error parsing date ", e );
            }    catch ( IllegalArgumentException iae ) {
                logService.debug( "Invalid date caught", iae );
            }
        }
        return endDate;
    }

    public void setEndDate( java.util.Date endDate ) {
        if ( endDate != null ) {
            GregorianCalendar cal = new GregorianCalendar();
            cal.setTime( endDate );

            setEndDateMonth( String.valueOf( cal.get( Calendar.MONTH ) ) );
            setEndDateYear( String.valueOf( cal.get( Calendar.YEAR ) ) );
        }
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
            errors.add("entryName", new ActionMessage("error.required.missing", "Position title"));
        } else if(entryName.trim().length() > PortfolioConstants.FIFTY_CHARS) {
            entryName = entryName.trim().substring(0, PortfolioConstants.FIFTY_CHARS);
            errors.add("entryName", new ActionMessage("error.lengthTooLong", "Postition title", PortfolioConstants.FIFTY_CHARS_DESC));
        }

        //Institution length
        if(institution != null && institution.trim().length() > PortfolioConstants.SIXTY_CHARS) {
            institution = institution.trim().substring(0, PortfolioConstants.SIXTY_CHARS);
            errors.add("institution", new ActionMessage("error.lengthTooLong", "Institution / Organization", PortfolioConstants.SIXTY_CHARS_DESC));
        }

        //Supervisor length
        if(supervisor != null && supervisor.trim().length() > PortfolioConstants.FIFTY_CHARS) {
            supervisor = supervisor.trim().substring(0, PortfolioConstants.FIFTY_CHARS);
            errors.add("supervisor", new ActionMessage("error.lengthTooLong", "Supervisor", PortfolioConstants.FIFTY_CHARS_DESC));
        }

        //Address1 length
        if(address1 != null && address1.trim().length() > PortfolioConstants.FIFTY_CHARS) {
            address1 = address1.trim().substring(0, PortfolioConstants.FIFTY_CHARS);
            errors.add("address1", new ActionMessage("error.lengthTooLong", "Street Address 1", PortfolioConstants.FIFTY_CHARS_DESC));
        }

        //Address2 length
        if(address2 != null && address2.trim().length() > PortfolioConstants.FIFTY_CHARS) {
            address2 = address2.trim().substring(0, PortfolioConstants.FIFTY_CHARS);
            errors.add("address2", new ActionMessage("error.lengthTooLong", "Street Address 2", PortfolioConstants.FIFTY_CHARS_DESC));
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

        //Telephone length
        if(telephone != null && telephone.trim().length() > PortfolioConstants.TWENTY_FIVE_CHARS) {
            telephone = telephone.trim().substring(0, PortfolioConstants.TWENTY_FIVE_CHARS);
            errors.add("telephone", new ActionMessage("error.lengthTooLong", "Phone", PortfolioConstants.TWENTY_FIVE_CHARS_DESC));
        }

        //Fax length
        if(fax != null && fax.trim().length() > PortfolioConstants.TWENTY_FIVE_CHARS) {
            fax = fax.trim().substring(0, PortfolioConstants.TWENTY_FIVE_CHARS);
            errors.add("fax", new ActionMessage("error.lengthTooLong", "Fax", PortfolioConstants.TWENTY_FIVE_CHARS_DESC));
        }

        if ( DateValidation.checkDate( getStartDateMonth(), getStartDateYear() ) == DateValidation.FAILURE ) {
            errors.add( "startDate", new ActionMessage( "error.date.invalid", "Start date") );
        }
        if ( DateValidation.checkDate( getEndDateMonth(), getEndDateYear() ) == DateValidation.FAILURE ) {
            errors.add( "endDate", new ActionMessage( "error.date.invalid", "End date" ) );
        }

        if ( ( description != null ) && ( description.length() > PortfolioConstants.EIGHT_HUNDRED_WORDS ) ) {
            description = description.substring( 0, PortfolioConstants.EIGHT_HUNDRED_WORDS );
            errors.add( "description", new ActionMessage( "error.lengthTooLong", "Description", PortfolioConstants.EIGHT_HUNDRED_WORDS_DESC ) );
        }

        if ( ( accomplishments != null ) && ( accomplishments.length() > PortfolioConstants.EIGHT_HUNDRED_WORDS ) ) {
            accomplishments = accomplishments.substring( 0, PortfolioConstants.EIGHT_HUNDRED_WORDS );
            errors.add( "accomplishments", new ActionMessage( "error.lengthTooLong", "Accomplishments", PortfolioConstants.EIGHT_HUNDRED_WORDS_DESC ) );
        }
        return errors;
    }

    public void reset(ActionMapping mapping, HttpServletRequest request) {
        //needed to reset checkbox field, if unchecked
        setPresentlyEmployed(null);
    }

    protected String startDateMonth = PortfolioTagConstants.MONTH_DEFAULT;
    protected String startDateYear  = PortfolioTagConstants.YEAR_DEFAULT;
    protected String endDateMonth   = PortfolioTagConstants.MONTH_DEFAULT;
    protected String endDateYear    = PortfolioTagConstants.YEAR_DEFAULT;
    protected java.lang.String institution = null;
    protected java.lang.String supervisor = null;
    protected java.lang.String address1 = null;
    protected java.lang.String address2 = null;
    protected java.lang.String city = null;
    protected java.lang.String state = null;
    protected java.lang.String zip = null;
    protected java.lang.String country = null;
    protected java.lang.String telephone = null;
    protected java.lang.String fax = null;
    protected java.lang.String presentlyEmployed = null;
    protected java.lang.String description = null;
    protected java.lang.String accomplishments = null;
}
