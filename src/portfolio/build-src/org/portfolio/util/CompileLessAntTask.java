/* $Name:  $ */
/* $Id: CompileLessAntTask.java,v 1.4 2010/10/27 19:24:56 ajokela Exp $ */
package org.portfolio.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.FileSet;

import com.asual.lesscss.LessEngine;

public class CompileLessAntTask extends Task {

    private final List<FileSet> fileSets = new ArrayList<FileSet>();

    public void addFileset(FileSet fs) {
        fileSets.add(fs);
    }

    @Override
    public void execute() throws BuildException {
        // class loader issues
        // http://enfranchisedmind.com/blog/posts/jruby-cucumber-gradle/
//        Thread.currentThread().setContextClassLoader(LessEngine.class.getClassLoader());
        
        LessEngine engine = new LessEngine();
        int totalCount = 0;

        for (FileSet fileSet : fileSets) {
            DirectoryScanner dirScanner = fileSet.getDirectoryScanner(getProject());
            String basePath = dirScanner.getBasedir().getAbsolutePath();
            String[] files = dirScanner.getIncludedFiles();

            for (String fileString : files) {
                String inFile = basePath + "/" + fileString;
                String outFile = basePath + "/" + fileString.replace(".less", ".css");
                
                try {
                    engine.compile(new File(inFile), new File(outFile));
                } catch (Exception e) {
                    throw new BuildException(e);
                }
                totalCount++;
            }
            System.out.printf("%s files processed\n", totalCount);
        }
//        engine.destroy();
    }

}
