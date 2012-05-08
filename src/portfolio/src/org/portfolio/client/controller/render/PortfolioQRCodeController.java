package org.portfolio.client.controller.render;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.portfolio.client.controller.ApplicationController;
import org.portfolio.client.controller.NoAuthentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.common.ByteMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

@NoAuthentication
@Controller
class PortfolioQRCodeController extends ApplicationController {
	
    @RequestMapping("/qrcode/{portfolioid}")
    public void qrcode( @PathVariable("portfolioid") String portfolioid,
    							HttpServletRequest request,
    							HttpServletResponse response,
    							OutputStream os) {

    	int width, height;
    	
    	width = height = 96;
    	
    	response.setHeader("Content-Disposition", "filename=" + portfolioid + ".png");
    	response.setContentType("image/png");
    	
    	Writer writer = new QRCodeWriter();
    	InputStream in;
    	
    	try {
    		
			ByteMatrix matrix = writer.encode("https://portfolio.umn.edu/portfolio/" + portfolioid, BarcodeFormat.QR_CODE, width, height);
			//generate an image from the byte matrix
			int w = matrix.getWidth(); 
			int h = matrix.getHeight(); 

			byte[][] array = matrix.getArray();
			
			//create buffered image to draw to
			BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

			//iterate through the matrix and draw the pixels to the image
			for (int y = 0; y < height; y++) { 
				for (int x = 0; x < width; x++) { 
					int grayValue = array[y][x] & 0xff; 
					image.setRGB(x, y, (grayValue == 0 ? 0 : 0xFFFFFF));
				}
			}
			
			try {
				
				ImageIO.write(image, "png", os);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
				in = request.getSession().getServletContext().getResourceAsStream("/images/noImage.gif");
				response.setContentType("image/gif");
				
				try {
					IOUtils.copy(in, os);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
			
		} catch (WriterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			in = request.getSession().getServletContext().getResourceAsStream("/images/noImage.gif");
			response.setContentType("image/gif");
			
			try {
				IOUtils.copy(in, os);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
  
    }

}
