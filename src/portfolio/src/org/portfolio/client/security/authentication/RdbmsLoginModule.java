/* $Name:  $ */
/* $Id: RdbmsLoginModule.java,v 1.7 2010/11/04 21:08:53 ajokela Exp $ */
/*
 * $Header: /opt/UMN-src/portfolio/src/org/portfolio/client/security/authentication/RdbmsLoginModule.java,v 1.7 2010/11/04 21:08:53 ajokela Exp $
 * $Revision: 1.7 $
 * $Date: 2010/11/04 21:08:53 $
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

package org.portfolio.client.security.authentication;

import java.util.Iterator;
import java.util.Map;

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
public class RdbmsLoginModule implements PortfolioLoginModule {
    private static LogService logService = new LogService(RdbmsLoginModule.class);

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

    // configurable options
    String userTable;
    String roleMembershipTable;
    String userTableUserField;
    String roleMembershipTableUserField;
    String roleMembershipTableRoleField;
    String userTablePasswordField;
    String userTableFirstnameField;
    String userTableLastnameField;
    String userTableMiddlenameField;
    String userTableEmailField;
    String databaseName;

    /**
     * <p>Creates a login module that can authenticate against
     * a JDBC datasource.
     */
    public RdbmsLoginModule() {
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

        // initialize any configured options
        userTable = (String) options.get("user.table");
        roleMembershipTable = (String) options.get("role.membership.table");
        userTableUserField = (String) options.get("user.table.user.field");
        roleMembershipTableUserField = (String) options.get("role.membership.table.user.field");
        roleMembershipTableRoleField = (String) options.get("role.membership.table.role.field");
        userTablePasswordField = (String) options.get("user.table.password.field");
        userTableFirstnameField = (String) options.get("user.table.firstname.field");
        userTableLastnameField = (String) options.get("user.table.lastname.field");
        userTableMiddlenameField = (String) options.get("user.table.middlename.field");
        userTableEmailField = (String) options.get("user.table.email.field");
        databaseName = (String) options.get("database.name");

        logService.debug("\t\t[RdbmsLoginModule] initialize");
        logService.debug("\t\t[RdbmsLoginModule] user.table: " + userTable);
        logService.debug("\t\t[RdbmsLoginModule] role.membership.table: " + roleMembershipTable);
        logService.debug("\t\t[RdbmsLoginModule] user.table.user.field: " + userTableUserField);
        logService.debug("\t\t[RdbmsLoginModule] role.membership.table.user.field: " + roleMembershipTableUserField);
        logService.debug("\t\t[RdbmsLoginModule] role.membership.table.role.field: " + roleMembershipTableRoleField);
        logService.debug("\t\t[RdbmsLoginModule] user.table.password.field: " + userTablePasswordField);
        logService.debug("\t\t[RdbmsLoginModule] user.table.firstname.field: " + userTableFirstnameField);
        logService.debug("\t\t[RdbmsLoginModule] user.table.lastname.field: " + userTableLastnameField);
        logService.debug("\t\t[RdbmsLoginModule] user.table.middlename.field: " + userTableMiddlenameField);
        logService.debug("\t\t[RdbmsLoginModule] user.table.email.field: " + userTableEmailField);
        logService.debug("\t\t[RdbmsLoginModule] database.name: " + databaseName);

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

        logService.debug("\t\t[RdbmsLoginModule] login");

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

        logService.debug("\t\t[RdbmsLoginModule] commit");

        if (success) {

            if (subject.isReadOnly()) {
                throw new LoginException("Subject is Readonly");
            }

            try {
                subject.getPublicCredentials().add(credential);

                return (true);
            } catch (Exception ex) {
                ex.printStackTrace(System.out);
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
    public boolean abort() throws javax.security.auth.login.LoginException {

        logService.debug("\t\t[RdbmsLoginModule] abort");

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
    public boolean logout() throws javax.security.auth.login.LoginException {

        logService.debug("\t\t[RdbmsLoginModule] logout");

        // remove the principals the login module added
        Iterator<PortfolioPrincipalImpl> it = subject.getPrincipals(PortfolioPrincipalImpl.class).iterator();
        while (it.hasNext()) {
            PortfolioPrincipalImpl p = (PortfolioPrincipalImpl) it.next();
            logService.debug("\t\t[RdbmsLoginModule] removing principal " + p.toString());
            subject.getPrincipals().remove(p);
        }

        // remove the credentials the login module added
        Iterator<PortfolioCredential>itt = subject.getPublicCredentials(PortfolioCredential.class).iterator();
        
        while (itt.hasNext()) {
            PortfolioCredential c = (PortfolioCredential) itt.next();
            logService.debug("\t\t[RdbmsLoginModule] removing credential " + c.toString());
            subject.getPublicCredentials().remove(c);
        }

        return (true);
    }

    public PortfolioPrincipal getPrincipal(String username) {
        return null;
    }

    protected PortfolioPrincipalImpl checkPassword(String username, String password) {
    	/*
        boolean retVal = false;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Connection connection = null;
        */
        PortfolioPrincipalImpl principal = null;
        try {
        	//connection = DatabaseConnectionFactory.getConnection(databaseName);
            
        	if (true) throw new UnsupportedOperationException("TODO");
            
            /*
            stmt = connection.prepareStatement("select * from " + userTable +
                    " where " + userTableUserField + "= ? and " + userTablePasswordField + "= ?");
            stmt.setString(1, username);
            stmt.setString(2, password);
            rs = stmt.executeQuery();
            if (rs.next()) {
                principal = new PortfolioPrincipalImpl();
                this.credential = new PortfolioCredential(password);
                retVal = true;
                principal.setName(rs.getString(userTableUserField));
                // we should check for nulls here, as well at the setAttribute.
                if ( (userTableFirstnameField != null ) && ( userTableFirstnameField.length() > 0) ) {
                    principal.setAttribute(PortfolioAuthenticator.FIRST_NAME, rs.getString(userTableFirstnameField));
                }
                if ( ( userTableLastnameField != null ) && ( userTableLastnameField.length() > 0) ) {
                    principal.setAttribute(PortfolioAuthenticator.LAST_NAME, rs.getString(userTableLastnameField));
                }
                if ( (userTableMiddlenameField != null ) && ( userTableMiddlenameField.length() > 0) ) {
                    principal.setAttribute(PortfolioAuthenticator.MIDDLE_NAME, rs.getString(userTableMiddlenameField));
                }
                if ( ( userTableEmailField != null ) && ( userTableEmailField.length() > 0 ) ) {
                    principal.setAttribute(PortfolioAuthenticator.EMAIL, rs.getString(userTableEmailField));
                }
                //TODO break this out into a property
                principal.setAttribute(PortfolioAuthenticator.USER_TYPE, rs.getString("USERTYPE"));

            }
            */
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
            	/*
                rs.close();
                stmt.close();
                connection.close();
            	*/
            } catch (Exception e) {
            } // ignore these
        }
        return principal;
    }
}

