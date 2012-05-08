/* $Name:  $ */
/* $Id: TemplateProgressSummaryReportAction.java,v 1.11 2011/03/24 19:03:25 ajokela Exp $ */
package org.portfolio.client.action.community.report;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
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
import org.portfolio.bus.community.CommunityAuthorizationManager;
import org.portfolio.bus.community.CommunityManager;
import org.portfolio.client.action.BaseAction;
import org.portfolio.dao.PersonHome;
import org.portfolio.dao.PortfolioHome;
import org.portfolio.dao.ViewerHome;
import org.portfolio.dao.template.TemplateElementMappingHome;
import org.portfolio.dao.template.TemplateHome;
import org.portfolio.model.Person;
import org.portfolio.model.Portfolio;
import org.portfolio.model.ShareEntry;
import org.portfolio.model.Template;
import org.portfolio.model.TemplateElementMapping;
import org.portfolio.model.Viewer;
import org.portfolio.model.assessment.Assessment;
import org.portfolio.model.community.Community;
import org.portfolio.model.community.CommunityRoleType;
import org.portfolio.util.RequiredInjection;

/**
 * @author Matt Sheehan
 * 
 */
public class TemplateProgressSummaryReportAction extends BaseAction {

    private AssessmentManager assessmentManager;
    private CommunityManager communityManager;
    private PortfolioHome shareDefinitionHome;
    private TemplateElementMappingHome templateElementMappingHome;
    private PortfolioManager portfolioManager;
    private ViewerHome viewerHome;
    private PersonHome personHome;
    private TemplateHome templateHome;
    private CommunityAuthorizationManager communityAuthorizationManager;

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String communityIdParam = request.getParameter("communityId");
        Community community = communityManager.getCommunityById(communityIdParam);
        request.setAttribute("community", community);
        
        communityAuthorizationManager.hasAssessmentCoordinatorAccess(getPerson(request), community.getId());

        List<Template> assessableTemplates = communityManager.getPublishedCommunityTemplates(Integer.toString(community.getId())); // communityManager.getAssessableCommunityTemplates(communityIdParam);
        request.setAttribute("assessableTemplates", assessableTemplates);

        List<Person> members = communityManager.getMembers(communityIdParam, CommunityRoleType.MEMBER);
        request.setAttribute("members", members);

        if (request.getParameter("run") != null) {
        	
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

        	
            String templateParam = request.getParameter("templateId");
            String[] personIds = request.getParameterValues("uid");

            Template template = templateHome.findTemplateById(templateParam, dateFromString, dateToString);
            List<AggregateData> portfolioData = new ArrayList<AggregateData>();
            Map<TemplateElementMapping, List<AggregateData>> portfolioElementMap = new HashMap<TemplateElementMapping, List<AggregateData>>();
            Map<TemplateElementMapping, AggregateData> portfolioElementTotalsMap = new HashMap<TemplateElementMapping, AggregateData>();
            List<TemplateElementMapping> elements = templateElementMappingHome.findAssessableByTemplateId(templateParam);

            AggregateData totalPortfolioData = new AggregateData();

            if (personIds != null) {
	            
	            for (String personId : personIds) {
	                Person person = personHome.findByPersonId(personId);
	
	                List<Portfolio> portfolios = shareDefinitionHome.findByOwnerAndTemplateId(personId, templateParam);
	                Collections.sort(portfolios, Portfolio.DATE_MODIFIED_ORDER);
	                request.setAttribute("portfolios", portfolios);
	
	                AggregateData aggregateData = new AggregateData(person);
	                portfolioData.add(aggregateData);
	                for (Portfolio shareDefinition : portfolios) {
	                    List<Viewer> evaluators = viewerHome.findEvaluatorsByShareId(shareDefinition.getShareId());
	                    if (!evaluators.isEmpty()) {
	                        List<Assessment> assessments = assessmentManager.findLatestPortfolioAssessments(shareDefinition.getShareId(), dateFromString, dateToString);
	                        for (Assessment assessment : assessments) {
	                            if (assessment.isFinal()) {
	                                aggregateData.addAssessment(assessment);
	                                totalPortfolioData.addAssessment(assessment);
	                            }
	                        }
	                    }
	                }
	
	                for (TemplateElementMapping templateElementMapping : elements) {
	                    List<AggregateData> entryDataList = portfolioElementMap.get(templateElementMapping);
	                    if (entryDataList == null) {
	                        entryDataList = new ArrayList<AggregateData>();
	                        portfolioElementMap.put(templateElementMapping, entryDataList);
	                    }
	
	                    AggregateData temTotal = portfolioElementTotalsMap.get(templateElementMapping);
	                    if (temTotal == null) {
	                        temTotal = new AggregateData();
	                        portfolioElementTotalsMap.put(templateElementMapping, temTotal);
	                    }
	
	                    AggregateData entryAggregateData = new AggregateData(person);
	                    entryDataList.add(entryAggregateData);
	                    for (Portfolio portfolio : portfolios) {
	                        List<ShareEntry> shareEntries = portfolioManager.findShareEntriesByShareIdElementId(
	                                portfolio.getShareId(),
	                                templateElementMapping.getId());
	                        for (ShareEntry shareEntry : shareEntries) {
	                            List<Assessment> entryAssessments = assessmentManager.findLatestPortfolioElementAssessments(shareEntry.getId(), dateFromString, dateToString);
	                            for (Assessment assessment : entryAssessments) {
	                                if (assessment.isFinal()) {
	                                    entryAggregateData.addAssessment(assessment);
	                                    temTotal.addAssessment(assessment);
	                                }
	                            }
	                        }
	                    }
	                }
	            }
	            request.setAttribute("totalPortfolioData", totalPortfolioData);
	            request.setAttribute("template", template);
	            request.setAttribute("elements", elements);
	            request.setAttribute("portfolioData", portfolioData);
	            request.setAttribute("portfolioElementMap", portfolioElementMap);
	            request.setAttribute("portfolioElementTotalsMap", portfolioElementTotalsMap);
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
    public void setTemplateElementMappingHome(TemplateElementMappingHome templateElementMappingHome) {
        this.templateElementMappingHome = templateElementMappingHome;
    }

    @RequiredInjection
    public void setPortfolioManager(PortfolioManager portfolioManager) {
        this.portfolioManager = portfolioManager;
    }

    @RequiredInjection
    public void setTemplateHome(TemplateHome templateHome) {
        this.templateHome = templateHome;
    }

    @RequiredInjection
    public void setPersonHome(PersonHome personHome) {
        this.personHome = personHome;
    }

    @RequiredInjection
    public void setViewerHome(ViewerHome viewerHome) {
        this.viewerHome = viewerHome;
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
