/* $Name:  $ */
/* $Id: GuideBuilderSectionsAction.java,v 1.15 2010/11/04 21:08:53 ajokela Exp $ */
package org.portfolio.client.action.wizard;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;
import org.portfolio.bus.CollectionGuideManager;
import org.portfolio.bus.community.CommunityAuthorizationManager;
import org.portfolio.bus.community.CommunityManager;
import org.portfolio.client.action.DispatchAction;
import org.portfolio.model.community.Community;
import org.portfolio.model.wizard.Category;
import org.portfolio.model.wizard.CollectionGuide;
import org.portfolio.model.wizard.WizardElementDefinition;
import org.portfolio.util.LogService;
import org.portfolio.util.RequiredInjection;

/**
 * @author Matt Sheehan
 * 
 */
public class GuideBuilderSectionsAction extends DispatchAction {

	private CommunityManager communityManager;
	private CollectionGuideManager collectionGuideManager;
	private CommunityAuthorizationManager communityAuthorizationManager;
	@SuppressWarnings("unused")
	private final LogService logService = new LogService(this.getClass());
	 
	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		String guideIdParam = request.getParameter("guideId");
		
		/*
		 * for (Enumeration e = java.sql.DriverManager.getDrivers(); e.hasMoreElements() ;) {
    System.out.print(d = (Object)e.nextElement());
		 */
		
		/*
		for(@SuppressWarnings("unchecked")
		Enumeration<String> e = request.getParameterNames(); e.hasMoreElements() ; ) {
			String param = e.nextElement();
			logService.debug("==> param => " + param + " == '" + request.getParameter(param) + "'");
		}
		
		*/
		CollectionGuide guide = collectionGuideManager.getById(Integer.parseInt(guideIdParam));
		
		if (guide != null) {
			
			communityAuthorizationManager.verifyCommunityCoordinatorAccess(getPerson(request), guide.getCommunityId());
	
			Community community = communityManager.getCommunityById(guide.getCommunityId().toString());
			request.setAttribute("community", community);
			request.setAttribute("guide", guide);
			request.setAttribute("guideFound", true);
			
		}
		else {
			request.setAttribute("guideFound", false);
		}
		
		return mapping.findForward("view");
	}

	public ActionForward createSection(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String guideIdParam = request.getParameter("guideId");
		String parentCategoryIdParam = request.getParameter("parentCategoryId");
		String nameParam = request.getParameter("name");

		CollectionGuide guide = collectionGuideManager.getById(Integer.parseInt(guideIdParam));
		ActionRedirect actionRedirect = new ActionRedirect(mapping.findForward("createSection"));
		// request.setAttribute("guideFound", false);
		
		if (guide != null) {
			
			communityAuthorizationManager.verifyCommunityCoordinatorAccess(getPerson(request), guide.getCommunityId());
	
			Category category = new Category();
			category.setTitle(nameParam);
			if (!isEmpty(parentCategoryIdParam)) {
				category.setParentCategoryId(Integer.parseInt(parentCategoryIdParam));
			}
			category.setWizardId(guide.getId());
			category.setCategories(new ArrayList<Category>());
			category.setWizardElementDefinitions(new ArrayList<WizardElementDefinition>());
			
			collectionGuideManager.saveCategory(category);
	
			actionRedirect.addParameter("guideId", guide.getId());
			request.setAttribute("guideFound", true);
		}
		else {
			request.setAttribute("guideFound", false);
		}
		
		return actionRedirect;
	}

	public ActionForward deleteSection(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String categoryIdParam = request.getParameter("categoryId");
		Category category = collectionGuideManager.getCategoryById(Integer.parseInt(categoryIdParam));

		ActionRedirect actionRedirect = new ActionRedirect(mapping.findForward("deleteSection"));

		// request.setAttribute("guideFound", false);
		
		if (category != null) {
		
			CollectionGuide guide = collectionGuideManager.getById(category.getWizardId());

			if (guide != null) {
				
				communityAuthorizationManager.verifyCommunityCoordinatorAccess(getPerson(request), guide.getCommunityId());
		
				collectionGuideManager.deleteCategory(category);
		
				actionRedirect.addParameter("guideId", guide.getId());
				request.setAttribute("guideFound", true);
				
			}
		}
		else {
			request.setAttribute("guideFound", false);
		}
		
		return actionRedirect;
	}
	
	public ActionForward renameSection(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		String categoryIdParam = request.getParameter("categoryId");
		String nameParam = request.getParameter("name"); 
		ActionRedirect actionRedirect = new ActionRedirect(mapping.findForward("renameSection"));
		
		Category category = collectionGuideManager.getCategoryById(Integer.parseInt(categoryIdParam));
		category.setTitle(nameParam);
		CollectionGuide guide = collectionGuideManager.getById(category.getWizardId());
		// request.setAttribute("guideFound", false);
		
		if (guide != null) {
			
			communityAuthorizationManager.verifyCommunityCoordinatorAccess(getPerson(request), guide.getCommunityId());
			
			collectionGuideManager.saveCategory(category);
			
			
			actionRedirect.addParameter("guideId", guide.getId());
		
			request.setAttribute("guideFound", true);
			
		}
		else {
			request.setAttribute("guideFound", false);
		}
		
		return actionRedirect;
	}

	public ActionForward sectionUp(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String categoryIdParam = request.getParameter("categoryId");
		Category category = collectionGuideManager.getCategoryById(Integer.parseInt(categoryIdParam));
		ActionRedirect actionRedirect = new ActionRedirect(mapping.findForward("sectionUp"));
		
		CollectionGuide guide = collectionGuideManager.getById(category.getWizardId());
		// request.setAttribute("guideFound", false);
		
		if ( guide != null ) {
			
			communityAuthorizationManager.verifyCommunityCoordinatorAccess(getPerson(request), guide.getCommunityId());
	
			collectionGuideManager.moveCategoryUp(category);
			
			actionRedirect.addParameter("guideId", guide.getId());
			request.setAttribute("guideFound", true);
		}
		else {
			request.setAttribute("guideFound", false);
		}
		
		return actionRedirect;
	}

	public ActionForward sectionDown(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String categoryIdParam = request.getParameter("categoryId");
		Category category = collectionGuideManager.getCategoryById(Integer.parseInt(categoryIdParam));
		ActionRedirect actionRedirect = new ActionRedirect(mapping.findForward("sectionDown"));
		
		CollectionGuide guide = collectionGuideManager.getById(category.getWizardId());
		// request.setAttribute("guideFound", false);
		
		if ( guide != null ) {
			
			communityAuthorizationManager.verifyCommunityCoordinatorAccess(getPerson(request), guide.getCommunityId());
	
			collectionGuideManager.moveCategoryDown(category);
	
			actionRedirect.addParameter("guideId", guide.getId());
			request.setAttribute("guideFound", true);
			
		}
		else {
			request.setAttribute("guideFound", false);
		}
		
		return actionRedirect;
	}

	@RequiredInjection
    public void setCommunityManager(CommunityManager communityManager) {
        this.communityManager = communityManager;
    }

    @RequiredInjection
    public void setCollectionGuideManager(CollectionGuideManager collectionGuideManager) {
        this.collectionGuideManager = collectionGuideManager;
    }

    @RequiredInjection
    public void setCommunityAuthorizationManager(CommunityAuthorizationManager communityAuthorizationManager) {
        this.communityAuthorizationManager = communityAuthorizationManager;
    }
}
