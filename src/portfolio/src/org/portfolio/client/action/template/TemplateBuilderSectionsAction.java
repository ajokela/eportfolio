/* $Name:  $ */
/* $Id: TemplateBuilderSectionsAction.java,v 1.9 2011/03/14 19:37:44 ajokela Exp $ */
package org.portfolio.client.action.template;

import java.util.ArrayList;
import java.util.List;

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
import org.portfolio.model.TemplateCategory;
import org.portfolio.model.TemplateElementMapping;
import org.portfolio.model.community.Community;
import org.portfolio.util.RequiredInjection;

/**
 * @author Matt Sheehan
 * 
 */
public class TemplateBuilderSectionsAction extends DispatchAction {

	private TemplateManager templateManager;
    private CommunityManager communityManager;
    private boolean hasErrors = false;
    List<String> errors = new ArrayList<String>();
    
	public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String templateIdParam = request.getParameter("templateId");

		Template template = templateManager.getTemplateById(templateIdParam);
		request.setAttribute("template", template);
		
        Community community = communityManager.getCommunityById(template.getCommunityId().toString());
        request.setAttribute("community", community);

		List<TemplateCategory> categories = templateManager.getCategoriesByTemplateId(templateIdParam);
		request.setAttribute("categories", categories);
		
		if(hasErrors) {
			request.setAttribute("hasErrors", hasErrors);
			request.setAttribute("templateErrors", errors);
		}

		return mapping.findForward("view");
	}

	public ActionForward createSection(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String templateIdParam = request.getParameter("templateId");
		String parentCategoryIdParam = request.getParameter("parentCategoryId");
		String nameParam = request.getParameter("name");

		Template template = null;
		
		template = templateManager.getTemplateById(templateIdParam);
		
		if(nameParam.length() > 0 || !nameParam.contentEquals("")) {
			
			// communityAuthorizationManager.verifyCommunityCoordinatorAccess(getPerson(request),
			// guide.getCommunityId());
	
			TemplateCategory category = new TemplateCategory();
			category.setTitle(nameParam);
			
			if (!isEmpty(parentCategoryIdParam)) {
				category.setParentCategoryId(Integer.parseInt(parentCategoryIdParam));
			}
			
			category.setTemplateId(templateIdParam);
			templateManager.saveCategory(category);
			
			hasErrors = false;
			errors.clear();
			
		}
		else {
			
			hasErrors = true;
			
			errors.add("Oops!  Before you can create a new section or subsection, it <u>must</u> have a name.");
			
		}
		
		ActionRedirect actionRedirect = new ActionRedirect(mapping.findForward("createSection"));
		actionRedirect.addParameter("templateId", template.getId());
		
		
		return actionRedirect;
	}

	public ActionForward deleteSection(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String categoryIdParam = request.getParameter("categoryId");
		TemplateCategory category = templateManager.getCategoryById(Integer.parseInt(categoryIdParam));
		Template template = templateManager.getTemplateById(category.getTemplateId());

		// communityAuthorizationManager.verifyCommunityCoordinatorAccess(getPerson(request),
		// guide.getCommunityId());

		templateManager.deleteCategory(category);

		ActionRedirect actionRedirect = new ActionRedirect(mapping.findForward("deleteSection"));
		actionRedirect.addParameter("templateId", template.getId());
		return actionRedirect;
	}

	public ActionForward sectionUp(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String categoryIdParam = request.getParameter("categoryId");
		TemplateCategory category = templateManager.getCategoryById(Integer.parseInt(categoryIdParam));
		Template template = templateManager.getTemplateById(category.getTemplateId());

		// communityAuthorizationManager.verifyCommunityCoordinatorAccess(getPerson(request),
		// guide.getCommunityId());

		templateManager.moveCategoryUp(category);

		ActionRedirect actionRedirect = new ActionRedirect(mapping.findForward("sectionUp"));
		actionRedirect.addParameter("templateId", template.getId());
		return actionRedirect;
	}

	public ActionForward sectionDown(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String categoryIdParam = request.getParameter("categoryId");
		TemplateCategory category = templateManager.getCategoryById(Integer.parseInt(categoryIdParam));
		Template template = templateManager.getTemplateById(category.getTemplateId());

		// communityAuthorizationManager.verifyCommunityCoordinatorAccess(getPerson(request),
		// guide.getCommunityId());

		templateManager.moveCategoryDown(category);

		ActionRedirect actionRedirect = new ActionRedirect(mapping.findForward("sectionDown"));
		actionRedirect.addParameter("templateId", template.getId());
		return actionRedirect;
	}

	public ActionForward deleteElement(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String templateElementIdParam = request.getParameter("templateElementId");
		TemplateElementMapping templateElementMapping = templateManager.getTemplateElementMappingById(templateElementIdParam);

		templateManager.deleteTemplateElementMapping(templateElementMapping);

		ActionRedirect actionRedirect = new ActionRedirect(mapping.findForward("deleteElement"));
		actionRedirect.addParameter("templateId", templateElementMapping.getTemplate_id());
		return actionRedirect;
	}

	public ActionForward elementUp(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String templateElementIdParam = request.getParameter("templateElementId");
		TemplateElementMapping templateElementMapping = templateManager.getTemplateElementMappingById(templateElementIdParam);

		templateManager.moveTemplateElementMappingUp(templateElementMapping);

		ActionRedirect actionRedirect = new ActionRedirect(mapping.findForward("elementUp"));
		actionRedirect.addParameter("templateId", templateElementMapping.getTemplate_id());
		return actionRedirect;
	}

	public ActionForward elementDown(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String templateElementIdParam = request.getParameter("templateElementId");
		TemplateElementMapping templateElementMapping = templateManager.getTemplateElementMappingById(templateElementIdParam);

		templateManager.moveTemplateElementMappingDown(templateElementMapping);

		ActionRedirect actionRedirect = new ActionRedirect(mapping.findForward("elementDown"));
		actionRedirect.addParameter("templateId", templateElementMapping.getTemplate_id());
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
