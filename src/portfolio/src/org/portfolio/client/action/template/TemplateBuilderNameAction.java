/* $Name:  $ */
/* $Id: TemplateBuilderNameAction.java,v 1.11 2010/10/27 19:24:56 ajokela Exp $ */
package org.portfolio.client.action.template;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;
import org.portfolio.bus.CollectionGuideManager;
import org.portfolio.bus.TemplateManager;
import org.portfolio.bus.community.CommunityManager;
import org.portfolio.client.action.DispatchAction;
import org.portfolio.model.Template;
import org.portfolio.model.community.Community;
import org.portfolio.model.wizard.CollectionGuide;
import org.portfolio.util.RequiredInjection;

/**
 * @author Matt Sheehan
 * 
 */
public class TemplateBuilderNameAction extends DispatchAction {

	private TemplateManager templateManager;
	private CollectionGuideManager collectionGuideManager;
	private CommunityManager communityManager;

	public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String communityIdParam = request.getParameter("communityId");
		String templateIdParam = request.getParameter("templateId");

		if (!isEmpty(templateIdParam)) {
			Template template = templateManager.getTemplateById(templateIdParam);
			request.setAttribute("template", template);
			communityIdParam = template.getCommunityId().toString();
		}
		
		Community community = communityManager.getCommunityById(communityIdParam);
		request.setAttribute("community", community);

		List<CollectionGuide> collectionGuides = communityManager.getAllCommunityWizards(communityIdParam);
		Collections.sort(collectionGuides, CollectionGuide.TITLE_COMPARATOR);
		request.setAttribute("collectionGuides", collectionGuides);

		return mapping.findForward("view");
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String communityIdParam = request.getParameter("communityId");
		String templateIdParam = request.getParameter("templateId");
		String nameParam = request.getParameter("name");
		String descriptionParam = request.getParameter("description");

		Template template;
		if (isEmpty(templateIdParam)) {
			template = new Template();
			template.setCommunityId(Integer.parseInt(communityIdParam));
		} else {
			template = templateManager.getTemplateById(templateIdParam);
		}
		template.setName(nameParam);
		template.setDescription(descriptionParam);

		templateManager.saveTemplate(template);

		if (isEmpty(templateIdParam) && "true".equals(request.getParameter("guideBase"))) {
			String guideIdParam = request.getParameter("guideId");
			CollectionGuide guide = collectionGuideManager.getById(Integer.parseInt(guideIdParam));
			templateManager.copyCollectionGuide(template, guide);
		}

		ActionRedirect actionRedirect = new ActionRedirect(mapping.findForward("next"));
		actionRedirect.addParameter("templateId", template.getId());
		return actionRedirect;
	}

	@RequiredInjection
    public void setTemplateManager(TemplateManager templateManager) {
        this.templateManager = templateManager;
    }

    @RequiredInjection
    public void setCollectionGuideManager(CollectionGuideManager collectionGuideManager) {
        this.collectionGuideManager = collectionGuideManager;
    }

    @RequiredInjection
    public void setCommunityManager(CommunityManager communityManager) {
        this.communityManager = communityManager;
    }
}
