/* $Name:  $ */
/* $Id: RoleAssignmentHome.java,v 1.8 2010/10/27 19:24:57 ajokela Exp $ */
package org.portfolio.dao.community;

import java.util.List;

import org.portfolio.model.Person;
import org.portfolio.model.community.Community;

/**
 * @author Matt Sheehan
 * 
 */
public interface RoleAssignmentHome {

	public List<Person> findPersonsByCommunityIdAndRoleTypeCode(String communityId, String roleTypeCode);

	public void insert(String communityId, String personId, String roleTypeCode);

    public void delete(String communityId, String personId);

    public void delete(String communityId, String personId, String roleTypeCode);

	public List<String> findRolesByCommunityIdAndPersonId(String communityId, String personId);

	public List<Community> findCommunitiesByPersonIdAndRoleTypeCode(String personId, String roleTypeCode);

	public List<Community> findCommunitiesByPersonId(String personId);

    public void deleteAllByCommunity(int communityId);

}
