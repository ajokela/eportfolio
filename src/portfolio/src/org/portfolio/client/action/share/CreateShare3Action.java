/* $Name:  $ */
/* $Id: CreateShare3Action.java,v 1.11 2010/10/27 19:24:56 ajokela Exp $ */
/*
 * $Header: /opt/UMN-src/portfolio/src/org/portfolio/client/action/share/CreateShare3Action.java,v 1.11 2010/10/27 19:24:56 ajokela Exp $
 * $Revision: 1.11 $
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

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.portfolio.client.action.BaseAction;
import org.portfolio.model.Portfolio;
import org.portfolio.model.Viewer;
import org.portfolio.model.Viewer.ViewType;

public class CreateShare3Action extends BaseAction {
        
	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		List<Viewer> viewerList = (List<Viewer>) session.getAttribute("viewers");

		if (viewerList == null) {
	        Portfolio share = (Portfolio) session.getAttribute("share");
			viewerList = share.getViewersByType(ViewType.VIEWER);
		}
		session.setAttribute("viewers", viewerList);
		getBackPage(request, response);
		
		return mapping.findForward("success");
	}
}
