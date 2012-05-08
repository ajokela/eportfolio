package org.portfolio.bus;

import org.portfolio.model.ImageThumbnail;
import org.portfolio.model.UploadedMaterial;

public interface ImageThumbnailManager {
	
	ImageThumbnail createThumbnail(UploadedMaterial uploadedMaterial);
	
}