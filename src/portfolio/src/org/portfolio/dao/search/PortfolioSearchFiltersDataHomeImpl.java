/* $Name:  $ */
/* $Id: PortfolioSearchFiltersDataHomeImpl.java,v 1.4 2010/10/27 19:24:57 ajokela Exp $ */
package org.portfolio.dao.search;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * @author Matt Sheehan
 * 
 */
@Repository
public class PortfolioSearchFiltersDataHomeImpl implements PortfolioSearchFiltersDataHome {

    private SimpleJdbcTemplate simpleJdbcTemplate;

    @Autowired
    public void setDataSource(@Qualifier("portfolio") DataSource dataSource) {
        this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
    }

    private String findCommunityIdsByPersonIdQuery = "select distinct c.id, c.name from share_definition sd, template t, cy_community c "
            + "where (sd.person_id=? or exists (select 'x' from viewer v where v.person_id=? and v.share_id=sd.share_id)) "
            + "and sd.template_id = t.template_id and t.community_id=c.id order by lower(c.name)";

    public List<Map<String, Object>> findCommunityDataByPersonId(String personId) {
        return simpleJdbcTemplate.query(findCommunityIdsByPersonIdQuery, new RowMapper<Map<String, Object>>() {
            public Map<String, Object> mapRow(ResultSet rs, int arg1) throws SQLException {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("communityId", rs.getInt("id"));
                map.put("communityName", rs.getString("name"));
                return map;
            }
        }, personId, personId);
    }
}
