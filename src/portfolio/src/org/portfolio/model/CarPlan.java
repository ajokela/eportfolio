/* $Name:  $ */
/* $Id: CarPlan.java,v 1.13 2010/10/27 19:24:57 ajokela Exp $ */
/*
 * $Header: /opt/UMN-src/portfolio/src/org/portfolio/model/CarPlan.java,v 1.13 2010/10/27 19:24:57 ajokela Exp $
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

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.portfolio.util.PortfolioConstants;

/**
 * This class encapsulates CarPlan element object.
 *
 * @author	John Bush
 * @since	0.0
 * @version	$Revision: 1.13 $
 */
public class CarPlan extends ElementBase {

    private static final long serialVersionUID = -1377537726590181789L;

    public java.lang.String getEntryName() {
        return (entryName != null ? entryName : EMPTY_STRING);
    }

    public void setEntryName(java.lang.String entryName) {
        this.entryName = entryName;
    }

    public java.lang.String getActionPlan() {
        return (actionPlan != null ? actionPlan : EMPTY_STRING);
    }

    public void setActionPlan(java.lang.String actionPlan) {
        this.actionPlan = actionPlan;
    }

    public java.lang.String getTimeline() {
        return (timeline != null ? timeline : EMPTY_STRING);
    }

    public void setTimeline(java.lang.String timeline) {
        this.timeline = timeline;
    }

    //--------------------------------------------------------------------------
    // Methods dictated by implemented interfaces

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        if (request.getParameter("viewfile.x") != null) return errors;

        if ((entryName == null) || (entryName.trim().length() == 0)) {
            errors.add("entryName", new ActionMessage("error.required.missing", "Career Objective / Goal"));
        } else if(entryName.trim().length() > PortfolioConstants.SIXTY_CHARS) {
            entryName = entryName.trim().substring(0, PortfolioConstants.SIXTY_CHARS);
            errors.add("entryName", new ActionMessage("error.lengthTooLong", "Career Objective / Goal", PortfolioConstants.SIXTY_CHARS_DESC ));
        }

        if ((actionPlan != null) && (actionPlan.length() > PortfolioConstants.EIGHT_HUNDRED_WORDS)) {
            actionPlan = actionPlan.substring(0, PortfolioConstants.EIGHT_HUNDRED_WORDS);
            errors.add("actionPlan", new ActionMessage("error.lengthTooLong", "Action plan", PortfolioConstants.EIGHT_HUNDRED_WORDS_DESC ));
        }

        if ((timeline != null) && (timeline.length() > PortfolioConstants.EIGHT_HUNDRED_WORDS)) {
            timeline = timeline.substring(0, PortfolioConstants.EIGHT_HUNDRED_WORDS);
            errors.add("timeline", new ActionMessage("error.lengthTooLong","Timeline", PortfolioConstants.EIGHT_HUNDRED_WORDS_DESC));
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
        buff.append("actionPlan=").append(getActionPlan()).append(",");
        buff.append("timeline=").append(getTimeline()).append(",");
        buff.append("is new? =>" + isNew());
        return buff.toString();
    }

    protected java.lang.String actionPlan = null;
    protected java.lang.String timeline = null;
    protected boolean isRemote = false;
}
