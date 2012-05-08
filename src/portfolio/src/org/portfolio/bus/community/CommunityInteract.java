package org.portfolio.bus.community;

import java.util.List;

import org.portfolio.model.community.Community;
import org.portfolio.model.community.Interact;

public interface CommunityInteract {
	
	void add(Interact interact);
	void remove(Interact interact);
	void remove(int interactId);
	void update(Interact interact);
	List<Interact> findCommunityInteracts(Community community);
	List<Interact> findCommunityInteracts(int community_id);
	Interact findById(int interactId);
	
}