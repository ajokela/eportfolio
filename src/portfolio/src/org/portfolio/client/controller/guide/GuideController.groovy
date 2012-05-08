/* $Name:  $ */
/* $Id: GuideController.groovy,v 1.15 2010/11/04 21:08:53 ajokela Exp $ */
package org.portfolio.client.controller.guide;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.portfolio.bus.CollectionGuideManager;
import org.portfolio.bus.ElementManager;
import org.portfolio.bus.EntrySearch;
import org.portfolio.bus.EntrySearchCriteria;
import org.portfolio.bus.community.CommunityManager;
import org.portfolio.client.RequestUtils;
import org.portfolio.model.ElementDataObject;
import org.portfolio.model.EntryKey;
import org.portfolio.model.Person;
import org.portfolio.model.community.Community;
import org.portfolio.model.wizard.CollectionGuide;
import org.portfolio.model.wizard.CollectionGuideUserData;
import org.portfolio.model.wizard.WizardElementDefinition;
import org.portfolio.util.CollectionUtil;
import org.portfolio.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GuideController {

    @Autowired
    private CollectionGuideManager collectionGuideManager;
    @Autowired
    private EntrySearch entrySearch;
    @Autowired
    private CommunityManager communityManager;
    @Autowired
    private ElementManager elementManager;

    @RequestMapping(["/guide/{id}"])
    public String viewGuide(@PathVariable("id") int guideId, HttpServletRequest request, Model model) {
        CollectionGuide guide = collectionGuideManager.getById(guideId);
        Community community = communityManager.getCommunityById(guide.getCommunityId());
        Person person = RequestUtils.getPerson(request);
        CollectionGuideUserData collectionGuideUserData = new CollectionGuideUserData(person, guide);

        model.addAttribute("community", community);
        model.addAttribute("wizard", guide);
        model.addAttribute("userData", collectionGuideUserData);
        return "guide/guide";
    }
    
    @RequestMapping(["/guide/json/{id}"])
    public void guideJson(
            @PathVariable("id") int guideId, 
            HttpServletRequest request, 
            HttpServletResponse response,
            Writer writer) {
        response.contentType = "application/json"

        CollectionGuide guide = collectionGuideManager.getById(guideId)
        Community community = communityManager.getCommunityById(guide.communityId)
        Person person = RequestUtils.getPerson(request)
        CollectionGuideUserData collectionGuideUserData = new CollectionGuideUserData(person, guide)

        def jsonResponse = [
            stat: "ok",
            data: [:]
        ]
        
        jsonResponse.data.guide = [
            id: guide.id,
            title: guide.title,
            enterTip: guide.tip,
            shareTip: guide.shareTip,
            description: guide.description,
            cats: guide.categories.collect {cat -> [
                id: cat.id,                                   
                title: cat.title,
                subcats: cat.categories.collect {subcat -> [
                    id: subcat.id,
                    title: subcat.title,
                    parentCategoryId: subcat.parentCategoryId,
                    defs: subcat.wizardElementDefinitions.collect {wed -> [
                        id: wed.id,
                        title: wed.title,
                        description: wed.description,
                        required: wed.required,
                        autoImport: wed.autoImport,
                        viewOnly: wed.viewOnly,
                        categories: wed.categories,
                        elementDef: [
                            id: wed.elementDefinition.id,
                            name: wed.elementDefinition.name,
                            iconPath: wed.elementDefinition.iconPath,
                            sourceName: wed.elementDefinition.sourceName,
                            category: wed.elementDefinition.elementType.category
                        ],
                        entries: collectionGuideUserData.getEntriesByGuideElementDefinitionId(wed.id).sort(ElementDataObject.DATE_MODIFIED_ORDER).collect(entryToJsonFormat)
                    ]}
                ]}
            ]}
        ]
        
        writer.write JSONObject.fromObject(jsonResponse).toString()
    }
    
    def entryToJsonFormat = { entry ->
        [
            entryKeyId: entry.entryKey.id,
            entryName: entry.entryName,
            modifiedDate: entry.modifiedDate?.format('MM/dd/yy'),
            modifiedDateMillis: entry.modifiedDate?.time,
            elementDefinition: [
                name: entry.elementDefinition.name,
                iconPath: entry.elementDefinition.iconPath
            ]
        ]
    } 

    @RequestMapping(["/guide/element/detach"])
    public void detachEntry(
            @RequestParam("guideDefId") int guideDefId,
            @RequestParam("entryKeyId") EntryKey[] entries,
            PrintWriter writer) {
        // TODO authorization
        collectionGuideManager.detachEntriesFromGuideElementDef(guideDefId, entries)
        // TODO return status
    }

    @RequestMapping(["/guide/element/attach"])
    public void attachEntry(
            @RequestParam("guideDefId") int guideDefId,
            @RequestParam("entryKeyId") EntryKey[] entryKeys,
            HttpServletResponse response,
            PrintWriter writer) {
        response.contentType = "application/json"
        // TODO authorization
        collectionGuideManager.attachEntriesToGuideElementDef(guideDefId, entryKeys);
        

        def jsonResponse = [
            stat: "ok",
            data: [guideDefId: guideDefId]
        ]
        
        def entries = entryKeys.collect {entryKeyId -> elementManager.findElementInstance(entryKeyId)}.sort(ElementDataObject.DATE_MODIFIED_ORDER)
        jsonResponse.data.entries = entries.collect(entryToJsonFormat)
        
        writer.write JSONObject.fromObject(jsonResponse).toString()
    }
}
