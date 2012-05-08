/* $Name:  $ */
/* $Id: TemplateProgressDetailedSummaryReport.java,v 1.4 2011/01/06 15:51:30 ajokela Exp $ */
package org.portfolio.client.controller.community.reports;

import java.text.ParseException;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.portfolio.bus.assessment.AssessmentManager;
import org.portfolio.bus.community.CommunityAuthorizationManager;
import org.portfolio.bus.community.CommunityManager;
import org.portfolio.client.controller.ApplicationController;
import org.portfolio.dao.PortfolioHome;
import org.portfolio.model.Person;
import org.portfolio.model.assessment.Assessment;
import org.portfolio.util.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TemplateProgressDetailedSummaryReport extends ApplicationController {
	
    @Autowired private CommunityManager communityManager;
    @Autowired private CommunityAuthorizationManager communityAuthorizationManager;
    
    @Autowired private PortfolioHome shareDefinitionHome;
    @Autowired private AssessmentManager assessmentManager;
	

    
    @RequestMapping("/community/reports/templateProgressDetailedSummaryReport/{communityid}")
    public String templateProgressDetailed( @PathVariable("communityid") String communityId,
    										@RequestParam(value="report", required=false) String report,
    										@RequestParam(value="templateId", required=false) String templateId,
    										@RequestParam(value="dateFromString", required=false) String dateFromString,
    										@RequestParam(value="dateToString", required=false) String dateToString,
    							HttpServletRequest request) throws ParseException {
    
    	TPDSReport rport = new TPDSReport(communityId, report != null ? true : false, request, communityManager, communityAuthorizationManager, shareDefinitionHome, assessmentManager);
    	
    	rport.generate(templateId, dateFromString, dateToString);
    	
    	return "community/report/templateProgressDetailedSummaryReport";
    }
    

    
    public static class AssessmentData {

        private Person person;
        private Integer numAssessments;
        private List<Assessment> prelim_assessments;
        private List<Assessment> final_assessments;

        @SuppressWarnings("unused")
		private LogService logService = new LogService(this.getClass());
        
        public AssessmentData(Person person, List<Assessment> prelim_assessments, List<Assessment> final_assessments) {
            this.person = person;
            this.prelim_assessments = prelim_assessments;
            this.final_assessments = final_assessments;
        }

        public AssessmentData() {
        }

        public Person getPerson() {
            return person;
        }

        public int getNumAssessments() {
            return numAssessments;
        }

        public List<Assessment> getPrelimAssessments() {
        	return this.prelim_assessments;
        }
        
        public List<Assessment> getFinalAssessments() {
        	return this.final_assessments;
        }
        
        public double getPrelimAverage() {
        	
        	int count = 0;
        	double scores = 0.0;
        	
        	for(Iterator<Assessment> i = this.prelim_assessments.iterator(); i.hasNext();) {
        		Assessment a = i.next();
        		
        		if(a != null) {
        			count++;
        			scores += a.getOverallQuantifiedScore();
        		}
        		
        	}
        	
        	if (count > 0) {
        		return scores / (double)count;
        	}
        	
        	return 0.0;
        }
        
        public int getPrelimCount() {
        	int count = 0;
        	
        	for(Iterator<Assessment> i = this.prelim_assessments.iterator(); i.hasNext();) {
        		Assessment a = i.next();
        		
        		if(a != null) {
        			count++;
        		}
        		
        	}

        	return count;
        	
        }
        
        public double getFinalAverage() {
        	
        	int count = 0;
        	double scores = 0.0;
        	
        	for(Iterator<Assessment> i = this.final_assessments.iterator(); i.hasNext();) {
        		Assessment a = i.next();
        		
        		if(a != null) {
        			count++;
        			scores += a.getOverallQuantifiedScore();
        		}
        		
        	}
        	
        	if (count > 0) {
        		return scores / (double)count;
        	}
        	
        	return 0.0;
        }

        public int getFinalCount() {
        	int count = 0;
        	
        	for(Iterator<Assessment> i = this.final_assessments.iterator(); i.hasNext();) {
        		Assessment a = i.next();
        		
        		if(a != null) {
        			count++;
        		}
        		
        	}

        	return count;
        	
        }
        
        public void bufferPrelimAssessments(int total) {
        	if (this.prelim_assessments.size() < total) {
        		int diff = total - this.prelim_assessments.size();
        		
        		for(int i=0; i<diff; ++i) {
        			this.prelim_assessments.add(null);
        		}
        		
        	}
        }
        
        public void bufferFinalAssessments(int total) {
        	if (this.final_assessments.size() < total) {
        		int diff = total - this.final_assessments.size();
        		
        		for(int i=0; i<diff; ++i) {
        			this.final_assessments.add(null);
        		}
        		
        	}
        }
    }
    
}
