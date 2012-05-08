/* $Name:  $ */
/* $Id: ElementController.groovy,v 1.6 2010/11/04 21:08:53 ajokela Exp $ */
package org.portfolio.client.controller.element;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.apache.struts.util.MessageResources;
import org.portfolio.util.DateUtil;
import net.sf.json.JSONObject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.portfolio.bus.AttachmentManager;
import org.portfolio.bus.ElementDefinitionManager;
import org.portfolio.bus.ElementManager;
import org.portfolio.bus.PermissionsManager;
import org.portfolio.bus.TagManager;
import org.portfolio.client.RequestUtils;
import org.portfolio.model.ElementDataObject;
import org.portfolio.model.ElementDefinition;
import org.portfolio.model.ElementType;
import org.portfolio.model.EntryKey;
import org.portfolio.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.portfolio.model.wizard.WizardElementDefinition; // BK
import org.portfolio.dao.wizard.WizardElementDefinitionHome; // BK
import org.portfolio.dao.wizard.WizardElementInstanceHome; // BK

/**
 * Class to handle the display/update, etc. for element pages.
 */
@Controller
public class ElementController {

    static MessageResources messageResources = MessageResources.getMessageResources("org.portfolio.client.action.ApplicationResources");

    @Autowired
    private ElementManager elementManager;
    @Autowired
    private ElementDefinitionManager elementDefinitionManager;
    @Autowired
    private TagManager tagManager;
    @Autowired
    private PermissionsManager permissionsManager;
    @Autowired
    private AttachmentManager attachmentManager;
    @Autowired
    private WizardElementDefinitionHome wizardElementDefinitionHome; // BK
	@Autowired
    private WizardElementInstanceHome wizardElementInstanceHome; // BK

    @RequestMapping(value = ["/element/save/"], method = [RequestMethod.POST])
    public void save(
            @RequestParam("nodeId") String elementDefId,
            @RequestParam(value = "entryId", required = false) BigDecimal entryId,
            @RequestParam(value = "attachToEntryKeyId", required = false) EntryKey attachToEntryKey,
            HttpServletRequest request,
            HttpServletResponse response,
            Writer writer) throws Exception {
        response.setContentType("application/json");
        String personId = RequestUtils.getPersonId(request);

        Date now = new Date();
        ElementDataObject elementDataObject;
        if (entryId != null) {
            elementDataObject = elementManager.findElementInstance(elementDefId, personId, entryId);
        } else {
            ElementDefinition elementDefinition = elementDefinitionManager.findByElementId(elementDefId);
            elementDataObject = elementManager.newInstance(elementDefinition);
            elementDataObject.setPersonId(personId);
            elementDataObject.setDateCreated(now);
        }
        elementDataObject.setModifiedDate(now);

        org.apache.struts.util.RequestUtils.populate(elementDataObject, request); // TODO switch to Spring?
        ActionErrors actionErrors = new ActionErrors();
        if (elementDataObject instanceof ActionForm) {
            actionErrors.add(((ActionForm) elementDataObject).validate(new ActionMapping(), request));
        }

        if (actionErrors.isEmpty()) {
            elementManager.store(elementDataObject);
            if (attachToEntryKey != null) {
                attachmentManager.addAttachments(attachToEntryKey, [ elementDataObject.getEntryKey() ] as EntryKey[]);

            }
        }

        def jsonResponse = [:]

        if (actionErrors.empty) {
            jsonResponse.stat = 'ok'
            jsonResponse.data = [entry: entryToJsonFormat(elementDataObject)]
        } else {
            jsonResponse.stat = 'fail'
            jsonResponse.errors = actionErrorsToJsonFormat(actionErrors)
        }
        
        writer.write JSONObject.fromObject(jsonResponse).toString()
    }
    
    def actionErrorsToJsonFormat = { actionErrors ->
        actionErrors.properties().collect { name ->
            [
                elementName: name, 
                messages: actionErrors.get(name).collect { actionMessage -> messageResources.getMessage(actionMessage.key, actionMessage.values) }
            ]
        }
    }
    
    def entryToJsonFormat = { entry ->
        [
            entryKeyId: entry.entryKey.id,
            entryName: entry.entryName,
            modifiedDate: entry.modifiedDate?.format('MM/dd/yy'),
            modifiedDateMillis: entry.modifiedDate?.time,
            elementDefinition: [
                name: entry.elementDefinition.name,
                iconPath: entry.elementDefinition.iconPath,
            ]
        ]
    }

    @RequestMapping(["/element/create/"]) // the listing of element defs to pick from when creating a new element
    public String createList(HttpServletRequest request) {
        List<ElementDefinition> defs = elementDefinitionManager.findCreatable();
        Collections.sort(defs);
        request.setAttribute("defs", defs);
        return "element/create";
    }

    @RequestMapping(["/element/create/{elementDefId}"])
    public String createShow(@PathVariable("elementDefId") String elementDefId, Model model) {
        ElementDefinition edef = elementDefinitionManager.findByElementId(elementDefId);
        ElementDataObject newInstance = elementManager.newInstance(edef);
        model.addAttribute("dataDef", newInstance);
        model.addAttribute("org.apache.struts.taglib.html.BEAN", newInstance);
        if (edef.getFileAccessor() != null) {
            // forward to file upload
            return "element/uploadFile";
        } else {
            return "element/editElement";
        }
    }

    @RequestMapping(["/element/view/{entryKeyId}"]) // appears that this is not used anywhere
    public String view(@PathVariable("entryKeyId") EntryKey entryKey, HttpServletRequest request) {
        Person person = RequestUtils.getPerson(request);
        boolean isOwner = entryKey.getPersonId().equals(person.getPersonId());
        if (!isOwner && !permissionsManager.hasPermission(entryKey)) {
            throw new SecurityException("no access");
        }
        ElementDataObject element = elementManager.findElementInstance(entryKey);
        request.setAttribute("dataDef", element);
        request.setAttribute("readOnly", !isOwner);
        return "element/viewElement";
    }

    @RequestMapping(["/element/edit/{entryKeyId}"])
    public String edit(@PathVariable("entryKeyId") EntryKey entryKey, HttpServletRequest request) {
        Person person = RequestUtils.getPerson(request);
        if (!entryKey.getPersonId().equals(person.getPersonId())) {
            throw new SecurityException("no access");
        }
        ElementDataObject element = elementManager.findElementInstance(entryKey);
        
		// BK
		List<WizardElementDefinition> wizardDefs = wizardElementDefinitionHome.findByElementDefinitionId(element.elementDefinition.id)
		for (Iterator<WizardElementDefinition> iter = wizardDefs.iterator(); iter.hasNext();) {
			WizardElementDefinition s = iter.next();		
			if ( !wizardElementInstanceHome.existsFor(s.id, entryKey.getEntryId(), entryKey.getPersonId()) || s.categories.size < 1 ) {
				iter.remove();
			}
		}
		request.setAttribute("keywords", wizardDefs.collect{ it.categories }.flatten()*.toLowerCase().unique().sort());
		// --
		
        request.setAttribute("dataDef", element);
        request.setAttribute("org.apache.struts.taglib.html.BEAN", element);
        return "element/editElement";
    }

    @RequestMapping(["/element/print/"])
    public String print(@RequestParam("entryKeyId") EntryKey[] entryKeys, HttpServletRequest request) {
        Person person = RequestUtils.getPerson(request);

        request.setAttribute("entries", entryKeys.collect { elementManager.findElementInstance(it); });
        return "element/printElement";
    }

    @RequestMapping(["/element/delete/"])
    public void delete(@RequestParam("entryKeyId") EntryKey[] entryKeys, HttpServletRequest request, PrintWriter writer) {
        Person person = RequestUtils.getPerson(request);

        for (EntryKey entryKey : entryKeys) {
            if (!entryKey.getElementDefinition().isDeletable()) {
                // TODO return error
            }
        }

        elementManager.remove(Arrays.asList(entryKeys));
    }

    @RequestMapping(["/element/tag/add"])
    public void addTag(
            @RequestParam("entryKeyId") EntryKey entryKey,
            @RequestParam("tag") String tag,
            HttpServletRequest request,
            PrintWriter writer) {
        Person person = RequestUtils.getPerson(request);
        if (!entryKey.getPersonId().equals(person.getPersonId())) {
            throw new SecurityException("no access");
        }
        tagManager.addTag(entryKey, tag);
    }

    @RequestMapping(["/element/tag/remove"])
    public void removeTag(
            @RequestParam("entryKeyId") EntryKey entryKey,
            @RequestParam("tag") String tag,
            HttpServletRequest request,
            PrintWriter writer) {
        Person person = RequestUtils.getPerson(request);
        if (!entryKey.getPersonId().equals(person.getPersonId())) {
            throw new SecurityException("no access");
        }
        tagManager.deleteTag(entryKey, tag);
    }
}
