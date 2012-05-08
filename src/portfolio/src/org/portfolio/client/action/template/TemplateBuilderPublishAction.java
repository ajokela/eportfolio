/* $Name:  $ */
/* $Id: TemplateBuilderPublishAction.java,v 1.5 2010/10/27 19:24:56 ajokela Exp $ */
package org.portfolio.client.action.template;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;
import org.portfolio.bus.TemplateManager;
import org.portfolio.bus.community.CommunityManager;
import org.portfolio.client.action.DispatchAction;
import org.portfolio.model.Template;
import org.portfolio.model.community.Community;
import org.portfolio.util.RequiredInjection;

/**
 * @author Matt Sheehan
 * 
 */
public class TemplateBuilderPublishAction extends DispatchAction {

	private TemplateManager templateManager;
    private CommunityManager communityManager;

	public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String templateIdParam = request.getParameter("templateId");
		Template template = templateManager.getTemplateById(templateIdParam);
        
        Community community = communityManager.getCommunityById(template.getCommunityId().toString());
        request.setAttribute("community", community);

		request.setAttribute("template", template);
		return mapping.findForward("view");
	}

	public ActionForward togglePublish(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String templateIdParam = request.getParameter("templateId");
		Template template = templateManager.getTemplateById(templateIdParam);

		boolean isPublished = isEmpty(request.getParameter("unpublish"));
		template.setPublished(isPublished);
		templateManager.saveTemplate(template);

		ActionRedirect actionRedirect = new ActionRedirect(mapping.findForward("togglePublish"));
		actionRedirect.addParameter("templateId", templateIdParam);
		return actionRedirect;
	}

	@RequiredInjection
    public void setTemplateManager(TemplateManager templateManager) {
        this.templateManager = templateManager;
    }

    @RequiredInjection
    public void setCommunityManager(CommunityManager communityManager) {
        this.communityManager = communityManager;
    }
}
