/* $Name:  $ */
/* $Id: ObjectiveHome.java,v 1.13 2011/02/16 17:35:17 ajokela Exp $ */
package org.portfolio.dao.assessment;

import java.util.List;

import org.portfolio.model.assessment.AssessedObjective;
import org.portfolio.model.assessment.Objective;

/**
 * @author Matt Sheehan
 * 
 */
public interface ObjectiveHome {

	/**
	 * Insert the given objective.
	 * 
	 * @param objective
	 */
	public int insert(Objective objective);

	/**
	 * Delete the objective with the given ID as well as any child objectives.
	 * 
	 * @param objectiveId
	 */
	public void delete(int objectiveId);

	/**
	 * Update the existing record for this objective.
	 * 
	 * @param objective
	 */
	public void update(Objective objective);

	/**
	 * Find the top level objectives and all the children.
	 * 
	 * @param communityId
	 * @return the top level objectives.
	 */
	public List<Objective> findObjectiveSetsByCommunityId(int communityId);

	/**
	 * @param parentId the id of the parent
	 * @return the number of children
	 */
	public int findNumberOfChildrenByObjectiveId(Integer parentId);
	

	public int findNumberOfTopLevelObjectives();
        
        /** 
         * Doesn't set Sub-Objectives but gets all the objectives of a community as a list
         * Uses Hierarchial query to fetch the data in one query
         * Query Executed: SELECT Level,LPAD (' ', 5 * (LEVEL - 1)) || name "name", objective_id, parent_objective_id, description, community_id, sort_order,date_created, date_modified FROM at_objective obj
         *   WHERE community_id = ? START WITH parent_objective_id IS NULL CONNECT BY PRIOR objective_id = parent_objective_id 
         * @param communityIdParam
         * @return
         */
        public List<AssessedObjective> getAssessedObjectivesForCommunity(String communityIdParam);

	/**
	 * @param objectiveId
	 * @return
	 */
	public Objective findById(int objectiveId);

	/**
	 * @param parentId
	 * @param i
	 * @return
	 */
	public Objective findByParentIdAndOrder(Integer parentId, int order);

}
