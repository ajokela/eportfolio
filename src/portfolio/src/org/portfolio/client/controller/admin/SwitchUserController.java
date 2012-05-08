/* $Name:  $ */
/* $Id: SwitchUserController.java,v 1.15 2011/01/18 20:30:06 ajokela Exp $ */
package org.portfolio.client.controller.admin;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import org.portfolio.bus.ViewerSearch;
import org.portfolio.client.RequestUtils;
import org.portfolio.client.security.authentication.PortfolioAuthenticator;
import org.portfolio.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.apache.commons.codec.binary.Base64;

@Controller
public class SwitchUserController {

    @Autowired
    private ViewerSearch viewerSearch;

    @Autowired
    @Qualifier("member")
    private PortfolioAuthenticator portfolioAuthenticator;

    @RequestMapping("/admin/switchUser/{username}")
    public String switchUser(@PathVariable("username") String username, HttpServletRequest request) throws LoginException {
        Person realPerson = RequestUtils.getPerson(request);
        String url = "/";
        String match = "no_match";
        
        if (!realPerson.isAdmin()) {
            throw new SecurityException("Insufficient Privileges");
        }

        byte[] user = Base64.decodeBase64(username);
        
        username = new String(user);
        
        Person newPerson = viewerSearch.findMemberViewer(username);

        if (newPerson == null) {
        	url = "/admin/userNotFound?" + username + "&encoded=" + new String(user) + "&match=" + match;
        }
        else {
	        portfolioAuthenticator.switchUser(request, newPerson);
	
	        if (StringUtils.hasText(request.getHeader("Referer"))) {
	            url = request.getHeader("Referer");
	        }
        }
	     
        return "redirect:" + url;
    }

    @RequestMapping("/admin/switchBack")
    public String switchBack(HttpServletRequest request) throws LoginException {
        portfolioAuthenticator.switchBack(request);

        String url = "/";
        if (StringUtils.hasText(request.getHeader("Referer"))) {
            url = request.getHeader("Referer");
        }

        return "redirect:" + url;
    }
}
