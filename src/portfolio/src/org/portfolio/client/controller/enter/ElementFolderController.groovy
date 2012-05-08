/* $Name:  $ */
/* $Id: ElementFolderController.groovy,v 1.5 2010/11/04 21:08:53 ajokela Exp $ */
package org.portfolio.client.controller.enter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.Writer;import net.sf.json.JSON;import net.sf.json.JSONObject;import net.sf.json.JSONArray;import org.portfolio.dao.ElementFolderEntryHome;import org.portfolio.model.EntryKey;import org.springframework.web.bind.annotation.RequestParam;import org.portfolio.bus.element.ElementFolderManager;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.portfolio.client.RequestUtils;
import org.portfolio.dao.ElementFolderHome;
import org.portfolio.model.ElementFolder;
import org.portfolio.model.SharedPortfolioFolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ElementFolderController {
	
	@Autowired ElementFolderHome elementFolderHome;
	@Autowired ElementFolderEntryHome elemenetFolderEntryHome;
	@Autowired ElementFolderManager elementFolderManager;
	
	@RequestMapping(["/elementFolder/list/"])
	void list(HttpServletResponse response, HttpSession session, Writer writer) {
		response.contentType = "application/json"
		def folders = elementFolderHome.findByPersonId(RequestUtils.getPersonId(session)).sort()
		
		def jsonResponse = folders.collect { folder -> [name: folder.name, id: folder.id] }
		
		writer.write JSONArray.fromObject(jsonResponse).toString()
	}
    
    @RequestMapping(["/elementFolder/unfile/"])
    void unfile(
            @RequestParam("ids") EntryKey[] ids, 
            HttpServletResponse response, 
            HttpSession session, 
            Writer writer) {
        response.contentType = "application/json"
        String personId = RequestUtils.getPersonId(session)
        
        // TODO verify ownership
        elementFolderManager.unfileElements(ids as List)
        
        def jsonResponse = [
            stat: "ok",
            data: [:]
        ]
        
        writer.write JSONObject.fromObject(jsonResponse).toString()
    }
    
    @RequestMapping(["/elementFolder/new/"])
    void newFolder(
            @RequestParam("name") String name, 
            @RequestParam("ids") EntryKey[] ids, 
            HttpServletResponse response, 
            HttpSession session, 
            Writer writer) {
        response.contentType = "application/json"
        String personId = RequestUtils.getPersonId(session)
        
        // TODO verify ownership
        
        ElementFolder folder = new ElementFolder([personId: personId, name: name])
        elementFolderHome.insert(folder)
        
        elementFolderManager.moveElements(folder.id, ids as List)
        writer.write folder.id.toString()
    }
	
	@RequestMapping(["/elementFolder/move/"])
	void move(
	        @RequestParam("folderId") BigDecimal folderId, 
	        @RequestParam("ids") EntryKey[] ids, 
	        HttpSession session, 
            Writer writer) {
		String personId = RequestUtils.getPersonId(session)
		
		ElementFolder folder = elementFolderHome.findById(folderId)
		elementFolderManager.moveElements(folderId, ids as List)
		writer.write ""
	}
}
