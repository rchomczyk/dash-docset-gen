package com.rchomczyk.docsetgen;

import com.rchomczyk.docsetgen.argument.ArgumentAccessor;
import com.rchomczyk.docsetgen.argument.ArgumentParser;

public final class DocsetGeneratorInitializer {

    private DocsetGeneratorInitializer() {

    }

    public static void main(String[] arguments) {
        ArgumentParser argumentParser = new ArgumentParser();
        DocsetGenerator docsetGenerator = new DocsetGenerator();
        docsetGenerator.initialize(new ArgumentAccessor(), argumentParser.parse(arguments));
    }
}
