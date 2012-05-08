/* $Name:  $ */
/* $Id: ElementTypeManagerImpl.java,v 1.6 2010/11/04 21:08:52 ajokela Exp $ */
package org.portfolio.bus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.portfolio.model.ElementType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("elementTypeManager")
public class ElementTypeManagerImpl implements ElementTypeManager {

    @Autowired
    private Map<String, ElementType> elementTypeMap;
    
    public List<ElementType> findAll() {
    	
        return new ArrayList<ElementType>(elementTypeMap.values());
    }

    public ElementType findById(String id) {
        return elementTypeMap.get(id);
    }
}
