/* $Name:  $ */
/* $Id: ElementFolderHomeImpl.java,v 1.8 2010/11/04 21:08:53 ajokela Exp $ */
package org.portfolio.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.portfolio.model.ElementFolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ElementFolderHomeImpl implements ElementFolderHome {

    private SimpleJdbcTemplate simpleJdbcTemplate;
    @Autowired
    private SequenceGenerator sequenceGenerator;

    @Autowired
    public void setDataSource(@Qualifier("portfolio") DataSource dataSource) {
        this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
    }

    private ParameterizedRowMapper<ElementFolder> folderMapper = new ParameterizedRowMapper<ElementFolder>() {
        public ElementFolder mapRow(ResultSet rs, int rowNum) throws SQLException {
            ElementFolder folder = new ElementFolder();
            folder.setPersonId(rs.getString("person_id"));
            folder.setName(rs.getString("name"));
            folder.setId(rs.getBigDecimal("id"));
            return folder;
        }
    };

    @SuppressWarnings("deprecation")
	public List<ElementFolder> findByPersonId(String personId) {
        return simpleJdbcTemplate.query("select * from element_folder where person_id = ?", folderMapper, personId);
    }

    @SuppressWarnings("deprecation")
	public ElementFolder findById(BigDecimal folderId) {
        return simpleJdbcTemplate.queryForObject("select * from element_folder where id = ?", folderMapper, folderId);
    }

    public void insert(ElementFolder folder) {
        String sql = "insert into element_folder(person_id, id, name) " + "values (?,?,?)";
        int folderId = sequenceGenerator.getNextSequenceNumber("PORT_FOLDER_ID");
        simpleJdbcTemplate.update(sql, folder.getPersonId(), folderId, folder.getName());
        folder.setId(new BigDecimal(folderId));
    }

    public void update(BigDecimal folderId, String newFolderName) {
        simpleJdbcTemplate.update("update element_folder set name=? where id = ?", newFolderName, folderId);
    }

    public void delete(BigDecimal id) {
        simpleJdbcTemplate.update("delete from element_folder where id = ?", id);
    }

    @SuppressWarnings("deprecation")
	public ElementFolder findByName(String personId, String folderName) {
        String sql = "select * from element_folder where person_id=? and name=?";
        List<ElementFolder> result = simpleJdbcTemplate.query(sql, folderMapper, personId, folderName);
        return result.isEmpty() ? null : result.get(0);
    }
}
