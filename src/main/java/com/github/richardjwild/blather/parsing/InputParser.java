package com.github.richardjwild.blather.parsing;

import com.github.richardjwild.blather.datatransfer.UserRepository;
import com.github.richardjwild.blather.datatransfer.User;

public class InputParser {

    private final UserRepository userRepository;

    public InputParser(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    BlatherVerb verb(String line) {
        String verbText = deconstruct(line).verb;
        return BlatherVerb.fromText(verbText);
    }

    User postCommandRecipient(String line) {
        String postRecipient = deconstruct(line).postRecipient;
        return userRepository.findByName(postRecipient);
    }

    String postCommandMessage(String line) {
        return deconstruct(line).postMessage;
    }

    User readCommandSubject(String line) {
        String subject = deconstruct(line).readSubject;
        return userRepository.findByName(subject);
    }

    User followCommandActor(String inputLine) {
        String actor = deconstruct(inputLine).followActor;
        return userRepository.findByName(actor);
    }

    User followCommandSubject(String line) {
        String subject = deconstruct(line).followSubject;
        return userRepository.findByName(subject);
    }

    User wallCommandSubject(String line) {
        String subject = deconstruct(line).wallSubject;
        return userRepository.findByName(subject);
    }

    private InputCommand deconstruct(String line) {
        return new InputCommand(line);
    }
}
