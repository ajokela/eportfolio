/* $Name:  $ */
/* $Id: RenderHtmlController.groovy,v 1.14 2011/01/31 03:37:12 shee0109 Exp $ */
/* $Id: RenderHtmlController.groovy,v 1.14 2011/01/31 03:37:12 shee0109 Exp $ */
package org.portfolio.client.controller.render

import java.util.ArrayList
import java.util.Iterator
import java.util.List

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession

import org.portfolio.bus.CommentsManager
import org.portfolio.bus.PermissionsManager
import org.portfolio.bus.PortfolioManager
import org.portfolio.bus.PortfolioManagerImpl
import org.portfolio.bus.community.CommunityAuthorizationManager
import org.portfolio.client.RequestUtils
import org.portfolio.client.controller.ApplicationController
import org.portfolio.client.controller.NoAuthentication
import org.portfolio.dao.ViewerHome
import org.portfolio.model.TemplateCategory
import org.portfolio.model.assessment.AssessmentModelAssignment
import org.portfolio.util.LogService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import groovy.xml.MarkupBuilder
import org.portfolio.model.Viewer
import org.portfolio.model.Person
import org.portfolio.model.Portfolio
import org.portfolio.model.ShareEntry
import org.portfolio.model.ElementDataObject
import org.portfolio.model.Comment
import org.portfolio.client.BufferedHttpResponseWrapper
import net.sf.json.JSONObject
import net.sf.json.JSONArray

/**
 * @author Matt Sheehan
 */
@Controller
@NoAuthentication
class RenderHtmlController extends ApplicationController {

    private static final LogService logService = new LogService(RenderHtmlController)

    @Autowired CommunityAuthorizationManager communityAuthorizationManager
    @Autowired CommentsManager commentsManager
    @Autowired PortfolioManager portfolioManager
    @Autowired ViewerHome viewerHome
    @Autowired PermissionsManager permissionsManager

    @RequestMapping("/portfolio/{id}")
    String execute(
    @PathVariable("id") String portfolioId,
    @RequestParam(value = "preview", required = false) String preview,
    HttpServletRequest request,
    HttpServletResponse response,
    HttpSession session)
    throws Exception {

        Viewer viewer = null
        Person person = RequestUtils.getPerson(request)

        Portfolio portfolio = portfolioManager.findById(portfolioId)

        if (portfolio != null) {
            if (!portfolio.publicView && person == null) {
                RequestUtils.redirectToLogin(request, response)
                return null
            }

            if (person != null) {
                viewer = portfolio.getViewerByPersonId(person.personId)
            }

            if (!portfolio.publicView && viewer == null) {
                
				//throw new SecurityException("no access")
				
				String requestedViewerId = ""
				
				request.setAttribute("owner", portfolio.getPersonId())
				
				if(person != null) {
					requestedViewerId = person.getPersonId()
				}
            
				request.setAttribute("viewer", requestedViewerId)
				request.setAttribute("portfolio", portfolio.getShareId())

				return "accessDenied"
			}

            List<TemplateCategory> categories = portfolioManager.getTopLevelCategoriesByPortfolio(portfolio)

            categories.each { TemplateCategory templateCategory ->
                templateCategory.subcategories.each { TemplateCategory subcat ->
                    subcat.shareEntries.each { ShareEntry shareEntry ->
                        ElementDataObject element = shareEntry.element
                        permissionsManager.grantPermission(element.entryKey)
                        permissionsManager.grantPermissions(element.attachments)
                    }
                }
            }

            if (viewer == null) {
                // strip out private entries
                for (Iterator<TemplateCategory> iter = categories.iterator(); iter.hasNext();) {
                    TemplateCategory cat = iter.next()
                    List<TemplateCategory> subcategories = cat.getSubcategories()
                    for (Iterator<TemplateCategory> iter2 = subcategories.iterator(); iter2.hasNext();) {
                        TemplateCategory subcat = iter2.next()
                        List<ShareEntry> shareEntries = subcat.getShareEntries()
                        for (Iterator<ShareEntry> iter3 = shareEntries.iterator(); iter3.hasNext();) {
                            ShareEntry shareEntry = iter3.next()
                            if (!shareEntry.getElement().getElementDefinition().isPubliclySharable()) {
                                iter3.remove()
                            }
                        }
                        if (shareEntries.isEmpty()) {
                            iter2.remove()
                        }
                    }
                    if (subcategories.isEmpty()) {
                        iter.remove()
                    }
                }
            }

            if (!portfolio.publicView && person) {
                AssessmentModelAssignment assessmentModelAssignment = portfolio.getAssessmentModelAssignment();
                List<Person> evaluatorList = getEvaluators(portfolio);
                String viewerRole = portfolioManager.getViewerRole(portfolio, person);
                List<Comment> commentList = commentsManager.getCommentList(portfolio, person.getPersonId());

                request.setAttribute("assessmentModelAssignment", assessmentModelAssignment);
                request.setAttribute("commentList", commentList);
                request.setAttribute("viewerRole", viewerRole);
                request.setAttribute("evaluatorList", evaluatorList);
            }

			def portMap = portfolioToMap(portfolio, person, request, response)
			
			request.setAttribute("portfolio", portfolio)
			request.setAttribute("portfolioMap", JSONObject.fromObject(portMap))
			request.setAttribute("categories", categories)
			
			request.setAttribute("shareId", portfolio.getShareId())
			
            boolean previewMode = "true".equals(preview)
            boolean showShareWidget = viewer && !viewer.read && viewer.viewType == Viewer.ViewType.OWNER && !previewMode

            request.setAttribute("hasPrivateEntries", hasPrivateEntries(categories))
            request.setAttribute("showShare", showShareWidget ? "true" : "false")
            request.setAttribute("previewMode", previewMode)

            if (viewer && !previewMode) {
                viewer.read = true
                viewerHome.update(viewer)
            }

        }

		request.setAttribute("portfolioFound", portfolio != null)

        getBackPage(request, response)

        return "render/renderHtmlView"
    }

    private List<Person> getEvaluators(Portfolio portfolio) {
        List<Viewer> viewerList = portfolio.viewersObject
        List<Person> evaluatorList = new ArrayList<Person>()
        for (Viewer viewer: viewerList) {
            Person person = viewer.person
            if (communityAuthorizationManager.hasEvaluatorAccess(person, portfolio.template.communityId) && !viewer.personId.equals(portfolio.personId)) {
                evaluatorList.add(person)
            }
        }
        return evaluatorList
    }

    private boolean hasPrivateEntries(List<TemplateCategory> categories) {
        for (Iterator<TemplateCategory> iter = categories.iterator(); iter.hasNext();) {
            TemplateCategory cat = iter.next()
            List<TemplateCategory> subcategories = cat.getSubcategories()
            for (Iterator<TemplateCategory> iter2 = subcategories.iterator(); iter2.hasNext();) {
                TemplateCategory subcat = iter2.next()
                List<ShareEntry> shareEntries = subcat.getShareEntries()
                for (Iterator<ShareEntry> iter3 = shareEntries.iterator(); iter3.hasNext();) {
                    ShareEntry shareEntry = iter3.next()
                    if (!shareEntry.getElement().getElementDefinition().isPubliclySharable()) {
                        return true
                    }
                }
            }
        }
        return false
    }

    private portfolioToMap(portfolio, person, request, response) {
		
        def viewer = (person == null) ? null : portfolio.getViewerByPersonId(person.personId)
		
        Map map = [
            id: portfolio.shareId,
            title: portfolio.shareName,
            description: portfolio.shareDesc,
            owner: [
                id: portfolio.person.personId,
                displayName: portfolio.person.displayName,
                username: portfolio.person.username,
                email: portfolio.person.email
            ],
            dateModified: portfolio.dateModified?.format('MM/dd/yy'),
            dateLastModified: portfolio.dateModified?.format('MM/dd/yy - h:mm a'),
            dateCreated: portfolio.dateCreated?.format('MM/dd/yy - h:mm a'),
            expireDate: portfolio.dateExpire?.format('MM/dd/yy'),
            tags: portfolio.tags,
            flagged: viewer == null ? false : viewer.flagged,
            viewType: viewer == null ? 'viewer' : viewer.viewType.toString().toLowerCase(),
            publicView: portfolio.publicView,
            stockImage: portfolio.stockImage,
            numViewers: portfolio.viewerViewers.size(),
            viewers: portfolio.viewerViewers.collect { v ->
                [
                    username: v.person.username,
                    displayName: v.person.displayName
                ]
            },
            content: []
        ]

        List<TemplateCategory> categories = portfolioManager.getTopLevelCategoriesByPortfolio(portfolio)

        categories.each { TemplateCategory templateCategory ->
            map.content << [type: 'heading1', title: templateCategory.title]
            templateCategory.subcategories.each { TemplateCategory subcat ->
                map.content << [type: 'heading2', title: subcat.title]
                subcat.shareEntries.each { ShareEntry shareEntry ->
                    ElementDataObject edo = shareEntry.element
                    map.content << [type: 'entry', value: entryToMap(edo, shareEntry, portfolio, request, response)]
                }
            }
        }
		
		// logService.debug(JSONArray.fromObject(map.content).toString())

        map
    }

    private newPortfolioToMap(person) {
        [
            owner: [
                id: person.personId,
                displayName: person.displayName,
                username: person.username,
                email: person.email
            ],
            dateModified: new Date().format('MM/dd/yy'),
            dateLastModified: new Date().format('MM/dd/yy - h:mm a'),
            dateCreated: new Date().format('MM/dd/yy - h:mm a'),
            content: []
        ]

    }

    private entryToMap(entry, shareEntry, portfolio, request, response) {

        def wrapper = new BufferedHttpResponseWrapper(response);
        request.setAttribute("element", entry)
		request.setAttribute("portfolio", portfolio)
		
        request.getRequestDispatcher("/jsp/render/elements/${entry.shortClassName}.jsp").include(request, wrapper)
        def entryHtml = wrapper.output

        def elementDef = entry.elementDefinition

        [
            entryKeyId: entry.entryKeyId,
            entryName: entry.entryName,
            entryHtml: entryHtml,
            elementTypeName: entry.elementDefinition.elementType.name,
            tags: entry.tags,
            photoAttachments: entry.photoAttachments.collect {
                [
                    entryKeyId: it.entryKeyId,
                    entryName: it.entryName,
                    fileName: it.fileName,
					formattedSize: it.formattedSize,
					description: it.description,
					dateCreated: it.getDateCreated() == null ? "N/A" : it.getDateCreated().toLocaleString()
                ]
            },
            fileAttachments: entry.fileAttachments.collect {
                [
                    entryKeyId: it.entryKeyId,
                    entryName: it.entryName,
                    fileName: it.fileName,
					formattedSize: it.formattedSize,
					description: it.description,
					dateCreated: it.getDateCreated() == null ? "N/A" : it.getDateCreated().toLocaleString()
					
                ]
            },
            linkAttachments: entry.linkAttachments.collect {
                [
                    entryKeyId: it.entryKeyId,
                    entryName: it.entryName,
                    url: it.url
                ]
            },
			shortClassName: entry.shortClassName,
			assessmentModelAssignment: shareEntry.assessmentModelAssignment == null ? false : true,
			shareEntryId: shareEntry.id,
            elementDefinition: [
                name: elementDef.elementType.name,
				className: entry.shortClassName,
                updatable: elementDef.updatable,
                deletable: elementDef.deletable,
                iconPath: elementDef.iconPath,
                allowsAttachments: elementDef.elementType.allowsAttachments]
        ]

    }

    @RequestMapping("/portfolio/edit")
    String editNew(HttpServletRequest request,
                   HttpServletResponse response,
                   HttpSession session) {

        Person person = RequestUtils.getPerson(request)
        if (!person) {
            RequestUtils.redirectToLogin(request, response)
            return null
        }
        def map = newPortfolioToMap(person)

        request.setAttribute('portfolio', map)
        request.setAttribute('portfolioJson', JSONObject.fromObject(map).toString().replaceAll('\\\\', '\\\\\\\\'))

        return "render/editPortfolio"

    }

    @RequestMapping("/portfolio/edit/{id}")
    String edit(@PathVariable("id") String portfolioId,
                HttpServletRequest request,
                HttpServletResponse response,
                HttpSession session) {

        Person person = RequestUtils.getPerson(request)
        Portfolio portfolio = portfolioId ? portfolioManager.findById(portfolioId) : null
        Viewer viewer = null

        if (portfolio) {
            if (!portfolio.publicView && !person) {
                RequestUtils.redirectToLogin(request, response)
                return null
            }
            if (person) {
                viewer = portfolio.getViewerByPersonId(person.personId)
            }

            if (!portfolio.publicView && !viewer) {
                throw new SecurityException("no access")
            }
        }
        def map = portfolioToMap(portfolio, person, request, response)

        request.setAttribute('portfolio', map)
        request.setAttribute('portfolioJson', JSONObject.fromObject(map).toString().replaceAll('\\\\', '\\\\\\\\'))

        return "render/editPortfolio"

    }

    @RequestMapping("/portfolio/{id}/xml")
    void serializeToXml(@PathVariable("id") String portfolioId,
                        HttpServletRequest request,
                        HttpServletResponse response,
                        HttpSession session,
                        Writer out) {

        Person person = RequestUtils.getPerson(request)
        if (!person.admin) {
            throw new SecurityException("no access")
        }

        Portfolio portfolio = portfolioManager.findById(portfolioId)
        List<TemplateCategory> categories = portfolioManager.getTopLevelCategoriesByPortfolio(portfolio)
        def writer = new StringWriter()
        def xml = new MarkupBuilder(writer)
        xml.portfolio() {
            categories.each { TemplateCategory templateCategory ->
                heading1(templateCategory.title)
                templateCategory.subcategories.each { TemplateCategory subcat ->
                    heading2(templateCategory.title)
                    subcat.shareEntries.each { ShareEntry shareEntry ->
                        ElementDataObject edo = shareEntry.getElement()
                        entry(entryKeyId: edo.entryKeyId) {
                        }
                    }
                }

            }
        }
        response.contentType = 'text/xml'
        out << writer.toString()
    }

    @RequestMapping("/portfolio/{id}/json")
    void serializeToJson(@PathVariable("id") String portfolioId,
                         HttpServletRequest request,
                         HttpServletResponse response,
                         HttpSession session,
                         Writer out) {
        response.contentType = "application/json"

        Person person = RequestUtils.getPerson(request)
        if (!person.admin) {
            throw new SecurityException("no access")
        }

        Portfolio portfolio = portfolioManager.findById(portfolioId)

        def jsonResponse = portfolioToMap(portfolio, person, request, response)

        out.write JSONObject.fromObject(jsonResponse).toString()
    }


    @RequestMapping("/portfolio/{id}/updateTitle")
    void updateTitle(@PathVariable("id") String portfolioId,
                     @RequestParam("value") String newTitle,
                     HttpServletRequest request,
                     HttpServletResponse response,
                     HttpSession session,
                     Writer out) {
        // TODO security

        Portfolio portfolio = portfolioManager.findById(portfolioId)
        portfolio.shareName = newTitle
        portfolioManager.savePortfolio portfolio

        response.contentType = 'text/html'
        out.write newTitle
    }

    @RequestMapping("/portfolio/{id}/updateDescription")
    void updateDescription(@PathVariable("id") String portfolioId,
                           @RequestParam("value") String newDescription,
                           HttpServletRequest request,
                           HttpServletResponse response,
                           HttpSession session,
                           Writer out) {
        // TODO security

        Portfolio portfolio = portfolioManager.findById(portfolioId)
        portfolio.shareDesc = newDescription
        portfolioManager.savePortfolio portfolio

        response.contentType = 'text/html'
        out.write newDescription
    }

    @RequestMapping("/portfolio/{id}/updateHeading")
    void updateHeading(@PathVariable("id") String portfolioId,
                       @RequestParam("value") String newTitle,
                       HttpServletRequest request,
                       HttpServletResponse response,
                       HttpSession session,
                       Writer out) {
        // TODO 

        response.contentType = 'text/html'
        out.write newTitle
    }


    @RequestMapping("/portfolio/{id}/updateColorScheme")
    void updateColorScheme(@PathVariable("id") String portfolioId,
                     @RequestParam("value") String newColorScheme,
                     HttpServletRequest request,
                     HttpServletResponse response,
                     HttpSession session,
                     Writer out) {
        // TODO security

        Portfolio portfolio = portfolioManager.findById(portfolioId)
        portfolio.colorScheme = newColorScheme
        portfolioManager.savePortfolio portfolio

        response.contentType = 'text/html'
        out.write newColorScheme
    }

    @RequestMapping("/portfolio/{id}/updateTheme")
    void updateTheme(@PathVariable("id") String portfolioId,
                     @RequestParam("value") String newTheme,
                     HttpServletRequest request,
                     HttpServletResponse response,
                     HttpSession session,
                     Writer out) {
        // TODO security

        Portfolio portfolio = portfolioManager.findById(portfolioId)
        portfolio.theme = newTheme
        portfolioManager.savePortfolio portfolio

        response.contentType = 'text/html'
        out.write newTheme
    }
}
