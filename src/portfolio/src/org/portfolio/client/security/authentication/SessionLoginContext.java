/* $Name:  $ */
/* $Id: SessionLoginContext.java,v 1.3 2010/10/27 19:24:56 ajokela Exp $ */
/*
 * $Header: /opt/UMN-src/portfolio/src/org/portfolio/client/security/authentication/SessionLoginContext.java,v 1.3 2010/10/27 19:24:56 ajokela Exp $
 * $Revision: 1.3 $
 * $Date: 2010/10/27 19:24:56 $
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

import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

/**
 * The SessionLoginContext extends the JAAS LoginContext, so that,
 * when bound to an HttpSession, it will execute login(), and when
 * the session times out, it will execute logout(). One can bind
 * the SessionLoginContext to the session, by calling<br/><pre>
 *      session.setAttribute("loginContext", myLoginContext);
 * </pre>
 *
 * @author Paul Feuer and John Musser
 * @version 1.0
 */

public class SessionLoginContext extends LoginContext implements HttpSessionBindingListener {

    /**
     * Default constructor. See javax.security.auth.login.LoginContext
     * for details.
     */
    public SessionLoginContext(String name) throws LoginException {
        super(name);
    }

    /**
     * Default constructor. See javax.security.auth.login.LoginContext
     * for details.
     */
    public SessionLoginContext(String name, CallbackHandler callbackHandler) throws LoginException {
        super(name, callbackHandler);
    }

    /**
     * Default constructor. See javax.security.auth.login.LoginContext
     * for details.
     */
    public SessionLoginContext(String name, Subject subject) throws LoginException {
        super(name, subject);
    }

    /**
     * Default constructor. See javax.security.auth.login.LoginContext
     * for details.
     */
    public SessionLoginContext(String name, Subject subject, CallbackHandler callbackHandler) throws LoginException {
        super(name, subject, callbackHandler);
    }

    /**
     * Notifies the object that it is being bound to a
     * session and identifies the session.
     *
     * @param event the event that identifies the session
     */
    public void valueBound(HttpSessionBindingEvent event) {
        try {
            login();
        } catch (LoginException ex) {
            throw new java.lang.RuntimeException(ex.getMessage());
        }
    }

    /**
     * Notifies the object that it is being unbound from a
     * session and identifies the session.
     *
     * @param event the event that identifies the session
     */
    public void valueUnbound(HttpSessionBindingEvent event) {
        try {
            logout();
        } catch (LoginException ex) {
            throw new java.lang.RuntimeException(ex.getMessage());
        }
    }
}

