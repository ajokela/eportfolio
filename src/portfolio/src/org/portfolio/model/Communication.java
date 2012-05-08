/* $Name:  $ */
/* $Id: Communication.java,v 1.13 2010/10/27 19:24:57 ajokela Exp $ */
/*
 * $Header: /opt/UMN-src/portfolio/src/org/portfolio/model/Communication.java,v 1.13 2010/10/27 19:24:57 ajokela Exp $
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
 * This class encapsulates Communication element object.
 *
 * @author	John Bush
 * @author      Jack Brown
 * @since	0.0
 * @version	$Revision: 1.13 $
 */
public class Communication extends ElementBase {

    private static final long serialVersionUID = -1769885316007056240L;
	
    public java.lang.String getEntryName() {
        return (entryName != null ? entryName : EMPTY_STRING);
    }

    public void setEntryName(java.lang.String entryName) {
        this.entryName = entryName;
    }

    public java.lang.String getExpository() {
        return (expository != null ? expository : EMPTY_STRING);
    }

    public void setExpository(java.lang.String expository) {
        this.expository = expository;
    }

    public java.lang.String getCreative() {
        return (creative != null ? creative : EMPTY_STRING);
    }

    public void setCreative(java.lang.String creative) {
        this.creative = creative;
    }

    public java.lang.String getDiscipline() {
        return (discipline != null ? discipline : EMPTY_STRING);
    }

    public void setDiscipline(java.lang.String discipline) {
        this.discipline = discipline;
    }

    public java.lang.String getOneOnOne() {
        return (oneOnOne != null ? oneOnOne : EMPTY_STRING);
    }

    public void setOneOnOne(java.lang.String oneOnOne) {
        this.oneOnOne = oneOnOne;
    }

    public java.lang.String getSmallGroup() {
        return (smallGroup != null ? smallGroup : EMPTY_STRING);
    }

    public void setSmallGroup(java.lang.String smallGroup) {
        this.smallGroup = smallGroup;
    }

    public java.lang.String getFacilitation() {
        return (facilitation != null ? facilitation : EMPTY_STRING);
    }

    public void setFacilitation(java.lang.String facilitation) {
        this.facilitation = facilitation;
    }

    public java.lang.String getPublicSpeaking() {
        return (publicSpeaking != null ? publicSpeaking : EMPTY_STRING);
    }

    public void setPublicSpeaking(java.lang.String publicSpeaking) {
        this.publicSpeaking = publicSpeaking;
    }

    public java.lang.String getListening() {
        return (listening != null ? listening : EMPTY_STRING);
    }

    public void setListening(java.lang.String listening) {
        this.listening = listening;
    }

    public java.lang.String getConflictRes() {
        return (conflictRes != null ? conflictRes : EMPTY_STRING);
    }

    public void setConflictRes(java.lang.String conflictRes) {
        this.conflictRes = conflictRes;
    }

    //--------------------------------------------------------------------------
    // Methods dictated by implemented interfaces

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        if (request.getParameter("viewfile.x") != null) return errors;

        if ((entryName == null) || (entryName.trim().length() == 0)) {
            errors.add("entryName", new ActionMessage("error.required.missing", "Name of skills" ));
        } else if(entryName.trim().length() > PortfolioConstants.FIFTY_CHARS) {
            entryName = entryName.trim().substring(0, PortfolioConstants.FIFTY_CHARS);
            errors.add("entryName", new ActionMessage("error.lengthTooLong", "Name of skills", PortfolioConstants.FIFTY_CHARS_DESC));
        }

        if ((expository != null) && (expository.length() > PortfolioConstants.EIGHT_HUNDRED_WORDS)) {
            expository = expository.substring(0, PortfolioConstants.EIGHT_HUNDRED_WORDS);
            errors.add("expository", new ActionMessage("error.lengthTooLong", "Expository writing", PortfolioConstants.EIGHT_HUNDRED_WORDS_DESC));
        }
        if ((creative != null) && (creative.length() > PortfolioConstants.EIGHT_HUNDRED_WORDS)) {
            creative = creative.substring(0, PortfolioConstants.EIGHT_HUNDRED_WORDS);
            errors.add("creative", new ActionMessage( "error.lengthTooLong", "Creative writing", PortfolioConstants.EIGHT_HUNDRED_WORDS_DESC ));
        }
        if ((discipline != null) && (discipline.length() > PortfolioConstants.EIGHT_HUNDRED_WORDS)) {
            discipline = discipline.substring(0, PortfolioConstants.EIGHT_HUNDRED_WORDS);
            errors.add("discipline", new ActionMessage( "error.lengthTooLong", "Writing in your discipline", PortfolioConstants.EIGHT_HUNDRED_WORDS_DESC ));
        }
        if ((oneOnOne != null) && (oneOnOne.length() > PortfolioConstants.EIGHT_HUNDRED_WORDS)) {
            oneOnOne = oneOnOne.substring(0, PortfolioConstants.EIGHT_HUNDRED_WORDS);
            errors.add("oneOnOne", new ActionMessage( "error.lengthTooLong", "One-on-one interaction", PortfolioConstants.EIGHT_HUNDRED_WORDS_DESC ));
        }
        if ((smallGroup != null) && (smallGroup.length() > PortfolioConstants.EIGHT_HUNDRED_WORDS)) {
            smallGroup = smallGroup.substring(0, PortfolioConstants.EIGHT_HUNDRED_WORDS);
            errors.add("smallGroup", new ActionMessage( "error.lengthTooLong", "Small group communication", PortfolioConstants.EIGHT_HUNDRED_WORDS_DESC ));
        }
        if ((facilitation != null) && (facilitation.length() > PortfolioConstants.EIGHT_HUNDRED_WORDS)) {
            facilitation = facilitation.substring(0, PortfolioConstants.EIGHT_HUNDRED_WORDS);
            errors.add("facilitation", new ActionMessage( "error.lengthTooLong", "Small group facilitation", PortfolioConstants.EIGHT_HUNDRED_WORDS_DESC ));
        }
        if ((publicSpeaking != null) && (publicSpeaking.length() > PortfolioConstants.EIGHT_HUNDRED_WORDS)) {
            publicSpeaking = publicSpeaking.substring(0, PortfolioConstants.EIGHT_HUNDRED_WORDS);
            errors.add("publicSpeaking", new ActionMessage( "error.lengthTooLong", "Public speaking", PortfolioConstants.EIGHT_HUNDRED_WORDS_DESC ));
        }
        if ((listening != null) && (listening.length() > PortfolioConstants.EIGHT_HUNDRED_WORDS)) {
            listening = listening.substring(0, PortfolioConstants.EIGHT_HUNDRED_WORDS);
            errors.add("listening", new ActionMessage( "error.lengthTooLong", "Listening", PortfolioConstants.EIGHT_HUNDRED_WORDS_DESC ));
        }
        if ((conflictRes != null) && (conflictRes.length() > PortfolioConstants.EIGHT_HUNDRED_WORDS)) {
            conflictRes = conflictRes.substring(0, PortfolioConstants.EIGHT_HUNDRED_WORDS);
            errors.add("conflictRes", new ActionMessage( "error.lengthTooLong", "Conflict resolution", PortfolioConstants.EIGHT_HUNDRED_WORDS_DESC ));
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
        buff.append("expository=").append(getExpository()).append(",");
        buff.append("creative=").append(getCreative()).append(",");
        buff.append("discipline=").append(getDiscipline()).append(",");
        buff.append("oneOnOne=").append(getOneOnOne()).append(",");
        buff.append("smallGroup=").append(getSmallGroup()).append(",");
        buff.append("facilitation=").append(getFacilitation()).append(",");
        buff.append("publicSpeaking=").append(getPublicSpeaking()).append(",");
        buff.append("listening=").append(getListening()).append(",");
        buff.append("conflictRes=").append(getConflictRes()).append(",");
        buff.append("is new? =>" + isNew());
        return buff.toString();
    }

    protected java.lang.String expository = null;
    protected java.lang.String creative = null;
    protected java.lang.String discipline = null;
    protected java.lang.String oneOnOne = null;
    protected java.lang.String smallGroup = null;
    protected java.lang.String facilitation = null;
    protected java.lang.String publicSpeaking = null;
    protected java.lang.String listening = null;
    protected java.lang.String conflictRes = null;
    protected boolean isRemote = false;
}
