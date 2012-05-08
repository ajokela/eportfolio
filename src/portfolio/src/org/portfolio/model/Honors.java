/* $Name:  $ */
/* $Id: Honors.java,v 1.7 2010/10/27 19:24:57 ajokela Exp $ */
/*
 * $Header: /opt/UMN-src/portfolio/src/org/portfolio/model/Honors.java,v 1.7 2010/10/27 19:24:57 ajokela Exp $
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
 * This class encapsulates Honors element object.
 * 
 * @author John Bush
 * @author Jack Brown
 * @since 0.0
 * @version $Revision: 1.7 $
 */
public class Honors extends ElementBase {

    private static final long serialVersionUID = 4225717090499596058L;

    public java.lang.String getEntryName() {
        return (entryName != null ? entryName : EMPTY_STRING);
    }

    public void setEntryName(java.lang.String entryName) {
        this.entryName = entryName;
    }

    public java.lang.String getComments() {
        return (comments != null ? comments : EMPTY_STRING);
    }

    public void setComments(java.lang.String comments) {
        this.comments = comments;
    }

    public java.util.Date getRcdDate() {
        Date rcdDate = null;
        if ((!getRcdDateMonth().equals(PortfolioTagConstants.MONTH_DEFAULT))
                && (!getRcdDateYear().equals(PortfolioTagConstants.YEAR_DEFAULT))) {
            try {
                // logService.debug("getting month and year");
                int month = Integer.parseInt(getRcdDateMonth());
                int year = Integer.parseInt(getRcdDateYear());

                Calendar cal = new GregorianCalendar(year, month, 1);
                cal.setLenient(false);
                rcdDate = new org.portfolio.util.DateForXSL(cal.getTime().getTime(), true);
            } catch (NumberFormatException e) {
                logService.debug("Error parsing date ", e);
            } catch (IllegalArgumentException iae) {
                logService.debug("Invalid date caught", iae);
            }
        }
        return rcdDate;
    }

    public void setRcdDate(java.util.Date rcdDate) {
        if (rcdDate != null) {
            GregorianCalendar cal = new GregorianCalendar();
            cal.setTime(rcdDate);

            setRcdDateMonth(String.valueOf(cal.get(Calendar.MONTH)));
            setRcdDateYear(String.valueOf(cal.get(Calendar.YEAR)));
        }
    }

    public String getRcdDateMonth() {
        return (rcdDateMonth != null ? rcdDateMonth : PortfolioTagConstants.MONTH_DEFAULT);
    }

    public void setRcdDateMonth(String rcdDateMonth) {
        this.rcdDateMonth = (rcdDateMonth != null ? rcdDateMonth : PortfolioTagConstants.MONTH_DEFAULT);
    }

    public String getRcdDateYear() {
        return (rcdDateYear != null ? rcdDateYear : PortfolioTagConstants.YEAR_DEFAULT);
    }

    public void setRcdDateYear(String rcdDateYear) {
        this.rcdDateYear = (rcdDateYear != null ? rcdDateYear : PortfolioTagConstants.YEAR_DEFAULT);
    }

    // --------------------------------------------------------------------------
    // Methods dictated by implemented interfaces

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        if (request.getParameter("viewfile.x") != null)
            return errors;

        if ((entryName == null) || (entryName.trim().length() == 0)) {
            errors.add("entryName", new ActionMessage("error.required.missing", "Type of honor"));
        } else if (entryName.trim().length() > PortfolioConstants.SIXTY_CHARS) {
            entryName = entryName.trim().substring(0, PortfolioConstants.SIXTY_CHARS);
            errors.add("entryName", new ActionMessage("error.lengthTooLong", "Type of honor", PortfolioConstants.SIXTY_CHARS_DESC));
        }

        if (DateValidation.checkDate(getRcdDateMonth(), getRcdDateYear()) == DateValidation.FAILURE) {
            errors.add("rcdDate", new ActionMessage("error.date.invalid", "Date received"));
        }

        if ((comments != null) && (comments.length() > PortfolioConstants.EIGHT_HUNDRED_WORDS)) {
            comments = comments.substring(0, PortfolioConstants.EIGHT_HUNDRED_WORDS);
            errors.add("comments", new ActionMessage("error.lengthTooLong", "Comments", PortfolioConstants.EIGHT_HUNDRED_WORDS_DESC));
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
        buff.append("rcdDate=").append(getRcdDate()).append(",");
        buff.append("comments=").append(getComments()).append(",");
        buff.append("is new? =>" + isNew());
        return buff.toString();
    }

    private String rcdDateMonth = PortfolioTagConstants.MONTH_DEFAULT;
    private String rcdDateYear = PortfolioTagConstants.YEAR_DEFAULT;
    protected java.lang.String comments = null;
    protected boolean isRemote = false;
}
