package com.rchomczyk.docsetgen.argument;

import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class ArgumentAccessor {

    public ArgumentCouple getArgument(@NotNull Set<ArgumentCouple> arguments, @NotNull String key) throws ArgumentCoupleException {
        return arguments.stream()
                .filter(couple -> couple.key().equalsIgnoreCase(key))
                .findAny()
                .orElseThrow(() -> new ArgumentCoupleException(String.format(
                        "Argument named '%s' was not found in any of the couples.", key)));
    }
}
