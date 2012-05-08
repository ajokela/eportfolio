/* $Name:  $ */
/* $Id: ProfDevelopment.java,v 1.13 2010/10/27 19:24:57 ajokela Exp $ */
/*
 * $Header: /opt/UMN-src/portfolio/src/org/portfolio/model/ProfDevelopment.java,v 1.13 2010/10/27 19:24:57 ajokela Exp $
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
import org.portfolio.util.DateForXSL;
import org.portfolio.util.DateValidation;
import org.portfolio.util.PortfolioConstants;

/**
 * This class encapsulates ProfDevelopment element object.
 *
 * @author	John Bush
 * @since	0.0
 * @version	$Revision: 1.13 $
 */
public class ProfDevelopment extends ElementBase {

    private static final long serialVersionUID = -8859312699658428913L;
	
    public java.lang.String getEntryName() {
        return (entryName != null ? entryName : EMPTY_STRING);
    }

    public void setEntryName(java.lang.String entryName) {
        this.entryName = entryName;
    }

    public java.lang.String getSponsor() {
        return (sponsor != null ? sponsor : EMPTY_STRING);
    }

    public void setSponsor(java.lang.String sponsor) {
        this.sponsor = sponsor;
    }

    public java.lang.String getDescription() {
        return (description != null ? description : EMPTY_STRING);
    }

    public void setDescription(java.lang.String description) {
        this.description = description;
    }

    public java.lang.String getCertificate() {
        return (certificate != null ? certificate : EMPTY_STRING);
    }

    public void setCertificate(java.lang.String certificate) {
        this.certificate = certificate;
    }

    /**
     * Dynamically determines the FromDate
     * @return the fromDate, or null if not parsable.
     */
    public java.util.Date getFromDate() {
        Date fromDate = null;
        if ( ( !getFromDateMonth().equals( PortfolioTagConstants.MONTH_DEFAULT ) ) &&
                ( !getFromDateYear().equals( PortfolioTagConstants.YEAR_DEFAULT ) ) &&
                ( !getFromDateDay().equals( PortfolioTagConstants.DAY_DEFAULT ) ) ) {
            try {
                // logService.debug( "getting month and year" );
                int day = Integer.parseInt( getFromDateDay());
                int month = Integer.parseInt( getFromDateMonth() );
                int year = Integer.parseInt( getFromDateYear() );

                Calendar cal = new GregorianCalendar( year, month, day );
                cal.setLenient(false);
                fromDate = new DateForXSL(cal.getTime().getTime(),false);
            } catch ( NumberFormatException e ) {
                logService.debug( "Error parsing date ", e );
            }   catch ( IllegalArgumentException iae ) {
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
                cal.setLenient (false);
                toDate = new DateForXSL(cal.getTime().getTime(),false);
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
            errors.add("entryName", new ActionMessage("error.required.missing", "Activity name"));
        } else if(entryName.trim().length() > PortfolioConstants.SIXTY_CHARS) {
            entryName = entryName.trim().substring(0, PortfolioConstants.SIXTY_CHARS);
            errors.add("entryName", new ActionMessage("error.lengthTooLong", "Activity name", PortfolioConstants.SIXTY_CHARS_DESC));
        }
        // fromDate
        if ( DateValidation.checkDate( getFromDateMonth(), getFromDateDay(), getFromDateYear() ) == DateValidation.FAILURE ) {
            errors.add( "fromDate", new ActionMessage( "error.date.invalid", "From" ) );
        }

        // toDate
        if ( DateValidation.checkDate( getToDateMonth(), getToDateDay(), getToDateYear() ) == DateValidation.FAILURE ) {
            errors.add( "toDate", new ActionMessage( "error.date.invalid", "To" ) );
        }

        //Sponsor length
        if(sponsor != null && sponsor.trim().length() > PortfolioConstants.FIFTY_CHARS) {
            sponsor = sponsor.trim().substring(0, PortfolioConstants.FIFTY_CHARS);
            errors.add("sponsor", new ActionMessage("error.lengthTooLong", "Sponsor", PortfolioConstants.FIFTY_CHARS_DESC));
        }

        // description
        if ( ( description != null ) && ( description.length() > PortfolioConstants.EIGHT_HUNDRED_WORDS ) ) {
            description = description.substring( 0, PortfolioConstants.EIGHT_HUNDRED_WORDS );
            errors.add( "description", new ActionMessage( "error.lengthTooLong", "Description", PortfolioConstants.EIGHT_HUNDRED_WORDS_DESC ) );
        }

        //Certificate length
        if(certificate != null && certificate.trim().length() > PortfolioConstants.FIFTY_CHARS) {
            certificate = certificate.trim().substring(0, PortfolioConstants.FIFTY_CHARS);
            errors.add("certificate", new ActionMessage("error.lengthTooLong", "Certificate earned", PortfolioConstants.FIFTY_CHARS_DESC));
        }

        return errors;
    }

    private String fromDateDay = PortfolioTagConstants.DAY_DEFAULT;
    private String fromDateMonth = PortfolioTagConstants.MONTH_DEFAULT;
    private String fromDateYear = PortfolioTagConstants.YEAR_DEFAULT;
    private String toDateDay = PortfolioTagConstants.DAY_DEFAULT;
    private String toDateMonth = PortfolioTagConstants.MONTH_DEFAULT;
    private String toDateYear = PortfolioTagConstants.YEAR_DEFAULT;

    protected java.lang.String sponsor = null;
    protected java.lang.String description = null;
    protected java.lang.String certificate = null;
}
