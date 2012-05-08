/* $Name:  $ */
/* $Id: AssessScores.java,v 1.7 2010/10/27 19:24:57 ajokela Exp $ */
/*
 * $Header: /opt/UMN-src/portfolio/src/org/portfolio/model/AssessScores.java,v 1.7 2010/10/27 19:24:57 ajokela Exp $
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
import org.portfolio.util.DateForXSL;
import org.portfolio.util.DateValidation;
import org.portfolio.util.PortfolioConstants;

/**
 * This class encapsulates AssessScores element object.
 *
 * @author	John Bush
 * @author      Jack Brown, University of Minnesota
 * @since	0.0
 * @version	$Revision: 1.7 $
 */
public class AssessScores extends ElementBase {

    private static final long serialVersionUID = -7312257770157409254L;
	
    public java.lang.String getEntryName() {
        return (entryName != null ? entryName : EMPTY_STRING);
    }

    public void setEntryName(java.lang.String entryName) {
        this.entryName =entryName;
    }

    public java.lang.String getScore1() {
        return (score1 != null ? score1 : EMPTY_STRING);
    }

    public void setScore1(java.lang.String score1) {
        this.score1 =  score1;
    }

    public java.lang.String getSection1() {
        return (section1 != null ? section1 : EMPTY_STRING);
    }

    public void setSection1(java.lang.String section1) {
        this.section1 = section1;
    }

    public java.lang.String getPercent1() {
        return (percent1 != null ? percent1 : EMPTY_STRING);
    }

    public void setPercent1(java.lang.String percent1) {
        this.percent1 = percent1;
    }

    public java.lang.String getScore2() {
        return (score2 != null ? score2 : EMPTY_STRING);
    }

    public void setScore2(java.lang.String score2) {
        this.score2 =  score2;
    }

    public java.lang.String getSection2() {
        return (section2 != null ? section2 : EMPTY_STRING);
    }

    public void setSection2(java.lang.String section2) {
        this.section2 =  section2;
    }

    public java.lang.String getPercent2() {
        return (percent2 != null ? percent2 : EMPTY_STRING);
    }

    public void setPercent2(java.lang.String percent2) {
        this.percent2 =  percent2;
    }

    public java.lang.String getScore3() {
        return (score3 != null ? score3 : EMPTY_STRING);
    }

    public void setScore3(java.lang.String score3) {
        this.score3 =  score3;
    }

    public java.lang.String getSection3() {
        return (section3 != null ? section3 : EMPTY_STRING);
    }

    public void setSection3(java.lang.String section3) {
        this.section3 =  section3;
    }

    public java.lang.String getPercent3() {
        return (percent3 != null ? percent3 : EMPTY_STRING);
    }

    public void setPercent3(java.lang.String percent3) {
        this.percent3 =  percent3;
    }

    public java.lang.String getScore4() {
        return (score4 != null ? score4 : EMPTY_STRING);
    }

    public void setScore4(java.lang.String score4) {
        this.score4 =  score4;
    }

    public java.lang.String getSection4() {
        return (section4 != null ? section4 : EMPTY_STRING);
    }

    public void setSection4(java.lang.String section4) {
        this.section4 = section4;
    }

    public java.lang.String getPercent4() {
        return (percent4 != null ? percent4 : EMPTY_STRING);
    }

    public void setPercent4(java.lang.String percent4) {
        this.percent4 = percent4;
    }

    public java.lang.String getScore5() {
        return (score5 != null ? score5 : EMPTY_STRING);
    }

    public void setScore5(java.lang.String score5) {
        this.score5 =  score5;
    }

    public java.lang.String getSection5() {
        return (section5 != null ? section5 : EMPTY_STRING);
    }

    public void setSection5(java.lang.String section5) {
        this.section5 =  section5;
    }

    public java.lang.String getPercent5() {
        return (percent5 != null ? percent5 : EMPTY_STRING);
    }

    public void setPercent5(java.lang.String percent5) {
        this.percent5 = percent5;
    }

    public java.lang.String getScore6() {
        return (score6 != null ? score6 : EMPTY_STRING);
    }

    public void setScore6(java.lang.String score6) {
        this.score6 = score6;
    }

    public java.lang.String getSection6() {
        return (section6 != null ? section6 : EMPTY_STRING);
    }

    public void setSection6(java.lang.String section6) {
        this.section6 = section6;
    }

    public java.lang.String getPercent6() {
        return (percent6 != null ? percent6 : EMPTY_STRING);
    }

    public void setPercent6(java.lang.String percent6) {
        this.percent6 = percent6;
    }

    public java.lang.String getComposite() {
        return (composite != null ? composite : EMPTY_STRING);
    }

    public void setComposite(java.lang.String composite) {
        this.composite = composite;
    }

    public java.lang.String getTotal() {
        return (total != null ? total : EMPTY_STRING);
    }

    public void setTotal(java.lang.String total) {
        this.total = total;
    }

    public java.lang.String getInterpretation() {
        return (interpretation != null ? interpretation : EMPTY_STRING);
    }

    public void setInterpretation(java.lang.String interpretation) {
        this.interpretation = interpretation;
    }


    public java.util.Date getDateTaken() {
        Date dateTaken = null;
        if ( ( !getDateTakenMonth().equals( PortfolioTagConstants.MONTH_DEFAULT ) ) &&
                ( !getDateTakenYear().equals( PortfolioTagConstants.YEAR_DEFAULT ) ) ) {
            try {
                // logService.debug( "getting month and year" );
                int month = Integer.parseInt( getDateTakenMonth() );
                int year = Integer.parseInt( getDateTakenYear() );

                Calendar cal = new GregorianCalendar( year, month, 1 );
                cal.setLenient(false);
                dateTaken = new DateForXSL(cal.getTime().getTime(),true);
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



    //--------------------------------------------------------------------------
    // Methods dictated by implemented interfaces

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        if (request.getParameter("viewfile.x") != null) return errors;

        if ((entryName == null) || (entryName.trim().length() == 0)) {
            errors.add("entryName", new ActionMessage("error.required.missing", "Exam Name"));
        } else if(entryName.trim().length() > PortfolioConstants.SIXTY_CHARS) {
            entryName = entryName.trim().substring(0, PortfolioConstants.SIXTY_CHARS);
            errors.add("entryName", new ActionMessage("error.lengthTooLong", "Exam Name" , PortfolioConstants.SIXTY_CHARS_DESC));
        }

        int dateValid = DateValidation.checkDate( getDateTakenMonth(), getDateTakenYear(), true );
        if ( dateValid == DateValidation.MISSING_REQUIRED ) {
            errors.add( "dateTaken", new ActionMessage( "error.required.missing", "Date Taken" ) );
        } else if ( dateValid == DateValidation.FAILURE ) {
            //Defect # 2477 - 11/24/2004 - code change to display "error.date.invalid" error.
            errors.add( "dateTaken", new ActionMessage( "error.date.invalid", "Date Taken" ) );
        }
        //Section 1 length
        if(section1 != null && section1.trim().length() > PortfolioConstants.THIRTY_FIVE_CHARS) {
            section1 = section1.trim().substring(0, PortfolioConstants.THIRTY_FIVE_CHARS);
            errors.add("section1", new ActionMessage("error.lengthTooLong", "Section 1", PortfolioConstants.THIRTY_FIVE_CHARS_DESC));
        }
        //Score 1 length
        if(score1 != null && score1.trim().length() > PortfolioConstants.TWENTY_FIVE_CHARS) {
            score1 = score1.trim().substring(0, PortfolioConstants.TWENTY_FIVE_CHARS);
            errors.add("score1", new ActionMessage("error.lengthTooLong", "Score 1", PortfolioConstants.TWENTY_FIVE_CHARS_DESC));
        }
        //Percent 1 length
        if ( percent1 != null && percent1.trim().length() > PortfolioConstants.TWENTY_FIVE_CHARS ) {
            percent1 = percent1.trim().substring( 0, PortfolioConstants.TWENTY_FIVE_CHARS );
            errors.add( "percent1", new ActionMessage( "error.lengthTooLong", "Percent 1", PortfolioConstants.TWENTY_FIVE_CHARS_DESC ) );
        }
        //Section 2 length
        if ( section2 != null && section2.trim().length() > PortfolioConstants.THIRTY_FIVE_CHARS ) {
            section2 = section2.trim().substring( 0, PortfolioConstants.THIRTY_FIVE_CHARS );
            errors.add( "section1", new ActionMessage( "error.lengthTooLong", "Section 2", PortfolioConstants.THIRTY_FIVE_CHARS_DESC ) );
        }

        //Score 2 length
        if ( score2 != null && score2.trim().length() > PortfolioConstants.TWENTY_FIVE_CHARS ) {
            score2 = score2.trim().substring( 0, PortfolioConstants.TWENTY_FIVE_CHARS );
            errors.add( "score2", new ActionMessage( "error.lengthTooLong", "Score 2", PortfolioConstants.TWENTY_FIVE_CHARS_DESC ) );
        }
        //Percent 2 length
        if ( percent2 != null && percent2.trim().length() > PortfolioConstants.TWENTY_FIVE_CHARS ) {
            percent2 = percent2.trim().substring( 0, PortfolioConstants.TWENTY_FIVE_CHARS );
            errors.add( "percent2", new ActionMessage( "error.lengthTooLong", "Percent 2", PortfolioConstants.TWENTY_FIVE_CHARS_DESC ) );
        }
        //Section 3 length
        if ( section3 != null && section3.trim().length() > PortfolioConstants.THIRTY_FIVE_CHARS ) {
            section3 = section3.trim().substring( 0, PortfolioConstants.THIRTY_FIVE_CHARS );
            errors.add( "section3", new ActionMessage( "error.lengthTooLong", "Section 3", PortfolioConstants.THIRTY_FIVE_CHARS_DESC ) );
        }
        //Score 3 length
        if ( score3 != null && score3.trim().length() > PortfolioConstants.TWENTY_FIVE_CHARS ) {
            score3 = score3.trim().substring( 0, PortfolioConstants.TWENTY_FIVE_CHARS );
            errors.add( "score3", new ActionMessage( "error.lengthTooLong", "Score 3", PortfolioConstants.TWENTY_FIVE_CHARS_DESC ) );
        }
        //Percent 3 length
        if ( percent3 != null && percent3.trim().length() > PortfolioConstants.TWENTY_FIVE_CHARS ) {
            percent3 = percent3.trim().substring( 0, PortfolioConstants.TWENTY_FIVE_CHARS );
            errors.add( "percent3", new ActionMessage( "error.lengthTooLong", "Percent 3", PortfolioConstants.TWENTY_FIVE_CHARS_DESC ) );
        }
        //Section 4 length
        if ( section4 != null && section4.trim().length() > PortfolioConstants.THIRTY_FIVE_CHARS ) {
            section4 = section4.trim().substring( 0, PortfolioConstants.THIRTY_FIVE_CHARS );
            errors.add( "section4", new ActionMessage( "error.lengthTooLong", "Section 4", PortfolioConstants.THIRTY_FIVE_CHARS_DESC ) );
        }
        //Score 4 length
        if ( score4 != null && score4.trim().length() > PortfolioConstants.TWENTY_FIVE_CHARS ) {
            score4 = score4.trim().substring( 0, PortfolioConstants.TWENTY_FIVE_CHARS );
            errors.add( "score4", new ActionMessage( "error.lengthTooLong", "Score 4", PortfolioConstants.TWENTY_FIVE_CHARS_DESC ) );
        }
        //Percent 4 length
        if ( percent4 != null && percent4.trim().length() > PortfolioConstants.TWENTY_FIVE_CHARS ) {
            percent4 = percent4.trim().substring( 0, PortfolioConstants.TWENTY_FIVE_CHARS );
            errors.add( "percent4", new ActionMessage( "error.lengthTooLong", "Percent 4", PortfolioConstants.TWENTY_FIVE_CHARS_DESC ) );
        }
        //Section 5 length
        if ( section5 != null && section5.trim().length() > PortfolioConstants.THIRTY_FIVE_CHARS ) {
            section5 = section5.trim().substring( 0, PortfolioConstants.THIRTY_FIVE_CHARS );
            errors.add( "section5", new ActionMessage( "error.lengthTooLong", "Section 5", PortfolioConstants.THIRTY_FIVE_CHARS_DESC ) );
        }
        //Score 5 length
        if ( score5 != null && score5.trim().length() > PortfolioConstants.TWENTY_FIVE_CHARS ) {
            score5 = score5.trim().substring( 0, PortfolioConstants.TWENTY_FIVE_CHARS );
            errors.add( "score5", new ActionMessage( "error.lengthTooLong", "Score 5", PortfolioConstants.TWENTY_FIVE_CHARS_DESC ) );
        }
        //Percent 5 length
        if ( percent5 != null && percent5.trim().length() > PortfolioConstants.TWENTY_FIVE_CHARS ) {
            percent5 = percent5.trim().substring( 0, PortfolioConstants.TWENTY_FIVE_CHARS );
            errors.add( "percent5", new ActionMessage( "error.lengthTooLong", "Percent 5", PortfolioConstants.TWENTY_FIVE_CHARS_DESC ) );
        }
        //Section 6 length
        if ( section6 != null && section6.trim().length() > PortfolioConstants.THIRTY_FIVE_CHARS ) {
            section6 = section6.trim().substring( 0, PortfolioConstants.THIRTY_FIVE_CHARS );
            errors.add( "section6", new ActionMessage( "error.lengthTooLong", "Section 6", PortfolioConstants.THIRTY_FIVE_CHARS_DESC ) );
        }
        //Score 6 length
        if ( score6 != null && score6.trim().length() > PortfolioConstants.TWENTY_FIVE_CHARS ) {
            score6 = score6.trim().substring( 0, PortfolioConstants.TWENTY_FIVE_CHARS );
            errors.add( "score6", new ActionMessage( "error.lengthTooLong", "Score 6", PortfolioConstants.TWENTY_FIVE_CHARS_DESC ) );
        }
        //Percent 6 length
        if ( percent6 != null && percent6.trim().length() > PortfolioConstants.TWENTY_FIVE_CHARS ) {
            percent6 = percent6.trim().substring( 0, PortfolioConstants.TWENTY_FIVE_CHARS );
            errors.add( "percent6", new ActionMessage( "error.lengthTooLong", "Percent 6", PortfolioConstants.TWENTY_FIVE_CHARS_DESC ) );
        }
        //Composite length
        if(composite != null && composite.trim().length() > PortfolioConstants.TWENTY_FIVE_CHARS) {
            composite = composite.trim().substring(0, PortfolioConstants.TWENTY_FIVE_CHARS);
            errors.add("composite", new ActionMessage("error.lengthTooLong", "Composite", PortfolioConstants.TWENTY_FIVE_CHARS_DESC ));
        }
        //Total length
        if(total != null && total.trim().length() > PortfolioConstants.TWENTY_FIVE_CHARS) {
            total = total.trim().substring(0, PortfolioConstants.TWENTY_FIVE_CHARS);
            errors.add("total", new ActionMessage("error.lengthTooLong", "Total", PortfolioConstants.TWENTY_FIVE_CHARS_DESC ));
        }
        //Interpretation text area
        if ((interpretation != null) && (interpretation.length() > PortfolioConstants.EIGHT_HUNDRED_WORDS)) {
            interpretation = interpretation.substring(0, PortfolioConstants.EIGHT_HUNDRED_WORDS);
            errors.add("description", new ActionMessage("error.lengthTooLong", "Interpretation", PortfolioConstants.EIGHT_HUNDRED_WORDS_DESC));
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
        buff.append("dateCreated=").append(getDateCreated()).append(",");
        buff.append("modifiedDate=").append(getModifiedDate()).append(",");
        buff.append("entryName=").append(getEntryName()).append(",");
        buff.append("dateTaken=").append(getDateTaken()).append(",");
        buff.append("score1=").append(getScore1()).append(",");
        buff.append("section1=").append(getSection1()).append(",");
        buff.append("percent1=").append(getPercent1()).append(",");
        buff.append("score2=").append(getScore2()).append(",");
        buff.append("section2=").append(getSection2()).append(",");
        buff.append("percent2=").append(getPercent2()).append(",");
        buff.append("score3=").append(getScore3()).append(",");
        buff.append("section3=").append(getSection3()).append(",");
        buff.append("percent3=").append(getPercent3()).append(",");
        buff.append("score4=").append(getScore4()).append(",");
        buff.append("section4=").append(getSection4()).append(",");
        buff.append("percent4=").append(getPercent4()).append(",");
        buff.append("score5=").append(getScore5()).append(",");
        buff.append("section5=").append(getSection5()).append(",");
        buff.append("percent5=").append(getPercent5()).append(",");
        buff.append("score6=").append(getScore6()).append(",");
        buff.append("section6=").append(getSection6()).append(",");
        buff.append("percent6=").append(getPercent6()).append(",");
        buff.append("composite=").append(getComposite()).append(",");
        buff.append("total=").append(getTotal()).append(",");
        buff.append("interpretation=").append(getInterpretation()).append(",");
        buff.append("is new? =>" + isNew());
        return buff.toString();
    }


    protected String dateTakenMonth = PortfolioTagConstants.MONTH_DEFAULT;
    protected String dateTakenYear = PortfolioTagConstants.YEAR_DEFAULT;
    protected java.lang.String score1 = null;
    protected java.lang.String section1 = null;
    protected java.lang.String percent1 = null;
    protected java.lang.String score2 = null;
    protected java.lang.String section2 = null;
    protected java.lang.String percent2 = null;
    protected java.lang.String score3 = null;
    protected java.lang.String section3 = null;
    protected java.lang.String percent3 = null;
    protected java.lang.String score4 = null;
    protected java.lang.String section4 = null;
    protected java.lang.String percent4 = null;
    protected java.lang.String score5 = null;
    protected java.lang.String section5 = null;
    protected java.lang.String percent5 = null;
    protected java.lang.String score6 = null;
    protected java.lang.String section6 = null;
    protected java.lang.String percent6 = null;
    protected java.lang.String composite = null;
    protected java.lang.String total = null;
    protected java.lang.String interpretation = null;
    protected boolean isRemote = false;
}
