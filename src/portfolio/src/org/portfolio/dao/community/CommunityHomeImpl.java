/* $Name:  $ */
/* $Id: CommunityHomeImpl.java,v 1.20 2011/03/24 19:03:26 ajokela Exp $ */
package org.portfolio.dao.community;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import javax.sql.DataSource;

import org.portfolio.dao.PersonHome;
import org.portfolio.dao.SequenceGenerator;
import org.portfolio.model.community.Community;
import org.portfolio.model.community.Community.CommunityType;
import org.portfolio.util.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("communityHome")
public class CommunityHomeImpl implements CommunityHome {

    @Autowired
    private PersonHome personHome;
    @Autowired
    private SequenceGenerator sequenceGenerator;
    private SimpleJdbcTemplate simpleJdbcTemplate;
    
    private LogService logService = new LogService(this.getClass());

    @Autowired
    public void setDataSource(@Qualifier("portfolio") DataSource dataSource) {
        this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
    }

    private final RowMapper<Community> rowMapper = new RowMapper<Community>() {
        public Community mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            Community community = new Community();
            int communtiyId = resultSet.getInt("id");
            community.setId(communtiyId);
            community.setName(resultSet.getString("name"));
            community.setDescription(resultSet.getString("description"));
            community.setCampusCode(resultSet.getString("campus_code"));
            community.setContactEmailAddress(resultSet.getString("contact_email_address"));
            community.setDateCreated(resultSet.getDate("date_created"));
            community.setType(CommunityType.valueOf(resultSet.getString("type")));
            community.setProgram(resultSet.getString("program"));
            community.setPrivateCommunity(resultSet.getBoolean("is_private"));
            community.setDeleted(resultSet.getBoolean("is_deleted"));
            
            community.setPerson(personHome.findCommunityPerson(communtiyId));
            
            return community;
        }
    };

    public Community findByCommunityId(int communityId) {
    	
    	String sql = "select * from cy_community where id=?";
    	Community community = null;
    	
    	try {
	        
	        community = simpleJdbcTemplate.queryForObject(sql, rowMapper, communityId);
    	}
    	catch(Exception e) {
   
    		logService.debug(e);
    		
    		try {
    			community = simpleJdbcTemplate.queryForObject(sql, rowMapper, 46);  // default ePortfolio Community ID
    		}
    		catch (Exception ee) {
    			logService.debug(ee);
    		}
    		
    	}
    	
    	return community;
    }

    public Community findByCommunityId(String communityId) {
        return findByCommunityId(Integer.parseInt(communityId));
    }

    public int insert(Community community) {
        int id = sequenceGenerator.getNextSequenceNumber("COMMUNITY_ID_SEQ");
        String sql = "insert into cy_community (id, name, description, campus_code, contact_email_address, date_created, type, program, is_private, is_deleted) "
                + "values (?,?,?,?,?,?,?,?,?,?)";
        simpleJdbcTemplate.update(
                sql,
                id,
                community.getName(),
                community.getDescription(),
                community.getCampusCode(),
                community.getContactEmailAddress(),
                new Timestamp(System.currentTimeMillis()),
                community.getType().name(),
                community.getProgram(),
                community.isPrivateCommunity() ? 1 : 0,
                community.isDeleted() ? 1 : 0);
        community.setId(id);
        
        return id;
    }

    public void delete(int communityId) {
        String sql = "delete from cy_community where id=?";
        simpleJdbcTemplate.update(sql, communityId);
    }

    public void update(Community community) {
        String sql = "update cy_community set name=?, description=?, campus_code=?, contact_email_address=?, type=?, program=?, "
                + "is_private=?, is_deleted=? where id=?";
        simpleJdbcTemplate.update(
                sql,
                community.getName(),
                community.getDescription(),
                community.getCampusCode(),
                community.getContactEmailAddress(),
                community.getType().name(),
                community.getProgram(),
                community.isPrivateCommunity() ? 1 : 0,
                community.isDeleted() ? 1 : 0,
                community.getId());
    }

    public List<Community> findAll() {
        String sql = "select * from cy_community where is_deleted=0";
        return simpleJdbcTemplate.query(sql, rowMapper);
    }

    public List<Community> findPublicCommunities() {
        String sql = "select * from cy_community where is_private=0 and is_deleted=0";
        return simpleJdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public List<Community> findDeletedCommunities() {
        String sql = "select * from cy_community where is_deleted=1";
        return simpleJdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public List<Community> findPrivateCommunities() {
        String sql = "select * from cy_community where is_private=1 and is_deleted=0";
        return simpleJdbcTemplate.query(sql, rowMapper);
    }
}
