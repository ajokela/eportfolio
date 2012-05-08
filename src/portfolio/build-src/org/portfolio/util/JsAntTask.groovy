/* $Name:  $ */
/* $Id: JsAntTask.groovy,v 1.2 2010/10/27 19:28:15 ajokela Exp $ */
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

import com.google.javascript.jscomp.CompilerOptions
import com.google.javascript.jscomp.JSSourceFile
import com.google.javascript.jscomp.Result
import com.google.template.soy.SoyFileSet
import com.google.template.soy.jssrc.SoyJsSrcOptions;

public class JsAntTask extends Task {

    String destdir
    def dirSets = []
    
    public void add(DirSet dirSet) {
        dirSets << dirSet
    }
    
    @Override
    public void execute() throws BuildException {
        def dirSet = dirSets[0]
        def dirs = dirSet.getDirectoryScanner(project).includedDirectories.collect { new File(dirSet.getDir(project), it) }
        
        dirs.each { dir ->
            println "processing: $dir.name"
            def newJs = new StringBuffer()
            
            def jsFiles = dir.listFiles([accept:{file-> file ==~ /.*?\.js/ }] as FileFilter)
            if (jsFiles) {
                newJs << jsFiles.collect { it.text }.join('\n')
            }
            
            def soyFiles = dir.listFiles([accept:{file-> file ==~ /.*?\.soy/ }] as FileFilter)
            if (soyFiles) {
                newJs << compileSoy(soyFiles)
            }
            
            if (jsFiles || soyFiles) {
                new File(destdir).mkdirs()
				
				println "adding '${dir.name}.js' in ${destdir}..."
				
                def outFile = new File(destdir, "${dir.name}.js")
                if (outFile.exists()) {
                    outFile.delete();
                }
                outFile << newJs.toString()
            }
        }
    }

    private String compileSoy(files) {
        def builder = new SoyFileSet.Builder()
        files.each { builder.add it }
        SoyFileSet sfs = builder.build()
        List<String> compileToJsSrc = sfs.compileToJsSrc(new SoyJsSrcOptions(), null)
        return compileToJsSrc.join("\n")
    }

    private String minifyJs(source) {
        com.google.javascript.jscomp.Compiler.setLoggingLevel(Level.OFF)

        CompilerOptions options = new CompilerOptions()
        com.google.javascript.jscomp.Compiler compiler = new com.google.javascript.jscomp.Compiler(System.out)

        JSSourceFile sourceFile = JSSourceFile.fromInputStream("TODO.js", new StringInputStream(source))
        
        JSSourceFile[] externs = []
        JSSourceFile[] sources = [ sourceFile ]
                                   

        Result result = compiler.compile(externs, sources, options)
        if (!result.success) {
            throw new BuildException(result.errors.join("\n"))
        }
        return compiler.toSource()
    }
}
