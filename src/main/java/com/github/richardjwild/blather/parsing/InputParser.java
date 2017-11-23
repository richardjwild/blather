package com.github.richardjwild.blather.parsing;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;

public class InputParser {

    ParsedInput parse(String line) {
        String[] words = line.split(" ");
        return new ParsedInput(
                determineVerb(words),
                determinePostRecipient(words),
                determinePostMessage(words),
                determineReadSubject(words),
                determineFollowActor(words),
                determineFollowSubject(words),
                determineWallSubject(words));
    }

    private String determineVerb(String[] words) {
        return words.length == 1 ? words[0] : words[1];
    }

    private String determinePostRecipient(String[] words) {
        return words[0];
    }

    private String determinePostMessage(String[] words) {
        return stream(words).skip(2).collect(joining(" "));
    }

    private String determineReadSubject(String[] words) {
        return words[0];
    }

    private String determineFollowActor(String[] words) {
        return words[0];
    }

    private String determineFollowSubject(String[] words) {
        return (words.length >= 3) ? words[2] : null;
    }

    private String determineWallSubject(String[] words) {
        return words[0];
    }
}
