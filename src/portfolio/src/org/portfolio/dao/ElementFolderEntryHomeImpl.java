/* $Name:  $ */
/* $Id: ElementFolderEntryHomeImpl.java,v 1.6 2010/10/27 19:24:57 ajokela Exp $ */
package org.portfolio.dao;

import java.math.BigDecimal;
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
public class ElementFolderEntryHomeImpl implements ElementFolderEntryHome {

    private SimpleJdbcTemplate simpleJdbcTemplate;

    @Autowired
    public void setDataSource(@Qualifier("portfolio") DataSource dataSource) {
        this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
    }

    private final RowMapper<EntryKey> rowMapper = new RowMapper<EntryKey>() {
        public EntryKey mapRow(ResultSet rs, int arg1) throws SQLException {
            return new EntryKey(rs.getString("person_id"), rs.getString("element_def_id"), rs.getBigDecimal("entry_id"));
        }
    };

    public List<EntryKey> findByFolderId(BigDecimal folderId) {
        return simpleJdbcTemplate.query("select * from element_folder_entry where folder_id=?", rowMapper, folderId);
    }

    public void insert(BigDecimal folderId, EntryKey entryKey) {
        simpleJdbcTemplate.update(
                "insert into element_folder_entry (folder_id, person_id, element_def_id, entry_id) values (?,?,?,?)",
                folderId,
                entryKey.getPersonId(),
                entryKey.getElementDefinition().getId(),
                entryKey.getEntryId());
    }

    public void remove(EntryKey entryKey) {
        simpleJdbcTemplate.update("delete from element_folder_entry where person_id=? and element_def_id=? and entry_id=?", entryKey
                .getPersonId(), entryKey.getElementDefinition().getId(), entryKey.getEntryId());
    }
}
