/* $Name:  $ */
/* $Id: LDAPLoginModule.java,v 1.5 2010/11/04 21:08:53 ajokela Exp $ */
/*
 * $Header: /opt/UMN-src/portfolio/src/org/portfolio/client/security/authentication/LDAPLoginModule.java,v 1.5 2010/11/04 21:08:53 ajokela Exp $
 * $Revision: 1.5 $
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

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;
import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;

import org.portfolio.util.LogService;

public class LDAPLoginModule implements PortfolioLoginModule {
    //obtained from LoginContext
    private Subject subject;
    private CallbackHandler callbackHandler;

    //default options and othere info obtained from configuration
    Properties options = new Properties();;


    //authentication state of person
    private boolean verification = false;

    //default options
    private Hashtable<String, Object> props;
    private DirContext ctx;

    final static private String DISTINGUISHED_NAME = "dn";
    private static LogService logService = new LogService(LDAPLoginModule.class);

    public LDAPLoginModule() {
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
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void initialize(Subject subject, CallbackHandler callbackHandler, Map sharedState, Map options) {
        if (logService == null)
            logService = new LogService();

        this.options.putAll(options);
        this.subject = subject;
        this.callbackHandler = callbackHandler;
        for (Iterator<String> i = options.keySet().iterator(); i.hasNext();) {
            String key = (String) i.next();
            logService.debug("\t\t[LDAPLoginModule] " + key + "=" + options.get(key));
        }


    }

    public PortfolioPrincipal getPrincipal(String username) {
        PortfolioPrincipal principal = new PortfolioPrincipalImpl();
        principal.setName(username);

        props = new Hashtable<String, Object>();
        props.put(Context.INITIAL_CONTEXT_FACTORY, options.getProperty("context.factory"));
        props.put(Context.PROVIDER_URL, options.getProperty("server.url"));
        props.put(Context.SECURITY_PRINCIPAL, options.getProperty("system.dn"));
        props.put(Context.SECURITY_CREDENTIALS, options.getProperty("system.password"));
        props.put(Context.SECURITY_AUTHENTICATION, options.getProperty("authentication"));
        try {
            ctx = new InitialDirContext(props);
            BasicAttributes attributes = new BasicAttributes();
            attributes.put(new BasicAttribute(options.getProperty("attribute.uid")));
            NamingEnumeration<?> enumeration = ctx.search(options.getProperty("uid.search.dn"),
                                                       options.getProperty("attribute.uid") + "=" + username,
                                                       new SearchControls());

            if (enumeration.hasMore()) {
                SearchResult result = (SearchResult) enumeration.next();
                String name = result.getName();
                Attributes userAttributes = result.getAttributes();
                 principal.setAttribute(PortfolioAuthenticator.LAST_NAME, userAttributes.get(options.getProperty("attribute.lastname")).get().toString().trim());
                 principal.setAttribute(PortfolioAuthenticator.EMAIL, userAttributes.get(options.getProperty("attribute.email")).get().toString().trim());
                 principal.setAttribute(PortfolioAuthenticator.EXTERNAL_ID, username);
                 principal.setAttribute(PortfolioAuthenticator.FIRST_NAME, userAttributes.get(options.getProperty("attribute.firstname")).get().toString().trim());
                 principal.setAttribute(DISTINGUISHED_NAME, name + "," + options.getProperty("uid.search.dn"));
                 return principal;
            }
        } catch (Exception e) {
            logService.error( e);
        }

        return null;
    }

    /**
     * Authenticate the user by prompting for a username and password.
     *
     * <p>
     *
     * @return true in all cases since this <code>LoginModule</code>
     *		should not be ignored.
     *
     * @exception LoginException if this <code>LoginModule</code>
     *		is unable to perform the authentication.
     */
    public boolean login() throws LoginException {
        PortfolioPrincipal principal = null;

        logService.debug("\t\t[LDAPLoginModule] login");

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

            principal = getPrincipal(username);
            if (principal == null) throw new LoginException("The username " + username + " does not exist");

//try to connect with dn and supplied password, this verifies the credentials are correct
            props.put(Context.SECURITY_PRINCIPAL, principal.getAttribute(DISTINGUISHED_NAME));
            props.put(Context.SECURITY_CREDENTIALS, password);
            ctx = new InitialDirContext(props);
            subject.getPrincipals().add(principal);
            verification = true;
        } catch (NamingException e) {
            throw new LoginException(e.toString() + "  " + e.getRootCause());
        } catch (Exception ex) {
            verification = false;
            throw new LoginException(ex.getMessage());
        }

        return verification;
    }


    /**
     * <p> This method is called if the LoginContext's
     * overall authentication succeeded
     * (the relevant REQUIRED, REQUISITE, SUFFICIENT and OPTIONAL LoginModules
     * succeeded).
     *
     * <p> If this LoginModule's own authentication attempt
     * succeeded (checked by retrieving the private state saved by the
     * <code>login</code> method), then this method associates a
     * <code>SamplePrincipal</code>
     * with the <code>Subject</code> located in the
     * <code>LoginModule</code>.  If this LoginModule's own
     * authentication attempted failed, then this method removes
     * any state that was originally saved.
     *
     * <p>
     *
     * @exception LoginException if the commit fails.
     *
     * @return true if this LoginModule's own login and commit
     *		attempts succeeded, or false otherwise.
     */

    public boolean commit() throws LoginException {
        logService.debug("\t\t[LDAPLoginModule] commit");

        if (verification) {

            if (subject.isReadOnly()) {
                throw new LoginException("Subject is Readonly");
            } else
                return true;
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
     * @exception LoginException if the abort fails.
     *
     * @return false if this LoginModule's own login and/or commit attempts
     *		failed, and true otherwise.
     */
    public boolean abort() throws LoginException {
        logService.debug("\t\t[LDAPLoginModule] about");
        if (!verification) {
            verification = false;
            return true;
        }
        return false;
    }


    /**
     * Logout the user.
     *
     * <p> This method removes the <code>SamplePrincipal</code>
     * that was added by the <code>commit</code> method.
     *
     * <p>
     *
     * @exception LoginException if the logout fails.
     *
     * @return true in all cases since this <code>LoginModule</code>
     * should not be ignored.
     */

    public boolean logout() throws LoginException {
        logService.debug("\t\t[LDAPLoginModule] logout");

        try {
            subject.getPrincipals().clear();
            verification = false;
            return true;
        } catch (Exception e) {
            throw new LoginException(e.toString());
        }
    }


}

