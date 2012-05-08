package org.portfolio.client.controller.community.reports;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.portfolio.bus.PortfolioManager;
import org.portfolio.bus.assessment.AssessmentManager;
import org.portfolio.bus.community.CommunityManager;
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
import org.portfolio.util.LogService;

class SummaryObject {

	private AssessmentManager assessmentManager;
	private PortfolioHome shareDefinitionHome;
	private TemplateElementMappingHome templateElementMappingHome;
	private PortfolioManager portfolioManager;
	private ViewerHome viewerHome;
	private CommunityManager communityManager;

    private int groupBy = 2;  
    
    private LogService logService = new LogService(this.getClass());
    
	private Map<Template, List<Person>> studentsByTemplate;
	private Map<Person, List<Template>> templatesByStudent;
	private Map<Person, List<Portfolio>> portfoliosToStudent;

	private Map<Template, List<Portfolio>> portfoliosToTemplateMap;
    private Map<Portfolio, List<TemplateElementMapping>> temMap;
    private Map<Portfolio, RowData> portfolioMap;
    private Map<Portfolio, Template> portfolioTemplateMap;
    private Map<TemplateElementMapping, List<RowData>> shareEntryMap;
    
    private boolean detailed = true;
    
    public SummaryObject(AssessmentManager am, PortfolioHome ph, TemplateElementMappingHome tem, PortfolioManager pm, ViewerHome vh, CommunityManager cm) {
    	assessmentManager = am;
    	shareDefinitionHome = ph;
    	templateElementMappingHome = tem;
    	portfolioManager = pm;
    	viewerHome = vh;
    	communityManager = cm;
    	
    	portfolioTemplateMap = new LinkedHashMap<Portfolio, Template>();
    	
    }

	public void report(String dateFromString, String dateToString, String group, String communityId, List<Person> members, String templateId, HttpServletRequest request, boolean detailed) {
    	String[] dates = processDates(dateFromString, dateToString);
    	
        List<Template> assessableTemplates = null;
        
        this.detailed = detailed;
        
        if (templateId.contentEquals("-1")) {
        	
        	assessableTemplates = communityManager.getPublishedCommunityTemplates(communityId); // communityManager.getAssessableCommunityTemplates(communityId);
        }
        else {
        	List<Template> yo = communityManager.getPublishedCommunityTemplates(communityId);
        	
        	// assessableTemplates = communityManager.getAssessableCommunityTemplates(communityId, templateId);
        	
        	for(Iterator<Template> i=yo.iterator(); i.hasNext();) {
        		Template t = i.next();
        		
        		if (t.getId().contentEquals(templateId)) {
        			assessableTemplates = new ArrayList<Template>();
        			assessableTemplates.add(t);
        			break;
        		}
        		
        	}
        	
        }
        
    	dateFromString = dates[0];
    	dateToString   = dates[1];
    	
    	
    	if (group != null) {
    		if (group.contentEquals("template")) {
    			groupBy = 1;
    		}
    		else if (group.contentEquals("student")) {
    			groupBy = 2;
    		}
    	}
    	
    	studentsByTemplate = new LinkedHashMap<Template, List<Person>>();
    	templatesByStudent = new LinkedHashMap<Person, List<Template>>();
    	portfoliosToStudent = new LinkedHashMap<Person, List<Portfolio>>();
    	
    	if (groupBy == 1) {
        	
        	for(Iterator<Template> h = assessableTemplates.iterator(); h.hasNext(); ) {
        		
        		Template t = h.next();
        		
        		List<Person> people = new ArrayList<Person>();
        		
        		for(Iterator<Person> i = members.iterator(); i.hasNext(); ) {

        			Person p = i.next();
        			
        			List<Portfolio> portfolios = shareDefinitionHome.findByOwnerAndTemplateId(p.getPersonId(), t.getId(), dateFromString, dateToString);
		            
	
        			if(portfolios.size() > 0) {
        				people.add(p);
        				
        				List<Portfolio> goodPorts = new ArrayList<Portfolio>();
        				List<Portfolio> ports = portfoliosToStudent.get(p);
        				
        				if(ports == null) {
        					ports = new ArrayList<Portfolio>();
        				}
        				
        				for(Iterator<Portfolio> j = portfolios.iterator(); j.hasNext();) {
        					Portfolio k = j.next();
        					
        					if(k.getAssessmentModelAssignment() != null) {
        						goodPorts.add(k);
        					}
        					
        				}
        				
        				ports.addAll(goodPorts);

        				if (ports.size() > 0) {
        					portfoliosToStudent.put(p, ports);
        				}

        			}
        			
        		}
        		
        		if (people.size() > 0) {
        			studentsByTemplate.put(t, people);
        		}
        	}
    	
    	}
    	
    	if (groupBy == 2) {
        	
        	for(Iterator<Person> h = members.iterator(); h.hasNext();) {
        		
        		Person p = h.next();
        		
        		List<Template> templates = new ArrayList<Template>();
        		
        		for(Iterator<Template> i = assessableTemplates.iterator(); i.hasNext();) {
        			Template t = i.next();
        			
        			List<Portfolio> portfolios = shareDefinitionHome.findByOwnerAndTemplateId(p.getPersonId(), t.getId(), dateFromString, dateToString);
        			
        			if(portfolios.size() > 0) {
        				templates.add(t);
        				
        				List<Portfolio> goodPorts = new ArrayList<Portfolio>();
        				List<Portfolio> ports = portfoliosToStudent.get(p);
        				
        				if(ports == null) {
        					ports = new ArrayList<Portfolio>();
        				}
        				
        				for(Iterator<Portfolio> j = portfolios.iterator(); j.hasNext();) {
        					Portfolio k = j.next();
        					
        					if(k.getAssessmentModelAssignment() != null) {
        						goodPorts.add(k);
        					}
        					
        				}
        				
        				ports.addAll(goodPorts);
        				
        				if (ports.size() > 0) {
        					portfoliosToStudent.put(p, ports);
        				}
        				
        			}
        			
        		}
        		
        		if (templates.size() > 0) {
        			templatesByStudent.put(p, templates);
        		}
        		
        	}
    	}
    	
    	portfoliosToTemplateMap = new LinkedHashMap<Template, List<Portfolio>>();
        temMap = new LinkedHashMap<Portfolio, List<TemplateElementMapping>>();
        portfolioMap = new LinkedHashMap<Portfolio, RowData>();
        shareEntryMap = new LinkedHashMap<TemplateElementMapping, List<RowData>>();

    	for(Iterator<Template> h = assessableTemplates.iterator(); h.hasNext();) {
	        
    		Template t = h.next();
    		
    		for(Iterator<Person> i = members.iterator(); i.hasNext(); ) {
    		
    			Person p = i.next();
    			
	            List<Portfolio> ports = shareDefinitionHome.findByOwnerAndTemplateId(p.getPersonId(), t.getId(), dateFromString, dateToString);
	            Collections.sort(ports, Portfolio.DATE_MODIFIED_ORDER);
	            
	            List<Portfolio> portfolios = portfoliosToTemplateMap.get(t);
	            
	            if (portfolios == null) {
	            	portfolios = new ArrayList<Portfolio>();
	            }
	            
	            portfolios.addAll(ports);
	            portfoliosToTemplateMap.put(t, portfolios);
	            
	            for (Portfolio shareDefinition : ports) {
	            	
	                List<Viewer> evaluators = viewerHome.findEvaluatorsByShareId(shareDefinition.getShareId());
	                RowData portfolioRowData = createRowDataForPortfolio(shareDefinition, evaluators, dateFromString, dateToString);
	                portfolioMap.put(shareDefinition, portfolioRowData);
	
	                portfolioTemplateMap.put(shareDefinition, shareDefinition.getTemplate());
	                
	                if(this.detailed) {
		                
		                List<TemplateElementMapping> elements = templateElementMappingHome.findAssessableByTemplateId(shareDefinition.getTemplateId());
		                temMap.put(shareDefinition, elements);
		
		                for (TemplateElementMapping templateElementMapping : elements) {
		                    List<RowData> entryRowData = createRowDataForPortfolioElement(shareDefinition, evaluators, templateElementMapping, dateFromString, dateToString);
		                    shareEntryMap.put(templateElementMapping, entryRowData);
		                }
		                
	                }
	            }
    		
    		}
         
    	}
    	
    	logService.debug("portfoliosToStudent.size = " + portfoliosToStudent.size());
    	
    	request.setAttribute("portfoliosToStudent", portfoliosToStudent);
    	request.setAttribute("portfoliosToTemplateMap", portfoliosToTemplateMap);
    	request.setAttribute("portfolioTemplateMap", portfolioTemplateMap);
        request.setAttribute("portfolioMap", portfolioMap);
        request.setAttribute("temMap", temMap);
        request.setAttribute("shareEntryMap", shareEntryMap);
        request.setAttribute("detailed", this.detailed);
        request.setAttribute("report", "report");
        
	}
	
	public boolean getDetailed() {
		return detailed;
	}
	
	public Map<Template, List<Person>> getStudentsByTemplate() {
		return studentsByTemplate;
	}
	
	public Map<Person, List<Template>> getTemplatesByStudent() {
		return templatesByStudent;
	}
	
	public Map<Person, List<Portfolio>> getPortfoliosToStudents() {
		return portfoliosToStudent;
	}
	
	public Map<Template, List<Portfolio>> getPortfoliosToTemplates() {
		return portfoliosToTemplateMap;
	}
	
	public Map<Portfolio, RowData> getPortfolioMap() {
		return portfolioMap;
	}
	
	public Map<Portfolio, List<TemplateElementMapping>> getTemMap() {
		return temMap;
	}
	
	public Map<TemplateElementMapping, List<RowData>> getShareEntryMap() {
		return shareEntryMap;
	}
	
    private String[] processDates(String dateFromString, String dateToString) {
    	
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
    	
        String dates[] = new String[2];
        
        dates[0] = dateFromString;
        dates[1] = dateToString;
        
        return dates;
    }

    private RowData createRowDataForPortfolio(Portfolio shareDefinition, List<Viewer> evaluators, String dateFromString, String dateToString) {
        List<Assessment> assessments = assessmentManager.findLatestPortfolioAssessments(shareDefinition.getShareId(), dateFromString, dateToString);
        return new RowData(shareDefinition, assessments, evaluators);
    }

    private List<RowData> createRowDataForPortfolioElement(Portfolio shareDefinition, List<Viewer> evaluators,
            TemplateElementMapping templateElementMapping, String dateFromString, String dateToString) {
        List<ShareEntry> shareEntries = portfolioManager.findShareEntriesByShareIdElementId(
                shareDefinition.getShareId(),
                templateElementMapping.getId());
        List<RowData> entryRowData = new ArrayList<RowData>();
        for (ShareEntry shareEntry : shareEntries) {
            List<Assessment> entryAssessments = assessmentManager.findLatestPortfolioElementAssessments(shareEntry.getId(), dateFromString, dateToString);
            entryRowData.add(new RowData(shareEntry, entryAssessments, evaluators));
        }
        return entryRowData;
    }
	
}