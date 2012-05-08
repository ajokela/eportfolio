package org.portfolio.dao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.portfolio.model.Whiteboard;
import org.portfolio.util.Configuration;
import org.portfolio.util.HexTools;
import org.portfolio.util.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("whiteboardHome")
public class WhiteboardHomeImpl implements WhiteboardHome {
    private SimpleJdbcTemplate simpleJdbcTemplate;
    private final LogService logService = new LogService(this.getClass());
    
    private String sysdate_parentheses = Configuration.get("portfolio.jdbc.sysdate_uses_parentheses");
    
    @Autowired
    public void setDataSource(@Qualifier("portfolio") DataSource dataSource) {
        this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
    }
    
	private final RowMapper<Whiteboard> rowMapper = new RowMapper<Whiteboard>() {
		public Whiteboard mapRow(ResultSet rs, int arg1) throws SQLException {
			Whiteboard wb = new Whiteboard();
		
			wb.setId(rs.getInt("ID"));
			wb.setSessionId(rs.getString("SESSION_ID"));
			wb.setCommunityId(rs.getInt("COMMUNITY_ID"));
			wb.setPersonId(rs.getString("PERSON_ID"));
			wb.setColor(rs.getString("COLOR"));
			wb.setOffsetX1(rs.getInt("OFFSETX1"));
			wb.setOffsetX2(rs.getInt("OFFSETX2"));
			wb.setOffsetY1(rs.getInt("OFFSETY1"));
			wb.setOffsetY2(rs.getInt("OFFSETY2"));
			wb.setUpdatedAt(rs.getTimestamp("UPDATED_AT"));
			wb.setCreatedAt(rs.getTimestamp("CREATED_AT"));
			wb.setLength(rs.getInt("LENGTH"));
			wb.setGrouping(rs.getInt("GROUPING"));
			
			wb.setIsDeleted(rs.getString("IS_DELETED").contentEquals("T"));
			
			return wb;
		}
	};
	
	public List<Whiteboard> findByCommunityId(int community_id) {
		String sql = "SELECT * FROM whiteboard WHERE community_id = ? AND IS_DELETED = 'F' ORDER BY created_at";
		
		return simpleJdbcTemplate.query(sql, rowMapper, community_id);
	}
	
	public List<Whiteboard> findByCommunityIdDateRange(int community_id, Date from_date, Date to_date) {
		String sql = "SELECT * FROM whiteboards WHERE community_id = ? AND created_at >= ? AND created_at <= ?  AND IS_DELETED = 'F' ORDER BY created_at";
		
		return simpleJdbcTemplate.query(sql, rowMapper, community_id, from_date, to_date);
	}
	
	public 	List<Whiteboard> findNewLinesBySessionId(int id, String session_id, String person_id) {
		String sql = "SELECT * FROM whiteboards WHERE session_id = ? AND id IN (SELECT MAX(id) FROM whiteboards WHERE id > ? GROUP BY offsetx1, offsety1, offsetx2, offsety2) AND person_id <> ?";
		
		return simpleJdbcTemplate.query(sql, rowMapper, session_id, id, person_id);
	}
	
	public List<Whiteboard> findNewLinesBySessionId(Whiteboard wb) {
		return findNewLinesBySessionId(wb.getId(), wb.getSessionId(), wb.getPersonId());
	}
	
	public List<Whiteboard> findByCommunityId(Whiteboard wb) {
		return findByCommunityId(wb.getCommunityId());
	}
	
	public List<Whiteboard> findByCommunityIdDateRange(Whiteboard wb, Date from_date, Date to_date) {
		return findByCommunityIdDateRange(wb.getCommunityId(), from_date, to_date);
	}
	
	public void deleteByCommunityId(int community_id) {
		String sql = "UPDATE whiteboards SET is_deleted = 'T' WHERE community_id = ?";
		
		simpleJdbcTemplate.update(sql, community_id);
	}
	
	public void deleteByCommunityId(Whiteboard wb) {
		deleteByCommunityId(wb.getCommunityId());
	}
	
	public void deleteByCommunityIdDateRange(int community_id, Date from_date, Date to_date) {
		String sql = "UPDATE whiteboards SET is_deleted = 'T' WHERE community_id = ? AND created_at >= ? AND created_at <= ?";
		
		simpleJdbcTemplate.update(sql, community_id, from_date, to_date);
	}
	
	public void deleteByCommunityIdDateRange(Whiteboard wb, Date from_date, Date to_date) {
		deleteByCommunityIdDateRange(wb.getCommunityId(), from_date, to_date);
	}
	
	public void deleteBySessionId(String session_id) {
		String sql = "UPDATE whiteboards SET is_deleted = 'T' WHERE session_id = ?";
		
		simpleJdbcTemplate.update(sql, session_id);
	}
	public void deleteBySessionId(Whiteboard wb) {
		deleteBySessionId(wb.getSessionId());
	}

	
	public String insertWhiteboard(Whiteboard wb) {
		
		if(wb != null) {
			
			if(wb.getSessionId() == null) {
				try {
					
					MessageDigest md = MessageDigest.getInstance("SHA-256");
					md.update((new Date()).toString().getBytes());
					wb.setSessionId(HexTools.bytesToHex(md.digest()));
					
				} catch (NoSuchAlgorithmException e) {
					
					logService.debug(e);
				}
			}
			
			String sp = "sysdate";
	    	if(sysdate_parentheses != null && sysdate_parentheses.contentEquals("true")) {
	    		sp += "()";
	    	}
			
			String sql = "INSERT INTO whiteboards (id, session_id, community_id, person_id, color, offsetx1, offsetx2, offsety1, offsety2, length, grouping, updated_at, created_at, is_deleted) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, " + sp +", " + sp + ", 'F')";
			
			int length = 0;
			
			length = (int)Math.sqrt( Math.pow(wb.getOffsetX1() - wb.getOffsetX2(), 2) + Math.pow(wb.getOffsetY1() - wb.getOffsetY2(), 2) /*pow(($offsetx1-$offsetx2), 2) + pow(($offsety1-$offsety2), 2)*/);
			
			simpleJdbcTemplate.update(sql, wb.getId(), wb.getSessionId(), wb.getCommunityId(), wb.getPersonId(), wb.getColor(), wb.getOffsetX1(), wb.getOffsetX2(), wb.getOffsetY1(), wb.getOffsetY2(), length, wb.getGrouping());
			
			return wb.getSessionId();
		}
		
		return "INVALID";
	}
	
	

}