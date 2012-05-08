/* $Name:  $ */
/* $Id: EntrySearch.java,v 1.16 2010/10/27 19:24:56 ajokela Exp $ */
package org.portfolio.bus;

import java.util.List;
import java.util.Set;

import org.portfolio.model.ElementDataObject;
import org.portfolio.model.EntryKey;

/**
 * @author Matt Sheehan
 * 
 */
public interface EntrySearch {

    /**
     * Retrieves all entries with the given criteria for this person.
     * 
     * @param criteria the search criteria
     * @return a list of ElementDataObjects or an empty list if none found,
     *         never null.
     */
    List<? extends ElementDataObject> findByCriteria(EntrySearchCriteria criteria);
    
    int findSizeByCriteria(EntrySearchCriteria criteria);
    
    /**
     * Retrieves all entries with the given elementId for this person.
     * 
     * @param personId TODO
     * @param elementId the id of the element definition.
     * 
     * @return a list of ElementDataObjects or an empty list if none found,
     *         never null.
     */
    List<? extends ElementDataObject> findByPersonIdAndElementId(String personId, String elementId);

    Set<EntryKey> findEntryKeysByCriteria(EntrySearchCriteria criteria);

	Integer findCountByCriteria(EntrySearchCriteria criteria);

}
