/* $Name:  $ */
/* $Id: CreateShare1Action.java,v 1.24 2010/11/04 21:08:53 ajokela Exp $ */
/*
 * $Header: /opt/UMN-src/portfolio/src/org/portfolio/client/action/share/CreateShare1Action.java,v 1.24 2010/11/04 21:08:53 ajokela Exp $
 * $Revision: 1.24 $
 * $Date: 2010/11/04 21:08:53 $
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
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.portfolio.bus.community.CommunityManager;
import org.portfolio.client.action.BaseAction;
import org.portfolio.dao.PortfolioHome;
import org.portfolio.model.Portfolio;
import org.portfolio.model.community.Community;

public class CreateShare1Action extends BaseAction {

	private CommunityManager communityManager;
	private PortfolioHome shareDefinitionHome;
	
	public CreateShare1Action() {
		super();
		
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String shareIdParam = request.getParameter("shareId");
		String communityIdParam = request.getParameter("communityId");
		String templateIdParam = request.getParameter("templateId");

		HttpSession session = request.getSession();


		
		Portfolio share;
		if (!isEmpty(shareIdParam)) {
			// edit share
			share = shareDefinitionHome.findByShareId(shareIdParam);
			request.setAttribute("communityId", share.getTemplate().getCommunityId());
		} else if (request.getParameter("nextStep") != null && session.getAttribute("share") != null) {
			// coming from another step
			share = (Portfolio) session.getAttribute("share");
			request.setAttribute("communityId", share.getTemplate().getCommunityId());
		} else {
			// create share
			share = new Portfolio();
			if (!isEmpty(templateIdParam)) {
				share.setTemplateId(templateIdParam);
			}
			request.setAttribute("communityId", communityIdParam);
		}

		if (request.getParameter("nextStep") == null) {
			session.setAttribute("share", share);
			session.setAttribute("shareOrig", share);
			session.setAttribute("viewers", null);
		} else {
			session.setAttribute("share", session.getAttribute("shareOrig"));
		}

		request.setAttribute("communitiesByCampus", createCommunitiesByCampusMap());

		getBackPage(request, response);
		
		return mapping.findForward("success");
	}

	private Map<String, Set<Community>> createCommunitiesByCampusMap() {
		List<Community> allCommunities = communityManager.getAllCommunities();
		Map<String, Set<Community>> communitiesByCampus = new TreeMap<String, Set<Community>>(String.CASE_INSENSITIVE_ORDER);
		for (Community community : allCommunities) {
			String campusCode = community.getCampusName();
			Set<Community> communities = communitiesByCampus.get(campusCode);
			if (communities == null) {
				communities = new TreeSet<Community>();
				communitiesByCampus.put(campusCode, communities);
			}
			communities.add(community);
		}
		return communitiesByCampus;
	}

    public void setCommunityManager(CommunityManager communityManager) {
        this.communityManager = communityManager;
    }

    public void setShareDefinitionHome(PortfolioHome shareDefinitionHome) {
        this.shareDefinitionHome = shareDefinitionHome;
    }
}
