/* $Name:  $ */
/* $Id: WizardElementKeywordHome.java,v 1.8 2010/10/27 19:24:57 ajokela Exp $ */
package org.portfolio.dao.wizard;

import java.math.BigDecimal;
import java.util.List;

import org.portfolio.model.wizard.WizardElementDefinition;

/**
 * @author Matt Sheehan
 * 
 */
public interface WizardElementKeywordHome {

	/**
	 * Returns a List of Keywords associated with the given
	 * WizardElementDefinition, or an empty List if none found.
	 */
	public List<String> findKeywordsByWizardElementId(int wizardElementId);

	/**
	 * Returns a List of Keywords associated with the given personId, or an
	 * empty List if none found.
	 */
	public List<String> findKeywordsByPersonId(String personId);

	/**
	 * Insert a new WizardElementDefinition - Keyword association.
	 */
	public void insert(WizardElementDefinition definition, String keyword);

	/**
	 * True if a the definition and keyword are already associated. Fasle
	 * otherwise.
	 */
	public boolean existsFor(WizardElementDefinition definition, String keyword);

	public List<WizardElementDefinition> findWizardElementDefinitionsByKeywordId(BigDecimal keywordId);

	/**
	 * 
	 * @param entryId
	 * @return never null
	 */
	public List<String> findKeywordsByEntryId(BigDecimal entryId);

	public void delete(WizardElementDefinition definition, String string);

}
