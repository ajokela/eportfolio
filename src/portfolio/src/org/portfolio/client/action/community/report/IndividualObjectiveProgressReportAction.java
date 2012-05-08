/* $Name:  $ */
/* $Id: IndividualObjectiveProgressReportAction.java,v 1.13 2011/03/24 19:03:25 ajokela Exp $ */
package org.portfolio.client.action.community.report;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
import org.portfolio.util.StringEncryptor;

/**
 * @author Matt Sheehan
 * 
 */
public class IndividualObjectiveProgressReportAction extends BaseAction {

    private AssessmentManager assessmentManager;
    private CommunityManager communityManager;
    private PortfolioHome shareDefinitionHome;
    private PortfolioManager portfolioManager;
    private StringEncryptor stringEncryptor;
    private ObjectiveManager objectiveManager;
    private AssessmentModelHome assessmentModelHome;
    private ViewerHome viewerHome;
    private CommunityAuthorizationManager communityAuthorizationManager;

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
    	
        Person person = getPerson(request);
        String communityIdParam = request.getParameter("communityId");
        Community community = communityManager.getCommunityById(communityIdParam);
        request.setAttribute("community", community);

        List<Objective> objectiveSets = communityManager.getCommunityObjectiveSets(communityIdParam);
        request.setAttribute("objectiveSets", objectiveSets);
        List<Person> members = null;
        
        if (communityAuthorizationManager.hasCommunityCoordinatorAccess(person, community.getId())) {
            members = communityManager.getMembers(communityIdParam, CommunityRoleType.MEMBER);
        }
        else {
        	members = new ArrayList<Person>();
        	members.add(person);
        }
        
        request.setAttribute("members", members);

        if (request.getParameter("run") != null) {
            String objectiveIdParam = request.getParameter("objectiveId");
            String personId;
            if (request.getParameter("euid") != null) {
                personId = stringEncryptor.decrypt(request.getParameter("euid"));
            } else {
                personId = getPersonId(request);
            }

            Objective objectiveSet = objectiveManager.getObjectiveById(Integer.parseInt(objectiveIdParam));
            List<Objective> flatSubObjectivesList = objectiveSet.getFlatSubObjectivesList();

            Map<Objective, List<RowData>> portfoliosMap = new LinkedHashMap<Objective, List<RowData>>();
            Map<Objective, List<RowData>> portfolioElementsMap = new LinkedHashMap<Objective, List<RowData>>();
            
            String dateFromString = request.getParameter("dateFromString");
            String dateToString   = request.getParameter("dateToString");
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
            
            for (Objective objective : flatSubObjectivesList) {

            	List<AssessmentModel> assessmentModels = assessmentModelHome.findAssessmentModelsByObjective(objective.getId(), dateFromString, dateToString);
                List<RowData> portfolioRowData = new ArrayList<RowData>();
                List<RowData> portfolioElementRowData = new ArrayList<RowData>();
                for (AssessmentModel assessmentModel : assessmentModels) {
                    List<Portfolio> portfolios = shareDefinitionHome.findByOwnerAndAssessmentModelId(personId, assessmentModel.getId());
                    for (Portfolio portfolio : portfolios) {
                        RowData rowData = createRowDataForPortfolio(objective, assessmentModel, portfolio, dateFromString, dateToString);
                        portfolioRowData.add(rowData);
                    }
                    portfoliosMap.put(objective, portfolioRowData);
                    List<ShareEntry> portfolioElements = portfolioManager.findShareEntriesByPersonIdAndAssessmentModelId(
                            personId,
                            assessmentModel.getId());
                    for (ShareEntry portfolioElement : portfolioElements) {
                        RowData rowData = createRowDataForPortfolioElement(objective, assessmentModel, portfolioElement, dateFromString, dateToString);
                        portfolioElementRowData.add(rowData);
                    }
                    portfolioElementsMap.put(objective, portfolioElementRowData);

                }
                
            }
            request.setAttribute("portfolioElementsMap", portfolioElementsMap);
            request.setAttribute("portfoliosMap", portfoliosMap);
            request.setAttribute("objectiveSet", objectiveSet);
        }

        if (request.getParameter("export") != null) {
            return mapping.findForward("export");
        } else {
            return mapping.findForward("success");
        }
    }

    private RowData createRowDataForPortfolioElement(Objective objective, AssessmentModel assessmentModel, ShareEntry shareEntry, String dateFrom, String dateTo) {
        List<Assessment> assessments = assessmentManager.findLatestPortfolioElementAssessments(shareEntry.getId(), dateFrom, dateTo);
        List<Viewer> evaluators = viewerHome.findEvaluatorsByShareEntryId(shareEntry.getId());
        int scoreIndex = assessmentModel.getObjectiveIndex(objective);
        return new RowData(shareEntry, assessments, evaluators, scoreIndex, assessmentModel);
    }

    private RowData createRowDataForPortfolio(Objective objective, AssessmentModel assessmentModel, Portfolio shareDefinition, String dateFrom, String dateTo) {
        List<Assessment> assessments = assessmentManager.findLatestPortfolioAssessments(shareDefinition.getShareId(), dateFrom, dateTo);
        List<Viewer> evaluators = viewerHome.findEvaluatorsByShareId(shareDefinition.getShareId());
        int scoreIndex = assessmentModel.getObjectiveIndex(objective);
        return new RowData(shareDefinition, assessments, evaluators, scoreIndex, assessmentModel);
    }

    public void setAssessmentManager(AssessmentManager assessmentManager) {
        this.assessmentManager = assessmentManager;
    }

    public void setCommunityManager(CommunityManager communityManager) {
        this.communityManager = communityManager;
    }

    public void setShareDefinitionHome(PortfolioHome shareDefinitionHome) {
        this.shareDefinitionHome = shareDefinitionHome;
    }

    public void setPortfolioManager(PortfolioManager portfolioManager) {
        this.portfolioManager = portfolioManager;
    }

    public void setStringEncryptor(StringEncryptor stringEncryptor) {
        this.stringEncryptor = stringEncryptor;
    }

    public void setObjectiveManager(ObjectiveManager objectiveManager) {
        this.objectiveManager = objectiveManager;
    }

    public void setAssessmentModelHome(AssessmentModelHome assessmentModelHome) {
        this.assessmentModelHome = assessmentModelHome;
    }

    public void setViewerHome(ViewerHome viewerHome) {
        this.viewerHome = viewerHome;
    }

    public void setCommunityAuthorizationManager(CommunityAuthorizationManager communityAuthorizationManager) {
        this.communityAuthorizationManager = communityAuthorizationManager;
    }

    public static class RowData {
        private final Object assessable;
        private final Map<Person, Assessment> assessments;
        private final List<Viewer> evaluators;
        private final int scoreIndex;
        private final AssessmentModel assessmentModel;
        
        public RowData(Object assessable, List<Assessment> assessments, List<Viewer> evaluators, int scoreIndex, AssessmentModel assessmentModel) {

            this.assessable = assessable;
            this.evaluators = evaluators;
            this.scoreIndex = scoreIndex;
            this.assessmentModel = assessmentModel;
            
            this.assessments = new HashMap<Person, Assessment>();
            for (Assessment assessment : assessments) {
                this.assessments.put(assessment.getEvaluator(), assessment);
            }
        }
        

        public Object getAssessable() {
            return assessable;
        }

        public Map<Person, Assessment> getAssessments() {
            return assessments;
        }

        public List<Viewer> getEvaluators() {
            return evaluators;
        }

        public int getScoreIndex() {
            return scoreIndex;
        }
        
        public AssessmentModel getAssessmentModel() {
        	return assessmentModel;
        }

    }
}
