/* $Name:  $ */
/* $Id: PortfolioAuthenticator.java,v 1.7 2010/10/27 19:24:56 ajokela Exp $ */
package org.portfolio.client.security.authentication;

import org.portfolio.model.Person;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;

/**
 * Definition of a Portfolio Authenticator
 *
 * @author Jack Brown, University of Minnesota
 * @version $Revision: 1.7 $ $Date: 2010/10/27 19:24:56 $
 * @since 0.1
 */
public interface PortfolioAuthenticator {
    // field names
    public static final String X500 = "x500";
    public static final String PERSON_ID = "personId";
    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String MIDDLE_NAME = "middleName";
    public static final String EMAIL = "email";
    public static final String CAMPUS = "campus";
    public static final String EXTERNAL_ID = "x500";
    public static final String USER_TYPE = "usertype";


    /**
     * Check to see if the user is authenticated. Will not forward the user to
     * any failure pages, as this is the job of the client class calling this
     * method.
     * @return true only if the user is authenticated. Error conditions should
     * always return false.
     */
    boolean isAuthenticated(HttpServletRequest request);

    /**
     * Logs the user into portfolio
     * @param request
     * @throws LoginException upon login failure.
     */
    void login(HttpServletRequest request) throws LoginException;

    /**
     * Logs the user out of portfolio. Should also invalidate the session.
     */
    void logout(HttpServletRequest request);

    /**
     * Admin function.
     * @param request
     * @param newPerson
     */
    void switchUser(HttpServletRequest request, Person newPerson);

    void switchBack(HttpServletRequest request);
}
