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
        return userRepository.find(postRecipient).orElse(new User(postRecipient));
    }

    String postCommandMessage(String line) {
        return deconstruct(line).postMessage;
    }

    User readCommandSubject(String line) {
        String subject = deconstruct(line).readSubject;
        return userRepository.find(subject).orElse(null);
    }

    User followCommandActor(String inputLine) {
        String actor = deconstruct(inputLine).followActor;
        return userRepository.find(actor).orElse(null);
    }

    User followCommandSubject(String line) {
        String subject = deconstruct(line).followSubject;
        return userRepository.find(subject).orElse(null);
    }

    User wallCommandSubject(String line) {
        String subject = deconstruct(line).wallSubject;
        return userRepository.find(subject).orElse(null);
    }

    private InputCommand deconstruct(String line) {
        return new InputCommand(line);
    }
}
