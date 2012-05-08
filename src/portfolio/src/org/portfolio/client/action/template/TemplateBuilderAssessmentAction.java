/* $Name:  $ */
/* $Id: TemplateBuilderAssessmentAction.java,v 1.6 2010/10/27 19:24:56 ajokela Exp $ */
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

/**
 * @author Matt Sheehan
 * 
 */
public class TemplateBuilderAssessmentAction extends DispatchAction {

	private TemplateManager templateManager;
    private CommunityManager communityManager;
	
	public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String templateIdParam = request.getParameter("templateId");
		Template template = templateManager.getTemplateById(templateIdParam);
		request.setAttribute("template", template);
        
        Community community = communityManager.getCommunityById(template.getCommunityId().toString());
        request.setAttribute("community", community);

		return mapping.findForward("view");
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String templateIdParam = request.getParameter("templateId");
		String assessableParam = request.getParameter("assessable");
		String prevParam = request.getParameter("prev");

		Template template = templateManager.getTemplateById(templateIdParam);
        template.setAssessable("true".equals(assessableParam));
        templateManager.saveTemplate(template);
		
		String forward;
		if (!isEmpty(prevParam)) {
			forward = "prev";
		} else if ("true".equals(assessableParam)) {
			forward = "next_assignAssessment";
		} else {
			forward = "next_publish";
		}
		ActionRedirect actionRedirect = new ActionRedirect(mapping.findForward(forward));
		actionRedirect.addParameter("templateId", templateIdParam);
		return actionRedirect;
	}

    public void setTemplateManager(TemplateManager templateManager) {
        this.templateManager = templateManager;
    }

    public void setCommunityManager(CommunityManager communityManager) {
        this.communityManager = communityManager;
    }
}
