/* $Name:  $ */
/* $Id: EntrySearchDataHomeImpl.java,v 1.14 2010/10/27 19:24:57 ajokela Exp $ */
package org.portfolio.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.portfolio.model.EntryKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author Matt Sheehan
 */
@Repository
public class EntrySearchDataHomeImpl implements EntrySearchDataHome {
    
    private SimpleJdbcTemplate simpleJdbcTemplate;

    @Autowired
    public void setDataSource(@Qualifier("portfolio") DataSource dataSource) {
        this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
    }

    private static final RowMapper<EntryKey> entryIdElementIdRowMapper = new RowMapper<EntryKey>() {
        public EntryKey mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new EntryKey(rs.getString("person_id"), rs.getString("element_id"), rs.getBigDecimal("entry_id"));
        }
    };

    public List<EntryKey> findBySystemTag(String keyword, String personId) {
        String sql = "select wei.entry_id, wcem.element_id, wei.person_id from wizard_element_instance wei, wizard_element_keyword wek, "
                + "WIZARD_ELEMENT_MAPPING wcem where wei.person_id=? and wei.wizard_element_id=wek.wizard_element_id "
                + "and wek.keyword_text=? and wei.wizard_element_id=wcem.id";
        return simpleJdbcTemplate.query(sql, entryIdElementIdRowMapper, personId, keyword);
    }

    public List<EntryKey> findLocalEntriesByWizardElementId(int wizardElementId, String personId) {
        String sql = "select distinct wcem.element_id, wei.entry_id, wei.person_id from WIZARD_ELEMENT_MAPPING wcem, "
                + "WIZARD_ELEMENT_INSTANCE wei where wcem.id = wei.wizard_element_id and wcem.id=? and wei.person_id=?";
        return simpleJdbcTemplate.query(sql, entryIdElementIdRowMapper, wizardElementId, personId);
    }

    public List<EntryKey> findLocalEntriesByWizardId(Integer wizardId, String personId) {
        String sql = "select distinct wcem.element_id, wei.entry_id, wei.person_id from WIZARD_CATEGORY wc, WIZARD_ELEMENT_MAPPING wcem, "
                + "WIZARD_ELEMENT_INSTANCE wei where wcem.id = wei.wizard_element_id and wcem.category_id = wc.id "
                + "and wc.wizard_id=? and wei.person_id=?";
        return simpleJdbcTemplate.query(sql, entryIdElementIdRowMapper, wizardId, personId);
    }

    public List<EntryKey> findLocalEntriesByWizardGroupId(Integer wizardGroupId, String personId) {
        String sql = "select distinct wcem.element_id, wei.entry_id, wei.person_id from WIZARD_CATEGORY wc, WIZARD_ELEMENT_MAPPING wcem, "
                + "WIZARD_ELEMENT_INSTANCE wei, WIZARD w where wcem.id = wei.wizard_element_id and wcem.category_id = wc.id "
                + "and wc.wizard_id=w.id and w.wizard_group_id=? and wei.person_id=?";
        return simpleJdbcTemplate.query(sql, entryIdElementIdRowMapper, wizardGroupId, personId);
    }

    public List<EntryKey> findByTag(String tagText, String personId) {
        return simpleJdbcTemplate.query(
                "select * from element_tag where text=? and person_id=?",
                entryIdElementIdRowMapper,
                tagText,
                personId);
    }
}
