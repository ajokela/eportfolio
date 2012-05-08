/* $Name:  $ */
/* $Id: UploadedMaterialHomeImpl.java,v 1.18 2011/03/24 19:03:26 ajokela Exp $ */
package org.portfolio.dao.element;

import org.portfolio.model.MetaData;
import org.portfolio.model.ExifData;
import org.portfolio.model.Person;
import org.portfolio.model.Person.UserType;

import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.bsf.BSFException;
import org.portfolio.bus.ImageThumbnailManager;
import org.portfolio.bus.ImageThumbnailManagerImpl;
import org.portfolio.dao.AbstractElementHome;
import org.portfolio.dao.FileAccessor;
import org.portfolio.dao.PersonHome;
import org.portfolio.model.ElementDataObject;
import org.portfolio.model.UploadedMaterial;
import org.portfolio.util.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;

public class UploadedMaterialHome extends AbstractElementHome  {
    
	private LogService logService = new LogService(this.getClass());
	
	@Autowired
	private PersonHome personHome;
	
	@SuppressWarnings("unused")
	@Autowired
	private ImageThumbnailHome imageThumbnailHome;
	
	@SuppressWarnings("unused")
	private ImageThumbnailManager imageThumbnailManager = new ImageThumbnailManagerImpl();
	
    @Override
    public void store(ElementDataObject data) {
        if (data.isNew()) {
            insert((UploadedMaterial) data);
        } else {
            update((UploadedMaterial) data);
        }
    }

    @Override
    public void remove(ElementDataObject data) {
        // getSimpleJdbcTemplate().update("delete from uploaded_materials where entry_id = ?", data.getEntryId());
    	getSimpleJdbcTemplate().update("UPDATE uploaded_materials SET is_deleted = 'Y' where entry_id = ?", data.getEntryId());
    }

    private final RowMapper<UploadedMaterial> rowMapper = new RowMapper<UploadedMaterial>() {
        public UploadedMaterial mapRow(ResultSet rs, int arg1) throws SQLException {
        	
        	try {
        		
        		UploadedMaterial uploadedMaterial = new UploadedMaterial();
	            uploadedMaterial.setPersonId(rs.getString("PERSON_ID"));
	            uploadedMaterial.setEntryId(rs.getBigDecimal("ENTRY_ID"));
	            uploadedMaterial.setFileName(rs.getString("NAME"));
	            uploadedMaterial.setEntryName(rs.getString("SAMPLE_NAME"));
	            uploadedMaterial.setDescription(rs.getString("DESCRIPTION"));
	            uploadedMaterial.setDateCreated(rs.getTimestamp("CREATION_DATE"));
	            uploadedMaterial.setModifiedDate(rs.getTimestamp("MODIFIED_DATE"));
	            uploadedMaterial.setAuthor(rs.getString("AUTHOR"));
	            uploadedMaterial.setMimeType(rs.getString("MIME_TYPE"));
	            uploadedMaterial.setSize(rs.getLong("SAMPLE_SIZE"));
	            uploadedMaterial.setWidth(rs.getInt("WIDTH"));
	            uploadedMaterial.setHeight(rs.getInt("HEIGHT"));
	            uploadedMaterial.setSHA2(rs.getString("SHA2"));
	            uploadedMaterial.setIsDeleted(rs.getString("IS_DELETED").contentEquals("Y") ? true : false);
	            
	            
	            boolean exists = true;
	            
	            if (rs.getString("FILE_EXISTS") != null) {
	            	exists = rs.getString("FILE_EXISTS").contains("t");
	            }
	            
	            uploadedMaterial.setFileExists(exists);
	            uploadedMaterial.setMetaDataID(rs.getInt("METADATA_ID"));
	            
	            if(uploadedMaterial.isImage()) {
	            	/*
	            	uploadedMaterial.setImageThumbnail(imageThumbnailHome.findByUploadedMaterialId(uploadedMaterial.getEntryId().intValue()));
	            	
	            	if(uploadedMaterial.getImageThumbnail() == null) {
	            		// logService.debug("==> Image Has No Thumbnail...");
	            		// imageThumbnailManager.createThumbnail(uploadedMaterial);
	            	}
	            	*/
	            }
	            
	            return uploadedMaterial;
        	}
        	catch (BSFException e) {
        		logService.debug(e);
        	}
        	catch (SQLException e) {
            	logService.debug(e);
        	}
			return null;
        }
    };
    
    private final RowMapper<MetaData> rowMapperMetaData = new RowMapper<MetaData>() {
    	public MetaData mapRow(ResultSet rs, int arg1) throws SQLException {
    		
    		try {
    			
    			MetaData md = new MetaData();
    			md.setCreatedAt(rs.getDate("CREATED_AT"));
    			md.setUpdatedAt(rs.getDate("UPDATED_AT"));
    			md.setFileName(rs.getString("FILENAME"));
    			md.setId(rs.getInt("ID"));
    			md.setIsBinary(rs.getString("IS_BINARY").contentEquals("t"));
    			
    			/*
    			if ( md.setExifData(rs.getBlob("DATA").getBytes(0L, (int)rs.getBlob("DATA").length())) ) {
    				
    			}
    			*/
    			
    		} catch (SQLException e) {
            	logService.debug(e);
        	} catch (Exception e) {
        		logService.debug(e);
        	}
    		
    		return null;
    	}
    };
    
    private final RowMapper<ExifData> rowMapperExifData = new RowMapper<ExifData>() {
    	public ExifData mapRow(ResultSet rs, int arg1) throws SQLException {
    		
    		try {
    			
    			ExifData exif = new ExifData();
    			
    			exif.setId(rs.getInt("ID"));
    			exif.setUploadedMaterialId(rs.getInt("UPLOADED_MATERIAL_ID"));
    			exif.setCreatedAt(rs.getTimestamp("CREATED_AT"));
    			exif.setUpdatedAt(rs.getTimestamp("UPDATED_AT"));
    			
    			exif.setWidth(rs.getInt("WIDTH"));
    			exif.setHeight(rs.getInt("HEIGHT"));
    			exif.setBitsPerSample(rs.getString("BITS_PER_SAMPLE"));
    			exif.setPhotometricInterpretation(rs.getInt("PHOTOMETRIC_INTERPRETATION"));
    			exif.setOrientation(rs.getInt("ORIENTATION"));
    			exif.setXres(rs.getInt("XRES"));
    			exif.setYres(rs.getInt("YRES"));
    			exif.setResolutionUnit(rs.getInt("RESOLUTION_UNIT"));
    			exif.setSoftware(rs.getString("SOFTWARE"));
    			exif.setModifyDate(rs.getString("MODIFY_DATE"));
    			exif.setExifOffset(rs.getInt("EXIF_OFFSET"));
    			exif.setExifVersion(rs.getString("EXIF_VERSION"));
    			exif.setColorSpace(rs.getInt("COLOR_SPACE"));
    			exif.setExifImageWidth(rs.getInt("EXIF_IMAGE_WIDTH"));
    			exif.setExifImageLength(rs.getInt("EXIF_IMAGE_LENGTH"));
    			exif.setCompression(rs.getInt("COMPRESSION"));
    			exif.setMake(rs.getString("MAKE"));
    			exif.setModel(rs.getString("MODEL"));
    			exif.setGpsInfo(rs.getInt("GPS_INFO"));
    			exif.setExposureTime(rs.getString("EXPOSURE_TIME"));
    			exif.setfNumber(rs.getString("F_NUMBER"));
    			exif.setExposureProgram(rs.getInt("EXPOSURE_PROGRAM"));
    			exif.setIso(rs.getInt("ISO"));
    			exif.setDateTimeOriginal(rs.getString("DATE_TIME_ORIGINAL"));
    			exif.setCreateDate(rs.getString("CREATE_DATE"));
    			exif.setApertureValue(rs.getString("APERTURE_VALUE"));
    			exif.setMeteringMode(rs.getInt("METERING_MODE"));
    			exif.setFlash(rs.getInt("FLASH"));
    			exif.setFocalLength(rs.getString("FOCAL_LENGTH"));
    			exif.setSubjectLocation(rs.getString("SUBJECT_LOCATION"));
    			exif.setFlashpixVersion(rs.getString("FLASHPIX_VERSION"));
    			exif.setSensingMode(rs.getInt("SENSING_MODE"));
    			exif.setExposureMode(rs.getInt("EXPOSURE_MODE"));
    			exif.setWhiteBalance(rs.getInt("WHITE_BALANCE"));
    			exif.setSceneCaptureType(rs.getInt("WHITE_BALANCE"));
    			exif.setSharpness(rs.getInt("SHARPNESS"));
    			exif.setGpsLatitudeRef(rs.getString("GPS_LATITUDE_REF"));
    			exif.setGpsLongitudeRef(rs.getString("GPS_LONGITUDE_REF"));
    			exif.setGpsLatitudeDegrees(rs.getDouble("GPS_LATITUDE_DEGREES"));
    			exif.setGpsLatitudeMinutes(rs.getDouble("GPS_LATITUDE_MINUTES"));
    			exif.setGpsLatitudeSeconds(rs.getDouble("GPS_LATITUDE_SECONDS"));
    			exif.setGpsLongitudeDegrees(rs.getDouble("GPS_LONGITUDE_DEGREES"));
    			exif.setGpsLongitudeMinutes(rs.getDouble("GPS_LONGITUDE_MINUTES"));
    			exif.setGpsLongitudeSeconds(rs.getDouble("GPS_LONGITUDE_SECONDS"));
				
		
        	} catch (Exception e) {
        		logService.debug(e);
        	}
    		
    		return null;
    	}
    };
    
    public List<? extends ElementDataObject> findByPersonId(String personId) {
        List<UploadedMaterial> list;
                
        list = getSimpleJdbcTemplate().query(
                "select * from uploaded_materials where person_id = ? AND is_deleted = 'N' order by upper(sample_name)",
                rowMapper,
                personId);
       
    	return list;
    }
    
    public ElementDataObject findByEntryId(BigDecimal entryId) {
    	String sql = "select * from uploaded_materials where entry_id = ? AND is_deleted = 'N'";
    
    	List<UploadedMaterial> result = getSimpleJdbcTemplate().query(sql, rowMapper, entryId);
    	
    	if (result != null && result.size() > 0 && !result.isEmpty()) {
    		return result.get(0);
    	}
    	
    	return null;
    }

    public ElementDataObject findElementInstance(String personId, BigDecimal entryId) {
    	
    	List<UploadedMaterial> result = new ArrayList<UploadedMaterial>();
    	String sql;
    	
    	if(personId != null) {
    		
	    	Person person = personHome.findByPersonId(personId);
	    	
	    	if(person.getUsertype() == UserType.CMTY) {
	    		
		        sql = "select * from uploaded_materials where entry_id = ? AND is_deleted = 'N'";
		        
		        result = getSimpleJdbcTemplate().query(sql, rowMapper, entryId);
	    	
	    	} else {
	    	
		        sql = "select * from uploaded_materials where person_id = ? and entry_id = ? AND is_deleted = 'N'";
		        
		        result = getSimpleJdbcTemplate().query(sql, rowMapper, personId, entryId);
	    	
	    	}
    	
    	} else {
    		
    		/*
    		sql = "select * from uploaded_materials where entry_id = ? AND is_deleted = 'N'";
	        result = getSimpleJdbcTemplate().query(sql, rowMapper, entryId);
	        
	        List<UploadedMaterial>tmp_list = new ArrayList<UploadedMaterial>();
	        
	        for(Iterator<UploadedMaterial> i=result.iterator(); i.hasNext();) {
	        	UploadedMaterial um = i.next();
	        	
	        }
    		*/
    	}
    	
        UploadedMaterial element = null;
        
        if (!result.isEmpty()) {
        	element = result.get(0);
        	
        }
        
        return element;
    }
    
    private void findElementMetaData(UploadedMaterial element) {
    	
    	String sql = "SELECT * FROM metadata WHERE id = ?";
    	
    	List<MetaData> mds = getSimpleJdbcTemplate().query(sql, rowMapperMetaData, element.getMetaDataID());
    	
    	if(mds != null && mds.size() > 0) {
    		element.setMetaData(mds.get(0));
    	}
    	else {
    		element.setMetaData(null);
    	}
    	
    }
    
    public void findElementExifData(UploadedMaterial element) {
    	
    	String sql = "SELECT * FROM exifdata WHERE uploaded_material_id = ?";
    	
    	List<ExifData> eds = getSimpleJdbcTemplate().query(sql, rowMapperExifData, element.getEntryId().intValue());
    	
    	if(eds != null && eds.size() > 0) {
    		element.setExifdata(eds.get(0));
    	}
    	else {
    		element.setExifdata(null);
    	}
    	
    }

    
    private void update(UploadedMaterial material) {
        String sql = "update uploaded_materials set name = ?,sample_name = ?,description = ?,modified_date = " + sysdate
                + ",author = ?,mime_type = ?" + ",sample_size = ?,width = ?,height = ?, sha2 = ?, file_exists = ?, is_deleted = ? where entry_id=?";
        getSimpleJdbcTemplate().update(
                sql,
                material.getFileName(),
                material.getEntryName(),
                material.getDescription(),
                material.getAuthor(),
                material.getMimeType(),
                material.getSize(),
                material.getWidth(),
                material.getHeight(),
                material.getSHA2(),
                material.getFileExists() ? "t" : "f",
                material.isDeleted() ? "Y" : "N",
                material.getEntryId());
        
        updateMetaExifData(material);
        
    }
    
    private void updateMetaExifData(UploadedMaterial material) {
    	
    	if(material.getExifdata() == null) {
    		
    		findElementMetaData(material);
    		
    		if(material.getMetaData() == null) {
				
				insertMetaData(material);

    		}

    		if(material.getMimeType().contentEquals("image/jpeg") || material.getMimeType().contentEquals("image/tiff")) {
    			addExifData(material);
    		}

    	}
    	else {
    		logService.debug("material.getExifdata() is not null");
    	}
    	
    }

    private void insert(UploadedMaterial material) {
        String sql = "insert into uploaded_materials(person_id,entry_id,name,sample_name,description,creation_date,modified_date,author,mime_type,sample_size,width"
                + ",height, sha2, file_exists, metadata_id, is_deleted) values (?,?,?,?,?," + sysdate + "," + sysdate + ",?,?,?,?,?,?,?,?,?)";
        int id = sequenceGenerator.getNextSequenceNumber("UPLOADID");
        int metadata_id = sequenceGenerator.getNextSequenceNumber("METADATA_ID_SEQ");
        
        String sample_name = "";
        
        if (material.getEntryName() != null) {
        	// ? material.getFileName() + material.getPersonId() + "_" + Integer.toString(id) : material.getEntryName()
        	sample_name = material.getEntryName();
        } else {
        	sample_name = material.getFileName() + material.getPersonId() + "_" + Integer.toString(id);
        }
        
        if (sample_name == null) {
        	Random generator = new Random(System.currentTimeMillis() / 1000);
        	sample_name = "SAMPLE_NAME_" + Integer.toString(generator.nextInt());
        }
        
        getSimpleJdbcTemplate().update(
                sql,
                material.getPersonId(),
                id,
                material.getFileName(),
                sample_name == null ? "SAMPLE_NAME" : sample_name,
                material.getDescription(),
                material.getAuthor(),
                material.getMimeType(),
                material.getSize(),
                material.getWidth(),
                material.getHeight(),
                material.getSHA2(),
                "t",
                metadata_id,
                "N");
        
        material.setIsDeleted(false);
        material.setMetaDataID(metadata_id);
        material.setEntryId(String.valueOf(id));
        
        insertMetaData(material);
        
        if(material.getMimeType().contentEquals("image/jpeg") || material.getMimeType().contentEquals("image/tiff")) {

        	addExifData(material);

        }
        
    }
    
    private void addExifData(UploadedMaterial material) {
    	FileAccessor fileAccessor;
    	
    	MetaData metadata = material.getMetaData();
    	
    	try {
    		
    		if (material.getElementDefinition() != null) {
	    		
	    		fileAccessor = material.getElementDefinition().getFileAccessor();
	    		
	    		InputStream is = fileAccessor.getFile(material.getPersonId(), material.getFileName());
	    		
	    		if(material.getSize() <= Integer.MAX_VALUE) {
	    		
	    			logService.debug("Setting EXIF Data");
	    			
	        		byte[] buff = new byte[(int)material.getSize()];
	        		
	        		is.read(buff, 0, (int)material.getSize());
	        		
	        		if (metadata.isPhoto(buff)) {
	        			
	        			int exifdata_id = sequenceGenerator.getNextSequenceNumber("EXIFDATA_ID_SEQ");
	        			ExifData exif = new ExifData();
	        			exif.setExifData(buff);
	        			exif.setUpdatedAt(new Date());
	        			exif.setCreatedAt(exif.getUpdatedAt());
	        			
	        			exif.setId(exifdata_id);
	        			exif.setUploadedMaterialId(material.getEntryId().intValue());
	        			
	        			insertExifData(exif);
	        			
	        		}
	        		else {
	        			logService.debug("Not a Photo[" + material.getEntryId() + "]");
	        		}
	    		}
    		}
    		else {
    			logService.debug("material[" + material.getEntryIdStr() + "].getElementDefinition() is null");
    		}
    		
    		
    	}
    	catch (Exception e) {
    		logService.debug(e);
    	}
    	
    }
    
    private void insertMetaData(UploadedMaterial material) {

    	if(material.getMetaData() == null) {
	    	
			int metadata_id = sequenceGenerator.getNextSequenceNumber("METADATA_ID_SEQ");
	    	MetaData metadata = new MetaData();
	    	metadata.setCreatedAt(new Date());
	    	metadata.setUpdatedAt(metadata.getCreatedAt());
	    	metadata.setFileName(material.getFileName());
	    	metadata.setId(metadata_id);
	    	
			String sql = "INSERT INTO metadata (id, filename, data, is_binary, updated_at, created_at) VALUES (?,?,?,?,?,?)";
			
			getSimpleJdbcTemplate().update(
	                sql,
	                metadata.getId(),
	                metadata.getFileName(),
	                metadata.getData(),
	                metadata.isBinary() ? "t" : "f",
	                metadata.getUpdatedAt(),
	                metadata.getCreatedAt() );
					
			material.setMetaData(metadata);
    	}
    	
    }
    
    private void insertExifData(ExifData exif) {

    	String sql = "INSERT INTO EXIFDATA (ID,UPLOADED_MATERIAL_ID,WIDTH,HEIGHT,BITS_PER_SAMPLE,PHOTOMETRIC_INTERPRETATION,ORIENTATION,XRES,YRES,"+ 
		"RESOLUTION_UNIT,SOFTWARE,MODIFY_DATE,EXIF_OFFSET,EXIF_VERSION,COLOR_SPACE,EXIF_IMAGE_WIDTH,EXIF_IMAGE_LENGTH,COMPRESSION,MAKE,MODEL," +
		"GPS_INFO,EXPOSURE_TIME,F_NUMBER,EXPOSURE_PROGRAM,ISO,DATE_TIME_ORIGINAL,CREATE_DATE,APERTURE_VALUE,METERING_MODE,FLASH,FOCAL_LENGTH," +
		"SUBJECT_LOCATION,FLASHPIX_VERSION,SENSING_MODE,EXPOSURE_MODE,WHITE_BALANCE,SCENE_CAPTURE_TYPE,SHARPNESS,GPS_LATITUDE_REF,GPS_LONGITUDE_REF," +
		"GPS_LATITUDE_DEGREES,GPS_LATITUDE_MINUTES,GPS_LATITUDE_SECONDS,GPS_LONGITUDE_DEGREES,GPS_LONGITUDE_MINUTES,GPS_LONGITUDE_SECONDS,UPDATED_AT,CREATED_AT) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		getSimpleJdbcTemplate().update(sql,
				exif.getId(),
				exif.getUploadedMaterialId(),
				exif.getWidth(),
				exif.getHeight(),
				exif.getBitsPerSample(),
				exif.getPhotometricInterpretation(),
				exif.getOrientation(),
				exif.getXres(),
				exif.getYres(),
				exif.getResolutionUnit(),
				exif.getSoftware(),
				exif.getModifyDate(),
				exif.getExifOffset(),
				exif.getExifVersion(),
				exif.getColorSpace(),
				exif.getExifImageWidth(),
				exif.getExifImageLength(),
				exif.getCompression(),
				exif.getMake(),
				exif.getModel(),
				exif.getGpsInfo(),
				exif.getExposureTime(),
				exif.getfNumber(),
				exif.getExposureProgram(),
				exif.getIso(),
				exif.getDateTimeOriginal(),exif.getCreateDate(),exif.getApertureValue(),exif.getMeteringMode(),exif.getFlash(),exif.getFocalLength(),
				exif.getSubjectLocation(),exif.getFlashpixVersion(),exif.getSensingMode(),exif.getExposureMode(),exif.getWhiteBalance(),exif.getSceneCaptureType(),
				exif.getSharpness(),exif.getGpsLatitudeRef(),exif.getGpsLongitudeRef(),exif.getGpsLatitudeDegrees(),exif.getGpsLatitudeMinutes(),
				exif.getGpsLatitudeSeconds(),exif.getGpsLongitudeDegrees(),exif.getGpsLongitudeMinutes(),exif.getGpsLongitudeSeconds(),
				exif.getUpdatedAt(),exif.getCreatedAt());
				
    }

	
	public boolean elementInstanceExist(String personId, BigDecimal entryId) {

		int result = 0;
    	Person person = personHome.findByPersonId(personId);
    	String sql;
    	
    	if(person.getUsertype() == UserType.CMTY) {
    		
	        sql = "select COUNT(*) from uploaded_materials where entry_id = ? AND is_deleted = 'N'";
	        
	        result = getSimpleJdbcTemplate().queryForInt(sql, entryId);
    	
    	} else {
    	
	        sql = "select COUNT(*) from uploaded_materials where person_id = ? and entry_id = ? AND is_deleted = 'N'";
	        
	        result = getSimpleJdbcTemplate().queryForInt(sql, personId, entryId);
    	
    	}

    	if(result > 0) {
    		return true;
    	}
        
		return false;
	}
    
}
