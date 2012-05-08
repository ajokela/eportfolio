/* $Name:  $ */
/* $Id: DeleteShareAction.java,v 1.6 2010/10/27 19:24:56 ajokela Exp $ */
/*
 * $Header: /opt/UMN-src/portfolio/src/org/portfolio/client/action/share/DeleteShareAction.java,v 1.6 2010/10/27 19:24:56 ajokela Exp $
 * $Revision: 1.6 $
 * $Date: 2010/10/27 19:24:56 $
 *
 * =====================================================================
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

package org.portfolio.client.action.share;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.portfolio.bus.PortfolioManager;
import org.portfolio.client.action.BaseAction;
import org.portfolio.util.RequiredInjection;

public class DeleteShareAction extends BaseAction {

	private PortfolioManager portfolioManager;

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String shareIdParam = request.getParameter("shareId");
		portfolioManager.deletePortfolio(shareIdParam);
		return mapping.findForward("success");
	}

	@RequiredInjection
    public void setPortfolioManager(PortfolioManager portfolioManager) {
        this.portfolioManager = portfolioManager;
    }
}
