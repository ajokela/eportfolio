/* $Name:  $ */
/* $Id: CommunityDirectoryController.java,v 1.4 2010/10/27 19:24:56 ajokela Exp $ */
package org.portfolio.client.controller.community;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.portfolio.bus.community.CommunityManager;
import org.portfolio.client.RequestUtils;
import org.portfolio.client.controller.ApplicationController;
import org.portfolio.model.Person;
import org.portfolio.model.community.Community;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CommunityDirectoryController extends ApplicationController {

    @Autowired
    private CommunityManager communityManager;

    @RequestMapping("/community/directory")
    public String execute(HttpSession session, Model model) {
        Person person = RequestUtils.getPerson(session);
        
        List<Community> allCommunities = communityManager.getPublicCommunities();
        if (person.isAdmin()) {
            allCommunities.addAll(communityManager.getDeletedCommunities());
            allCommunities.addAll(communityManager.getPrivateCommunities());
        }
        
        Collections.sort(allCommunities);

        List<Community> yourCommunities = communityManager.getCommunitiesByPersonId(person.getPersonId());
        Collections.sort(yourCommunities);

        model.addAttribute("yourCommunities", yourCommunities);
        model.addAttribute("allCommunities", allCommunities);
        return "community/communityDirectory";
    }
}
