/* $Name:  $ */
/* $Id: CommunityReportsAction.java,v 1.4 2010/10/27 19:24:56 ajokela Exp $ */
package org.portfolio.client.action.community;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.portfolio.bus.community.CommunityAuthorizationManager;
import org.portfolio.bus.community.CommunityManager;
import org.portfolio.client.action.BaseAction;
import org.portfolio.model.community.Community;

/**
 * @author Matt Sheehan
 * 
 */
public class CommunityReportsAction extends BaseAction {

    private CommunityManager communityManager;
    private CommunityAuthorizationManager communityAuthorizationManager;

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String communityIdParam = request.getParameter("communityId");
        Community community = communityManager.getCommunityById(communityIdParam);
        // TODO Auto-generated method stub
        request.setAttribute("community", community);
        request.setAttribute("hasSummaryReportAccess", communityAuthorizationManager.hasAssessmentCoordinatorAccess(
                getPerson(request),
                community.getId()));
        return mapping.findForward("success");
    }

    public void setCommunityManager(CommunityManager communityManager) {
        this.communityManager = communityManager;
    }

    public void setCommunityAuthorizationManager(CommunityAuthorizationManager communityAuthorizationManager) {
        this.communityAuthorizationManager = communityAuthorizationManager;
    }

}
