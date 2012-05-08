/* $Name:  $ */
/* $Id: ConsoleCallbackHandler.java,v 1.3 2010/10/27 19:24:56 ajokela Exp $ */
/*
 * $Header: /opt/UMN-src/portfolio/src/org/portfolio/client/security/authentication/ConsoleCallbackHandler.java,v 1.3 2010/10/27 19:24:56 ajokela Exp $
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

/* Java imports */

import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;

/**
 * <p>
 * ConsoleCallbackHandler prompts and reads from the
 * command line console line for username and password.
 * This can be used by a JAAS application to instantiate a
 * CallbackHandler
 *
 * @see     javax.security.auth.callback
 * @author  Paul Feuer and John Musser
 * @version 1.0
 */

public class ConsoleCallbackHandler implements CallbackHandler {

    /**
     * <p>Creates a callback handler that prompts and reads from the
     * command line for answers to authentication questions.
     * This can be used by JAAS applications to instantiate a
     * CallbackHandler.
     */
    public ConsoleCallbackHandler() {
    }

    /**
     * Handles the specified set of callbacks.
     * This class supports NameCallback and PasswordCallback.
     *
     * @param   callbacks the callbacks to handle
     * @throws  java.io.IOException if an input or output error occurs.
     * @throws  javax.security.auth.callback.UnsupportedCallbackException if the callback is not an
     * instance of NameCallback or PasswordCallback
     */
    public void handle(Callback[] callbacks)
            throws java.io.IOException, UnsupportedCallbackException {

        for (int i = 0; i < callbacks.length; i++) {

            if (callbacks[i] instanceof NameCallback) {
                System.out.print(((NameCallback) callbacks[i]).getPrompt());
                String user = (new BufferedReader(new InputStreamReader(System.in))).readLine();
                ((NameCallback) callbacks[i]).setName(user);
            } else if (callbacks[i] instanceof PasswordCallback) {
                System.out.print(((PasswordCallback) callbacks[i]).getPrompt());
                String pass = (new BufferedReader(new InputStreamReader(System.in))).readLine();
                ((PasswordCallback) callbacks[i]).setPassword(pass.toCharArray());
            } else {
                throw(new UnsupportedCallbackException(
                        callbacks[i], "Callback class not supported"));
            }
        }
    }
}

