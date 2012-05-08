/* $Name:  $ */
/* $Id: SensoryModality.java,v 1.13 2010/10/27 19:24:57 ajokela Exp $ */
/*
 * $Header: /opt/UMN-src/portfolio/src/org/portfolio/model/SensoryModality.java,v 1.13 2010/10/27 19:24:57 ajokela Exp $
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
 * This class encapsulates SensoryModality element object.
 *
 * @author	John Bush
 * @since	0.0
 * @version	$Revision: 1.13 $
 */
public class SensoryModality extends ElementBase {

    private static final long serialVersionUID = -4032524748329926428L;
	
    public java.lang.String getEntryName() {
        return (entryName != null ? entryName : EMPTY_STRING);
    }

    public void setEntryName(java.lang.String entryName) {
        this.entryName = entryName;
    }

    public Date getDateTaken() {
        Date toDate = null;
        if ( ( !getDateTakenMonth().equals( PortfolioTagConstants.MONTH_DEFAULT ) ) &&
                ( !getDateTakenYear().equals( PortfolioTagConstants.YEAR_DEFAULT ) ) ) {
            try {
                int month = Integer.parseInt( getDateTakenMonth() );
                int year = Integer.parseInt( getDateTakenYear() );

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

    public void setDateTaken( Date dateTaken ) {
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


    public java.lang.String getAuditory() {
        return (auditory != null ? auditory : EMPTY_STRING);
    }

    public void setAuditory(java.lang.String auditory) {
        this.auditory =  auditory;
    }

    public java.lang.String getVisual() {
        return (visual != null ? visual : EMPTY_STRING);
    }

    public void setVisual(java.lang.String visual) {
        this.visual = visual;
    }

    public java.lang.String getKinesthetic() {
        return (kinesthetic != null ? kinesthetic : EMPTY_STRING);
    }

    public void setKinesthetic(java.lang.String kinesthetic) {
        this.kinesthetic =  kinesthetic;
    }

    public java.lang.String getInterpretation() {
        return (interpretation != null ? interpretation : EMPTY_STRING);
    }

    public void setInterpretation(java.lang.String interpretation) {
        this.interpretation = interpretation;
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


        // date taken validation
        if ( DateValidation.checkDate( getDateTakenMonth(), getDateTakenYear() ) == DateValidation.FAILURE ) {
            errors.add( "dateTaken", new ActionMessage( "error.date.invalid", "Date taken" ) );
        }

        //Auditory length
        if(auditory != null && auditory.trim().length() > PortfolioConstants.TWENTY_FIVE_CHARS) {
            auditory = auditory.trim().substring(0, PortfolioConstants.TWENTY_FIVE_CHARS);
            errors.add("auditory", new ActionMessage("error.lengthTooLong", "Auditory", PortfolioConstants.TWENTY_FIVE_CHARS_DESC));
        }
        
        //Visual length
        if(visual != null && visual.trim().length() > PortfolioConstants.TWENTY_FIVE_CHARS) {
            visual = visual.trim().substring(0, PortfolioConstants.TWENTY_FIVE_CHARS);
            errors.add("visual", new ActionMessage("error.lengthTooLong", "Visual", PortfolioConstants.TWENTY_FIVE_CHARS_DESC));
        }
        
        //Kinesthetic length
        if(kinesthetic != null && kinesthetic.trim().length() > PortfolioConstants.TWENTY_FIVE_CHARS) {
            kinesthetic = kinesthetic.trim().substring(0, PortfolioConstants.TWENTY_FIVE_CHARS);
            errors.add("kinesthetic", new ActionMessage("error.lengthTooLong", "Kinesthetic", PortfolioConstants.TWENTY_FIVE_CHARS_DESC));
        }
        
        if ((interpretation != null) && (interpretation.length() > PortfolioConstants.EIGHT_HUNDRED_WORDS)) {
            interpretation = interpretation.substring(0, PortfolioConstants.EIGHT_HUNDRED_WORDS);
            errors.add("interpretation", new ActionMessage("error.lengthTooLong", "Interpretation / Reaction", PortfolioConstants.EIGHT_HUNDRED_WORDS_DESC));
        }

        return errors;
    }

    private String dateTakenMonth = PortfolioTagConstants.MONTH_DEFAULT;
    private String dateTakenYear = PortfolioTagConstants.YEAR_DEFAULT;

    protected java.lang.String auditory = null;
    protected java.lang.String visual = null;
    protected java.lang.String kinesthetic = null;
    protected java.lang.String interpretation = null;
}
