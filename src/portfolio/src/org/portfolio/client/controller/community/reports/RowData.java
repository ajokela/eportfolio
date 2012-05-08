package org.portfolio.client.controller.community.reports;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.portfolio.model.Person;
import org.portfolio.model.Viewer;
import org.portfolio.model.assessment.Assessment;

public class RowData {
    private final Object assessable;
    private final Map<Person, Assessment> prelimAssessments;
    private final Map<Person, Assessment> finalAssessments;
    private final List<Viewer> evaluators;

    public RowData(Object assessable, List<Assessment> assessments, List<Viewer> evaluators) {
        this.assessable = assessable;
        this.evaluators = evaluators;

        this.prelimAssessments = new LinkedHashMap<Person, Assessment>();
        this.finalAssessments  = new LinkedHashMap<Person, Assessment>();
        
        for (Assessment assessment : assessments) {
        	
        	if (!assessment.isFinal()) {
        		this.prelimAssessments.put(assessment.getEvaluator(), assessment);
        	}
        	
        	if (assessment.isFinal()) {
        		this.finalAssessments.put(assessment.getEvaluator(), assessment);
        	}
        }
    }

    public Object getAssessable() {
        return assessable;
    }

    public Map<Person, Assessment> getPrelimAssessments() {
        return prelimAssessments;
    }

    public Map<Person, Assessment> getFinalAssessments() {
        return finalAssessments;
    }

    public List<Viewer> getEvaluators() {
        return evaluators;
    }
}
