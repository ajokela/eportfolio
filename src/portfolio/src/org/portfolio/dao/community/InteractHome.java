package org.portfolio.dao.community;

import java.util.List;

import org.portfolio.model.community.Interact;

public interface InteractHome {
	void insert(Interact interact);
	void delete(Interact interact);
	void delete(int interactId);
	void update(Interact interact);
	List<Interact> findByCommunityId(int community_id);
	List<Interact> findByCommunityId(String community_id);
	Interact findById(int interactId);
}