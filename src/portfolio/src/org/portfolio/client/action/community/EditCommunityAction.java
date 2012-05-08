/* $Name:  $ */
/* $Id: EditCommunityAction.java,v 1.8 2010/10/27 19:24:56 ajokela Exp $ */
package org.portfolio.client.action.community;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.portfolio.bus.community.CommunityAuthorizationManager;
import org.portfolio.bus.community.CommunityManager;
import org.portfolio.client.action.BaseAction;
import org.portfolio.model.Person;
import org.portfolio.model.community.Community;
import org.portfolio.util.RequiredInjection;

/**
 * @author Matt Sheehan
 * 
 */
public class EditCommunityAction extends BaseAction {
    
    private CommunityAuthorizationManager communityAuthorizationManager;
    private CommunityManager communityManager;

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String communityIdParam = request.getParameter("communityId");
        Person person = getPerson(request);

        communityAuthorizationManager.verifyCommunityCoordinatorAccess(person, Integer.parseInt(communityIdParam));
        
        Community community = communityManager.getCommunityById(communityIdParam);
        request.setAttribute("community", community);

        return mapping.findForward("success");
    }

    @RequiredInjection
    public void setCommunityAuthorizationManager(CommunityAuthorizationManager communityAuthorizationManager) {
        this.communityAuthorizationManager = communityAuthorizationManager;
    }

    @RequiredInjection
    public void setCommunityManager(CommunityManager communityManager) {
        this.communityManager = communityManager;
    }
    
}
