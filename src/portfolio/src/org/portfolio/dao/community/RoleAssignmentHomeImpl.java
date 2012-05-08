/* $Name:  $ */
/* $Id: RoleAssignmentHomeImpl.java,v 1.12 2010/10/27 19:24:57 ajokela Exp $ */
package org.portfolio.dao.community;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import javax.sql.DataSource;

import org.portfolio.dao.PersonHome;
import org.portfolio.model.Person;
import org.portfolio.model.community.Community;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * @author Matt Sheehan
 * 
 */
@Repository("roleAssignmentHome")
public class RoleAssignmentHomeImpl implements RoleAssignmentHome {

    @Autowired
    private PersonHome personHome;
    @Autowired
    private CommunityHome communityHome;
    
    private SimpleJdbcTemplate simpleJdbcTemplate;

    @Autowired
    public void setDataSource(@Qualifier("portfolio") DataSource dataSource) {
        this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
    }

    private final RowMapper<Community> communityRowMapper = new RowMapper<Community>() {
        public Community mapRow(ResultSet rs, int rowNum) throws SQLException {
            return communityHome.findByCommunityId(rs.getInt("community_id"));
        }
    };

    public void insert(String communityId, String personId, String roleTypeCode) {
        String sql = "insert into cy_role_assignment (community_id,person_id,role_type,assignment_date) values (?,?,?,?)";
        simpleJdbcTemplate.update(sql, communityId, personId, roleTypeCode, new Timestamp(System.currentTimeMillis()));
    }

    public void delete(String communityId, String personId, String roleTypeCode) {
        String sql = "delete from cy_role_assignment where community_id=? and person_id=? and role_type=?";
        simpleJdbcTemplate.update(sql, communityId, personId, roleTypeCode);
    }

    public void delete(String communityId, String personId) {
        String sql = "delete from cy_role_assignment where community_id=? and person_id=?";
        simpleJdbcTemplate.update(sql, communityId, personId);
    }

    public List<Person> findPersonsByCommunityIdAndRoleTypeCode(String communityId, String roleTypeCode) {
        String sql = "select person_id from cy_role_assignment where community_id=? and role_type=?";
        RowMapper<Person> rowMapper = new RowMapper<Person>() {
            public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
                return personHome.findByPersonId(rs.getString("person_id"));
            }
        };
        return simpleJdbcTemplate.query(sql, rowMapper, communityId, roleTypeCode);
    }

    public List<String> findRolesByCommunityIdAndPersonId(String communityId, String personId) {
        String sql = "select role_type from cy_role_assignment where community_id=? and person_id=?";
        return simpleJdbcTemplate.query(sql, new SingleColumnRowMapper<String>(), communityId, personId);
    }

    public List<Community> findCommunitiesByPersonIdAndRoleTypeCode(String personId, String roleTypeCode) {
        String sql = "select a.community_id from cy_role_assignment a, cy_community b where a.person_id=? and a.role_type=? AND b.id = a.community_id ORDER BY LOWER(b.name)";
        return simpleJdbcTemplate.query(sql, communityRowMapper, personId, roleTypeCode);
    }

    public List<Community> findCommunitiesByPersonId(String personId) {
        String sql = "SELECT DISTINCT x.community_id FROM (select a.community_id from cy_role_assignment a, cy_community b where a.person_id=? AND b.id = a.community_id ORDER BY LOWER(b.name)) x";
        return simpleJdbcTemplate.query(sql, communityRowMapper, personId);
    }

    public void deleteAllByCommunity(int communityId) {
        String sql = "delete from cy_role_assignment where community_id=?";
        simpleJdbcTemplate.update(sql, communityId);
    }
}
