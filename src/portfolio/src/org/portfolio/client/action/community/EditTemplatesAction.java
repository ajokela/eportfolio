/* $Name:  $ */
/* $Id: EditTemplatesAction.java,v 1.4 2010/10/27 19:24:56 ajokela Exp $ */
package org.portfolio.client.action.community;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.portfolio.bus.community.CommunityAuthorizationManager;
import org.portfolio.bus.community.CommunityManager;
import org.portfolio.client.action.BaseAction;
import org.portfolio.model.Template;
import org.portfolio.model.community.Community;
import org.portfolio.util.RequiredInjection;

/**
 * @author Matt Sheehan
 * 
 */
public class EditTemplatesAction extends BaseAction {

    private CommunityManager communityManager;
    private CommunityAuthorizationManager communityAuthorizationManager;

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String communityIdParam = request.getParameter("communityId");
        Community community = communityManager.getCommunityById(communityIdParam);
        communityAuthorizationManager.verifyCommunityCoordinatorAccess(getPerson(request), Integer.parseInt(communityIdParam));

        List<Template> publishedTemplates = communityManager.getPublishedCommunityTemplates(communityIdParam);
        List<Template> unpublishedTemplates = communityManager.getUnpublishedCommunityTemplates(communityIdParam);

        request.setAttribute("publishedTemplates", publishedTemplates);
        request.setAttribute("unpublishedTemplates", unpublishedTemplates);
        request.setAttribute("community", community);
        return mapping.findForward("success");
    }

    @RequiredInjection
    public void setCommunityManager(CommunityManager communityManager) {
        this.communityManager = communityManager;
    }

    @RequiredInjection
    public void setCommunityAuthorizationManager(CommunityAuthorizationManager communityAuthorizationManager) {
        this.communityAuthorizationManager = communityAuthorizationManager;
    }
}
