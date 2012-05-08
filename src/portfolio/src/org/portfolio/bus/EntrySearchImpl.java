/* $Name:  $ */
/* $Id: EntrySearchImpl.java,v 1.26 2011/01/17 20:58:04 ajokela Exp $ */
package org.portfolio.bus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.portfolio.dao.ElementFolderEntryHome;
import org.portfolio.dao.EntrySearchDataHome;
import org.portfolio.dao.wizard.WizardElementDefinitionHome;
import org.portfolio.model.ElementDataObject;
import org.portfolio.model.ElementDefinition;
import org.portfolio.model.EntryKey;
import org.portfolio.model.wizard.WizardElementDefinition;
import org.portfolio.util.CollectionUtil;
import org.portfolio.util.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Matt Sheehan
 */
@Service
public class EntrySearchImpl implements EntrySearch {
    
    @SuppressWarnings("unused")
	private static LogService logService = new LogService(EntrySearchImpl.class);

    @Autowired private EntrySearchDataHome helper;
    @Autowired private ElementDefinitionManager elementDefinitionHome;
    @Autowired private WizardElementDefinitionHome wizardElementDefinitionHome;
    @Autowired private ElementManager elementManager;
    @Autowired private ElementFolderEntryHome elementFolderEntryHome;

    public List<ElementDataObject> findByCriteria(EntrySearchCriteria criteria) {
        Set<EntryKey> entryKeys = findEntryKeysByCriteria(criteria);
        List<ElementDataObject> entries = loadEntries(entryKeys, criteria.getPersonId());
        Collections.sort(entries, ElementDataObject.TYPE_NAME_ORDER);
        return entries;
    }
    
    public int findSizeByCriteria(EntrySearchCriteria criteria) {
    	Set<EntryKey> entryKeys = findEntryKeysByCriteria(criteria);
        int size = sizeEntries(entryKeys, criteria.getPersonId());
        
    	return size;
    }

    public Set<EntryKey> findEntryKeysByCriteria(EntrySearchCriteria criteria) {
        Set<EntryKey> entryKeys = null;
        Set<ElementDefinition> defs = new HashSet<ElementDefinition>();

        if (criteria.getCommunityId() != null && criteria.getWizardId() == null && criteria.getWizardElementId() == null) {
            List<EntryKey> entryKeysFromWizardGroup = findByCommunityId(criteria.getCommunityId(), criteria.getPersonId());
            entryKeys = CollectionUtil.intersection(entryKeys, entryKeysFromWizardGroup);
        }

        if (criteria.getWizardId() != null && criteria.getWizardElementId() == null) {
            List<EntryKey> entryKeysFromWizard = findByWizardId(criteria.getWizardId(), criteria.getPersonId());
            entryKeys = CollectionUtil.intersection(entryKeys, entryKeysFromWizard);
        }

        if (criteria.getWizardElementId() != null) {
            List<EntryKey> entryKeysFromWizardElement = findByWizardElementId(criteria.getWizardElementId(), criteria.getPersonId());
            entryKeys = CollectionUtil.intersection(entryKeys, entryKeysFromWizardElement);
        }

        if (criteria.getTag() != null) {
            List<EntryKey> entryKeysFromTag = helper.findByTag(criteria.getTag(), criteria.getPersonId());
            entryKeys = CollectionUtil.intersection(entryKeys, entryKeysFromTag);
        }

        if (criteria.getFolderId() != null) {
            List<EntryKey> entryKeysFromFolders = elementFolderEntryHome.findByFolderId(criteria.getFolderId());
            entryKeys = CollectionUtil.intersection(entryKeys, entryKeysFromFolders);
        }
        
        if (entryKeys == null && defs.isEmpty()) {
            defs.addAll(elementDefinitionHome.findAll());
        } else if (entryKeys != null){
            for (EntryKey entryKey : entryKeys) {
                defs.add(entryKey.getElementDefinition());
            }
        }

        if (criteria.getElementIds() != null) {
            Set<ElementDefinition> criteriaElementDefintions = getElementDefinitionsFromIds(criteria.getElementIds());
            defs = CollectionUtil.intersection(defs, criteriaElementDefintions);
        }

        if (criteria.getElementSourceId() != null) {
            List<ElementDefinition> criteriaElementDefintions = elementDefinitionHome.findBySourceId(criteria.getElementSourceId());
            defs = CollectionUtil.intersection(defs, criteriaElementDefintions);
        }

        if (criteria.getCategory() != null) {
            List<ElementDefinition> criteriaElementDefintions = elementDefinitionHome.findByCategory(criteria.getCategory());
            defs = CollectionUtil.intersection(defs, criteriaElementDefintions);
        }

        if (criteria.getQuery() != null) {
            String[] tokens = criteria.getQuery().split("\\s");
            Set<EntryKey> entryKeysFromEntryName = null;
            for (ElementDefinition elementDefinition : defs) {
                if (entryKeys == null || entryKeyListContainsElementId(entryKeys, elementDefinition.getId())) {
                    for (String token : tokens) {
                        List<BigDecimal> findEntryIdsMatchingEntryName = elementManager.findEntryIdsMatchingQuery(elementDefinition.getId(), criteria.getPersonId(), token);
                        List<EntryKey> entryKeysMatchingEntryName = createEntryKeys(findEntryIdsMatchingEntryName, elementDefinition, criteria.getPersonId());
                        entryKeysFromEntryName = CollectionUtil.union(entryKeysFromEntryName, entryKeysMatchingEntryName);
                    }
                }
            }
            entryKeys = CollectionUtil.intersection(entryKeys, entryKeysFromEntryName);
        }

        // if we got to this point and entryKeys is null, we haven't searched
        // yet so we need to search all.
        if (entryKeys == null) {
            entryKeys = new HashSet<EntryKey>();
            for (ElementDefinition elementDefinition : defs) {
                List<BigDecimal> findEntryIdsByPersonId = elementManager.findEntryIdsByPersonId(elementDefinition.getId(), criteria.getPersonId());
                List<EntryKey> tempEntryKeys = createEntryKeys(findEntryIdsByPersonId, elementDefinition, criteria.getPersonId());

                entryKeys = CollectionUtil.union(entryKeys, tempEntryKeys);
            }
        }
        
        // At this point we need the things that reduce the existing set
        // TODO unfiled
        
        
        return entryKeys == null ? new HashSet<EntryKey>() : entryKeys;
    }

    private List<EntryKey> createEntryKeys(List<BigDecimal> findEntryIdsByPersonId, ElementDefinition elementDefinition, String personId) {
        List<EntryKey> result = new ArrayList<EntryKey>();
        for (BigDecimal bigDecimal : findEntryIdsByPersonId) {
            result.add(new EntryKey(personId, elementDefinition.getId(), bigDecimal));
        }
        return result;
    }

    private Set<ElementDefinition> getElementDefinitionsFromIds(String[] elementIds) {
        Set<ElementDefinition> set = new HashSet<ElementDefinition>();
        for (String string : elementIds) {
            set.add(elementDefinitionHome.findByElementId(string));
        }
        return set;
    }

    private boolean entryKeyListContainsElementId(Collection<EntryKey> entryKeys, String elementId) {
        for (EntryKey entryKey : entryKeys) {
            if (entryKey.getElementId().equals(elementId)) {
                return true;
            }
        }
        return false;
    }

    private List<EntryKey> findByCommunityId(Integer communityId, String personId) {
        Set<EntryKey> entryKeys = new HashSet<EntryKey>();
    	
        entryKeys.addAll(helper.findLocalEntriesByWizardGroupId(communityId, personId));
        
        entryKeys.addAll(findEntryKeys_AutoImportByCommunityId(communityId, personId));
        
        return new ArrayList<EntryKey>(entryKeys);
    }

    private List<EntryKey> findEntryKeys_AutoImportByCommunityId(Integer communityId, String personId) {
        List<WizardElementDefinition> weds = wizardElementDefinitionHome.findAutoImportByCommunityId(communityId);
        
        Set<EntryKey> entryKeys = new HashSet<EntryKey>();
        for (WizardElementDefinition wed : weds) {
        	
        	List<EntryKey> ek = elementManager.findEntryKeysByPersonId(wed.getElementDefinition().getId(), personId);
        	entryKeys.addAll(ek);
        }
        return new ArrayList<EntryKey>(entryKeys);
    }

    /*
    private List<ElementDefinition> findAutoImportByCommunityId(Integer communityId, String personId) {
        List<WizardElementDefinition> weds = wizardElementDefinitionHome.findAutoImportByCommunityId(communityId);
        Set<ElementDefinition> eds = new HashSet<ElementDefinition>();
        for (WizardElementDefinition wed : weds) {
            eds.add(wed.getElementDefinition());
        }
        return new ArrayList<ElementDefinition>(eds);
    }
	*/
    
    private List<EntryKey> findByWizardId(Integer wizardId, String personId) {
        List<EntryKey> entryKeys = new ArrayList<EntryKey>();
        entryKeys.addAll(helper.findLocalEntriesByWizardId(wizardId, personId));
        return entryKeys;
    }

    private List<EntryKey> findByWizardElementId(int wizardElementId, String personId) {
        WizardElementDefinition wizDef = wizardElementDefinitionHome.findById(wizardElementId);
        List<EntryKey> list = null;
        if (wizDef != null) {
	        if (wizDef.isAutoImport()) {
	        	if ( wizDef.getElementDefinition() != null ) {
		            String elementDefId = wizDef.getElementDefinition().getId();
		            List<BigDecimal> findEntryIdsByPersonId = elementManager.findEntryIdsByPersonId(elementDefId, personId);
		            return createEntryKeys(findEntryIdsByPersonId, wizDef.getElementDefinition(), personId);
	        	}
	        } else {
	            return helper.findLocalEntriesByWizardElementId(wizardElementId, personId);
	        }
        }
        
        return list;
    }

    private List<ElementDataObject> loadEntries(Collection<EntryKey> entryKeys, String personId) {
        List<ElementDataObject> result = new ArrayList<ElementDataObject>();
        for (EntryKey entryKey : entryKeys) {
            ElementDataObject elementInstance = elementManager.findElementInstance(entryKey.getElementId(), personId, entryKey.getEntryId());
            if (elementInstance != null) {
                result.add(elementInstance);
            }
        }
        return result;
    }

    private int sizeEntries(Collection<EntryKey> entryKeys, String personId) {
        
    	int size = 0;
    	
    	for (EntryKey entryKey : entryKeys) {
            
        	boolean elementInstance = elementManager.elementInstanceExist(entryKey.getElementId(), personId, entryKey.getEntryId(), personId);
            
            if (elementInstance) {
                size++;
            }
        }
    	
        return size;
    }
    
    public List<? extends ElementDataObject> findByPersonIdAndElementId(String personId, String elementId) {
        return elementManager.findByPersonId(elementId, personId);
    }

	public Integer findCountByCriteria(EntrySearchCriteria criteria) {

		List<ElementDataObject> list = findByCriteria(criteria);
		
		if(list != null) {
			return list.size();
		}
		
		return 0;
	}
}
