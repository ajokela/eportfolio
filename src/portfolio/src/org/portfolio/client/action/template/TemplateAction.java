/* $Name:  $ */
/* $Id: TemplateAction.java,v 1.4 2010/10/27 19:24:56 ajokela Exp $ */
package org.portfolio.client.action.template;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;
import org.portfolio.bus.TemplateManager;
import org.portfolio.bus.community.CommunityAuthorizationManager;
import org.portfolio.client.action.DispatchAction;
import org.portfolio.model.Template;

/**
 * @author Matt Sheehan
 * 
 */
public class TemplateAction extends DispatchAction {

    private TemplateManager templateManager;
    private CommunityAuthorizationManager communityAuthorizationManager;

    public ActionForward copy(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String templateIdParam = request.getParameter("templateId");
        Template template = templateManager.getTemplateById(templateIdParam);

        communityAuthorizationManager.verifyCommunityCoordinatorAccess(getPerson(request), template.getCommunityId());

        templateManager.copyTemplate(template);

        ActionRedirect actionRedirect = new ActionRedirect(mapping.findForward("success"));
        return actionRedirect.addParameter("communityId", template.getCommunityId());

    }

    public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String templateIdParam = request.getParameter("templateId");
        Template template = templateManager.getTemplateById(templateIdParam);

        communityAuthorizationManager.verifyCommunityCoordinatorAccess(getPerson(request), template.getCommunityId());

        templateManager.deleteTemplate(template);

        ActionRedirect actionRedirect = new ActionRedirect(mapping.findForward("success"));
        return actionRedirect.addParameter("communityId", template.getCommunityId());
    }

    public void setCommunityAuthorizationManager(CommunityAuthorizationManager communityAuthorizationManager) {
        this.communityAuthorizationManager = communityAuthorizationManager;
    }

    public void setTemplateManager(TemplateManager templateManager) {
        this.templateManager = templateManager;
    }
}
