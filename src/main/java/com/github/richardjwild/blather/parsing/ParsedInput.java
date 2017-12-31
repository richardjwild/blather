package com.github.richardjwild.blather.parsing;

public class ParsedInput {

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

    public Verb verb() {
        return Verb.fromText(verb);
    }

    public String postCommandRecipient() {
        return postRecipient;
    }

    public String postCommandMessage() {
        return postMessage;
    }

    public String readCommandSubject() {
        return readSubject;
    }

    public String followCommandActor() {
        return followActor;
    }

    public String followCommandSubject() {
        return followSubject;
    }

    public String wallCommandSubject() {
        return wallSubject;
    }
}
