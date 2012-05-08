/* $Name:  $ */
/* $Id: PortfolioHttpRequestCallBack.java,v 1.4 2010/10/27 19:24:56 ajokela Exp $ */
package org.portfolio.client.security.authentication;

import javax.servlet.http.HttpServletRequest;
import javax.security.auth.callback.Callback;

/**
 * This class is an implementation of Callback to provide LoginModules
 * with access to the Request Object
 *
 * @author <a href="felipeen@udel.edu">Luis F.C. Mendes</a> - University of Delaware
 * @version $Revision 1.0 $
 */

public class PortfolioHttpRequestCallBack implements Callback, java.io.Serializable {

    private static final long serialVersionUID = -5641248478362775814L;
	private HttpServletRequest request;

    public HttpServletRequest getRequest() {
        return this.request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }
}
