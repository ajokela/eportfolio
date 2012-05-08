/* $Name:  $ */
/* $Id: GuestAuthenticatorImpl.java,v 1.12 2011/03/02 20:46:59 rkavajec Exp $ */
/*=====================================================================
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
package org.portfolio.client.security.authentication;

import java.util.Date;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;

import org.portfolio.dao.PersonHome;
import org.portfolio.model.Person;
import org.portfolio.util.LogService;
import org.portfolio.util.PortfolioConstants;
import org.springframework.beans.factory.annotation.Required;

/**
 * Will authenticate a guest user
 * 
 * @author <a href="jbush@rsmart.com">John Bush</a>
 * @author <a href="felipeen@udel.edu">Luis F.C. Mendes</a> - University of
 *         Delaware
 * @version $Revision 1.0 $
 */
public class GuestAuthenticatorImpl extends BasePortfolioAuthenticator {

    private static final LogService logService = new LogService(GuestAuthenticatorImpl.class);
    
    private PersonHome personHome;
    private boolean isAuthenticated = false;

    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    public void login(HttpServletRequest request) throws LoginException {
        isAuthenticated = false;
        Person person = null;
        try {
            person = personHome.findByEmailAndPassword(request.getParameter("username"), request.getParameter("password"));

        } catch (Exception e) {
            logService.error(e);
        } finally {
            if (person == null)
                throw new LoginException("no person found with email and password supplied");
            else {
                // update lastLogin
                try {
                    person.setLastLogin(new Date());
                    personHome.store(person);
                } catch (Exception e) {
                    logService.error(e);
                }
                isAuthenticated = true;
                request.getSession().setAttribute(PortfolioConstants.AUTHENTICATOR_SESSION_KEY, this);
            }
        }

        try {
            personHome.store(person);
        } catch (Exception e) {
            logService.error(e);
        }

        initAfterLogin(person, request.getSession());
    }

    public void logout(HttpServletRequest request) {
        super.logout(request);
        isAuthenticated = false;
    }

    @Override
    public void switchUser(HttpServletRequest request, Person newPerson) {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public void switchBack(HttpServletRequest request) {
        throw new UnsupportedOperationException("TODO");
    }

    @Required
    public void setPersonHome(PersonHome personHome) {
        this.personHome = personHome;
    }
}
