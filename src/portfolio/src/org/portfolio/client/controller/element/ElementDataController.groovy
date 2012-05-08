/* $Name:  $ */
/* $Id: ElementDataController.groovy,v 1.9 2010/10/27 19:28:15 ajokela Exp $ */
package org.portfolio.client.controller.element
import org.springframework.web.bind.annotation.RequestMapping

import org.portfolio.model.ElementDefinition
import org.portfolio.model.*;

import java.io.Writer

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

import net.sf.json.JSONObject

import org.portfolio.bus.ElementDefinitionManager;
import org.portfolio.bus.ElementManager
import org.portfolio.bus.PermissionsManager
import org.portfolio.client.BufferedHttpResponseWrapper;
import org.portfolio.client.RequestUtils
import org.portfolio.model.ElementDataObject
import org.portfolio.model.EntryKey
import org.portfolio.model.Person
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestParam
import org.portfolio.model.wizard.WizardElementDefinition; // BK
import org.portfolio.dao.wizard.WizardElementDefinitionHome; // BK
import org.portfolio.dao.wizard.WizardElementInstanceHome; // BK
// import org.portfolio.bus.TagManager;
import org.portfolio.util.LogService

@Controller
class ElementDataController {
	
	@Autowired
	private PermissionsManager permissionsManager
	@Autowired
	private ElementManager elementManager
    @Autowired
    private ElementDefinitionManager elementDefinitionManager;
    @Autowired
    private WizardElementDefinitionHome wizardElementDefinitionHome; // BK
	@Autowired
    private WizardElementInstanceHome wizardElementInstanceHome; // BK
    
	private LogService logService = new LogService(this.getClass());
	
	@RequestMapping(["/element/data/{entryKeyId}"])
	void entryInfo(
        	@PathVariable("entryKeyId") EntryKey entryKey, 
        	HttpServletRequest request, 
        	HttpServletResponse response,
        	Writer writer) {
		response.contentType = "application/json"
		
		Person person = RequestUtils.getPerson(request)
		if (entryKey.personId != person.personId && !permissionsManager.hasPermission(entryKey)) {
			throw new SecurityException("no access")
		}

        boolean isOwner = entryKey.personId == person.personId
        if (!isOwner && !permissionsManager.hasPermission(entryKey)) {
            throw new SecurityException("no access");
        }
		
		ElementDataObject element = elementManager.findElementInstance(entryKey)
		
		if(element == null) {
			logService.debug("===> element is null" )
		}
		
		def wrapper = new BufferedHttpResponseWrapper(response);
		request.setAttribute("element", element)
		request.getRequestDispatcher("/jsp/render/elements/${element.shortClassName}.jsp").include(request, wrapper)
		def entryHtml = wrapper.output
		
		def jsonResponse = [
        		stat: "ok",
        		data: [readOnly: !isOwner]
		]
		
		String entryName = element.entryName;
		
		if (element.shortClassName == 'Mentors') {
			Mentors mentors = (Mentors)element;
			
			if (mentors.getFirstName() != null) {
				entryName = mentors.getFirstName() + " " + entryName;
			}
		}
		
		jsonResponse.data.entry = [
		                        entryKeyId: element.entryKeyId,
		                        entryName: entryName,
		                        entryHtml: entryHtml,
		                        elementTypeName: element.elementDefinition.elementType.name,
		                        tags: element.tags,
		                        photoAttachments: element.photoAttachments.collect { [
		                                 entryKeyId: it.entryKeyId, 
		                                 entryName: it.entryName] },
		                        fileAttachments: element.fileAttachments.collect { [
		                                 entryKeyId: it.entryKeyId, 
		                                 entryName: it.entryName, 
		                                 fileName: it.fileName] },
		                        linkAttachments: element.linkAttachments.collect { [
		                                 entryKeyId: it.entryKeyId, 
		                                 entryName: it.entryName, 
		                                 url: it.url] }
		]
		                        
		def elementDef = element.elementDefinition
        jsonResponse.data.entry.elementDefinition = [
                                name: elementDef.elementType.name,
                                updatable: elementDef.updatable,
                                deletable: elementDef.deletable,
                                iconPath: elementDef.iconPath,
                                allowsAttachments: elementDef.elementType.allowsAttachments,
                                category: elementDef.elementType.category == null ? "" : elementDef.elementType.category.toString().toLowerCase(),
								elementId: elementDef.elementId
        ]
        
        // BK
		List<WizardElementDefinition> wizardDefs = wizardElementDefinitionHome.findByElementDefinitionId(elementDef.id)
		for (Iterator<WizardElementDefinition> iter = wizardDefs.iterator(); iter.hasNext();) {
      		WizardElementDefinition s = iter.next();		
            if ( !wizardElementInstanceHome.existsFor(s.id, entryKey.getEntryId(), entryKey.getPersonId()) || s.categories.size < 1 ) {
        		iter.remove();
            }
        }
		jsonResponse.data.entry.wizard = [
			keywords: wizardDefs.collect{ it.categories }.flatten()*.toLowerCase().unique().sort()
        ]
        // --
        
		writer.write JSONObject.fromObject(jsonResponse).toString()
		
	}

    @RequestMapping(["/elementDef/data/{elementDefId}"])
	void elementDefinitionInfo(
        	@PathVariable("elementDefId") String elementDefId, 
        	HttpServletResponse response,
        	Writer writer) {
		response.contentType = "application/json"
		ElementDefinition elementDef = elementDefinitionManager.findByElementId(elementDefId)
		def jsonResponse = [
        		stat: "ok",
        		data: [
        			elementTypeName: elementDef.elementType.name]
		]
		
		writer.write JSONObject.fromObject(jsonResponse).toString()
	}

		
}
