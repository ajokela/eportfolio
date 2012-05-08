/* $Name:  $ */
/* $Id: GuideUsageController.groovy,v 1.2 2010/10/27 19:28:15 ajokela Exp $ */
package org.portfolio.client.controller.guide

import org.portfolio.bus.CollectionGuideManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import javax.servlet.http.HttpServletResponseimport net.sf.json.JSONObjectimport java.io.Writer
@Controller
public class GuideUsageController {

    @Autowired
    private CollectionGuideManager collectionGuideManager

    @RequestMapping(["/guide/usage/guide/{id}"])
    public void guide(
            @PathVariable("id") int id, 
            HttpServletResponse response,
            Writer writer) {
        response.contentType = "application/json"
        
        def jsonResponse = [
            stat: "ok",
            data: [:]
        ]
        
        jsonResponse.data.numUsers = collectionGuideManager.getNumberofUsersUsingGuide(id)
        
        writer.write JSONObject.fromObject(jsonResponse).toString()
    }

    @RequestMapping(["/guide/usage/cat/{id}"])
    public void cat(
            @PathVariable("id") int id, 
            HttpServletResponse response,
            Writer writer) {
        response.contentType = "application/json"
        
        def jsonResponse = [
            stat: "ok",
            data: [:]
        ]
        
        jsonResponse.data.numUsers = collectionGuideManager.getNumberofUsersUsingCategory(id)
        
        writer.write JSONObject.fromObject(jsonResponse).toString()
    }

    @RequestMapping(["/guide/usage/def/{id}"])
    public void elementDef(
            @PathVariable("id") int id, 
            HttpServletResponse response,
            Writer writer) {
        response.contentType = "application/json"
        
        def jsonResponse = [
            stat: "ok",
            data: [:]
        ]
        
        jsonResponse.data.numUsers = collectionGuideManager.getNumberofUsersUsingGuideElementDef(id)
        
        writer.write JSONObject.fromObject(jsonResponse).toString()
    }
}
