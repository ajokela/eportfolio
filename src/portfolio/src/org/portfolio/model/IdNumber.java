/* $Name:  $ */
/* $Id: IdNumber.java,v 1.15 2010/10/27 19:24:57 ajokela Exp $ */
package org.portfolio.model;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.portfolio.util.PortfolioConstants;

public class IdNumber extends ElementBase {

    private static final long serialVersionUID = 1499277838194741337L;

    private String idNumber;
    private String addlInfo;

    public String getIdNumber() {
        return (idNumber != null ? idNumber : EMPTY_STRING);
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getAddlInfo() {
        return (addlInfo != null ? addlInfo : EMPTY_STRING);
    }

    public void setAddlInfo(String addlInfo) {
        this.addlInfo = addlInfo;
    }

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        if (request.getParameter("viewfile.x") != null)
            return errors;

        if ((entryName == null) || (entryName.trim().length() == 0)) {
            errors.add("entryName", new ActionMessage("error.required.missing", "Entry Name"));
        } else if (entryName.trim().length() > PortfolioConstants.FIFTY_CHARS) {
            entryName = entryName.substring(0, PortfolioConstants.FIFTY_CHARS);
            errors.add("entryName", new ActionMessage("error.lengthTooLong", "Entry Name", PortfolioConstants.FIFTY_CHARS_DESC));
        }

        if (idNumber != null && idNumber.trim().length() > PortfolioConstants.FIFTY_CHARS) {
            errors.add("idNumber", new ActionMessage("error.lengthTooLong", "ID Number", PortfolioConstants.FIFTY_CHARS_DESC));
        }

        // Validate text field code consistent with similar fields...
        if ((addlInfo != null) && (addlInfo.length() > PortfolioConstants.EIGHT_HUNDRED_WORDS)) {
            addlInfo = addlInfo.substring(0, PortfolioConstants.EIGHT_HUNDRED_WORDS);
            errors.add("addlInfo", new ActionMessage("error.lengthTooLong", "addlInfo", PortfolioConstants.EIGHT_HUNDRED_WORDS_DESC));
        }

        return errors;
    }
}
