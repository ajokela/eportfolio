/* $Name:  $ */
/* $Id: ImageSizer.java,v 1.8 2010/10/27 19:24:57 ajokela Exp $ */
/* =====================================================================
 *
 * The contents of this file are subject to the OSPI License Version 1.0 (the
 * License).  You may not copy or use this file, in either source code or
 * executable form, except in compliance with the License.  You may obtain a
 * copy of the License at http://www.theospi.org/.
 *
 * Software distributed under the License is distributed on an AS IS basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied.  See the License
 * for the specific language governing rights and limitations under the
 * License.
 *
 * Copyrights:
 *
 * Portions Copyright (c) 2003 the r-smart group, inc.
 *
 * Acknowledgements
 *
 * Special thanks to the OSPI Users and Contributors for their suggestions and
 * support.
 */
package org.portfolio.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.IndexColorModel;
import java.awt.image.renderable.ParameterBlock;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.media.jai.JAI;
import javax.media.jai.OpImage;
import javax.media.jai.PlanarImage;
import javax.media.jai.RenderedOp;

import com.sun.media.jai.codec.SeekableStream;

/**
 * Use this class to size images.
 * http://bits2share.netsons.org/2008/05/20/create-high-quality-thumbnails-in-java/
 */
public class ImageSizer {

    /**
     * The JAI.create action name for handling a stream.
     */
    private static final String JAI_STREAM_ACTION = "stream";
 
    /**
     * The JAI.create action name for handling a resizing using a subsample averaging technique.
     */
    private static final String JAI_SUBSAMPLE_AVERAGE_ACTION = "SubsampleAverage";
 
    /**
     * The JAI.create encoding format name for JPEG.
     */
    private static final String JAI_ENCODE_FORMAT_JPEG = "JPEG";
 
    /**
     * The JAI.create action name for encoding image data.
     */
    private static final String JAI_ENCODE_ACTION = "encode";

    @SuppressWarnings("unused")
	private static LogService logService = new LogService(ImageSizer.class);
	
    public static BufferedImage thumbnail(BufferedImage image, int x, int y, int size) {
    	BufferedImage thumbnail = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
    	
    	PlanarImage pimage = PlanarImage.wrapRenderedImage(thumbnail);
    	ParameterBlock pb = new ParameterBlock();
    	
    	float scalex = ((float)image.getWidth()) / (float)size;
    	float scaley = ((float)image.getHeight()) / (float)size;
    	float scale  = Math.min(scalex, scaley);
    	scale = Math.min(scale, 1);
    	
    	RenderingHints qualityHints = new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    	
    	@SuppressWarnings("deprecation")
		RenderedOp renderedOp = JAI.create("SubsampleAverage", image, scale, scale, qualityHints);
    	
    	try {
    		((OpImage)renderedOp.getRendering()).setTileCache(null);
    	}
    	catch(Exception e) {
    		
    	}
    	
    	pimage = PlanarImage.wrapRenderedImage(renderedOp.getAsBufferedImage());

    	pb.addSource(pimage);
		pb.add((float)x);
		pb.add((float)y);
		pb.add((float)size);
		pb.add((float)size);
		
		renderedOp = JAI.create("crop", pb);
		
		try {
			((OpImage)renderedOp.getRendering()).setTileCache(null);
		}
		catch(Exception e) {
			
		}
		
    	return renderedOp.getAsBufferedImage();
    }
    
	public static BufferedImage resizeImage(BufferedImage image, int newWidth, int newHeight) {
		BufferedImage thumbnail = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);

		float scalex = ((float) image.getWidth()) / ((float) newWidth);
		float scaley = ((float) image.getHeight()) / ((float) newHeight);

		float scale = Math.min(scalex, scaley);
		scale = Math.max(scale, 1);

		BufferedImage scaled = createResizedImage(image, Math.round(image.getWidth() / scale), Math.round(image.getHeight() / scale));

		Graphics2D g = thumbnail.createGraphics();
		g.setColor(Color.white);
		g.fillRect(0, 0, newWidth, newHeight);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.drawImage(scaled, (newWidth - scaled.getWidth()) / 2, (newHeight - scaled.getHeight()) / 2, null);
		g.dispose();

		return thumbnail;
	}

	private static BufferedImage createResizedImage(BufferedImage img, int targetWidth, int targetHeight) {

		if (img.getWidth() == targetHeight && img.getHeight() == targetHeight)
			return img;

		boolean higherQuality = true;
		boolean isIndexColorModel = img.getColorModel() instanceof IndexColorModel;
		int type;

		if (!isIndexColorModel) {
			type = (img.getTransparency() == Transparency.OPAQUE) ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
		} else {
			type = img.getType();
		}

		BufferedImage ret = (BufferedImage) img;

		int w, h;
		if (higherQuality) {
			w = img.getWidth();
			h = img.getHeight();
		} else {
			w = targetWidth;
			h = targetHeight;
		}

		do {
			if (higherQuality && w > targetWidth) {
				w = Math.max(w / 2, targetWidth);
			}
			if (higherQuality && h > targetHeight) {
				h = Math.max(h / 2, targetHeight);
			}
			
			BufferedImage tmp = isIndexColorModel ? new BufferedImage(w, h, type, (IndexColorModel) img.getColorModel()) : new BufferedImage(w, h, type);

			Graphics2D g2 = tmp.createGraphics();
			
			if (isSuperHuge(img)) {
				g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
			} else {
				g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			}
			
			g2.drawImage(ret, 0, 0, w, h, null);
			g2.dispose();

			ret = tmp;
		} while (w != targetWidth || h != targetHeight);

		return ret;
	}

	private static boolean isSuperHuge(BufferedImage img) {
		return img.getHeight() > 1500 || img.getWidth() > 1500;
	}
	
	public static byte[] resizeImageAsJPG(byte[] pImageData, int x1, int y1, int pMaxWidth) throws IOException {
        InputStream imageInputStream = new ByteArrayInputStream(pImageData);
        // read in the original image from an input stream
        SeekableStream seekableImageStream = SeekableStream.wrapInputStream(imageInputStream, true);
        RenderedOp originalImage = JAI.create(JAI_STREAM_ACTION, seekableImageStream);
        
        try {
        	((OpImage) originalImage.getRendering()).setTileCache(null);
        }
        catch(Exception e) {
        	// logService.debug(e);
        }
        
        try {
	        int origImageWidth = originalImage.getWidth();
	        // now resize the image
	        double scale = 1.0;
	        
	        
	        if ((pMaxWidth > 0 && origImageWidth > pMaxWidth)) {
	            scale = (double) pMaxWidth / originalImage.getWidth();            
	        }
	        
	        ParameterBlock paramBlock = new ParameterBlock();
	        paramBlock.addSource(originalImage); // The source image
	        paramBlock.add(scale); // The xScale
	        paramBlock.add(scale); // The yScale
	        paramBlock.add((double)x1); // The x translation
	        paramBlock.add((double)y1); // The y translation
	     
	        RenderingHints qualityHints = new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
	     
	        RenderedOp resizedImage = JAI.create(JAI_SUBSAMPLE_AVERAGE_ACTION, paramBlock, qualityHints);
	     
	        // lastly, write the newly-resized image to an output stream, in a specific encoding
	        ByteArrayOutputStream encoderOutputStream = new ByteArrayOutputStream();
	        JAI.create(JAI_ENCODE_ACTION, resizedImage, encoderOutputStream, JAI_ENCODE_FORMAT_JPEG, null);
	        // Export to Byte Array
	        byte[] resizedImageByteArray = encoderOutputStream.toByteArray();
	        return resizedImageByteArray;
	        
        }
        catch (Exception e) {
        	
        }
        
        return new byte[1];
	}
	
    public static byte[] resizeImageAsJPG(byte[] pImageData, int pMaxWidth) throws IOException {
    	
    	return resizeImageAsJPG(pImageData, 0, 0, pMaxWidth);
        
    }
	
}
