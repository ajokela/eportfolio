/* $Name:  $ */
/* $Id: CommunityMessageController.java,v 1.3 2011/01/18 20:30:06 ajokela Exp $ */
package org.portfolio.client.controller.community;

import java.io.IOException;
import java.io.Writer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.portfolio.bus.community.CommunityAuthorizationManager;
import org.portfolio.bus.community.CommunityManager;
import org.portfolio.bus.community.CommunityMessageManager;
import org.portfolio.client.RequestUtils;
import org.portfolio.client.controller.ApplicationController;
import org.portfolio.model.Person;
import org.portfolio.model.community.Community;
import org.portfolio.model.community.CommunityRoleType;
import org.portfolio.model.community.Message;
import org.portfolio.util.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import net.sf.json.JSONObject;

@Controller
public class CommunityMessageController extends ApplicationController {

    @Autowired private CommunityManager communityManager;
    @Autowired private CommunityAuthorizationManager communityAuthorizationManager;
    @Autowired private CommunityMessageManager cmm;
    
    private LogService logService = new LogService(this.getClass());
    
    @RequestMapping("/community/message_send/{communityId}")
    public void sendMessage(  @PathVariable("communityId") String communityId, 
    							@RequestParam("message") String message, 
    							@RequestParam("subject") String subject,
    							@RequestParam("started_at") String started_at,
    							@RequestParam("ended_at") String ended_at,
    							@RequestParam(value = "messageId", required = false) String messageId,
    				    		HttpServletRequest request,
    				            HttpServletResponse response,
    				            HttpSession session,
    				            Writer out) throws ParseException, IOException {
    	
        Person person = RequestUtils.getPerson(request);
        JSONObject reply = new JSONObject();
        
        Community community = communityManager.getCommunityById(communityId);
        
        try {
        
	        if (community.isPrivateCommunity()) {
	            if(!communityAuthorizationManager.verifyMemberAccess(person, community.getId())) {
	            	reply.element("status", "failure");
	                out.write(reply.toString());
	            	return;
	            }
	        }
	
	        List<CommunityRoleType> roles = communityManager.getRolesByCommunityIdAndPersonId(communityId, person.getPersonId());
	
	        if( !roles.contains(CommunityRoleType.COMMUNITY_COORDINATOR) && !person.isAdmin()) {
	        	logService.debug("Current Person is not a Coordinator");
	        }
	        else {
		        
		        Message msg    = new Message();
		        
		        Date s_at;
		        Date e_at;
		        
		        if (started_at.contentEquals("None")|| started_at.contentEquals("")) {
		        	s_at = null;
		        }
		        else {
		        	
		        	
		        	try {
		        		s_at = new SimpleDateFormat("MM/dd/yyyy").parse(started_at);
		        	}
		        	catch(Exception e) {
		        		s_at = null;
		        	}
		        }
		        
		        if (ended_at.contentEquals("None") || ended_at.contentEquals("")) {
		        	e_at = null;
		        }
		        else {
		        	
		        	try {
		        		e_at = new SimpleDateFormat("MM/dd/yyyy").parse(ended_at);
		        	}
		        	catch(Exception e) {
		        		e_at = null;
		        	}
		        }
		        
		        msg.setId(messageId == null ? -1 : Integer.valueOf(messageId));
		        msg.setMessageTxt(message);
		        msg.setSubject(subject);
		        msg.setCommunityId(community.getId());
		        msg.setCoordinatorId(person.getPersonId());
		        msg.setStartedAt(s_at);
		        msg.setEndedAt(e_at);
		        
		        cmm.saveMessage(msg);
		        
		        response.setContentType("application/json");
		        
		        reply.element("status", "ok");
		        out.write(reply.toString());
		        return;
	        }	        
        }
        catch(SecurityException e) {
        	logService.debug(e);
        }
        
        reply.element("status", "failure");
        out.write(reply.toString());
       
    }
    
    
    @RequestMapping("/community/message/delete/{communityid}")
    public void delete( @RequestParam("messageId") String messageId,
    		@PathVariable("communityid") String communityId, 
    		HttpServletRequest request,
            HttpServletResponse response,
            HttpSession session,
            Writer out) throws IOException {

    	JSONObject reply = new JSONObject();
    	
    	Person person = RequestUtils.getPerson(request);
    	
    	List<Person> members = new ArrayList<Person>();
    	
    	members.addAll(communityManager.getMembers(communityId, CommunityRoleType.ASSESSMENT_COORDINATOR));
    	members.addAll(communityManager.getMembers(communityId, CommunityRoleType.COMMUNITY_COORDINATOR));
    	
    	boolean member = false;

    	if(members.contains(person) || person.isAdmin()) {
    		member = true;
    	}
    	
    	if(member) {
    		
    		boolean status = cmm.setMessageRemoved(Integer.valueOf(messageId));
    		
    		reply.element("status", status ? "ok" : "failure");
    			
    	}
    	else {
    		reply.element("status", "failure");
    	}
    	
    	response.setContentType("application/json");
    	
    	out.write(reply.toString());
    }
    
    @RequestMapping("/community/message/edit/{communityid}/{messageid}")
    public String edit_message(  @PathVariable("communityid") String communityId,
    						@PathVariable("messageid") String messageId,
    							HttpServletRequest request) throws ParseException {
    
    	Person person = RequestUtils.getPerson(request);
    	
    	List<Person> members = new ArrayList<Person>();
    	Community community = communityManager.getCommunityById(communityId);
    	
    	members.addAll(communityManager.getMembers(communityId, CommunityRoleType.ASSESSMENT_COORDINATOR));
    	members.addAll(communityManager.getMembers(communityId, CommunityRoleType.COMMUNITY_COORDINATOR));
    	members.addAll(communityManager.getMembers(communityId, CommunityRoleType.EVALUATOR));
    	
    	boolean member = false;
    	boolean found  = false;

    	if(members.contains(person) || person.isAdmin()) {
    		member = true;
    	}
    	
    	Message msg = cmm.findMessageById(Integer.valueOf(messageId));
    	
    	if(msg != null) {
    		found = true;
    	}
    	
    	if(!member || !found) {
    		
    		if(!member) {
    			logService.debug("'" + person.getDisplayName() + "' is not a member of community " + communityId);
    		}
    		
    		return "/community/error/" + communityId; 	
    	}
    	
    	List<Message> msgs = cmm.findMessagesByCommunity(Integer.valueOf(communityId), person);
    	
    	request.setAttribute("messages", msgs);
    	request.setAttribute("message", msg);
    	request.setAttribute("community", community);
    	
    	cmm.setMessageRead(msg, person);
    	
    	return "community/communityEditMessage";
    }
    
    @RequestMapping("/community/error/{communityid}")
    public String error(@PathVariable("communityid") String communityId, HttpServletRequest request) {
    	return "community/error";
    }
    
    @RequestMapping("/community/message/{communityid}/{messageid}")
    public String message(  @PathVariable("communityid") String communityId,
    						@PathVariable("messageid") String messageId,
    							HttpServletRequest request) throws ParseException {
    
    	Person person = RequestUtils.getPerson(request);
    	
    	List<Person> members = communityManager.getMembers(communityId, CommunityRoleType.MEMBER);
    	Community community = communityManager.getCommunityById(communityId);
    	
    	members.addAll(communityManager.getMembers(communityId, CommunityRoleType.ASSESSMENT_COORDINATOR));
    	members.addAll(communityManager.getMembers(communityId, CommunityRoleType.COMMUNITY_COORDINATOR));
    	members.addAll(communityManager.getMembers(communityId, CommunityRoleType.EVALUATOR));
    	
    	boolean member = false;
    	boolean found  = false;

    	if(members.contains(person) || person.isAdmin()) {
    		member = true;
    	}
    	
    	Message msg = cmm.findMessageById(Integer.valueOf(messageId));
    	
    	if(msg != null) {
    		found = true;
    	}
    	
    	if(!member || !found) {
    		
    		if(!member) {
    			logService.debug("'" + person.getDisplayName() + "' is not a member of community " + communityId);
    		}
    		
    		return "/community/" + communityId; 	
    	}
    	
    	List<Message> msgs = cmm.findMessagesByCommunity(Integer.valueOf(communityId), person);
    	
    	request.setAttribute("messages", msgs);
    	request.setAttribute("message", msg);
    	request.setAttribute("community", community);
    	
    	cmm.setMessageRead(msg, person);
    	
    	return "community/communityShowMessage";
    }
    	

    @RequestMapping("/community/messages/{id}")
    public String messages(@PathVariable("id") String communityId, HttpServletRequest request) {
        Person person = RequestUtils.getPerson(request);

        Community community = communityManager.getCommunityById(communityId);
        
        if (community.isPrivateCommunity()) {
            if(!communityAuthorizationManager.verifyMemberAccess(person, community.getId())) {
            	
            }
        }

        List<Message> msgs = cmm.findMessagesByCommunity(community.getId(), person);
        
        request.setAttribute("community", community);
        request.setAttribute("messages", msgs);
        
        return "community/communityShowMessages";
        
    }
    
    @RequestMapping("/community/messages/manage/{id}")
    public String newMessage(@PathVariable("id") String communityId, HttpServletRequest request) {
        Person person = RequestUtils.getPerson(request);

        Community community = communityManager.getCommunityById(communityId);
        
        if (community.isPrivateCommunity()) {
            if(!communityAuthorizationManager.verifyMemberAccess(person, community.getId())) {
            	return "community/accessDenied";
            }
        }

        List<Message> msgs = cmm.findMessagesByCommunity(community.getId(), person);
        
        request.setAttribute("community", community);
        request.setAttribute("messages", msgs);
        
        return "community/communityMessages";
        
    }
    
}
