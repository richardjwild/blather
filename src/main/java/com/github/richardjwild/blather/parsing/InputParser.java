package com.github.richardjwild.blather.parsing;

public class InputParser {

    public InputParser() {
    }

    BlatherVerb verb(String line) {
        String verbText = deconstruct(line).verb;
        return BlatherVerb.fromText(verbText);
    }

    String postCommandRecipient(String line) {
        return deconstruct(line).postRecipient;
    }

    String postCommandMessage(String line) {
        return deconstruct(line).postMessage;
    }

    String readCommandSubject(String line) {
        return deconstruct(line).readSubject;
    }

    String followCommandActor(String inputLine) {
        return deconstruct(inputLine).followActor;
    }

    String followCommandSubject(String line) {
        return deconstruct(line).followSubject;
    }

    String wallCommandSubject(String line) {
        return deconstruct(line).wallSubject;
    }

    private InputCommand deconstruct(String line) {
        return new InputCommand(line);
    }
}
