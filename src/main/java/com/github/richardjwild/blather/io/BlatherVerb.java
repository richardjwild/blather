package com.github.richardjwild.blather.io;

import static java.util.Arrays.stream;

public enum BlatherVerb {

    POST("->"),
    READ(""),
    FOLLOW("follows"),
    WALL("wall"),
    QUIT("quit");

    private final String verbText;

    BlatherVerb(String verbText) {
        this.verbText = verbText;
    }

    public static BlatherVerb fromText(String text) {
        return stream(values())
                .filter(verb -> verb.verbText.equals(text))
                .findFirst()
                .orElse(READ);
    }
}
