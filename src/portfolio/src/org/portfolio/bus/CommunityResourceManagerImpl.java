package org.portfolio.bus;

import java.util.List;

import org.portfolio.dao.CommunityResourcesHome;
import org.portfolio.model.CommunityResource;
import org.springframework.beans.factory.annotation.Autowired;

import org.portfolio.model.EntryKey;
import org.portfolio.util.LogService;

import org.springframework.stereotype.Service;

@Service("communityResources")
public class CommunityResourceManagerImpl implements CommunityResourceManager {

	@Autowired
	private CommunityResourcesHome communityResourcesHome;
	@SuppressWarnings("unused")
	private LogService logService = new LogService(this.getClass());
	
	public List<CommunityResource> getResources(String community_id) {
		return getResources(Integer.valueOf(community_id));
	}
	
	public List<CommunityResource> getResources(int community_id) {

		return communityResourcesHome.findByCommunityId(community_id);
		
	}
	
	public CommunityResource getResource(int id) {
		return communityResourcesHome.findByCommunityResourceId(id);
	}
	
	public CommunityResource getResource(String id) {
		return getResource(Integer.valueOf(id));
	}
	
	public void addResource(int community_id, EntryKey entryKey) {
		
		// logService.debug("entryKey.getElementId() -> " + entryKey.getElementId());
		
		CommunityResource cr = new CommunityResource();
		
		cr.setCommunityId(community_id);
		cr.setEntryId(entryKey.getEntryId().intValue());
		cr.setPersonId(entryKey.getPersonId());
		cr.setResourceType(entryKey.getElementId());
		cr.setPlace(0);
		
		communityResourcesHome.add(cr);
		
	}
	
	public void updateResource(CommunityResource cr) {
		
		communityResourcesHome.update(cr);
		
	}
	
	public void delResource(int resource_id) {
		communityResourcesHome.remove(resource_id);
	}
	
}