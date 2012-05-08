/* $Name:  $ */
/* $Id: EntrySearchDataHome.java,v 1.12 2010/11/04 21:08:53 ajokela Exp $ */
package org.portfolio.dao;

import java.util.List;

import org.portfolio.model.EntryKey;

/**
 * @author Matt Sheehan
 * 
 */
public interface EntrySearchDataHome {

    List<EntryKey> findBySystemTag(String keyword, String personId);

    List<EntryKey> findByTag(String tagText, String personId);

    List<EntryKey> findLocalEntriesByWizardElementId(int wizardElementId, String personId);

    List<EntryKey> findLocalEntriesByWizardId(Integer wizardId, String personId);

    List<EntryKey> findLocalEntriesByWizardGroupId(Integer communityId, String personId);
}
