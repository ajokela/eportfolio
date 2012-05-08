/* $Name:  $ */
/* $Id: AdditionalPhoto.java,v 1.5 2010/10/27 19:24:57 ajokela Exp $ */
/*
 * ============================================================================
 *
 * @author	Raji Srinivasan, University of Minnesota Web Development
 * @since	0.1
 * @version	$Revision: 1.5 $

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
 * This class encapsulates AdditionalPhoto element object.
 *
 * @author	John Bush
 * @author      Jack Brown, University of Minnesota
 * @since	0.0
 * @version	$Revision: 1.5 $
 */
public class AdditionalPhoto extends ElementBase {

    private static final long serialVersionUID = 2264178754089462980L;

    public java.lang.String getEntryName() {
        return (entryName != null ? entryName : EMPTY_STRING);
    }

    public void setEntryName(java.lang.String entryName) {
        this.entryName = entryName;
    }

    public java.lang.String getAdditionalInformation() {
        return (additionalInformation != null ? additionalInformation : EMPTY_STRING);
    }

    public void setAdditionalInformation(java.lang.String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }


   
   public java.util.Date getDateTaken() {	
    	Date dateTaken = null;
        if ( ( !getDateTakenMonth().equals( PortfolioTagConstants.MONTH_DEFAULT ) ) &&
                ( !getDateTakenYear().equals( PortfolioTagConstants.YEAR_DEFAULT ) ) &&
                ( !getDateTakenDay().equals( PortfolioTagConstants.DAY_DEFAULT ) ) ) {
            try {
                // logService.debug( "getting month and year" );
                int day = Integer.parseInt( getDateTakenDay() );
                int month = Integer.parseInt( getDateTakenMonth() );
                int year = Integer.parseInt( getDateTakenYear() );

                Calendar cal = new GregorianCalendar( year, month, day );
                cal.setLenient(false);
                dateTaken = new org.portfolio.util.DateForXSL(
                        cal.getTime().getTime(),true); 
                
            } catch ( NumberFormatException e ) {
                logService.debug( "Error parsing date ", e );
            } catch ( IllegalArgumentException iae ) {
                logService.debug( "Invalid date caught", iae );
            }
        }
        return dateTaken;
    }

    public void setDateTaken(java.util.Date dateTaken) {
        if ( dateTaken != null  ) {
            GregorianCalendar cal = new GregorianCalendar();
            cal.setTime( dateTaken );

            setDateTakenDay( String.valueOf( cal.get( Calendar.DAY_OF_MONTH ) ) );
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

    public String getDateTakenDay() {
        return ( dateTakenDay != null ? dateTakenDay : PortfolioTagConstants.DAY_DEFAULT );
    }

    public void setDateTakenDay(String dateTakenDay) {
        this.dateTakenDay = ( dateTakenDay != null ? dateTakenDay : PortfolioTagConstants.DAY_DEFAULT );
    }
    //--------------------------------------------------------------------------
    // Methods dictated by implemented interfaces

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        if (request.getParameter("viewfile.x") != null) return errors;

        if ((entryName == null) || (entryName.trim().length() == 0)) {
            errors.add("entryName", new ActionMessage("error.required.missing", "Type of Photo"));
        } else if(entryName.trim().length() > PortfolioConstants.FIFTY_CHARS) {
            entryName = entryName.trim().substring(0, PortfolioConstants.FIFTY_CHARS);
            errors.add("entryName", new ActionMessage("error.lengthTooLong", "Type of Photo", PortfolioConstants.FIFTY_CHARS_DESC));
        }

        if ( DateValidation.checkDate( getDateTakenMonth() , getDateTakenDay(), getDateTakenYear() ) == DateValidation.FAILURE) {
        errors.add( "dateTaken", new ActionMessage( "error.date.invalid", "Date Taken" ) );
        }

        //Word Processing length
        if(additionalInformation != null && additionalInformation.trim().length() > PortfolioConstants.FIFTY_WORDS) {
            additionalInformation = additionalInformation.trim().substring(0, PortfolioConstants.FIFTY_WORDS);
            errors.add("additionalInformation", new ActionMessage("error.lengthTooLong", "Additional Information", PortfolioConstants.FIFTY_WORDS_DESC ));
        }

        return errors;
    }

    public void setIsRemote(boolean isRemote) {
        this.isRemote = isRemote;
    }

    public boolean isRemote() {
        return isRemote;
    }

    /**
     * <p>Returns a string representation of the object. In general, the
     * <code>toString</code> method returns a string that
     * "textually represents" this object. The result should
     * be a concise but informative representation that is easy for a
     * person to read.</p>
     * <p>In the case of this object, it will show a conglomeration of the defining fields.</p>
     *
     * @return  a string representation of the object.
     */
    public String toString() {
        StringBuffer buff = new StringBuffer();
        buff.append(super.toString()).append("\n");

        // for this instance.
        buff.append("personId=").append(getPersonId()).append(",");
        buff.append("entryId=").append(getEntryId()).append(",");
        buff.append("entryName=").append(getEntryName()).append(",");
        buff.append("dateCreated=").append(getDateCreated()).append(",");
        buff.append("modifiedDate=").append(getModifiedDate()).append(",");
        buff.append("dateTaken=").append(getDateTaken()).append(",");
        buff.append("additionalInformation=").append(getAdditionalInformation()).append(",");
        buff.append("is new? =>" + isNew());
        return buff.toString();
    }


    protected String dateTakenDay = PortfolioTagConstants.DAY_DEFAULT;
    protected String dateTakenMonth = PortfolioTagConstants.MONTH_DEFAULT;
    protected String dateTakenYear = PortfolioTagConstants.YEAR_DEFAULT;

    protected java.lang.String additionalInformation = null;
    protected boolean isRemote = false;
}
