/* $Name:  $ */
/* $Id: Email.java,v 1.14 2010/10/27 19:24:57 ajokela Exp $ */
package org.portfolio.model;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.portfolio.util.PortfolioConstants;

public class Email extends ElementBase {

    private static final long serialVersionUID = -8604901894900290161L;
    private String email;
    protected String prefEmailFlag = null;

    public String getEmail() {
        return (email != null ? email : EMPTY_STRING);
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        if (request.getParameter("viewfile.x") != null)
            return errors;

        if ((entryName == null) || (entryName.trim().length() == 0)) {
            errors.add("entryName", new ActionMessage("error.required.missing", "Type of e-mail address (personal, work, etc)"));
        } else if (entryName.trim().length() > PortfolioConstants.FIFTY_CHARS) {
            entryName = entryName.trim().substring(0, PortfolioConstants.FIFTY_CHARS);
            errors.add("entryName", new ActionMessage(
                    "error.lengthTooLong",
                    "Type of e-mail address (personal, work, etc)",
                    PortfolioConstants.FIFTY_CHARS_DESC));
        }

        // Email field length
        if (email != null && email.trim().length() > PortfolioConstants.FIFTY_CHARS) {
            email = email.trim().substring(0, PortfolioConstants.FIFTY_CHARS);
            errors.add("email", new ActionMessage("error.lengthTooLong", "E-mail address", PortfolioConstants.FIFTY_CHARS_DESC));
        }
        return errors;
    }
}
