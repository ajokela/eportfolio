/* $Name:  $ */
/* $Id: ElementsSearchFiltersDataHomeImpl.java,v 1.3 2010/10/27 19:24:57 ajokela Exp $ */
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

@Repository
public class ElementsSearchFiltersDataHomeImpl implements ElementsSearchFiltersDataHome {

    private SimpleJdbcTemplate simpleJdbcTemplate;

    @Autowired
    public void setDataSource(@Qualifier("portfolio") DataSource dataSource) {
        this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
    }

    @Override
    public List<Map<String, Object>> findCommunityDataByPersonId(String personId) {
        String query = "select distinct c.id, c.name from wizard_element_instance wei, wizard_element_mapping wem, wizard_category wc, "
                + "wizard w, cy_community c where wei.person_id=? and wei.wizard_element_id = wem.id and wem.category_id = wc.id "
                + "and w.id = wc.wizard_id and c.id = w.wizard_group_id order by lower(c.name)";
        return simpleJdbcTemplate.query(query, new RowMapper<Map<String, Object>>() {
            public Map<String, Object> mapRow(ResultSet rs, int arg1) throws SQLException {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("communityId", rs.getInt("id"));
                map.put("communityName", rs.getString("name"));
                return map;
            }
        }, personId);
    }

}
