/* $Name:  $ */
/* $Id: AssessmentModelAssignmentHome.java,v 1.9 2011/02/16 17:35:17 ajokela Exp $ */
package org.portfolio.dao.assessment;

import java.util.List;

import org.portfolio.model.PortfolioItemType;
import org.portfolio.model.assessment.AssessmentModel;
import org.portfolio.model.assessment.AssessmentModelAssignment;
import org.portfolio.model.community.Community;

/**
 * @author Tom Smith
 * 
 */
public interface AssessmentModelAssignmentHome {

	int insert(AssessmentModelAssignment assessmentModelAssignment);

    void update(AssessmentModelAssignment assessmentModelAssignment);

	void delete(int assessmentModelAssignmentId);

    void deleteByItemIdAndItemType(String shareId, PortfolioItemType portfolio);
    
    AssessmentModelAssignment copyAssessmentModelAssignment(AssessmentModelAssignment ama, Community community, boolean copyAMObjectives);
    
    List<AssessmentModelAssignment> findByAssessmentModel(AssessmentModel am, PortfolioItemType portfolioItemType);

	AssessmentModelAssignment findByAssessedItemIdAndPortfolioItemType(int assessedItemId, PortfolioItemType portfolioItemType);
}
