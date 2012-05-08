/* $Name:  $ */
/* $Id: RootController.java,v 1.7 2011/03/24 19:03:26 ajokela Exp $ */
package org.portfolio.client.controller;

import groovy.lang.Writable;
import groovy.text.SimpleTemplateEngine;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.portfolio.bus.PortfolioManager;
import org.portfolio.dao.PersonHome;
import org.portfolio.model.Person;
import org.portfolio.model.Portfolio;
import org.portfolio.util.Configuration;
import org.portfolio.util.LogService;
import org.portfolio.util.Mailer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RequestAccessController extends ApplicationController {

	@Autowired private PersonHome personHome;
	@Autowired private PortfolioManager portfolioManager;
    @Autowired private Mailer mailer;
    
    private LogService logService = new LogService(this.getClass());
    
    @RequestMapping("/requestAccess")
    public String execute(@RequestParam(value = "owner", required = true) String owner,
    		@RequestParam(value = "viewer", required = false) String viewer,
    		@RequestParam(value = "portfolio", required = true) String portfolioId,
    	    HttpServletRequest request,
    	    HttpServletResponse response,
    	    HttpSession session) throws IOException {

    	boolean sent = false;
    	
        String emailTextPrivate = "****************************************************************\n";
        	emailTextPrivate += "You have received a request to share Portfolio \"$shareName\" with:\n\n";
        	emailTextPrivate += "$viewer ($viewerUserName)\n\n";
        	emailTextPrivate += "If you do not wish to share this Portfolio, no further action is required.\n\n";
        	emailTextPrivate += "To share the Portfolio with $viewer, please visit ${url}/portfolio/${portfolio}\n\n";
        	emailTextPrivate += "1) Click 'Share' (located toward the upper right)\n";
        	emailTextPrivate += "2) Click 'Add Viewers'\n";
        	emailTextPrivate += "3) Enter the person's email address or username into the 'E-mail address or UM Internet' field\n";
        	emailTextPrivate += "4) Click 'Add'\n";
        	emailTextPrivate += "5) Click 'Save'\n\n\n";
        	emailTextPrivate += "To allow the Portfolio to be publicly viewable, follow the following steps:\n\n";
        	emailTextPrivate += "1) Click 'Share' (located toward the upper right)\n";
        	emailTextPrivate += "2) Click the first 'Change'\n";
        	emailTextPrivate += "3) Select 'Public'\n";
        	emailTextPrivate += "4) Click 'Save'\n";
        	emailTextPrivate += "5) Click 'Save', again\n\n";
        	emailTextPrivate += "Questions, comments, or concerns can be sent to $systemContactEmail.\n\n";
        	emailTextPrivate += "****************************************************************\n";
        	emailTextPrivate += "Portfolio is a secure web site at the $institutionLongName ($institutionShortName) for saving, organizing, viewing, and selectively sharing personal educational records.";


        String emailTextPublic = "****************************************************************\n";
        	emailTextPublic += "You have received a request to share Portfolio \"$shareName\" publicly\n\n";
        	emailTextPublic += "If you do not wish to share this Portfolio, no further action is required.\n\n";
        	emailTextPublic += "To share the Portfolio with the public, please visit ${url}/portfolio/${portfolio}\n\n";
        	emailTextPublic += "1) Click 'Share' (located toward the upper right)\n\n";
        	emailTextPublic += "2) Click the first 'Change'\n\n";
        	emailTextPublic += "3) Select 'Public'\n\n";
        	emailTextPublic += "4) Click 'Save'\n\n";
        	emailTextPublic += "5) Click 'Save', again\n\n\n";
        	emailTextPublic += "Questions, comments, or concerns can be sent to $systemContactEmail.\n\n";
        	emailTextPublic += "****************************************************************\n";
        	emailTextPublic += "Portfolio is a secure web site at the $institutionLongName ($institutionShortName) for saving, organizing, viewing, and selectively sharing personal educational records.";

        	
    	if(owner != null && portfolioId != null) {
    		
    		Portfolio portfolio = portfolioManager.findById(portfolioId);
    		Person person = personHome.findByPersonId(owner);
    		
    		if(portfolio != null && person != null) {
    			
    			if(portfolio.getPersonId().contentEquals(person.getPersonId())) {
    				
    				Person viewerPerson = null;
    				
    				if(viewer != null) {
    					viewerPerson = personHome.findByPersonId(viewer);
    				}
    				
    				try {
    					if(viewerPerson != null) {
    						sendRequestNotification(viewerPerson, portfolio, emailTextPrivate);
    					}
    					else {
    						sendRequestNotification(null, portfolio, emailTextPublic);
    					}
    					
						sent = true;
					} catch (Exception e) {
						
						logService.debug(e);
					}
    			}
    			
    		}
    		
    	}
    	
    	if(sent) {
    		logService.debug("====> Message Sent");
    	} else {
    		logService.debug("====> Message not Sent");
    	}
    	    	
    	return "accessRequested";
    }
    
    private void sendRequestNotification(Person person, Portfolio portfolio, String emailText) throws Exception {
        String subject = "A Portfolio Share has been requested";

        String fromEmail = portfolio.getPerson().getEmail();
        
        Map<String, String>binding = new HashMap<String, String>();
        
        binding.put("viewer", person == null ? "the public" : person.getDisplayName());
        binding.put("viewerUserName", person == null ? "" : person.getUsername());
        binding.put("shareName", portfolio.getShareName());
        binding.put("url",  Configuration.get("portfolio.base.url"));
        binding.put("institutionLongName", Configuration.get("institution.longName"));
        binding.put("institutionShortName", Configuration.get("institution.shortName"));
        binding.put("systemContactEmail", Configuration.get("portfolio.contact.email"));
        binding.put("portfolio", portfolio.getShareId());
        
        SimpleTemplateEngine engine = new SimpleTemplateEngine();
        Writable template = engine.createTemplate(emailText).make(binding);
        
        String noreply = Configuration.get("epf.noreply");
        
        if(noreply == null) {
        	noreply = "noreply@umn.edu";
        }
        
        mailer.sendMail(template.toString(), subject, fromEmail, person == null ? noreply : person.getEmail());

    }

}
