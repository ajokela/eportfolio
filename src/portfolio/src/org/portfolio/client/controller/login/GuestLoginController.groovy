/* $Name:  $ */
/* $Id: GuestLoginController.groovy,v 1.6 2011/03/17 19:15:29 ajokela Exp $ */
package org.portfolio.client.controller.login;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObjectimport org.portfolio.client.controller.NoAuthenticationimport org.portfolio.util.PortfolioConstantsimport javax.servlet.http.HttpSession
import org.portfolio.client.security.authentication.PortfolioAuthenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.portfolio.util.LogService;
import org.portfolio.model.Person;
import org.portfolio.client.RequestUtils;

@Controller
@NoAuthentication
public class GuestLoginController {

    @Autowired
    @Qualifier("guest") 
    private PortfolioAuthenticator portfolioAuthenticator;
	
	private LogService logService = new LogService(this.getClass());

    @RequestMapping(["/guestLogin"])
    public void execute(
            HttpServletRequest request, 
            HttpSession session, 
            HttpServletResponse response,
            Writer writer) {
			
        response.contentType = "application/json"
        
        def jsonResponse;
		
        try {
            session.setAttribute(PortfolioConstants.AUTHENTICATOR_SESSION_KEY, portfolioAuthenticator);
            portfolioAuthenticator.login(request);

			Person person = RequestUtils.getPerson(session);
			
			if(person != null && person.isEnabled()) {
				
				jsonResponse = [
	                stat: "ok"
	            ]
			
			}
			else {
				
				jsonResponse = [
					stat: "fail",
					error: "The user you are attempting to log in with has been disabled."
				]
			
			}
			
        } catch (LoginException e) {
		
            jsonResponse = [
                stat: "fail",
                error: "The username/password could not be found."
            ]
			
        }
        
        writer.write JSONObject.fromObject(jsonResponse).toString()
    }

	@RequestMapping(["/basicGuestLogin"])
	public void guestLogin(
			HttpServletRequest request,
			HttpSession session,
			HttpServletResponse response,
			Writer writer) {
			
		// response.contentType = "application/json"
		
		def jsonResponse;
		
		try {
			session.setAttribute(PortfolioConstants.AUTHENTICATOR_SESSION_KEY, portfolioAuthenticator);
			portfolioAuthenticator.login(request);

			logService.debug("==> Successful login - redirecting");
						
			/*
			jsonResponse = [
				stat: "ok"
			]
			*/
			
			// new RedirectView("/guestView.do", true);
			
			response.sendRedirect("/");
			
		} catch (LoginException e) {
		
			/*
			jsonResponse = [
				stat: "fail",
				error: "The username/password could not be found."
			]
			*/
		
			logService.debug("==> Failed login \n\n--------------\n" + e.getLocalizedMessage());
		
			// new RedirectView("/glogin", true);
			response.sendRedirect("/glogin");
			
		}
		
		// writer.write JSONObject.fromObject(jsonResponse).toString()
	}
			
}
