package org.portfolio.dao;

import java.util.List;

import org.portfolio.model.CommunityResource;

public interface CommunityResourcesHome {
	
	List<CommunityResource> findByCommunityId(int community_id);
	void remove(CommunityResource cr);
	void remove(int resource_id);
	void add(CommunityResource cr);
	void update(CommunityResource cr);
	CommunityResource findByCommunityResourceId(int resource_id);
	CommunityResource findByCommunityIdAndEntryId(int community_id, int entry_id);
	
}