/* $Name:  $ */
/* $Id: ObjectiveProgressSummaryReportAction.java,v 1.11 2011/03/24 19:03:25 ajokela Exp $ */
package org.portfolio.client.action.community.report;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.portfolio.bus.PortfolioManager;
import org.portfolio.bus.assessment.AssessmentManager;
import org.portfolio.bus.assessment.ObjectiveManager;
import org.portfolio.bus.community.CommunityAuthorizationManager;
import org.portfolio.bus.community.CommunityManager;
import org.portfolio.client.action.BaseAction;
import org.portfolio.dao.PersonHome;
import org.portfolio.dao.PortfolioHome;
import org.portfolio.dao.ViewerHome;
import org.portfolio.dao.assessment.AssessmentModelHome;
import org.portfolio.model.Person;
import org.portfolio.model.Portfolio;
import org.portfolio.model.ShareEntry;
import org.portfolio.model.Viewer;
import org.portfolio.model.assessment.Assessment;
import org.portfolio.model.assessment.AssessmentModel;
import org.portfolio.model.assessment.Objective;
import org.portfolio.model.community.Community;
import org.portfolio.model.community.CommunityRoleType;
import org.portfolio.util.RequiredInjection;

/**
 * @author Matt Sheehan
 * 
 */
public class ObjectiveProgressSummaryReportAction extends BaseAction {

    private AssessmentManager assessmentManager;
    private CommunityManager communityManager;
    private PortfolioHome shareDefinitionHome;
    private PortfolioManager portfolioManager;
    private ObjectiveManager objectiveManager;
    private AssessmentModelHome assessmentModelHome;
    private ViewerHome viewerHome;
    private PersonHome personHome;
    private CommunityAuthorizationManager communityAuthorizationManager;

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
    	
        String communityIdParam = request.getParameter("communityId");
        Community community = communityManager.getCommunityById(communityIdParam);
        
        communityAuthorizationManager.hasAssessmentCoordinatorAccess(getPerson(request), community.getId());
        
        request.setAttribute("community", community);

        List<Objective> objectiveSets = communityManager.getCommunityObjectiveSets(communityIdParam);
        request.setAttribute("objectiveSets", objectiveSets);

        List<Person> members = communityManager.getMembers(communityIdParam, CommunityRoleType.MEMBER);
        request.setAttribute("members", members);


        
        if (request.getParameter("run") != null) {
        	
            String dateFromString 	= request.getParameter("dateFromString");
            String dateToString   	= request.getParameter("dateToString");
            String objectiveIdParam = request.getParameter("objectiveId");
            
            Date dateFrom = null;
            Date dateTo   = null;
            
            if (dateFromString != null && dateFromString.trim().length() > 0 && !dateFromString.equalsIgnoreCase("none")) {
                try {
                    dateFrom = new SimpleDateFormat("MM/dd/yyyy").parse(dateFromString);
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else {
                dateFrom = null;
            }            

            if (dateToString != null && dateToString.trim().length() > 0 && !dateToString.equalsIgnoreCase("none")) {
                try {
                    dateTo = new SimpleDateFormat("MM/dd/yyyy").parse(dateToString);
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else {
                dateTo = null;
            }            

            if (dateFrom != null) {
            
            	dateFromString = request.getParameter("dateFromString");
            	            
            }
            else {
            	dateFromString = null;
            }
            	
            if (dateTo != null) {
                
            	dateToString = request.getParameter("dateToString");
            	            
            }
            else {
            	dateToString = null;
            }
            
            if (objectiveIdParam != null && objectiveIdParam.length() > 0) {
	        	
	            Objective objectiveSet = objectiveManager.getObjectiveById(Integer.parseInt(objectiveIdParam));
	            List<Objective> flatSubObjectivesList = objectiveSet.getFlatSubObjectivesList();
	
	            String[] personIds = request.getParameterValues("uid");
	            Map<Objective, List<AggregateData>> objectiveMap = new HashMap<Objective, List<AggregateData>>();
	            Map<Objective, AggregateData> totalsMap = new HashMap<Objective, AggregateData>();
	            
	            for (Objective objective : flatSubObjectivesList) {
	                List<AggregateData> dataList = new ArrayList<AggregateData>();
	                AggregateData totals = new AggregateData();
	                
	                for (String personId : personIds) {
	                    AggregateData aggregateData = new AggregateData(personHome.findByPersonId(personId));
	
	                    List<AssessmentModel> assessmentModels = assessmentModelHome.findAssessmentModelsByObjective(objective.getId(), dateFromString, dateToString);
	                    for (AssessmentModel assessmentModel : assessmentModels) {
	                        List<Portfolio> portfolios = shareDefinitionHome.findByOwnerAndAssessmentModelId(personId, assessmentModel
	                                .getId());
	                        
	                        for (Portfolio portfolio : portfolios) {
	                            List<Viewer> evaluators = viewerHome.findEvaluatorsByShareId(portfolio.getShareId());
	                            if (!evaluators.isEmpty()) {
	                                List<Assessment> assessments = assessmentManager.findLatestPortfolioAssessments(portfolio.getShareId(), dateFromString, dateToString);
	                                for (Assessment assessment : assessments) {
	                                    if (assessment.isFinal()) {
	                                        aggregateData.addAssessment(assessment);
	                                        totals.addAssessment(assessment);
	                                    }
	                                }
	                            }
	                        }
	                        List<ShareEntry> portfolioElements = portfolioManager.findShareEntriesByPersonIdAndAssessmentModelId(
	                                personId,
	                                assessmentModel.getId());
	                        for (ShareEntry portfolioElement : portfolioElements) {
	                            List<Viewer> evaluators = viewerHome.findEvaluatorsByShareEntryId(portfolioElement.getId());
	                            if (!evaluators.isEmpty()) {
	                                List<Assessment> assessments = assessmentManager.findLatestPortfolioElementAssessments(portfolioElement.getId(), dateFromString, dateToString);
	                                for (Assessment assessment : assessments) {
	                                    if (assessment.isFinal()) {
	                                        aggregateData.addAssessment(assessment);
	                                        totals.addAssessment(assessment);
	                                    }
	                                }
	                            }
	                        }
	                    }
	                    
	                    dataList.add(aggregateData);
	                }
	                objectiveMap.put(objective, dataList);
	                totalsMap.put(objective, totals);
	            }
	            request.setAttribute("totalsMap", totalsMap);
	            request.setAttribute("objectiveMap", objectiveMap);
	            request.setAttribute("objectiveSet", objectiveSet);
	        }
            
        }
        
        if (request.getParameter("export") != null) {
            return mapping.findForward("export");
        } else {
            return mapping.findForward("success");
        }
    }

    @RequiredInjection
    public void setAssessmentManager(AssessmentManager assessmentManager) {
        this.assessmentManager = assessmentManager;
    }

    @RequiredInjection
    public void setCommunityManager(CommunityManager communityManager) {
        this.communityManager = communityManager;
    }

    @RequiredInjection
    public void setShareDefinitionHome(PortfolioHome shareDefinitionHome) {
        this.shareDefinitionHome = shareDefinitionHome;
    }

    @RequiredInjection
    public void setPortfolioManager(PortfolioManager portfolioManager) {
        this.portfolioManager = portfolioManager;
    }

    @RequiredInjection
    public void setObjectiveManager(ObjectiveManager objectiveManager) {
        this.objectiveManager = objectiveManager;
    }

    public void setAssessmentModelHome(AssessmentModelHome assessmentModelHome) {
        this.assessmentModelHome = assessmentModelHome;
    }

    @RequiredInjection
    public void setViewerHome(ViewerHome viewerHome) {
        this.viewerHome = viewerHome;
    }

    @RequiredInjection
    public void setPersonHome(PersonHome personHome) {
        this.personHome = personHome;
    }
    
    public static class AggregateData {
        
        private Person person;
        private int totalScore;
        private int numAssessments;

        public AggregateData(Person person) {
            this.person = person;
        }

        public AggregateData() {
        }

        public Person getPerson() {
            return person;
        }

        public int getTotalScore() {
            return totalScore;
        }

        public int getNumAssessments() {
            return numAssessments;
        }
        
        public void addAssessment(Assessment assessment) {
            totalScore += assessment.getOverallQuantifiedScore();
            numAssessments++; 
        }
        
        public Double getAverageScore() {
            return 1.0 * totalScore / numAssessments;
        }
    }

    public void setCommunityAuthorizationManager(CommunityAuthorizationManager communityAuthorizationManager) {
        this.communityAuthorizationManager = communityAuthorizationManager;
    }
}
