/* $Name:  $ */
/* $Id: InformationSkills.java,v 1.6 2010/10/27 19:24:57 ajokela Exp $ */
/*
 * ============================================================================
 *
 * @author	John Hemmerle, University of Minnesota Web Development
 * @since	0.1
 * @version	$Revision: 1.6 $

 */

package org.portfolio.model;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.portfolio.util.PortfolioConstants;

/**
 * This class encapsulates ComputerSkills element object.
 *
 * @author	John Bush
 * @author      Jack Brown, University of Minnesota
 * @since	0.0
 * @version	$Revision: 1.6 $
 */
public class InformationSkills extends ElementBase {

    private static final long serialVersionUID = 4959842629047644197L;
	
    public java.lang.String getEntryName() {
        return (entryName != null ? entryName : EMPTY_STRING);
    }

    public void setEntryName(java.lang.String entryName) {
        this.entryName = entryName;
    }

    public java.lang.String getRecognizeNeed() {
        return (recognizeNeed != null ? recognizeNeed : EMPTY_STRING);
    }

    public void setRecognizeNeed(java.lang.String recognizeNeed) {
        this.recognizeNeed = recognizeNeed;
    }

    public java.lang.String getAccessInfo() {
        return (accessInfo != null ? accessInfo : EMPTY_STRING);
    }

    public void setAccessInfo(java.lang.String accessInfo) {
        this.accessInfo = accessInfo;
    }

    public java.lang.String getEvaluateInfo() {
        return (evaluateInfo != null ? evaluateInfo : EMPTY_STRING);
    }

    public void setEvaluateInfo(java.lang.String evaluateInfo) {
        this.evaluateInfo = evaluateInfo;
    }

    public java.lang.String getUseEffectively() {
        return (useEffectively != null ? useEffectively : EMPTY_STRING);
    }

    public void setUseEffectively(java.lang.String useEffectively) {
        this.useEffectively = useEffectively;
    }

    public java.lang.String getUseEthically() {
        return (useEthically != null ? useEthically : EMPTY_STRING);
    }

    public void setUseEthically(java.lang.String useEthically) {
        this.useEthically = useEthically;
    }


    //--------------------------------------------------------------------------
    // Methods dictated by implemented interfaces

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        if (request.getParameter("viewfile.x") != null) return errors;

        if ((entryName == null) || (entryName.trim().length() == 0)) {
            errors.add("entryName", new ActionMessage("error.required.missing", "Name of skills"));
        } else if(entryName.trim().length() > PortfolioConstants.EIGHT_HUNDRED_WORDS) {
            entryName = entryName.trim().substring(0, PortfolioConstants.EIGHT_HUNDRED_WORDS);
            errors.add("entryName", new ActionMessage("error.lengthTooLong", "Name of skills", PortfolioConstants.FIFTY_CHARS_DESC));
        }

        //General Operations length
        if(recognizeNeed != null && recognizeNeed.trim().length() > PortfolioConstants.EIGHT_HUNDRED_WORDS) {
            recognizeNeed = recognizeNeed.trim().substring(0, PortfolioConstants.EIGHT_HUNDRED_WORDS);
            errors.add("recognizeNeed", new ActionMessage("error.lengthTooLong", "Recognize Info Need Text", PortfolioConstants.EIGHT_HUNDRED_WORDS_DESC));
        }

        //Communications-Internet length
        if(accessInfo != null && accessInfo.trim().length() > PortfolioConstants.EIGHT_HUNDRED_WORDS) {
            accessInfo = accessInfo.trim().substring(0, PortfolioConstants.EIGHT_HUNDRED_WORDS);
            errors.add("accessInfo", new ActionMessage("error.lengthTooLong", "AccessInfo Info Text" , PortfolioConstants.EIGHT_HUNDRED_WORDS_DESC ));
        }

        //Word Processing length
        if(evaluateInfo != null && evaluateInfo.trim().length() > PortfolioConstants.EIGHT_HUNDRED_WORDS) {
            evaluateInfo = evaluateInfo.trim().substring(0, PortfolioConstants.EIGHT_HUNDRED_WORDS);
            errors.add("evaluateInfo", new ActionMessage("error.lengthTooLong", "EvaluateInfo Info", PortfolioConstants.EIGHT_HUNDRED_WORDS_DESC ));
        }

        //Spreadsheet length
        if(useEffectively != null && useEffectively.trim().length() > PortfolioConstants.EIGHT_HUNDRED_WORDS) {
            useEffectively = useEffectively.trim().substring(0, PortfolioConstants.EIGHT_HUNDRED_WORDS);
            errors.add("useEffectively", new ActionMessage("error.lengthTooLong", "Use Info Effectively", PortfolioConstants.EIGHT_HUNDRED_WORDS_DESC ));
        }

        //Database length
        if(useEthically != null && useEthically.trim().length() > PortfolioConstants.EIGHT_HUNDRED_WORDS) {
            useEthically = useEthically.trim().substring(0, PortfolioConstants.EIGHT_HUNDRED_WORDS);
            errors.add("useEthically", new ActionMessage("error.lengthTooLong", "Use Info Ethically", PortfolioConstants.EIGHT_HUNDRED_WORDS_DESC ));
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
        buff.append("entryName=").append(getEntryName()).append(",");
        buff.append("dateCreated=").append(getDateCreated()).append(",");
        buff.append("modifiedDate=").append(getModifiedDate()).append(",");
        buff.append("recognizeNeed=").append(getRecognizeNeed()).append(",");
        buff.append("accessInfo=").append(getAccessInfo()).append(",");
        buff.append("evaluateInfo=").append(getEvaluateInfo()).append(",");
        buff.append("useEffectively=").append(getUseEffectively()).append(",");
        buff.append("useEthically=").append(getUseEthically()).append(",");
        buff.append("is new? =>" + isNew());
        return buff.toString();
    }

    protected java.lang.String recognizeNeed = null;
    protected java.lang.String accessInfo = null;
    protected java.lang.String evaluateInfo = null;
    protected java.lang.String useEffectively = null;
    protected java.lang.String useEthically = null;
    protected boolean isRemote = false;
}
