/* $Name:  $ */
/* $Id: ClosureCompileAntTask.java,v 1.3 2010/10/27 19:24:56 ajokela Exp $ */
package org.portfolio.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.FileList;

import com.google.common.collect.Lists;
import com.google.common.io.LimitInputStream;
import com.google.javascript.jscomp.CompilationLevel;
import com.google.javascript.jscomp.CompilerOptions;
import com.google.javascript.jscomp.CompilerRunner;
import com.google.javascript.jscomp.JSSourceFile;
import com.google.javascript.jscomp.Result;
import com.google.javascript.jscomp.WarningLevel;

public class ClosureCompileAntTask extends Task {
    private WarningLevel warningLevel;
    private boolean debugOptions;
    private CompilationLevel compilationLevel;
    private boolean customExternsOnly;
    private File outputFile;
    private final List<FileList> externFileSets;
    private final List<FileList> sourceFileSets;

    public ClosureCompileAntTask() {
        this.warningLevel = WarningLevel.DEFAULT;
        this.debugOptions = false;
        this.compilationLevel = CompilationLevel.SIMPLE_OPTIMIZATIONS;
        this.customExternsOnly = false;
        this.externFileSets = Lists.newLinkedList();
        this.sourceFileSets = Lists.newLinkedList();
    }

    /**
     * Set the warning level.
     * 
     * @param value The warning level by string name. (default, quiet, verbose).
     */
    public void setWarning(String value) {
        if ("default".equalsIgnoreCase(value)) {
            this.warningLevel = WarningLevel.DEFAULT;
        } else if ("quiet".equalsIgnoreCase(value)) {
            this.warningLevel = WarningLevel.QUIET;
        } else if ("verbose".equalsIgnoreCase(value)) {
            this.warningLevel = WarningLevel.VERBOSE;
        } else {
            throw new BuildException("Unrecognized 'warning' option value (" + value + ")");
        }
    }

    /**
     * Enable debugging options.
     * 
     * @param value True if debug mode is enabled.
     */
    public void setDebug(boolean value) {
        this.debugOptions = value;
    }

    /**
     * Set the compilation level.
     * 
     * @param value The optimization level by string name. (whitespace, simple, advanced).
     */
    public void setCompilationLevel(String value) {
        if ("simple".equalsIgnoreCase(value)) {
            this.compilationLevel = CompilationLevel.SIMPLE_OPTIMIZATIONS;
        } else if ("advanced".equalsIgnoreCase(value)) {
            this.compilationLevel = CompilationLevel.ADVANCED_OPTIMIZATIONS;
        } else if ("whitespace".equalsIgnoreCase(value)) {
            this.compilationLevel = CompilationLevel.WHITESPACE_ONLY;
        } else {
            throw new BuildException("Unrecognized 'compilation' option value (" + value + ")");
        }
    }

    /**
     * Use only custom externs.
     */
    public void setCustomExternsOnly(boolean value) {
        this.customExternsOnly = value;
    }

    /**
     * Set output file.
     */
    public void setOutput(File value) {
        this.outputFile = value;
    }

    /**
     * Sets the externs file.
     */
    public void addExterns(FileList set) {
        this.externFileSets.add(set);
    }

    /**
     * Sets the source files.
     */
    public void addSources(FileList set) {
        this.sourceFileSets.add(set);
    }

    public void execute() {
        if (this.outputFile == null) {
            throw new BuildException("outputFile attribute must be set");
        }

        com.google.javascript.jscomp.Compiler.setLoggingLevel(Level.OFF);

        CompilerOptions options = createCompilerOptions();
        com.google.javascript.jscomp.Compiler compiler = new com.google.javascript.jscomp.Compiler(System.out);

        JSSourceFile[] externs = findExternFiles();
        JSSourceFile[] sources = findSourceFiles();

        log("Compiling " + sources.length + " file(s) with " + externs.length + " extern(s)");

        Result result = compiler.compile(externs, sources, options);
        if (result.success) {
            writeResult(compiler.toSource());
        }
    }

    private CompilerOptions createCompilerOptions() {
        CompilerOptions options = new CompilerOptions();

        if (this.debugOptions) {
            this.compilationLevel.setDebugOptionsForCompilationLevel(options);
        } else {
            this.compilationLevel.setOptionsForCompilationLevel(options);
        }

        this.warningLevel.setOptionsForWarningLevel(options);
        return options;
    }

    private JSSourceFile[] findExternFiles() {
        List<JSSourceFile> files = Lists.newLinkedList();
        if (!this.customExternsOnly) {
            files.addAll(getDefaultExterns());
        }

        for (FileList set : this.externFileSets) {
            files.addAll(findJavaScriptFiles(set));
        }

        return files.toArray(new JSSourceFile[files.size()]);
    }

    private JSSourceFile[] findSourceFiles() {
        List<JSSourceFile> files = Lists.newLinkedList();

        for (FileList set : this.sourceFileSets) {
            files.addAll(findJavaScriptFiles(set));
        }

        return files.toArray(new JSSourceFile[files.size()]);
    }

    /**
     * Translates an Ant file list into the file format that the compiler expects.
     */
    private List<JSSourceFile> findJavaScriptFiles(FileList fileSet) {
        List<JSSourceFile> files = Lists.newLinkedList();
        File baseDir = fileSet.getDir(getProject());

        for (String included : fileSet.getFiles(getProject())) {
            files.add(JSSourceFile.fromFile(new File(baseDir, included)));
        }

        return files;
    }

    /**
     * Gets the default externs set.
     * 
     * Adapted from {@link CompilerRunner}.
     */
    private List<JSSourceFile> getDefaultExterns() {
        try {
            InputStream input = CompilerRunner.class.getResourceAsStream("/externs.zip");
            ZipInputStream zip = new ZipInputStream(input);
            List<JSSourceFile> externs = Lists.newLinkedList();

            for (ZipEntry entry; (entry = zip.getNextEntry()) != null;) {
                LimitInputStream entryStream = new LimitInputStream(zip, entry.getSize());
                externs.add(JSSourceFile.fromInputStream(entry.getName(), entryStream));
            }

            return externs;
        } catch (IOException e) {
            throw new BuildException(e);
        }
    }

    private void writeResult(String source) {
        if (this.outputFile.getParentFile().mkdirs()) {
            log("Created missing parent directory " + this.outputFile.getParentFile(), Project.MSG_DEBUG);
        }

        try {
            FileWriter out = new FileWriter(this.outputFile);
            out.append(source);
            out.close();
        } catch (IOException e) {
            throw new BuildException(e);
        }

        log("Compiled javascript written to " + this.outputFile.getAbsolutePath(), Project.MSG_DEBUG);
    }
}
