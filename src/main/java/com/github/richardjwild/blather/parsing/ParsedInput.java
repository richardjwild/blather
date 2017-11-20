package com.github.richardjwild.blather.parsing;

class ParsedInput {

    final String verb;
    final String postRecipient;
    final String postMessage;
    final String readSubject;
    final String followActor;
    final String followSubject;
    final String wallSubject;

    public ParsedInput(String verb, String postRecipient, String postMessage, String readSubject,
                       String followActor, String followSubject, String wallSubject) {
        this.verb = verb;
        this.postRecipient = postRecipient;
        this.postMessage = postMessage;
        this.readSubject = readSubject;
        this.followActor = followActor;
        this.followSubject = followSubject;
        this.wallSubject = wallSubject;
    }

    public BlatherVerb verb() {
        return BlatherVerb.fromText(verb);
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
