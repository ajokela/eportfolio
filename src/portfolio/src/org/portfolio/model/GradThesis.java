/* $Name:  $ */
/* $Id: GradThesis.java,v 1.7 2010/10/27 19:24:57 ajokela Exp $ */
/*
 * $Header: /opt/UMN-src/portfolio/src/org/portfolio/model/GradThesis.java,v 1.7 2010/10/27 19:24:57 ajokela Exp $
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
 * This class encapsulates GradThesis element object.
 *
 * @author	John Bush
 * @author      Jack Brown, University of Minnesota
 * @since	0.0
 * @version	$Revision: 1.7 $
 */
public class GradThesis extends ElementBase {

    private static final long serialVersionUID = -874946431872484310L;
	
    public java.lang.String getEntryName() {
        return (entryName != null ? entryName : EMPTY_STRING);
    }

    public void setEntryName(java.lang.String entryName) {
        this.entryName =  entryName;
    }

    public java.lang.String getUniversity() {
        return (university != null ? university : EMPTY_STRING);
    }

    public void setUniversity(java.lang.String university) {
        this.university = university;
    }

    public java.lang.String getIntroduction() {
        return (introduction != null ? introduction : EMPTY_STRING);
    }

    public void setIntroduction(java.lang.String introduction) {
        this.introduction = introduction;
    }

    public java.lang.String getCommentary() {
        return (commentary != null ? commentary : EMPTY_STRING);
    }

    public void setCommentary(java.lang.String commentary) {
        this.commentary = commentary;
    }


    public java.util.Date getDefenseDate() {
        Date toDate = null;
        if ( ( !getDefenseDateMonth().equals( PortfolioTagConstants.MONTH_DEFAULT ) ) &&
                ( !getDefenseDateYear().equals( PortfolioTagConstants.YEAR_DEFAULT ) ) &&
                ( !getDefenseDateDay().equals( PortfolioTagConstants.DAY_DEFAULT ) ) ) {
            try {
                int day = Integer.parseInt( getDefenseDateDay() );
                int month = Integer.parseInt( getDefenseDateMonth() );
                int year = Integer.parseInt( getDefenseDateYear() );

                Calendar cal = new GregorianCalendar( year, month, day );
                cal.setLenient(false);
                toDate = new org.portfolio.util.DateForXSL(cal.getTime().getTime(),false);
            } catch ( NumberFormatException e ) {
                logService.debug( "Error parsing date ", e );
            }  catch ( IllegalArgumentException iae ) {
                logService.debug( "Invalid date caught", iae );
            }
        }
        return toDate;
    }

    public void setDefenseDate( java.util.Date defenseDate ) {
        if ( defenseDate != null ) {
            GregorianCalendar cal = new GregorianCalendar();
            cal.setTime( defenseDate );

            setDefenseDateDay( String.valueOf( cal.get( Calendar.DAY_OF_MONTH ) ) );
            setDefenseDateMonth( String.valueOf( cal.get( Calendar.MONTH ) ) );
            setDefenseDateYear( String.valueOf( cal.get( Calendar.YEAR ) ) );
        }
    }


    public String getDefenseDateMonth() {
        return ( defenseDateMonth != null ? defenseDateMonth : PortfolioTagConstants.MONTH_DEFAULT );
    }

    public void setDefenseDateMonth(String defenseDateMonth) {
        this.defenseDateMonth = ( defenseDateMonth != null ? defenseDateMonth : PortfolioTagConstants.MONTH_DEFAULT );
    }

    public String getDefenseDateYear() {
        return ( defenseDateYear != null ? defenseDateYear : PortfolioTagConstants.YEAR_DEFAULT );
    }

    public void setDefenseDateYear( String defenseDateYear ) {
        this.defenseDateYear = ( defenseDateYear != null ? defenseDateYear : PortfolioTagConstants.YEAR_DEFAULT );
    }

    public String getDefenseDateDay() {
        return ( defenseDateDay != null ? defenseDateDay : PortfolioTagConstants.DAY_DEFAULT );
    }

    public void setDefenseDateDay( String defenseDateDay ) {
        this.defenseDateDay = ( defenseDateDay != null ? defenseDateDay : PortfolioTagConstants.DAY_DEFAULT );
    }

    //--------------------------------------------------------------------------
    // Methods dictated by implemented interfaces

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        if (request.getParameter("viewfile.x") != null) return errors;

        if ((entryName == null) || (entryName.trim().length() == 0)) {
            errors.add("entryName", new ActionMessage("error.required.missing", "Thesis title"));
        } else if(entryName.trim().length() > PortfolioConstants.FIFTY_CHARS) {
            entryName = entryName.trim().substring(0, PortfolioConstants.FIFTY_CHARS);
            errors.add("entryName", new ActionMessage("error.lengthTooLong", "Thesis title", PortfolioConstants.FIFTY_CHARS_DESC));
        }


        if ( ( introduction != null ) && ( introduction.length() > PortfolioConstants.EIGHT_HUNDRED_WORDS ) ) {
            introduction = introduction.substring( 0, PortfolioConstants.EIGHT_HUNDRED_WORDS );
            errors.add( "introduction", new ActionMessage( "error.lengthTooLong", "Introduction", PortfolioConstants.EIGHT_HUNDRED_WORDS_DESC ) );
        }

        //University length validation
        if(university != null && university.trim().length() > PortfolioConstants.FIFTY_CHARS) {
            university = university.trim().substring(0, PortfolioConstants.FIFTY_CHARS);
            errors.add("university", new ActionMessage("error.lengthTooLong", "University of completion", PortfolioConstants.FIFTY_CHARS_DESC ));
        }

        // defense date
        if ( DateValidation.checkDate( getDefenseDateMonth(), getDefenseDateDay(), getDefenseDateYear() ) == DateValidation.FAILURE ) {
            errors.add( "degreeDate", new ActionMessage( "error.date.invalid", "Date of defense" ) );
        }

        if ((commentary != null) && (commentary.length() > PortfolioConstants.EIGHT_HUNDRED_WORDS)) {
            commentary = commentary.substring(0, PortfolioConstants.EIGHT_HUNDRED_WORDS);
            errors.add("commentary", new ActionMessage("error.lengthTooLong", "Abstract / Comments", PortfolioConstants.EIGHT_HUNDRED_WORDS_DESC ));
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
        buff.append("university=").append(getUniversity()).append(",");
        buff.append("defenseDate=").append(getDefenseDate()).append(",");
        buff.append("introduction=").append(getIntroduction()).append(",");
        buff.append("commentary=").append(getCommentary()).append(",");
        buff.append("is new? =>" + isNew());
        return buff.toString();
    }

    protected java.lang.String university = null;
    protected String defenseDateDay = PortfolioTagConstants.DAY_DEFAULT;
    protected String defenseDateMonth = PortfolioTagConstants.MONTH_DEFAULT;
    protected String defenseDateYear = PortfolioTagConstants.YEAR_DEFAULT;
    protected java.lang.String introduction = null;
    protected java.lang.String commentary = null;
    protected boolean isRemote = false;
}
