package org.portfolio.client.controller.community.reports;

import hidden.edu.emory.mathcs.backport.java.util.Collections;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.portfolio.client.RequestUtils;
import org.portfolio.client.controller.community.reports.TemplateProgressDetailedSummaryReport.AssessmentData;
import org.portfolio.model.Person;
import org.portfolio.model.Portfolio;
import org.portfolio.model.Template;
import org.portfolio.model.assessment.Assessment;
import org.portfolio.model.community.Community;
import org.portfolio.model.community.CommunityRoleType;
import org.portfolio.bus.assessment.AssessmentManager;
import org.portfolio.bus.community.CommunityAuthorizationManager;
import org.portfolio.bus.community.CommunityManager;
import org.portfolio.dao.PortfolioHome;
import org.portfolio.util.LogService;

public class TPDSReport {
	
    private CommunityManager communityManager;
    private CommunityAuthorizationManager communityAuthorizationManager;
    
    private PortfolioHome shareDefinitionHome;
    private AssessmentManager assessmentManager;
	
	private String communityId;
	private boolean report;
	private HttpServletRequest request;
	private Map<Person, AssessmentData> personAssessmentMap = new LinkedHashMap<Person, AssessmentData>();
	private int num_final_assess = 0;
	private int num_prelim_assess= 0;

	@SuppressWarnings("unused")
	private LogService logService = new LogService(this.getClass());

	public TPDSReport(String communityId, boolean report, HttpServletRequest request, CommunityManager cm, CommunityAuthorizationManager cam, PortfolioHome ph, AssessmentManager am) {
		this.communityId = communityId;
		this.report = report;
		this.request = request;
		this.assessmentManager = am;
		this.communityAuthorizationManager = cam;
		this.communityManager = cm;
		this.shareDefinitionHome = ph;
	}
	
	public Map<Person, AssessmentData> getPersonAssessmentMap() {
		return personAssessmentMap;
	}
	
	public int getFinalAssessmentNumber() {
		return num_final_assess;
	}
	
	public int getPrelimAssessmentNumber() {
		return num_prelim_assess;
	}
	
	public void generate(String templateId, String dateFrom, String dateTo) {
		
    	Person person = RequestUtils.getPerson(request);
    	// logService.debug("communityId => " + communityId);
    	
    	Community community = communityManager.getCommunityById(communityId);
    	
    	if (!communityAuthorizationManager.hasAssessmentCoordinatorAccess(person, community.getId())) {
    		return;
    	}

    	List<Person> members = communityManager.getMembers(communityId, CommunityRoleType.MEMBER);
    	
    	List<Template> templates = communityManager.getPublishedCommunityTemplates(Integer.toString(community.getId())); // communityManager.getAssessableCommunityTemplates(Integer.toString(community.getId()));// templateHome.findByCommunityId(community.getId());
    	
    	Map<Template, List<Portfolio>> templatePortfoliosMap = new HashMap<Template, List<Portfolio>>();
    	
    	for(Iterator<Template> i = templates.iterator(); i.hasNext();) {

    		Template t = i.next();
    		List<Portfolio> plist = new ArrayList<Portfolio>();
    		
    		for(Iterator<Person> j = members.iterator(); j.hasNext(); ) {
    			Person p = j.next();
    			
        		List<Portfolio> portfolios = shareDefinitionHome.findByOwnerAndTemplateId(p.getPersonId(), t.getId());
        		
        		for(Iterator<Portfolio> k = portfolios.iterator(); k.hasNext(); ) {
        			
        			Portfolio pf = k.next();
        			
        			plist.add(pf);
        			
        		}
        		
        	}

    		templatePortfoliosMap.put(t, plist);
    		
    	}
    	
		
    	if(report) {
    		
        	List<String> dates = processDates(dateFrom, dateTo);
        	
        	String dateFromString = dates.get(0);
        	String dateToString   = dates.get(1);
        	
        	Template template = null;
        	
        	for(Iterator<Template> t = templates.iterator(); t.hasNext();) {
        		Template tt = t.next();
        		
        		if(templateId != null && tt.getId().contentEquals(templateId)) {
        			template = tt;
        			break;
        		}
        	}
        	
        	dateFrom = dateFromString;
        	dateTo   = dateToString;
        	
        	if(dateFrom == null || dateFrom == "") {
        		dateFrom = "None";
        	}
        	
        	if(dateTo == null || dateTo == "") {
        		dateTo = "None";
        	}
        	
        	request.setAttribute("dateFrom", dateFrom);
        	request.setAttribute("dateTo", dateTo);
        	            	
        	if (template != null) {
        		
        		for(Iterator<Person> i = members.iterator(); i.hasNext();) {
        			
					List<Assessment> final_assessments = new ArrayList<Assessment>();
					List<Assessment> prelim_assessments = new ArrayList<Assessment>();

        			Person p = i.next();
        			
        			List<Portfolio> portfolios = shareDefinitionHome.findByOwnerAndTemplateId(p.getPersonId(), template.getId(), dateFromString, dateToString);
        			
        			for(Iterator<Portfolio> j = portfolios.iterator(); j.hasNext();) {
        				Portfolio pfolio = j.next();
        				List<Assessment> p_assessments = assessmentManager.findLatestPortfolioAssessments(pfolio.getShareId(), dateFromString, dateToString);
        				
        				if(p_assessments != null) {
        					Collections.sort(p_assessments, Assessment.ASSESSED_DATE_COMPARATOR);
        					
        					for(Iterator<Assessment> k = p_assessments.iterator(); k.hasNext();) {
            					Assessment a = k.next();
            				
            					if(a.isFinal()) {
            						final_assessments.add(a);
            					}
            					else {
            						prelim_assessments.add(a);
            					}
            					
            				}
            				
        				}
        			}
        			
    				Collections.sort(final_assessments, Assessment.ASSESSED_DATE_COMPARATOR);
    				Collections.sort(prelim_assessments, Assessment.ASSESSED_DATE_COMPARATOR);
    				
    				if(final_assessments.size() > num_final_assess) {
    					num_final_assess = final_assessments.size();
    				}
    				
    				if(prelim_assessments.size() > num_prelim_assess) {
    					num_prelim_assess = prelim_assessments.size();
    				}

    				AssessmentData ad = new AssessmentData(p, prelim_assessments, final_assessments);
    				
    				personAssessmentMap.put(p, ad);

        		}
        		
        		Map<Person, AssessmentData> tempPersonAssessmentMap = new LinkedHashMap<Person, AssessmentData>();

        		for(Iterator<Person> i = personAssessmentMap.keySet().iterator(); i.hasNext();) {
        			Person p = i.next();
        			AssessmentData ad = personAssessmentMap.get(p);
        			
    				ad.bufferFinalAssessments(num_final_assess);
    				ad.bufferPrelimAssessments(num_prelim_assess);
    				
    				tempPersonAssessmentMap.put(p, ad);
    				
        		}
        		
        		personAssessmentMap.clear();
        		personAssessmentMap.putAll(tempPersonAssessmentMap);
        		
        		request.setAttribute("personAssessmentMap", personAssessmentMap);
    			request.setAttribute("num_final_assess", num_final_assess);
    			request.setAttribute("num_prelim_assess", num_prelim_assess);

        	}

    		request.setAttribute("templateid", templateId);
    		request.setAttribute("report", true);
    		
    	}
    	
    	request.setAttribute("members", members);
    	request.setAttribute("templates", templates);
    	request.setAttribute("templatePortfoliosMap", templatePortfoliosMap);
    	
    	request.setAttribute("community", community);
    	request.setAttribute("members", members);    
    	
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