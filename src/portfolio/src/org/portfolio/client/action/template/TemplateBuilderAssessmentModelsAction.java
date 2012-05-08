/* $Name:  $ */
/* $Id: TemplateBuilderAssessmentModelsAction.java,v 1.12 2010/10/27 19:24:56 ajokela Exp $ */
package org.portfolio.client.action.template;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;
import org.portfolio.bus.TemplateManager;
import org.portfolio.bus.assessment.AssessmentModelManager;
import org.portfolio.bus.community.CommunityManager;
import org.portfolio.client.action.DispatchAction;
import org.portfolio.model.Person;
import org.portfolio.model.PortfolioItemType;
import org.portfolio.model.Template;
import org.portfolio.model.TemplateCategory;
import org.portfolio.model.TemplateElementMapping;
import org.portfolio.model.assessment.AssessmentModel;
import org.portfolio.model.assessment.AssessmentModelAssignment;
import org.portfolio.model.community.Community;
import org.portfolio.model.community.CommunityRoleType;
import org.portfolio.util.RequiredInjection;

/**
 * @author Matt Sheehan
 * 
 */
public class TemplateBuilderAssessmentModelsAction extends DispatchAction {

	private TemplateManager templateManager;
	private AssessmentModelManager assessmentModelManager;
	private CommunityManager communityManager;

	public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String templateIdParam = request.getParameter("templateId");

		Template template = templateManager.getTemplateById(templateIdParam);
		request.setAttribute("template", template);

		List<Person> evaluators = communityManager.getMembers(template.getCommunityId().toString(), CommunityRoleType.EVALUATOR);
		request.setAttribute("evaluators", evaluators);

		List<TemplateCategory> categories = templateManager.getCategoriesByTemplateId(templateIdParam);
		request.setAttribute("categories", categories);

		List<AssessmentModel> basicAssessmentModels = communityManager.getCommunityAssessmentModels(
				template.getCommunityId().toString(),
				"basic");
		List<AssessmentModel> outcomeAssessmentModels = communityManager.getCommunityAssessmentModels(
				template.getCommunityId().toString(),
				"outcome");
		List<AssessmentModel> rubricAssessmentModels = communityManager.getCommunityAssessmentModels(
				template.getCommunityId().toString(),
				"rubric");
        
        Community community = communityManager.getCommunityById(template.getCommunityId().toString());
        request.setAttribute("community", community);

		
		request.setAttribute("basicAssessmentModels", basicAssessmentModels);
		request.setAttribute("outcomeAssessmentModels", outcomeAssessmentModels);
		request.setAttribute("rubricAssessmentModels", rubricAssessmentModels);

		return mapping.findForward("view");
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String templateIdParam = request.getParameter("templateId");

		Template template = templateManager.getTemplateById(templateIdParam);

		String pAssessmentModelParam = request.getParameter("pAssessmentModel");
		String pAssessmentAnonymousParam = request.getParameter("pAssessmentAnonymous");

		AssessmentModelAssignment templateAss = template.getAssessmentModelAssignment();
		if (isEmpty(pAssessmentModelParam)) {
			if (templateAss != null) {
				assessmentModelManager.deleteAssessmentModelAssignment(templateAss.getIdentifier());
			}
		} else {
			if (templateAss == null) {
				templateAss = new AssessmentModelAssignment();
			}
			templateAss.setAssessmentModel(assessmentModelManager.getAssessmentModelById(Integer.parseInt(pAssessmentModelParam)));
			templateAss.setAssessedItemId(Integer.parseInt(template.getId()));
			templateAss.setPortfolioItemType(PortfolioItemType.TEMPLATE);
			templateAss.setAnonymous("true".equals(pAssessmentAnonymousParam));
			assessmentModelManager.saveAssessmentModelAssignment(templateAss);
		}

		for (TemplateElementMapping tem : template.getElements()) {
			String eAssessmentModelParam = request.getParameter("eAssessmentModel_" + tem.getId());
			String eAssessmentAnonymousParam = request.getParameter("eAssessmentAnonymous_" + tem.getId());

			AssessmentModelAssignment elementAss = tem.getAssessmentModelAssignment();
			if (isEmpty(eAssessmentModelParam)) {
				if (elementAss != null) {
					assessmentModelManager.deleteAssessmentModelAssignment(elementAss.getIdentifier());
				}
			} else {
				if (elementAss == null) {
					elementAss = new AssessmentModelAssignment();
				}
				elementAss.setAssessmentModel(assessmentModelManager.getAssessmentModelById(Integer.parseInt(eAssessmentModelParam)));
				elementAss.setAssessedItemId(Integer.parseInt(tem.getId()));
				elementAss.setPortfolioItemType(PortfolioItemType.TEMPLATE_ELEMENT);
				elementAss.setAnonymous("true".equals(eAssessmentAnonymousParam));
				assessmentModelManager.saveAssessmentModelAssignment(elementAss);
			}
		}

		String mappingString = isEmpty(request.getParameter("prev")) ? "next" : "prev";
		ActionRedirect actionRedirect = new ActionRedirect(mapping.findForward(mappingString));
		actionRedirect.addParameter("templateId", template.getId());
		return actionRedirect;
	}

	@RequiredInjection
    public void setTemplateManager(TemplateManager templateManager) {
        this.templateManager = templateManager;
    }

    @RequiredInjection
    public void setAssessmentModelManager(AssessmentModelManager assessmentModelManager) {
        this.assessmentModelManager = assessmentModelManager;
    }

    @RequiredInjection
    public void setCommunityManager(CommunityManager communityManager) {
        this.communityManager = communityManager;
    }
}
