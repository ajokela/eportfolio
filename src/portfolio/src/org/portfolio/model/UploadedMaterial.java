/* $Name:  $ */
/* $Id: UploadedMaterial.java,v 1.41 2011/03/14 19:37:44 ajokela Exp $ */
package org.portfolio.model;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.bsf.BSFException;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.portfolio.util.PortfolioConstants;

public class UploadedMaterial extends FileElementBase {

    public UploadedMaterial() throws BSFException {
		super();
		
	}

	private static final double MEGABYTE = 1024 * 1024.0;
    private static final long serialVersionUID = 7185222378812480507L;
    
    @SuppressWarnings("unused")
	private static final List<String> unsupportedAudioTypes = Arrays.asList("audio/x-m4a");
    private static final List<String> unsupportedImageTypes = Arrays.asList("image/tiff");

    private String description;
    private String author;
    private String mimeType;
    private int width;
    private int height;
    private int metadata_id;
    
    private boolean is_deleted;
    
    private List<ElementDataObject> elements = new ArrayList<ElementDataObject>();

    private List<Face> faces = null;
    
    private MetaData metadata;
    private ExifData exifdata;
    
    private ImageThumbnail imageThumbnail;
    
    public boolean isImage() {
        return mimeType != null && mimeType.toLowerCase().startsWith("image") && !unsupportedImageTypes.contains(mimeType.toLowerCase());
    }

    public boolean isAudio() {
    	if (this.getFileName() != null) {
    		return this.getFileName().toLowerCase().endsWith(".mp3");
    	}
    	
    	return false;
    	
    }

    @Deprecated
    public String getSampleName() {
        return entryName;
    }

    public String getDescription() {
        return description != null ? description : EMPTY_STRING;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author != null ? author : EMPTY_STRING;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getMimeType() {
        return mimeType != null ? mimeType : EMPTY_STRING;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getSampleSizeMB() {
        DecimalFormat df = new DecimalFormat("##0.##");
        return df.format(size / MEGABYTE);
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public List<ElementDataObject> getElements() {
        return elements;
    }

    public void setElements(List<ElementDataObject> elements) {
        this.elements = elements;
    }

    public boolean getIsImage() {
        return isImage();
    }
    
    public int getMetaDataID() {
    	return metadata_id;
    }
    
    public MetaData getMetaData() {
    	return metadata;
    }
    
    public void setMetaDataID(int metadata_id) {
    	this.metadata_id = metadata_id;
    }
    
    public void setMetaData(MetaData metadata) {
    	this.metadata = metadata;
    	
    	if(this.metadata != null) {
    		this.metadata_id = this.metadata.getId();
    	}
    	else {
    		this.metadata_id = 0;
    	}
    	
    }

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        if (entryName == null || entryName.length() == 0)
            errors.add("sampleName", new ActionMessage("error.required.missing", "Material name"));

        if (fileName == null || fileName.length() == 0)
            errors.add("name", new ActionMessage("error.required.missing", "url"));

        if ((description != null) && (description.length() > PortfolioConstants.EIGHT_HUNDRED_WORDS)) {
            description = description.substring(0, PortfolioConstants.EIGHT_HUNDRED_WORDS);
            errors.add("text", new ActionMessage("error.lengthTooLong", "description", PortfolioConstants.EIGHT_HUNDRED_WORDS_DESC));
        }

        // if (isUrl()) {
        // UrlValidator urlValidator = new UrlValidator();
        // if (!urlValidator.isValid(name)) {
        // errors.add("name", new ActionMessage("error.file.invalidUrl", name));
        // }
        // }
        return errors;
    }

    public String getUrl() {
        return "GetFile?entryId=" + getEntryId();
    }

    public boolean equals(Object obj) {
        return obj instanceof UploadedMaterial && ((UploadedMaterial) obj).getEntryIdStr().equals(getEntryIdStr());
    }

    public String getEntryIdStr() {
        if (entryId == null)
            return null;
        return entryId.toString();
    }

    /**
     * if file is foo.txt, returns "txt".
     * 
     * @return
     */
    public String getFileExtension() {
        String theName = getFileName();
        int index = theName.lastIndexOf(".");
        if (index >= 0) {
            return theName.substring(index + 1);
        }
        return "";
    }

	public void setExifdata(ExifData exifdata) {
		this.exifdata = exifdata;
	}

	public ExifData getExifdata() {
		return exifdata;
	}

	public void setImageThumbnail(ImageThumbnail imageThumbnail) {
		this.imageThumbnail = imageThumbnail;
	}

	public ImageThumbnail getImageThumbnail() {
		return imageThumbnail;
	}

	public void setIsDeleted(boolean is_deleted) {
		this.is_deleted = is_deleted;
	}

	public boolean isDeleted() {
		return is_deleted;
	}

	public List<Face> getFaces() {
		return faces;
	}

	public void setFaces(List<Face> faces) {
		this.faces = faces;
	}
	
	public boolean hasFaces() {
		if (this.faces != null) {
			return true;
		}
		
		return false;
	}
}
