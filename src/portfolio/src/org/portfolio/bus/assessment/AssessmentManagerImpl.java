/* $Name:  $ */
/* $Id: AssessmentManagerImpl.java,v 1.15 2011/03/24 19:03:25 ajokela Exp $ */
package org.portfolio.bus.assessment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.portfolio.dao.assessment.AssessmentHome;
import org.portfolio.model.Person;
import org.portfolio.model.PortfolioItemType;
import org.portfolio.model.assessment.Assessment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("assessmentManager")
public class AssessmentManagerImpl implements AssessmentManager {

    @Autowired
    private AssessmentHome assessmentHome;

    public List<Assessment> findEvaluatorAssessments(String personId, int assessedItemId, PortfolioItemType portfolioItemType) {
        return assessmentHome.findEvaluatorAssessments(personId, assessedItemId, portfolioItemType);
    }

    public List<Assessment> findAssessmentsByItemIdAndType(int assessedItemId, PortfolioItemType portfolioItemType) {
        return assessmentHome.findAssessmentsByItemIdAndType(assessedItemId, portfolioItemType, null, null);
    }

    public void saveAssessment(Assessment assessment) {
        assessmentHome.insert(assessment);
    }

    public Assessment findAssessmentById(int id) {
        return assessmentHome.findById(id);
    }

    public List<Assessment> findLatestPortfolioAssessments(String portfolioId, String dateFrom, String dateTo) {
    	if(portfolioId != null && portfolioId.matches("^[0-9]+$")) {
	        List<Assessment> assessments = assessmentHome.findAssessmentsByItemIdAndType(
	                Integer.parseInt(portfolioId),
	                PortfolioItemType.PORTFOLIO, dateFrom, dateTo);
	        return extractLatestAssessments(assessments);
    	}
    	
    	return new ArrayList<Assessment>();
    }

    public List<Assessment> findLatestPortfolioElementAssessments(Integer shareEntryId, String dateFrom, String dateTo) {
        List<Assessment> assessments = assessmentHome.findAssessmentsByItemIdAndType(shareEntryId, PortfolioItemType.PORTFOLIO_ELEMENT, dateFrom, dateTo);
        return extractLatestAssessments(assessments);
    }

    private List<Assessment> extractLatestAssessments(List<Assessment> assessments) {
        Collections.sort(assessments, Assessment.ASSESSED_DATE_COMPARATOR);
        Collections.reverse(assessments);

        Map<Person, Assessment> latestAssessmentMap = new HashMap<Person, Assessment>();
        for (Assessment assessment : assessments) {
            latestAssessmentMap.put(assessment.getEvaluator(), assessment);
        }
        return new ArrayList<Assessment>(latestAssessmentMap.values());
    }
}
