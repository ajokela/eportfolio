/* $Name:  $ */
/* $Id: CommunityInfoController.java,v 1.2 2011/01/18 20:30:06 ajokela Exp $ */
package org.portfolio.client.controller.community;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.portfolio.bus.PortfolioSearch;
import org.portfolio.bus.community.CommunityAuthorizationManager;
import org.portfolio.bus.community.CommunityManager;
import org.portfolio.client.RequestUtils;
import org.portfolio.client.controller.ApplicationController;
import org.portfolio.model.ElementDataObject;
import org.portfolio.model.Person;
import org.portfolio.model.Portfolio;
import org.portfolio.model.PortfolioSearchCriteria;
import org.portfolio.model.Template;
import org.portfolio.model.assessment.Objective;
import org.portfolio.model.community.Community;
import org.portfolio.model.community.CommunityRoleType;
import org.portfolio.model.wizard.CollectionGuide;
import org.portfolio.model.wizard.CollectionGuideUserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CommunityInfoController extends ApplicationController {

    @Autowired
    private CommunityManager communityManager;
    @Autowired
    private CommunityAuthorizationManager communityAuthorizationManager;
    @Autowired
    private PortfolioSearch portfolioSearch;

    @RequestMapping("/community/info/{id}")
    public String view(@PathVariable("id") String communityId, HttpServletRequest request) {
        Person person = RequestUtils.getPerson(request);

        Community community = communityManager.getCommunityById(communityId);
        if (community.isPrivateCommunity()) {
            if(!communityAuthorizationManager.verifyMemberAccess(person, community.getId())) {
            	return "community/accessDenied";
            }
        }

        List<CollectionGuide> wizards = communityManager.getPublishedCommunityWizards(communityId);

        Map<CollectionGuide, Integer> numElementsMap = new HashMap<CollectionGuide, Integer>();
        Map<CollectionGuide, Integer> percentageMap = new HashMap<CollectionGuide, Integer>();

        int totalNumElements = 0;
        for (CollectionGuide guide : wizards) {
            CollectionGuideUserData collectionGuideUserData = new CollectionGuideUserData(person, guide);
            int numElements = collectionGuideUserData.getNumberOfEntries();
            int percentage = collectionGuideUserData.getPercentComplete();

            totalNumElements += numElements;

            numElementsMap.put(guide, numElements);
            percentageMap.put(guide, percentage);
        }
        request.setAttribute("totalNumElements", totalNumElements);
        request.setAttribute("wizards", wizards);
        request.setAttribute("numElementsMap", numElementsMap);
        request.setAttribute("percentageMap", percentageMap);

        int numMembers = communityManager.getMembers(communityId, CommunityRoleType.MEMBER).size();
        request.setAttribute("numMembers", numMembers);

        List<CommunityRoleType> roles = communityManager.getRolesByCommunityIdAndPersonId(communityId, person.getPersonId());
        request.setAttribute("roles", roles);

        request.setAttribute("hasEditCommunityAccess", roles.contains(CommunityRoleType.COMMUNITY_COORDINATOR));

        List<Objective> objectiveSets = communityManager.getCommunityObjectiveSets(communityId);
        request.setAttribute("objectiveSets", objectiveSets);

        List<Person> communityCoordinators = communityManager.getMembers(communityId, CommunityRoleType.COMMUNITY_COORDINATOR);
        request.setAttribute("communityCoordinators", communityCoordinators);

        List<? extends ElementDataObject> links = communityManager.getCommunityLinks(community);
		Collections.sort(links, ElementDataObject.PLACE_ORDER);
		request.setAttribute("links", links);
        //request.setAttribute("links", communityManager.getCommunityLinks(community));

        request.setAttribute("community", community);

        Map<Template, Integer> totalMap = new HashMap<Template, Integer>();
        Map<Template, Integer> createdByMeMap = new HashMap<Template, Integer>();
        Map<Template, Integer> createdByOthersMap = new HashMap<Template, Integer>();

        List<Template> templates = communityManager.getPublishedCommunityTemplates(communityId);
        for (Template template : templates) {
            int numberOfMyPortfolios = getNumberOfMyPortfolios(person, template);
            int numberOfSharedPortfolios = getNumberOfSharedPortfolios(person, template);
            createdByMeMap.put(template, numberOfMyPortfolios);
            createdByOthersMap.put(template, numberOfSharedPortfolios);
            totalMap.put(template, numberOfMyPortfolios + numberOfSharedPortfolios);
        }

        PortfolioSearchCriteria criteria = new PortfolioSearchCriteria(person.getPersonId()).setCommunityId(community.getId());
        Set<Portfolio> portfolios = portfolioSearch.findByCriteria(criteria);

        request.setAttribute("total", portfolios.size());
        request.setAttribute("totalMap", totalMap);
        request.setAttribute("createdByMeMap", createdByMeMap);
        request.setAttribute("createdByOthersMap", createdByOthersMap);
        request.setAttribute("templates", templates);
        return "community/communityInfo";
    }

    private int getNumberOfSharedPortfolios(Person person, Template template) {
        PortfolioSearchCriteria criteria = new PortfolioSearchCriteria(person.getPersonId())
                .setTemplateId(template.getId())
                .setSharedPortfoliosOnly(true);
        Set<Portfolio> sharedPortfolios = portfolioSearch.findByCriteria(criteria);
        return sharedPortfolios.size();
    }

    private int getNumberOfMyPortfolios(Person person, Template template) {
        PortfolioSearchCriteria criteria = new PortfolioSearchCriteria(person.getPersonId())
                .setTemplateId(template.getId())
                .setMyPortfoliosOnly(true);
        Set<Portfolio> sharedPortfolios = portfolioSearch.findByCriteria(criteria);
        return sharedPortfolios.size();
    }
}
