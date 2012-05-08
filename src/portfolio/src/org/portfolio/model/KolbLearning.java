/* $Name:  $ */
/* $Id: KolbLearning.java,v 1.13 2010/10/27 19:24:57 ajokela Exp $ */
/*
 * $Header: /opt/UMN-src/portfolio/src/org/portfolio/model/KolbLearning.java,v 1.13 2010/10/27 19:24:57 ajokela Exp $
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
 * This class encapsulates KolbLearning element object.
 *
 * @author	John Bush
 * @author      Jack Brown, University of Minnesota
 * @since	0.0
 * @version	$Revision: 1.13 $
 */
public class KolbLearning extends ElementBase {

    private static final long serialVersionUID = -5735421592851237311L;
	
    public java.lang.String getEntryName() {
        return (entryName != null ? entryName : EMPTY_STRING);
    }

    public void setEntryName(java.lang.String entryName) {
        this.entryName = entryName;
    }

    public java.lang.String getConcreteExperience() {
        return (concreteExperience != null ? concreteExperience : EMPTY_STRING);
    }

    public void setConcreteExperience(java.lang.String concreteExperience) {
        this.concreteExperience = concreteExperience;
    }

    public java.lang.String getReflectiveObservation() {
        return (reflectiveObservation != null ? reflectiveObservation : EMPTY_STRING);
    }

    public void setReflectiveObservation(java.lang.String reflectiveObservation) {
        this.reflectiveObservation =  reflectiveObservation;
    }

    public java.lang.String getAbstractConceptualization() {
        return (abstractConceptualization != null ? abstractConceptualization : EMPTY_STRING);
    }

    public void setAbstractConceptualization(java.lang.String abstractConceptualization) {
        this.abstractConceptualization = abstractConceptualization;
    }

    public java.lang.String getActiveExperimentation() {
        return (activeExperimentation != null ? activeExperimentation : EMPTY_STRING);
    }

    public void setActiveExperimentation(java.lang.String activeExperimentation) {
        this.activeExperimentation = activeExperimentation;
    }

    public java.lang.String getLearningStyleType() {
        return (learningStyleType != null ? learningStyleType : EMPTY_STRING);
    }

    public void setLearningStyleType(java.lang.String learningStyleType) {
        this.learningStyleType = learningStyleType;
    }

    public java.lang.String getInterpretation() {
        return (interpretation != null ? interpretation : EMPTY_STRING);
    }

    public void setInterpretation(java.lang.String interpretation) {
        this.interpretation = interpretation;
    }

    public Date getDateTaken() {
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


    //--------------------------------------------------------------------------
    // Methods dictated by implemented interfaces

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
        if ( DateValidation.checkDate( getDateTakenMonth(),  getDateTakenYear() ) == DateValidation.FAILURE ) {
            errors.add( "dateTaken", new ActionMessage( "error.date.invalid", "Date taken" ) );
        }

        //Concrete experience length
        if(concreteExperience != null && concreteExperience.trim().length() > PortfolioConstants.TEN_CHARS) {
            concreteExperience = concreteExperience.trim().substring(0, PortfolioConstants.TEN_CHARS);
            errors.add("concreteExperience", new ActionMessage("error.lengthTooLong", "Concrete Experience (CE)", PortfolioConstants.TEN_CHARS_DESC ));
        }

        //Reflective observation length
        if(reflectiveObservation != null && reflectiveObservation.trim().length() > PortfolioConstants.TEN_CHARS) {
            reflectiveObservation = reflectiveObservation.trim().substring(0, PortfolioConstants.TEN_CHARS);
            errors.add("reflectiveObservation", new ActionMessage( "error.lengthTooLong", "Reflective Observation (RO)", PortfolioConstants.TEN_CHARS_DESC ));
        }

        //Abstract Conceptualization length
        if(abstractConceptualization != null && abstractConceptualization.trim().length() > PortfolioConstants.TEN_CHARS) {
            abstractConceptualization = abstractConceptualization.trim().substring(0, PortfolioConstants.TEN_CHARS);
            errors.add("abstractConceptualization", new ActionMessage( "error.lengthTooLong", "Abstract Conceptualization (AC)", PortfolioConstants.TEN_CHARS_DESC ));
        }

        //Active Experimentation length
        if(activeExperimentation != null && activeExperimentation.trim().length() > PortfolioConstants.TEN_CHARS) {
            activeExperimentation = activeExperimentation.trim().substring(0, PortfolioConstants.TEN_CHARS);
            errors.add("activeExperimentation", new ActionMessage( "error.lengthTooLong", "Active Experimementation (AE)", PortfolioConstants.TEN_CHARS_DESC ));
        }
        
        if ( ( interpretation != null ) && (interpretation.length() > PortfolioConstants.EIGHT_HUNDRED_WORDS ) ) {
            interpretation = interpretation.substring(0, PortfolioConstants.EIGHT_HUNDRED_WORDS);
            errors.add("interpretation", new ActionMessage( "error.lengthTooLong", "Interpretation / Reaction", PortfolioConstants.EIGHT_HUNDRED_WORDS_DESC ));
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
        buff.append("concreteExperience=").append(getConcreteExperience()).append(",");
        buff.append("reflectiveObservation=").append(getReflectiveObservation()).append(",");
        buff.append("abstractConceptualization=").append(getAbstractConceptualization()).append(",");
        buff.append("activeExperimentation=").append(getActiveExperimentation()).append(",");
        buff.append("learningStyleType=").append(getLearningStyleType()).append(",");
        buff.append("interpretation=").append(getInterpretation()).append(",");
        buff.append("is new? =>" + isNew());
        return buff.toString();
    }

    private String dateTakenMonth = PortfolioTagConstants.MONTH_DEFAULT;
    private String dateTakenYear = PortfolioTagConstants.YEAR_DEFAULT;

    protected java.lang.String concreteExperience = null;
    protected java.lang.String reflectiveObservation = null;
    protected java.lang.String abstractConceptualization = null;
    protected java.lang.String activeExperimentation = null;
    protected java.lang.String learningStyleType = null;
    protected java.lang.String interpretation = null;
    protected boolean isRemote = false;
}
