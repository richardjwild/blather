package com.github.richardjwild.blather.parsing;

import static java.util.Arrays.stream;

public enum Verb {

    POST("->"),
    READ(""),
    FOLLOW("follows"),
    WALL("wall"),
    QUIT("quit");

    private final String verbText;

    Verb(String verbText) {
        this.verbText = verbText;
    }

    public static Verb fromText(String text) {
        return stream(values())
                .filter(verb -> verb.verbText.equals(text))
                .findFirst()
                .orElse(READ);
    }
}
