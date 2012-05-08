/* $Name:  $ */
/* $Id: WelcomeController.java,v 1.7 2011/03/24 19:03:26 ajokela Exp $ */
package org.portfolio.client.controller;

// import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.portfolio.bus.EntrySearch;
import org.portfolio.bus.EntrySearchCriteria;
import org.portfolio.bus.PortfolioSearch;
import org.portfolio.bus.community.CommunityManager;
import org.portfolio.client.RequestUtils;
import org.portfolio.model.PortfolioSearchCriteria;
import org.portfolio.model.community.Community;
// import org.portfolio.util.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WelcomeController extends ApplicationController {

    private CommunityManager communityManager;
    private EntrySearch entrySearch;
    private PortfolioSearch portfolioSearch;

    
    
    @RequestMapping("/welcome")
    public String execute(HttpServletRequest request)
            throws Exception {
    	
        
        String personId = RequestUtils.getPersonId(request);
        List<Community> communities = communityManager.getCommunitiesByPersonId(personId);
        Collections.sort(communities);
        request.setAttribute("communities", communities);

        request.setAttribute("numElements", getNumElements(personId));
        request.setAttribute("numMyPortfolios", getNumMyPortfolios(personId));
        request.setAttribute("numSharedPortfolios", getNumSharedPortfolios(personId));
        
        // checkUserAgent(request);
        
        return "welcome";
    }
    
    private int getNumSharedPortfolios(String personId) {
        PortfolioSearchCriteria sharedPortfoliosCriteria = new PortfolioSearchCriteria(personId);
        sharedPortfoliosCriteria.setSharedPortfoliosOnly(true);
        
        int size = portfolioSearch.findSizeByCriteria(sharedPortfoliosCriteria);
        
        return size;
    }

    private int getNumMyPortfolios(String personId) {
        PortfolioSearchCriteria myPortfoliosCriteria = new PortfolioSearchCriteria(personId);
        myPortfoliosCriteria.setMyPortfoliosOnly(true);
        
        int size = portfolioSearch.findSizeByCriteria(myPortfoliosCriteria);
        
        return size;
    }

    private int getNumElements(String personId) {
        
    	EntrySearchCriteria elementSearchCriteria = new EntrySearchCriteria(personId);
        return entrySearch.findEntryKeysByCriteria(elementSearchCriteria).size();
        
    }

    @Autowired
    public void setPortfolioSearch(PortfolioSearch portfolioSearch) {
        this.portfolioSearch = portfolioSearch;
    }

    @Autowired
    public void setCommunityManager(CommunityManager communityManager) {
        this.communityManager = communityManager;
    }

    @Autowired
    public void setEntrySearch(EntrySearch entrySearch) {
        this.entrySearch = entrySearch;
    }

}
