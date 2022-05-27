package com.rchomczyk.docsetgen;

import com.rchomczyk.docsetgen.argument.ArgumentAccessor;
import com.rchomczyk.docsetgen.argument.ArgumentCouple;
import com.rchomczyk.docsetgen.indexer.Indexer;
import com.rchomczyk.docsetgen.javadoc.Javadoc;
import com.rchomczyk.docsetgen.javadoc.JavadocReader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Stream;

public class DocsetGenerator {

    private static final String MANIFEST_TEMPLATE =
            """
            <?xml version="1.0" encoding="UTF-8"?>
            <!DOCTYPE plist PUBLIC "-//Apple//DTD PLIST 1.0//EN" "http://www.apple.com/DTDs/PropertyList-1.0.dtd">
            <plist version="1.0">
            <dict>
                <key>CFBundleIdentifier</key>
                <string>{{identifier}}</string>
                <key>CFBundleName</key>
                <string>{{name}}</string>
                <key>DocSetPlatformFamily</key>
                <string>{{identifier}}</string>
                <key>isDashDocset</key>
                <true/>
                <key>dashIndexFilePath</key>
                <string>index.html</string>
                <key>isJavaScriptEnabled</key>
                <true/>
            </dict>
            </plist>\040\040
            """;

    public void initialize(ArgumentAccessor argumentAccessor, Set<ArgumentCouple> arguments) {
        String docsetName = argumentAccessor.getArgument(arguments, "--name").value();

        File sourceFile = this.createDirectory(new File(argumentAccessor.getArgument(arguments, "--source").value()));
        File targetFile = this.createDirectory(new File(argumentAccessor.getArgument(arguments, "--target").value()));

        File contentsFile = this.createDirectory(new File(targetFile, "Contents"));
        this.writeWithContent(new File(contentsFile, "Info.plist"), MANIFEST_TEMPLATE
                .replace("{{identifier}}", docsetName.toLowerCase(Locale.ROOT))
                .replace("{{name}}", docsetName));

        File resourcesFile = this.createDirectory(new File(contentsFile, "Resources"));
        File documentsFile = new File(resourcesFile, "Documents");
        try (Stream<Path> paths = Files.walk(sourceFile.toPath())) {
            paths.forEach(source -> {
                Path target = Paths.get(documentsFile.getAbsolutePath(), source.toString()
                        .substring(sourceFile.getAbsolutePath().length()));
                try {
                    Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException exception) {
                    throw new IllegalStateException("Couldn't copy the part of html documentation into the docset.", exception);
                }
            });
        } catch (IOException exception) {
            throw new IllegalStateException("Couldn't copy the html documentation into the docset.", exception);
        }

        Indexer indexer = new Indexer(new File(resourcesFile, "docSet.dsidx"));
        indexer.configure();

        JavadocReader javadocReader = new JavadocReader(new Javadoc(sourceFile));
        javadocReader.read()
                .forEach(indexer::populate);
    }

    private File createDirectory(File file) {
        if (!file.exists() && !file.mkdirs()) {
            throw new IllegalStateException(String.format("File '%s' does not exist and cannot be created.", file.getAbsolutePath()));
        }
        return file;
    }

    private void writeWithContent(File file, String fileContent) {
        try {
            if (!file.exists() && !file.createNewFile()) {
                throw new IllegalStateException(String.format("File '%s' does not exist and cannot be created.", file.getAbsolutePath()));
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(fileContent);
                writer.flush();
            }
        } catch (IOException exception) {
            throw new IllegalStateException("There was an unexpected incident, while trying to create the file with the content.");
        }
    }
}
