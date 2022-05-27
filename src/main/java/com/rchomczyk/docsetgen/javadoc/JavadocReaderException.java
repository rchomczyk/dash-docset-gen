package com.rchomczyk.docsetgen.javadoc;

import org.jetbrains.annotations.NotNull;

public class JavadocReaderException extends RuntimeException {

    public JavadocReaderException(@NotNull String message, @NotNull Throwable throwable) {
        super(message, throwable);
    }
}
