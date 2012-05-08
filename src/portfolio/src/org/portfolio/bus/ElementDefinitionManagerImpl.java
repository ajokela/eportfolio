/* $Name:  $ */
/* $Id: ElementDefinitionManagerImpl.java,v 1.14 2010/11/04 21:08:52 ajokela Exp $ */
package org.portfolio.bus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.portfolio.model.ElementDefinition;
import org.portfolio.model.ElementSource;
import org.portfolio.model.ElementType.Category;
import org.portfolio.util.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("elementDefinitionManager")
public class ElementDefinitionManagerImpl implements ElementDefinitionManager {

    @Autowired
    private Map<String, ElementSource> elementSourceMap;
    private LogService logService = new LogService(this.getClass());
    
    public List<ElementDefinition> findAll() {
        List<ElementDefinition> all = new ArrayList<ElementDefinition>();
        for (ElementSource library : elementSourceMap.values()) {
            all.addAll(library.getElementDefinitions());
        }
        return all;
    }

    public ElementDefinition findByElementId(String elementId) {
    	
        for (ElementSource library : elementSourceMap.values()) {

        	ElementDefinition elementDefinition = library.getElementDefinition(elementId);
            if (elementDefinition != null) {
            	
                return elementDefinition;
            }
        }
        
        logService.debug("---> findByElementId(): NOT FOUND -> " + elementId);
        
        return null;
        // TODO
    }

    public ElementDefinition findByClassName(String className) {
        for (ElementDefinition elementDefinition : findAll()) {
            if (className.equals(elementDefinition.getElementType().getModelClassName())) {
                return elementDefinition;
            }
        }
        return null;
        // TODO
    }

    public List<ElementDefinition> findBySourceId(String sourceId) {
        for (ElementSource elementSource : elementSourceMap.values()) {
            if (elementSource.getId().equals(sourceId)) {
                return elementSource.getElementDefinitions();
            }
        }
        return null;
    }

    public List<ElementDefinition> findByElementTypeId(String elementTypeId) {
        List<ElementDefinition> result = new ArrayList<ElementDefinition>();
        for (ElementDefinition elementDefinition : findAll()) {
            if (elementTypeId.equals(elementDefinition.getElementType().getId())) {
                result.add(elementDefinition);
            }
        }
        return result;
        // TODO
    }

    public List<ElementDefinition> findCreatable() {
        List<ElementDefinition> creatable = new ArrayList<ElementDefinition>();
        for (ElementDefinition elementDefinition : findAll()) {
            if (elementDefinition.isCreatable()) {
                creatable.add(elementDefinition);
            }
        }
        return creatable;
    }

    public List<ElementDefinition> findByCategory(String category) {
        List<ElementDefinition> result = new ArrayList<ElementDefinition>();
        for (ElementDefinition elementDefinition : findAll()) {
            Category elCategory = elementDefinition.getElementType().getCategory();
            if (elCategory != null && elCategory.name().equals(category)) {
                result.add(elementDefinition);
            }
        }
        return result;
    }

    @Override
    public List<ElementSource> findAllElementSources() {
    	
    	
        return new ArrayList<ElementSource>(elementSourceMap.values());
    }
}
