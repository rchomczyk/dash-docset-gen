package com.rchomczyk.docsetgen.argument;

import java.util.HashSet;
import java.util.Set;

public class ArgumentParser {

    public Set<ArgumentCouple> parse(String[] arguments) {
        int length = arguments.length;
        if (length % 2 != 0) {
            throw new IllegalArgumentException("Number of arguments isn't an even value.");
        }
        Set<ArgumentCouple> couples = new HashSet<>(length / 2);
        for (int i = 0; i < length; i += 2) {
            couples.add(new ArgumentCouple(arguments[i], arguments[i + 1]));
        }
        return couples;
    }
}
