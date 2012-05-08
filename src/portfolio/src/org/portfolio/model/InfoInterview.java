/* $Name:  $ */
/* $Id: InfoInterview.java,v 1.13 2010/10/27 19:24:57 ajokela Exp $ */
/*
 * $Header: /opt/UMN-src/portfolio/src/org/portfolio/model/InfoInterview.java,v 1.13 2010/10/27 19:24:57 ajokela Exp $
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
 * This class encapsulates InfoInterview element object.
 *
 * @author	John Bush
 * @since	0.0
 * @version	$Revision: 1.13 $
 */
public class InfoInterview extends ElementBase {

    private static final long serialVersionUID = -7530901708504669351L;
	
    public java.lang.String getEntryName() {
        return (entryName != null ? entryName : EMPTY_STRING);
    }

    public void setEntryName(java.lang.String entryName) {
        this.entryName = entryName;
    }


    public java.lang.String getOccupation() {
        return (occupation != null ? occupation : EMPTY_STRING);
    }

    public void setOccupation(java.lang.String occupation) {
        this.occupation = occupation;
    }

    public java.lang.String getOrganization() {
        return (organization != null ? organization : EMPTY_STRING);
    }

    public void setOrganization(java.lang.String organization) {
        this.organization = organization;
    }

    public java.lang.String getInfoOccupation() {
        return (infoOccupation != null ? infoOccupation : EMPTY_STRING);
    }

    public void setInfoOccupation(java.lang.String infoOccupation) {
        this.infoOccupation = infoOccupation;
    }

    public java.lang.String getAddlNotes() {
        return (addlNotes != null ? addlNotes : EMPTY_STRING);
    }

    public void setAddlNotes(java.lang.String addlNotes) {
        this.addlNotes = addlNotes;
    }


    public java.util.Date getIntDate() {
        Date intDate = null;
        if ( ( !getIntDateMonth().equals( PortfolioTagConstants.MONTH_DEFAULT ) ) &&
                ( !getIntDateYear().equals( PortfolioTagConstants.YEAR_DEFAULT ) ) ) {
            try {
                int month = Integer.parseInt( getIntDateMonth() );
                int year = Integer.parseInt( getIntDateYear() );

                Calendar cal = new GregorianCalendar( year, month, 1 );
                cal.setLenient(false);
                intDate = new org.portfolio.util.DateForXSL(cal.getTime().getTime(),true);
            } catch ( NumberFormatException e ) {
                logService.debug( "Error parsing date ", e );
            } catch ( IllegalArgumentException iae ) {
                logService.debug( "Invalid date caught", iae );
            }
        }
        return intDate;
    }

    public void setIntDate( java.util.Date intDate ) {
        if ( intDate != null ) {
            GregorianCalendar cal = new GregorianCalendar();
            cal.setTime( intDate );

            setIntDateMonth( String.valueOf( cal.get( Calendar.MONTH ) ) );
            setIntDateYear( String.valueOf( cal.get( Calendar.YEAR ) ) );
        }
    }

    public String getIntDateMonth() {
        return ( intDateMonth != null ? intDateMonth : PortfolioTagConstants.MONTH_DEFAULT );
    }

    public void setIntDateMonth(String intDateMonth) {
        this.intDateMonth = ( intDateMonth != null ? intDateMonth : PortfolioTagConstants.MONTH_DEFAULT );
    }

    public String getIntDateYear() {
        return ( intDateYear != null ? intDateYear : PortfolioTagConstants.YEAR_DEFAULT );
    }

    public void setIntDateYear(String intDateYear) {
        this.intDateYear = ( intDateYear != null ? intDateYear : PortfolioTagConstants.YEAR_DEFAULT );
    }


    //--------------------------------------------------------------------------
    // Methods dictated by implemented interfaces

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        if (request.getParameter("viewfile.x") != null) return errors;
        // correction for bug 308
        if ((entryName == null) || (entryName.trim().length() == 0)) {
            errors.add("entryName", new ActionMessage("error.required.missing", "Name of person interviewed" ));
        } else if(entryName.trim().length() > PortfolioConstants.FIFTY_CHARS) {
            entryName = entryName.trim().substring(0, PortfolioConstants.FIFTY_CHARS);
            errors.add("entryName", new ActionMessage("error.lengthTooLong", "Name of person interviewed", PortfolioConstants.FIFTY_CHARS_DESC));
        }
        // interview date
        if ( DateValidation.checkDate( getIntDateMonth() , getIntDateYear()) == DateValidation.FAILURE ) {
            errors.add( "intDate", new ActionMessage( "error.date.invalid", "Date of interview" ) );
        }

        //Occupation length
        if(occupation != null && occupation.trim().length() > PortfolioConstants.FIFTY_CHARS) {
            occupation = occupation.trim().substring(0, PortfolioConstants.FIFTY_CHARS);
            errors.add("occupation", new ActionMessage("error.lengthTooLong", "Occupation", PortfolioConstants.FIFTY_CHARS_DESC));
        }

        //Organization length
        if(organization != null && organization.trim().length() > PortfolioConstants.FIFTY_CHARS) {
            organization = organization.trim().substring(0, PortfolioConstants.FIFTY_CHARS);
            errors.add("organization", new ActionMessage("error.lengthTooLong", "Institution / Organization", PortfolioConstants.FIFTY_CHARS_DESC));
        }
        
        if ((infoOccupation != null) && (infoOccupation.length() > PortfolioConstants.EIGHT_HUNDRED_WORDS)) {
            infoOccupation = infoOccupation.substring(0, PortfolioConstants.EIGHT_HUNDRED_WORDS);
            errors.add("infoOccupation", new ActionMessage("error.lengthTooLong", "Information about this occupation", PortfolioConstants.EIGHT_HUNDRED_WORDS_DESC));
        }

        if ((addlNotes != null) && (addlNotes.length() > PortfolioConstants.EIGHT_HUNDRED_WORDS)) {
            addlNotes = addlNotes.substring(0, PortfolioConstants.EIGHT_HUNDRED_WORDS);
            errors.add("addlNotes", new ActionMessage("error.lengthTooLong", "Additional notes / Comments ", PortfolioConstants.EIGHT_HUNDRED_WORDS_DESC ));
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
        buff.append("intDate=").append(getIntDate()).append(",");
        buff.append("occupation=").append(getOccupation()).append(",");
        buff.append("organization=").append(getOrganization()).append(",");
        buff.append("infoOccupation=").append(getInfoOccupation()).append(",");
        buff.append("addlNotes=").append(getAddlNotes()).append(",");
        buff.append("is new? =>" + isNew());
        return buff.toString();
    }

    protected String intDateMonth = PortfolioTagConstants.MONTH_DEFAULT;
    protected String intDateYear = PortfolioTagConstants.YEAR_DEFAULT;
    protected java.lang.String occupation = null;
    protected java.lang.String organization = null;
    protected java.lang.String infoOccupation = null;
    protected java.lang.String addlNotes = null;
    protected boolean isRemote = false;
}
