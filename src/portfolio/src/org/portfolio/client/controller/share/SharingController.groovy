/* $Name:  $ */
/* $Id: SharingController.groovy,v 1.14 2011/03/25 13:50:12 ajokela Exp $ */
package org.portfolio.client.controller.share

import java.util.ArrayList
import java.util.Date
import java.util.Iterator
import java.util.List

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession

import org.apache.commons.validator.EmailValidator
import org.portfolio.bus.PortfolioManager
import org.portfolio.bus.ViewerSearch
import org.portfolio.client.RequestUtils
import org.portfolio.dao.PersonHome
import org.portfolio.dao.ViewerHome
import org.portfolio.model.Person
import org.portfolio.model.Portfolio
import org.portfolio.model.Viewer
import org.portfolio.util.LogService;
import org.portfolio.util.StringEncryptor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import net.sf.json.JSONObject
import org.portfolio.bus.PortfolioEmailSender
import org.springframework.util.StringUtils
import org.portfolio.dao.PortfolioHome;

@Controller
public class SharingController {

    @Autowired
    private ViewerSearch viewerSearch
    @Autowired
    private ViewerHome viewerHome
    @Autowired
    private PersonHome personHome
    @Autowired
    private PortfolioManager portfolioManager
    @Autowired
    private PortfolioEmailSender portfolioEmailSender;
	@Autowired
	private PortfolioHome portfolioHome;

	protected LogService logService = new LogService(this.getClass());
	
    /**
     * This doesn't save viewers. Just returns the person's data and does some error checking. Still need to call saveViewers.
     */
    @RequestMapping("/portfolio/sharing/addViewer")
    void add(
            @RequestParam("portfolioId") String portfolioId,
            @RequestParam("query") String query,
            HttpServletRequest request,
            HttpSession session,
            HttpServletResponse response,
            Writer writer) {
        response.contentType = "application/json"

        Portfolio portfolio = portfolioManager.findById(portfolioId)
        Person person = RequestUtils.getPerson(request)

        if (person.personId != portfolio.personId) {
            throw new SecurityException("no access")
        }

        Person newPerson = findPerson(request, query)

        def jsonResponse
        if (newPerson == null) {
            jsonResponse = [
                stat: 'fail',
                error: 'The user could not be found: '+query
            ]
        } else if (newPerson.personId == person.personId) {
            jsonResponse = [
                stat: 'fail',
                error: 'You can not add yourself.'
            ]
        } else if (portfolio.getViewerByPersonId(newPerson.personId)) {
            jsonResponse = [
                stat: 'fail',
                error: 'The user is already a viewer for this portfolio.'
            ]
        } else {
            jsonResponse = [
                stat: 'ok',
                data: [
                    viewer: [
                        username: newPerson.username,
                        displayName: newPerson.displayName
                    ]
                ]
            ]
        }

        writer.write JSONObject.fromObject(jsonResponse).toString()
    }

    @RequestMapping("/portfolio/sharing/removeViewer")
    void remove(
            @RequestParam("username") String username,
            @RequestParam("portfolioId") String portfolioId,
            HttpServletRequest request,
            HttpServletResponse response,
            Writer writer) {
        response.contentType = "application/json"

        Person person = personHome.findByUsername(username)
        Portfolio portfolio = portfolioManager.findById(portfolioId)

        // TODO check person is owner

        viewerHome.deleteByPersonIdShareId(person.getPersonId(), portfolioId)

        def jsonResponse = [
            stat: 'ok'
        ]

        writer.write JSONObject.fromObject(jsonResponse).toString()
    }

    /**
     * @return all viewers for the portfolio
     */
    @RequestMapping("/portfolio/sharing/saveViewers")
    void saveViewers(
            @RequestParam("username") String[] usernames,
            @RequestParam("portfolioId") String portfolioId,
            @RequestParam("sendEmail") boolean sendEmail,
            @RequestParam(value="cc", required=false) boolean ccSelf,
            @RequestParam(value="message", required=false) String message,
            HttpServletRequest request,
            HttpServletResponse response,
            Writer writer) {
        response.contentType = "application/json"

        Portfolio portfolio = portfolioManager.findById(portfolioId);
        Person person = RequestUtils.getPerson(request);

        if (person.personId != portfolio.personId) {
            throw new SecurityException("no access");
        }

        def newViewers = usernames.collect { username ->
            def newPerson = personHome.findByUsername(username);

            return addViewer(portfolio, newPerson);
        }

		portfolioEmailSender.sendEmails(portfolio.shareId, newViewers, ccSelf, message, sendEmail);
		

        portfolio.refreshViewers();
        def allViewers = portfolio.getViewerViewers().sort {it.person.displayName};
		
				
        def jsonResponse
        jsonResponse = [
            stat: 'ok',
            data: [
                viewers: allViewers.collect { viewer -> [
                    username: viewer.person.username,
                    displayName: viewer.person.displayName
                ]},
				publicView: portfolio.publicView
            ]
        ]

        writer.write JSONObject.fromObject(jsonResponse).toString()
    }

    @RequestMapping("/portfolio/sharing/updateVisibility")
    void updateVisibility(
            @RequestParam("visibility") String visibility,
            @RequestParam("portfolioId") String portfolioId,
            HttpServletRequest request,
            HttpServletResponse response,
            Writer writer){
        response.contentType = "application/json";

        // TODO check person is owner

        Portfolio portfolio = portfolioManager.findById(portfolioId)
        portfolio.publicView = (visibility == 'public')

        portfolioManager.savePortfolio(portfolio)
		
		Person person = RequestUtils.getPerson(request);
		
		portfolio.refreshViewers();
		def allViewers = portfolio.getViewerViewers().sort {it.person.displayName};
		
        def jsonResponse = [
            stat: 'ok',
            data: [
                viewers: allViewers.collect { viewer -> [
                    username: viewer.person.username,
                    displayName: viewer.person.displayName
                ]},
				publicView: portfolio.publicView
            ]
        ];
	
        writer.write JSONObject.fromObject(jsonResponse).toString();
    }

    @RequestMapping("/portfolio/sharing/updateExpiration")
    void updateExpiration(
            @RequestParam(value = "date", required=false) String dateValue,
            @RequestParam("portfolioId") String portfolioId,
            HttpServletRequest request,
            HttpServletResponse response,
            Writer writer){
        response.contentType = "application/json"

        // TODO check person is owner

        Portfolio portfolio = portfolioManager.findById(portfolioId)

        Date expireDate = null
        if (dateValue) {
            expireDate = Date.parse("M/d/yyyy", dateValue);
        }

        portfolio.setDateExpire(expireDate);
        portfolioManager.savePortfolio(portfolio)

        def jsonResponse = [
            stat: 'ok'
        ]
        writer.write JSONObject.fromObject(jsonResponse).toString()
    }

    private Viewer addViewer(Portfolio theShare, Person newPerson) {
        Viewer viewerEntry = viewerHome.findInstance(newPerson.getPersonId(), theShare.getShareId())
        if (viewerEntry == null) {
            viewerEntry = new Viewer(portfolioHome)
            viewerEntry.setDateShared(new Date())
            viewerEntry.setShareId(theShare.getShareId())
            viewerEntry.setPerson(newPerson)
            viewerHome.insert(viewerEntry)
        }
        return viewerEntry
    }

    private Person findPerson(HttpServletRequest request, String string) {
		
		string = string.trim()
		
        if (string.contains("@")) {
			
			logService.debug("====> Finding by Email Address - " + string)
			return findByEmail(string)
			
        }

		logService.debug("====> Finding by Username - " + string)
		
        return findByUsername(string)
    }

    private Person findByEmail(String email) {
        Person newPerson = null
        
		newPerson = viewerSearch.findGuestViewer(email)
		
        if (newPerson == null && EmailValidator.getInstance().isValid(email)) {
            newPerson = personHome.createNewGuestUser(email)
        }

		return newPerson
    }

    private Person findByUsername(String username) {
        return viewerSearch.findMemberViewer(username)
    }
}
