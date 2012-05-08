/* $Name:  $ */
/* $Id: CommunityAssessmentModelAction.java,v 1.9 2011/03/14 19:37:44 ajokela Exp $ */
package org.portfolio.client.action.community;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;
import org.portfolio.bus.assessment.AssessmentModelManager;
import org.portfolio.bus.community.CommunityManager;
import org.portfolio.client.action.DispatchAction;
import org.portfolio.model.assessment.AssessmentModel;
import org.portfolio.model.community.Community;
import org.portfolio.util.RequiredInjection;

/**
 * @author Tom Smith
 * 
 */
public class CommunityAssessmentModelAction extends DispatchAction {

	private CommunityManager communityManager;
	private AssessmentModelManager assessmentModelManager;

	public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
        HttpSession session = request.getSession();
		String communityIdParam = request.getParameter("communityId");
		Community community = communityManager.getCommunityById(communityIdParam);

		List<AssessmentModel> basicAssessmentModels = communityManager.getCommunityAssessmentModels(communityIdParam, "basic");
		List<AssessmentModel> outcomeAssessmentModels = communityManager.getCommunityAssessmentModels(communityIdParam, "outcome");
		List<AssessmentModel> rubricAssessmentModels = communityManager.getCommunityAssessmentModels(communityIdParam, "rubric");
		
		request.setAttribute("basicAssessmentModels", basicAssessmentModels);
		request.setAttribute("outcomeAssessmentModels", outcomeAssessmentModels);
		request.setAttribute("rubricAssessmentModels", rubricAssessmentModels);

		request.setAttribute("communityId", communityIdParam);
		request.setAttribute("community", community);
        session.removeAttribute("assessmentModelForm");
		return mapping.findForward("view");
	}

	public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String assessmentModelIdParam = request.getParameter("assessmentModelId");
		int assessmentModelId = Integer.parseInt(assessmentModelIdParam);
		
		AssessmentModel assessmentModel = assessmentModelManager.getAssessmentModelById(assessmentModelId);
		
		// TODO check authorization
		
		assessmentModelManager.deleteAssessmentModel(assessmentModel);

		ActionRedirect actionRedirect = new ActionRedirect(mapping.findForward("remove"));
		actionRedirect.addParameter("communityId", assessmentModel.getCommunityId());
		return actionRedirect;
	}

	@RequiredInjection
    public void setCommunityManager(CommunityManager communityManager) {
        this.communityManager = communityManager;
    }

    @RequiredInjection
    public void setAssessmentModelManager(AssessmentModelManager assessmentModelManager) {
        this.assessmentModelManager = assessmentModelManager;
    }
}
