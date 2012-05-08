/* $Name:  $ */
/* $Id: ValidateXslAntTask.java,v 1.3 2010/10/27 19:24:56 ajokela Exp $ */
package org.portfolio.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.FileSet;

/**
 * Ant task for validating XSL stylesheets. Supports fileset element.
 * 
 * @author Matt Sheehan
 */
public class ValidateXslAntTask extends Task {

	private final List<FileSet> fileSets = new ArrayList<FileSet>();
	private final TransformerFactory transformerFactory = TransformerFactory.newInstance();

	public void addFileset(FileSet fs) {
		fileSets.add( fs );
	}

	/**
	 * @see org.apache.tools.ant.Task#execute()
	 */
	@Override
	public void execute() throws BuildException {
		super.execute();
		int errorCount = 0;
		int totalCount = 0;

		System.out.println( "validating xsl files..." );
		for (FileSet fileSet : fileSets) {
			DirectoryScanner dirScanner = fileSet.getDirectoryScanner( getProject() );
			String basePath = dirScanner.getBasedir().getAbsolutePath();
			String[] xslFiles = dirScanner.getIncludedFiles();

			for (String fileString : xslFiles) {
				File file = new File( basePath + "/" + fileString );
				try {
					transformerFactory.newTransformer( new StreamSource( file ) );
				} catch (TransformerConfigurationException e) {
					// Note: transformer code writes error details to sysout.
					System.out.println( "xsl failed to compile: " + file.getAbsolutePath() );
					errorCount++;
				}
				totalCount++;
			}
		}
		System.out.printf( "%s files processed, %s failed\n", totalCount, errorCount );
	}

}
