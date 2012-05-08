/* $Name:  $ */
/* $Id: CommunityController.java,v 1.20 2011/03/24 19:03:26 ajokela Exp $ */
package org.portfolio.client.controller.community;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import org.portfolio.bus.CommunityResourceManager;
import org.portfolio.bus.PortfolioSearch;
import org.portfolio.bus.community.CommunityAuthorizationManager;
import org.portfolio.bus.community.CommunityInteract;
import org.portfolio.bus.community.CommunityManager;
import org.portfolio.bus.community.CommunityMessageManager;
import org.portfolio.client.RequestUtils;
import org.portfolio.client.controller.ApplicationController;
import org.portfolio.model.CommunityResource;
import org.portfolio.model.ElementDataObject;
import org.portfolio.model.Link;
import org.portfolio.model.Person;
import org.portfolio.model.Portfolio;
import org.portfolio.model.PortfolioSearchCriteria;
import org.portfolio.model.Template;
import org.portfolio.model.assessment.Objective;
import org.portfolio.model.community.Community;
import org.portfolio.model.community.CommunityRoleType;
import org.portfolio.model.community.Interact;
import org.portfolio.model.community.Message;
import org.portfolio.model.wizard.CollectionGuide;
import org.portfolio.model.wizard.CollectionGuideUserData;
import org.portfolio.util.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CommunityController extends ApplicationController {

    @Autowired private CommunityManager communityManager;
    @Autowired private CommunityAuthorizationManager communityAuthorizationManager;
    @Autowired private PortfolioSearch portfolioSearch;
    // @Autowired private AssessmentManager assessmentManager;
    @Autowired private CommunityMessageManager cmm;
    @Autowired private CommunityResourceManager crm;
    @Autowired private CommunityInteract ci;
    
    private static final int MAX_NAME_LENGTH = 48;
    protected LogService logService = new LogService(this.getClass());
    
    @RequestMapping("/community/{id}")
    public String viewCommunity(@PathVariable("id") String communityId, HttpServletRequest request) {
    	return view(communityId, request);
    }
    
    @RequestMapping("/community/resources/reorder/{id}")
    public void reorderResources(@PathVariable("id") String communityId, 
    							 @RequestParam(value = "data", required = true) String data,
    							 HttpServletRequest request) {
    	
    	// resourceList[]=63&resourceList[]=64&resourceList[]=65
    	
    	Community community = communityManager.getCommunityById(communityId);
        
    	Person person = RequestUtils.getPerson(request);
    	
        if (communityAuthorizationManager.hasCommunityCoordinatorAccess(
        		person,
                community.getId()) || person.isAdmin()) {
    	
	    	String[] bits = data.split("&");
	    	
	    	int n = 1;
	    	
	    	for(int i=0; i<bits.length; ++i) {
	    		String[] resourceParts = bits[i].split("=");
	    		
	    		if(resourceParts.length == 2) {
	    			CommunityResource cr = crm.getResource(resourceParts[1]);
	    			
	    			if(cr != null) {
	    				cr.setPlace(n);
	    				crm.updateResource(cr);
	    			}
	    			
	    			++n;
	    		}
	    		
	    	}
    	}    	
    }

    
    @RequestMapping("/mycommunityreports/{id}")
    public String myCommunityReports(@PathVariable("id") String communityId, HttpServletRequest request) {
        
        Community community = communityManager.getCommunityById(communityId);
        
        request.setAttribute("community", community);
        request.setAttribute("hasSummaryReportAccess", communityAuthorizationManager.hasAssessmentCoordinatorAccess(
        		RequestUtils.getPerson(request),
                community.getId()));

    	return "community/myCommunityReports";
    }
    
    private String view(String communityId, HttpServletRequest request) {
    	
        Person person = RequestUtils.getPerson(request);

        Community community = communityManager.getCommunityById(communityId);
        
        if (community.isPrivateCommunity()) {
            if(!communityAuthorizationManager.verifyMemberAccess(person, community.getId())) {
            	return "community/accessDenied";
            }
        }

        List<CollectionGuide> wizards = communityManager.getPublishedCommunityWizards(communityId);

        Map<CollectionGuide, Integer> numElementsMap = new HashMap<CollectionGuide, Integer>();
        Map<CollectionGuide, Integer> percentageMap = new HashMap<CollectionGuide, Integer>();
        Map<CollectionGuide, Integer> totalWedsMap = new HashMap<CollectionGuide, Integer>();
        
        List<Message> msgs = cmm.findUnreadMessages(Integer.valueOf(communityId), person);
        
        List<CommunityResource> crs = crm.getResources(communityId);
        
        int totalNumElements = 0;
        int x = 0;
        
        List<Interact> interacts = ci.findCommunityInteracts(community);
        
        for (CollectionGuide guide : wizards) {
        	
            CollectionGuideUserData collectionGuideUserData = new CollectionGuideUserData(person, guide);
            int numElements = collectionGuideUserData.getNumberOfEntries();
            int percentage  = collectionGuideUserData.getPercentComplete();
            int wedsCount   = collectionGuideUserData.getTotalWeds();
            
            totalNumElements += numElements;
            
        	if ( guide.getTitle().length() > MAX_NAME_LENGTH) {
        		
        		String name = guide.getTitle();
        		int cut_point = -1;
        		
        		for(int i=MAX_NAME_LENGTH - 1; i>=0; i--) {
        			char c = name.charAt(i);
        			
        			if (c == ' ' && i >= (MAX_NAME_LENGTH * 0.66)) {
        				cut_point = i;
        				break;
        			}
        			else if ( i < (MAX_NAME_LENGTH * 0.66) ) {
        				cut_point = (int)(MAX_NAME_LENGTH * 0.66);
        				break;
        			}
         			
        		}
        		
        		name = name.substring(0, cut_point);
        		name += "...";
        		
        		guide.setCollectionGuideUserData(collectionGuideUserData);
        		guide.setTitle(name);
        		
        		wizards.set(x, guide);
        		
        	}


            numElementsMap.put(guide, numElements);
            percentageMap.put(guide, percentage);
            totalWedsMap.put(guide, wedsCount);
            
            ++x;
        }
        
        request.setAttribute("totalNumElements", totalNumElements);
        request.setAttribute("wizards", wizards);
        request.setAttribute("numElementsMap", numElementsMap);
        request.setAttribute("percentageMap", percentageMap);
        request.setAttribute("totalWedsMap", totalWedsMap);

        request.setAttribute("messages", msgs);
        
        request.setAttribute("messageCnt", msgs.size());
        
        int numMembers = communityManager.getMembers(communityId, CommunityRoleType.MEMBER).size();
        request.setAttribute("numMembers", numMembers);

        List<CommunityRoleType> roles = communityManager.getRolesByCommunityIdAndPersonId(communityId, person.getPersonId());
        request.setAttribute("roles", roles);

        boolean hasEditCommunityAccess = false;
        boolean isEvaluator = false;
        
        if (roles.contains(CommunityRoleType.COMMUNITY_COORDINATOR) || person.isAdmin()) {
        	hasEditCommunityAccess = true;
        }
        
        if(roles.contains(CommunityRoleType.EVALUATOR)) {
        	isEvaluator = true;
        }
        
        request.setAttribute("hasEditCommunityAccess", hasEditCommunityAccess );
        request.setAttribute("isEvaluator", isEvaluator);
        
        // request.setAttribute("hasEditCommunityAccess", roles.contains(CommunityRoleType.COMMUNITY_COORDINATOR) || person.isAdmin());
        // request.setAttribute("isEvaluator", roles.contains(CommunityRoleType.EVALUATOR) || person.isAdmin());

        List<Objective> objectiveSets = communityManager.getCommunityObjectiveSets(communityId);
        request.setAttribute("objectiveSets", objectiveSets);

        List<Person> communityCoordinators = communityManager.getMembers(communityId, CommunityRoleType.COMMUNITY_COORDINATOR);
        request.setAttribute("communityCoordinators", communityCoordinators);

        List<? extends ElementDataObject> resources = communityManager.getCommunityResources(community);
 
        List<Link> links = communityManager.getCommunityLinks(community);
        Collections.sort(links, ElementDataObject.PLACE_ORDER);
        
		if(links.size() > 5) {
			
			List<Link> cleanList = new ArrayList<Link>();

			cleanList.addAll(links.subList(0, 5));
			
			links.clear();
			
			links.addAll(cleanList);
		
			Collections.sort(links, ElementDataObject.PLACE_ORDER);
		}
		
		request.setAttribute("links", links);
        
		//request.setAttribute("links", communityManager.getCommunityLinks(community));

        request.setAttribute("resources", resources);

        request.setAttribute("community", community);

        Map<Template, Integer> totalMap = new HashMap<Template, Integer>();
        Map<Template, Integer> createdByMeMap = new HashMap<Template, Integer>();
        Map<Template, Integer> createdByOthersMap = new HashMap<Template, Integer>();
        
        List<Template> templates = communityManager.getPublishedCommunityTemplates(communityId);
        
        /*
        List<Portfolio> myP = new ArrayList<Portfolio>();
        List<Portfolio> sharedP = new ArrayList<Portfolio>();
        */
        
        x = 0;
        int unread = 0;
        int unassessed = 0;
        
        int totNumberOfMyPortfolios     = 0;
        int totNumberOfSharedPortfolios = 0;
        int totPortfolios               = 0; 
        
        for (Template template : templates) {      	
        	
            int numberOfMyPortfolios = getNumberOfMyPortfolios(person, template);
            int numberOfSharedPortfolios = getNumberOfSharedPortfolios(person, template);
            
            unread += getNumberOfUnreadSharedPortfolios(person, template);
            
            /*
            Set<Portfolio> sharedPortfolios = getSharedPortfolios(person, template);
            
            for(Iterator<Portfolio> i = sharedPortfolios.iterator(); i.hasNext();) {
            	
            	Portfolio p = i.next();
            	
            	List<Assessment> assessments = assessmentManager.findLatestPortfolioAssessments(p.getShareId(), null, null);
            	
            	if(assessments.size() == 0) {
            		unassessed++;
            	}
            	
            	if ( p.getShareName().length() > MAX_NAME_LENGTH) {
            		
            		String name = p.getShareName();
            		int cut_point = -1;
            		
            		for(int idx=MAX_NAME_LENGTH - 1; idx>=0; idx--) {
            			char c = name.charAt(idx);
            			
            			if (c == ' ' && idx >= (MAX_NAME_LENGTH * 0.66)) {
            				cut_point = idx;
            				break;
            			}
            			else if ( idx < (MAX_NAME_LENGTH * 0.66) ) {
            				cut_point = (int)(MAX_NAME_LENGTH * 0.66);
            				break;
            			}
             			
            		}
            		
            		name = name.substring(0, cut_point);
            		name += "...";

            		p.setShareName(name);

            	}
            	
            	
            	
            	sharedP.add(p);
            	
            }
            */
            /*
             
            Set<Portfolio> myPortfolios = getMyPortfolios(person, template);
            
            for(Iterator<Portfolio> i = myPortfolios.iterator(); i.hasNext();) {
            	
            	Portfolio p = i.next();
            	
            	if ( p.getShareName().length() > MAX_NAME_LENGTH) {
            		
            		String name = p.getShareName();
            		int cut_point = -1;
            		
            		for(int idx=MAX_NAME_LENGTH - 1; idx>=0; idx--) {
            			char c = name.charAt(idx);
            			
            			if (c == ' ' && idx >= (MAX_NAME_LENGTH * 0.66)) {
            				cut_point = idx;
            				break;
            			}
            			else if ( idx < (MAX_NAME_LENGTH * 0.66) ) {
            				cut_point = (int)(MAX_NAME_LENGTH * 0.66);
            				break;
            			}
             			
            		}
            		
            		name = name.substring(0, cut_point);
            		name += "...";

            		p.setShareName(name);

            	}
            	
            	myP.add(p);
            	
            }
            */
            
        	if ( template.getName().length() > MAX_NAME_LENGTH) {
        		
        		String name = template.getName();
        		int cut_point = -1;
        		
        		for(int i=MAX_NAME_LENGTH - 1; i>=0; i--) {
        			char c = name.charAt(i);
        			
        			if (c == ' ' && i >= (MAX_NAME_LENGTH * 0.66)) {
        				cut_point = i;
        				break;
        			}
        			else if ( i < (MAX_NAME_LENGTH * 0.66) ) {
        				cut_point = (int)(MAX_NAME_LENGTH * 0.66);
        				break;
        			}
         			
        		}
        		
        		name = name.substring(0, cut_point);
        		name += "...";

        		template.setName(name);
        		templates.set(x, template);
        		
        		
        	}
            
        	totNumberOfMyPortfolios += numberOfMyPortfolios;
        	totNumberOfSharedPortfolios += numberOfSharedPortfolios;
        	
            createdByMeMap.put(template, numberOfMyPortfolios);
            createdByOthersMap.put(template, numberOfSharedPortfolios);
            totalMap.put(template, numberOfMyPortfolios + numberOfSharedPortfolios);
            
            totPortfolios += numberOfMyPortfolios + numberOfSharedPortfolios;
            
            ++x;
        }

        PortfolioSearchCriteria criteria = new PortfolioSearchCriteria(person.getPersonId()).setCommunityId(community.getId());
        //Set<Portfolio> portfolios = portfolioSearch.findByCriteria(criteria);
        
        int portSize = portfolioSearch.findSizeByCriteria(criteria);
        
        request.setAttribute("unread", unread);
        request.setAttribute("unassessed", unassessed);
        
        request.setAttribute("totPortfolios", totPortfolios);
        request.setAttribute("total", portSize);
        request.setAttribute("totalMap", totalMap);
        request.setAttribute("createdByMeMap", createdByMeMap);
        request.setAttribute("createdByMeCnt", totNumberOfMyPortfolios);
        request.setAttribute("createdByOthersCnt", totNumberOfSharedPortfolios);
        request.setAttribute("createdByOthersMap", createdByOthersMap);
        request.setAttribute("templates", templates);
        request.setAttribute("totTemplates", templates.size());
        // request.setAttribute("sharedTemplates", sharedP);
        // request.setAttribute("myTemplates", myP);
        
        request.setAttribute("interacts", interacts);
        
        if(crs.size() > 5) {
        	
        	List<CommunityResource> tempCRS = new ArrayList<CommunityResource>();
        	
        	tempCRS.addAll(crs.subList(0, 5));
        	
        	crs.clear();
        	
        	crs.addAll(tempCRS);
        	
        }
        
        request.setAttribute("communityResources", crs);
        
        return "community/communityHome";
    }
    
    @RequestMapping("/community/copy/{id}")
    public String communityCopy(@PathVariable("id") String communityId,
    							HttpServletRequest request) {
    
    	Community community = communityManager.getCommunityById(communityId);
    	
    	request.setAttribute("community", community);
    	
    	return "community/copyCommunity";
    }
    
    
    @RequestMapping("/community/copy/post/{id}")
    public String communityCopyPost(@PathVariable("id") String communityId,
    							@RequestParam(value = "name", required = true) String name,
    							@RequestParam(value = "members", required = false) String members,
    							@RequestParam(value = "objectives", required = false) String objectives,
    							HttpServletRequest request) {
    	
    	String oldId = communityId;
    	
        Person person = RequestUtils.getPerson(request);
        
        Community community = communityManager.getCommunityById(communityId);
        
        communityId = null;
        
        if( person.isAdmin() ) {
        	
        	boolean copyMembers = false;
        	boolean copyObjectives = false;
        	
        	if(members != null && members.contentEquals("true")) {
        		logService.debug("==> Members copied");
        		copyMembers = true;
        	}
        	else {
        		logService.debug("==> Members NOT copied");
        	}

        	if(objectives != null && objectives.contentEquals("true")) {
        		logService.debug("==> Objectives copied");
        		copyObjectives = true;
        	}
        	else {
        		logService.debug("==> Objectives NOT copied");
        	}

        	if(name != null && name.length() > 0) {
        		
        		int id = communityManager.copyCommunity(community, name, copyMembers, copyObjectives);
        		
        		if (id > 0) {
        			communityId = Integer.toString(id);
        		}
        		else {
        			communityId = null;
        		}
        		
        	}
        	
        }

        return communityId != null ? "redirect:/community/" + communityId : "redirect:/community/" + oldId;
        
    }
    
    /*
    private Set<Portfolio> getSharedPortfolios(Person person, Template template) {
        PortfolioSearchCriteria criteria = new PortfolioSearchCriteria(person.getPersonId())
        .setTemplateId(template.getId())
        .setSharedPortfoliosOnly(true);
        
        Set<Portfolio> sharedPortfolios = portfolioSearch.findByCriteria(criteria);

        return sharedPortfolios;
    }
    */
    
    private int getNumberOfSharedPortfolios(Person person, Template template) {
        PortfolioSearchCriteria criteria = new PortfolioSearchCriteria(person.getPersonId())
                .setTemplateId(template.getId())
                .setSharedPortfoliosOnly(true);
        Set<Portfolio> sharedPortfolios = portfolioSearch.findByCriteria(criteria);
        return sharedPortfolios.size();
    }
    
    private int getNumberOfUnreadSharedPortfolios(Person person, Template template) {
        
    	PortfolioSearchCriteria criteria = new PortfolioSearchCriteria(person.getPersonId())
                .setTemplateId(template.getId())
                .setSharedPortfoliosOnly(true)
                .setIsPublic(false)
                .setRead(false);
        
    	int port_size = portfolioSearch.findSizeByCriteria(criteria);
    	
        // Set<Portfolio> sharedPortfolios = portfolioSearch.findByCriteria(criteria);
        
        return port_size; // sharedPortfolios.size();
    }
    
    /*
    private Set<Portfolio> getMyPortfolios(Person person, Template template) {
        PortfolioSearchCriteria criteria = new PortfolioSearchCriteria(person.getPersonId()).setTemplateId(template.getId()).setMyPortfoliosOnly(true);
        Set<Portfolio> myPortfolios = portfolioSearch.findByCriteria(criteria);
        
        return myPortfolios;
    }
    */

    private int getNumberOfMyPortfolios(Person person, Template template) {
        PortfolioSearchCriteria criteria = new PortfolioSearchCriteria(person.getPersonId())
                .setTemplateId(template.getId())
                .setMyPortfoliosOnly(true);
        // Set<Portfolio> myPortfolios = portfolioSearch.findByCriteria(criteria);
        
        int port_size = portfolioSearch.findSizeByCriteria(criteria);
        
        return port_size; // myPortfolios.size();
    }
}
