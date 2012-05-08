/* $Name:  $ */
/* $Id: ElementsSearchFiltersController.groovy,v 1.6 2010/11/04 21:08:53 ajokela Exp $ */
package org.portfolio.client.controller.element;

import java.io.PrintWriter;
import java.util.List;

import org.portfolio.client.RequestUtils;
import net.sf.json.groovy.JsonGroovyBuilder;import org.portfolio.bus.ElementDefinitionManager;import org.portfolio.bus.ElementManager;import org.portfolio.bus.ElementTypeManager;import org.portfolio.dao.ElementFolderHome;import org.portfolio.dao.search.ElementsSearchFiltersDataHome;
import org.portfolio.bus.TagManager;import org.portfolio.model.ElementType;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

// import com.sun.tools.javac.util.List;


/**
 * @author Matt Sheehan
 * 
 */
@Controller
public class ElementsSearchFiltersController {
	
	@Autowired ElementTypeManager elementTypeManager;
	@Autowired ElementDefinitionManager elementDefinitionManager;
	@Autowired ElementManager elementManager;
	@Autowired ElementFolderHome elementFolderHome;
	@Autowired TagManager tagManager;
	@Autowired ElementsSearchFiltersDataHome elementsSearchFiltersDataHome;
	
	@RequestMapping(["/collection/searchFilters"])
	public void execute(HttpServletRequest request, HttpServletResponse response, PrintWriter writer) {
		response.contentType = "application/json";
		def personId = RequestUtils.getPersonId(request);
		
		def populatedElementDefs = elementDefinitionManager.findAll().findAll{
		    elementManager.findCountByPersonId(it.elementId, personId) > 0
		}
		
		def populatedElementTypes = populatedElementDefs.collect{elementDef -> elementDef.elementType}.unique()
		populatedElementTypes.sort{it.name}
		
        def communityDataByPersonId = elementsSearchFiltersDataHome.findCommunityDataByPersonId(personId);
		
		def folders = elementFolderHome.findByPersonId(personId);
				
		List<String> tags = tagManager.getTagsByUser(personId);
		
		Collections.sort(tags, String.CASE_INSENSITIVE_ORDER);
		
		def builder = new JsonGroovyBuilder()		   
		def json = builder.json {
			result {
			    type="Text"
			    label="All elements"
			    labelStyle="filter-all"
			    hash="all"   
			    children {
	                type="Text"
	                label="Starred"
	                labelStyle="filter-starred"
	                hash="starred"
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
                label="By element type"
                labelStyle="filter-type"
                hash=""
                    populatedElementTypes.each {elementType ->
                    children {
                        type="Text"
                        label=elementType.name
                        labelStyle="filter-type"
                        hash="type/${elementType.id}"
                    }
                }
            }/*
            result { // TODO this should be dynamic
                type="Text"
                label="By source"
                labelStyle="filter-source"
                hash=""
                children {
                    type="Text"
                    label="ePortfolio"
                    labelStyle="filter-source"
                    hash="source/ePortfolio"
                }
            }
            */
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
                folders.sort().each {folder ->
                    children {
                        type="Text"
                        label=folder.name
                        labelStyle="filter-folder"
                        hash="folder/$folder.id"
                    }
                }
            }
            result {
                type="Text"
                label="By tag"
                labelStyle="filter-tag"
                hash=""
                tags.each {tag ->
                    children {
                        type="Text"
                        label=tag
                        labelStyle="filter-tag"
                        hash="tag/$tag"
                    }
                }
            }
            result {
                type="Text"
                label="By category"
                labelStyle="filter-category"
                hash=""
                ElementType.Category.values().each {cat ->
                    children {
                        type="Text"
                        label=cat.toString()
                        labelStyle="filter-category"
                        hash="category/"+cat.name()
                    }
                }
            }
		} 
		writer.write json.toString()
	}
}
