/* $Name:  $ */
/* $Id: BasePortfolioAuthenticator.java,v 1.16 2010/10/27 19:24:56 ajokela Exp $ */
package org.portfolio.client.security.authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.portfolio.model.Person;
import org.portfolio.util.LogService;
import org.portfolio.util.PortfolioConstants;

public abstract class BasePortfolioAuthenticator implements PortfolioAuthenticator {

	private LogService logService = new LogService(this.getClass());
	
    public boolean isAuthenticated(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return session.getAttribute(PortfolioConstants.PERSON_SESSION_KEY) != null;
    }

    public void logout(HttpServletRequest request) {
        request.getSession().invalidate();
    }

    protected void initAfterLogin(Person person, HttpSession session) {
    	
    	if(person == null) {
    		logService.debug("===> person is null in initAfterLogin");
    	}
    	
        session.setAttribute(PortfolioConstants.PERSON_ID_SESSION_KEY, person.getPersonId());
        session.setAttribute(PortfolioConstants.PERSON_SESSION_KEY, person);
    }
}
