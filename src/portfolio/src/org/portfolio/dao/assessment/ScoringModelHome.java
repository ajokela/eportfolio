/* $Name:  $ */
/* $Id: ScoringModelHome.java,v 1.6 2010/10/27 19:24:57 ajokela Exp $ */
package org.portfolio.dao.assessment;

import java.util.List;

import org.portfolio.model.assessment.ScoringModel;

/**
 * This class manages the lifecycle for <code>Scoring Model</code> objects.
 */
public interface ScoringModelHome {

	public ScoringModel find(int scoreModelId);

	/**
	 * Returns a <code>ScoringModel</code> object given a <code>String</code> representing the
	 * scoring model ID, or <code>null</code> if no such scoring model could be found.
	 */
	public ScoringModel find(String scoringModelIDString);

	/**
	 * Returns a <code>List</code> of all <code>ScoringModel</code> objects.   
	 */
	public List<ScoringModel> find();

	/**
	 * @see org.portfolio.dao.assessment.ScoringModelHome#insert(java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public void insert(String scoringModelId, String name, String description, String dataType, String valueType, String valueSet, String quantifiedSet);

	/**
	 * @see org.portfolio.dao.assessment.ScoringModelHome#delete(java.lang.String)
	 */
	public void delete(String scoringModelId);
}
