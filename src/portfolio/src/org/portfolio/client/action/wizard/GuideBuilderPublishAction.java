/* $Name:  $ */
/* $Id: GuideBuilderPublishAction.java,v 1.6 2010/10/27 19:24:56 ajokela Exp $ */
package org.portfolio.client.action.wizard;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;
import org.portfolio.bus.CollectionGuideManager;
import org.portfolio.bus.community.CommunityManager;
import org.portfolio.client.action.DispatchAction;
import org.portfolio.model.community.Community;
import org.portfolio.model.wizard.CollectionGuide;
import org.portfolio.util.RequiredInjection;

/**
 * @author Matt Sheehan
 * 
 */
public class GuideBuilderPublishAction extends DispatchAction {
    
    private CommunityManager communityManager;
	private CollectionGuideManager collectionGuideManager;

	public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String guideIdParam = request.getParameter("guideId");
		CollectionGuide collectionGuide = collectionGuideManager.getById(Integer.parseInt(guideIdParam));
		request.setAttribute("guide", collectionGuide);
		
        Community community = communityManager.getCommunityById(collectionGuide.getCommunityId().toString());
        request.setAttribute("community", community);
		
		return mapping.findForward("view");
	}

	public ActionForward togglePublish(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String guideIdParam = request.getParameter("guideId");
		CollectionGuide collectionGuide = collectionGuideManager.getById(Integer.parseInt(guideIdParam));
	
		boolean isPublished = isEmpty(request.getParameter("unpublish"));
		collectionGuide.setPublished(isPublished);
		
		collectionGuideManager.saveWizard(collectionGuide);		

		ActionRedirect actionRedirect = new ActionRedirect(mapping.findForward("togglePublish"));
		actionRedirect.addParameter("guideId", collectionGuide.getId());
		return actionRedirect;
	}

	@RequiredInjection
    public void setCommunityManager(CommunityManager communityManager) {
        this.communityManager = communityManager;
    }

    @RequiredInjection
    public void setCollectionGuideManager(CollectionGuideManager collectionGuideManager) {
        this.collectionGuideManager = collectionGuideManager;
    }

}
