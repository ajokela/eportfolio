/* $Name:  $ */
/* $Id: ShareEntryHome.java,v 1.21 2010/10/27 19:24:57 ajokela Exp $ */
package org.portfolio.dao;

import java.util.List;

import org.portfolio.model.EntryKey;
import org.portfolio.model.ShareEntry;

/**
 * @author Matt Sheehan
 *
 */
public interface ShareEntryHome {

	void store(ShareEntry shareEntry);

	void remove(ShareEntry shareEntry);

	void deleteShareEntries(String shareId);
	
	void deleteForEntry(EntryKey entryKey);

    List<ShareEntry> findByShareIdElementId(String shareId, String elementId);

    List<ShareEntry> findByShareId(String shareId);
    
    List<ShareEntry> findHiddenByShareId(String shareId);

    List<ShareEntry> findByShareIdCategoryId(String shareId, int categoryId);
    
    ShareEntry findByShareEntryId(int intValue);

    List<ShareEntry> findByPersonIdAndAssessmentModelId(String personId, Integer id);

    boolean entryExistsInPortfolio(EntryKey entryKey, String portfolioId);
}
