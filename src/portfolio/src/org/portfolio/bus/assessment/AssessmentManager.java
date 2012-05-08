/* $Name:  $ */
/* $Id: AssessmentManager.java,v 1.15 2011/03/24 19:03:25 ajokela Exp $ */
package org.portfolio.bus.assessment;

import java.util.List;

import org.portfolio.model.PortfolioItemType;
import org.portfolio.model.assessment.Assessment;

public interface AssessmentManager {

    void saveAssessment(Assessment assessment);

    Assessment findAssessmentById(int id);

    List<Assessment> findEvaluatorAssessments(String personId, int assessedItemId, PortfolioItemType portfolioItemType);

    List<Assessment> findAssessmentsByItemIdAndType(int assessedItemId, PortfolioItemType portfolioItemType);

    List<Assessment> findLatestPortfolioAssessments(String portfolioId, String dateFrom, String dateTo);

    List<Assessment> findLatestPortfolioElementAssessments(Integer shareEntryId, String dateFrom, String dateTo);
}
