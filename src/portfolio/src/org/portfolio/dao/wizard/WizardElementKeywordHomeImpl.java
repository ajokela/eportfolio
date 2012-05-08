/* $Name:  $ */
/* $Id: WizardElementKeywordHomeImpl.java,v 1.10 2010/11/04 21:08:53 ajokela Exp $ */
package org.portfolio.dao.wizard;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import javax.sql.DataSource;

import org.portfolio.model.wizard.WizardElementDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * @author Matt Sheehan
 */
@Repository
public class WizardElementKeywordHomeImpl implements WizardElementKeywordHome {
    
    @Autowired private WizardElementDefinitionHome wizardElementDefinitionHome;
    private SimpleJdbcTemplate simpleJdbcTemplate;

    @Autowired
    public void setDataSource(@Qualifier("portfolio") DataSource dataSource) {
        this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
    }
    
    private static final RowMapper<String> rowMapper = new RowMapper<String>() {
        public String mapRow(ResultSet rs, int rowNum) throws SQLException {
            return rs.getString("keyword_text");
        }
    };

    public List<String> findKeywordsByWizardElementId(int wizardElementId) {
        String sql = "select * from wizard_element_keyword wek where wek.wizard_element_id=?";
        List<String> result = simpleJdbcTemplate.query(sql, rowMapper, wizardElementId);
        Collections.sort(result);
        return result;
    }

    public List<String> findKeywordsByPersonId(String personId) {
        String sql = "select distinct wek.keyword_text " + "from WIZARD_ELEMENT_KEYWORD wek, WIZARD_ELEMENT_INSTANCE wei "
                + "where wek.wizard_element_id = wei.wizard_element_id and wei.person_id=?";
        List<String> result = simpleJdbcTemplate.query(sql, rowMapper, personId);
        Collections.sort(result);
        return result;

    }

    public void insert(WizardElementDefinition definition, String keyword) {
        String sql = "insert into wizard_element_keyword (wizard_element_id, keyword_text) values (?,?)";
        simpleJdbcTemplate.update(sql, definition.getId(), keyword);
    }

    public boolean existsFor(WizardElementDefinition definition, String keyword) {
        List<String> keywords = findKeywordsByWizardElementId(definition.getId());
        return keywords.contains(keyword);
    }

    public List<WizardElementDefinition> findWizardElementDefinitionsByKeywordId(BigDecimal keywordId) {
        String sql = "select * from wizard_element_keyword where wek.keyword_id=?";
        RowMapper<WizardElementDefinition> wedRowMapper = new RowMapper<WizardElementDefinition>() {
            public WizardElementDefinition mapRow(ResultSet rs, int rowNum) throws SQLException {
                return wizardElementDefinitionHome.findById(rs.getInt("wizard_element_id"));
            }
        };
        return simpleJdbcTemplate.query(sql, wedRowMapper, keywordId);
    }

    public List<String> findKeywordsByEntryId(BigDecimal entryId) {
        String sql = "select distinct wek.keyword_text from wizard_element_instance wei, wizard_element_keyword wek "
                + "where wei.wizard_element_id = wek.wizard_element_id and wei.entry_id=?";
        return simpleJdbcTemplate.query(sql, rowMapper, entryId);

    }

    public void delete(WizardElementDefinition definition, String keywordText) {
        String sql = "delete from WIZARD_ELEMENT_KEYWORD where wizard_element_id=? and keyword_text=?";
        simpleJdbcTemplate.update(sql, definition.getId(), keywordText);
    }
}
