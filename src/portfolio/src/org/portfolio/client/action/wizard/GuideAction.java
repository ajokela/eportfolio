/* $Name:  $ */
/* $Id: GuideAction.java,v 1.4 2010/10/27 19:24:56 ajokela Exp $ */
package org.portfolio.client.action.wizard;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;
import org.portfolio.bus.CollectionGuideManager;
import org.portfolio.bus.community.CommunityAuthorizationManager;
import org.portfolio.bus.community.CommunityManager;
import org.portfolio.client.action.DispatchAction;
import org.portfolio.model.community.Community;
import org.portfolio.model.wizard.CollectionGuide;

/**
 * @author Matt Sheehan
 * 
 */
public class GuideAction extends DispatchAction {

    private CollectionGuideManager collectionGuideManager;
    private CommunityAuthorizationManager communityAuthorizationManager;
    private CommunityManager communityManager;

    public ActionForward copy(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String guideIdParam = request.getParameter("guideId");
        CollectionGuide guide = collectionGuideManager.getById(Integer.parseInt(guideIdParam));

        communityAuthorizationManager.verifyCommunityCoordinatorAccess(getPerson(request), guide.getCommunityId());

        collectionGuideManager.copyGuide(guide, guide.getCommunityId());

        ActionRedirect actionRedirect = new ActionRedirect(mapping.findForward("success"));
        actionRedirect.addParameter("communityId", guide.getCommunityId());
        return actionRedirect;
    }

    public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String guideIdParam = request.getParameter("guideId");
        CollectionGuide guide = collectionGuideManager.getById(Integer.parseInt(guideIdParam));

        communityAuthorizationManager.verifyCommunityCoordinatorAccess(getPerson(request), guide.getCommunityId());

        collectionGuideManager.deleteGuide(guide);

        ActionRedirect actionRedirect = new ActionRedirect(mapping.findForward("success"));
        actionRedirect.addParameter("communityId", guide.getCommunityId());
        return actionRedirect;
    }

    public ActionForward importGuide(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String guideIdParam = request.getParameter("guideId");
        CollectionGuide guide = collectionGuideManager.getById(Integer.parseInt(guideIdParam));
        
        String destCommunityIdParam = request.getParameter("destCommunityId");
        Community destCommunity = communityManager.getCommunityById(destCommunityIdParam);

        communityAuthorizationManager.verifyCommunityCoordinatorAccess(getPerson(request), destCommunity.getId());
        
        Community community = communityManager.getCommunityById(guide.getCommunityId().toString());
        if (community.isPrivateCommunity()) {
            throw new SecurityException("missing required access");
        }
        
        CollectionGuide newGuide = collectionGuideManager.copyGuide(guide, destCommunity.getId());

        ActionRedirect actionRedirect = new ActionRedirect(mapping.findForward("success"));
        actionRedirect.addParameter("communityId", newGuide.getCommunityId());
        return actionRedirect;

    }

    public void setCollectionGuideManager(CollectionGuideManager collectionGuideManager) {
        this.collectionGuideManager = collectionGuideManager;
    }

    public void setCommunityAuthorizationManager(CommunityAuthorizationManager communityAuthorizationManager) {
        this.communityAuthorizationManager = communityAuthorizationManager;
    }

    public void setCommunityManager(CommunityManager communityManager) {
        this.communityManager = communityManager;
    }

}
