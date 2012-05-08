/* $Name:  $ */
/* $Id: CommunityHome.java,v 1.11 2011/01/27 17:37:01 ajokela Exp $ */
package org.portfolio.dao.community;

import java.util.List;

import org.portfolio.model.community.Community;

/**
 * @author Matt Sheehan
 * 
 */

public interface CommunityHome {

    Community findByCommunityId(int communityId);

    Community findByCommunityId(String communityId);

    int insert(Community community);

    void delete(int communityId);

    void update(Community community);

    List<Community> findAll();

    List<Community> findPublicCommunities();

    List<Community> findDeletedCommunities();

    List<Community> findPrivateCommunities();

}
