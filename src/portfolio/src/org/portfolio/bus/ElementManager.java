/* $Name:  $ */
/* $Id: ElementManager.java,v 1.13 2010/11/23 14:27:17 ajokela Exp $ */
package org.portfolio.bus;

import java.math.BigDecimal;
import java.util.List;

import org.portfolio.model.ElementDataObject;
import org.portfolio.model.ElementDefinition;
import org.portfolio.model.EntryKey;
import org.portfolio.model.Link;
import org.portfolio.model.Person;

public interface ElementManager {

    void store(ElementDataObject data);

    void remove(ElementDataObject data);

    void remove(List<EntryKey> entryKeys);

    int findCountByPersonId(String elementDefId, String personId);

    ElementDataObject findElementInstance(String elementDefId, String personId, BigDecimal entryId);
    
    ElementDataObject findElementInstance(String elementDefId, String personId, BigDecimal entryId, boolean isPublic, String requestPersonId);

    boolean elementInstanceExist(String elementDefId, String personId, BigDecimal entryId, String requestPersonId);
    
    ElementDataObject findElementInstance(EntryKey entryKey, Person person);

    ElementDataObject findElementInstance(EntryKey entryKey);

    ElementDataObject newInstance(ElementDefinition def);
    
    ElementDataObject newInstance(String elementDefId);

    List<? extends ElementDataObject> findByPersonId(String elementDefId, String personId);
    
    List<EntryKey> findEntryKeysByPersonId(String elementDefId, String personId);
    
    List<Link> findEPFLinksByPersonId(String personId);
    
    List<? extends ElementDataObject> findByPersonId(String personId);

    List<? extends ElementDataObject> findLinksByPersonId(String personId);

    List<? extends ElementDataObject> findPhotosByPersonId(String personId);

    List<? extends ElementDataObject> findFilesByPersonId(String personId);

    List<BigDecimal> findEntryIdsByPersonId(String elementDefId, String personId);

    List<BigDecimal> findEntryIdsMatchingQuery(String elementDefId, String personId, String entryName);

    List<? extends ElementDataObject> loadAll(List<EntryKey> keys);

}
