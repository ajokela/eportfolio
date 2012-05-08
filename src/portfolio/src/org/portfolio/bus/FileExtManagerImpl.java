/* $Name:  $ */
/* $Id: ElementManagerImpl.java,v 1.22 2011/03/24 19:03:25 ajokela Exp $ */
package org.portfolio.bus;

import org.portfolio.dao.FileExtHome;
import org.portfolio.model.FileExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("fileExtManager")
public class FileExtManagerImpl implements FileExtManager {
	
	@Autowired
	private FileExtHome fileExtHome;
	
	public FileExt findByExt(String ext) {
		return fileExtHome.findByExt(ext);
	}
}