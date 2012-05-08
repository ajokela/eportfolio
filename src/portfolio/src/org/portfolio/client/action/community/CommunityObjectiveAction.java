/* $Name:  $ */
/* $Id: CommunityObjectiveAction.java,v 1.19 2010/10/27 19:24:56 ajokela Exp $ */
package org.portfolio.client.action.community;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;
import org.portfolio.bus.assessment.ObjectiveManager;
import org.portfolio.bus.community.CommunityAuthorizationManager;
import org.portfolio.bus.community.CommunityManager;
import org.portfolio.client.action.DispatchAction;
import org.portfolio.model.assessment.Objective;
import org.portfolio.model.community.Community;

/**
 * @author Matt Sheehan
 * 
 */
public class CommunityObjectiveAction extends DispatchAction {

    private CommunityManager communityManager;
    private ObjectiveManager objectiveManager;
    private CommunityAuthorizationManager communityAuthorizationManager;

    public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String communityIdParam = request.getParameter("communityId");
        Community community = communityManager.getCommunityById(communityIdParam);
        List<Objective> objectiveSets = communityManager.getCommunityObjectiveSets(communityIdParam);
        Collections.sort(objectiveSets, Objective.NAME_ORDER);
        request.setAttribute("objectiveSets", objectiveSets);
        request.setAttribute("community", community);

        Map<Community, List<Objective>> objectiveSetMap = new TreeMap<Community, List<Objective>>();
        List<Community> publicCommunities = communityManager.getPublicCommunities();
        for (Community publicCommunity : publicCommunities) {
            List<Objective> communityObjectiveSets = communityManager.getCommunityObjectiveSets(publicCommunity.getId().toString());
            if (!communityObjectiveSets.isEmpty()) {
                objectiveSetMap.put(publicCommunity, communityObjectiveSets);
            }
        }

        request.setAttribute("objectiveSetMap", objectiveSetMap);
        return mapping.findForward("view");
    }

    public ActionForward viewSet(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String objectiveSetIdParam = request.getParameter("objectiveSetId");

        Objective objectiveSet = objectiveManager.getObjectiveById(Integer.parseInt(objectiveSetIdParam));
        Community community = communityManager.getCommunityById(objectiveSet.getCommunityId().toString());
        
        request.setAttribute("objectiveSet", objectiveSet);
        request.setAttribute("community", community);
        return mapping.findForward("viewSet");
        
    }

    public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String communityIdParam = request.getParameter("communityId");
        String nameParam = request.getParameter("name");
        String descriptionParam = request.getParameter("description");
        String parentIdParam = request.getParameter("parentId");

        Date now = new Date();

        Objective objective = new Objective();
        objective.setCommunityId(Integer.parseInt(communityIdParam));
        objective.setDescription(descriptionParam);
        objective.setDateCreated(now);
        objective.setDateModified(now);
        objective.setName(nameParam);
        if (!isEmpty(parentIdParam)) {
            objective.setParentId(Integer.parseInt(parentIdParam));
        }
        objective.setSubObjectives(new ArrayList<Objective>());
        objectiveManager.saveObjective(objective);

        ActionRedirect actionRedirect = new ActionRedirect(mapping.findForward("add"));
        actionRedirect.addParameter("communityId", communityIdParam);
        return actionRedirect;
    }

    public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String objectiveIdParam = request.getParameter("objectiveId");
        int objectiveId = Integer.parseInt(objectiveIdParam);

        Objective objective = objectiveManager.getObjectiveById(objectiveId);

        // TODO check authorization

        objectiveManager.deleteObjective(objective);

        ActionRedirect actionRedirect = new ActionRedirect(mapping.findForward("delete"));
        actionRedirect.addParameter("communityId", objective.getCommunityId());
        return actionRedirect;
    }

    public ActionForward up(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String objectiveIdParam = request.getParameter("objectiveId");
        int objectiveId = Integer.parseInt(objectiveIdParam);

        Objective objective = objectiveManager.getObjectiveById(objectiveId);

        // TODO check authorization

        objectiveManager.moveObjectiveUp(objective);

        ActionRedirect actionRedirect = new ActionRedirect(mapping.findForward("up"));
        actionRedirect.addParameter("communityId", objective.getCommunityId());
        return actionRedirect;

    }

    public ActionForward down(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String objectiveIdParam = request.getParameter("objectiveId");
        int objectiveId = Integer.parseInt(objectiveIdParam);

        Objective objective = objectiveManager.getObjectiveById(objectiveId);

        // TODO check authorization

        objectiveManager.moveObjectiveDown(objective);

        ActionRedirect actionRedirect = new ActionRedirect(mapping.findForward("down"));
        actionRedirect.addParameter("communityId", objective.getCommunityId());
        return actionRedirect;

    }

    public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String objectiveIdParam = request.getParameter("objectiveId");
        String nameParam = request.getParameter("name");
        String descriptionParam = request.getParameter("description");

        Objective objective = objectiveManager.getObjectiveById(Integer.parseInt(objectiveIdParam));
        objective.setDescription(descriptionParam);
        objective.setDateModified(new Date());
        objective.setName(nameParam);
        objectiveManager.saveObjective(objective);

        ActionRedirect actionRedirect = new ActionRedirect(mapping.findForward("edit"));
        actionRedirect.addParameter("communityId", objective.getCommunityId());
        return actionRedirect;

    }

    public ActionForward reorder(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String parentIdParam = request.getParameter("parentId");
        String childIdsParam = request.getParameter("childIds");
        List<String> childIds = Arrays.asList(childIdsParam.split(","));

        Objective parent = objectiveManager.getObjectiveById(Integer.parseInt(parentIdParam));
        List<Objective> subObjectives = parent.getSubObjectives();
        for (Objective subobj : subObjectives) {
            subobj.setOrder(childIds.indexOf("" + subobj.getId()) + 1);
        }
        objectiveManager.saveObjectives(subObjectives);
        return null;
    }

    public ActionForward copy(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String idParam = request.getParameter("id");
        Objective objective = objectiveManager.getObjectiveById(Integer.parseInt(idParam));
        // TODO !!!!!!!!
        ActionRedirect actionRedirect = new ActionRedirect(mapping.findForward("edit"));
        actionRedirect.addParameter("communityId", objective.getCommunityId());
        return actionRedirect;
    }

    public ActionForward importSet(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String destCommunityIdParam = request.getParameter("destCommunityId");
        String objectiveIdParam = request.getParameter("objectiveId");

        Community destCommunity = communityManager.getCommunityById(destCommunityIdParam);
        communityAuthorizationManager.verifyCommunityCoordinatorAccess(getPerson(request), destCommunity.getId());

        Objective objective = objectiveManager.getObjectiveById(Integer.parseInt(objectiveIdParam));

        Community origCommunity = communityManager.getCommunityById(objective.getCommunityId().toString());
        if (origCommunity.isPrivateCommunity()) {
            throw new SecurityException("missing required access");
        }

        objectiveManager.copyObjectiveSet(objective, destCommunity.getId());

        ActionRedirect actionRedirect = new ActionRedirect(mapping.findForward("edit"));
        actionRedirect.addParameter("communityId", destCommunityIdParam);
        return actionRedirect;
    }

    public void setCommunityManager(CommunityManager communityManager) {
        this.communityManager = communityManager;
    }

    public void setObjectiveManager(ObjectiveManager objectiveManager) {
        this.objectiveManager = objectiveManager;
    }

    public void setCommunityAuthorizationManager(CommunityAuthorizationManager communityAuthorizationManager) {
        this.communityAuthorizationManager = communityAuthorizationManager;
    }
}
