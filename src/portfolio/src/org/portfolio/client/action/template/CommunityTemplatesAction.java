/* $Name:  $ */
/* $Id: CommunityTemplatesAction.java,v 1.5 2010/10/27 19:24:56 ajokela Exp $ */
package org.portfolio.client.action.template;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.portfolio.bus.community.CommunityManager;
import org.portfolio.client.action.BaseAction;
import org.portfolio.model.Template;

/**
 * @author Matt Sheehan
 * 
 */
public class CommunityTemplatesAction extends BaseAction {

    private CommunityManager communityManager;

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String communityIdParam = request.getParameter("communityId");

        List<Template> templates = communityManager.getPublishedCommunityTemplates(communityIdParam);
        Collections.sort(templates, Template.NAME_ORDER);
        request.setAttribute("templates", templates);

        return mapping.findForward("success");
    }

    public void setCommunityManager(CommunityManager communityManager) {
        this.communityManager = communityManager;
    }
}
