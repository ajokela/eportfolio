/* $Name:  $ */
/* $Id: DefaultLoginModule.java,v 1.6 2010/11/04 21:08:53 ajokela Exp $ */
package org.portfolio.client.security.authentication;

import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;

import org.portfolio.util.LogService;


/**
 * Login module for a Database System
 *
 * @author <a href="jbush@rsmart.com">John Bush</a>
 * @author <a href="felipeen@udel.edu">Luis F.C. Mendes</a> - University of Delaware
 * @version $Revision 1.0 $
 */
public class DefaultLoginModule implements PortfolioLoginModule {
    private static LogService logService = new LogService(DefaultLoginModule.class);

    PortfolioCredential credential;

    // initial state
    CallbackHandler callbackHandler;
    Subject subject;
    @SuppressWarnings("rawtypes")
	Map sharedState;
    @SuppressWarnings("rawtypes")
	Map options;

    // the authentication status
    boolean success;



    /**
     * <p>Creates a login module that can authenticate against
     * a JDBC datasource.
     */
    public DefaultLoginModule() {
        success = false;
    }

    /**
     * Initialize this <code>LoginModule</code>.
     *
     * <p>
     *
     * @param subject the <code>Subject</code> to be authenticated. <p>
     *
     * @param callbackHandler a <code>CallbackHandler</code> for communicating
     *			with the end user (prompting for usernames and
     *			passwords, for example). <p>
     *
     * @param sharedState shared <code>LoginModule</code> state. <p>
     *
     * @param options options specified in the login
     *			<code>Configuration</code> for this particular
     *			<code>LoginModule</code>.
     */
    @SuppressWarnings("rawtypes")
	public void initialize(Subject subject, CallbackHandler callbackHandler,
                           Map sharedState, Map options) {

        // save the initial state
        this.callbackHandler = callbackHandler;
        this.subject = subject;
        this.sharedState = sharedState;
        this.options = options;
    }

    /**
     * <p> Verify the password against the relevant JDBC datasource.
     *
     * @return true always, since this <code>LoginModule</code>
     *      should not be ignored.
     *
     * @exception javax.security.auth.login.FailedLoginException if the authentication fails. <p>
     *
     * @exception javax.security.auth.login.LoginException if this <code>LoginModule</code>
     *      is unable to perform the authentication.
     */
    public boolean login() throws LoginException {

        logService.debug("\t\t[DefaultLoginModule] login");

        if (callbackHandler == null)
            throw new LoginException("Error: no CallbackHandler available " +
                    "to garner authentication information from the user");

        try {
            // Setup default callback handlers.
            Callback[] callbacks = new Callback[]{
                new PortfolioHttpRequestCallBack()};

            callbackHandler.handle(callbacks);

            HttpServletRequest request = ((PortfolioHttpRequestCallBack) callbacks[0]).getRequest();

            String username = request.getParameter("username");
            String password = request.getParameter("password");

            PortfolioPrincipalImpl principal = checkPassword(username, password);
            if (principal != null) {
                success = true;
                subject.getPrincipals().add(principal);
            }
            callbacks[0] = null;

            if (!success)
                throw new LoginException("Authentication failed: Password does not match");

            return (true);
        } catch (LoginException ex) {
            throw ex;
        } catch (Exception ex) {
            success = false;
            throw new LoginException(ex.getMessage());
        }
    }

    /**
     * Abstract method to commit the authentication process (phase 2).
     *
     * <p> This method is called if the LoginContext's
     * overall authentication succeeded
     * (the relevant REQUIRED, REQUISITE, SUFFICIENT and OPTIONAL LoginModules
     * succeeded).
     *
     * <p> If this LoginModule's own authentication attempt
     * succeeded (checked by retrieving the private state saved by the
     * <code>login</code> method), then this method associates a
     * <code>PortfolioPrincipalImpl</code>
     * with the <code>Subject</code> located in the
     * <code>LoginModule</code>.  If this LoginModule's own
     * authentication attempted failed, then this method removes
     * any state that was originally saved.
     *
     * <p>
     *
     * @exception javax.security.auth.login.LoginException if the commit fails
     *
     * @return true if this LoginModule's own login and commit
     *      attempts succeeded, or false otherwise.
     */
    public boolean commit() throws LoginException {

        logService.debug("\t\t[DefaultLoginModule] commit");

        if (success) {

            if (subject.isReadOnly()) {
                throw new LoginException("Subject is Readonly");
            }

            try {
                subject.getPublicCredentials().add(credential);

                return (true);
            } catch (Exception ex) {
                throw new LoginException(ex.getMessage());
            }
        } else {
            return (true);
        }
    }

    /**
     * <p> This method is called if the LoginContext's
     * overall authentication failed.
     * (the relevant REQUIRED, REQUISITE, SUFFICIENT and OPTIONAL LoginModules
     * did not succeed).
     *
     * <p> If this LoginModule's own authentication attempt
     * succeeded (checked by retrieving the private state saved by the
     * <code>login</code> and <code>commit</code> methods),
     * then this method cleans up any state that was originally saved.
     *
     * <p>
     *
     * @exception javax.security.auth.login.LoginException if the abort fails.
     *
     * @return false if this LoginModule's own login and/or commit attempts
     *     failed, and true otherwise.
     */
    public boolean abort() throws LoginException {

        logService.debug("\t\t[DefaultLoginModule] abort");

        // Clean out state
        success = false;

        logout();

        return (true);
    }

    /**
     * Logout a user.
     *
     * <p> This method removes the Principals
     * that were added by the <code>commit</code> method.
     *
     * <p>
     *
     * @exception javax.security.auth.login.LoginException if the logout fails.
     *
     * @return true in all cases since this <code>LoginModule</code>
     *		should not be ignored.
     */
    public boolean logout() throws LoginException {

        logService.debug("\t\t[DefaultLoginModule] logout");

        // remove the principals the login module added
        Iterator<PortfolioPrincipalImpl> it = subject.getPrincipals(PortfolioPrincipalImpl.class).iterator();
        
        while (it.hasNext()) {
            PortfolioPrincipalImpl p = (PortfolioPrincipalImpl) it.next();
            subject.getPrincipals().remove(p);
        }

        // remove the credentials the login module added
        Iterator<PortfolioCredential>itt = subject.getPublicCredentials(PortfolioCredential.class).iterator();
        
        while (itt.hasNext()) {
            PortfolioCredential c = (PortfolioCredential) itt.next();
            subject.getPublicCredentials().remove(c);
        }

        return (true);
    }

    public PortfolioPrincipal getPrincipal(String username) {
        return null;
    }

    protected PortfolioPrincipalImpl checkPassword(String username, String password) {
        PortfolioPrincipalImpl principal = null;
        try {
            ResourceBundle props = ResourceBundle.getBundle("org.portfolio.security.authentication.defaultAuthentication");
            if (!props.getString("password").equals(password) || !props.getString("username").equals(username)) return null;
            principal = new PortfolioPrincipalImpl();
            this.credential = new PortfolioCredential(password);
            principal.setName(props.getString("username"));
            principal.setAttribute(PortfolioAuthenticator.FIRST_NAME, props.getString("firstname"));
            principal.setAttribute(PortfolioAuthenticator.LAST_NAME, props.getString("lastname"));
            principal.setAttribute(PortfolioAuthenticator.EMAIL, props.getString("email"));
            principal.setAttribute(PortfolioAuthenticator.USER_TYPE, props.getString("role"));            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return principal;
    }
}

