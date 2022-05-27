package com.rchomczyk.docsetgen.javadoc;

import java.io.File;

public class Javadoc {

    private final File sourceFile;

    public Javadoc(File sourceFile) {
        this.sourceFile = sourceFile;
    }

    public File getIndexFile() {
        return new File(sourceFile, "index-all.html");
    }

    public File getIndexClassFile() {
        return new File(sourceFile, "allclasses-index.html");
    }

    public File getIndexPackageFile() {
        return new File(sourceFile, "allpackages-index.html");
    }

    public File getIndexConstantFile() {
        return new File(sourceFile, "constant-values.html");
    }

    public File getSourceFile() {
        return sourceFile;
    }
}
