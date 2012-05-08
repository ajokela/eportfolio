/* $Name:  $ */
/* $Id: ExploreInventory.java,v 1.13 2010/10/27 19:24:57 ajokela Exp $ */
/*
 * $Header: /opt/UMN-src/portfolio/src/org/portfolio/model/ExploreInventory.java,v 1.13 2010/10/27 19:24:57 ajokela Exp $
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
 * This class encapsulates ExploreInventory element object.
 *
 * @author	John Bush
 * @since	0.0
 * @version	$Revision: 1.13 $
 */
public class ExploreInventory extends ElementBase {

    private static final long serialVersionUID = -5721608842455153847L;
	
    public java.lang.String getEntryName() {
        return (entryName != null ? entryName : EMPTY_STRING);
    }

    public void setEntryName(java.lang.String entryName) {
        this.entryName =  entryName;
    }


    public java.lang.String getFirstOccupation() {
        return (firstOccupation != null ? firstOccupation : EMPTY_STRING);
    }

    public void setFirstOccupation(java.lang.String firstOccupation) {
        this.firstOccupation = firstOccupation;
    }

    public java.lang.String getSecondOccupation() {
        return (secondOccupation != null ? secondOccupation : EMPTY_STRING);
    }

    public void setSecondOccupation(java.lang.String secondOccupation) {
        this.secondOccupation =  secondOccupation;
    }

    public java.lang.String getThirdOccupation() {
        return (thirdOccupation != null ? thirdOccupation : EMPTY_STRING);
    }

    public void setThirdOccupation(java.lang.String thirdOccupation) {
        this.thirdOccupation = thirdOccupation;
    }

    public java.lang.String getFirstLeisure() {
        return (firstLeisure != null ? firstLeisure : EMPTY_STRING);
    }

    public void setFirstLeisure(java.lang.String firstLeisure) {
        this.firstLeisure = firstLeisure;
    }

    public java.lang.String getSecondLeisure() {
        return (secondLeisure != null ? secondLeisure : EMPTY_STRING);
    }

    public void setSecondLeisure(java.lang.String secondLeisure) {
        this.secondLeisure = secondLeisure;
    }

    public java.lang.String getThirdLeisure() {
        return (thirdLeisure != null ? thirdLeisure : EMPTY_STRING);
    }

    public void setThirdLeisure(java.lang.String thirdLeisure) {
        this.thirdLeisure = thirdLeisure;
    }

    public java.lang.String getFirstLearning() {
        return (firstLearning != null ? firstLearning : EMPTY_STRING);
    }

    public void setFirstLearning(java.lang.String firstLearning) {
        this.firstLearning = firstLearning;
    }

    public java.lang.String getSecondLearning() {
        return (secondLearning != null ? secondLearning : EMPTY_STRING);
    }

    public void setSecondLearning(java.lang.String secondLearning) {
        this.secondLearning = secondLearning;
    }

    public java.lang.String getThirdLearning() {
        return (thirdLearning != null ? thirdLearning : EMPTY_STRING);
    }

    public void setThirdLearning(java.lang.String thirdLearning) {
        this.thirdLearning = thirdLearning;
    }

    public java.lang.String getReaction() {
        return (reaction != null ? reaction : EMPTY_STRING);
    }

    public void setReaction(java.lang.String reaction) {
        this.reaction = reaction;
    }

    public java.util.Date getDateTaken() {
        Date dateTaken = null;
        if ( ( !getDateTakenMonth().equals( PortfolioTagConstants.MONTH_DEFAULT ) ) &&
                ( !getDateTakenYear().equals( PortfolioTagConstants.YEAR_DEFAULT ) ) ) {
            try {
                int month = Integer.parseInt( getDateTakenMonth() );
                int year = Integer.parseInt( getDateTakenYear() );

                Calendar cal = new GregorianCalendar( year, month, 1 );
                cal.setLenient(false);
                dateTaken = new org.portfolio.util.DateForXSL(cal.getTime().getTime(),true);
            } catch ( NumberFormatException e ) {
                logService.debug( "Error parsing date ", e );
            } catch ( IllegalArgumentException iae ) {
                logService.debug( "Invalid date caught", iae );
            }
        }
        return dateTaken;
    }

    public void setDateTaken( java.util.Date dateTaken ) {
        if ( dateTaken != null ) {
            GregorianCalendar cal = new GregorianCalendar();
            cal.setTime( dateTaken );

            setDateTakenMonth( String.valueOf( cal.get( Calendar.MONTH ) ) );
            setDateTakenYear( String.valueOf( cal.get( Calendar.YEAR ) ) );
        }
    }
    public String getDateTakenMonth() {
        return ( dateTakenMonth != null ? dateTakenMonth : PortfolioTagConstants.MONTH_DEFAULT );
    }

    public void setDateTakenMonth(String dateTakenMonth) {
        this.dateTakenMonth = ( dateTakenMonth != null ? dateTakenMonth : PortfolioTagConstants.MONTH_DEFAULT );
    }

    public String getDateTakenYear() {
        return ( dateTakenYear != null ? dateTakenYear : PortfolioTagConstants.YEAR_DEFAULT );
    }

    public void setDateTakenYear(String dateTakenYear) {
        this.dateTakenYear = ( dateTakenYear != null ? dateTakenYear : PortfolioTagConstants.YEAR_DEFAULT );
    }


    //--------------------------------------------------------------------------
    // Methods dictated by implemented interfaces

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        if (request.getParameter("viewfile.x") != null) return errors;

        if ((entryName == null) || (entryName.trim().length() == 0)) {
            errors.add("entryName", new ActionMessage("error.required.missing", "Name of inventory"));
        } else if(entryName.trim().length() > PortfolioConstants.FIFTY_CHARS) {
            entryName = entryName.trim().substring(0, PortfolioConstants.FIFTY_CHARS);
            errors.add("entryName", new ActionMessage("error.lengthTooLong", "Name of inventory", PortfolioConstants.FIFTY_CHARS_DESC ));
        }
        if ( DateValidation.checkDate( getDateTakenMonth(), getDateTakenYear()) == DateValidation.FAILURE) {
            errors.add( "fromDate", new ActionMessage( "error.date.invalid", "Date taken" ) );
        }

        //First occupation length
        if(firstOccupation != null && firstOccupation.trim().length() > PortfolioConstants.FIFTY_CHARS) {
            firstOccupation = firstOccupation.trim().substring(0, PortfolioConstants.FIFTY_CHARS);
            errors.add("firstOccupation", new ActionMessage("error.lengthTooLong", "First choice - Occupations", PortfolioConstants.FIFTY_CHARS_DESC ));
        }

        //First Leisure length
        if(firstLeisure != null && firstLeisure.trim().length() > PortfolioConstants.FIFTY_CHARS) {
            firstLeisure = firstLeisure.trim().substring(0, PortfolioConstants.FIFTY_CHARS);
            errors.add("firstLeisure", new ActionMessage("error.lengthTooLong", "First choice - Leisure activities", PortfolioConstants.FIFTY_CHARS_DESC));
        }

        //First Learning length
        if(firstLearning != null && firstLearning.trim().length() > PortfolioConstants.FIFTY_CHARS) {
            firstLearning = firstLearning.trim().substring(0, PortfolioConstants.FIFTY_CHARS);
            errors.add("firstLearning", new ActionMessage("error.lengthTooLong", "First choice - Learning activities", PortfolioConstants.FIFTY_CHARS_DESC));
        }

        //Second occupation length
        if(secondOccupation != null && secondOccupation.trim().length() > PortfolioConstants.FIFTY_CHARS) {
            secondOccupation = secondOccupation.trim().substring(0, PortfolioConstants.FIFTY_CHARS);
            errors.add("secondOccupation", new ActionMessage("error.lengthTooLong", "Second choice - Occupations", PortfolioConstants.FIFTY_CHARS_DESC));
        }

        //Second Leisure length
        if(secondLeisure != null && secondLeisure.trim().length() > PortfolioConstants.FIFTY_CHARS) {
            secondLeisure = secondLeisure.trim().substring(0, PortfolioConstants.FIFTY_CHARS);
            errors.add("secondLeisure", new ActionMessage("error.lengthTooLong", "Second choice - Leisure activities", PortfolioConstants.FIFTY_CHARS_DESC ));
        }

        //Second Learning length
        if(secondLearning != null && secondLearning.trim().length() > PortfolioConstants.FIFTY_CHARS) {
            secondLearning = secondLearning.trim().substring(0, PortfolioConstants.FIFTY_CHARS);
            errors.add("secondLearning", new ActionMessage("error.lengthTooLong", "Second choice - Learning activities", PortfolioConstants.FIFTY_CHARS_DESC ));
        }

        //Third occupation length
        if(thirdOccupation != null && thirdOccupation.trim().length() > PortfolioConstants.FIFTY_CHARS) {
            thirdOccupation = thirdOccupation.trim().substring(0, PortfolioConstants.FIFTY_CHARS);
            errors.add("thirdOccupation", new ActionMessage("error.lengthTooLong", "Third choice - Occupations", PortfolioConstants.FIFTY_CHARS_DESC ));
        }

        //Third Leisure length
        if(thirdLeisure != null && thirdLeisure.trim().length() > PortfolioConstants.FIFTY_CHARS) {
            thirdLeisure = thirdLeisure.trim().substring(0, PortfolioConstants.FIFTY_CHARS);
            errors.add("thirdLeisure", new ActionMessage("error.lengthTooLong","Third choice - Leisure activities", PortfolioConstants.FIFTY_CHARS_DESC));
        }

        //Third Learning length
        if(thirdLearning != null && thirdLearning.trim().length() > PortfolioConstants.FIFTY_CHARS) {
            thirdLearning = thirdLearning.trim().substring(0, PortfolioConstants.FIFTY_CHARS);
            errors.add("thirdLearning", new ActionMessage("error.lengthTooLong", "Third choice - Learning activities", PortfolioConstants.FIFTY_CHARS_DESC ));
        }
        
        if ((reaction != null) && (reaction.length() > PortfolioConstants.EIGHT_HUNDRED_WORDS)) {
            reaction = reaction.substring(0, PortfolioConstants.EIGHT_HUNDRED_WORDS);
            errors.add("description", new ActionMessage("error.lengthTooLong", "Interpretation / Reaction", PortfolioConstants.EIGHT_HUNDRED_WORDS_DESC));
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
        buff.append("dateTaken=").append(getDateTaken()).append(",");
        buff.append("firstOccupation=").append(getFirstOccupation()).append(",");
        buff.append("secondOccupation=").append(getSecondOccupation()).append(",");
        buff.append("thirdOccupation=").append(getThirdOccupation()).append(",");
        buff.append("firstLeisure=").append(getFirstLeisure()).append(",");
        buff.append("secondLeisure=").append(getSecondLeisure()).append(",");
        buff.append("thirdLeisure=").append(getThirdLeisure()).append(",");
        buff.append("firstLearning=").append(getFirstLearning()).append(",");
        buff.append("secondLearning=").append(getSecondLearning()).append(",");
        buff.append("thirdLearning=").append(getThirdLearning()).append(",");
        buff.append("reaction=").append(getReaction()).append(",");
        buff.append("is new? =>" + isNew());
        return buff.toString();
    }

    protected String dateTakenMonth = PortfolioTagConstants.MONTH_DEFAULT;
    protected String dateTakenYear = PortfolioTagConstants.MONTH_DEFAULT;
    protected java.lang.String firstOccupation = null;
    protected java.lang.String secondOccupation = null;
    protected java.lang.String thirdOccupation = null;
    protected java.lang.String firstLeisure = null;
    protected java.lang.String secondLeisure = null;
    protected java.lang.String thirdLeisure = null;
    protected java.lang.String firstLearning = null;
    protected java.lang.String secondLearning = null;
    protected java.lang.String thirdLearning = null;
    protected java.lang.String reaction = null;
    protected boolean isRemote = false;
}
