/* $Name:  $ */
/* $Id: WizardElementDefinitionHome.java,v 1.11 2010/10/27 19:24:57 ajokela Exp $ */
package org.portfolio.dao.wizard;

import java.util.List;

import org.portfolio.model.wizard.WizardElementDefinition;

/**
 * @author Matt Sheehan
 * 
 */
public interface WizardElementDefinitionHome {

    List<WizardElementDefinition> findByCategoryId(int categoryId);

    List<WizardElementDefinition> findByWizardId(int wizardId);

    List<WizardElementDefinition> findAutoImportByCommunityId(Integer communityId);

    WizardElementDefinition findById(int mapId);

    WizardElementDefinition findByCategoryIdAndSortOrder(Integer categoryId, int i);

    void insert(WizardElementDefinition wizardElementDefinition);

    void delete(int wizardElementDefinitionId);

    void update(WizardElementDefinition wizardElementDefinition);
    
    List<WizardElementDefinition> findByElementDefinitionId(String elementDefinitionId);

}
