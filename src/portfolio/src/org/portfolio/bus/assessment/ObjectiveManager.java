/* $Name:  $ */
/* $Id: ObjectiveManager.java,v 1.7 2010/10/27 19:24:56 ajokela Exp $ */
package org.portfolio.bus.assessment;

import java.util.List;

import org.portfolio.model.assessment.Objective;

/**
 * @author Matt Sheehan
 *
 */
public interface ObjectiveManager {

	Objective getObjectiveById(int parseInt);
	
	void deleteObjective(Objective objective);

	/**
	 * If this is a new objective, it will be added to the end.
	 */
	void saveObjective(Objective objective);

	void moveObjectiveUp(Objective objective);

	void moveObjectiveDown(Objective objective);

    void saveObjectives(List<Objective> subObjectives);

    Objective copyObjectiveSet(Objective objective, Integer id);
	
}
