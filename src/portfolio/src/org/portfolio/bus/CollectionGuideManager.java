/* $Name:  $ */
/* $Id: CollectionGuideManager.java,v 1.14 2010/10/27 19:24:56 ajokela Exp $ */
package org.portfolio.bus;

import java.util.List;

import org.portfolio.model.EntryKey;
import org.portfolio.model.wizard.Category;
import org.portfolio.model.wizard.CollectionGuide;
import org.portfolio.model.wizard.WizardElementDefinition;

/**
 * @author Matt Sheehan
 * 
 */
public interface CollectionGuideManager {

    CollectionGuide getById(int guideId);

    void saveWizard(CollectionGuide wizard);

    void moveCategoryUp(Category category);

    Category getCategoryById(int categoryId);

    void moveCategoryDown(Category category);

    void deleteCategory(Category category);

    void saveCategory(Category category);

    WizardElementDefinition getCollectionGuideElementDefinitionById(int guideDefId);

    void deleteCollectionGuideElementDefinition(WizardElementDefinition selectedDefiniton);

    void moveCollectionGuideElementDefinitionUp(WizardElementDefinition selectedDefiniton);

    void moveCollectionGuideElementDefinitionDown(WizardElementDefinition selectedDefiniton);

    void saveCollectionGuideElementDefinition(WizardElementDefinition definition);

    List<CollectionGuide> getAllCollectionGuides();

    CollectionGuide copyGuide(CollectionGuide guide, Integer destinationCommunityId);

    void deleteGuide(CollectionGuide guide);
    
    void attachEntriesToGuideElementDef(int guideDefId, EntryKey[] entryKeys);
    
    void detachEntriesFromGuideElementDef(int guideDefId, EntryKey[] entryKeys);

    int getNumberofUsersUsingGuide(int guideId);
    
    int getNumberofUsersUsingCategory(int categoryId);
    
    int getNumberofUsersUsingGuideElementDef(int guideDefId);
    

}
