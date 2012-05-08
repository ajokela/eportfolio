package org.portfolio.bus;

import java.util.List;

import org.portfolio.model.CommunityResource;

import org.portfolio.model.EntryKey;


public interface CommunityResourceManager {
	
	List<CommunityResource> getResources(int community_id);
	List<CommunityResource> getResources(String community_id);
	
	CommunityResource getResource(int id);
	CommunityResource getResource(String id);
	
	void updateResource(CommunityResource cr);
	
	void addResource(int community_id, EntryKey entryKey);
	
	void delResource(int community_id);
	
}