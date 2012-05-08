/* $Name:  $ */
/* $Id: LogoutAction.java,v 1.8 2010/10/27 19:24:56 ajokela Exp $ */
package org.portfolio.client.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.portfolio.client.security.authentication.PortfolioAuthenticator;
import org.portfolio.model.Person;

public class LogoutAction extends BaseAction {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        Person person = getPerson(request);
        PortfolioAuthenticator authenticator = getAuthenticator(request);
        if (authenticator != null) {
            authenticator.logout(request);
        }
        
        if (person != null) {
        	return mapping.findForward(person.isGuest() ? "guestLogout" : "memberLogout");
        }

        return mapping.findForward("memberLogout");
    }
}
