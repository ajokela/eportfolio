/* $Name:  $ */
/* $Id: EducHistory.java,v 1.13 2010/10/27 19:24:57 ajokela Exp $ */
/*
 * $Header: /opt/UMN-src/portfolio/src/org/portfolio/model/EducHistory.java,v 1.13 2010/10/27 19:24:57 ajokela Exp $
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
 * This class encapsulates EducHistory element object.
 *
 * @author	John Bush
 * @since	0.0
 * @version	$Revision: 1.13 $
 */
public class EducHistory extends ElementBase {

    private static final long serialVersionUID = -8429346352760227125L;
	
    public java.lang.String getEntryName() {
        return (entryName != null ? entryName : EMPTY_STRING);
    }

    public void setEntryName(java.lang.String entryName) {
        this.entryName = entryName;
    }

    public java.lang.String getDegree() {
        return (degree != null ? degree : EMPTY_STRING);
    }

    public void setDegree(java.lang.String degree) {
        this.degree = degree;
    }
    /**
     * Dynamically determines the FromDate
     * @return the fromDate, or null if not parsable.
     */
    public java.util.Date getFromDate() {
        Date fromDate = null;
        if ( ( !getFromDateMonth().equals( PortfolioTagConstants.MONTH_DEFAULT ) ) &&
                ( !getFromDateYear().equals( PortfolioTagConstants.YEAR_DEFAULT ) ) ) {
            try {
                // logService.debug( "getting month and year" );
                int month = Integer.parseInt( getFromDateMonth() );
                int year = Integer.parseInt( getFromDateYear() );

                Calendar cal = new GregorianCalendar( year, month, 1 );
                cal.setLenient(false);
                fromDate = new DateForXSL( cal.getTime().getTime(),true);
            } catch ( NumberFormatException e ) {
                logService.debug( "Error parsing date ", e );
            } catch ( IllegalArgumentException iae ) {
                logService.debug( "Invalid date caught", iae );
            }
        }
        return fromDate;
    }

    public void setFromDate(java.util.Date fromDate) {
        if ( fromDate != null ) {
            GregorianCalendar cal = new GregorianCalendar();
            cal.setTime( fromDate );

            setFromDateMonth( String.valueOf( cal.get( Calendar.MONTH ) ) );
            setFromDateYear( String.valueOf( cal.get( Calendar.YEAR ) ) );
        }
    }

    public java.util.Date getToDate() {
        Date toDate = null;
        if ( ( !getToDateMonth().equals( PortfolioTagConstants.MONTH_DEFAULT ) ) &&
                ( !getToDateYear().equals( PortfolioTagConstants.YEAR_DEFAULT ) ) ) {
            try {
                // logService.debug( "getting month and year" );
                int month = Integer.parseInt( getToDateMonth() );
                int year = Integer.parseInt( getToDateYear() );

                Calendar cal = new GregorianCalendar( year, month, 1 );
                cal.setLenient(false);
                toDate = new DateForXSL(cal.getTime().getTime(),true);
            } catch ( NumberFormatException e ) {
                logService.debug( "Error parsing date ", e );
            } catch ( IllegalArgumentException iae ) {
                logService.debug( "Invalid date caught", iae );
            }
        }
        return toDate;
    }

    public void setToDate(java.util.Date toDate) {
        if ( toDate != null ) {
            GregorianCalendar cal = new GregorianCalendar();
            cal.setTime( toDate );

            setToDateMonth( String.valueOf( cal.get( Calendar.MONTH ) ) );
            setToDateYear( String.valueOf( cal.get( Calendar.YEAR ) ) );
        }
    }

    public java.util.Date getDegreeDate() {
        Date toDate = null;
        if ( ( !getDegreeDateMonth().equals( PortfolioTagConstants.MONTH_DEFAULT ) ) &&
                ( !getDegreeDateYear().equals( PortfolioTagConstants.YEAR_DEFAULT ) ) &&
                ( !getDegreeDateDay().equals( PortfolioTagConstants.DAY_DEFAULT ) ) ) {
            try {
                int day = Integer.parseInt( getDegreeDateDay() );
                int month = Integer.parseInt( getDegreeDateMonth() );
                int year = Integer.parseInt( getDegreeDateYear() );

                Calendar cal = new GregorianCalendar( year, month, day );
                cal.setLenient(false);
                toDate = cal.getTime();
            } catch ( NumberFormatException e ) {
                logService.debug( "Error parsing date ", e );
            } catch ( IllegalArgumentException iae ) {
                logService.debug( "Invalid date caught", iae );
            }
        }
        return toDate;
    }

    public void setDegreeDate(java.util.Date degreeDate) {
        if ( degreeDate != null ) {
            GregorianCalendar cal = new GregorianCalendar();
            cal.setTime( degreeDate );

            setDegreeDateDay( String.valueOf( cal.get( Calendar.DAY_OF_MONTH ) ) );
            setDegreeDateMonth( String.valueOf( cal.get( Calendar.MONTH ) ) );
            setDegreeDateYear( String.valueOf( cal.get( Calendar.YEAR ) ) );
        }
    }

    public String getFromDateMonth() {
        return ( fromDateMonth != null ? fromDateMonth : PortfolioTagConstants.MONTH_DEFAULT );
    }

    public void setFromDateMonth(String fromDateMonth) {
        this.fromDateMonth = ( fromDateMonth != null ? fromDateMonth : PortfolioTagConstants.MONTH_DEFAULT );
    }

    public String getFromDateYear() {
        return ( fromDateYear != null ? fromDateYear : PortfolioTagConstants.YEAR_DEFAULT );
    }

    public void setFromDateYear(String fromDateYear) {
        this.fromDateYear = ( fromDateYear != null ? fromDateYear : PortfolioTagConstants.YEAR_DEFAULT );
    }


    public String getToDateMonth() {
        return ( toDateMonth != null ? toDateMonth : PortfolioTagConstants.MONTH_DEFAULT );
    }

    public void setToDateMonth(String toDateMonth ) {
        this.toDateMonth = ( toDateMonth != null ? toDateMonth : PortfolioTagConstants.MONTH_DEFAULT );
    }

    public String getToDateYear() {
        return ( toDateYear != null ? toDateYear : PortfolioTagConstants.YEAR_DEFAULT );
    }

    public void setToDateYear(String toDateYear) {
        this.toDateYear = ( toDateYear != null ? toDateYear : PortfolioTagConstants.YEAR_DEFAULT );
    }

    public String getDegreeDateYear() {
        return ( degreeDateYear != null ? degreeDateYear : PortfolioTagConstants.YEAR_DEFAULT );
    }
    public void setDegreeDateYear(String degreeDateYear) {
        this.degreeDateYear = ( degreeDateYear != null ? degreeDateYear : PortfolioTagConstants.YEAR_DEFAULT );
    }

    public String getDegreeDateMonth() {
        return ( degreeDateMonth != null ? degreeDateMonth : PortfolioTagConstants.MONTH_DEFAULT );
    }

    public void setDegreeDateMonth(String degreeDateMonth) {
        this.degreeDateMonth = ( degreeDateMonth != null ? degreeDateMonth : PortfolioTagConstants.MONTH_DEFAULT );
    }


    public String getDegreeDateDay() {
        return ( degreeDateDay != null ? degreeDateDay : PortfolioTagConstants.DAY_DEFAULT );
    }

    public void setDegreeDateDay( String degreeDateDay ) {
        this.degreeDateDay = ( degreeDateDay != null ? degreeDateDay : PortfolioTagConstants.DAY_DEFAULT );
    }

    //--------------------------------------------------------------------------
    // Methods dictated by implemented interfaces

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        if (request.getParameter("viewfile.x") != null) return errors;

        if ((entryName == null) || (entryName.trim().length() == 0)) {
            errors.add("entryName", new ActionMessage("error.required.missing", "School attended"));
        } else if(entryName.trim().length() > PortfolioConstants.FIFTY_CHARS) {
            entryName = entryName.trim().substring(0, PortfolioConstants.FIFTY_CHARS);
            // bug 329 - missing period.
            errors.add("entryName", new ActionMessage("error.lengthTooLong","School attended", PortfolioConstants.FIFTY_CHARS_DESC ));
        }
        // fromDate
        if ( DateValidation.checkDate( getFromDateMonth(), getFromDateYear() ) == DateValidation.FAILURE ) {
            errors.add( "fromDate", new ActionMessage( "error.date.invalid", "From" ) );
        }

        // toDate
        if ( DateValidation.checkDate( getToDateMonth(), getToDateYear() ) == DateValidation.FAILURE ) {
            errors.add( "toDate", new ActionMessage( "error.date.invalid", "To" ) );
        }

        //Degree earned length
        if(degree != null && degree.trim().length() > PortfolioConstants.FIFTY_CHARS) {
            degree = degree.trim().substring(0, PortfolioConstants.FIFTY_CHARS);
            errors.add("degree", new ActionMessage("error.lengthTooLong", "Degree earned", PortfolioConstants.FIFTY_CHARS_DESC));
        }

        // degreeDate
        if ( DateValidation.checkDate( getDegreeDateMonth(), getDegreeDateDay() , getDegreeDateYear() ) == DateValidation.FAILURE ) {
            errors.add( "degreeDate", new ActionMessage( "error.date.invalid", "Date degree earned" ) );
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
        buff.append("degree=").append(getDegree()).append(",");
        buff.append("fromDate=").append(getFromDate()).append(",");
        buff.append("toDate=").append(getToDate()).append(",");
        buff.append("degreeDate=").append(getDegreeDate()).append(",");
        buff.append("is new? =>" + isNew());
        return buff.toString();
    }

    protected java.lang.String degree = null;
    protected String fromDateMonth = PortfolioTagConstants.MONTH_DEFAULT;;
    protected String fromDateYear = PortfolioTagConstants.YEAR_DEFAULT;;
    protected String toDateMonth = PortfolioTagConstants.MONTH_DEFAULT;;
    protected String toDateYear = PortfolioTagConstants.YEAR_DEFAULT;;
    protected String degreeDateDay = PortfolioTagConstants.DAY_DEFAULT;;;
    protected String degreeDateMonth = PortfolioTagConstants.MONTH_DEFAULT;;
    protected String degreeDateYear = PortfolioTagConstants.YEAR_DEFAULT;;
    protected boolean isRemote = false;
}
