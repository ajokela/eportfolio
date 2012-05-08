/* $Name:  $ */
/* $Id: CommunityAction.java,v 1.43 2010/11/04 21:08:53 ajokela Exp $ */
package org.portfolio.client.action.community;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;
import org.portfolio.bus.community.CommunityAuthorizationManager;
import org.portfolio.bus.community.CommunityManager;
import org.portfolio.client.action.DispatchAction;
import org.portfolio.model.Person;
import org.portfolio.model.community.Community;
import org.portfolio.model.community.CommunityRoleType;
import org.portfolio.model.community.Community.CommunityType;

/**
 * @author Matt Sheehan
 * 
 */
public class CommunityAction extends DispatchAction {

    private CommunityManager communityManager;
    private CommunityAuthorizationManager communityAuthorizationManager;

    public ActionForward create(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        Person person = getPerson(request);
        if (!person.isAdmin()) {
            throw new SecurityException("missing required access");
        }
        return edit(mapping, form, request, response);
    }

    public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String communityIdParam = request.getParameter("communityId");
        if (!isEmpty(communityIdParam)) {
            Community community = communityManager.getCommunityById(communityIdParam);
            communityAuthorizationManager.verifyCommunityCoordinatorAccess(getPerson(request), community.getId());
            request.setAttribute("community", community);
        }
        request.setAttribute("campusCodeMap", getCampusCodeMap());
        request.setAttribute("types", Community.CommunityType.values());
        return mapping.findForward("edit");
    }

    private Map<String, String> getCampusCodeMap() {
        Map<String, String> campusCodeMap = new LinkedHashMap<String, String>();
        campusCodeMap.put("NONE", "ePortfolio");
        return campusCodeMap;
    }

    public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        // TODO security
        String communityIdParam = request.getParameter("communityId");
        String nameParam = request.getParameter("name");
        String descriptionParam = request.getParameter("description");
        String campusCodeParam = request.getParameter("campusCode");
        String programParam = request.getParameter("program");
        String typeParam = request.getParameter("type");
        String privateCommunityParam = request.getParameter("privateCommunity");

        Community community;
        if (isEmpty(communityIdParam)) {
            community = new Community();
        } else {
            community = communityManager.getCommunityById(communityIdParam);
        }
        community.setName(nameParam);
        community.setDescription(descriptionParam);
        community.setCampusCode(campusCodeParam);
        community.setProgram(programParam);
        community.setPrivateCommunity("true".equals(privateCommunityParam));
        community.setType(CommunityType.valueOf(typeParam));
        communityManager.saveCommunity(community, getPerson(request));

        request.setAttribute("community", community);

        ActionRedirect actionRedirect = new ActionRedirect(mapping.findForward("saved"));
        actionRedirect.addParameter("communityId", community.getId());
        return actionRedirect;
    }

    public ActionForward becomeMember(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String communityIdParam = request.getParameter("communityId");
        Person person = getPerson(request);
        communityManager.addRoleAssignment(communityIdParam, person.getPersonId(), CommunityRoleType.MEMBER);

        
        return new ActionRedirect("/community/" + communityIdParam);
    }

    public ActionForward removeMember(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    	throws Exception {

    	String communityIdParam = request.getParameter("communityId");
    	Person person = getPerson(request);

    	communityManager.removeRoleAssignment(communityIdParam, person.getPersonId(), CommunityRoleType.MEMBER);
    	
    	return new ActionRedirect("/community/directory");
    }
    
    
    public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String communityIdParam = request.getParameter("communityId");
        Person person = getPerson(request);

        communityAuthorizationManager.verifyCommunityCoordinatorAccess(person, Integer.parseInt(communityIdParam));
        communityManager.deleteCommunity(Integer.parseInt(communityIdParam));

        ActionRedirect actionRedirect = new ActionRedirect(mapping.findForward("delete"));
        return actionRedirect;
    }

    public ActionForward restore(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String communityIdParam = request.getParameter("communityId");
        Person person = getPerson(request);

        communityAuthorizationManager.verifyCommunityCoordinatorAccess(person, Integer.parseInt(communityIdParam));
        communityManager.restoreCommunity(Integer.parseInt(communityIdParam));

        ActionRedirect actionRedirect = new ActionRedirect(mapping.findForward("restore"));
        return actionRedirect;
    }

    public void setCommunityManager(CommunityManager communityManager) {
        this.communityManager = communityManager;
    }

    public void setCommunityAuthorizationManager(CommunityAuthorizationManager communityAuthorizationManager) {
        this.communityAuthorizationManager = communityAuthorizationManager;
    }
}
