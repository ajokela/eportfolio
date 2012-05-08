package org.portfolio.dao.element;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.bsf.BSFException;
import org.portfolio.dao.ElementHome;
import org.portfolio.dao.SequenceGenerator;
import org.portfolio.model.ElementDataObject;
import org.portfolio.model.ImageThumbnail;
import org.portfolio.util.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("imageThumbnailHome")
public class ImageThumbnailHome implements ElementHome {
	
	@Autowired private SequenceGenerator sequenceGenerator;
	private SimpleJdbcTemplate simpleJdbcTemplate;
	private LogService logService = new LogService(this.getClass());
	
	public ImageThumbnailHome() {
		super();
	}
	

    @Autowired
    public void setDataSource(@Qualifier("portfolio") DataSource dataSource) {
        this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
    }
	
    @Override
    public void store(ElementDataObject data) {
        if (data.isNew()) {
            insert((ImageThumbnail) data);
        } else {
            update((ImageThumbnail) data);
        }
    }

	
	private final RowMapper<ImageThumbnail> rowMapper = new RowMapper<ImageThumbnail>() {
        public ImageThumbnail mapRow(ResultSet rs, int arg1) throws SQLException {
        	
        	try {
        		
        		ImageThumbnail it = new ImageThumbnail();
	            
        		it.setCreated_at(rs.getTimestamp("CREATED_AT"));
        		it.setId(rs.getInt("ID"));
        		it.setFileName(rs.getString("FILENAME"));
        		it.setUploaded_material_id(rs.getInt("UPLOADED_MATERIAL_ID"));
        		it.setUpdated_at(rs.getTimestamp("UPDATED_AT"));
        		it.setMimeType(rs.getString("MIMETYPE"));
        		it.setFilesize(rs.getLong("FILESIZE"));
        		it.setHeight(rs.getInt("HEIGHT"));
        		it.setWidth(rs.getInt("WIDTH"));
       		
	            return it;
        	}
        	catch (BSFException e) {
        		logService.debug(e);
        	}
        	catch (SQLException e) {
            	logService.debug(e);
        	} catch (Exception e) {
				// TODO Auto-generated catch block
				logService.debug(e);
			}
			return null;
        }
    };
	
	
	public ImageThumbnail findByUploadedMaterialId(int um_id) {
		String str = "SELECT * FROM image_thumbnails WHERE uploaded_material_id = ?";
		
		List<ImageThumbnail> thumbs = simpleJdbcTemplate.query(str, rowMapper, um_id);
		
		if(thumbs != null && thumbs.size() > 0 && !thumbs.isEmpty()) {
			return thumbs.get(0);
		}
		
		return null;
	}
	
	protected void insert(ImageThumbnail it) {
		int id = sequenceGenerator.getNextSequenceNumber("IMAGE_THUMBNAIL_SEQ");
		String sql = "INSERT INTO image_thumbnail (id, uploaded_material_id, percent_size, filename, filesize, mimetype, x1, y1, x2, y2, width, height, created_at, updated_at) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,sysdate,sysdate)";
	
		simpleJdbcTemplate.update(sql, id, it.getUploaded_material_id(), it.getPercent_size(), it.getFileName(), it.getFilesize(), it.getMimeType(), it.getX1(), it.getY1(), it.getX2(), it.getY2(), it.getWidth(), it.getHeight());
		
	}
	
	protected void update(ImageThumbnail it) {
		
	}
	
	protected void delete(ImageThumbnail it) {
		 
	}

	@Override
	public List<? extends ElementDataObject> findByPersonId(String personId)
			throws BSFException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ElementDataObject findElementInstance(String personId,
			BigDecimal entryId) throws BSFException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remove(ElementDataObject data) {
		// TODO Auto-generated method stub
		
	}
	
	public boolean elementInstanceExist(String personId, BigDecimal entryId) {
		/*
		Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = getDataSource().getConnection();
            ps = conn.prepareStatement(SELECT_QUERY_SINGLE_COUNT);
            ps.setString(1, personId);
            ps.setBigDecimal(2, entryId);
            rs = ps.executeQuery();

            if (rs.next()) {
            	if(rs.getInt(1) != 0) {
            		return true;
            	}
            } else {
                return false;
            }
        } catch (SQLException sqle) {
            logService.error("Exception caught when attempting to query name table.", sqle);
            throw new DataAccessException(sqle);
        } finally {
            close(conn, ps, rs);
        }
		 */
		
        return false;
	}
	
}