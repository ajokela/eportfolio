/* $Name:  $ */
/* $Id: AssessmentHome.java,v 1.15 2011/03/24 19:03:26 ajokela Exp $ */
package org.portfolio.dao.assessment;

import java.util.List;

import org.portfolio.model.PortfolioItemType;
import org.portfolio.model.assessment.Assessment;

/**
 * @author Tom Smith
 * 
 */
public interface AssessmentHome {

    /**
     * Insert the given assessment.
     */
    void insert(Assessment assessment);

    /**
     * Find the all of the assessments for this evaluator within the portfolio.
     */
    List<Assessment> findEvaluatorAssessments(String shareId, String personId);

    Assessment findById(int assessmentId);

    List<Assessment> findEvaluatorAssessments(String personId, int assessedItemId, PortfolioItemType portfolioItemType);

    List<Assessment> findAssessmentsByItemIdAndType(int assessedItemId, PortfolioItemType portfolioItemType, String dateFrom, String dateTo);
    
    List<Assessment> findAssessmentsByShareIdAndType(String shareId, PortfolioItemType portfolioItemType, String dateFrom, String dateTo);

}
