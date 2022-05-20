package com.github.richardjwild.blather.command;

import com.github.richardjwild.blather.user.User;
import com.github.richardjwild.blather.user.UserRepository;

import java.util.Optional;

public class FollowCommand implements Command {

    private final String followerUserName;
    private final String toFollowUserName;
    private final UserRepository userRepository;

    public FollowCommand(
            String followerUserName,
            String toFollowUserName,
            UserRepository userRepository)
    {
        this.followerUserName = followerUserName;
        this.toFollowUserName = toFollowUserName;
        this.userRepository = userRepository;
    }

    @Override
    public void execute() {
        findUserToFollow().ifPresent(this::addFollower);
    }

    private Optional<User> findUserToFollow() {
        return userRepository.find(toFollowUserName);
    }

    private void addFollower(User toFollow) {
        User follower = findOrCreateFollower();
        if (!follower.equals(toFollow)) {
            follower.follow(toFollow);
            userRepository.save(follower);
        }
    }

    private User findOrCreateFollower() {
        return findFollower().orElseGet(this::createFollower);
    }

    private Optional<User> findFollower() {
        return userRepository.find(followerUserName);
    }

    private User createFollower() {
        return new User(followerUserName);
    }
}
