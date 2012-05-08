/* $Name:  $ */
/* $Id: UserController.java,v 1.1 2011/03/17 19:16:28 ajokela Exp $ */
package org.portfolio.client.controller.user;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


import org.portfolio.client.RequestUtils;
import org.portfolio.client.controller.ApplicationController;
import org.portfolio.dao.PersonHome;
import org.portfolio.model.Person;
import org.portfolio.util.Configuration;
import org.portfolio.util.LogService;
import org.portfolio.util.Mailer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.apache.commons.codec.binary.Base64;

import groovy.lang.Writable;
import groovy.text.SimpleTemplateEngine;

@Controller
public class UserController extends ApplicationController {

	@Autowired private PersonHome personHome;
	@Autowired private Mailer mailer;
	
	@SuppressWarnings("unused")
	private LogService logService = new LogService(this.getClass());
    
    private String emailText = "" +

	"****************************************************************\n" +
	"Hello ${owner.firstName}:\n" +
	"\n" +
	"This email is to inform you that your ePortfolio account \n" +
	"with the $institutionLongName ($institutionShortName),\n" +
	"locate at $url, has been disabled.\n" +
	"\n" +
	"If you have further questions regarding this, please email\n" +
	"$email.\n\n" +
	
	"****************************************************************\n" + 
	"ePortfolio is a secure web site at the $institutionLongName ($institutionShortName) for saving, organizing, viewing, and selectively sharing personal educational records.\n";

	
	@RequestMapping(value="/user/disable", method=RequestMethod.POST) 
	public String userDisable(@RequestParam("username") String username,
							  @RequestParam(value="email_user", required=false) String emailUser, HttpServletRequest request) throws Exception {

		Person person = RequestUtils.getPerson(request);
    	
    	if(!person.isAdmin()) {
    		return "redirect:/";
    	}

    	Person p = personHome.findByUsername(username);
    	
    	if(emailUser != null) {
    		request.setAttribute("emailUser", "true");
    	}
    	else {
    		request.setAttribute("emailUser", "false");
    	}
    	
    	request.setAttribute("person", p);

		return "user/disable";
	}
	
    @RequestMapping(value="/user/disable/{username}/{email_user}", method=RequestMethod.GET)
    public String userDisableVerified( @PathVariable("username") String username,
    								   @PathVariable("email_user") String emailUser,
    								   HttpServletRequest request) throws Exception {
    	
    	Person person = RequestUtils.getPerson(request);
    	
    	if(!person.isAdmin()) {
    		return "redirect:/";
    	}
    	
    	username = new String(Base64.decodeBase64(username.getBytes()));
    	
    	Person p = personHome.findByUsername(username);
    	
    	if(p != null) {
    		p.setIsEnabled(false);
    		
    		personHome.store(p);
    	}
    	
    	request.setAttribute("userDisabled", p);
    	
    	if(emailUser != null && emailUser.contentEquals("true")) {
	        Map<String, Object> binding = new HashMap<String, Object>();
	        
	        binding.put("creator", person);
	        binding.put("owner", p);
	        binding.put("date", new Date());
	        binding.put("institutionLongName", Configuration.get("institution.longName"));
	        binding.put("institutionShortName", Configuration.get("institution.shortName"));
	        binding.put("systemContactEmail", Configuration.get("portfolio.contact.email"));
	        binding.put("email", Configuration.get("portfolio.contact.email"));
	        binding.put("url", Configuration.get("portfolio.base.url"));
	        
	        SimpleTemplateEngine engine = new SimpleTemplateEngine();
	        Writable template = engine.createTemplate(emailText).make(binding);
        
	        String email;
	        
	        if(!Configuration.get("portfolio.base.url").contains("portfolio")) {
	        	email = new String("ajokela@umn.edu");
	        }
	        else {
	        	email = Configuration.get("portfolio.contact.email");
	        }
	        
	        mailer.sendMail(template.toString(), "Notice from " + Configuration.get("institution.shortName") + " ePortfolio" , p.getEmail(), Configuration.get("portfolio.contact.email"), new String[] {email});

	        request.setAttribute("mailSent", "true");
    	}
        
    	return "admin/index";
    }
    
    @RequestMapping(value="/user/disabled", method=RequestMethod.GET)
    public String userDisabled( HttpServletRequest request) throws Exception {
    
    	return "user/disabled";
    }
}
    