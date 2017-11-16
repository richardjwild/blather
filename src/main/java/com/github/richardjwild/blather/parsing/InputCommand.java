package com.github.richardjwild.blather.parsing;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;

class InputCommand {

    final String verb;
    final String postRecipient;
    final String postMessage;
    final String readSubject;
    final String followActor;
    final String followSubject;
    final String wallSubject;

    InputCommand(String inputLine) {
        String[] words = inputLine.split(" ");
        verb = determineVerb(words);
        postRecipient = determinePostRecipient(words);
        postMessage = determinePostMessage(words);
        readSubject = determineReadSubject(words);
        followActor = determineFollowActor(words);
        followSubject = determineFollowSubject(words);
        wallSubject = determineWallSubject(words);
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
