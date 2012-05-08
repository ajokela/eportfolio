/* $Name:  $ */
/* $Id: ObjectiveHomeImpl.java,v 1.18 2011/02/16 17:35:17 ajokela Exp $ */
package org.portfolio.dao.assessment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.sql.DataSource;

import org.portfolio.dao.SequenceGenerator;
import org.portfolio.model.assessment.AssessedObjective;
import org.portfolio.model.assessment.Objective;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("objectiveHome")
public class ObjectiveHomeImpl implements ObjectiveHome {
    
    @Autowired
    private SequenceGenerator sequenceGenerator;
    private SimpleJdbcTemplate simpleJdbcTemplate;

    @Autowired
    public void setDataSource(@Qualifier("portfolio") DataSource dataSource) {
        this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
    }

    private final RowMapper<Objective> rowMapper = new RowMapper<Objective>() {
        public Objective mapRow(ResultSet rs, int rowNum) throws SQLException {
            Objective objective = new Objective();
            int objectiveId = rs.getInt("objective_id");
            objective.setId(objectiveId);
            int parentId = rs.getInt("parent_objective_id");
            objective.setParentId(parentId == 0 ? null : parentId);
            objective.setCommunityId(rs.getInt("community_id"));
            objective.setName(rs.getString("name"));
            objective.setDescription(rs.getString("description"));
            objective.setOrder(rs.getInt("sort_order"));
            objective.setDateCreated(rs.getDate("date_created"));
            objective.setDateModified(rs.getDate("date_modified"));
            // TODO would be faster to load it all up in one query
            objective.setSubObjectives(findChildObjectivesByObjectiveId(objectiveId));
            return objective;
        }
    };

    public int insert(Objective objective) {
        int id = sequenceGenerator.getNextSequenceNumber("OBJECTIVE_ID_SEQ");
        String sql = "insert into at_objective (objective_id, parent_objective_id, community_id, name, description, "
                + "sort_order, date_created, date_modified) values (?,?,?,?,?,?,?,?)";
        Timestamp now = new Timestamp(System.currentTimeMillis());
        simpleJdbcTemplate.update(
                sql,
                id,
                objective.getParentId(),
                objective.getCommunityId(),
                objective.getName(),
                objective.getDescription(),
                objective.getOrder(),
                now,
                now);
        objective.setId(id);
        
        return id;
    }

    public void update(Objective objective) {
        String sql = "update at_objective set parent_objective_id=?, community_id=?, name=?, description=?, "
                + "sort_order=?, date_modified=? where objective_id=?";
        simpleJdbcTemplate.update(
                sql,
                objective.getParentId(),
                objective.getCommunityId(),
                objective.getName(),
                objective.getDescription(),
                objective.getOrder(),
                new Timestamp(System.currentTimeMillis()),
                objective.getId());
    }

    public void delete(int objectiveId) {
        // Note database has "on delete cascade" for parent_objective_id so
        // children will also be deleted.
        
    	String sql;
    	
    	sql = "DELETE FROM AT_PERFORMANCE_DESCRIPTOR WHERE objective_id = ?";
    	
    	simpleJdbcTemplate.update(sql, objectiveId);
    	
    	sql = "delete from at_objective where objective_id=?";
        
        simpleJdbcTemplate.update(sql, objectiveId);
    }

    public List<Objective> findObjectiveSetsByCommunityId(int communityId) {
        String sql = "select * from at_objective where community_id=? and parent_objective_id is null order by sort_order";
        return simpleJdbcTemplate.query(sql, rowMapper, communityId);
    }

    private List<Objective> findChildObjectivesByObjectiveId(int objectiveId) {
        String sql = "select * from at_objective where parent_objective_id=? order by sort_order";
        return simpleJdbcTemplate.query(sql, rowMapper, objectiveId);
    }

    public int findNumberOfChildrenByObjectiveId(Integer parentId) {
        String sql = "select count(*) c from at_objective where parent_objective_id = ?";
        RowMapper<Integer> myRowMapper = new RowMapper<Integer>() {
            public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getInt(1);
            }
        };
        return simpleJdbcTemplate.queryForObject(sql, myRowMapper, parentId);
    }

    public int findNumberOfTopLevelObjectives() {
        String sql = "select count(*) c from at_objective where parent_objective_id is null";
        RowMapper<Integer> myRowMapper = new RowMapper<Integer>() {
            public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getInt(1);
            }
        };
        return simpleJdbcTemplate.queryForObject(sql, myRowMapper);
    }

    /**
     * Doesn't set Sub-Objectives but gets all the objectives of a community as a list Uses Hierarchial query to fetch the data in one query
     * Query Executed: SELECT Level,LPAD (' ', 5 * (LEVEL - 1)) || name "name", objective_id, parent_objective_id, description,
     * community_id, sort_order,date_created, date_modified FROM at_objective obj WHERE community_id = ? START WITH parent_objective_id IS
     * NULL CONNECT BY PRIOR objective_id = parent_objective_id
     * 
     * @param communityIdParam
     * @return
     */
    public List<AssessedObjective> getAssessedObjectivesForCommunity(String communityIdParam) {
        
    	// String sql = "select level,name,objective_id, parent_objective_id, description, community_id, sort_order, date_created, date_modified FROM at_objective obj WHERE community_id = ? START WITH parent_objective_id IS NULL CONNECT BY PRIOR objective_id = parent_objective_id";
        
    	List<AssessedObjective> fullList = new ArrayList<AssessedObjective>();
    	
    	_getAssessedObjectivesForCommunity(communityIdParam, null, fullList, 1);
    	
    	return fullList;
    }
    
    private void _getAssessedObjectivesForCommunity(String communityId, String parent_objective_id, List<AssessedObjective> list, Integer level) {

        RowMapper<AssessedObjective> objectivesmapper = new RowMapper<AssessedObjective>() {
            public AssessedObjective mapRow(ResultSet rs, int rowNum) throws SQLException {
                AssessedObjective objective = new AssessedObjective();
                objective.setId(rs.getInt("objective_id"));
                objective.setName(rs.getString("name"));
                objective.setLevel(rs.getInt("LEVEL"));
                objective.setDisplaySequence(rs.getInt("sort_order"));
                return objective;
            }
        };
   	
    	boolean is_null = false;
    	String sql = "";
    	List<AssessedObjective> eL = null;
    	
    	if(parent_objective_id == null) {
    		is_null = true;
    	}
    	
    	if(is_null) {
    		sql = "select " + level + " AS level,name,objective_id, parent_objective_id, description, community_id, sort_order, date_created, date_modified FROM at_objective obj WHERE community_id = ? AND parent_objective_id IS NULL";
    	}
    	else {
    		sql = "select " + level + " AS level,name,objective_id, parent_objective_id, description, community_id, sort_order, date_created, date_modified FROM at_objective obj WHERE community_id = ? AND parent_objective_id = ?";
    	}
    	
    	if(is_null) {
    		eL = simpleJdbcTemplate.query(sql, objectivesmapper, communityId);
    	}
    	else {
    		eL = simpleJdbcTemplate.query(sql, objectivesmapper, communityId, parent_objective_id);
    	}
    	
    	if(eL != null && eL.size() > 0) {
    		for(Iterator<AssessedObjective> i = eL.iterator(); i.hasNext();) {
    			AssessedObjective ao = i.next();
    			list.add(ao);
    			
    			_getAssessedObjectivesForCommunity(communityId, ao.getId().toString(), list, level + 1);
    		}
    	}
    	
    }

    /**
     * @see org.portfolio.dao.assessment.ObjectiveHome#findById(int)
     */
    public Objective findById(int objectiveId) {
        String sql = "select * from at_objective where objective_id=?";
        return simpleJdbcTemplate.queryForObject(sql, rowMapper, objectiveId);
    }

    /**
     * @see org.portfolio.dao.assessment.ObjectiveHome#findByParentIdAndOrder(java.lang.Integer, int)
     */
    public Objective findByParentIdAndOrder(Integer parentId, int order) {
        String sql = "select * from at_objective where parent_objective_id=? and sort_order=?";
        List<Objective> result = simpleJdbcTemplate.query(sql, rowMapper, parentId, order);
        return result.isEmpty() ? null : result.get(0);
    }
}
