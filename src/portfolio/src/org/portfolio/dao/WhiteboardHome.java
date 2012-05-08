package org.portfolio.dao;

import java.util.Date;
import java.util.List;

import org.portfolio.model.Whiteboard;


public interface WhiteboardHome {
	
	List<Whiteboard> findByCommunityId(int community_id);
	List<Whiteboard> findByCommunityIdDateRange(int community_id, Date from_date, Date to_date);

	List<Whiteboard> findByCommunityId(Whiteboard wb);
	List<Whiteboard> findByCommunityIdDateRange(Whiteboard wb, Date from_date, Date to_date);
	
	void deleteByCommunityId(int community_id);
	void deleteByCommunityId(Whiteboard wb);
	void deleteByCommunityIdDateRange(int community_id, Date from_date, Date to_date);
	void deleteByCommunityIdDateRange(Whiteboard wb, Date from_date, Date to_date);
	void deleteBySessionId(String session_id);
	void deleteBySessionId(Whiteboard wb);

	String insertWhiteboard(Whiteboard wb);
	
	List<Whiteboard> findNewLinesBySessionId(int id, String session_id, String person_id);
	List<Whiteboard> findNewLinesBySessionId(Whiteboard wb);
	
}