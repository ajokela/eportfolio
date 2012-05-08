/* $Name:  $ */
/* $Id: SaveAssessmentAction.java,v 1.13 2010/10/27 19:24:56 ajokela Exp $ */
package org.portfolio.client.action.assessment;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;
import org.portfolio.bus.assessment.AssessmentManager;
import org.portfolio.bus.assessment.AssessmentModelManager;
import org.portfolio.client.action.BaseAction;
import org.portfolio.model.Person;
import org.portfolio.model.PortfolioItemType;
import org.portfolio.model.assessment.Assessment;
import org.portfolio.model.assessment.AssessmentModel;
import org.portfolio.model.assessment.ScoringModel;
import org.portfolio.util.ArrayUtil;
import org.portfolio.util.RequiredInjection;

/**
 * @author Matt Sheehan
 * 
 */
public class SaveAssessmentAction extends BaseAction {

    private AssessmentModelManager assessmentModelManager;
    private AssessmentManager assessmentManager;

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String shareId = request.getParameter("shareId");
        String assessmentModelIdParam = request.getParameter("assessmentModelId");
        String assessedItemId = request.getParameter("assessedItemId");
        PortfolioItemType assessedItemType = PortfolioItemType.valueOf(request.getParameter("assessedItemType"));

        AssessmentModel assessmentModel = assessmentModelManager.getAssessmentModelById(Integer.parseInt(assessmentModelIdParam));
        request.setAttribute("assessmentModel", assessmentModel);

        if (request.getParameter("save") != null) {
            int[] scoreIndexes = getParameterValuesAsInts(request, "scoreIndex");
            String scoreType = request.getParameter("scoreType");
            saveAssessment(assessedItemId, assessedItemType, getPerson(request), scoreIndexes, assessmentModel, scoreType, shareId);
        }

        ActionRedirect actionRedirect = new ActionRedirect(mapping.findForward("success"));
        String name = assessedItemType == PortfolioItemType.PORTFOLIO ? "shareId" : "shareEntryId";
        actionRedirect.addParameter(name, assessedItemId);
        actionRedirect.addParameter("wrapperId", request.getParameter("wrapperId"));
        actionRedirect.addParameter("showAssessments", true);
        return actionRedirect;
    }

    private void saveAssessment(String assessedItemId, PortfolioItemType portfolioItemType, Person currentPerson, int[] scoreIndexes,
            AssessmentModel assessmentModel, String scoreType, String shareId) {
        String[] theScores = new String[scoreIndexes.length];
        int[] theQuantifiedScores = new int[scoreIndexes.length];

        ScoringModel scoringModel = assessmentModel.getScoringModel();
        for (int i = 0; i < scoreIndexes.length; i++) {
            theScores[i] = scoringModel.getScores()[scoreIndexes[i]];
            theQuantifiedScores[i] = scoringModel.getQuantifiedScores()[scoreIndexes[i]];
        }

        int overallScoreIndex = ArrayUtil.calculateAverage(scoreIndexes);
        String overallScore = scoringModel.getScores()[overallScoreIndex];
        int overallQuantifiedScore = ArrayUtil.calculateAverage(theQuantifiedScores);

        // Save the Assessment
        Assessment assessment = new Assessment();
        assessment.setEvaluator(currentPerson);
        assessment.setAssessedItemId(Integer.parseInt(assessedItemId));
        assessment.setAssessedItemType(portfolioItemType);
        assessment.setScoreAssignment(ArrayUtil.join(theScores, ","));
        assessment.setQuantifiedScore(ArrayUtil.join(theQuantifiedScores, ","));
        assessment.setAssessmentType(scoreType);
        assessment.setShareId(shareId);
        assessment.setOverallScore(overallScore);
        assessment.setOverallQuantifiedScore(overallQuantifiedScore);
        assessmentManager.saveAssessment(assessment);
    }

    @RequiredInjection
    public void setAssessmentModelManager(AssessmentModelManager assessmentModelManager) {
        this.assessmentModelManager = assessmentModelManager;
    }

    @RequiredInjection
    public void setAssessmentManager(AssessmentManager assessmentManager) {
        this.assessmentManager = assessmentManager;
    }
}
