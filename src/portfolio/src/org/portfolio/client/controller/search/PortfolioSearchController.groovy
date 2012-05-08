package org.portfolio.client.controller.search

import java.util.ArrayList
import java.util.Collections
import java.util.List
import org.portfolio.bus.TemplateManager
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestParam
import org.portfolio.model.Person
import org.portfolio.client.RequestUtils
import org.portfolio.util.DateUtil
import org.portfolio.util.LogService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.beans.factory.annotation.Autowired
import net.sf.json.JSONObject
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

import org.apache.struts.action.ActionForm
import org.apache.struts.action.ActionForward
import org.apache.struts.action.ActionMapping
import org.portfolio.bus.PortfolioSearch
import org.portfolio.bus.community.CommunityManager
import org.portfolio.client.action.BaseAction
import org.portfolio.client.action.search.PortfolioSearchFiltersAction
import org.portfolio.dao.SharedPortfolioFolderHome
import org.portfolio.model.Portfolio
import org.portfolio.model.BarePortfolio
import org.portfolio.model.Template
import org.portfolio.model.PortfolioSearchCriteria
import org.portfolio.model.SharedPortfolioFolder
import org.portfolio.model.community.Community
import org.portfolio.util.RequiredInjection
import org.springframework.web.bind.annotation.PathVariable
import org.portfolio.bus.PortfolioManager
import org.portfolio.util.Configuration
import org.portfolio.bus.assessment.AssessmentManager
import org.portfolio.model.assessment.Assessment

/**
 * @author Matt Sheehan and Alex Jokela
 * 
 */
@Controller
public class PortfolioSearchController {

    @Autowired private PortfolioSearch portfolioSearch
    @Autowired private PortfolioManager portfolioManager
    @Autowired private CommunityManager communityManager
    @Autowired private SharedPortfolioFolderHome sharedPortfolioFolderHome
    @Autowired private TemplateManager templateManager
	
	@Autowired private AssessmentManager assessmentManager

	private static final String SEARCH_TYPE_COUNT = "count"
    private final String portfolioBaseUrl = Configuration.get("portfolio.base.url")
	
	private final LogService logService = new LogService(this.getClass())
	
	@Override
    @RequestMapping(["/portfolio/search"])
	public void execute(
            @RequestParam("hash") String hashParam,
			@RequestParam(value="searchType", required=false) String searchT,
			@RequestParam(value="start", required=false) String strStart,
			@RequestParam(value="end", required=false) String strEnd,
			@RequestParam(value="sortType", required=false) String sortType,
	        HttpServletRequest request, 
	        HttpServletResponse response, 
            Writer writer)
	throws Exception {
        response.contentType = "application/json"
        
		String[] hashArgs = hashParam.split("/")
		String searchType = hashArgs[0]
		String searchArgs = hashArgs.length == 1 ? null : hashArgs[1]
		
		Person person = RequestUtils.getPerson(request)
		
		String description = null
		PortfolioSearchCriteria criteria = new PortfolioSearchCriteria(person.personId)
		
		if(searchT != null && searchT.compareToIgnoreCase(SEARCH_TYPE_COUNT) == 0) {
			searchType = SEARCH_TYPE_COUNT;	
		}
		
		int look_assessed = -1
		
		int count = 0
		int start = -1
		int end   = -1
		
		if(strStart != null && strEnd != null) {
			try {
				start = Integer.parseInt(strStart)
			}
			catch(Exception e) {
				
			}
			
			try {
				end = Integer.parseInt(strEnd)
			}
			catch(Exception e) {
				
			}
		}
		
		description = "All portfolios"
		
		switch (searchType) {
			case "my":
				criteria.myPortfoliosOnly = true
				description = "Portfolios created by me"
				break
			case "shared":
				criteria.sharedPortfoliosOnly = true
				description = "Portfolios created by others"
				break
			case "community":
				Community community = communityManager.getCommunityById(searchArgs)
				criteria.communityId = community.id
				description = "Portfolios by community: <a class=\"shareTitleBig\" href=\"/community/${community.id}\">${community.name}</a> "
				break
			case "assessed":
			
				Community community = communityManager.getCommunityById(searchArgs)
				criteria.communityId = community.id

				look_assessed = 0
				description = "Assessed Portfolios by community: <a class=\"shareTitleBig\" href=\"/community/${community.id}\">${community.name}</a> "
			
				break
			case "unassessed":
			
				Community community = communityManager.getCommunityById(searchArgs)
				
				List<Template> templates = communityManager.getPublishedCommunityTemplates(Integer.toString(community.getId()))
				
				if(templates != null) {
					
					if (templates.size() > 0) {
						criteria.setTemplates(templates)
					}
					else {
						criteria.setTemplates(null)
					}
				}
				else {
					criteria.setTemplates(null)
				}
				
				criteria.communityId = community.id

				criteria.setIsPublic(false)
				
				look_assessed = 1
				description = "Unassessed Portfolios by community: <a class=\"shareTitleBig\" href=\"/community/${community.id}\">${community.name}</a> "
				
				break
				
			case "community-unread":

				Community community = communityManager.getCommunityById(searchArgs)
				criteria.communityId = community.id
				criteria.read = false
				
				criteria.setSharedPortfoliosOnly(true)
				
				description = "Unread Portfolios by community: <a class=\"shareTitleBig\" href=\"/community/${community.id}\">${community.name}</a> "
			

				break
			case "starred":
				criteria.flagged = true
				description = "Starred portfolios"
				break
			case "unread":
				criteria.read = false
				description = "Unread portfolios"
				break
			case "folder":
				SharedPortfolioFolder folder = sharedPortfolioFolderHome.findById(new BigDecimal(searchArgs))
				criteria.folderId = folder.folderId
				description = "Portfolios by folder: ${folder.folderName}"
				break
			case "unfiled":
				criteria.filed = false
				description = "Unfiled portfolios"
				break
			case "search":
				criteria.query = searchArgs
				description = "Search results for &quot;$searchArgs&quot;"
				break
			case "advanced-search":
				description = processAdvancedSearch(criteria, searchArgs)
				break
			case "count":
				// criteria.returnType = PortfolioSearchCriteria.PORTFOLIOSEARCHCRITERIA_SIZE
				
				break
			case "all": // fall through
			default: 
				// description = "All portfolios"
				break
		}
		
		def jsonResponse = [
			stat: 'ok',
			data: [ description: '' ]
		]
		
		List<BarePortfolio> barePortfolios = portfolioSearch.findAllBarePortfoliosByCriteria(criteria)
		
		count = barePortfolios.size()
		
		if(start > -1 && end > -1) {
			
			if(sortType != null) {
				sortType = sortType.toLowerCase()
				
				if(sortType.compareToIgnoreCase(PortfolioSearch.PORTFOLIOSEARCH_SORT_BY_AUTHOR) == 0) {
					criteria.setSortBy(PortfolioSearchCriteria.PORTFOLIOSEARCHCRITERIA_ORDER_AUTHOR)
				}
				else if(sortType.compareToIgnoreCase(PortfolioSearch.PORTFOLIOSEARCH_SORT_BY_TITLE) == 0) {
					criteria.setSortBy(PortfolioSearchCriteria.PORTFOLIOSEARCHCRITERIA_ORDER_TITLE)
				}
				else {
					criteria.setSortBy(PortfolioSearchCriteria.PORTFOLIOSEARCHCRITERIA_ORDER_DATE)
				}
				
				
			}

			if(start >= count) {
				start = -1
			}
			
			if(end > count) {
				end = count
			}
			
			criteria.start = start
			criteria.end   = end
			
			List<BarePortfolio> set = portfolioSearch.findBarePortfoliosByCriteria(criteria, barePortfolios)
			
			if(criteria.returnType != PortfolioSearchCriteria.PORTFOLIOSEARCHCRITERIA_SIZE) {
				
				List<Portfolio> portfolios = portfolioSearch.findByBarePortfolioList(set) // portfolioSearch.findByCriteria(criteria).asList().sort(Portfolio.DATE_MODIFIED_ORDER)
				
				count = portfolios.size()
				
				def matches
				
				List<Portfolio> clean = new ArrayList<Portfolio>()
				
				if (look_assessed < 0) {
					clean = portfolios
				}
				else if (look_assessed == 0) {
					// look for portfolios that have been assessed
					
					for(Iterator<Portfolio> i = portfolios.iterator(); i.hasNext();) {
						Portfolio p = i.next()
						
						List<Assessment> assessments = assessmentManager.findLatestPortfolioAssessments(p.getShareId())
						
						if(assessments.size() > 0) {
							clean.add(p)
						}
							
					}
					
				}
				else if (look_assessed == 1) {
					// look for portfolios that have not been assessed
					
					for(Iterator<Portfolio> i = portfolios.iterator(); i.hasNext();) {
						
						Portfolio p = i.next()
						
						List<Assessment> assessments = assessmentManager.findLatestPortfolioAssessments(p.getShareId(), null, null)
						
						if(assessments.size() == 0) {
							clean.add(p)
						}
							
					}
		
				}
		 		
				if(criteria.getSortBy() == PortfolioSearchCriteria.PORTFOLIOSEARCHCRITERIA_ORDER_TITLE) {
					Collections.sort(clean, Portfolio.TITLE_ORDER)
				}
				else if(criteria.getSortBy() == PortfolioSearchCriteria.PORTFOLIOSEARCHCRITERIA_ORDER_AUTHOR) {
					Collections.sort(clean, Portfolio.AUTHOR_ORDER)
				}
				else {
					Collections.sort(clean, Portfolio.DATE_ORDER)
				}
				
				matches = clean
				
				jsonResponse.data.matches = matches.collect { portfolio ->
					convertPortfolioToJsonData(portfolio, person)
				}
		
			}
		}
		
		description += " (${count} found)"
        
		jsonResponse.data.count       = count
		jsonResponse.data.description = description
        
        jsonResponse.data.person = [
            id: person.personId,
            username: person.username
        ]
		
        writer.write JSONObject.fromObject(jsonResponse).toString()
	}
	


    private convertPortfolioToJsonData(portfolio, person) {
    	
        def viewer = portfolio.getViewerByPersonId(person.personId)
        [
            id: portfolio.shareId,
            name: portfolio.shareName,
            description: portfolio.shareDesc,
            owner: [
                displayName: portfolio.person.truncatedDisplayName,
                username:    portfolio.person.username
            ],
            dateModified:     portfolio.dateModified?.format('MM/dd/yy'),
            dateLastModified: portfolio.dateModified?.format('MM/dd/yy - h:mm a'),
            dateCreated:      portfolio.dateCreated?.format('MM/dd/yy - h:mm a'),
            expireDate:       portfolio.dateExpire?.format('MM/dd/yy'),
            tags: portfolio.tags,
            flagged: viewer.flagged,
            viewType: viewer.viewType.toString().toLowerCase(),
            publicView: portfolio.publicView,
            numViewers: portfolio.viewerViewers.size(),
            viewers: portfolio.viewerViewers.collect { v ->
                [
                    username: v.person.username,
                    displayName: v.person.displayName
                ]
            },
            url: "${portfolioBaseUrl}/portfolio/$portfolio.shareId".toString(),
			ajaxUserLookupString: portfolio.ajaxUserLookupString,
			templateName: portfolio.template != null ? portfolio.template.name : "",
			templateId: portfolio.template != null ? portfolio.template.id : -1,
			communityName: portfolio.template != null ? portfolio.community.name : "",
			communityId: portfolio.template != null ? portfolio.community.id : -1,
			historyString: portfolio.historyString,
			history: portfolio.history.collect { v ->
				[
					description: v.description,
					stamp: v.formattedStamp
				]
            },
        ]
    }


    @RequestMapping(["/portfolio/summary/{id}"])
	public void summary(
            @PathVariable("id") String id,
	        HttpServletRequest request,
	        HttpServletResponse response,
            Writer writer){
        response.contentType = "application/json"
        
		Person person = RequestUtils.getPerson(request)
        Portfolio portfolio = portfolioManager.findById(id)

        def jsonResponse = [
                stat: 'ok',
                data: [ portfolio: convertPortfolioToJsonData(portfolio, person) ]
        ]
        writer.write JSONObject.fromObject(jsonResponse).toString()
    }

	def processAdvancedSearch(criteria, searchArgs) {
		StringBuilder sb = new StringBuilder("Search results for: ")
		String[] advancedSearchArgs = searchArgs.split("&")
		for (String string : advancedSearchArgs) {
			String[] pair = string.split("=")
			String name = pair[0]
			String value = pair[1]
			switch (name) {
				case "communityId":
					def community = communityManager.getCommunityById(value)
					criteria.communityId = community.id
					sb.append "<span class=\"label\">Community:</span> <a class=\"shareTitle\" href=\"/community/${community.id}\">${community.name}</a> "
					break
				case "type":
					criteria.myPortfoliosOnly = "my".equals(value)
					criteria.sharedPortfoliosOnly = "shared".equals(value)
					def createdBy = "my".equals(value) ? "me" : "others"
					sb.append "<span class=\"label\">Created by:</span> $createdBy "
					break
				case "templateId":
					def template = templateManager.getTemplateById(value)
					def community = communityManager.getCommunityById(template.communityId)
					criteria.templateId = template.id
					sb.append "<span class=\"label\">Template:</span> ${template.name} <br /><span style=\"font-size: 10pt; color: #eee;\">(Community: <a class=\"shareTitle\" href=\"/community/${community.id}\">${community.name}</a>)</span> "
					break
				case "name":
					criteria.shareName = value
					sb.append "<span class=\"label\">Name contains:</span> $value "
					break
				case "tag": 
					criteria.addTag(value)
					sb.append "<span class=\"label\">Tagged as:</span> $value "
					break
			}
		}
		return sb.toString()
	}
}
