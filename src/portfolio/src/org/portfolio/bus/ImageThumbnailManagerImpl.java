package org.portfolio.bus;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.portfolio.dao.FileAccessor;
import org.portfolio.dao.FileAccessorImpl;
import org.portfolio.dao.element.ImageThumbnailHome;
import org.portfolio.model.ImageThumbnail;
import org.portfolio.model.UploadedMaterial;
import org.portfolio.util.FileUtil;
import org.portfolio.util.ImageSizer;
import org.portfolio.util.LogService;
import org.springframework.beans.factory.annotation.Autowired;

public class ImageThumbnailManagerImpl implements ImageThumbnailManager {
	
	@Autowired
	private ImageThumbnailHome ith;
	
	private LogService logService = new LogService(this.getClass());
	public ImageThumbnail createThumbnail(UploadedMaterial uploadedMaterial) {
		ImageThumbnail imageThumbnail = null;
		
		try {
			
			if(uploadedMaterial != null) {
				
				InputStream is = uploadedMaterial.getFile();
				
				if(is != null) {

					BufferedImage sourceImage = ImageIO.read(is);
					
					if(sourceImage != null) {
					
						double square = 0.0;
						
				    	if(sourceImage.getWidth() < sourceImage.getHeight()) {
				    		square = sourceImage.getWidth();
				    	}
				    	else {
				    		square = sourceImage.getHeight();
				    	}

						double x = (sourceImage.getWidth() - square) / 2.0;
						double y = (sourceImage.getHeight() - square) / 2.0;
					
						BufferedImage bImage = ImageSizer.thumbnail(sourceImage, (int)x, (int)y, 240); // ImageIO.read(in);

						if(bImage != null) {

							ByteArrayOutputStream baos = new ByteArrayOutputStream();
							ImageIO.write( bImage, "jpg", baos );
							byte[] sized = baos.toByteArray();

							int w = bImage.getWidth();
							int h = bImage.getHeight();

							imageThumbnail = new ImageThumbnail();
							String fileName = FileUtil.randomFileName("jpg");
							
							imageThumbnail.setFileName(fileName);
							imageThumbnail.setUploaded_material_id(uploadedMaterial.getEntryId().intValue());
			
							float percent = (((float)w / (float)sourceImage.getWidth()) + ((float)h / (float)sourceImage.getHeight())) / 2.0f;
							
							imageThumbnail.setPercent_size(percent);
							
							imageThumbnail.setPersonId(uploadedMaterial.getPersonId());
							
							FileAccessor fa = new FileAccessorImpl();
							
							logService.debug("==> Saving " + imageThumbnail.getFileName());
							
							imageThumbnail = (ImageThumbnail)fa.saveFile(imageThumbnail, sized);
							
							imageThumbnail.setFilesize(fa.getFileSize(imageThumbnail));
							
							imageThumbnail.setMimeType("image/jpeg");
							
							ith.store(imageThumbnail);
							
						}
					
						
					}
 					
				}
			}
			
		} catch (Exception e) {
			logService.debug(e);
		}
		
		return imageThumbnail;
	}
	
}