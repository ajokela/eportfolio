/* $Name:  $ */
/* $Id: CommunityController.java,v 1.20 2011/03/24 19:03:26 ajokela Exp $ */
package org.portfolio.client.controller.community;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.sf.json.JSONObject;
import net.sf.json.groovy.JsonGroovyBuilder;

import org.portfolio.bus.TemplateManager

import org.portfolio.bus.EntrySearchCriteria
import org.portfolio.model.ElementDefinition
import org.portfolio.model.Link
import org.portfolio.bus.EntrySearch
import org.portfolio.bus.ElementDefinitionManager
import org.portfolio.dao.ElementFolderHome
import org.portfolio.model.ElementFolder
import org.portfolio.model.EntryKey
import org.portfolio.bus.ElementTypeManager
import org.portfolio.model.ElementDataObject
import java.lang.annotation.ElementType
import org.portfolio.model.ElementType

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.portfolio.client.RequestUtils
import org.springframework.beans.factory.annotation.Autowired

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.portfolio.model.community.CommunityRoleType;

import org.portfolio.bus.PortfolioSearch;
import org.portfolio.bus.community.CommunityManager;
import org.portfolio.client.action.BaseAction;

import org.portfolio.model.Portfolio;
import org.portfolio.model.PortfolioSearchCriteria;
import org.portfolio.model.community.Community;
import org.portfolio.util.DateUtil;
import org.portfolio.util.RequiredInjection;
import org.portfolio.util.LogService;
import org.portfolio.model.ElementDefinition;
import org.springframework.web.bind.annotation.PathVariable;
import org.portfolio.model.CommunityResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.portfolio.bus.CommunityResourceManager;
import org.portfolio.model.Person;
import org.portfolio.model.EntryKey;
import org.portfolio.util.StringUtil;
import org.apache.commons.codec.binary.Base64;
import java.util.regex.*;

@Controller
public class CommunityResourcesController {
	
	@Autowired
	private CommunityResourceManager communityResourceManager;
    
	@Autowired
	private CommunityManager communityManager;
	
	protected LogService logService = new LogService(this.getClass());

    @RequestMapping("/community/resources/{communityId}")
    public String getResources(@PathVariable("communityId") String communityId, 
							   HttpServletRequest request,
							   HttpServletResponse response, 
							   Writer writer) {
    	
		Community community = communityManager.getCommunityById(communityId);
		
		request.setAttribute("community", community);
							   
		return "community/communityResources";
	
    }
	
	@RequestMapping("/community/resources/ajax/{communityId}")
	public void ajaxResourcesList(@PathVariable("communityId") String communityId, 
							      @RequestParam(value = "searchParam", required = false) String searchParam,
							   HttpServletRequest request,
							   HttpServletResponse response, 
							   Writer writer) {
							   							   
		List<CommunityResource> resources = communityResourceManager.getResources(Integer.valueOf(communityId));
		List<Link> links = communityManager.getCommunityLinks(communityId);
		
		response.contentType = "application/json"

		if(searchParam != "" && searchParam != null) {
			
			int limitSearch = 0;
			
			try {
				byte[] bytes = Base64.decodeBase64(searchParam);
				searchParam = new String(bytes);
				if(searchParam != null) {
					searchParam = searchParam.toLowerCase();
				}
				else {
					searchParam = "";
				}
			}
			catch (Exception e) {
				logService.debug(e);	
			}
			
			logService.debug("searchParam = '" + searchParam + "'");
					
			List<CommunityResource> tempResources = new ArrayList<CommunityResource>();		
			tempResources.addAll(resources);
			
			List<Link> tempLinks = new ArrayList<Link>();
			tempLinks.addAll(links);
			
			
			if(searchParam.startsWith("res:")) {
				logService.debug("Match Only Resources...");
				limitSearch = 1;
				String[] parts = searchParam.split(":");
				
				if(parts.size() > 1) {
					searchParam = parts[1];	
				}
				else {
					searchParam = "";
				}
			}
			else if(searchParam.startsWith("link:")) {
				
				logService.debug("Match Only Links...");
				
				limitSearch = 2;
				String[] parts = searchParam.split(":");
				
				if(parts.size() > 1) {
					searchParam = parts[1];
				}
				else {
					searchParam = "";
				}
			}
			
			searchParam = searchParam.replaceAll("^ ", "");
			searchParam = searchParam.replaceAll(" \$", "");
			
			logService.debug("searching For: " + (limitSearch == 0 ? "" : (limitSearch == 1 ? "Resources" : (limitSearch == 2 ? "Links" : "Unknown"))) + " '" + searchParam + "'")
			
			if(limitSearch == 0 || limitSearch == 1) {			
			
				resources.collect {
					
					boolean keep = false;
					
					if(it.name != null && it.name.toLowerCase().contains(searchParam)) {
						keep = true;
					}
					
					if(it.owner.username != null && it.owner.username.toLowerCase().contains(searchParam)) {
						keep = true;
					}
	
					if(it.owner.displayName != null && it.owner.displayName.toLowerCase().contains(searchParam)) {
						keep = true;
					}
	
					if(it.description != null && it.description.toLowerCase().contains(searchParam)) {
						keep = true;
					}
	
					if(it.elementName != null && it.elementName.toLowerCase().contains(searchParam)) {
						keep = true;
					}
	
					if(StringUtil.join(it.tags, ",") != null && StringUtil.join(it.tags, ",").toLowerCase().contains(searchParam)) {
						keep = true;
					}
	
					if(!keep) {
						tempResources.remove(it);
					}
					
				}		
				
				resources.clear();
				resources.addAll(tempResources);
			}

			if(limitSearch == 0 || limitSearch == 2) {
				
				links.collect {
					boolean keep = false;
					
					if(it.description != null && it.description.toLowerCase().contains(searchParam)) {
						keep = true;
					}
					
					if(it.entryName != null && it.entryName.toLowerCase().contains(searchParam)) {
						keep = true;
					}
	
					if(it.url != null && it.url.toLowerCase().contains(searchParam)) {
						keep = true;
					}
					
					if(!keep) {
						tempLinks.remove(it);
					}
						
				}
			
				links.clear();
				links.addAll(tempLinks);
			
			}
			
		}
		
		Collections.sort(links, ElementDataObject.PLACE_ORDER);
					
		def jsonResponse = [
							stat: 'ok',
							data:  [
									resources: resources.collect { [
										
										 id: 				it.id,
										 communityId: 		it.communityId,
										 personId: 			it.personId,
										 entryId: 			it.entryId,
										 place: 			it.place,
										 createdAt: 		it.createdAt,
										 updatedAt: 		it.updatedAt,
										 name:				it.name,
										 encodedId:			it.encodedId,
										 username:      	it.owner.username,
										 displayName:   	it.owner.displayName,
										 description:   	it.description != null ? it.description : '',
										 elementName:   	it.elementName != null ? it.elementName : '',
										 shortName:     	it.shortName,
										 humanReadableSize: it.humanReadableSize,
										 tags:				StringUtil.join(it.tags, ",")
										 ] },
									
									 links: links.collect { [
										 entryName:		it.entryName,
										 description:	it.description != null ? it.description : '',
										 url:			it.url,
										 id:			it.entryId
										 ] }

									]
							];

						
		writer.write JSONObject.fromObject(jsonResponse).toString()
	}
   	
	@RequestMapping("/community/resources/add/{communityId}/{entryIds}")
	public void addResource(@PathVariable("communityId") String communityId,
							@PathVariable("entryIds") String entryIds,
							   HttpServletRequest request,
							   HttpServletResponse response, 
							   Writer writer) {
			
		Person person = RequestUtils.getPerson(request)
							   
	    def jsonResponse = [
							stat: 'ok',
							data: [
									description: 'Complete'
								  ]
							];
	   
		// logService.debug(entryIds);
	 
		String[] entries = entryIds.split(",");
		
		for(int i=0; i<entries.length; ++i) {
			EntryKey ek = new EntryKey(entries[i]);

			communityResourceManager.addResource(Integer.valueOf(communityId), ek);
		}
		
		response.contentType = "application/json"
		
		writer.write JSONObject.fromObject(jsonResponse).toString()

	}
							   

    @RequestMapping("/community/resources/del/{communityId}/{resourceId}")
	public void delResource(@PathVariable("communityId") String communityId,
				   @PathVariable("resourceId") String resourceId,
						  HttpServletRequest request,
						  HttpServletResponse response,
						  Writer writer) {
	   
		Person person = RequestUtils.getPerson(request)
		
		List<Person> coordinators = communityManager.getMembers(communityId, CommunityRoleType.COMMUNITY_COORDINATOR);
		
				
		def jsonResponse = [
							stat: 'ok',
							data: [
									description: 'Complete'
								  ]
							]
	  
		if(coordinators.contains(person) || person.isAdmin() ) {
			
			communityResourceManager.delResource(Integer.valueOf(resourceId));
		}
		else {
			logService.debug("Unable to delete[" + resourceId + "]...")
		}
	
		response.contentType = "application/json"
		
		writer.write JSONObject.fromObject(jsonResponse).toString()
   
	}
	   
}