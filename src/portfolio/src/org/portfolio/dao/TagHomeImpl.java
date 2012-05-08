/* $Name:  $ */
/* $Id: TagHomeImpl.java,v 1.10 2010/10/27 19:24:57 ajokela Exp $ */
package org.portfolio.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("tagHome")
public class TagHomeImpl implements TagHome {

    private SimpleJdbcTemplate simpleJdbcTemplate;

    @Autowired
    public void setDataSource(@Qualifier("portfolio") DataSource dataSource) {
        this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
    }

    public void delete(String personId, BigDecimal entryId, String elementId, String text) {
        String sql = "delete from element_tag where person_id=? and entry_id=? and element_id=? and text=?";
        simpleJdbcTemplate.update(sql, personId, entryId, elementId, text);
    }

    public void deleteAll(String personId, String elementId, BigDecimal entryId) {
        String sql = "delete from element_tag where person_id=? and entry_id=? and element_id=?";
        simpleJdbcTemplate.update(sql, personId, entryId, elementId);
    }

    public boolean exists(String personId, String elementId, BigDecimal entryId, String text) {
        int count = simpleJdbcTemplate.queryForInt(
                "select count(*) from element_tag where person_id=? and entry_id=? and element_id=? and text=?",
                personId,
                entryId,
                elementId,
                text);
        return count > 0;
    }

    public List<String> findAllByElement(String personId, String elementId, BigDecimal entryId) {
        return simpleJdbcTemplate.query(
                "select text from element_tag where person_id=? and entry_id=? and element_id=?",
                new SingleColumnRowMapper<String>(),
                personId,
                entryId,
                elementId);
    }

    public List<String> findAllByEntryId(BigDecimal entryId) {
    	return simpleJdbcTemplate.query(
                "select text from element_tag where entry_id=?",
                new SingleColumnRowMapper<String>(),
                entryId);
    }
    
    public List<String> findByPersonId(String personId) {
        return simpleJdbcTemplate.query(
                "select distinct text from element_tag where person_id=?",
                new SingleColumnRowMapper<String>(),
                personId);
    }

    public void insert(String personId, BigDecimal entryId, String elementId, String text) {
        simpleJdbcTemplate.update(
                "insert into element_tag (person_id,entry_id,element_id,text) values(?,?,?,?)",
                personId,
                entryId,
                elementId,
                text);
    }
}
