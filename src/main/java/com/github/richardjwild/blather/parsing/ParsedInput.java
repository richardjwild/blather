package com.github.richardjwild.blather.parsing;

class ParsedInput {

    private final String verb;
    private final String postRecipient;
    private final String postMessage;
    private final String readSubject;
    private final String followActor;
    private final String followSubject;
    private final String wallSubject;

    ParsedInput(String verb, String postRecipient, String postMessage, String readSubject,
                String followActor, String followSubject, String wallSubject) {
        this.verb = verb;
        this.postRecipient = postRecipient;
        this.postMessage = postMessage;
        this.readSubject = readSubject;
        this.followActor = followActor;
        this.followSubject = followSubject;
        this.wallSubject = wallSubject;
    }

    BlatherVerb verb() {
        return BlatherVerb.fromText(verb);
    }

    String postCommandRecipient() {
        return postRecipient;
    }

    String postCommandMessage() {
        return postMessage;
    }

    String readCommandSubject() {
        return readSubject;
    }

    String followCommandActor() {
        return followActor;
    }

    String followCommandSubject() {
        return followSubject;
    }

    String wallCommandSubject() {
        return wallSubject;
    }
}
