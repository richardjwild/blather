package com.github.richardjwild.blather.io;

import com.github.richardjwild.blather.repo.UserRepository;
import com.github.richardjwild.blather.user.User;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.github.richardjwild.blather.io.BlatherVerb.*;
import static java.util.Arrays.stream;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.joining;

public class InputParser {

    private static final Map<String, BlatherVerb> VERBS = new HashMap<>();

    static {
        VERBS.put("follows", FOLLOW);
        VERBS.put("->", POST);
        VERBS.put("Quit", QUIT);
    }

    private final UserRepository userRepository;

    public InputParser(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public BlatherVerb readVerb(String line) {
        String verbText = verbTextFrom(line);
        return ofNullable(VERBS.get(verbText)).orElse(READ);
    }

    private String verbTextFrom(String line) {
        String[] words = words(line);
        return words.length == 1 ? words[0] : words[1];
    }

    private String[] words(String line) {
        return line.split(" ");
    }

    public User readPostRecipient(String line) {
        return userRepository.findByName(words(line)[0]);
    }

    public String readPostMessage(String line) {
        return stream(words(line))
                .skip(2)
                .collect(joining(" "));
    }

    public User readSubject(String line) {
        return userRepository.findByName(line);
    }

    public User readFollowSubject(String line) {
        String user = words(line)[2];
        return userRepository.findByName(user);
    }

    public User readWallSubject(String line) {
        return null;
    }

    public User readUser(String line) {
        String user = words(line)[0];
        return userRepository.findByName(user);
    }
}
