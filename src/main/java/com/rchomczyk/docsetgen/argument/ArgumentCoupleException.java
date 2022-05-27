package com.rchomczyk.docsetgen.argument;

import org.jetbrains.annotations.NotNull;

public class ArgumentCoupleException extends RuntimeException {

    public ArgumentCoupleException(@NotNull String message) {
        super(message);
    }
}
