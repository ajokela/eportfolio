/* $Name:  $ */
/* $Id: AssessmentModelManagerImpl.java,v 1.14 2010/10/27 19:24:56 ajokela Exp $ */
package org.portfolio.bus.assessment;

import java.util.ArrayList;
import java.util.List;

import org.portfolio.dao.assessment.AssessmentModelAssignmentHome;
import org.portfolio.dao.assessment.AssessmentModelHome;
import org.portfolio.dao.assessment.ObjectiveHome;
import org.portfolio.dao.assessment.ScoringModelHome;
import org.portfolio.model.assessment.AssessedObjective;
import org.portfolio.model.assessment.AssessmentModel;
import org.portfolio.model.assessment.AssessmentModelAssignment;
import org.portfolio.model.assessment.ScoringModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("assessmentModelManager")
public class AssessmentModelManagerImpl implements AssessmentModelManager {

    @Autowired
    private ObjectiveHome objectiveHome;
    @Autowired
    private ScoringModelHome scoringModelHome;
    @Autowired
    private AssessmentModelHome assessmentModelHome;
    @Autowired
    private AssessmentModelAssignmentHome assessmentModelAssignmentHome;

    /**
     * Gets all the objectives without filling the subobjectives for a community
     */
    public List<AssessedObjective> getObjectivesListForCommunity(String communityId) {
        return objectiveHome.getAssessedObjectivesForCommunity(communityId);
    }

    public ScoringModel getScoringModel(String scoringId) {
        return scoringModelHome.find(scoringId);
    }
    
    /**
     * Gets all the scoring models for assessmentModel format
     */
    public List<ScoringModel> getRubricScoringModels(String amFormat){
        List<ScoringModel> scoringModels = scoringModelHome.find();
        List<ScoringModel> result = new ArrayList<ScoringModel>();
        for(ScoringModel model : scoringModels){
            if(model.isVisibleInAM(amFormat)){
                result.add(model);
            }
        }
        return result;
    }

    /**
     * Saves the Assessment Model
     * @param assessmentModel
     * @return Assessment Model Identifier
     */
    public Integer saveAssessmentModel(AssessmentModel assessmentModel) {
        if (assessmentModel.getId() == null) {
            assessmentModelHome.insertAssessmentModel(assessmentModel);
        } else {
            assessmentModelHome.updateAssessmentModel(assessmentModel);
        }
        return assessmentModel.getId();
    }

    public void deleteAssessmentObjectives(String assessmentModelId, String[] objectiveIds) {
    }
    
    
	@Transactional
	public void deleteAssessmentModel(AssessmentModel assessmentModel) {
		assessmentModelHome.deleteAssessmentModel(assessmentModel);
	}

    public AssessmentModel getAssessmentModelById(Integer assessmentModelId) {
        return assessmentModelHome.findAssessmentModelById(assessmentModelId);
    }

	public void deleteAssessmentModelAssignment(Integer identifier) {
		assessmentModelAssignmentHome.delete(identifier);
	}

	public void saveAssessmentModelAssignment(AssessmentModelAssignment templateAss) {
		if (templateAss.getIdentifier() == null) {
			assessmentModelAssignmentHome.insert(templateAss);
		} else {
			assessmentModelAssignmentHome.update(templateAss);
		}
	}
}
