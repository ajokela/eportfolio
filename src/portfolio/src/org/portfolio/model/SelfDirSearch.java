/* $Name:  $ */
/* $Id: SelfDirSearch.java,v 1.13 2010/10/27 19:24:57 ajokela Exp $ */
/*
 * $Header: /opt/UMN-src/portfolio/src/org/portfolio/model/SelfDirSearch.java,v 1.13 2010/10/27 19:24:57 ajokela Exp $
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
 * This class encapsulates SelfDirSearch element object.
 *
 * @author	John Bush
 * @since	0.0
 * @version	$Revision: 1.13 $
 */
public class SelfDirSearch extends ElementBase {

    private static final long serialVersionUID = 9080173642017234797L;
	
    public java.lang.String getEntryName() {
        return (entryName != null ? entryName : EMPTY_STRING);
    }

    public void setEntryName(java.lang.String entryName) {
        this.entryName = entryName;
    }


    public java.lang.String getRealistic() {
        return (realistic != null ? realistic : EMPTY_STRING);
    }

    public void setRealistic(java.lang.String realistic) {
        this.realistic = realistic;
    }

    public java.lang.String getInvestigative() {
        return (investigative != null ? investigative : EMPTY_STRING);
    }

    public void setInvestigative(java.lang.String investigative) {
        this.investigative = investigative;
    }

    public java.lang.String getArtistic() {
        return (artistic != null ? artistic : EMPTY_STRING);
    }

    public void setArtistic(java.lang.String artistic) {
        this.artistic = artistic;
    }

    public java.lang.String getSocial() {
        return (social != null ? social : EMPTY_STRING);
    }

    public void setSocial(java.lang.String social) {
        this.social = social;
    }

    public java.lang.String getEnterprising() {
        return (enterprising != null ? enterprising : EMPTY_STRING);
    }

    public void setEnterprising(java.lang.String enterprising) {
        this.enterprising = enterprising;
    }

    public java.lang.String getConventional() {
        return (conventional != null ? conventional : EMPTY_STRING);
    }

    public void setConventional(java.lang.String conventional) {
        this.conventional = conventional;
    }

    public java.lang.String getSummaryCode() {
        return (summaryCode != null ? summaryCode : EMPTY_STRING);
    }

    public void setSummaryCode(java.lang.String summaryCode) {
        this.summaryCode = summaryCode;
    }

    public java.lang.String getOccuInfo() {
        return (occuInfo != null ? occuInfo : EMPTY_STRING);
    }

    public void setOccuInfo(java.lang.String occuInfo) {
        this.occuInfo = occuInfo;
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
                dateTaken = new org.portfolio.util.DateForXSL(cal.getTime().getTime(),false);
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

    public void setDateTakenMonth( String dateTakenMonth ) {
        this.dateTakenMonth = ( dateTakenMonth != null ? dateTakenMonth : PortfolioTagConstants.MONTH_DEFAULT );
    }

    public String getDateTakenYear() {
        return ( dateTakenYear != null ? dateTakenYear : PortfolioTagConstants.YEAR_DEFAULT );
    }

    public void setDateTakenYear( String dateTakenYear ) {
        this.dateTakenYear = ( dateTakenYear != null ? dateTakenYear : PortfolioTagConstants.YEAR_DEFAULT );
    }


    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        if (request.getParameter("viewfile.x") != null) return errors;

        if ((entryName == null) || (entryName.trim().length() == 0)) {
            errors.add("entryName", new ActionMessage("error.required.missing", "Name of inventory"));
        } else if(entryName.trim().length() > PortfolioConstants.FIFTY_CHARS) {
            entryName = entryName.trim().substring(0, PortfolioConstants.FIFTY_CHARS);
            errors.add("entryName", new ActionMessage("error.lengthTooLong", "Name of inventory", PortfolioConstants.FIFTY_CHARS_DESC));
        }

        if ( DateValidation.checkDate( getDateTakenMonth(), getDateTakenYear()) == DateValidation.FAILURE ) {
            errors.add( "fromDate", new ActionMessage( "error.date.invalid", "Date taken" ) );
        }
        //Realistic length
        if(realistic != null && realistic.trim().length() > PortfolioConstants.THIRTY_CHARS) {
            realistic = realistic.trim().substring(0, PortfolioConstants.THIRTY_CHARS);
            errors.add("realistic", new ActionMessage("error.lengthTooLong", "Realistic score", PortfolioConstants.THIRTY_CHARS_DESC));
        }
        
        //Investigative length
        if(investigative != null && investigative.trim().length() > PortfolioConstants.THIRTY_CHARS) {
            investigative = investigative.trim().substring(0, PortfolioConstants.THIRTY_CHARS);
            errors.add("investigative", new ActionMessage("error.lengthTooLong", "Investigative score", PortfolioConstants.THIRTY_CHARS_DESC));
        }
        
        //Artistic length
        if(artistic != null && artistic.trim().length() > PortfolioConstants.THIRTY_CHARS) {
            artistic = artistic.trim().substring(0, PortfolioConstants.THIRTY_CHARS);
            errors.add("artistic", new ActionMessage("error.lengthTooLong", "Artistic score", PortfolioConstants.THIRTY_CHARS_DESC));
        }
        
        //Social length
        if(social != null && social.trim().length() > PortfolioConstants.THIRTY_CHARS) {
            social = social.trim().substring(0, PortfolioConstants.THIRTY_CHARS);
            errors.add("social", new ActionMessage("error.lengthTooLong", "Social score", PortfolioConstants.THIRTY_CHARS_DESC));
        }
        
        //Enterprising length
        if(enterprising != null && enterprising.trim().length() > PortfolioConstants.THIRTY_CHARS) {
            enterprising = enterprising.trim().substring(0, PortfolioConstants.THIRTY_CHARS);
            errors.add("enterprising", new ActionMessage("error.lengthTooLong", "Enterprising score", PortfolioConstants.THIRTY_CHARS_DESC));
        }
        
        //Conventional length
        if(conventional != null && conventional.trim().length() > PortfolioConstants.THIRTY_CHARS) {
            conventional = conventional.trim().substring(0, PortfolioConstants.THIRTY_CHARS);
            errors.add("conventional", new ActionMessage("error.lengthTooLong", "Conventional score", PortfolioConstants.THIRTY_CHARS_DESC));
        }
        
        //Summary code length
        if(summaryCode != null && summaryCode.trim().length() > PortfolioConstants.THIRTY_CHARS) {
            summaryCode = summaryCode.trim().substring(0, PortfolioConstants.THIRTY_CHARS);
            errors.add("summaryCode", new ActionMessage("error.lengthTooLong", "Summary code", PortfolioConstants.THIRTY_CHARS_DESC));
        }

        //Occupation
        if ((occuInfo != null) && (occuInfo.length() > PortfolioConstants.EIGHT_HUNDRED_WORDS)) {
            occuInfo = occuInfo.substring(0, PortfolioConstants.EIGHT_HUNDRED_WORDS);
            errors.add("occuInfo", new ActionMessage("error.lengthTooLong", "Occupational information", PortfolioConstants.EIGHT_HUNDRED_WORDS_DESC));
        }

        //Reaction
        if ((reaction != null) && (reaction.length() > PortfolioConstants.EIGHT_HUNDRED_WORDS)) {
            reaction = reaction.substring(0, PortfolioConstants.EIGHT_HUNDRED_WORDS);
            errors.add("description", new ActionMessage("error.lengthTooLong", "Interpretation / Reaction", PortfolioConstants.EIGHT_HUNDRED_WORDS_DESC));
        }

        return errors;
    }

    protected String dateTakenMonth = PortfolioTagConstants.MONTH_DEFAULT;
    protected String dateTakenYear = PortfolioTagConstants.YEAR_DEFAULT;
    protected java.lang.String realistic = null;
    protected java.lang.String investigative = null;
    protected java.lang.String artistic = null;
    protected java.lang.String social = null;
    protected java.lang.String enterprising = null;
    protected java.lang.String conventional = null;
    protected java.lang.String summaryCode = null;
    protected java.lang.String occuInfo = null;
    protected java.lang.String reaction = null;
}
