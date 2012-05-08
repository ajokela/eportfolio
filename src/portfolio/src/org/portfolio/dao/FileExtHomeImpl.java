/* $Name:  $ */
/* $Id: ElementFolderHomeImpl.java,v 1.8 2010/11/04 21:08:53 ajokela Exp $ */
package org.portfolio.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.portfolio.model.FileExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class FileExtHomeImpl implements FileExtHome {

    private SimpleJdbcTemplate simpleJdbcTemplate;

    @Autowired
    public void setDataSource(@Qualifier("portfolio") DataSource dataSource) {
        this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
    }

    private final RowMapper<FileExt> rowMapper = new RowMapper<FileExt>() {
        public FileExt mapRow(ResultSet rs, int arg1) throws SQLException {
            FileExt file_ext = new FileExt();
            int id = rs.getInt("ID");
            file_ext.setId(id);
            file_ext.setExt(rs.getString("EXT"));
            file_ext.setDescription(rs.getString("DESCRIPTION"));
            
            return file_ext;
        }
    };
    
	public FileExt findByExt(String ext) {
		
        List<FileExt> exts = simpleJdbcTemplate.query(
                "select * from file_extensions where LOWER(ext) = LOWER(?)",
                rowMapper,
                ext);
        
        if(exts != null && exts.size() > 0) {
        	return exts.get(0);
        }
		
		return null;
	}
}
