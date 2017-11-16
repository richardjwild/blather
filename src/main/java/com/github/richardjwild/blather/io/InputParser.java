package com.github.richardjwild.blather.io;

import com.github.richardjwild.blather.repo.UserRepository;
import com.github.richardjwild.blather.user.User;

import java.util.HashMap;
import java.util.Map;

import static com.github.richardjwild.blather.io.BlatherVerb.FOLLOW;
import static com.github.richardjwild.blather.io.BlatherVerb.POST;

public class InputParser {

    private static final Map<String, BlatherVerb> VERBS = new HashMap<>();

    static {
        VERBS.put("follows", FOLLOW);
        VERBS.put("->", POST);
    }

    private final UserRepository userRepository;

    public InputParser(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public BlatherVerb readVerb(String line) {
        String[] words = line.split(" ");
        if (words.length == 1)
            return BlatherVerb.READ;
        return VERBS.get(words[1]);
    }

    public User readRecipient(String line) {
        return null;
    }

    public String readMessage(String line) {
        return null;
    }

    public User readSubject(String line) {
        return userRepository.findByName(line);
    }

    public User readFollowSubject(String line) {
        String user = line.split(" ")[2];
        return userRepository.findByName(user);
    }

    public User readWallSubject(String line) {
        return null;
    }

    public User readUser(String line) {
        String user = line.split(" ")[0];
        return userRepository.findByName(user);
    }
}
