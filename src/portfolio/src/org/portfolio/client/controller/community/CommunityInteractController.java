package org.portfolio.client.controller.community;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.portfolio.bus.community.CommunityAuthorizationManager;
import org.portfolio.bus.community.CommunityInteract;
import org.portfolio.client.RequestUtils;
import org.portfolio.client.controller.ApplicationController;
import org.portfolio.dao.community.CommunityHome;
import org.portfolio.model.Person;
import org.portfolio.model.community.Community;
import org.portfolio.model.community.Interact;
import org.portfolio.util.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CommunityInteractController extends ApplicationController {

	@Autowired
	private CommunityInteract communityInteract;
	
	@Autowired
	private CommunityHome communityHome;
	
	@Autowired
	private CommunityAuthorizationManager communityAuthorizationManager;
	
	private LogService logService = new LogService(this.getClass());
	
    @RequestMapping("/community/interact/{id}")
    public String view(@PathVariable("id") String communityId, HttpServletRequest request) {
    	
    	Person person = RequestUtils.getPerson(request);
    	
    	try{
    		communityAuthorizationManager.verifyCommunityCoordinatorAccess(person, Integer.valueOf(communityId));
    	}
    	catch(SecurityException e) {
    		return "redirect:/myCommunities.do";
    	}
    	
    	List<String> interactTypes = Interact.getTypesPretty();
    	
    	Community community = communityHome.findByCommunityId(communityId);
    	
    	if(community == null) {
    		return "redirect:/myCommunities.do";
    	}
    	
    	List<Interact> interacts = communityInteract.findCommunityInteracts(Integer.valueOf(communityId));
    	
    	request.setAttribute("interacts", interacts);
    	request.setAttribute("interactTypes", interactTypes);
    	request.setAttribute("community", community);
    	
    	return "community/interact";
    }
    
    @RequestMapping("/community/interact/save/{id}")
    public String save(@PathVariable("id") String communityId,
    				   @RequestParam("val") String val, 
    				   @RequestParam("action") String action, 
    				   @RequestParam("type") String type,
    				   HttpServletRequest request) throws Exception {
    	
    	Interact interact = new Interact();
    	
    	interact.setCommunityId(communityId);
    	interact.setType(type);
    	interact.setVal(val);

    	communityInteract.add(interact);
    	
    	return "redirect:/community/interact/" + communityId;
    }
    
    @RequestMapping("/community/interact/save/position")
    public void save_position(@RequestParam("communityId") String communityId,
    						  @RequestParam("data") String data,
    							HttpServletRequest request) {
    	
    	int pos = 0;
    	
    	// linkList[]=3&linkList[]=4
    	
    	String[] pieces = data.split("&");
    	
    	for(int i=0; i<pieces.length; ++i) {
    		String[] parts = pieces[i].split("\\=");

    		if(parts.length == 2) {
    			Interact interact = communityInteract.findById(Integer.valueOf(parts[1]));
    		
    			if(interact != null) {
    				logService.debug("Updating place on id[" + interact.getId() + "]");
    				interact.setPlace(pos);
    				communityInteract.update(interact);
    			}
    			
    			++pos;
    		}
    	}
    	
    	logService.debug("data: " + data + " - pieces.length: " + pieces.length);
    }
    
    @RequestMapping("/community/interact/delete/{id}/{interact_id}")
    public String delete(@PathVariable("id") String communityId,
    					 @PathVariable("interact_id") String interactId,
			   HttpServletRequest request) {
    	
    	communityInteract.remove(Integer.valueOf(interactId));
    	
    	return "redirect:/community/interact/" + communityId;
    }
    
}