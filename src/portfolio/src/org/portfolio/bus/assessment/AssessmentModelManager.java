/* $Name:  $ */
/* $Id: AssessmentModelManager.java,v 1.11 2010/10/27 19:24:56 ajokela Exp $ */
package org.portfolio.bus.assessment;

import java.util.List;
import org.portfolio.model.assessment.AssessedObjective;
import org.portfolio.model.assessment.AssessmentModel;
import org.portfolio.model.assessment.AssessmentModelAssignment;
import org.portfolio.model.assessment.ScoringModel;

/**
 * 
 * @author Vijay Rajagopal Dec 5, 2008
 */
public interface AssessmentModelManager {

    /**
     * Gets all the objectives without filling the subobjectives for a community
     * 
     * @param communityId
     * @return List<AssessedObjective>
     */
    public List<AssessedObjective> getObjectivesListForCommunity(String communityId);

    /**
     * Gets the ScoringModel based on the ScoringId
     * 
     * @param scoringId
     * @return ScoringModel
     */
    public ScoringModel getScoringModel(String scoringId);

    /**
     * Gets all the scoring models for assessment model format
     */
    public List<ScoringModel> getRubricScoringModels(String amFormat);
    
    /**
     * Saves the assessment model.
     * 
     * @param assessmentModel
     * @return Integer
     */
    public Integer saveAssessmentModel(AssessmentModel assessmentModel);

    /**
     * Deletes the assessment model.
     * 
     * @param assessmentModel
     */
    public void deleteAssessmentModel(AssessmentModel assessmentModel);

    /**
     * Find an the assessment model given an assessment model Id.
     * 
     * @param assessmentModelId
     */
    public AssessmentModel getAssessmentModelById(Integer assessmentModelId);

    /**
     * @param identifier
     */
    public void deleteAssessmentModelAssignment(Integer identifier);

    /**
     * @param templateAss
     */
    public void saveAssessmentModelAssignment(AssessmentModelAssignment templateAss);
}
