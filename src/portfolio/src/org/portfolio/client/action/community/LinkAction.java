/* $Name:  $ */
/* $Id: LinkAction.java,v 1.10 2010/11/04 21:08:53 ajokela Exp $ */
package org.portfolio.client.action.community;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;
import org.portfolio.bus.ElementManager;
import org.portfolio.bus.community.CommunityAuthorizationManager;
import org.portfolio.bus.community.CommunityManager;
import org.portfolio.client.RequestUtils;
import org.portfolio.client.action.DispatchAction;
import org.portfolio.dao.PersonHome;
import org.portfolio.model.ElementDataObject;
import org.portfolio.model.EntryKey;
import org.portfolio.model.Link;
import org.portfolio.model.Person;
import org.portfolio.model.community.Community;
import org.portfolio.util.LogService;

/**
 * @author Matt Sheehan
 * 
 */
public class LinkAction extends DispatchAction {
	
	private LogService logService = new LogService(this.getClass());

    private CommunityManager communityManager;
    private CommunityAuthorizationManager communityAuthorizationManager;
    private ElementManager elementManager;
    private PersonHome personHome;
    public int numLinks;

    @DefaultMethod
    public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
    	
    	Person person = RequestUtils.getPerson(request);
    	
        String communityIdParam = request.getParameter("communityId");
        Integer communityId = Integer.parseInt(communityIdParam);

        Community community = communityManager.getCommunityById(communityId.toString());
        communityAuthorizationManager.verifyCommunityCoordinatorAccess(person, communityId);

//new-start
        // Implementation of user being able to rearrange order of links in the list
        // Link.place field was added and set to zero for all existing records
        // Records will be updated to have the correct "place" as they are viewed, based on default order (id?)
        // First link in a list will have place=1, so we can use place=0 to test for links that haven't been updated yet
        List<? extends ElementDataObject> links2 = null;
        List<? extends ElementDataObject> links = communityManager.getCommunityLinks(community); // get all the links for this community
        numLinks = links.size();
        if (numLinks == 0) {
        	logService.debug ("no links for this community");	
        } else {
        	logService.debug ("numLinks = " + numLinks);
        	
        	ElementDataObject first = links.get(0); // get the first link
    		Link ll = (Link)first;
    		if (ll.getPlace() == 0) { // if zero, need to update all links for this community
    			int n = 1;
    			for(ElementDataObject link : links ){
    				Link l = (Link)link;
    				l.setPlace(n);
    				n++;
    				elementManager.store(l);
    			}
    		}
        	
    		links2 = communityManager.getCommunityLinks(community); // re-get all the links for this community, in case they were updated
    		Collections.sort(links2, ElementDataObject.PLACE_ORDER);
    		
        }
        request.setAttribute("links", links2);
        //request.setAttribute("links", communityManager.getCommunityLinks(community));
//new-end
        
        request.setAttribute("community", community);
        return mapping.findForward("list");
    }

    public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
    	Person person = RequestUtils.getPerson(request);
        String entryKeyIdParam = request.getParameter("entryKeyId");

        EntryKey entryKey = new EntryKey(entryKeyIdParam);
        Link link = (Link) elementManager.findElementInstance(entryKey, person);
        String personId = link.getPersonId();
        Integer communityId = personHome.findByPersonId(personId).getCommunityId();

        Community community = communityManager.getCommunityById(communityId);

        communityAuthorizationManager.verifyCommunityCoordinatorAccess(getPerson(request), community.getId());
        request.setAttribute("link", link);
        request.setAttribute("community", community);
        return mapping.findForward("edit");
    }

    public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
    	Person person = RequestUtils.getPerson(request);
        String entryKeyIdParam = request.getParameter("entryKeyId");

        EntryKey entryKey = new EntryKey(entryKeyIdParam);
        Link link = (Link) elementManager.findElementInstance(entryKey, person);
        String personId = link.getPersonId();
        Integer communityId = personHome.findByPersonId(personId).getCommunityId();

        Community community = communityManager.getCommunityById(communityId);
        communityAuthorizationManager.verifyCommunityCoordinatorAccess(getPerson(request), community.getId());

        String nameParam = request.getParameter("name");
        String urlParam = request.getParameter("url");
        String descriptionParam = request.getParameter("description");

        link.setEntryName(nameParam);
        link.setUrl(urlParam);
        link.setDescription(descriptionParam);

        elementManager.store(link);
        return new ActionRedirect(mapping.findForward("update")).addParameter("communityId", community.getId());
    }

    public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
    	Person person = RequestUtils.getPerson(request);
        String entryKeyIdParam = request.getParameter("entryKeyId");
        String communityIdParam = request.getParameter("communityId");

        Community community = communityManager.getCommunityById(communityIdParam);
        communityAuthorizationManager.verifyCommunityCoordinatorAccess(getPerson(request), community.getId());

        EntryKey entryKey = new EntryKey(entryKeyIdParam);
        ElementDataObject link = elementManager.findElementInstance(entryKey, person);
        if (numLinks == 1) { // this is the only link, remove it
        	elementManager.remove(link);
        } else {
        	if (numLinks == ((Link) link).getPlace()) { // this is the last link, just remove it
            	elementManager.remove(link);
        	} else { // will need to renumber links after removing
            	elementManager.remove(link);
                List<? extends ElementDataObject> links = communityManager.getCommunityLinks(community); // get all the links for this community
        		Collections.sort(links, ElementDataObject.PLACE_ORDER);
        		int n = 1;
    			for(ElementDataObject link2 : links ){
    				Link l = (Link) elementManager.findElementInstance(link2.getEntryKey(), person);
    				l.setPlace(n);
    				n++;
    				elementManager.store(l);
    			}
        	}
        }
        return new ActionRedirect(mapping.findForward("delete")).addParameter("communityId", community.getId());
    }

    public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String communityIdParam = request.getParameter("communityId");

        Community community = communityManager.getCommunityById(communityIdParam);
        communityAuthorizationManager.verifyCommunityCoordinatorAccess(getPerson(request), community.getId());

        String nameParam = request.getParameter("name");
        String urlParam = request.getParameter("url");
        String descriptionParam = request.getParameter("description");

        Link link = (Link) elementManager.newInstance("epf_link");
        link.setEntryName(nameParam);
        link.setUrl(urlParam);
        link.setDescription(descriptionParam);
        link.setPersonId(community.getPerson().getPersonId());
        link.setPlace(numLinks+1);
        
        elementManager.store(link);
        return new ActionRedirect(mapping.findForward("save")).addParameter("communityId", communityIdParam);
    }

    public void setCommunityManager(CommunityManager communityManager) {
        this.communityManager = communityManager;
    }

    public void setCommunityAuthorizationManager(CommunityAuthorizationManager communityAuthorizationManager) {
        this.communityAuthorizationManager = communityAuthorizationManager;
    }

    public void setElementManager(ElementManager elementManager) {
        this.elementManager = elementManager;
    }

    public void setPersonHome(PersonHome personHome) {
        this.personHome = personHome;
    }
    
    // added by Becky K to deal with drag-drop reordering of links
    public ActionForward reorder(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    	throws Exception {
    	Person person = RequestUtils.getPerson(request);
    	String communityIdParam = request.getParameter("communityId");
        Community community = communityManager.getCommunityById(communityIdParam);
        communityAuthorizationManager.verifyCommunityCoordinatorAccess(getPerson(request), community.getId());
        
    	String dataParam = request.getParameter("data");
        List<String> newOrder = Arrays.asList(dataParam.split("&"));
        
        int n = 1;
		for(String p : newOrder ){
			String x = p.substring(11); // have to pull off 'linkList[]=' that came in from Sortable.serialize

	        EntryKey entryKey = new EntryKey(x);
	        Link link = (Link) elementManager.findElementInstance(entryKey, person);
//logService.debug ("place = " + link.getPlace() + " => " + n);
			link.setPlace(n);
			n++;
			elementManager.store(link);
		}
		
		//List<? extends ElementDataObject> links2 = communityManager.getCommunityLinks(community); // get all the links for this community
		//Collections.sort(links2, ElementDataObject.PLACE_ORDER);
		
        return mapping.findForward("list");
    }
}
