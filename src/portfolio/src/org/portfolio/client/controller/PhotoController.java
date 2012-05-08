/* $Name:  $ */
/* $Id: PhotoController.java,v 1.24 2011/03/14 19:37:44 ajokela Exp $ */
package org.portfolio.client.controller;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.portfolio.bus.ElementManager;
import org.portfolio.bus.PermissionsManager;
import org.portfolio.client.RequestUtils;
import org.portfolio.model.ElementDataObject;
import org.portfolio.model.EntryKey;
import org.portfolio.model.FileElement;
import org.portfolio.model.Person;
import org.portfolio.util.ImageSizer;
import org.portfolio.util.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

// import org.apache.sanselan.formats.tiff.constants.TiffConstants;

@Controller
@NoAuthentication
public class PhotoController extends ApplicationController {

    private LogService logService = new LogService(this.getClass());

    @Autowired
    private ElementManager elementManager;
    @Autowired
    private PermissionsManager permissionsManager;

    
    private InputStream noImage;
    
    @RequestMapping("/photo/{entryKey}")
    public void execute(
            @PathVariable("entryKey") EntryKey entryKey,
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(value = "maxHeight", required = false) Integer maxHeight,
            @RequestParam(value = "maxWidth", required = false) Integer maxWidth,
            @RequestParam(value = "height", required = false) Integer height,
            @RequestParam(value = "width", required = false) Integer width,
            OutputStream os) throws Exception {

    	
    	System.setProperty("com.sun.media.jai.disableMediaLib", "true");
    	
        Person person = RequestUtils.getPerson(request);

        noImage = request.getSession().getServletContext().getResourceAsStream("/images/noImage.gif");
        
        boolean is_same_person = false;
        boolean is_same_entry  = false;
        boolean has_permission = false;

        if ( person != null ) {
	        is_same_person = person.getPersonId().equals(entryKey.getPersonId());
	        is_same_entry  = entryKey.equals(person.getProfilePictureKey());
        }
        else {
        	logService.debug("===> Person is NULL");
        }
        
        has_permission = permissionsManager.hasPermission(entryKey);
        
        FileElement fileElement = null;
        
        if ( !is_same_person && !is_same_entry && !has_permission ) {
            // TODO
            // Access if...
            // 1. user is owner (check)
            // 2. picture is a profile pic (check) 
            // 3. picture is shared with user via a portfolio
            // 4. picture is in a public portfolio
            // 5. picture is ucard photo for umProfile
            // throw new SecurityException("no access");
        	
        	logService.debug("======>>> Invalid Permissions ");
        	
        }
        else {
	    
        	ElementDataObject elementDataObject = elementManager.findElementInstance(entryKey, person);
	
	        if (!(elementDataObject instanceof FileElement || elementDataObject == null)) {
	            throw new Exception("not a FileElement");
	        } 
	        
	        fileElement = (FileElement) elementDataObject;
        
        }
        
        String encodedFileName = URLEncoder.encode(fileElement == null ? "noImage.gif" : fileElement.getEntryName(), "UTF-8");
        response.setHeader("Content-Disposition", "filename=" + encodedFileName);
        
        InputStream in;
        int imageWidth;
        int imageHeight;
        try {
        	
        	if (fileElement == null) {
        		in = noImage;
        		
        		BufferedImage sourceImage = ImageIO.read(noImage);
                imageWidth = sourceImage.getWidth();
                imageHeight = sourceImage.getHeight();

        	}
        	else {
        		
        		in = fileElement.getFile();
            
        		imageWidth = fileElement.getWidth();
        		imageHeight = fileElement.getHeight();

        	}
        		
            if (imageHeight <= 0 || imageWidth <= 0) {
                // some image records have no dims. try to fix
            	BufferedImage sourceImage = null;
            	
            	if (in != null) {
	                sourceImage = ImageIO.read(in);
            	}
            	else {
            		sourceImage = ImageIO.read(noImage);
            	}
            	
                imageWidth = sourceImage.getWidth();
                imageHeight = sourceImage.getHeight();
                fileElement.setWidth(imageWidth);
                fileElement.setHeight(imageHeight);
                elementManager.store(fileElement);
            }
            
        } catch (Exception e) {
            // sometimes the files don't exists
            
        	logService.debug(e);
        	
        	in = noImage; // request.getSession().getServletContext().getResourceAsStream("/images/noImage.gif");
            imageHeight = 120;
            imageWidth = 120;
            
        }

        try {
	        
	        boolean materialNeedsToBeScaled = (maxHeight != null && maxHeight < imageHeight) || (maxWidth != null && maxWidth < imageWidth);
	        if (materialNeedsToBeScaled) {
	            float scale = getScale(imageWidth, imageHeight, maxWidth, maxHeight);
	            int newHeight = (int) (imageHeight * scale);
	            int newWidth = (int) (imageWidth * scale);
	            response.setContentType("image/jpeg");
	            writeResizedImage(in, os, newHeight, newWidth);
	        } else if (height != null || width != null) {
	        	response.setContentType("image/jpeg");
	            writeResizedImage(in, os, height, width);
	        } else {
	            response.setContentType(fileElement.getMimeType());
	            IOUtils.copy(in, os);
	        }
        }
        catch (Exception e) {
        	
        	logService.debug(e);
        	
        	try {
        		
        		response.setContentType("image/gif");
        		in = noImage; // request.getSession().getServletContext().getResourceAsStream("/images/noImage.gif");
        		
        		if( in != null) {
        			IOUtils.copy(in, os);
        		}
        		
        	}
        	catch(Exception ee) {
        		logService.debug(ee);
        	}
        	
        }
    }
    
    private byte[] scale(byte[] fileData, int width, int height) {
        ByteArrayInputStream in = new ByteArrayInputStream(fileData);
        try {
			
        	BufferedImage img = ImageIO.read(in);
			
			if(height == 0) {
			    height = (width * img.getHeight())/ img.getWidth(); 
			}
			
			if(width == 0) {
				width = (height * img.getWidth())/ img.getHeight();
			}
			
			Image scaledImage = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
			BufferedImage imageBuff = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			imageBuff.getGraphics().drawImage(scaledImage, 0, 0, new Color(0,0,0), null);
			
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			
			ImageIO.write(imageBuff, "jpg", buffer);
			
			return buffer.toByteArray();
			
        } catch (IOException e) {
                logService.debug(e);
        }
        
        return null;
    }

    private void writeResizedImage(InputStream in, OutputStream os, int newHeight, int newWidth) throws IOException {
        
    	try {
	        
    		if (in != null) {
    			
		        byte[] image = IOUtils.toByteArray(in);
		        byte[] thumbnail = scale(image, newWidth, newWidth); // ImageSizer.resizeImageAsJPG(image, newWidth);
	        
		        os.write(thumbnail);
		        
    		}
    		
    	}
    	catch (IOException e) {
    		logService.debug(e);
    		try {
	    		byte[] image = IOUtils.toByteArray(noImage);
	    		byte[] thumbnail = ImageSizer.resizeImageAsJPG(image, newWidth);
	    		
	    		os.write(thumbnail);
    		}
    		catch (IOException ee) {
    			logService.debug(ee);
    		}
    	}
    }

    private float getScale(int width, int height, int maxWidth, int maxHeight) {
        return Math.min((float) maxHeight / (float) height, (float) maxWidth / (float) width);
    }
    

}
