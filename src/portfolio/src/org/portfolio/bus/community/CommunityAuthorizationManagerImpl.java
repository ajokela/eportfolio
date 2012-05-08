/* $Name:  $ */
/* $Id: CommunityAuthorizationManagerImpl.java,v 1.6 2010/10/27 19:24:56 ajokela Exp $ */
package org.portfolio.bus.community;

import java.util.List;

import org.portfolio.dao.community.RoleAssignmentHome;
import org.portfolio.model.Person;
import org.portfolio.model.community.CommunityRoleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("communityAuthorizationManager")
public class CommunityAuthorizationManagerImpl implements CommunityAuthorizationManager {

    @Autowired
	private RoleAssignmentHome roleAssignmentHome;

	public boolean verifyEvaluatorAccess(Person person, int communityId) {
		if (!hasEvaluatorAccess(person, communityId)) {
			throw new SecurityException("missing required access");
		}
		
		return true;
	}

	public boolean verifyMemberAccess(Person person, int communityId) {
		if (!hasMemberAccess(person, communityId)) {
			// throw new SecurityException("missing required access");
			return false;
		}
		
		return true;
	}

	public boolean verifyAssessmentCoordinatorAccess(Person person, int communityId) {
		if (!hasAssessmentCoordinatorAccess(person, communityId)) {
			throw new SecurityException("missing required access");
		}
		
		return true;
	}
	

	public boolean verifyCommunityCoordinatorAccess(Person person, int communityId) {
		if (!hasCommunityCoordinatorAccess(person, communityId)) {
			throw new SecurityException("missing required access");
		}
		
		return true;
	}

	public boolean hasAssessmentCoordinatorAccess(Person person, int communityId) {
		return hasOneOfThese(person, communityId, CommunityRoleType.ASSESSMENT_COORDINATOR, CommunityRoleType.COMMUNITY_COORDINATOR);
	}

	public boolean hasCommunityCoordinatorAccess(Person person, int communityId) {
		return hasOneOfThese(person, communityId, CommunityRoleType.COMMUNITY_COORDINATOR);
	}

	public boolean hasEvaluatorAccess(Person person, int communityId) {
		return hasOneOfThese(
				person,
				communityId,
				CommunityRoleType.EVALUATOR,
				CommunityRoleType.ASSESSMENT_COORDINATOR,
				CommunityRoleType.COMMUNITY_COORDINATOR);
	}

	public boolean hasMemberAccess(Person person, int communityId) {
		return hasOneOfThese(
				person,
				communityId,
				CommunityRoleType.MEMBER,
				CommunityRoleType.EVALUATOR,
				CommunityRoleType.ASSESSMENT_COORDINATOR,
				CommunityRoleType.COMMUNITY_COORDINATOR);
	}

	private boolean hasOneOfThese(Person person, int communityId, CommunityRoleType... roleTypes) {
		List<String> roles = roleAssignmentHome.findRolesByCommunityIdAndPersonId("" + communityId, person.getPersonId());
		for (CommunityRoleType communityRoleType : roleTypes) {
			if (roles.contains(communityRoleType.getCode())) {
				return true;
			}
		}
		return person.isAdmin();
	}
}
