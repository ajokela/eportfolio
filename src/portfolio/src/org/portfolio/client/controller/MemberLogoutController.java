package org.portfolio.client.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@NoAuthentication
public class MemberLogoutController extends ApplicationController {
	
    @RequestMapping("/mlogout")
    public String execute(
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
    	
    	String redirect = "redirect:/";
    	
    	return redirect;
    }
    
}