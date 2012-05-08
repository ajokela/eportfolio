package org.portfolio.bus.community;

import java.util.List;

import org.portfolio.dao.community.InteractHome;
import org.portfolio.model.community.Community;
import org.portfolio.model.community.Interact;
import org.portfolio.util.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommunityInteractImpl implements CommunityInteract {
	
	@Autowired
	private InteractHome interactHome;
	
	LogService logService = new LogService(this.getClass());
	
	public void add(Interact interact) {
		
		interactHome.insert(interact);
	}
	
	public void remove(int interactId) {
		interactHome.delete(interactId);
	}

	public void remove(Interact interact) {
		interactHome.delete(interact);
	}
	
	public void update(Interact interact) {
		interactHome.update(interact);
	}

	public List<Interact> findCommunityInteracts(Community community) {

		return findCommunityInteracts(community.getId());
	}

	public List<Interact> findCommunityInteracts(int community_id) {

		return interactHome.findByCommunityId(community_id);
	}
	
	public Interact findById(int interactId) {
		return interactHome.findById(interactId);
	}
}