package org.portfolio.model;

import java.io.IOException;
import java.util.Date;

import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.Sanselan;
import org.apache.sanselan.formats.jpeg.JpegImageMetadata;
import org.apache.sanselan.common.IImageMetadata;
import org.apache.sanselan.common.RationalNumber;
import org.apache.sanselan.formats.tiff.TiffField;
import org.apache.sanselan.formats.tiff.TiffImageMetadata.GPSInfo;
import org.apache.sanselan.formats.tiff.constants.TiffConstants;

public class ExifData extends MetaData {

	/*
CREATE TABLE "PORTFOLIO5"."EXIFDATA"
  (
    "ID"                         NUMBER(*,0) NOT NULL ENABLE,
    "WIDTH"                      NUMBER(*,0) NOT NULL ENABLE,
    "HEIGHT"                     NUMBER(*,0) NOT NULL ENABLE,
    "BITS_PER_SAMPLE"            VARCHAR2(32 BYTE),
    "PHOTOMETRIC_INTERPRETATION" NUMBER(*,0),
    "ORIENTATION"                NUMBER(*,0),
    "XRES"                       NUMBER(*,0),
    "YRES"                       NUMBER(*,0),
    "RESOLUTION_UNIT"            NUMBER(*,0),
    "SOFTWARE"                   VARCHAR2(4000 BYTE),
    "MODIFY_DATE"                VARCHAR2(256 BYTE),
    "EXIF_OFFSET"                NUMBER(*,0),
    "EXIF_VERSION"               VARCHAR2(255 BYTE),
    "COLOR_SPACE"                NUMBER(*,0),
    "EXIF_IMAGE_WIDTH"           NUMBER(*,0),
    "EXIF_IMAGE_LENGTH"          NUMBER(*,0),
    "COMPRESSION"                NUMBER(*,0),
    "MAKE"                       VARCHAR2(4000 BYTE),
    "MODEL"                      VARCHAR2(4000 BYTE),
    "GPS_INFO"                   NUMBER(*,0),
    "EXPOSURE_TIME"              VARCHAR2(32 BYTE),
    "F_NUMBER"                   VARCHAR2(32 BYTE),
    "EXPOSURE_PROGRAM"           NUMBER(*,0),
    "ISO"                        NUMBER(*,0),
    "DATE_TIME_ORIGINAL"         VARCHAR2(255 BYTE),
    "CREATE_DATE"                VARCHAR2(32 BYTE),
    "APERTURE_VALUE"             VARCHAR2(32 BYTE),
    "METERING_MODE"              NUMBER(*,0),
    "FLASH"                      NUMBER(*,0),
    "FOCAL_LENGTH"               VARCHAR2(32 BYTE),
    "SUBJECT_LOCATION"           VARCHAR2(32 BYTE),
    "FLASHPIX_VERSION"           VARCHAR2(32 BYTE),
    "SENSING_MODE"               NUMBER(*,0),
    "EXPOSURE_MODE"              NUMBER(*,0),
    "WHITE_BALANCE"              NUMBER(*,0),
    "SCENE_CAPTURE_TYPE"         NUMBER(*,0),
    "SHARPNESS"                  NUMBER(*,0),
    "GPS_LATITUDE_REF"           VARCHAR2(255 BYTE),
    "GPS_LONGITUDE_REF"          VARCHAR2(255 BYTE),
    "GPS_LATITUDE_DEGREES"       NUMBER(16,10),
    "GPS_LATITUDE_MINUTES"       NUMBER(16,10),
    "GPS_LATITUDE_SECONDS"       NUMBER(16,10),
    "GPS_LONGITUDE_DEGREES"      NUMBER(16,10),
    "GPS_LONGITUDE_MINUTES"      NUMBER(16,10),
    "GPS_LONGITUDE_SECONDS"      NUMBER(16,10),
    "UPDATED_AT" TIMESTAMP (6) NOT NULL ENABLE,
    "CREATED_AT" TIMESTAMP (6) NOT NULL ENABLE,
    "UPLOAD_MATERIAL_ID"         NUMBER(*,0)
    
  )
	 */
	
	private IImageMetadata exifdata;
	
	private int id;
	private int width = -32;
	private int height = -32;
	private String bitsPerSample = new String("");
	private int photometricInterpretation;
	private int orientation = -32;
	private int xres = -32;
	private int yres = -32;
	private int resolutionUnit = -32;
	private String software = new String("");
	private String modifyDate = new String("");
	private int exifOffset = -32;
	private String exifVersion = new String("");
	private int colorSpace = -32;
	private int exifImageWidth = -32;
	private int exifImageLength = -32;
	private int compression = -32;
	private String make = new String("");
	private String model = new String("");
	private int gpsInfo = -32;
	private String exposureTime = new String("");
	private String fNumber = new String("");
	private int exposureProgram = -32;
	private int iso = -32;
	private String dateTimeOriginal = new String("");
	private String createDate = new String("");
	private String apertureValue = new String("");
	private int meteringMode = -32;
	private int flash = -32;
	private String focalLength = new String("");
	private String subjectLocation = new String("");
	private String flashpixVersion = new String("");
	private int sensingMode = -32;
	private int exposureMode = -32;
	private int whiteBalance = -32;
	private int sceneCaptureType = -32;
	private int sharpness = -32;
	private String gpsLatitudeRef = new String("");
	private String gpsLongitudeRef = new String("");
	private Double gpsLatitudeDegrees = -1132.0;
	private Double gpsLatitudeMinutes = -1132.0;
	private Double gpsLatitudeSeconds = -1132.0;
	private Double gpsLongitudeDegrees = -1132.0;
	private Double gpsLongitudeMinutes = -1132.0;
	private Double gpsLongitudeSeconds = -1132.0;
	private Date updatedAt;
	private Date createdAt;
	private int uploadedMaterialId;

	public ExifData() {
		super();
	}
	
	public boolean setExifData(byte[] data) throws ImageReadException, IOException {
		
		if(isPhoto(data)) {
			
			this.exifdata = Sanselan.getMetadata(data);
			
			if (this.exifdata instanceof JpegImageMetadata) {
				
				JpegImageMetadata jpegMetadata = (JpegImageMetadata) this.exifdata;
			    
				TiffField field = null;
				
				if(jpegMetadata != null) {
				
					logService.debug(jpegMetadata.toString());
					
					field = jpegMetadata.findEXIFValue(TiffConstants.EXIF_TAG_WIDTH_RESOLUTION);
					
					if(field != null) {
						setWidth(field.getIntValue());
					}
					
					field = jpegMetadata.findEXIFValue(TiffConstants.EXIF_TAG_HEIGHT_RESOLUTION);
					
					if(field != null) {
						setHeight(field.getIntValue());
					}
					
					field = jpegMetadata.findEXIFValue(TiffConstants.EXIF_TAG_BITS_PER_SAMPLE);
					
					if(field != null) {
						setBitsPerSample(field.getValueDescription());
					}
					
					field = jpegMetadata.findEXIFValue(TiffConstants.EXIF_TAG_PHOTOMETRIC_INTERPRETATION);
					
					if(field != null) {
						setPhotometricInterpretation(field.getIntValue());
					}
	
					field = jpegMetadata.findEXIFValue(TiffConstants.EXIF_TAG_ORIENTATION);
					
					if(field != null) {
						setOrientation(field.getIntValue());
					}
					
					field = jpegMetadata.findEXIFValue(TiffConstants.EXIF_TAG_XRESOLUTION);
					
					if(field != null) {
						setXres(field.getIntValue());
					}
					
					field = jpegMetadata.findEXIFValue(TiffConstants.EXIF_TAG_YRESOLUTION);
					
					if(field != null) {
						setYres(field.getIntValue());
					}
	
					field = jpegMetadata.findEXIFValue(TiffConstants.EXIF_TAG_RESOLUTION_UNIT);
					
					if(field != null) {
						setResolutionUnit(field.getIntValue());
					}
	
					field = jpegMetadata.findEXIFValue(TiffConstants.EXIF_TAG_SOFTWARE);
					
					if(field != null) {
						setSoftware(field.getValueDescription());
					}
	
					field = jpegMetadata.findEXIFValue(TiffConstants.EXIF_TAG_MODIFY_DATE);
					
					if(field != null) {
						setModifyDate(field.getValueDescription());
					}
	
					field = jpegMetadata.findEXIFValue(TiffConstants.EXIF_TAG_EXIF_OFFSET);
					
					if(field != null) {
						setExifOffset(field.getIntValue());
					}
					
					field = jpegMetadata.findEXIFValue(TiffConstants.EXIF_TAG_EXIF_VERSION);
					
					if(field != null) {
						setExifVersion(field.getValueDescription());
					}
					
					field = jpegMetadata.findEXIFValue(TiffConstants.EXIF_TAG_COLOR_SPACE);
					
					if(field != null) {
						setColorSpace(field.getIntValue());
					}
					
					field = jpegMetadata.findEXIFValue(TiffConstants.EXIF_TAG_IMAGE_WIDTH);
					
					if(field != null) {
						setExifImageWidth(field.getIntValue());
					}
					
					field = jpegMetadata.findEXIFValue(TiffConstants.EXIF_TAG_IMAGE_HEIGHT);
					
					if(field != null) {
						setExifImageLength(field.getIntValue());
					}
					
					field = jpegMetadata.findEXIFValue(TiffConstants.EXIF_TAG_COMPRESSION);
					
					if(field != null) {
						setCompression(field.getIntValue());
					}
					
					field = jpegMetadata.findEXIFValue(TiffConstants.EXIF_TAG_MAKE);
					
					if(field != null) {
						setMake(field.getValueDescription());
					}
					
					field = jpegMetadata.findEXIFValue(TiffConstants.EXIF_TAG_MODEL);
					
					if(field != null) {
						setModel(field.getValueDescription());
					}
					
					field = jpegMetadata.findEXIFValue(TiffConstants.EXIF_TAG_GPSINFO);
					
					if(field != null) {
						setGpsInfo(field.getIntValue());
					}
					
					field = jpegMetadata.findEXIFValue(TiffConstants.EXIF_TAG_EXPOSURE_TIME);
					
					if(field != null) {
						setExposureTime(field.getValueDescription());
					}
					
					field = jpegMetadata.findEXIFValue(TiffConstants.EXIF_TAG_FNUMBER);
					
					if(field != null) {
						setfNumber(field.getValueDescription());
					}
					
					field = jpegMetadata.findEXIFValue(TiffConstants.EXIF_TAG_EXPOSURE_PROGRAM);
					
					if(field != null) {
						setExposureProgram(field.getIntValue());
					}
					
					field = jpegMetadata.findEXIFValue(TiffConstants.EXIF_TAG_ISO);
					
					if(field != null) {
						setIso(field.getIntValue());
					}
					
					field = jpegMetadata.findEXIFValue(TiffConstants.EXIF_TAG_DATE_TIME_ORIGINAL);
					
					if(field != null) {
						setDateTimeOriginal(field.getValueDescription());
					}
					
					field = jpegMetadata.findEXIFValue(TiffConstants.EXIF_TAG_CREATE_DATE);
					
					if(field != null) {
						setCreateDate(field.getValueDescription());
					}
					
					field = jpegMetadata.findEXIFValue(TiffConstants.EXIF_TAG_APERTURE_VALUE);
					
					if(field != null) {
						setApertureValue(field.getValueDescription());
					}
					
					field = jpegMetadata.findEXIFValue(TiffConstants.EXIF_TAG_METERING_MODE);
					
					if(field != null) {
						setMeteringMode(field.getIntValue());
					}
					
					field = jpegMetadata.findEXIFValue(TiffConstants.EXIF_TAG_FLASH);
					
					if(field != null) {
						setFlash(field.getIntValue());
					}
					
					field = jpegMetadata.findEXIFValue(TiffConstants.EXIF_TAG_FOCAL_LENGTH);
					
					if(field != null) {
						setFocalLength(field.getValueDescription());
					}
					
					field = jpegMetadata.findEXIFValue(TiffConstants.EXIF_TAG_SUBJECT_LOCATION_1);
					
					if(field != null) {
						setSubjectLocation(field.getValueDescription());
					}
					
					field = jpegMetadata.findEXIFValue(TiffConstants.EXIF_TAG_FLASHPIX_VERSION);
					
					if(field != null) {
						setFlashpixVersion(field.getValueDescription());
					}
					
					field = jpegMetadata.findEXIFValue(TiffConstants.EXIF_TAG_SENSING_METHOD);
					
					if(field != null) {
						setSensingMode(field.getIntValue());
					}
					
					field = jpegMetadata.findEXIFValue(TiffConstants.EXIF_TAG_EXPOSURE_MODE);
					
					if(field != null) {
						setExposureMode(field.getIntValue());
					}
					
					field = jpegMetadata.findEXIFValue(TiffConstants.EXIF_TAG_WHITE_BALANCE_1);
					
					if(field != null) {
						setWhiteBalance(field.getIntValue());
					}
					
					field = jpegMetadata.findEXIFValue(TiffConstants.EXIF_TAG_SCENE_CAPTURE_TYPE);
					
					if(field != null) {
						setSceneCaptureType(field.getIntValue());
					}
					
					field = jpegMetadata.findEXIFValue(TiffConstants.EXIF_TAG_SHARPNESS_1);
					
					if(field != null) {
						setSharpness(field.getIntValue());
					}
					
					
					if(jpegMetadata.getExif() != null && jpegMetadata.getExif().getGPS() != null) {
						GPSInfo gpsInfo = jpegMetadata.getExif().getGPS();
						
						setGpsLatitudeRef(gpsInfo.latitudeRef);
						setGpsLongitudeRef(gpsInfo.longitudeRef);
						
						RationalNumber rn = null;
						
						rn = gpsInfo.latitudeDegrees;
						
						if(rn != null) {
							setGpsLatitudeDegrees(rn.doubleValue());
						}
						
						rn = gpsInfo.latitudeMinutes;
						
						if(rn != null) {
							setGpsLatitudeMinutes(rn.doubleValue());
						}
						
						rn = gpsInfo.latitudeSeconds;
						
						if(rn != null) {
							setGpsLatitudeSeconds(rn.doubleValue());
						}
	
						rn = gpsInfo.longitudeDegrees;
						
						if(rn != null) {
							setGpsLongitudeDegrees(rn.doubleValue());
						}
	
						rn = gpsInfo.longitudeMinutes;
						
						if(rn != null) {
							setGpsLongitudeMinutes(rn.doubleValue());
						}
	
						rn = gpsInfo.longitudeSeconds;
						
						if(rn != null) {
							setGpsLongitudeSeconds(rn.doubleValue());
						}
						
					}
					
					this.has_exif = true;
				
					return true;
				}
				else {
					logService.debug("jpegMetadata is NULL");
				}
			}
			
		}
		else {
			logService.debug("==> Data does not represent a recognizable format/type.");
		}
		
		// TODO: Put other checks for data formats here
		
		return false;
	}
	

	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public int getWidth() {
		return width;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	public int getHeight() {
		return height;
	}

	public void setGpsLatitudeRef(String gpsLatitudeRef) {
		this.gpsLatitudeRef = gpsLatitudeRef;
	}


	public String getGpsLatitudeRef() {
		return gpsLatitudeRef;
	}


	public void setGpsLongitudeRef(String gpsLongitudeRef) {
		this.gpsLongitudeRef = gpsLongitudeRef;
	}


	public String getGpsLongitudeRef() {
		return gpsLongitudeRef;
	}


	public void setGpsLatitudeDegrees(Double gpsLatitudeDegrees) {
		this.gpsLatitudeDegrees = gpsLatitudeDegrees;
	}


	public Double getGpsLatitudeDegrees() {
		return gpsLatitudeDegrees;
	}


	public void setGpsLatitudeMinutes(Double gpsLatitudeMinutes) {
		this.gpsLatitudeMinutes = gpsLatitudeMinutes;
	}


	public Double getGpsLatitudeMinutes() {
		return gpsLatitudeMinutes;
	}


	public void setGpsLatitudeSeconds(Double gpsLatitudeSeconds) {
		this.gpsLatitudeSeconds = gpsLatitudeSeconds;
	}


	public Double getGpsLatitudeSeconds() {
		return gpsLatitudeSeconds;
	}


	public void setGpsLongitudeDegrees(Double gpsLongitudeDegrees) {
		this.gpsLongitudeDegrees = gpsLongitudeDegrees;
	}


	public Double getGpsLongitudeDegrees() {
		return gpsLongitudeDegrees;
	}


	public void setGpsLongitudeMinutes(Double gpsLongitudeMinutes) {
		this.gpsLongitudeMinutes = gpsLongitudeMinutes;
	}


	public Double getGpsLongitudeMinutes() {
		return gpsLongitudeMinutes;
	}


	public void setGpsLongitudeSeconds(Double gpsLongitudeSeconds) {
		this.gpsLongitudeSeconds = gpsLongitudeSeconds;
	}

	public Double getGpsLongitudeSeconds() {
		return gpsLongitudeSeconds;
	}


	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	
	public Date getCreatedAt() {
		return createdAt;
	}


	public void setBitsPerSample(String bitsPerSample) {
		this.bitsPerSample = bitsPerSample;
	}


	public String getBitsPerSample() {
		return bitsPerSample;
	}


	public void setPhotometricInterpretation(int photometricInterpretation) {
		this.photometricInterpretation = photometricInterpretation;
	}


	public int getPhotometricInterpretation() {
		return photometricInterpretation;
	}


	public void setOrientation(int orientation) {
		this.orientation = orientation;
	}


	public int getOrientation() {
		return orientation;
	}


	public void setXres(int xres) {
		this.xres = xres;
	}


	public int getXres() {
		return xres;
	}


	public void setYres(int yres) {
		this.yres = yres;
	}


	public int getYres() {
		return yres;
	}


	public void setResolutionUnit(int resolutionUnit) {
		this.resolutionUnit = resolutionUnit;
	}


	public int getResolutionUnit() {
		return resolutionUnit;
	}


	public void setSoftware(String software) {
		this.software = software;
	}


	public String getSoftware() {
		return software;
	}


	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}


	public String getModifyDate() {
		return modifyDate;
	}


	public void setExifOffset(int exifOffset) {
		this.exifOffset = exifOffset;
	}


	public int getExifOffset() {
		return exifOffset;
	}


	public void setExifVersion(String exifVersion) {
		this.exifVersion = exifVersion;
	}


	public String getExifVersion() {
		return exifVersion;
	}


	public void setColorSpace(int colorSpace) {
		this.colorSpace = colorSpace;
	}


	public int getColorSpace() {
		return colorSpace;
	}


	public void setExifImageWidth(int exifImageWidth) {
		this.exifImageWidth = exifImageWidth;
	}


	public int getExifImageWidth() {
		return exifImageWidth;
	}


	public void setExifImageLength(int exifImageLength) {
		this.exifImageLength = exifImageLength;
	}


	public int getExifImageLength() {
		return exifImageLength;
	}


	public void setCompression(int compression) {
		this.compression = compression;
	}


	public int getCompression() {
		return compression;
	}


	public void setMake(String make) {
		this.make = make;
	}


	public String getMake() {
		return make;
	}


	public void setModel(String model) {
		this.model = model;
	}


	public String getModel() {
		return model;
	}


	public void setGpsInfo(int gpsInfo) {
		this.gpsInfo = gpsInfo;
	}


	public int getGpsInfo() {
		return gpsInfo;
	}


	public void setExposureTime(String exposureTime) {
		this.exposureTime = exposureTime;
	}


	public String getExposureTime() {
		return exposureTime;
	}


	public void setfNumber(String fNumber) {
		this.fNumber = fNumber;
	}


	public String getfNumber() {
		return fNumber;
	}


	public void setExposureProgram(int exposureProgram) {
		this.exposureProgram = exposureProgram;
	}


	public int getExposureProgram() {
		return exposureProgram;
	}


	public void setIso(int iso) {
		this.iso = iso;
	}


	public int getIso() {
		return iso;
	}


	public void setDateTimeOriginal(String dateTimeOriginal) {
		this.dateTimeOriginal = dateTimeOriginal;
	}


	public String getDateTimeOriginal() {
		return dateTimeOriginal;
	}


	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}


	public String getCreateDate() {
		return createDate;
	}


	public void setApertureValue(String apertureValue) {
		this.apertureValue = apertureValue;
	}


	public String getApertureValue() {
		return apertureValue;
	}


	public void setMeteringMode(int meteringMode) {
		this.meteringMode = meteringMode;
	}


	public int getMeteringMode() {
		return meteringMode;
	}


	public void setFlash(int flash) {
		this.flash = flash;
	}


	public int getFlash() {
		return flash;
	}


	public void setFocalLength(String focalLength) {
		this.focalLength = focalLength;
	}


	public String getFocalLength() {
		return focalLength;
	}


	public void setSubjectLocation(String subjectLocation) {
		this.subjectLocation = subjectLocation;
	}


	public String getSubjectLocation() {
		return subjectLocation;
	}


	public void setFlashpixVersion(String flashpixVersion) {
		this.flashpixVersion = flashpixVersion;
	}


	public String getFlashpixVersion() {
		return flashpixVersion;
	}


	public void setSensingMode(int sensingMode) {
		this.sensingMode = sensingMode;
	}


	public int getSensingMode() {
		return sensingMode;
	}


	public void setExposureMode(int exposureMode) {
		this.exposureMode = exposureMode;
	}


	public int getExposureMode() {
		return exposureMode;
	}


	public void setWhiteBalance(int whiteBalance) {
		this.whiteBalance = whiteBalance;
	}


	public int getWhiteBalance() {
		return whiteBalance;
	}


	public void setSceneCaptureType(int sceneCaptureType) {
		this.sceneCaptureType = sceneCaptureType;
	}


	public int getSceneCaptureType() {
		return sceneCaptureType;
	}


	public void setSharpness(int sharpness) {
		this.sharpness = sharpness;
	}


	public int getSharpness() {
		return sharpness;
	}


	public void setUploadedMaterialId(int uploadedMaterialId) {
		this.uploadedMaterialId = uploadedMaterialId;
	}


	public int getUploadedMaterialId() {
		return uploadedMaterialId;
	}

	
}