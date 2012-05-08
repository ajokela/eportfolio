/* $Name:  $ */
/* $Id: TemplateSummaryReport.java,v 1.6 2011/03/17 19:15:29 ajokela Exp $ */
package org.portfolio.client.controller.community.reports;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.portfolio.bus.PortfolioManager;
import org.portfolio.bus.assessment.AssessmentManager;
import org.portfolio.bus.community.CommunityAuthorizationManager;
import org.portfolio.bus.community.CommunityManager;
import org.portfolio.client.RequestUtils;
import org.portfolio.client.controller.ApplicationController;
import org.portfolio.dao.PortfolioHome;
import org.portfolio.dao.ViewerHome;
import org.portfolio.dao.template.TemplateElementMappingHome;
import org.portfolio.model.Person;
import org.portfolio.model.Template;
import org.portfolio.model.community.Community;
import org.portfolio.model.community.CommunityRoleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.portfolio.util.LogService;

/**
 * @author Alex C. Jokela <ajokela@d.umn.edu>
 * 
 */
@Controller
public class TemplateSummaryReport extends ApplicationController {

	@Autowired private AssessmentManager assessmentManager;
	@Autowired private CommunityManager communityManager;
	@Autowired private PortfolioHome shareDefinitionHome;
	@Autowired private TemplateElementMappingHome templateElementMappingHome;
	@Autowired private PortfolioManager portfolioManager;
	@Autowired private ViewerHome viewerHome;
	@Autowired private CommunityAuthorizationManager communityAuthorizationManager;
    
	private SummaryObject so;
	
	@SuppressWarnings("unused")
	private LogService logService = new LogService(this.getClass());
	
	public void templateProgress(String communityId, String report, String group, String dateFromString, String dateToString, String templateId, boolean detailed, HttpServletRequest request) {
    	
		Person person = RequestUtils.getPerson(request);
        
        Community community = communityManager.getCommunityById(communityId);
        request.setAttribute("community", community);

        request.setAttribute("communityId", community.getId());
        
        List<Template> assessableTemplates = communityManager.getPublishedCommunityTemplates(communityId); // communityManager.getAssessableCommunityTemplates(communityId);
        
        request.setAttribute("assessableTemplates", assessableTemplates);
        List<Person> members = communityManager.getMembers(communityId, CommunityRoleType.MEMBER);
        
        if (! communityAuthorizationManager.hasCommunityCoordinatorAccess(person, community.getId())) {
        	
        	members = new ArrayList<Person>();
        	members.add(person);
        	
        }

        if (report != null) {
        	
        	so = new SummaryObject(assessmentManager, shareDefinitionHome, templateElementMappingHome, portfolioManager, viewerHome, communityManager);
            
        	request.setAttribute("template", templateId);
        	
        	so.report(dateFromString, dateToString, group, communityId, members, templateId, request, detailed);
        	
        	request.setAttribute("dateFromString", dateFromString);
        	request.setAttribute("dateToString", dateToString);
        	request.setAttribute("group", group);
        	request.setAttribute("communityId", communityId);
        	request.setAttribute("members", members);
        	request.setAttribute("templateId", templateId);
   
        	
        }
        else {
        	request.setAttribute("template", "-1");
        }

	}
	
    @RequestMapping(method=RequestMethod.GET, value={"/community/reports/templateSummaryReport/{communityid}/detailed"})
    public String templateProgressDetailed( @PathVariable("communityid") String communityId,
    										@RequestParam(value="report", required=false) String report,
    										@RequestParam(value="group", required=false) String group,
    										@RequestParam(value="dateFromString", required=false) String dateFromString,
    										@RequestParam(value="dateToString", required=false) String dateToString,
    										@RequestParam(value="template", required=false) String templateId,
    							HttpServletRequest request) throws ParseException {
    	
    	templateProgress(communityId, report, group, dateFromString, dateToString, templateId, true, request);

        return "community/report/templateDetailedReport";
        
    }
    
    @RequestMapping(method=RequestMethod.GET, value={"/community/reports/templateSummaryReport/{communityid}/summary"})
    public String templateProgressSummary( @PathVariable("communityid") String communityId,
    										@RequestParam(value="report", required=false) String report,
    										@RequestParam(value="group", required=false) String group,
    										@RequestParam(value="dateFromString", required=false) String dateFromString,
    										@RequestParam(value="dateToString", required=false) String dateToString,
    										@RequestParam(value="template", required=false) String templateId,
    							HttpServletRequest request) throws ParseException {
    	
    	templateProgress(communityId, report, group, dateFromString, dateToString, templateId, false, request);

        return "community/report/templateSummaryReport";
        
    }


}
