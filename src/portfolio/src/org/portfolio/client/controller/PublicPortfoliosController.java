/* $Name:  $ */
/* $Id: PublicPortfoliosController.java,v 1.9 2011/01/27 17:37:01 ajokela Exp $ */
package org.portfolio.client.controller;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.List;
import java.util.Iterator;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.portfolio.bus.PortfolioTagManager;
import org.portfolio.dao.PersonHome;
import org.portfolio.dao.PortfolioHome;
import org.portfolio.model.Person;
import org.portfolio.model.Portfolio;
import org.portfolio.util.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@NoAuthentication
public class PublicPortfoliosController extends ApplicationController {

    @Autowired
    private PortfolioHome shareDefinitionHome;
    @Autowired
    private PortfolioTagManager tagMgr;
    @Autowired
    private PersonHome personHome;

    
	private LogService logService = new LogService(this.getClass());
    
    @RequestMapping("/public")
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("publicTagsForPublicPortfolios", JSONArray.fromObject(tagMgr.getTagNamesForPublicPortfolios()));
        
        request.setAttribute("firstRequest", "firstRequest");
        
        return "publicShareList";
    }

    @RequestMapping("/public/search")
    public String search(
            @RequestParam(value = "searchQuery", required = false) String searchQuery,
            Model model) {
    	
        Set<Portfolio> results = new LinkedHashSet<Portfolio>();
        Calendar cal = Calendar.getInstance();
        
        if (StringUtils.hasText(searchQuery) && searchQuery != null) {
    		
    		List<Person> peoples = personHome.findBy(searchQuery);
            
    		if (peoples != null) {
    			
    			for(Iterator<Person> it = peoples.iterator(); it.hasNext(); ) {
    				
    				Person person = it.next();
    				
                    results.addAll(shareDefinitionHome.findPublicByOwner(person.getPersonId()));
    			
    			}
    		
            }
            
    		results.addAll(tagMgr.queryPubByPubTag(searchQuery));
    		results.addAll(shareDefinitionHome.findPublicByNameOrDesc(searchQuery));

    		
        }

        if (searchQuery == null || (searchQuery != null && searchQuery.length() == 0) || ! StringUtils.hasText(searchQuery)) {
        	logService.debug("===> Searching for all...");
        	results.addAll(shareDefinitionHome.findAllPublic());
    	}
        
        model.addAttribute("searchQuery", searchQuery);
        Iterator<Portfolio> it;
        Set<Portfolio> new_results = new LinkedHashSet<Portfolio>();

        logService.debug("====> sizeof(results) = " + results.size());
        
        synchronized(results) {
        	
	        for(it = results.iterator(); it.hasNext(); ) {
	        	
	        	Portfolio pfolio = it.next();
	        	boolean            is_good = true;
	        	
	        	if ( !pfolio.isPublicView() ) {
	        		// results.remove(pfolio);
	        		is_good = false;
	        	}
	        	
	        	if ( pfolio.getDateExpire() != null && pfolio.getDateExpire().getTime() <= cal.getTime().getTime()) {
	        		// results.remove(pfolio);
	        		is_good = false;
	        	}
	        	
	        	if(is_good) {
	        		new_results.add(pfolio);
	        	}
	        	else {
	        		logService.debug("===> Not Good: " + pfolio.getShareId());
	        	}
	        	
	        }
	        
        }
        
        model.addAttribute("shares", new_results);
       
        logService.debug("====> sizeof(new_results) = " + new_results.size());
        
        return "forward:/public";
    }
}
