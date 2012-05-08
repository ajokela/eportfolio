/* $Name:  $ */
/* $Id: GuideBuilderDetailsAction.java,v 1.11 2010/10/27 19:24:56 ajokela Exp $ */
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
public class GuideBuilderDetailsAction extends DispatchAction {

	private CommunityManager communityManager;
	private CollectionGuideManager collectionGuideManager;
	private CommunityAuthorizationManager communityAuthorizationManager;

    public ActionForward create(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String communityIdParam = request.getParameter("communityId");
        int communityId = Integer.parseInt(communityIdParam);

        communityAuthorizationManager.verifyCommunityCoordinatorAccess(getPerson(request), communityId);

        Community community = communityManager.getCommunityById(communityIdParam);
        request.setAttribute("community", community);

        return mapping.findForward("view");
    }
    
	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String guideIdParam = request.getParameter("guideId");
		CollectionGuide guide = collectionGuideManager.getById(Integer.parseInt(guideIdParam));
		
		communityAuthorizationManager.verifyCommunityCoordinatorAccess(getPerson(request), guide.getCommunityId());
		
		Community community = communityManager.getCommunityById(guide.getCommunityId().toString());
		request.setAttribute("community", community);
		request.setAttribute("guide", guide);
		return mapping.findForward("view");
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
        String communityIdParam = request.getParameter("communityId");
		String guideIdParam = request.getParameter("guideId");
		String descriptionParam = request.getParameter("description");
		String textTipParam = request.getParameter("textTip");
		String shareTipParam = request.getParameter("shareTip");
		String prevParam = request.getParameter("prev");
        String nameParam = request.getParameter("name");
        int communityId = Integer.parseInt(communityIdParam);
        
		communityAuthorizationManager.verifyCommunityCoordinatorAccess(getPerson(request), communityId);

        CollectionGuide guide;
        if (isEmpty(guideIdParam)) {
            guide = new CollectionGuide();
            guide.setCommunityId(communityId);
        } else {
            guide = collectionGuideManager.getById(Integer.parseInt(guideIdParam));
        }
        guide.setTitle(nameParam);
		guide.setDescription(descriptionParam);
		guide.setTip(textTipParam);
		guide.setShareTip(shareTipParam);

		collectionGuideManager.saveWizard(guide);

		ActionRedirect actionRedirect = new ActionRedirect(mapping.findForward(isEmpty(prevParam) ? "next" : "prev"));
		actionRedirect.addParameter("guideId", guide.getId());
		return actionRedirect;
	}

    public void setCommunityManager(CommunityManager communityManager) {
        this.communityManager = communityManager;
    }

    public void setCollectionGuideManager(CollectionGuideManager collectionGuideManager) {
        this.collectionGuideManager = collectionGuideManager;
    }

    public void setCommunityAuthorizationManager(CommunityAuthorizationManager communityAuthorizationManager) {
        this.communityAuthorizationManager = communityAuthorizationManager;
    }
}
