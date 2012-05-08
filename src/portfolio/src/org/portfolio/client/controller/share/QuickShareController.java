/* $Name:  $ */
/* $Id: QuickShareController.java,v 1.12 2010/11/04 21:08:53 ajokela Exp $ */
package org.portfolio.client.controller.share;

import org.portfolio.model.Link;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.portfolio.bus.ElementManager;
import org.portfolio.bus.PortfolioManager;
import org.portfolio.client.RequestUtils;
import org.portfolio.dao.template.TemplateHome;
import org.portfolio.dao.wizard.WizardTemplateHome;
import org.portfolio.model.ElementDataObject;
import org.portfolio.model.EntryKey;
import org.portfolio.model.Person;
import org.portfolio.model.Portfolio;
import org.portfolio.model.ShareEntry;
import org.portfolio.model.Template;
import org.portfolio.model.TemplateCategory;
import org.portfolio.model.TemplateElementMapping;
import org.portfolio.util.ArrayUtil;
import org.portfolio.util.LogService;
import org.portfolio.util.PortfolioConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class QuickShareController {
    
    private LogService logService = new LogService(this.getClass());

    @Autowired
    private WizardTemplateHome wizardTemplateHome;
    @Autowired
    private TemplateHome templateHome;
    @Autowired
    private PortfolioManager portfolioManager;
    @Autowired
    private ElementManager elementManager;
    @RequestMapping("/quickshare")
    public String quickshare1(
            @RequestParam("entryKeyId") EntryKey[] entryKeys,
            @RequestParam(value = "wizardId", required = false) Integer wizardId,
            HttpSession session,
            Model model) {

    	Person person = RequestUtils.getPerson(session);
    	
        session.removeAttribute("share");
        session.removeAttribute("shareOrig");
        session.removeAttribute("shareId");
        session.removeAttribute("topLevelCats");
        boolean loadInstances = (wizardId != null);

        Portfolio share = null;
        Template template = null;
        if (wizardId != null) {
            share = new Portfolio();
            share.setQuickShare(true);

            List<Template> templates = wizardTemplateHome.getTemplates(wizardId);

            if (!templates.isEmpty()) {
                // use the first template matching this wizard
                share.setTemplateId(templates.get(0).getId());
                template = templates.get(0);
            }
        } else if (entryKeys != null) {
            // coming from entry/tree page
            share = new Portfolio();
            share.setQuickShare(true);
            share.setTemplateId(PortfolioConstants.CUSTOM_TEMPLATE_ID);
            template = templateHome.findTemplateById(PortfolioConstants.CUSTOM_TEMPLATE_ID);
        }
        
        model.addAttribute("share",share);

        if (template != null) {
            // get category/sub-category and element instances
            List<TemplateCategory> topLevelCats = portfolioManager.getTopLevelCategoriesForCreate(share, loadInstances);
            
            // coming from tree only set instances selected by user
            if (topLevelCats != null && !loadInstances && !ArrayUtil.isEmpty(entryKeys)) {
                populateUserSelectedInstances(entryKeys, topLevelCats, person);
            }
            
            cleanTheList(topLevelCats);
            populateElementMap(share, topLevelCats);
            session.setAttribute("topLevelCats", topLevelCats);
            
            return "share/quickShare1";
        } else {
            // TODO error
            throw new RuntimeException("couldn't find the goods");
        }
    }

    private void populateUserSelectedInstances(EntryKey[] entryKeys, List<TemplateCategory> topLevelCats, Person person) {
    	
    	// LinkHome lh = new LinkHome();
    	
        for (TemplateCategory topLevelCat : topLevelCats) {
        	
            for (TemplateCategory subcat : topLevelCat.getSubcategories()) {
                for (TemplateElementMapping element : subcat.getTemplateElementDefs()) {
                    element.setInstances(null);
                    
                    // logService.debug("subcat.getTitle(): " + subcat.getTitle());
                    
                    for (EntryKey entryKey : entryKeys) {
                    	
                    	/*
                    	logService.debug("element.getElementDefinition().getId(): " + element.getElementDefinition().getId());
                    	
                    	logService.debug("entryKey.getElementId(): " + entryKey.getElementId());
                    	logService.debug("entryKey.getPersonId(): " + entryKey.getPersonId());
                    	logService.debug("entryKey.getEntryId(): " + String.valueOf(entryKey.getEntryId()));
                    	
                    	logService.debug("subcat.getId(): " + String.valueOf(subcat.getId()) + " | subcat.getTitle(): " + subcat.getTitle());
                    	
                    	*/
                    	
                        if ( ((element.getElementDefinition().getId().equalsIgnoreCase(entryKey.getElementId())) 
                        		|| (subcat.getId() == 901 && entryKey.getElementId().compareToIgnoreCase("epf_link") == 0))  && entryKey.getHasBeenAdded() == 0) {
                            element.addInstance(elementManager.findElementInstance(entryKey, person));
                            entryKey.setHasBeenAdded();
                        }
                        
                        // logService.debug("subcat.getId(): " + String.valueOf(subcat.getId()) + " | subcat.getTitle(): " + subcat.getTitle());
                    }
                }
            }
        }
        
    }

    private void populateElementMap(Portfolio share, List<TemplateCategory> topLevelCats) {
        // populate elementMap
        // create share entries
        for (TemplateCategory topLevelCat : topLevelCats) {
            for (TemplateCategory subcat : topLevelCat.getSubcategories()) {
                List<ShareEntry> shareEntries = new ArrayList<ShareEntry>();
                int sortOrder = 0;
                for (TemplateElementMapping element : subcat.getTemplateElementDefs()) {
                    Collection<ElementDataObject> instances = element.getInstances();
                    if (instances != null && !instances.isEmpty()) {
                        logService.debug("==> instances: " + instances);
                        List<ElementDataObject> sortElements = new ArrayList<ElementDataObject>(instances);
                        Collections.sort(sortElements, ElementDataObject.NAME_ORDER);
                        for (ElementDataObject d1 : sortElements) {
                            // logService.debug("d1: " + d1);
                            sortOrder++;
                            
                    		if (d1.getClass().equals(Link.class)) {
                    			((Link)d1).setElementTitle(((Link)d1).getElementTitle());
                    			logService.debug("Link: elementTitle: " + d1.getElementTitle());
                    			
                    			d1.setEntryName(d1.getEntryName() + " - (" + ((Link)d1).getUrl() + ")");
                    		}
                    		                            
                            shareEntries.add(createShareEntry(share, sortOrder, element, d1));
                        }
                    }
                }
                subcat.setShareEntries(shareEntries);
            }
        }
    }

    private void cleanTheList(List<TemplateCategory> topLevelCats) {
        // now clean the list, so we only display cat/subcat and elements
        // with instances
        // remove empty elements
        Iterator<TemplateCategory> topIterator = topLevelCats.iterator();
        while (topIterator.hasNext()) {
            TemplateCategory topLevelCat = topIterator.next();
            Iterator<TemplateCategory> subIterator = topLevelCat.getSubcategories().iterator();
            while (subIterator.hasNext()) {
                TemplateCategory subcat = subIterator.next();
                Iterator<TemplateElementMapping> elementIterator = subcat.getTemplateElementDefs().iterator();
                while (elementIterator.hasNext()) {
                    TemplateElementMapping element = elementIterator.next();
                    if (element.getInstances() == null || element.getInstances().isEmpty()) {
                        elementIterator.remove();
                    }
                }
                // remove empty sub-categories
                if (subcat.getTemplateElementDefs() == null || subcat.getTemplateElementDefs().isEmpty()) {
                    subIterator.remove();
                }
            }
            // remove empty top Level Categories
            if (topLevelCat.getSubcategories() == null || topLevelCat.getSubcategories().isEmpty()) {
                topIterator.remove();
            }
        }
    }

    private ShareEntry createShareEntry(Portfolio share, int sortOrder, TemplateElementMapping element, ElementDataObject d1) {
        ShareEntry shareEntry = new ShareEntry();
        shareEntry.setShareId(share.getShareId());
        shareEntry.setEntryIndex(d1.getEntryId());
        shareEntry.setSortOrder(sortOrder);
        shareEntry.setElement(d1);
        shareEntry.setTemplateElementMapping(element);
        return shareEntry;
    }

}
