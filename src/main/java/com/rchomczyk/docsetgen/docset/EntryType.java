package com.rchomczyk.docsetgen.docset;

import org.jetbrains.annotations.Nullable;

import java.util.Locale;

/**
 * @see <a href="https://kapeli.com/docsets#dashDocset">Dash's documentation about the available types</a>
 */
public enum EntryType {

    PACKAGE(-1),
    CLASS(2),
    CONSTANT(-1),
    CONSTRUCTOR(-1),
    INTERFACE(1),
    ENUM(3),
    EXCEPTION(5),
    ANNOTATION(7),
    METHOD(-1);

    final int javadocId;

    EntryType(int javadocId) {
        this.javadocId = javadocId;
    }

    public String getName() {
        return name().substring(0, 1).toUpperCase(Locale.ROOT) + name().substring(1).toLowerCase(Locale.ROOT);
    }

    public int getJavadocId() {
        return javadocId;
    }

    @Nullable
    public static EntryType getEntryTypeWithIndex(int index) {
        for (EntryType entryType : EntryType.values()) {
            if (entryType.getJavadocId() == index) {
                return entryType;
            }
        }
        return null;
    }
}
