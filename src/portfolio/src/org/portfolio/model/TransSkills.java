/* $Name:  $ */
/* $Id: TransSkills.java,v 1.7 2010/10/27 19:24:57 ajokela Exp $ */
/*
 * $Header: /opt/UMN-src/portfolio/src/org/portfolio/model/TransSkills.java,v 1.7 2010/10/27 19:24:57 ajokela Exp $
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
 * This class encapsulates TransSkills element object.
 * 
 * @author John Bush
 * @author Jack Brown
 * @since 0.0
 * @version $Revision: 1.7 $
 */
public class TransSkills extends ElementBase {

    private static final long serialVersionUID = -1531929834737372309L;

    public java.lang.String getEntryName() {
        return (entryName != null ? entryName : EMPTY_STRING);
    }

    public void setEntryName(java.lang.String entryName) {
        this.entryName = entryName;
    }

    /**
     * Accessor to the surveyDate. Will parse the data on the fly from the
     * surveyMonth and surveyYear fields.
     * 
     * @return the surveyDate
     */
    public java.util.Date getSurveyDate() {
        Date surveyDate = null;

        logService.debug("getSurveyDate()");

        if ((!getSurveyDateMonth().equals(PortfolioTagConstants.MONTH_DEFAULT))
                && (!getSurveyDateYear().equals(PortfolioTagConstants.YEAR_DEFAULT))) {

            try {
                // logService.debug("getting month and year");
                int month = Integer.parseInt(getSurveyDateMonth());
                int year = Integer.parseInt(getSurveyDateYear());
                Calendar cal = new GregorianCalendar(year, month, 1);
                cal.setLenient(false);
                // need to catch the exception here.
                surveyDate = new org.portfolio.util.DateForXSL(cal.getTime().getTime(), true);
            } catch (NumberFormatException e) {
                logService.debug("Error parsing date ", e);
            } catch (IllegalArgumentException iae) {
                logService.debug("Invalid date caught", iae);
            }
        }
        logService.debug("surveyDate is " + surveyDate);
        return surveyDate;
    }

    public void setSurveyDate(java.util.Date surveyDate) {
        if (surveyDate != null) {
            GregorianCalendar cal = new GregorianCalendar();
            cal.setTime(surveyDate);

            setSurveyDateMonth(String.valueOf(cal.get(Calendar.MONTH)));
            setSurveyDateYear(String.valueOf(cal.get(Calendar.YEAR)));
        }
    }

    public String getSurveyDateMonth() {
        logService.debug("surveyMonth = " + surveyMonth);
        return (surveyMonth != null ? surveyMonth : PortfolioTagConstants.MONTH_DEFAULT);
    }

    public void setSurveyDateMonth(String surveyMonth) {
        this.surveyMonth = (surveyMonth != null ? surveyMonth : PortfolioTagConstants.MONTH_DEFAULT);
    }

    public String getSurveyDateYear() {
        return (surveyYear != null ? surveyYear : PortfolioTagConstants.YEAR_DEFAULT);
    }

    public void setSurveyDateYear(String surveyYear) {
        this.surveyYear = (surveyYear != null ? surveyYear : PortfolioTagConstants.YEAR_DEFAULT);
    }

    public java.lang.String getSelfKnowledgeTotal() {
        return (selfKnowledgeTotal != null ? selfKnowledgeTotal : EMPTY_STRING);
    }

    public void setSelfKnowledgeTotal(java.lang.String selfKnowledgeTotal) {
        this.selfKnowledgeTotal = selfKnowledgeTotal;
    }

    public java.lang.String getSelfKnowledgeAve() {
        return (selfKnowledgeAve != null ? selfKnowledgeAve : EMPTY_STRING);
    }

    public void setSelfKnowledgeAve(java.lang.String selfKnowledgeAve) {
        this.selfKnowledgeAve = selfKnowledgeAve;
    }

    public java.lang.String getEffectiveCommTotal() {
        return (effectiveCommTotal != null ? effectiveCommTotal : EMPTY_STRING);
    }

    public void setEffectiveCommTotal(java.lang.String effectiveCommTotal) {
        this.effectiveCommTotal = effectiveCommTotal;
    }

    public java.lang.String getEffectiveCommAve() {
        return (effectiveCommAve != null ? effectiveCommAve : EMPTY_STRING);
    }

    public void setEffectiveCommAve(java.lang.String effectiveCommAve) {
        this.effectiveCommAve = effectiveCommAve;
    }

    public java.lang.String getProcessControlTotal() {
        return (processControlTotal != null ? processControlTotal : EMPTY_STRING);
    }

    public void setProcessControlTotal(java.lang.String processControlTotal) {
        this.processControlTotal = processControlTotal;
    }

    public java.lang.String getProcessControlAve() {
        return (processControlAve != null ? processControlAve : EMPTY_STRING);
    }

    public void setProcessControlAve(java.lang.String processControlAve) {
        this.processControlAve = processControlAve;
    }

    public java.lang.String getVisioningTotal() {
        return (visioningTotal != null ? visioningTotal : EMPTY_STRING);
    }

    public void setVisioningTotal(java.lang.String visioningTotal) {
        this.visioningTotal = visioningTotal;
    }

    public java.lang.String getVisioningAve() {
        return (visioningAve != null ? visioningAve : EMPTY_STRING);
    }

    public void setVisioningAve(java.lang.String visioningAve) {
        this.visioningAve = visioningAve;
    }

    public java.lang.String getInterpretation() {
        return (interpretation != null ? interpretation : EMPTY_STRING);
    }

    public void setInterpretation(java.lang.String interpretation) {
        this.interpretation = interpretation;
    }

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        if (request.getParameter("viewfile.x") != null)
            return errors;

        if ((entryName == null) || (entryName.trim().length() == 0)) {
            errors.add("entryName", new ActionMessage("error.required.missing", "Name of skills"));
        } else if (entryName.trim().length() > PortfolioConstants.FIFTY_CHARS) {
            entryName = entryName.trim().substring(0, PortfolioConstants.FIFTY_CHARS);
            errors.add("entryName", new ActionMessage("error.lengthTooLong", "Name of skills", PortfolioConstants.FIFTY_CHARS_DESC));
        }
        // Fixing bug 59 with DateValidation class
        logService.debug("Starting to check survey date");
        if (DateValidation.checkDate(getSurveyDateMonth(), getSurveyDateYear()) == DateValidation.FAILURE) {
            errors.add("testDate", new ActionMessage("error.date.invalid", "Date taken"));
        }
        logService.debug("Done checking survey date");

        // self knowledge total length
        if (selfKnowledgeTotal != null && selfKnowledgeTotal.trim().length() > PortfolioConstants.SIX_CHARS) {
            selfKnowledgeTotal = selfKnowledgeTotal.trim().substring(0, PortfolioConstants.SIX_CHARS);
            errors.add("selfKnowledgeTotal", new ActionMessage(
                    "error.lengthTooLong",
                    "Self knowledge total",
                    PortfolioConstants.SIX_CHARS_DESC));
        }

        // self knowledge average length
        if (selfKnowledgeAve != null && selfKnowledgeAve.trim().length() > PortfolioConstants.SIX_CHARS) {
            selfKnowledgeAve = selfKnowledgeAve.trim().substring(0, PortfolioConstants.SIX_CHARS);
            errors.add("selfKnowledgeAve", new ActionMessage(
                    "error.lengthTooLong",
                    "Self knowledge average",
                    PortfolioConstants.SIX_CHARS_DESC));
        }

        // communication total length
        if (effectiveCommTotal != null && effectiveCommTotal.trim().length() > PortfolioConstants.SIX_CHARS) {
            effectiveCommTotal = effectiveCommTotal.trim().substring(0, PortfolioConstants.SIX_CHARS);
            errors.add("effectiveCommTotal", new ActionMessage(
                    "error.lengthTooLong",
                    "Effective communication total",
                    PortfolioConstants.SIX_CHARS_DESC));
        }

        // communication average length
        if (effectiveCommAve != null && effectiveCommAve.trim().length() > PortfolioConstants.SIX_CHARS) {
            effectiveCommAve = effectiveCommAve.trim().substring(0, PortfolioConstants.SIX_CHARS);
            errors.add("effectiveCommAve", new ActionMessage(
                    "error.lengthTooLong",
                    "Effective communication average",
                    PortfolioConstants.SIX_CHARS_DESC));
        }

        // process control total length
        if (processControlTotal != null && processControlTotal.trim().length() > PortfolioConstants.SIX_CHARS) {
            processControlTotal = processControlTotal.trim().substring(0, PortfolioConstants.SIX_CHARS);
            errors.add("processControlTotal", new ActionMessage(
                    "error.lengthTooLong",
                    "Process control total",
                    PortfolioConstants.SIX_CHARS_DESC));
        }

        // process control average length
        if (processControlAve != null && processControlAve.trim().length() > PortfolioConstants.SIX_CHARS) {
            processControlAve = processControlAve.trim().substring(0, PortfolioConstants.SIX_CHARS);
            errors.add("processControlAve", new ActionMessage(
                    "error.lengthTooLong",
                    "Process control average",
                    PortfolioConstants.SIX_CHARS_DESC));
        }

        // visioning total length
        if (visioningTotal != null && visioningTotal.trim().length() > PortfolioConstants.SIX_CHARS) {
            visioningTotal = visioningTotal.trim().substring(0, PortfolioConstants.SIX_CHARS);
            errors.add("visioningTotal", new ActionMessage("error.lengthTooLong", "Visioning total", PortfolioConstants.SIX_CHARS_DESC));
        }

        // visioning average length
        if (visioningAve != null && visioningAve.trim().length() > PortfolioConstants.SIX_CHARS) {
            visioningAve = visioningAve.trim().substring(0, PortfolioConstants.SIX_CHARS);
            errors.add("visioningAve", new ActionMessage("error.lengthTooLong", "Visioning average", PortfolioConstants.SIX_CHARS_DESC));
        }

        if ((interpretation != null) && (interpretation.length() > PortfolioConstants.EIGHT_HUNDRED_WORDS)) {
            interpretation = interpretation.substring(0, PortfolioConstants.EIGHT_HUNDRED_WORDS);
            errors.add("interpretation", new ActionMessage(
                    "error.lengthTooLong",
                    "Interpretation / Reaction",
                    PortfolioConstants.EIGHT_HUNDRED_WORDS_DESC));
        }

        return errors;
    }

    protected String surveyMonth = PortfolioTagConstants.MONTH_DEFAULT;
    protected String surveyYear = PortfolioTagConstants.YEAR_DEFAULT;
    protected java.lang.String selfKnowledgeTotal = null;
    protected java.lang.String selfKnowledgeAve = null;
    protected java.lang.String effectiveCommTotal = null;
    protected java.lang.String effectiveCommAve = null;
    protected java.lang.String processControlTotal = null;
    protected java.lang.String processControlAve = null;
    protected java.lang.String visioningTotal = null;
    protected java.lang.String visioningAve = null;
    protected java.lang.String interpretation = null;
}
