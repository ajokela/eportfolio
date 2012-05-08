/* $Name:  $ */
/* $Id: Performance.java,v 1.13 2010/10/27 19:24:57 ajokela Exp $ */
/*
 * $Header: /opt/UMN-src/portfolio/src/org/portfolio/model/Performance.java,v 1.13 2010/10/27 19:24:57 ajokela Exp $
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
 * This class encapsulates Performance element object.
 *
 * @author	John Bush
 * @author      Jack Brown
 * @since	1.0
 * @version	$Revision: 1.13 $
 */
public class Performance extends ElementBase {

    private static final long serialVersionUID = -8692216468913966076L;
	
    public java.lang.String getEntryName() {
        return (entryName != null ? entryName : EMPTY_STRING);
    }

    public void setEntryName(java.lang.String entryName) {
        this.entryName = entryName;
    }

    public java.lang.String getType() {
        return (type != null ? type : EMPTY_STRING);
    }

    public void setType(java.lang.String type) {
        this.type =  type;
    }

    public java.lang.String getLocation() {
        return (location != null ? location : EMPTY_STRING);
    }

    public void setLocation(java.lang.String location) {
        this.location = location;
    }

    public java.lang.String getJuried() {
        return (juried != null ? juried : EMPTY_STRING);
    }

    public void setJuried(java.lang.String juried) {
        this.juried = juried;
    }

    public java.lang.String getDescription() {
        return (description != null ? description : EMPTY_STRING);
    }

    public void setDescription(java.lang.String description) {
        this.description = description;
    }

    public java.util.Date getOpeningDate() {
        Date fromDate = null;
        if ( ( !getOpeningDateMonth().equals( PortfolioTagConstants.MONTH_DEFAULT ) ) &&
                ( !getOpeningDateYear().equals( PortfolioTagConstants.YEAR_DEFAULT ) ) &&
                ( !getOpeningDateDay().equals( PortfolioTagConstants.DAY_DEFAULT ) ) ) {
            try {
                // logService.debug( "getting month and year" );
                int day = Integer.parseInt( getOpeningDateDay() );
                int month = Integer.parseInt( getOpeningDateMonth() );
                int year = Integer.parseInt( getOpeningDateYear() );

                Calendar cal = new GregorianCalendar( year, month, day );
                cal.setLenient(false);
                fromDate = new org.portfolio.util.DateForXSL(cal.getTime().getTime(),false);
            } catch ( NumberFormatException e ) {
                logService.debug( "Error parsing date ", e );
            } catch ( IllegalArgumentException iae ) {
                logService.debug( "Invalid date caught", iae );
            }
        }
        return fromDate;
    }

    public void setOpeningDate( java.util.Date openingDate ) {
        if ( openingDate != null ) {
            GregorianCalendar cal = new GregorianCalendar();
            cal.setTime( openingDate );

            setOpeningDateDay( String.valueOf( cal.get( Calendar.DAY_OF_MONTH ) ) );
            setOpeningDateMonth( String.valueOf( cal.get( Calendar.MONTH ) ) );
            setOpeningDateYear( String.valueOf( cal.get( Calendar.YEAR ) ) );
        }
    }

    public String getOpeningDateDay() {
        return ( openingDateDay != null ? openingDateDay : PortfolioTagConstants.DAY_DEFAULT );
    }

    public void setOpeningDateDay( String openingDateDay ) {
        this.openingDateDay = ( openingDateDay != null ? openingDateDay : PortfolioTagConstants.DAY_DEFAULT );
    }

    public String getOpeningDateMonth() {
        return ( openingDateMonth != null ? openingDateMonth : PortfolioTagConstants.MONTH_DEFAULT );
    }

    public void setOpeningDateMonth( String openingDateMonth ) {
        this.openingDateMonth = ( openingDateMonth != null ? openingDateMonth : PortfolioTagConstants.MONTH_DEFAULT );
    }

    public String getOpeningDateYear() {
        return ( openingDateYear != null ? openingDateYear : PortfolioTagConstants.YEAR_DEFAULT );
    }

    public void setOpeningDateYear( String openingDateYear ) {
        this.openingDateYear = ( openingDateYear != null ? openingDateYear : PortfolioTagConstants.YEAR_DEFAULT );
    }

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        if (request.getParameter("viewfile.x") != null) return errors;

        if ((entryName == null) || (entryName.trim().length() == 0)) {
            errors.add("entryName", new ActionMessage("error.required.missing", "Name of performance"));
        } else if(entryName.trim().length() > PortfolioConstants.SIXTY_CHARS) {
            entryName = entryName.trim().substring(0, PortfolioConstants.SIXTY_CHARS);
            errors.add("entryName", new ActionMessage("error.lengthTooLong", "Name of performance", PortfolioConstants.SIXTY_CHARS_DESC));
        }

        //Type length
        if(type != null && type.trim().length() > PortfolioConstants.FIFTY_CHARS) {
            type = type.trim().substring(0, PortfolioConstants.FIFTY_CHARS);
            errors.add("type", new ActionMessage("error.lengthTooLong", "Type of performance", PortfolioConstants.FIFTY_CHARS_DESC));
        }

        //Location length
        if(location != null && location.trim().length() > PortfolioConstants.SIXTY_CHARS) {
            location = location.trim().substring(0, PortfolioConstants.SIXTY_CHARS);
            errors.add("location", new ActionMessage("error.lengthTooLong", "Location", PortfolioConstants.SIXTY_CHARS_DESC));
        }

        if ((description != null) && (description.length() > PortfolioConstants.EIGHT_HUNDRED_WORDS)) {
            description = description.substring(0, PortfolioConstants.EIGHT_HUNDRED_WORDS);
            errors.add("description", new ActionMessage("error.lengthTooLong", "Description", PortfolioConstants.EIGHT_HUNDRED_WORDS_DESC));
        }
        if ( DateValidation.checkDate( getOpeningDateMonth() , getOpeningDateDay(), getOpeningDateYear() ) == DateValidation.FAILURE ) {
            errors.add( "openingDate", new ActionMessage( "error.date.invalid", "Opening date" ) );
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
        buff.append("type=").append(getType()).append(",");
        buff.append("openingDate=").append(getOpeningDate()).append(",");
        buff.append("location=").append(getLocation()).append(",");
        buff.append("juried=").append(getJuried()).append(",");
        buff.append("description=").append(getDescription()).append(",");
        buff.append("is new? =>" + isNew());
        return buff.toString();
    }

    private String openingDateDay = PortfolioTagConstants.DAY_DEFAULT;
    private String openingDateMonth = PortfolioTagConstants.MONTH_DEFAULT;
    private String openingDateYear = PortfolioTagConstants.YEAR_DEFAULT;
    protected java.lang.String type = null;
    protected java.lang.String location = null;
    protected java.lang.String juried = "no";
    protected java.lang.String description = null;
    protected boolean isRemote = false;
}
