/* $Name:  $ */
/* $Id: CommunityAuthorizationManager.java,v 1.5 2010/10/27 19:24:56 ajokela Exp $ */
package org.portfolio.bus.community;

import org.portfolio.model.Person;

/**
 * @author Matt Sheehan
 * 
 */
public interface CommunityAuthorizationManager {

	/**
	 * @throws SecurityException
	 */
	public boolean verifyMemberAccess(Person person, int communityId);

	/**
	 * @throws SecurityException
	 */
	public boolean verifyEvaluatorAccess(Person person, int communityId);

	/**
	 * @throws SecurityException
	 */
	public boolean verifyAssessmentCoordinatorAccess(Person person, int communityId);

	/**
	 * @throws SecurityException
	 */
	public boolean verifyCommunityCoordinatorAccess(Person person, int communityId);	
	
	
	public boolean hasMemberAccess(Person person, int communityId);

	public boolean hasEvaluatorAccess(Person person, int communityId);

	public boolean hasAssessmentCoordinatorAccess(Person person, int communityId);

	public boolean hasCommunityCoordinatorAccess(Person person, int communityId);

}
