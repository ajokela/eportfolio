/* $Name:  $ */
/* $Id: SendPasswordAction.java,v 1.14 2011/03/17 19:15:29 ajokela Exp $ */
package org.portfolio.client.action.account;

import groovy.lang.Writable;
import groovy.text.SimpleTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.portfolio.client.action.BaseAction;
import org.portfolio.dao.PersonHome;
import org.portfolio.model.Person;
import org.portfolio.model.Person.UserType;
import org.portfolio.util.Configuration;
import org.portfolio.util.LogService;
import org.portfolio.util.Mailer;
import org.portfolio.util.PortfolioConstants;
import org.portfolio.util.RequiredInjection;

public class SendPasswordAction extends BaseAction {
    
    private Mailer mailer;
    
    private PersonHome personHome;

    @SuppressWarnings("unused")
	private LogService logService = new LogService(this.getClass());
    
	private String emailText = "" +

		"****************************************************************\n" +
		"You recently requested your password for your Portfolio account.\n" +
		"Here's your Portfolio Account Information:\n" +
		"\n" +
		"Your registered email address is : ${person.email}\n\n" +
		"Password : ${person.password}\n" +
		"\n" +
		"You can modify your Portfolio Account Information by:\n" +
		"\t1. Logging into Portfolio\n" + 
		"\t2. clicking on the 'Account Info' link\n" + 
		"\t\t(located in the main menu bar on the left)\n" + 
		"\t3. make changes to your name and password as needed\n" + 
		"\t4. click the 'Save Changes' button to submit your changes\n" + 
		"\n" +
		"Questions, comments, or concerns can be sent to $systemContactEmail\n\n" +
	
		"****************************************************************\n" + 
		"Portfolio is a secure web site at the $institutionLongName ($institutionShortName) for saving, organizing, viewing, and selectively sharing personal educational records.\n";

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    	throws Exception {
		
    	ActionMessages messages = new ActionMessages();
        ActionForward forward = null;

		Person person = null;
        String identifier = null;
        identifier = request.getParameter("email");

        if(identifier == null || identifier.length() == 0) {
        	// no email entered, send back to form
        	ActionMessage message = new ActionMessage("message.sendPassword.nodata");
        	messages.add("nodata", message);
        	saveMessages(request, messages);
        	forward = mapping.findForward(FORWARD_FAILURE);
        	return forward;
        } else {
    		person = personHome.findByEmail(identifier);

            if (person != null && person.isEnabled()) {
                // figure out of the person is a guest or member
            	UserType type = person.getUsertype();

                if (type.toString().equalsIgnoreCase(PortfolioConstants.GUEST) || 
                	type.toString().equalsIgnoreCase(PortfolioConstants.MEMBER) ||
                	type.toString().equalsIgnoreCase(PortfolioConstants.ADMIN)) {
	            	// guest account found, send email
	                Map<String, Object> binding = new HashMap<String, Object>();
	                
	                binding.put("person", person);
	                binding.put("institutionLongName", Configuration.get("institution.longName"));
	                binding.put("institutionShortName", Configuration.get("institution.shortName"));
	                binding.put("systemContactEmail", Configuration.get("portfolio.contact.email"));        
	                
	                SimpleTemplateEngine engine = new SimpleTemplateEngine();
	                Writable template = engine.createTemplate(emailText).make(binding);
	            
	                try {
	                	String email = person.getEmail();
	                	String from = Configuration.get("portfolio.contact.email");
	                	mailer.sendMail(template.toString(), "Portfolio Account Information - Password Request", email, from);
	                	
	                	ActionMessage message = new ActionMessage("message.sendPassword.sent");
	                	messages.add("success", message);
	                	saveMessages(request, messages);
	                	forward = mapping.findForward(FORWARD_FAILURE); //not really failure, but this is where I want to go!
	                	return forward;
	                } catch (Exception ex) {
	                    ActionMessage message = new ActionMessage("message.sendPassword.failedEmail");
	                	messages.add("failed", message);
	                	saveMessages(request, messages);
	                	forward = mapping.findForward(FORWARD_FAILURE);
	                	return forward;
	                }
                } /* else {
                	// member account found, tell them to contact Help Desk
                	ActionMessage message = new ActionMessage("message.sendPassword.member");
                	messages.add("member", message);
                	saveMessages(request, messages);
                	forward = mapping.findForward(FORWARD_FAILURE);
                	return forward;
                }
                */
            } /*else {
        		// person is not found

            }
            */
            
        	ActionMessage message = new ActionMessage("message.sendPassword.unknown", identifier);
        	messages.add("unknown", message);
        	saveMessages(request, messages);
        	forward = mapping.findForward(FORWARD_FAILURE);
        	return forward;
        }
    }

	@RequiredInjection
    public void setMailer(Mailer mailer) {
        this.mailer = mailer;
    }
	@RequiredInjection
    public void setPersonHome(PersonHome personHome) {
        this.personHome = personHome;
    }

}
