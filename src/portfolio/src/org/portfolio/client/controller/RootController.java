/* $Name:  $ */
/* $Id: RootController.java,v 1.7 2011/03/24 19:03:26 ajokela Exp $ */
package org.portfolio.client.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.portfolio.client.RequestUtils;
import org.portfolio.model.Person;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@NoAuthentication
public class RootController extends ApplicationController {

    @RequestMapping("/")
    public String execute(HttpServletRequest request, HttpSession session) {
        Person person = RequestUtils.getPerson(session);
        
        checkUserAgent(request);
        
        if (person != null) {
            return "forward:/welcome";
        } else {
            return "login";
        }
    }
}
