/* $Name:  $ */
/* $Id: MyersBriggs.java,v 1.7 2010/10/27 19:24:57 ajokela Exp $ */
/*
 * $Header: /opt/UMN-src/portfolio/src/org/portfolio/model/MyersBriggs.java,v 1.7 2010/10/27 19:24:57 ajokela Exp $
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
 * This class encapsulates MyersBriggs element object.
 *
 * @author	John Bush
 * @author      Jack Brown, University of Minnesota
 * @since	0.0
 * @version	$Revision: 1.7 $
 */
public class MyersBriggs extends ElementBase {

    private static final long serialVersionUID = -4710933931514812692L;
	
    public java.lang.String getEntryName() {
        return (entryName != null ? entryName : EMPTY_STRING);
    }

    public void setEntryName(java.lang.String entryName) {
        this.entryName = entryName;
    }

    public java.util.Date getTestDate() {
        Date testDate = null;

        // logService.debug( "getSurveyDate()" );

        if ( ( !getTestDateMonth().equals( PortfolioTagConstants.MONTH_DEFAULT ) ) &&
                ( !getTestDateYear().equals( PortfolioTagConstants.YEAR_DEFAULT ) ) ) {
            try {
                // logService.debug( "getting month and year" );
                int month = Integer.parseInt( getTestDateMonth() );
                int year = Integer.parseInt( getTestDateYear() );

                Calendar cal = new GregorianCalendar( year, month, 1 );
                cal.setLenient(false);
                testDate = new org.portfolio.util.DateForXSL(
                        cal.getTime().getTime(),true);  
            } catch ( NumberFormatException e ) {
                logService.debug( "Error parsing date ", e );
            } catch ( IllegalArgumentException iae ) {
                logService.debug( "Invalid date caught", iae );
            }
        }
        //logService.debug( "surveyDate is " + testDate );
        return testDate;
    }

    public void setTestDate(java.util.Date testDate) {
        if ( testDate != null ) {
            GregorianCalendar cal = new GregorianCalendar();
            cal.setTime( testDate );

            setTestDateMonth( String.valueOf( cal.get( Calendar.MONTH ) ) );
            setTestDateYear( String.valueOf( cal.get( Calendar.YEAR ) ) );
        }
    }

    public String getTestDateMonth() {
        return ( testMonth != null ? testMonth : PortfolioTagConstants.MONTH_DEFAULT );
    }

    public void setTestDateMonth(String testMonth) {
        this.testMonth = ( testMonth != null ? testMonth : PortfolioTagConstants.MONTH_DEFAULT );
    }

    public String getTestDateYear() {
        return ( testYear != null ? testYear : PortfolioTagConstants.YEAR_DEFAULT );
    }

    public void setTestDateYear(String testYear) {
        this.testYear = ( testYear != null ? testYear : PortfolioTagConstants.YEAR_DEFAULT );
    }

    public java.lang.String getPersType() {
        return (persType != null ? persType : EMPTY_STRING);
    }

    public void setPersType(java.lang.String persType) {
        this.persType = persType;
    }

    public java.lang.String getInterpretation() {
        return (interpretation != null ? interpretation : EMPTY_STRING);
    }

    public void setInterpretation(java.lang.String interpretation) {
        this.interpretation =  interpretation;
    }

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        if (request.getParameter("viewfile.x") != null) return errors;

        if ((entryName == null) || (entryName.trim().length() == 0)) {
            errors.add("entryName", new ActionMessage("error.required.missing", "Type of inventory"));
        } else if(entryName.trim().length() > PortfolioConstants.FIFTY_CHARS) {
            entryName = entryName.trim().substring(0, PortfolioConstants.FIFTY_CHARS);
            errors.add("entryName", new ActionMessage("error.lengthTooLong", "Type of inventory", PortfolioConstants.FIFTY_CHARS_DESC));
        }

        // new date validation
        int dateValidation = DateValidation.checkDate( getTestDateMonth(), getTestDateYear(), true );
        if ( dateValidation == DateValidation.MISSING_REQUIRED ) {
            errors.add( "testDate", new ActionMessage( "error.required.missing" , "Date taken") );
        } else if ( dateValidation == DateValidation.FAILURE ) {
            errors.add( "testDate", new ActionMessage( "error.date.invalid", "Date taken" ) );
        }

        //Personality type length
        if(persType != null && persType.trim().length() > PortfolioConstants.TWENTY_FIVE_CHARS) {
            persType = persType.trim().substring(0, PortfolioConstants.TWENTY_FIVE_CHARS);
            errors.add("persType", new ActionMessage("error.lengthTooLong", "Personality type", PortfolioConstants.TWENTY_FIVE_CHARS_DESC));
        }

        if ((interpretation != null) && (interpretation.length() > PortfolioConstants.EIGHT_HUNDRED_WORDS)) {
            interpretation = interpretation.substring(0, PortfolioConstants.EIGHT_HUNDRED_WORDS);
            errors.add("interpretation", new ActionMessage("error.lengthTooLong", "Interpretation", PortfolioConstants.EIGHT_HUNDRED_WORDS_DESC));
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
        buff.append("testDate=").append(getTestDate()).append(",");
        buff.append("persType=").append(getPersType()).append(",");
        buff.append("interpretation=").append(getInterpretation()).append(",");
        buff.append("is new? =>" + isNew());
        return buff.toString();
    }

    protected String testMonth = PortfolioTagConstants.MONTH_DEFAULT;
    protected String testYear = PortfolioTagConstants.YEAR_DEFAULT;
    protected java.lang.String persType = null;
    protected java.lang.String interpretation = null;
    protected boolean isRemote = false;
}
