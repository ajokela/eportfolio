/* $Name:  $ */
/* $Id: CollectionGuideUserData.java,v 1.5 2010/11/04 21:08:53 ajokela Exp $ */
package org.portfolio.model.wizard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Iterator;

import org.portfolio.bus.EntrySearch;
import org.portfolio.bus.EntrySearchCriteria;
import org.portfolio.dao.PortfolioHome;
import org.portfolio.model.ElementDataObject;
import org.portfolio.model.Person;
import org.portfolio.model.Portfolio;
import org.portfolio.model.Template;
import org.portfolio.util.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

@Configurable
public class CollectionGuideUserData {
    
    @SuppressWarnings("unused")
	private static LogService logService = new LogService(CollectionGuideUserData.class);

    @Autowired
    private PortfolioHome portfolioHome;
    @Autowired
    private EntrySearch entrySearch;

    private final CollectionGuide collectionGuide;
    private final Person person;
    private Map<Integer, List<? extends ElementDataObject>> entryMap; // wizardElementDefinitionId --> list of entries
    private Map<Integer, Integer> entryCountMap;
    
    public CollectionGuideUserData(Person person, CollectionGuide collectionGuide) {
        this.person = person;
        this.collectionGuide = collectionGuide;
    }

    private Map<Integer, List<? extends ElementDataObject>> createEntryMap() {
        Map<Integer, List<? extends ElementDataObject>> result = new HashMap<Integer, List<? extends ElementDataObject>>();
        List<WizardElementDefinition> weds = collectionGuide.getWizardElementDefinitions();
        for (WizardElementDefinition wizardElementDefinition : weds) {
            EntrySearchCriteria criteria = new EntrySearchCriteria(person.getPersonId());
            criteria.setWizardElementId(wizardElementDefinition.getId());
            
			List<? extends ElementDataObject> results = entrySearch.findByCriteria(criteria);
            result.put(wizardElementDefinition.getId(), results);
        }
        return result;
    }
    
    private Map<Integer, Integer> createEntryCountMap() {
    	Map<Integer, Integer> results = new LinkedHashMap<Integer, Integer>();
    	
    	List<WizardElementDefinition> weds = collectionGuide.getWizardElementDefinitions();
        for (WizardElementDefinition wizardElementDefinition : weds) {
            EntrySearchCriteria criteria = new EntrySearchCriteria(person.getPersonId());
            criteria.setWizardElementId(wizardElementDefinition.getId());
            
			Integer count = entrySearch.findCountByCriteria(criteria);
            
			// result.put(wizardElementDefinition.getId(), results);
			
			results.put(wizardElementDefinition.getId(), count);
        }
    	
    	return results;
    }
    
    public int getTotalWeds() {
    	List<WizardElementDefinition> weds = collectionGuide.getWizardElementDefinitions();
    	
    	if (!weds.isEmpty()) {
    		return weds.size();
    	}
    	
    	return 0;
    }

    public int getPercentComplete() {
        List<WizardElementDefinition> weds = collectionGuide.getWizardElementDefinitions();
        int numPopulatedDefs = 0;
        for (WizardElementDefinition wizardElementDefinition : weds) {
            List<? extends ElementDataObject> results = getEntriesByGuideElementDefinitionId(wizardElementDefinition.getId());
            if (!results.isEmpty()) {
                numPopulatedDefs++;
            }
        }

        int percentage = 0;
        if (!weds.isEmpty()) {
            percentage = numPopulatedDefs * 100 / weds.size();
        }
        return percentage;
    }

    public int getNumberOfEntries() {
    	
    	/*
        List<WizardElementDefinition> weds = collectionGuide.getWizardElementDefinitions();
        int numElements = 0;
        for (WizardElementDefinition wizardElementDefinition : weds) {
            List<? extends ElementDataObject> results = getEntriesByGuideElementDefinitionId(wizardElementDefinition.getId());
            numElements += results.size();
        }
        return numElements;
        */
    	
    	if(entryCountMap == null) {
    		entryCountMap = createEntryCountMap();
    	}
    	
    	Integer total = 0;
    	
    	for(Iterator<Integer> i = entryCountMap.keySet().iterator(); i.hasNext();) {
    		Integer wed = i.next();
    		Integer count = entryCountMap.get(wed);
    		
    		total += count;
    	}

    	return total.intValue();
    }

    public List<Portfolio> getPortfolios() {
        List<Portfolio> result = new ArrayList<Portfolio>();
        for (Template template : collectionGuide.getTemplates()) {
            result.addAll(portfolioHome.findByOwnerAndTemplateId(person.getPersonId(), template.getId()));
        }
        return result;
    }

    public List<? extends ElementDataObject> getEntriesByGuideElementDefinitionId(int guideElementDefinitionId) {
        if (entryMap == null) {
            entryMap = createEntryMap();
        }
        return entryMap.get(guideElementDefinitionId);
    }

	public Map<Integer, Integer> getEntryCountMap() {
		return entryCountMap;
	}

	public void setEntryCountMap(Map<Integer, Integer> entryCountMap) {
		this.entryCountMap = entryCountMap;
	}
}
