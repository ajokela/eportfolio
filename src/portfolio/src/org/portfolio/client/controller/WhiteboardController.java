/* $Name:  $ */
/* $Id: RootController.java,v 1.7 2011/03/24 19:03:26 ajokela Exp $ */
package org.portfolio.client.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.portfolio.bus.WhiteboardManager;
import org.portfolio.client.RequestUtils;
import org.portfolio.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WhiteboardController extends ApplicationController {

	@Autowired
	private WhiteboardManager whiteboardManager;
	
    @RequestMapping("/whiteboard/{community_id}/{lines}/{session_id}/{grouping}/{mode}")
    public String execute(@PathVariable("lines") String lines,
    					  @PathVariable("session_id") String session_id,
    					  @PathVariable("grouping") int grouping,
    					  @PathVariable("mode") String mode,
    					  @PathVariable("community_id") int community_id,
    					  HttpServletRequest request, HttpSession session) {
    	
        Person person = RequestUtils.getPerson(session);
        
        if(mode != null) {
        	
        	if(mode.contentEquals("DeleteAndRetrieve")) {
        		
        	}
        	else if(mode.contentEquals("SendAndRetrieve")) {
        		whiteboardManager.newLine(lines, session_id, community_id, grouping, person);
        	}
        	else if(mode.contentEquals("Retrieve")) {
        		
        	}
        	
        }

        return "";
    }
}
