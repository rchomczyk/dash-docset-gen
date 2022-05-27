package com.rchomczyk.docsetgen.indexer;

import org.jetbrains.annotations.NotNull;

public class IndexerException extends RuntimeException {

    public IndexerException(@NotNull String message, @NotNull Throwable cause) {
        super(message, cause);
    }
}
