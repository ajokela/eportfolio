/* $Name:  $ */
/* $Id: ElementDefinitionManager.java,v 1.8 2010/10/27 19:24:56 ajokela Exp $ */
package org.portfolio.bus;

import java.util.List;

import org.portfolio.model.ElementDefinition;
import org.portfolio.model.ElementSource;

/**
 * @author Matt Sheehan
 * 
 */
public interface ElementDefinitionManager {

    /**
     * @return list of all data element type definitions in the database
     */
    List<ElementDefinition> findAll();

    ElementDefinition findByElementId(String elementId);

    ElementDefinition findByClassName(String className);

    /**
     * Finds list of data elements sorted by tree_filter_name
     */
    List<ElementDefinition> findBySourceId(String sourceId);
    
    List<ElementDefinition> findByElementTypeId(String elementTypeId);

    List<ElementDefinition> findCreatable();

    List<ElementDefinition> findByCategory(String category);
    
    List<ElementSource> findAllElementSources();
}
