/* $Name:  $ */
/* $Id: GuideBuilderElementsAction.java,v 1.15 2010/11/04 21:08:53 ajokela Exp $ */
package org.portfolio.client.action.wizard;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;
import org.portfolio.bus.CollectionGuideManager;
import org.portfolio.bus.ElementDefinitionManager;
import org.portfolio.bus.community.CommunityAuthorizationManager;
import org.portfolio.bus.community.CommunityManager;
import org.portfolio.client.action.DispatchAction;
import org.portfolio.model.ElementSource;
import org.portfolio.model.community.Community;
import org.portfolio.model.wizard.Category;
import org.portfolio.model.wizard.CollectionGuide;
import org.portfolio.model.wizard.WizardElementDefinition;
import org.portfolio.util.RequiredInjection;

/**
 * @author Matt Sheehan
 * 
 */
public class GuideBuilderElementsAction extends DispatchAction {

    private CollectionGuideManager collectionGuideManager;
    private CommunityAuthorizationManager communityAuthorizationManager;
    private ElementDefinitionManager elementDefinitionHome;
    private CommunityManager communityManager;
    

    public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String categoryIdParam = request.getParameter("categoryId");
        String guideElementIdParam = request.getParameter("guideElementId");

        if (!isEmpty(guideElementIdParam)) {
            WizardElementDefinition selectedDefiniton = collectionGuideManager.getCollectionGuideElementDefinitionById(Integer
                    .parseInt(guideElementIdParam));
            request.setAttribute("selectedDefiniton", selectedDefiniton);
        }

        List<ElementSource> elementSources = elementDefinitionHome.findAllElementSources();
        request.setAttribute("elementSources", elementSources);

        Category category = collectionGuideManager.getCategoryById(Integer.parseInt(categoryIdParam));
        CollectionGuide guide = collectionGuideManager.getById(category.getWizardId());

        Community community = communityManager.getCommunityById(guide.getCommunityId().toString());
        request.setAttribute("community", community);

        communityAuthorizationManager.verifyCommunityCoordinatorAccess(getPerson(request), guide.getCommunityId());

        request.setAttribute("guide", guide);
        request.setAttribute("category", category);
        return mapping.findForward("view");
    }

    public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
    	
    	/*
		for(@SuppressWarnings("unchecked")
			Enumeration<String> e = request.getParameterNames(); e.hasMoreElements() ; ) {
				String param = e.nextElement();
				logService.debug("==> param => " + param + " == '" + request.getParameter(param) + "'");
		}
		*/	
    	
        String guideElementIdParam = request.getParameter("guideElementId");

        WizardElementDefinition selectedDefiniton = collectionGuideManager.getCollectionGuideElementDefinitionById(Integer
                .parseInt(guideElementIdParam));
        CollectionGuide collectionGuide = collectionGuideManager.getById(selectedDefiniton.getWizardId());
        communityAuthorizationManager.verifyCommunityCoordinatorAccess(getPerson(request), collectionGuide.getCommunityId());

        collectionGuideManager.deleteCollectionGuideElementDefinition(selectedDefiniton);

        ActionRedirect actionRedirect = new ActionRedirect(mapping.findForward("delete"));
        actionRedirect.addParameter("categoryId", selectedDefiniton.getCategoryId());
        return actionRedirect;
    }

    public ActionForward up(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String guideElementIdParam = request.getParameter("guideElementId");

        WizardElementDefinition selectedDefiniton = collectionGuideManager.getCollectionGuideElementDefinitionById(Integer
                .parseInt(guideElementIdParam));
        CollectionGuide collectionGuide = collectionGuideManager.getById(selectedDefiniton.getWizardId());
        communityAuthorizationManager.verifyCommunityCoordinatorAccess(getPerson(request), collectionGuide.getCommunityId());

        collectionGuideManager.moveCollectionGuideElementDefinitionUp(selectedDefiniton);

        ActionRedirect actionRedirect = new ActionRedirect(mapping.findForward("up"));
        actionRedirect.addParameter("categoryId", selectedDefiniton.getCategoryId());
        return actionRedirect;
    }

    public ActionForward down(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String guideElementIdParam = request.getParameter("guideElementId");

        WizardElementDefinition selectedDefiniton = collectionGuideManager.getCollectionGuideElementDefinitionById(Integer
                .parseInt(guideElementIdParam));
        CollectionGuide collectionGuide = collectionGuideManager.getById(selectedDefiniton.getWizardId());
        communityAuthorizationManager.verifyCommunityCoordinatorAccess(getPerson(request), collectionGuide.getCommunityId());

        collectionGuideManager.moveCollectionGuideElementDefinitionDown(selectedDefiniton);

        ActionRedirect actionRedirect = new ActionRedirect(mapping.findForward("down"));
        actionRedirect.addParameter("categoryId", selectedDefiniton.getCategoryId());
        return actionRedirect;
    }

    public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String categoryIdParam = request.getParameter("categoryId");
        int categoryId = Integer.parseInt(categoryIdParam);
        String guideElementIdParam = request.getParameter("guideElementId");

        Category category = collectionGuideManager.getCategoryById(categoryId);

        WizardElementDefinition definition;
        if (isEmpty(guideElementIdParam)) {
            definition = new WizardElementDefinition();
        } else {
            definition = collectionGuideManager.getCollectionGuideElementDefinitionById(Integer.parseInt(guideElementIdParam));
        }
        String systemTagsParam = request.getParameter("systemTags");
        String[] systemTags = isEmpty(systemTagsParam) ? new String[0] : systemTagsParam.trim().split("\\s*,\\s*");
        definition.setAutoImport("yes".equals(request.getParameter("autoImport")));
        definition.setCategoryId(categoryId);
        definition.setDescription(request.getParameter("description"));
        definition.setElementDefinitionId(request.getParameter("elementType"));
        definition.setKeywords(Arrays.asList(systemTags));
        definition.setRequired("yes".equals(request.getParameter("required")));
        definition.setTitle(request.getParameter("name"));
        definition.setWizardId(category.getWizardId());

        CollectionGuide collectionGuide = collectionGuideManager.getById(definition.getWizardId());
        communityAuthorizationManager.verifyCommunityCoordinatorAccess(getPerson(request), collectionGuide.getCommunityId());

        collectionGuideManager.saveCollectionGuideElementDefinition(definition);

        ActionRedirect actionRedirect = new ActionRedirect(mapping.findForward("save"));
        actionRedirect.addParameter("categoryId", definition.getCategoryId());
        return actionRedirect;
    }

    @RequiredInjection
    public void setCollectionGuideManager(CollectionGuideManager collectionGuideManager) {
        this.collectionGuideManager = collectionGuideManager;
    }

    @RequiredInjection
    public void setCommunityAuthorizationManager(CommunityAuthorizationManager communityAuthorizationManager) {
        this.communityAuthorizationManager = communityAuthorizationManager;
    }

    @RequiredInjection
    public void setElementDefinitionHome(ElementDefinitionManager elementDefinitionHome) {
        this.elementDefinitionHome = elementDefinitionHome;
    }

    @RequiredInjection
    public void setCommunityManager(CommunityManager communityManager) {
        this.communityManager = communityManager;
    }
}
