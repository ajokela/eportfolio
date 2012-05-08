/* $Name:  $ */
/* $Id: AssessmentModelWindowAction.java,v 1.5 2011/03/14 19:37:44 ajokela Exp $ */
package org.portfolio.client.action.assessment;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.portfolio.bus.assessment.AssessmentModelManager;
import org.portfolio.client.action.BaseAction;
import org.portfolio.model.assessment.AssessmentModel;
import org.portfolio.util.RequiredInjection;

/**
 * @author Matt Sheehan
 * 
 */
public class AssessmentModelWindowAction extends BaseAction {

    private AssessmentModelManager assessmentModelManager;

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String assessmentModelIdParam = request.getParameter("assessmentModelId");
        AssessmentModel assessmentModel = assessmentModelManager.getAssessmentModelById(Integer.parseInt(assessmentModelIdParam));
        
        if(assessmentModel.getDescription() == null) {
        	assessmentModel.setDescription("");
        }
        
        request.setAttribute("assessmentModel", assessmentModel);
        request.setAttribute("assessmentModelCount", assessmentModel.getScoringModel().getScores().length + 1);
        
        return mapping.findForward("success");
    }

    @RequiredInjection
    public void setAssessmentModelManager(AssessmentModelManager assessmentModelManager) {
        this.assessmentModelManager = assessmentModelManager;
    }
}
