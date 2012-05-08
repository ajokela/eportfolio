/* $Name:  $ */
/* $Id: TemplateBuilderElementGroupAction.java,v 1.10 2010/10/27 19:24:56 ajokela Exp $ */
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
import org.portfolio.model.TemplateCategory;
import org.portfolio.model.TemplateElementMapping;
import org.portfolio.model.community.Community;
import org.portfolio.model.wizard.CollectionGuide;
import org.portfolio.model.wizard.WizardElementDefinition;
import org.portfolio.util.RequiredInjection;

/**
 * @author Matt Sheehan
 * 
 */
public class TemplateBuilderElementGroupAction extends DispatchAction {

    private TemplateManager templateManager;
    private CollectionGuideManager collectionGuideManager;
    private CommunityManager communityManager;

    public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String categoryIdParam = request.getParameter("categoryId");
        TemplateCategory category = templateManager.getCategoryById(Integer.parseInt(categoryIdParam));

        Template template = templateManager.getTemplateById(category.getTemplateId());

        String guideIdParam = request.getParameter("guideId");
        if (!isEmpty(guideIdParam)) {
            CollectionGuide selectedGuide = collectionGuideManager.getById(Integer.parseInt(guideIdParam));
            request.setAttribute("selectedGuide", selectedGuide);
        }

        request.setAttribute("category", category);
        request.setAttribute("template", template);

        Community community = communityManager.getCommunityById(template.getCommunityId().toString());
        request.setAttribute("community", community);

        List<CollectionGuide> collectionGuides = communityManager.getAllCommunityWizards(community.getId().toString());
        Collections.sort(collectionGuides, CollectionGuide.TITLE_COMPARATOR);
        request.setAttribute("collectionGuides", collectionGuides);
        return mapping.findForward("view");
    }

    public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String categoryIdParam = request.getParameter("categoryId");
        String[] guideElementIdParams = request.getParameterValues("guideElementId");

        TemplateCategory category = templateManager.getCategoryById(Integer.parseInt(categoryIdParam));

        if (!isEmpty(guideElementIdParams)) {
            for (String guideElementIdParam : guideElementIdParams) {
                WizardElementDefinition guideElementDef = collectionGuideManager.getCollectionGuideElementDefinitionById(Integer
                        .parseInt(guideElementIdParam));
                TemplateElementMapping templateElementMapping = new TemplateElementMapping();
                templateElementMapping.setCategoryId(category.getId());
                templateElementMapping.setWizardElementDefinition(guideElementDef);
                templateElementMapping.setTemplate_id(category.getTemplateId());
                templateManager.saveTemplateElementMapping(templateElementMapping);
            }
        }

        ActionRedirect actionRedirect = new ActionRedirect(mapping.findForward("save"));
        actionRedirect.addParameter("templateId", category.getTemplateId());
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
