/* $Name:  $ */
/* $Id: LoginController.java,v 1.6 2011/03/17 19:15:29 ajokela Exp $ */
package org.portfolio.client.controller.login;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.portfolio.client.RequestUtils;
import org.portfolio.client.controller.ApplicationController;
import org.portfolio.client.controller.NoAuthentication;
import org.portfolio.client.security.authentication.PortfolioAuthenticator;
import org.portfolio.model.Person;
import org.portfolio.util.Configuration;
import org.portfolio.util.LogService;
import org.portfolio.util.PortfolioConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@NoAuthentication
public class LoginController extends ApplicationController {

    @Autowired
    @Qualifier("member")
    private PortfolioAuthenticator portfolioAuthenticator;

    private String baseUrl = Configuration.get("portfolio.base.url");
    private String loginUrlPattern =  Configuration.get("portfolio.login.pattern"); 

    private LogService logService = new LogService(this.getClass());
    
    @RequestMapping("/login")
    public String memberLogin(HttpServletRequest request, HttpSession session, HttpServletResponse response) {

    	int count = StringUtils.countMatches(loginUrlPattern, "%s");
    	String[] baseUrls;
    	
    	if(count > 0) {
    		
    	}
    	else {
    		count = 1;	
    	}
    	
    	baseUrls = new String[count];
    	
    	for(int i=0; i<count; ++i) {
    		baseUrls[i] = baseUrl;
    	}
    	
    	String loginUrl = String.format(loginUrlPattern, (Object[])baseUrls);
    	
        try {
            session.setAttribute(PortfolioConstants.AUTHENTICATOR_SESSION_KEY, portfolioAuthenticator);
            portfolioAuthenticator.login(request);

            if (RequestUtils.getPerson(session) == null) {
            	logService.debug("ERROR:  RequestUtils.getPerson(session) == null");
            	return "redirect:" + loginUrl;
            }
            
        } catch (LoginException e) {
        	logService.debug(e);
            return "redirect:" + loginUrl;
        }
        
        try {
        	Person person = RequestUtils.getPerson(session);
        	
        	if(!person.isEnabled()) {
        		return "redirect:/user/disabled";
        	}
        	
        }
        catch (Exception e) {
        	logService.debug(e);
        }
    	
        return "redirect:/";
    }
}
