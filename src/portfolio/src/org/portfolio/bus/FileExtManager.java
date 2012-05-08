/* $Name:  $ */
/* $Id: ElementManager.java,v 1.13 2010/11/23 14:27:17 ajokela Exp $ */
package org.portfolio.bus;

import org.portfolio.model.FileExt;


public interface FileExtManager {

	FileExt findByExt(String ext);
	
}
