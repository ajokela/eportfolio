/* $Name:  $ */
/* $Id: CssAntTask.groovy,v 1.5 2011/01/19 03:11:03 shee0109 Exp $ */
package org.portfolio.util

import java.io.File
import java.io.IOException
import java.io.StringWriter
import java.util.List
import java.util.logging.Level

import org.apache.tools.ant.BuildException
import org.apache.tools.ant.Task
import org.apache.tools.ant.filters.StringInputStream;
import org.apache.tools.ant.types.DirSet;

import com.asual.lesscss.LessEngine
import com.yahoo.platform.yui.compressor.CssCompressor

public class CssAntTask extends Task {

    String destdir
    def dirSets = []
    LessEngine engine
                   
    public void add(DirSet dirSet) {
        dirSets << dirSet
    }
    
    @Override
    public void init() throws BuildException {
        // class loader issues
        // http://enfranchisedmind.com/blog/posts/jruby-cucumber-gradle/
//        Thread.currentThread().contextClassLoader = LessEngine.class.classLoader
        engine = new LessEngine()
    }
    
    @Override
    public void execute() throws BuildException {
        def dirSet = dirSets[0]
        def dirs = dirSet.getDirectoryScanner(project).includedDirectories.collect { new File(dirSet.getDir(project), it) }

        dirs.each { dir ->
            println "processing: $dir.name"
            def newCss = new StringBuffer()
            
            def lessFiles = dir.listFiles([accept:{file-> file ==~ /.*?\.less/ }] as FileFilter)
            if (lessFiles) {
                newCss << compileLess(lessFiles)
            }
            
            def cssFiles = dir.listFiles([accept:{file-> file ==~ /.*?\.css/ }] as FileFilter)
            if (cssFiles) {
                newCss << cssFiles.collect { it.text }.join('\n')
            }
            
            if (lessFiles || cssFiles) {
                new File(destdir).mkdirs()
                def outFile = new File(destdir, "${dir.name}.css")
                if (outFile.exists()) {
                    outFile.delete();
                }
                outFile << newCss.toString()
            }
        }
    }

    private String minifyCss(source) {
        try {
            StringWriter stringWriter = new StringWriter()
            CssCompressor cssCompressor = new CssCompressor(new StringInputStream(source))
            cssCompressor.compress(stringWriter, 0)
            return stringWriter.toString()
        } catch (e) {
            throw new BuildException(e)
        }
    }

    private String compileLess(files) {

        def result = new StringBuffer()
        try {
            files.each {
                String text = engine.compile(it.text)
                result <<  text
            }
        } catch (e) {
            throw new BuildException(e)
        }
        
        return result.toString().replaceAll('\\\\n', '\n')
    }

    @Override
    void finalize() {
        super.finalize()
        engine.destroy()
    }
}
