package com.rchomczyk.docsetgen.javadoc;

import com.rchomczyk.docsetgen.docset.EntryType;
import org.jetbrains.annotations.NotNull;

public record JavadocEntry(@NotNull String name, @NotNull EntryType type, @NotNull String path) {

}