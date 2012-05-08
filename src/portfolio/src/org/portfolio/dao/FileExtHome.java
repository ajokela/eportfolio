/* $Name:  $ */
/* $Id: ElementFolderHome.java,v 1.4 2010/10/27 19:24:57 ajokela Exp $ */
package org.portfolio.dao;

import org.portfolio.model.FileExt;

public interface FileExtHome {

	FileExt findByExt(String ext);
	
}
