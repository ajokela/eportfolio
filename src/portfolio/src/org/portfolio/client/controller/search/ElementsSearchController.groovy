/* $Name:  $ */
/* $Id: ElementsSearchController.groovy,v 1.10 2011/02/24 21:49:16 ajokela Exp $ */
package org.portfolio.client.controller.search;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.sf.json.JSONObject;
import net.sf.json.groovy.JsonGroovyBuilder;

import org.portfolio.bus.TemplateManager
import org.portfolio.bus.EntrySearchCriteria
import org.portfolio.model.ElementDefinition
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
import org.portfolio.model.FileExt;
import org.portfolio.bus.FileExtManager;
import org.apache.commons.codec.binary.Base64;

/**
 * @author Matt Sheehan
 * 
 */
 @Controller
public class ElementsSearchController {
    
    @Autowired ElementDefinitionManager elementDefinitionManager
    @Autowired ElementTypeManager elementTypeManager
    @Autowired EntrySearch entrySearch
    @Autowired ElementFolderHome elementFolderHome
    @Autowired CommunityManager communityManager
	@Autowired FileExtManager fileExtManager;
	
	private LogService logService = new LogService(this.getClass());

    @RequestMapping(["/element/search"])
    public void execute(
            @RequestParam("searchType") String searchType,
            @RequestParam(value="searchArgs",required=false) String searchArgs, 
            HttpServletRequest request, 
            HttpServletResponse response, 
            Writer writer)
    throws Exception {
        response.contentType = "application/json"
        
        String personId = RequestUtils.getPersonId(request)
        def (description, matches) = runSearch(searchType, searchArgs, personId)
        
        def jsonResponse = [
                stat: 'ok',
                data: [ description: description ]
        ]
        
        jsonResponse.data.matches = matches.collect(entryToJsonFormat)
		
        writer.write JSONObject.fromObject(jsonResponse).toString();
    }
    
    @RequestMapping(["/element/search/contains"])
    public void searchContains(
            @RequestParam("entryKeyId") EntryKey entryKey,
            @RequestParam("searchType") String searchType,
            @RequestParam(value="searchArgs",required=false) String searchArgs, 
            HttpServletRequest request, 
            HttpServletResponse response, 
            Writer writer)
    throws Exception {
        response.contentType = "application/json"

        String personId = RequestUtils.getPersonId(request)
        def (description, matches) = runSearch(searchType, searchArgs, personId)
        
        def entry = matches.find {it.entryKey == entryKey}
        def jsonResponse = [
                stat: 'ok',
                data: [ contains: entry != null ]
        ]
        
        if (entry) {
            jsonResponse.data.entry = entryToJsonFormat(entry)
        }
            
        writer.write JSONObject.fromObject(jsonResponse).toString()
    }
    
    /*
     * DateUtil.relativeFormat(entry.modifiedDate)
     */
    
    def entryToJsonFormat = { entry ->
        [
            entryKeyId: entry.entryKey.id,
            entryName: entry.entryName,
            modifiedDate: entry.modifiedDate?.format('MM/dd/yy'),
            modifiedDateMillis: entry.modifiedDate?.time,
			uploadSize: entry.size,
			uploadFormattedSize: entry.formattedSize,
			typeDescription: getDescription(entry.fileName),
			isDeletable : entry.elementDefinition.isDeletable(),
            elementDefinition: [
                name: entry.elementDefinition.name,
                iconPath: entry.elementDefinition.iconPath
            ]
        ]

	} 
	
	def getDescription(String filename) {
		String desc = "File";
		
		if(filename != null) {

			String[] parts = filename.split("\\.");
			
			if(parts.length > 0) {
				FileExt fileExt = fileExtManager.findByExt(parts[parts.length - 1]);
				
				if (fileExt != null) {
					desc = fileExt.getDescription();
				}
				else {
					desc = parts[parts.length - 1].toUpperCase() + " File";
				}
			
				return desc;
			}
		}
		
		return desc;
	}
    
    /**
     * @ return [description, matches]
     */
    def runSearch(String searchType, String searchArgs, String personId){
        
        String description = null;
        EntrySearchCriteria criteria = new EntrySearchCriteria(personId)
        
        switch (searchType) {
			case "types":
			
				String[] parts = searchArgs.split(",");
				def elementDefs = new ArrayList<ElementDefinition>();
				
				for(int i=0; i<parts.size(); ++i) {
					def elementType = elementTypeManager.findById(parts[i]);
					def defs = elementDefinitionManager.findByElementTypeId(elementType.id)
					elementDefs.addAll(defs);
				}
				
				criteria.elementIds = elementDefs.collect {it.elementId} as String[];
				
				description = "";
			
				break
            case "type":
			
				String elementSearchType = searchArgs;
			
				if(searchArgs.contains("|")) {
					String[] parts = searchArgs.split("\\|");

					if(parts.size() > 0) {
						elementSearchType = parts[0];
					}
										
					if(parts.size() > 1) {
						logService.debug("==> query: " + parts[1]);
						
						byte[] query = Base64.decodeBase64(parts[1]);
						
						criteria.query = new String(query);
					}
					
				}
				
                def elementType = elementTypeManager.findById(elementSearchType);
				
				if(elementType != null) {
					def elementDefs = elementDefinitionManager.findByElementTypeId(elementType.id)
					criteria.elementIds = elementDefs.collect {it.elementId} as String[]
					description = "Elements by type: $elementType.name"
				}
				else {
					description = "";
				}
				
				break
				            
			case "elementDefinition":
                def elementDef =  elementDefinitionManager.findByElementId(searchArgs)
                criteria.elementIds = [elementDef.id]

                description = "Elements by element definition: $elementDef.name"
                break
            case "community":
                Community community = communityManager.getCommunityById(searchArgs)
                criteria.communityId = community.id
                description = "Elements by community: ${community.name}"
                break
            case "source":
                criteria.elementSourceId = searchArgs
                description = "Elements by source: $searchArgs"
                break
            case "folder":
                ElementFolder folder = elementFolderHome.findById(new BigDecimal(searchArgs))
                criteria.folderId = folder.id
                description = "Elements by folder: $folder.name"
                break
            case "unfiled":
                criteria.filed = false
                description = "Unfiled elements"
                break
            case "tag":
                criteria.tag = searchArgs
                description = "Elements by tag: $searchArgs"
                break
            case "category":
                criteria.category = searchArgs
                def catLabel = ElementType.Category.valueOf(searchArgs).toString()
                description = "Elements by Category: $catLabel"
                break
            case "search":
                criteria.query = searchArgs
                description = "Search results for \"$searchArgs\""
                break
            case "all": // fall through
            default: 
                description = "All elements";
        }
        
        def matches = entrySearch.findByCriteria(criteria).sort(ElementDataObject.DATE_MODIFIED_ORDER)
        return [description, matches]
    }
}
