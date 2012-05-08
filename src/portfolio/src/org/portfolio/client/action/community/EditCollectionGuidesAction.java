/* $Name:  $ */
/* $Id: EditCollectionGuidesAction.java,v 1.9 2010/10/27 19:24:56 ajokela Exp $ */
package org.portfolio.client.action.community;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.portfolio.bus.community.CommunityAuthorizationManager;
import org.portfolio.bus.community.CommunityManager;
import org.portfolio.client.action.BaseAction;
import org.portfolio.model.community.Community;
import org.portfolio.model.wizard.CollectionGuide;
import org.portfolio.util.RequiredInjection;

/**
 * @author Matt Sheehan
 * 
 */
public class EditCollectionGuidesAction extends BaseAction {

    private CommunityManager communityManager;
    private CommunityAuthorizationManager communityAuthorizationManager;

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String communityIdParam = request.getParameter("communityId");
        Community community = communityManager.getCommunityById(communityIdParam);
        communityAuthorizationManager.verifyCommunityCoordinatorAccess(getPerson(request), Integer.parseInt(communityIdParam));

        List<CollectionGuide> publishedGuides = communityManager.getPublishedCommunityWizards(communityIdParam);
        Collections.sort(publishedGuides, CollectionGuide.TITLE_COMPARATOR);
        List<CollectionGuide> unpublishedGuides = communityManager.getUnpublishedCommunityWizards(communityIdParam);
        Collections.sort(unpublishedGuides, CollectionGuide.TITLE_COMPARATOR);

        Map<Community, List<CollectionGuide>> guideMap = new TreeMap<Community, List<CollectionGuide>>();
        List<Community> publicCommunities = communityManager.getPublicCommunities();
        for (Community publicCommunity : publicCommunities) {
            List<CollectionGuide> guides = communityManager.getPublishedCommunityWizards(publicCommunity.getId().toString());
            if (!guides.isEmpty()) {
                guideMap.put(publicCommunity, guides);
            }
        }

        request.setAttribute("guideMap", guideMap);
        request.setAttribute("publishedGuides", publishedGuides);
        request.setAttribute("unpublishedGuides", unpublishedGuides);
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
