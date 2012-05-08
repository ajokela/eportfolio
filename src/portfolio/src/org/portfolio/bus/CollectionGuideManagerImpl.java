/* $Name:  $ */
/* $Id: CollectionGuideManagerImpl.java,v 1.28 2011/01/27 17:37:01 ajokela Exp $ */
package org.portfolio.bus;

import java.util.List;

import org.portfolio.dao.wizard.CategoryHome;
import org.portfolio.dao.wizard.CollectionGuideHome;
import org.portfolio.dao.wizard.WizardElementDefinitionHome;
import org.portfolio.dao.wizard.WizardElementInstanceHome;
import org.portfolio.dao.wizard.WizardElementKeywordHome;
import org.portfolio.model.EntryKey;
import org.portfolio.model.TemplateElementMapping;
import org.portfolio.model.wizard.Category;
import org.portfolio.model.wizard.CollectionGuide;
import org.portfolio.model.wizard.WizardElementDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("collectionGuideManager")
public class CollectionGuideManagerImpl implements CollectionGuideManager {

    @Autowired
    private CollectionGuideHome collectionGuideHome;
    @Autowired
    private CategoryHome categoryHome;
    @Autowired
    private WizardElementDefinitionHome wizardElementDefinitionHome;
    @Autowired
    private WizardElementKeywordHome wizardElementKeywordHome;
    @Autowired
    private TemplateManager templateManager;
    @Autowired
    private WizardElementInstanceHome wizardElementInstanceHome;

    public CollectionGuide getById(int guideId) {
        return collectionGuideHome.find(guideId);
    }

    public void saveWizard(CollectionGuide wizard) {
        if (wizard.getId() == null) {
            collectionGuideHome.insert(wizard);
        } else {
            collectionGuideHome.update(wizard);
        }
    }

    public Category getCategoryById(int categoryId) {
        return categoryHome.findById(categoryId);
    }

    @Transactional
    public void moveCategoryUp(Category category) {
        int order = category.getSortOrder();
        if (order > 1) {
            Integer parentCategoryId = category.getParentCategoryId();
            Category otherCategory;
            if (parentCategoryId == null) {
                otherCategory = categoryHome.findTopLevelCategoryByWizardIdAndSortOrder(category.getWizardId(), order - 1);
            } else {
                otherCategory = categoryHome.findByParentIdAndSortOrder(parentCategoryId, order - 1);
            }
            switchOrder(category, otherCategory);
        }
    }

    @Transactional
    public void moveCategoryDown(Category category) {
        int order = category.getSortOrder();
        Integer parentCategoryId = category.getParentCategoryId();
        Category otherCategory;
        if (parentCategoryId == null) {
            otherCategory = categoryHome.findTopLevelCategoryByWizardIdAndSortOrder(category.getWizardId(), order + 1);
        } else {
            otherCategory = categoryHome.findByParentIdAndSortOrder(parentCategoryId, order + 1);
        }
        if (otherCategory != null) {
            switchOrder(category, otherCategory);
        }
    }

    @Transactional
    public void deleteCategory(Category category) {
        Integer parentCategoryId = category.getParentCategoryId();
        Integer wizardId = category.getWizardId();

        // TODO do this in database?
        for (Category subcat : category.getCategories()) {
            deleteCategory(subcat);
        }
        for (WizardElementDefinition wed : category.getWizardElementDefinitions()) {
            wizardElementInstanceHome.deleteForElement(wed.getId());
            deleteCollectionGuideElementDefinition(wed);
        }

        categoryHome.delete(category.getId());

        List<Category> siblings;
        if (parentCategoryId == null) {
            siblings = categoryHome.findTopLevelCategories(wizardId);
        } else {
            siblings = categoryHome.findChildrenByCategoryId(parentCategoryId);
        }
        int order = 1;
        for (Category sibling : siblings) {
            sibling.setSortOrder(order);
            categoryHome.update(sibling);
            order++;
        }
    }

    @Transactional
    public void saveCategory(Category category) {
        if (category.getId() == null) {
            int numSiblings;
            if (category.getParentCategoryId() == null) {
                numSiblings = categoryHome.findNumberOfTopLevelCategoriesByWizardId(category.getWizardId());
            } else {
                numSiblings = categoryHome.findNumberOfChildrenByCategoryId(category.getParentCategoryId());
            }
            category.setSortOrder(numSiblings + 1);
            categoryHome.insert(category);
        } else {
            categoryHome.update(category);
        }
    }

    private void switchOrder(Category o1, Category o2) {
        int o1Order = o1.getSortOrder();
        int o2Order = o2.getSortOrder();
        o1.setSortOrder(o2Order);
        o2.setSortOrder(o1Order);
        categoryHome.update(o1);
        categoryHome.update(o2);
    }

    public WizardElementDefinition getCollectionGuideElementDefinitionById(int guideDefId) {
        WizardElementDefinition wed = wizardElementDefinitionHome.findById(guideDefId);
        wed.setKeywords(wizardElementKeywordHome.findKeywordsByWizardElementId(wed.getId()));
        return wed;
    }

    @Transactional
    public void deleteCollectionGuideElementDefinition(WizardElementDefinition selectedDefiniton) {
        for (String string : selectedDefiniton.getKeywords()) {
            wizardElementKeywordHome.delete(selectedDefiniton, string);
        }

        List<TemplateElementMapping> tems = templateManager.findByCollectionGuideElementId(selectedDefiniton.getId());
        for (TemplateElementMapping tem : tems) {
            templateManager.deleteTemplateElementMapping(tem);
        }

        Integer categoryId = selectedDefiniton.getCategoryId();

        wizardElementDefinitionHome.delete(selectedDefiniton.getId());

        List<WizardElementDefinition> siblings = wizardElementDefinitionHome.findByCategoryId(categoryId);
        int order = 1;
        for (WizardElementDefinition sibling : siblings) {
            sibling.setSortOrder(order);
            wizardElementDefinitionHome.update(sibling);
            order++;
        }
    }

    @Transactional
    public void moveCollectionGuideElementDefinitionDown(WizardElementDefinition definiton) {
        int order = definiton.getSortOrder();
        WizardElementDefinition other = wizardElementDefinitionHome.findByCategoryIdAndSortOrder(definiton.getCategoryId(), order + 1);
        if (other != null) {
            switchOrder(definiton, other);
        }
    }

    @Transactional
    public void moveCollectionGuideElementDefinitionUp(WizardElementDefinition definiton) {
        int order = definiton.getSortOrder();
        if (order > 1) {
            WizardElementDefinition other = wizardElementDefinitionHome.findByCategoryIdAndSortOrder(definiton.getCategoryId(), order - 1);
            switchOrder(definiton, other);
        }
    }

    private void switchOrder(WizardElementDefinition o1, WizardElementDefinition o2) {
        int o1Order = o1.getSortOrder();
        int o2Order = o2.getSortOrder();
        o1.setSortOrder(o2Order);
        o2.setSortOrder(o1Order);
        wizardElementDefinitionHome.update(o1);
        wizardElementDefinitionHome.update(o2);
    }

    @Transactional
    public void saveCollectionGuideElementDefinition(WizardElementDefinition definition) {
        if (definition.getId() == null) {
            int numSiblings = wizardElementDefinitionHome.findByCategoryId(definition.getCategoryId()).size();
            definition.setSortOrder(numSiblings + 1);
            wizardElementDefinitionHome.insert(definition);
        } else {
            wizardElementDefinitionHome.update(definition);
        }

        List<String> oldKeywords = wizardElementKeywordHome.findKeywordsByWizardElementId(definition.getId());
        for (String newKeyword : definition.getKeywords()) {
            if (oldKeywords.contains(newKeyword)) {
                oldKeywords.remove(newKeyword);
            } else {
                wizardElementKeywordHome.insert(definition, newKeyword);
            }
        }
        for (String string : oldKeywords) {
            wizardElementKeywordHome.delete(definition, string);
        }

    }

    public List<CollectionGuide> getAllCollectionGuides() {
        return collectionGuideHome.findAll();
    }

    @Transactional
    public CollectionGuide copyGuide(CollectionGuide guide, Integer destinationCommunityId) {
        CollectionGuide newGuide = new CollectionGuide();
        newGuide.setTitle(guide.getTitle());
        newGuide.setDescription(guide.getDescription());
        newGuide.setPublished(guide.isPublished());
        newGuide.setDeleted(guide.isDeleted());
        newGuide.setCommunityId(destinationCommunityId);
        newGuide.setShareTip(guide.getShareTip());
        newGuide.setTip(guide.getTip());

        collectionGuideHome.insert(newGuide);

        for (Category cat : guide.getCategories()) {
            Category newCat = new Category();
            newCat.setTitle(cat.getTitle());
            newCat.setSortOrder(cat.getSortOrder());
            newCat.setWizardId(newGuide.getId());

            categoryHome.insert(newCat);

            for (Category subcat : cat.getCategories()) {
                Category newSubcat = new Category();
                newSubcat.setTitle(subcat.getTitle());
                newSubcat.setParentCategoryId(newCat.getId());
                newSubcat.setSortOrder(subcat.getSortOrder());
                newSubcat.setWizardId(newGuide.getId());

                categoryHome.insert(newSubcat);

                for (WizardElementDefinition wed : subcat.getWizardElementDefinitions()) {
                	if(wed.getElementDefinition() != null) {
	                    WizardElementDefinition newWed = new WizardElementDefinition();
	                    newWed.setAutoImport(wed.isAutoImport());
	                    newWed.setCategoryId(newSubcat.getId());
	                    newWed.setDescription(wed.getDescription());
	                    newWed.setElementDefinitionId(wed.getElementDefinition().getId());
	                    newWed.setKeywords(wed.getKeywords());
	                    newWed.setRequired(wed.isRequired());
	                    newWed.setSortOrder(wed.getSortOrder());
	                    newWed.setTitle(wed.getTitle());
	                    newWed.setWizardId(newGuide.getId());
	
	                    wizardElementDefinitionHome.insert(newWed);
                
                	}
                }
            }
        }
        return getById(newGuide.getId());
    }

    public void deleteGuide(CollectionGuide guide) {
        guide.setDeleted(true);
        collectionGuideHome.update(guide);
    }

    @Override
    @Transactional
    public void attachEntriesToGuideElementDef(int guideDefId, EntryKey[] entryKeys) {
        for (EntryKey entryKey : entryKeys) {
            if (!wizardElementInstanceHome.existsFor(guideDefId, entryKey.getEntryId(), entryKey.getPersonId())) {
                wizardElementInstanceHome.insert(guideDefId, entryKey.getEntryId(), entryKey.getPersonId());
            }
        }
    }

    @Override
    @Transactional
    public void detachEntriesFromGuideElementDef(int guideDefId, EntryKey[] entryKeys) {
        for (EntryKey entryKey : entryKeys) {
            wizardElementInstanceHome.delete(guideDefId, entryKey.getEntryId(), entryKey.getPersonId());
        }
    }

    @Override
    public int getNumberofUsersUsingCategory(int categoryId) {
        return wizardElementInstanceHome.getPersonCountForCategory(categoryId);
    }

    @Override
    public int getNumberofUsersUsingGuide(int guideId) {
        return wizardElementInstanceHome.getPersonCountForGuide(guideId);
    }

    @Override
    public int getNumberofUsersUsingGuideElementDef(int guideDefId) {
        return wizardElementInstanceHome.getPersonCountForElement(guideDefId);
    }
}
