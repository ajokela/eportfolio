/* $Name:  $ */
/* $Id: SharedPortfolioFolderHomeImpl.java,v 1.8 2010/10/27 19:24:57 ajokela Exp $ */
package org.portfolio.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import javax.sql.DataSource;

import org.portfolio.model.SharedPortfolioFolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Home implementation for SharedPortfolioFolder
 * 
 * @author Vijay Rajagopal
 */
@Repository("sharedPortfolioFolderHome")
public class SharedPortfolioFolderHomeImpl implements SharedPortfolioFolderHome {
    
    @Autowired
    private SequenceGenerator sequenceGenerator;
    private SimpleJdbcTemplate simpleJdbcTemplate;

    @Autowired
    public void setDataSource(@Qualifier("portfolio") DataSource dataSource) {
        this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
    }

    private RowMapper<SharedPortfolioFolder> folderMapper = new RowMapper<SharedPortfolioFolder>() {

        public SharedPortfolioFolder mapRow(ResultSet rs, int rowNum) throws SQLException {
            SharedPortfolioFolder folder = new SharedPortfolioFolder();
            folder.setPersonId(rs.getString("PERSON_ID"));
            folder.setFolderName(rs.getString("FOLDER_NAME"));
            folder.setFolderId(rs.getBigDecimal("FOLDER_ID"));
            folder.setCreatedDate(rs.getDate("CREATED_DATE"));
            return folder;
        }
    };

    public List<SharedPortfolioFolder> findByPersonId(String personId) {
        return simpleJdbcTemplate.query("select * from shared_portfolio_folders where PERSON_ID = ?", folderMapper, personId);
    }

    public SharedPortfolioFolder findById(BigDecimal folderId) {
        return simpleJdbcTemplate.queryForObject("select * from shared_portfolio_folders where folder_id = ?", folderMapper, folderId);
    }

    public void insert(SharedPortfolioFolder folder) {
        String sql = "insert into shared_portfolio_folders(Person_id,folder_id,folder_name,created_date) " + "values (?,?,?,?)";
        int folderId = sequenceGenerator.getNextSequenceNumber("PORT_FOLDER_ID");
        simpleJdbcTemplate.update(
                sql,
                folder.getPersonId(),
                folderId,
                folder.getFolderName(),
                new Timestamp(System.currentTimeMillis()));
        folder.setFolderId(new BigDecimal(folderId));

    }

    public void update(BigDecimal folderId, String newFolderName) {
        String sql = "update shared_portfolio_folders set FOLDER_NAME=? where folder_id = ?";
        simpleJdbcTemplate.update(sql, newFolderName, folderId);
    }

    public void delete(BigDecimal folderId) {
        String sql = "delete shared_portfolio_folders where folder_id = ?";
        simpleJdbcTemplate.update(sql, folderId);
    }
}
