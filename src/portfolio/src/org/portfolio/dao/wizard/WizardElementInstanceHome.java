/* $Name:  $ */
/* $Id: WizardElementInstanceHome.java,v 1.6 2010/10/27 19:24:57 ajokela Exp $ */
package org.portfolio.dao.wizard;

import java.math.BigDecimal;

import org.portfolio.model.EntryKey;

/**
 * @author Matt Sheehan
 */
public interface WizardElementInstanceHome {
		
	void insert(int wizardElementId, BigDecimal entryId, String personId);
    
    void delete(int wizardElementId, BigDecimal entryId, String personId);
    
    void deleteForElement(int wizardElementId);
	
	void deleteForEntry(EntryKey entryKey);
	
	boolean existsFor(int wizardElementId, BigDecimal entryId, String personId);
    
    int getPersonCountForElement(int wizardElementId);
    
    int getPersonCountForCategory(int catId);
    
    int getPersonCountForGuide(int guideId);
	
}
