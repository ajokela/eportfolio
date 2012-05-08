/* $Name:  $ */
/* $Id: gLoginController.java,v 1.1 2011/02/16 20:25:08 ajokela Exp $ */
package org.portfolio.client.controller;


import javax.servlet.http.HttpServletRequest;

// import org.portfolio.util.LogService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@NoAuthentication
public class gLoginController extends ApplicationController {


    @RequestMapping("/glogin")
    public String execute(HttpServletRequest request) {
    	
        
        return "guestlogin";
    }
    
}
