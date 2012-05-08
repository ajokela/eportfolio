

package org.portfolio.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class StockImages {
	private final static Map<String, String> stockImages = new LinkedHashMap<String, String>();
	
	public StockImages() {
		stockImages.put("UMDLgTag-Maroon.png", "University of Minnesota Duluth");
		stockImages.put("coffeeStain.jpg", "Coffee Stain");
		stockImages.put("UMDsunrise.jpg", "Duluth Sunrise");
		stockImages.put("mgLeaves.jpg", "Maroon and Gold Leaves");
		stockImages.put("mellowBlue.jpg", "Mellow Blue");
		stockImages.put("openBook.jpg", "Open Book");
		
	}
	
	public Map<String, String> images() {
		return stockImages;
	}
}