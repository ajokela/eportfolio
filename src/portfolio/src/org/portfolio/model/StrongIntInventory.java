/* $Name:  $ */
/* $Id: StrongIntInventory.java,v 1.13 2010/10/27 19:24:57 ajokela Exp $ */
/*
 * $Header: /opt/UMN-src/portfolio/src/org/portfolio/model/StrongIntInventory.java,v 1.13 2010/10/27 19:24:57 ajokela Exp $
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
 * This class encapsulates StrongIntInventory element object.
 *
 * @author	John Bush
 * @since	0.0
 * @version	$Revision: 1.13 $
 */
public class StrongIntInventory extends ElementBase {

    private static final long serialVersionUID = 3409955998072544921L;
	
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

    public java.lang.String getInterest1() {
        return (interest1 != null ? interest1 : EMPTY_STRING);
    }

    public void setInterest1(java.lang.String interest1) {
        this.interest1 = interest1;
    }

    public java.lang.String getInterest2() {
        return (interest2 != null ? interest2 : EMPTY_STRING);
    }

    public void setInterest2(java.lang.String interest2) {
        this.interest2 = interest2;
    }

    public java.lang.String getInterest3() {
        return (interest3 != null ? interest3 : EMPTY_STRING);
    }

    public void setInterest3(java.lang.String interest3) {
        this.interest3 = interest3;
    }

    public java.lang.String getInterest4() {
        return (interest4 != null ? interest4 : EMPTY_STRING);
    }

    public void setInterest4(java.lang.String interest4) {
        this.interest4 = interest4;
    }

    public java.lang.String getInterest5() {
        return (interest5 != null ? interest5 : EMPTY_STRING);
    }

    public void setInterest5(java.lang.String interest5) {
        this.interest5 = interest5;
    }

    public java.lang.String getOccupation1() {
        return (occupation1 != null ? occupation1 : EMPTY_STRING);
    }

    public void setOccupation1(java.lang.String occupation1) {
        this.occupation1 = occupation1;
    }

    public java.lang.String getOccupation2() {
        return (occupation2 != null ? occupation2 : EMPTY_STRING);
    }

    public void setOccupation2(java.lang.String occupation2) {
        this.occupation2 = occupation2;
    }

    public java.lang.String getOccupation3() {
        return (occupation3 != null ? occupation3 : EMPTY_STRING);
    }

    public void setOccupation3(java.lang.String occupation3) {
        this.occupation3 =  occupation3;
    }

    public java.lang.String getOccupation4() {
        return (occupation4 != null ? occupation4 : EMPTY_STRING);
    }

    public void setOccupation4(java.lang.String occupation4) {
        this.occupation4 = occupation4;
    }

    public java.lang.String getOccupation5() {
        return (occupation5 != null ? occupation5 : EMPTY_STRING);
    }

    public void setOccupation5(java.lang.String occupation5) {
        this.occupation5 = occupation5;
    }

    public java.lang.String getOccupation6() {
        return (occupation6 != null ? occupation6 : EMPTY_STRING);
    }

    public void setOccupation6(java.lang.String occupation6) {
        this.occupation6 = occupation6;
    }

    public java.lang.String getOccupation7() {
        return (occupation7 != null ? occupation7 : EMPTY_STRING);
    }

    public void setOccupation7(java.lang.String occupation7) {
        this.occupation7 = occupation7;
    }

    public java.lang.String getOccupation8() {
        return (occupation8 != null ? occupation8 : EMPTY_STRING);
    }

    public void setOccupation8(java.lang.String occupation8) {
        this.occupation8 = occupation8;
    }

    public java.lang.String getOccupation9() {
        return (occupation9 != null ? occupation9 : EMPTY_STRING);
    }

    public void setOccupation9(java.lang.String occupation9) {
        this.occupation9 = occupation9;
    }

    public java.lang.String getOccupation10() {
        return (occupation10 != null ? occupation10 : EMPTY_STRING);
    }

    public void setOccupation10(java.lang.String occupation10) {
        this.occupation10 = occupation10;
    }

    public java.lang.String getInterpretation() {
        return (interpretation != null ? interpretation : EMPTY_STRING);
    }

    public void setInterpretation(java.lang.String interpretation) {
        this.interpretation = interpretation;
    }

    public java.util.Date getInventoryDate() {
        Date dateTaken = null;
        if ( ( !getInventoryDateMonth().equals( PortfolioTagConstants.MONTH_DEFAULT ) ) &&
                ( !getInventoryDateYear().equals( PortfolioTagConstants.YEAR_DEFAULT ) ) ) {
            try {
                int month = Integer.parseInt( getInventoryDateMonth() );
                int year = Integer.parseInt( getInventoryDateYear() );

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

    public void setInventoryDate( java.util.Date inventoryDate ) {
        if ( inventoryDate != null ) {
            GregorianCalendar cal = new GregorianCalendar();
            cal.setTime( inventoryDate );

            setInventoryDateMonth( String.valueOf( cal.get( Calendar.MONTH ) ) );
            setInventoryDateYear( String.valueOf( cal.get( Calendar.YEAR ) ) );
        }
    }

    public String getInventoryDateMonth() {
        return ( inventoryDateMonth != null ? inventoryDateMonth : PortfolioTagConstants.MONTH_DEFAULT );
    }

    public void setInventoryDateMonth(String dateTakenMonth) {
        this.inventoryDateMonth = ( dateTakenMonth != null ? dateTakenMonth : PortfolioTagConstants.MONTH_DEFAULT );
    }

    public String getInventoryDateYear() {
        return ( inventoryDateYear != null ? inventoryDateYear : PortfolioTagConstants.YEAR_DEFAULT );
    }

    public void setInventoryDateYear(String inventoryDateYear) {
        this.inventoryDateYear = ( inventoryDateYear != null ? inventoryDateYear : PortfolioTagConstants.YEAR_DEFAULT );
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

        if ( DateValidation.checkDate( getInventoryDateMonth() , getInventoryDateYear()) == DateValidation.FAILURE ) {
            errors.add( "inventoryDate", new ActionMessage( "error.date.invalid", "Date taken" ) );
        }

        //Realistic length
        if(realistic != null && realistic.trim().length() > PortfolioConstants.TWO_CHARS) {
            realistic = realistic.trim().substring(0, PortfolioConstants.TWO_CHARS);
            errors.add("realistic", new ActionMessage("error.lengthTooLong", "Realistic rating", PortfolioConstants.TWO_CHARS_DESC));
        }

        //Investigative length
        if(investigative != null && investigative.trim().length() > PortfolioConstants.TWO_CHARS) {
            investigative = investigative.trim().substring(0, PortfolioConstants.TWO_CHARS);
            errors.add("investigative", new ActionMessage("error.lengthTooLong", "Investigative rating", PortfolioConstants.TWO_CHARS_DESC));
        }

        //Artistic length
        if(artistic != null && artistic.trim().length() > PortfolioConstants.TWO_CHARS) {
            artistic = artistic.trim().substring(0, PortfolioConstants.TWO_CHARS);
            errors.add("artistic", new ActionMessage("error.lengthTooLong", "Artistic rating", PortfolioConstants.TWO_CHARS_DESC));
        }

        //Social length
        if(social != null && social.trim().length() > PortfolioConstants.TWO_CHARS) {
            social = social.trim().substring(0, PortfolioConstants.TWO_CHARS);
            errors.add("social", new ActionMessage("error.lengthTooLong", "Social rating", PortfolioConstants.TWO_CHARS_DESC));
        }

        //Enterprising length
        if(enterprising != null && enterprising.trim().length() > PortfolioConstants.TWO_CHARS) {
            enterprising = enterprising.trim().substring(0, PortfolioConstants.TWO_CHARS);
            errors.add("enterprising", new ActionMessage("error.lengthTooLong", "Enterprising rating", PortfolioConstants.TWO_CHARS_DESC));
        }

        //Conventional length
        if(conventional != null && conventional.trim().length() > PortfolioConstants.TWO_CHARS) {
            conventional = conventional.trim().substring(0, PortfolioConstants.TWO_CHARS);
            errors.add("conventional", new ActionMessage("error.lengthTooLong", "Conventional rating", PortfolioConstants.TWO_CHARS_DESC));
        }

        //Interest 1 length
        if(interest1 != null && interest1.trim().length() > PortfolioConstants.FIFTY_CHARS) {
            interest1 = interest1.trim().substring(0, PortfolioConstants.FIFTY_CHARS);
            errors.add("interest1", new ActionMessage("error.lengthTooLong", "Interest 1", PortfolioConstants.FIFTY_CHARS_DESC));
        }

        //Interest 2 length
        if(interest2 != null && interest2.trim().length() > PortfolioConstants.FIFTY_CHARS) {
            interest2 = interest2.trim().substring(0, PortfolioConstants.FIFTY_CHARS);
            errors.add("interest2", new ActionMessage("error.lengthTooLong", "Interest 2", PortfolioConstants.FIFTY_CHARS_DESC));
        }

        //Interest 3 length
        if(interest3 != null && interest3.trim().length() > PortfolioConstants.FIFTY_CHARS) {
            interest3 = interest3.trim().substring(0, PortfolioConstants.FIFTY_CHARS);
            errors.add("interest3", new ActionMessage("error.lengthTooLong", "Interest 3", PortfolioConstants.FIFTY_CHARS_DESC));
        }

        //Interest 4 length
        if(interest4 != null && interest4.trim().length() > PortfolioConstants.FIFTY_CHARS) {
            interest4 = interest4.trim().substring(0, PortfolioConstants.FIFTY_CHARS);
            errors.add("interest4", new ActionMessage("error.lengthTooLong", "Interest 4", PortfolioConstants.FIFTY_CHARS_DESC));
        }

        //Interest 5 length
        if(interest5 != null && interest5.trim().length() > PortfolioConstants.FIFTY_CHARS) {
            interest5 = interest5.trim().substring(0, PortfolioConstants.FIFTY_CHARS);
            errors.add("interest5", new ActionMessage("error.lengthTooLong", "Interest 5", PortfolioConstants.FIFTY_CHARS_DESC));
        }

        //Occupation 1 length
        if(occupation1 != null && occupation1.trim().length() > PortfolioConstants.FIFTY_CHARS) {
            occupation1 = occupation1.trim().substring(0, PortfolioConstants.FIFTY_CHARS);
            errors.add("occupation1", new ActionMessage("error.lengthTooLong", "Occupation 1", PortfolioConstants.FIFTY_CHARS_DESC));
        }

        //Occupation 2 length
        if(occupation2 != null && occupation2.trim().length() > PortfolioConstants.FIFTY_CHARS) {
            occupation2 = occupation2.trim().substring(0, PortfolioConstants.FIFTY_CHARS);
            errors.add("occupation2", new ActionMessage("error.lengthTooLong", "Occupation 2", PortfolioConstants.FIFTY_CHARS_DESC));
        }

        //Occupation 3 length
        if(occupation3 != null && occupation3.trim().length() > PortfolioConstants.FIFTY_CHARS) {
            occupation3 = occupation3.trim().substring(0, PortfolioConstants.FIFTY_CHARS);
            errors.add("occupation3", new ActionMessage("error.lengthTooLong", "Occupation 3", PortfolioConstants.FIFTY_CHARS_DESC));
        }

        //Occupation 4 length
        if(occupation4 != null && occupation4.trim().length() > PortfolioConstants.FIFTY_CHARS) {
            occupation4 = occupation4.trim().substring(0, PortfolioConstants.FIFTY_CHARS);
            errors.add("occupation4", new ActionMessage("error.lengthTooLong", "Occupation 4", PortfolioConstants.FIFTY_CHARS_DESC));
        }

        //Occupation 5 length
        if(occupation5 != null && occupation5.trim().length() > PortfolioConstants.FIFTY_CHARS) {
            occupation5 = occupation5.trim().substring(0, PortfolioConstants.FIFTY_CHARS);
            errors.add("occupation5", new ActionMessage("error.lengthTooLong", "Occupation 5", PortfolioConstants.FIFTY_CHARS_DESC));
        }

        //Occupation 6 length
        if(occupation6 != null && occupation6.trim().length() > PortfolioConstants.FIFTY_CHARS) {
            occupation6 = occupation6.trim().substring(0, PortfolioConstants.FIFTY_CHARS);
            errors.add("occupation6", new ActionMessage("error.lengthTooLong", "Occupation 6", PortfolioConstants.FIFTY_CHARS_DESC));
        }

        //Occupation 7 length
        if(occupation7 != null && occupation7.trim().length() > PortfolioConstants.FIFTY_CHARS) {
            occupation7 = occupation7.trim().substring(0, PortfolioConstants.FIFTY_CHARS);
            errors.add("occupation7", new ActionMessage("error.lengthTooLong", "Occupation 7", PortfolioConstants.FIFTY_CHARS_DESC));
        }

        //Occupation 8 length
        if(occupation8 != null && occupation8.trim().length() > PortfolioConstants.FIFTY_CHARS) {
            occupation8 = occupation8.trim().substring(0, PortfolioConstants.FIFTY_CHARS);
            errors.add("occupation8", new ActionMessage("error.lengthTooLong", "Occupation 8", PortfolioConstants.FIFTY_CHARS_DESC));
        }

        //Occupation 9 length
        if(occupation9 != null && occupation9.trim().length() > PortfolioConstants.FIFTY_CHARS) {
            occupation9 = occupation9.trim().substring(0, PortfolioConstants.FIFTY_CHARS);
            errors.add("occupation9", new ActionMessage("error.lengthTooLong", "Occupation 9", PortfolioConstants.FIFTY_CHARS_DESC));
        }

        //Occupation 10 length
        if(occupation10 != null && occupation10.trim().length() > PortfolioConstants.FIFTY_CHARS) {
            occupation10 = occupation10.trim().substring(0, PortfolioConstants.FIFTY_CHARS);
            errors.add("occupation10", new ActionMessage("error.lengthTooLong", "Occupation 10", PortfolioConstants.FIFTY_CHARS_DESC));
        }

        if ((interpretation != null) && (interpretation.length() > PortfolioConstants.EIGHT_HUNDRED_WORDS)) {
            interpretation = interpretation.substring(0, PortfolioConstants.EIGHT_HUNDRED_WORDS);
            errors.add("interpretation", new ActionMessage("error.lengthTooLong", "Interpretation / Reaction", PortfolioConstants.EIGHT_HUNDRED_WORDS_DESC));
        }

        return errors;
    }

    protected String inventoryDateMonth = PortfolioTagConstants.MONTH_DEFAULT;
    protected String inventoryDateYear = PortfolioTagConstants.YEAR_DEFAULT;
    protected java.lang.String realistic = null;
    protected java.lang.String investigative = null;
    protected java.lang.String artistic = null;
    protected java.lang.String social = null;
    protected java.lang.String enterprising = null;
    protected java.lang.String conventional = null;
    protected java.lang.String interest1 = null;
    protected java.lang.String interest2 = null;
    protected java.lang.String interest3 = null;
    protected java.lang.String interest4 = null;
    protected java.lang.String interest5 = null;
    protected java.lang.String occupation1 = null;
    protected java.lang.String occupation2 = null;
    protected java.lang.String occupation3 = null;
    protected java.lang.String occupation4 = null;
    protected java.lang.String occupation5 = null;
    protected java.lang.String occupation6 = null;
    protected java.lang.String occupation7 = null;
    protected java.lang.String occupation8 = null;
    protected java.lang.String occupation9 = null;
    protected java.lang.String occupation10 = null;
    protected java.lang.String interpretation = null;
}
