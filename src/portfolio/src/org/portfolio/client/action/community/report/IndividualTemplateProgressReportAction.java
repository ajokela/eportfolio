/* $Name:  $ */
/* $Id: IndividualTemplateProgressReportAction.java,v 1.11 2011/03/24 19:03:25 ajokela Exp $ */
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
import org.portfolio.dao.PortfolioHome;
import org.portfolio.dao.ViewerHome;
import org.portfolio.dao.template.TemplateElementMappingHome;
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
import org.portfolio.util.StringEncryptor;

/**
 * @author Matt Sheehan
 * 
 */
public class IndividualTemplateProgressReportAction extends BaseAction {

    private AssessmentManager assessmentManager;
    private CommunityManager communityManager;
    private PortfolioHome shareDefinitionHome;
    private TemplateElementMappingHome templateElementMappingHome;
    private PortfolioManager portfolioManager;
    private StringEncryptor stringEncryptor;
    private ViewerHome viewerHome;
    private CommunityAuthorizationManager communityAuthorizationManager;

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        Person person = getPerson(request);
        String communityIdParam = request.getParameter("communityId");
        Community community = communityManager.getCommunityById(communityIdParam);
        request.setAttribute("community", community);

        List<Template> assessableTemplates = communityManager.getPublishedCommunityTemplates(Integer.toString(community.getId())); // communityManager.getAssessableCommunityTemplates(communityIdParam);
        request.setAttribute("assessableTemplates", assessableTemplates);

        if (communityAuthorizationManager.hasCommunityCoordinatorAccess(person, community.getId())) {
            List<Person> members = communityManager.getMembers(communityIdParam, CommunityRoleType.MEMBER);
            request.setAttribute("members", members);
        }

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
            String personId;
            if (request.getParameter("euid") != null) {
                personId = stringEncryptor.decrypt(request.getParameter("euid"));
            } else {
                personId = getPersonId(request);
            }
            
            List<Portfolio> portfolios = shareDefinitionHome.findByOwnerAndTemplateId(personId, templateParam, dateFromString, dateToString);
            Collections.sort(portfolios, Portfolio.DATE_MODIFIED_ORDER);
            request.setAttribute("portfolios", portfolios);

            Map<Portfolio, List<TemplateElementMapping>> temMap = new HashMap<Portfolio, List<TemplateElementMapping>>();
            Map<Portfolio, RowData> portfolioMap = new HashMap<Portfolio, RowData>();
            Map<TemplateElementMapping, List<RowData>> shareEntryMap = new HashMap<TemplateElementMapping, List<RowData>>();

            for (Portfolio shareDefinition : portfolios) {
                List<Viewer> evaluators = viewerHome.findEvaluatorsByShareId(shareDefinition.getShareId());
                RowData portfolioRowData = createRowDataForPortfolio(shareDefinition, evaluators, dateFromString, dateToString);
                portfolioMap.put(shareDefinition, portfolioRowData);

                List<TemplateElementMapping> elements = templateElementMappingHome.findAssessableByTemplateId(shareDefinition
                        .getTemplateId());
                temMap.put(shareDefinition, elements);

                for (TemplateElementMapping templateElementMapping : elements) {
                    List<RowData> entryRowData = createRowDataForPortfolioElement(shareDefinition, evaluators, templateElementMapping, dateFromString, dateToString);
                    shareEntryMap.put(templateElementMapping, entryRowData);
                }
            }
            request.setAttribute("portfolioMap", portfolioMap);
            request.setAttribute("temMap", temMap);
            request.setAttribute("shareEntryMap", shareEntryMap);
        }

        if (request.getParameter("export") != null) {
            return mapping.findForward("export");
        } else {
            return mapping.findForward("success");
        }
    }

    private RowData createRowDataForPortfolio(Portfolio shareDefinition, List<Viewer> evaluators, String dateFrom, String dateTo) {
        List<Assessment> assessments = assessmentManager.findLatestPortfolioAssessments(shareDefinition.getShareId(), dateFrom, dateTo);
        return new RowData(shareDefinition, assessments, evaluators);
    }

    private List<RowData> createRowDataForPortfolioElement(Portfolio shareDefinition, List<Viewer> evaluators,
            TemplateElementMapping templateElementMapping, String dateFrom, String dateTo) {
        List<ShareEntry> shareEntries = portfolioManager.findShareEntriesByShareIdElementId(
                shareDefinition.getShareId(),
                templateElementMapping.getId());
        List<RowData> entryRowData = new ArrayList<RowData>();
        for (ShareEntry shareEntry : shareEntries) {
            List<Assessment> entryAssessments = assessmentManager.findLatestPortfolioElementAssessments(shareEntry.getId(), dateFrom, dateTo);
            entryRowData.add(new RowData(shareEntry, entryAssessments, evaluators));
        }
        return entryRowData;
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
    public void setStringEncryptor(StringEncryptor stringEncryptor) {
        this.stringEncryptor = stringEncryptor;
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

        public RowData(Object assessable, List<Assessment> assessments, List<Viewer> evaluators) {
            this.assessable = assessable;
            this.evaluators = evaluators;

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
    }
}
