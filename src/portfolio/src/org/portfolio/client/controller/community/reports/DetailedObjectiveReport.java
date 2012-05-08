/* $Name:  $ */
/* $Id: DetailedObjectiveReport.java,v 1.2 2011/03/24 19:03:26 ajokela Exp $ */
package org.portfolio.client.controller.community.reports;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;


import org.portfolio.bus.PortfolioManager;
import org.portfolio.bus.assessment.AssessmentManager;
import org.portfolio.bus.assessment.ObjectiveManager;
import org.portfolio.bus.community.CommunityAuthorizationManager;
import org.portfolio.bus.community.CommunityManager;
import org.portfolio.client.RequestUtils;
import org.portfolio.client.controller.ApplicationController;
import org.portfolio.dao.PortfolioHome;
import org.portfolio.dao.assessment.AssessmentModelHome;
import org.portfolio.model.Person;
import org.portfolio.model.Portfolio;
import org.portfolio.model.ShareEntry;
import org.portfolio.model.assessment.Assessment;
import org.portfolio.model.assessment.AssessmentModel;
import org.portfolio.model.assessment.Objective;
import org.portfolio.model.assessment.ScoringModel;
import org.portfolio.model.community.Community;
import org.portfolio.model.community.CommunityRoleType;
import org.portfolio.util.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class DetailedObjectiveReport extends ApplicationController {
	
    @Autowired private CommunityManager communityManager;
    @Autowired private CommunityAuthorizationManager communityAuthorizationManager;
    @Autowired private PortfolioHome shareDefinitionHome;
	@Autowired private AssessmentManager assessmentManager;
	@Autowired private ObjectiveManager objectiveManager;
	@Autowired private AssessmentModelHome assessmentModelHome;
	@Autowired private PortfolioManager portfolioManager;
	
	private LogService logService = new LogService(this.getClass());
    
	public static String arrayToString(String[] a, String separator) {
	    StringBuffer result = new StringBuffer();
	    if (a.length > 0) {
	        result.append(a[0]);
	        for (int i=1; i<a.length; i++) {
	            result.append(separator);
	            result.append(a[i]);
	        }
	    }
	    return result.toString();
	}
	
    @RequestMapping("/community/reports/detailedObjectiveReport/{communityid}")
    public String templateProgressDetailed( @PathVariable("communityid") String communityId,
    							HttpServletRequest request) throws Exception {
    	
    	Person person = RequestUtils.getPerson(request);
    	Community community = communityManager.getCommunityById(communityId);
    	List<Objective> objectiveSets = communityManager.getCommunityObjectiveSets(communityId);
        
    	if (!communityAuthorizationManager.hasAssessmentCoordinatorAccess(person, community.getId())) {
    		throw new Exception("Unauthorized");
    	}
    	
        List<Person> members = communityManager.getMembers(communityId, CommunityRoleType.MEMBER);
        
    	List<String> dates = processDates(null, null);
    	
    	String dateFromString = dates.get(0);
    	String dateToString   = dates.get(1);

        Map<Objective, Map<AssessmentModel, Map<String, Integer>>>objectiveAssessmentModelMap = new LinkedHashMap<Objective, Map<AssessmentModel, Map<String, Integer>>>();
        
        Map<AssessmentModel, List<Objective>>assessmentModelsObjectives = new LinkedHashMap<AssessmentModel, List<Objective>>();
        
    	for(Iterator<Objective> i = objectiveSets.iterator(); i.hasNext(); ) {
    		Objective o = i.next();
    		
            Objective objectiveSet = objectiveManager.getObjectiveById(o.getId());
            List<Objective> flatSubObjectivesList = objectiveSet.getFlatSubObjectivesList();

            
            
            for(Iterator<Objective> j = flatSubObjectivesList.iterator(); j.hasNext();) {
            	Objective objective = j.next();
            	
            	List<AssessmentModel> assessmentModels = assessmentModelHome.findAssessmentModelsByObjective(objective.getId(), dateFromString, dateToString);
            	
            	// objectivesAssessmentModels.put(assessmentModels, objective);
            	
            	// assessmentModelsObjectivesList.add(objectivesAssessmentModels);
            	
                for (Iterator<AssessmentModel> k = assessmentModels.iterator(); k.hasNext();) {
                    
                	AssessmentModel assessmentModel = k.next();
                	ScoringModel sm = assessmentModel.getScoringModel();
                	
                	if(!assessmentModelsObjectives.containsKey(assessmentModel)) {
                		List<Objective> objList = new ArrayList<Objective>();
                		assessmentModelsObjectives.put(assessmentModel, objList);
                	}
                	
                	assessmentModelsObjectives.get(assessmentModel).add(objective);
                	
                	// logService.debug("    => ScoringModel: " + arrayToString(sm.getScores(), ", "));
                	
                	for(Iterator<Person> l = members.iterator(); l.hasNext(); ) {
	                	
                		Person p = l.next();
                		
	                	List<Portfolio> portfolios = shareDefinitionHome.findByOwnerAndAssessmentModelId(p.getPersonId(), assessmentModel.getId());	                    
	                    List<ShareEntry> portfolioElements = portfolioManager.findShareEntriesByPersonIdAndAssessmentModelId(p.getPersonId(), assessmentModel.getId());
	                    
	                    for(Iterator<Portfolio> m = portfolios.iterator(); m.hasNext(); ) {
	                    	Portfolio portfolio = m.next();
	                    	
	                    	List<Assessment> assessments = assessmentManager.findLatestPortfolioAssessments(portfolio.getShareId(), dateFromString, dateToString);
	                    	
	                    	for(Iterator<Assessment> n = assessments.iterator(); n.hasNext();) {
		                    	
	                    		Assessment assessment = n.next();
	                    		
	                    		String score = assessment.getOverallScore();
	                    		
	                    		Map<AssessmentModel, Map<String, Integer>>assessmentModelScoresMap = objectiveAssessmentModelMap.get(objective);
	                    		
	                    		if(assessmentModelScoresMap == null) {
	                    			assessmentModelScoresMap = new LinkedHashMap<AssessmentModel, Map<String, Integer>>();
	                    		}
	                    		
	                    		Map<String, Integer>qualifiedScoreCounts = assessmentModelScoresMap.get(assessmentModel);

	                    		if(qualifiedScoreCounts == null) {
	                    			
	                    			qualifiedScoreCounts = new LinkedHashMap<String, Integer>();
	                    			
	                    		}
                    			                    		
	                    		String scoringModel[] = sm.getScores();

	                    		for(int q = 0; q < scoringModel.length; ++q) {
		                    		
		                    		 Integer scoreCount = qualifiedScoreCounts.get(scoringModel[q]);
		                    		 
		                    		 if(scoreCount == null) {
		                    			 qualifiedScoreCounts.put(scoringModel[q], 0);
		                    		 }
		                    		
	                    		}
	                    		
		                    	/*
		                    	 * We have:
		                    	 *   + Objective
		                    	 *   + Assessment Model / Scoring Model
		                    	 *   + Quantified Score
		                    	 * 
		                    	 */
	                    	
	                    		Integer scoreCount = qualifiedScoreCounts.get(score);
	                    		
	                    		if(scoreCount == null) {
	                    			scoreCount = 0;
	                    		}
	                    		
	                    		scoreCount++;
	                    		
	                    		qualifiedScoreCounts.put(score, scoreCount);
	                    		
	                    		assessmentModelScoresMap.put(assessmentModel, qualifiedScoreCounts);
	                    		
	                    		objectiveAssessmentModelMap.put(objective, assessmentModelScoresMap);
	                    		
	                    	}
	                    	
	                    }

	                    for(Iterator<ShareEntry> m = portfolioElements.iterator(); m.hasNext(); ) {
	                    	m.next();
	                    	
	                    	
	                    	
	                    }

	                    
                	}
                	
                }
                
            }
            
    	}
    	
    	
    	Set<Objective> objKeys = objectiveAssessmentModelMap.keySet();
    	
    	for(Iterator<Objective> i=objKeys.iterator(); i.hasNext();) {
    		Objective objKey = i.next();
    		
    		Map<AssessmentModel, Map<String, Integer>>assessmentModelMap = objectiveAssessmentModelMap.get(objKey);
    		
    		Set<AssessmentModel> assKeys = assessmentModelMap.keySet();
    		
    		for(Iterator<AssessmentModel> j = assKeys.iterator(); j.hasNext();) {
    			
    			AssessmentModel model = j.next();
    			
    			Map<String, Integer>scores = assessmentModelMap.get(model);
    			
    			Set<String> scoresKeys = scores.keySet();
    			
    			String strScores = "";
    			
    			for(Iterator<String> k = scoresKeys.iterator(); k.hasNext(); ) {
    				String key = k.next();
    				Integer count = scores.get(key);
    				
    				strScores += key + "/" + Integer.toString(count);
    				
    				if(k.hasNext()) {
    					strScores += "  |  ";
    				}
    				
    			}
    			
    			logService.debug(" ==> " + objKey.getName() + " - " + strScores );
    			
    		}
    		
    	}
    	
    	request.setAttribute("objectiveAssessmentModelMap", objectiveAssessmentModelMap);
    	request.setAttribute("assessmentModelsObjectives", assessmentModelsObjectives);

    	return "community/report/detailedObjectReport";
    }
    
    public List<String> processDates(String dateFromString, String dateToString) {
        
    	Date dateFrom = null;
        Date dateTo   = null;
        
        if (dateFromString != null && dateFromString.trim().length() > 0 && !dateFromString.equalsIgnoreCase("none")) {
            try {
                dateFrom = new SimpleDateFormat("MM/dd/yyyy").parse(dateFromString);
            } catch (ParseException e) {
                
            }
        } else {
            dateFrom = null;
        }            

        if (dateToString != null && dateToString.trim().length() > 0 && !dateToString.equalsIgnoreCase("none")) {
            try {
                dateTo = new SimpleDateFormat("MM/dd/yyyy").parse(dateToString);
            } catch (ParseException e) {
                
            }
        } else {
            dateTo = null;
        }            

        if (dateFrom == null) {

        	dateFromString = null;
        }
        	
        if (dateTo == null) {
            
        	dateToString = null;
        }

        List<String> dates =  new ArrayList<String>();
        
        dates.add(dateFromString);
        dates.add(dateToString);
        
        return dates;
    }
}
    