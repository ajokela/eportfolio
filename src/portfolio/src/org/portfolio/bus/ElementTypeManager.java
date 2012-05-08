/* $Name:  $ */
/* $Id: ElementTypeManager.java,v 1.4 2010/10/27 19:24:56 ajokela Exp $ */
package org.portfolio.bus;

import java.util.List;

import org.portfolio.model.ElementType;

public interface ElementTypeManager {

    List<ElementType> findAll();
    
    ElementType findById(String id);
}
