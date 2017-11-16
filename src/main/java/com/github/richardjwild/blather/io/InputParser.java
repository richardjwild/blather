package com.github.richardjwild.blather.io;

import com.github.richardjwild.blather.repo.UserRepository;
import com.github.richardjwild.blather.user.User;

public class InputParser {

    private final UserRepository userRepository;

    public InputParser(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public BlatherVerb readVerb(String line) {
        String[] words = line.split(" ");
        if (words.length == 1)
            return BlatherVerb.READ;
        return BlatherVerb.FOLLOW;
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
        return null;
    }

    public User readWallSubject(String line) {
        return null;
    }

    public User readUser(String line) {
        return null;
    }
}
