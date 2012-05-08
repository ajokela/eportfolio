/* $Name:  $ */
/* $Id: AssessmentModelHome.java,v 1.12 2011/02/16 17:35:17 ajokela Exp $ */
package org.portfolio.dao.assessment;

import java.util.List;

import org.portfolio.model.assessment.AssessmentModel;
import org.portfolio.model.community.Community;

public interface AssessmentModelHome {

    int insertAssessmentModel(AssessmentModel assessmentModel);

    void updateAssessmentModel(AssessmentModel model);

    void deleteAssessmentModel(AssessmentModel assessmentModel);
    
    AssessmentModel copyAssessmentModel(AssessmentModel model, Community community, boolean copyAMObjectives);

    /**
     * Inserts Assessment Model Objectives and performance Descriptors
     * 
     * @param model
     */
    void insertAMObjectives(AssessmentModel model);

    /**
     * Deletes the assessment Objectives Not in Assessment model Deletes the
     * performance descriptors of the objectives not in assessment model When
     * the user de-selects the assessment Objectives and saves the assessment
     * model
     */
    void deleteObjectivesNotInAM(Integer assessmentModelId, String[] objectiveIds);

    /**
     * Deletes Assessment Objectives and Performance Descriptors of Assessment
     * Model
     */
    void deleteObjectivesInAM(Integer assessmentModelId);

    /**
     * Finds all Assessment Models for a community of a given format.
     */
    List<AssessmentModel> findAssessmentModelsByCommuntyAndType(String communityIdParam, String assessmentModelFormat);

    /**
     * Finds an assessment model with the given assessment model Id.
     */
    AssessmentModel findAssessmentModelById(Integer assessmentModelId);
    
    List<AssessmentModel> findAssessmentModelsByObjective(int objectiveId, String dateFrom, String dateTo);

}
