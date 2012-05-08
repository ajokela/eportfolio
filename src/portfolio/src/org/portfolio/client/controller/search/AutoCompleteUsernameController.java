/* $Name:  $ */
/* $Id: CommunityController.java,v 1.20 2011/03/24 19:03:26 ajokela Exp $ */
package org.portfolio.client.controller.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.portfolio.client.RequestUtils;
import org.portfolio.client.controller.ApplicationController;
import org.portfolio.dao.PersonHome;
import org.portfolio.model.Person;
import org.portfolio.model.Person.UserType;
import org.portfolio.util.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class AutoCompleteUsernameController extends ApplicationController {

	@Autowired private PersonHome personHome;
	
	protected LogService logService = new LogService(this.getClass());
    
    @RequestMapping("/auto_complete_username")
    public String auto_complete_username(@RequestParam(value="query", required=false) String query, HttpServletRequest request) {
    	
    	Person person = RequestUtils.getPerson(request);
    	
    	List<Person> peoples = new ArrayList<Person>();
    	new HashMap<Person, Integer>();
    	List<UserType> userTypes = new ArrayList<UserType>();
    	
    	
    	
    	//List<Portfolio> portfolios = portfolioHome.findByOwner(person);
    	
    	if(query != null) {
    		
    		query = query.trim();
    		
    		String[] parts = query.split(",");
    		query = parts[0].trim();
    		
			userTypes.add(UserType.ADMIN);
			userTypes.add(UserType.MEMBER); 	
			userTypes.add(UserType.GUEST);
			
    		logService.debug("===> Searching for [" + query + "]");
    		
    		peoples = personHome.findBy(query, userTypes, person, 10);

    	}
    	
    	logService.debug("==> Found " + peoples.size() + " peoples...");
    	
    	request.setAttribute("peoples", peoples);
    	request.setAttribute("peoples_count",peoples.size());
    	
    	return "search/auto_complete_username";
    }

}