package org.portfolio.dao.community;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.portfolio.dao.SequenceGenerator;
import org.portfolio.model.community.Interact;
import org.portfolio.util.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("communityInteract")
public class InteractHomeImpl implements InteractHome {
	
    @Autowired
    private SequenceGenerator sequenceGenerator;
    private SimpleJdbcTemplate simpleJdbcTemplate;
   
	private LogService logService = new LogService(this.getClass());
    
    @Autowired
    public void setDataSource(@Qualifier("portfolio") DataSource dataSource) {
        this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
    }
    
    private final RowMapper<Interact> rowMapper = new RowMapper<Interact>() {
    	public Interact mapRow(ResultSet rs, int rowNum) throws SQLException {
    		Interact interact = new Interact();
    		
    		interact.setId(rs.getInt("ID"));
    		interact.setCommunityId(rs.getInt("COMMUNITY_ID"));
    		
    		try {
				
    			interact.setType(rs.getString("TYPE"));
	    		interact.setVal(rs.getString("VAL"));
	    		interact.setPlace(rs.getInt("PLACE"));
	    		
	    		/*
	    		interact.setCreatedAt(rs.getDate("created_at"));
	    		interact.setUpdatedAt(rs.getDate("updated_at"));
	    		*/
	    		
	    		interact.setDeleted(rs.getString("IS_DELETED").contentEquals("T") ? true : false);
			
    		} catch (Exception e) {
				logService.debug(e);
			}
    		
    		return interact;
    	}
    };
	
	public void insert(Interact interact) {
		
		
		
		String sql = "INSERT INTO interact (id, community_id, type, val, place, is_deleted, created_at, updated_at) VALUES (?, ?, ?, ?, ?, 'F', '0000-00-00 00:00:00', created_at)";
		
		int id = sequenceGenerator.getNextSequenceNumber("INTERACT_SEQ");
		
		List<Interact> interacts = simpleJdbcTemplate.query("SELECT a.* FROM interact a WHERE a.community_id = ? AND a.is_deleted = 'F' AND a.place = (SELECT MAX(place) FROM interact WHERE community_id = a.community_id AND is_deleted = a.is_deleted)", rowMapper, interact.getCommunityId());
		
		if(interacts != null && interacts.size() > 0) {
			Interact max = interacts.get(0);
			
			if(max != null) {
				interact.setPlace(max.getPlace()+1);
			}
			else {
				logService.debug("ERR: max Interact is null");
				interact.setPlace(0);
			}
		}
		else {
			interact.setPlace(0);
		}
		 
		simpleJdbcTemplate.update(sql, id, interact.getCommunityId(), interact.getType().toString(), interact.getVal(), interact.getPlace());
		
		interact.setId(id);
		
	}
	
	public void delete(int interactId) {
		delete(findById(interactId));
	}
	
	public void delete(Interact interact) {
		if(interact != null) {
			interact.setDeleted(true);
			update(interact);
		}
	}
		
	public void update(Interact interact) {
		String sql = "UPDATE interact SET type = ?, val = ?, place = ?, is_deleted = ? WHERE id = ?";
		
		simpleJdbcTemplate.update(sql, interact.getType().toString(), interact.getVal(), interact.getPlace(), interact.isDeleted() ? "T" : "F", interact.getId());
	}
	
	public Interact findById(int interactId) {
		
		String sql = "SELECT ID, COMMUNITY_ID, TYPE, VAL, PLACE, CREATED_AT, UPDATED_AT, IS_DELETED FROM interact WHERE id = ? AND is_deleted = 'F'";
		
		List<Interact> list = simpleJdbcTemplate.query(sql, rowMapper, interactId);
		
		if(list!=null && list.size() > 0) {
			return list.get(0);
		}
		
		return null;
	}
	
	public List<Interact> findByCommunityId(int community_id) {
		String sql = "SELECT ID, COMMUNITY_ID, TYPE, VAL, PLACE, CREATED_AT, UPDATED_AT, IS_DELETED FROM interact WHERE community_id = ? AND is_deleted = 'F' ORDER BY place";
		
		return simpleJdbcTemplate.query(sql, rowMapper, community_id);
	}
	
	public List<Interact> findByCommunityId(String community_id) {
		return findByCommunityId(Integer.valueOf(community_id));
	}
	
}