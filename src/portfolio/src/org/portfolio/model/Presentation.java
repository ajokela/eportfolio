/* $Name:  $ */
/* $Id: Presentation.java,v 1.13 2010/10/27 19:24:57 ajokela Exp $ */
/*
 * $Header: /opt/UMN-src/portfolio/src/org/portfolio/model/Presentation.java,v 1.13 2010/10/27 19:24:57 ajokela Exp $
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
 * This class encapsulates Presentation element object.
 *
 * @author      Jack Brown
 * @author	John Bush
 * @since	1.0
 * @version	$Revision: 1.13 $
 */
public class Presentation extends ElementBase {

    private static final long serialVersionUID = 6250997472995753921L;
	
    public java.lang.String getEntryName() {
        return (entryName != null ? entryName : EMPTY_STRING);
    }

    public void setEntryName(java.lang.String entryName) {
        this.entryName = (entryName != null) ? entryName.trim() : entryName;
    }

    public java.lang.String getDescription() {
        return (description != null ? description : EMPTY_STRING);
    }

    public void setDescription(java.lang.String description) {
        this.description = description;
    }

    public java.lang.String getPresenters() {
        return (presenters != null ? presenters : EMPTY_STRING);
    }

    public void setPresenters(java.lang.String presenters) {
        this.presenters = presenters;
    }

    public java.lang.String getEventName() {
        return (eventName != null ? eventName : EMPTY_STRING);
    }

    public void setEventName(java.lang.String eventName) {
        this.eventName = (eventName != null) ? eventName.trim() : eventName;
    }

    public java.lang.String getLocation() {
        return (location != null ? location : EMPTY_STRING);
    }

    public void setLocation(java.lang.String location) {
        this.location = (location != null) ? location.trim() : location;
    }

    public java.util.Date getEventDate() {
        Date eventDate = null;
        if ( ( !getEventDateMonth().equals( PortfolioTagConstants.MONTH_DEFAULT ) ) &&
                ( !getEventDateYear().equals( PortfolioTagConstants.YEAR_DEFAULT ) ) &&
                ( !getEventDateDay().equals( PortfolioTagConstants.DAY_DEFAULT ) ) ) {
            try {
                // logService.debug( "getting month and year" );
                int day = Integer.parseInt( getEventDateDay() );
                int month = Integer.parseInt( getEventDateMonth() );
                int year = Integer.parseInt( getEventDateYear() );

                Calendar cal = new GregorianCalendar( year, month, day );
                cal.setLenient(false);
                eventDate = new org.portfolio.util.DateForXSL(cal.getTime().getTime(),false);
            } catch ( NumberFormatException e ) {
                logService.debug( "Error parsing date ", e );
            } catch ( IllegalArgumentException iae ) {
                logService.debug( "Invalid date caught", iae );
            }
        }
        return eventDate;
    }

    public void setEventDate(java.util.Date eventDate) {
        if ( eventDate != null  ) {
            GregorianCalendar cal = new GregorianCalendar();
            cal.setTime( eventDate );

            setEventDateDay( String.valueOf( cal.get( Calendar.DAY_OF_MONTH ) ) );
            setEventDateMonth( String.valueOf( cal.get( Calendar.MONTH ) ) );
            setEventDateYear( String.valueOf( cal.get( Calendar.YEAR ) ) );
        }
    }

    public String getEventDateMonth() {
        return ( eventDateMonth != null ? eventDateMonth : PortfolioTagConstants.MONTH_DEFAULT );
    }

    public void setEventDateMonth(String eventDateMonth) {
        this.eventDateMonth = ( eventDateMonth != null ? eventDateMonth : PortfolioTagConstants.MONTH_DEFAULT );
    }

    public String getEventDateYear() {
        return ( eventDateYear != null ? eventDateYear : PortfolioTagConstants.YEAR_DEFAULT );
    }

    public void setEventDateYear(String eventDateYear) {
        this.eventDateYear = ( eventDateYear != null ? eventDateYear : PortfolioTagConstants.YEAR_DEFAULT );
    }

    public String getEventDateDay() {
        return ( eventDateDay != null ? eventDateDay : PortfolioTagConstants.DAY_DEFAULT );
    }

    public void setEventDateDay(String eventDateDay) {
        this.eventDateDay = ( eventDateDay != null ? eventDateDay : PortfolioTagConstants.DAY_DEFAULT );
    }

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        if (request.getParameter("viewfile.x") != null) return errors;

        if ((entryName == null) || (entryName.trim().length() == 0)) {
            errors.add("entryName", new ActionMessage("error.required.missing", "Title of presentation"));
        } else if(entryName.trim().length() > PortfolioConstants.SIXTY_CHARS) {
            entryName = entryName.trim().substring(0, PortfolioConstants.SIXTY_CHARS);
            errors.add("entryName", new ActionMessage("error.lengthTooLong", "Title of presentation", PortfolioConstants.SIXTY_CHARS_DESC));
        }

        //Event name length
        if(eventName != null && eventName.trim().length() > PortfolioConstants.SIXTY_CHARS) {
            eventName = eventName.trim().substring(0, PortfolioConstants.SIXTY_CHARS);
            errors.add("eventName", new ActionMessage("error.lengthTooLong", "Name of event", PortfolioConstants.SIXTY_CHARS_DESC));
        }

        //Location length
        if(location != null && location.trim().length() > PortfolioConstants.FIFTY_CHARS) {
            location = location.trim().substring(0, PortfolioConstants.FIFTY_CHARS);
            errors.add("location", new ActionMessage("error.lengthTooLong", "Location", PortfolioConstants.FIFTY_CHARS_DESC));
        }

        if ((description != null) && (description.length() > PortfolioConstants.EIGHT_HUNDRED_WORDS)) {
            description = description.substring(0, PortfolioConstants.EIGHT_HUNDRED_WORDS);
            errors.add("description", new ActionMessage("error.lengthTooLong", "Description", PortfolioConstants.EIGHT_HUNDRED_WORDS_DESC));
        }

        if ((presenters != null) && (presenters.length() > PortfolioConstants.EIGHT_HUNDRED_WORDS)) {
            presenters = presenters.substring(0, PortfolioConstants.EIGHT_HUNDRED_WORDS);
            errors.add("presenters", new ActionMessage("error.lengthTooLong", "Presenters", PortfolioConstants.EIGHT_HUNDRED_WORDS_DESC));
        }

        if ( DateValidation.checkDate( getEventDateMonth() , getEventDateDay(), getEventDateYear() ) == DateValidation.FAILURE) {
        errors.add( "eventDate", new ActionMessage( "error.date.invalid", "Presentation date" ) );
        }

        return errors;
    }

    protected String eventDateDay = PortfolioTagConstants.DAY_DEFAULT;
    protected String eventDateMonth = PortfolioTagConstants.MONTH_DEFAULT;
    protected String eventDateYear = PortfolioTagConstants.YEAR_DEFAULT;
    protected java.lang.String description = null;
    protected java.lang.String presenters = null;
    protected java.lang.String eventName = null;
    protected java.lang.String location = null;
}
