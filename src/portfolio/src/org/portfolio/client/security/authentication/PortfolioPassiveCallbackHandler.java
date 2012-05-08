/* $Name:  $ */
/* $Id: PortfolioPassiveCallbackHandler.java,v 1.3 2010/10/27 19:24:56 ajokela Exp $ */
/*
 * $Header: /opt/UMN-src/portfolio/src/org/portfolio/client/security/authentication/PortfolioPassiveCallbackHandler.java,v 1.3 2010/10/27 19:24:56 ajokela Exp $
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

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.servlet.http.HttpServletRequest;

import org.portfolio.util.LogService;

/**
 * PortfolioPassiveCallbackHandler has constructor that takes
 * a request object
 *
 * @author <a href="felipeen@udel.edu">Luis F.C. Mendes</a> - University of Delaware
 * @version $Revision: 1.3 $
 */

public class PortfolioPassiveCallbackHandler implements CallbackHandler {
    protected LogService logService = new LogService(this.getClass());
    private HttpServletRequest request;

    /**
     * Creates a callback handler with the request object
     */
    public PortfolioPassiveCallbackHandler(HttpServletRequest request) {
        this.request = request;
    }

    /**
     * Handles the specified set of Callbacks. Uses the
     * callback from portfolio
     *
     *
     * @param   callbacks the callbacks to handle
     * @throws  java.io.IOException if an input or output error occurs.
     * @throws  javax.security.auth.callback.UnsupportedCallbackException if the callback is not an
     */
    public void handle(Callback[] callbacks)
            throws java.io.IOException, UnsupportedCallbackException {
        for (int i = 0; i < callbacks.length; i++) {
            if (callbacks[i] instanceof PortfolioHttpRequestCallBack) {
                ((PortfolioHttpRequestCallBack) callbacks[i]).setRequest(request);
            } else {
                throw new UnsupportedCallbackException(
                        callbacks[i], "Callback class not supported");
            }
        }
    }
}
