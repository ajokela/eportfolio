/* $Name:  $ */
/* $Id: ElementHome.java,v 1.11 2010/10/27 19:24:57 ajokela Exp $ */
package org.portfolio.dao;

import java.math.BigDecimal;
import java.util.List;

import org.apache.bsf.BSFException;
import org.portfolio.model.ElementDataObject;

public interface ElementHome {

    void store(ElementDataObject data);

    void remove(ElementDataObject data);

    List<? extends ElementDataObject> findByPersonId(String personId) throws BSFException;

    ElementDataObject findElementInstance(String personId, BigDecimal entryId) throws BSFException;
    
    boolean elementInstanceExist(String personId, BigDecimal entryId);

}
