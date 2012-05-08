/* $Name:  $ */
/* $Id: AttachmentHomeImpl.java,v 1.6 2010/10/27 19:24:57 ajokela Exp $ */
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

@Repository
public class AttachmentHomeImpl implements AttachmentHome {

    private SimpleJdbcTemplate simpleJdbcTemplate;

    @Autowired
    public void setDataSource(@Qualifier("portfolio") DataSource dataSource) {
        this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
    }

    private RowMapper<EntryKey> rowMapper = new RowMapper<EntryKey>() {
        public EntryKey mapRow(ResultSet rs, int arg1) throws SQLException {
            return new EntryKey(rs.getString("person_id"), rs.getString("att_element_def_id"), rs.getBigDecimal("att_entry_id"));
        }
    };

    public boolean exists(EntryKey entry, EntryKey attachment) {
        return simpleJdbcTemplate.queryForInt(
                "select count(*) from element_attachment where person_id=? and element_def_id=? and entry_id=? "
                        + "and att_element_def_id=? and att_entry_id=?",
                entry.getPersonId(),
                entry.getElementDefinition().getId(),
                entry.getEntryId(),
                attachment.getElementDefinition().getId(),
                attachment.getEntryId()) > 0;
    }

    public List<EntryKey> findByEntry(EntryKey entry) {
        return simpleJdbcTemplate.query(
                "select * from element_attachment where person_id=? and element_def_id=? and entry_id=?",
                rowMapper,
                entry.getPersonId(),
                entry.getElementDefinition().getId(),
                entry.getEntryId());
    }

    public List<EntryKey> findByEntryAndType(EntryKey entry, String elementDefId) {
        return simpleJdbcTemplate.query(
                "select * from element_attachment where person_id=? and element_def_id=? and entry_id=? and att_element_def_id=? ",
                rowMapper,
                entry.getPersonId(),
                entry.getElementDefinition().getId(),
                entry.getEntryId(),
                elementDefId);
    }

    public void insert(EntryKey entry, EntryKey attachment) {
        simpleJdbcTemplate.update(
                "insert into element_attachment (person_id,element_def_id,entry_id,att_element_def_id,att_entry_id) values (?,?,?,?,?)",
                entry.getPersonId(),
                entry.getElementDefinition().getId(),
                entry.getEntryId(),
                attachment.getElementDefinition().getId(),
                attachment.getEntryId());

    }

    public void remove(EntryKey entry, EntryKey attachment) {
        simpleJdbcTemplate.update(
                "delete from element_attachment where person_id=? and element_def_id=? and entry_id=? "
                        + "and att_element_def_id=? and att_entry_id=?",
                entry.getPersonId(),
                entry.getElementDefinition().getId(),
                entry.getEntryId(),
                attachment.getElementDefinition().getId(),
                attachment.getEntryId());
    }

    public void removeAll(EntryKey entry) {
        simpleJdbcTemplate.update(
                "delete from element_attachment where person_id=? and element_def_id=? and entry_id=?",
                entry.getPersonId(),
                entry.getElementDefinition().getId(),
                entry.getEntryId());
    }

    public void removeAllByAttachment(EntryKey attachmentEntryKeyId) {
        simpleJdbcTemplate.update(
                "delete from element_attachment where person_id=? and att_element_def_id=? and att_entry_id=?",
                attachmentEntryKeyId.getPersonId(),
                attachmentEntryKeyId.getElementDefinition().getId(),
                attachmentEntryKeyId.getEntryId());
    }
}
