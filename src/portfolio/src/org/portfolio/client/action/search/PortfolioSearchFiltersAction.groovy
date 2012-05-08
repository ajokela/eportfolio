/* $Name:  $ */
/* $Id: PortfolioSearchFiltersAction.groovy,v 1.6 2011/02/09 19:40:32 ajokela Exp $ */
package org.portfolio.client.action.search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.portfolio.util.LogServiceimport net.sf.json.JSONArrayimport net.sf.json.groovy.JsonGroovyBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.portfolio.client.action.BaseAction;
import org.portfolio.dao.SharedPortfolioFolderHome;
import org.portfolio.dao.search.PortfolioSearchFiltersDataHome;
import org.portfolio.bus.community.CommunityManager
import org.portfolio.model.SharedPortfolioFolder;

import org.portfolio.model.Template;

import edu.umn.web.json.JsonSerializer;

/**
 * @author Matt Sheehan
 * 
 */
public class PortfolioSearchFiltersAction extends BaseAction {
	
	PortfolioSearchFiltersDataHome portfolioSearchFiltersDataHome;
	SharedPortfolioFolderHome sharedPortfolioFolderHome;
	CommunityManager communityManager;
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		response.setContentType("application/json");
		def personId = getPersonId(request)
		def communityDataByPersonId = portfolioSearchFiltersDataHome.findCommunityDataByPersonId(personId)
        def folders = sharedPortfolioFolderHome.findByPersonId(personId).sort()
        
		
		List<Template> templates = new ArrayList<Template>();
		
		communityDataByPersonId.each {community ->
			List<Template> tplates = communityManager.getAssessableCommunityTemplates(community.communityId);
			
			templates.addAll(tplates);
		}
		
		def builder = new JsonGroovyBuilder();		
		def json = builder.json {
			result {
			    type="Text"
			    label="All portfolios"
			    labelStyle="filter-all"
			    hash="all"
            }
			result {
                type="Text"
                label="Created by me"
                labelStyle="filter-my"
                hash="my"
		    }
			result {
                type="Text"
                label="Created by others"
                labelStyle="filter-shared"
                hash="shared"
            }
			result {
                type="Text"
                label="Starred"
                labelStyle="filter-starred"
                hash="starred"
            }
			result {
                type="Text"
                label="Unread"
                labelStyle="filter-unread"
                hash="unread"
            }
            result {
                type="Text"
                label="By template"
                labelStyle="filter-templates"
                hash=""
                templates.each { map ->
                    children {
                        type="Text"
                        label=map.name
                        labelStyle= "filter-templates"
                        hash= "advanced-search/templateId=${map.id}"
                    }
                }
            }
			result {
				type="Text"
				label="By community"
				labelStyle="filter-community"
				hash=""
				communityDataByPersonId.each { map ->
					children {
						type="Text"
						label=map.communityName
						labelStyle= "filter-community"
						hash= "community/${map.communityId}"
					}
				}
			}
            result {
                type="Text"
                label="By folder"
                labelStyle="filter-folder"
                hash=""
                children {
                    type="Text"
                    label="Unfiled"
                    labelStyle= "filter-unfiled"
                    hash= "unfiled"
                }
                folders.each {folder -> 
                    children {
                        type="Text"
                        label=folder.folderName
                        labelStyle= "filter-folder"
                        hash= "folder/${folder.folderId}"
                    }
                }
            }
		} 
		response.getWriter().write(json.toString());
		return null;
	}
}
