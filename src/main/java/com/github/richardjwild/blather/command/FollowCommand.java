package com.github.richardjwild.blather.command;

import com.github.richardjwild.blather.datatransfer.User;
import com.github.richardjwild.blather.datatransfer.UserRepository;

import java.util.Optional;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;

public class FollowCommand implements Command {

    private final String followerUserName;
    private final String toFollowUserName;
    private final UserRepository userRepository;

    FollowCommand(String followerUserName, String toFollowUserName, UserRepository userRepository) {
        this.followerUserName = followerUserName;
        this.toFollowUserName = toFollowUserName;
        this.userRepository = userRepository;
    }

    @Override
    public void execute() {
        getUserToFollow().ifPresent(userToFollow -> {
            User follower = getOrCreateFollower();
            follower.follow(userToFollow);
            save(follower);
        });
    }

    private User getOrCreateFollower() {
        return userRepository.find(followerUserName).orElseGet(this::createFollower);
    }

    private User createFollower() {
        return new User(followerUserName);
    }

    private void save(User follower) {
        userRepository.save(follower);
    }

    private Optional<User> getUserToFollow() {
        return userRepository.find(toFollowUserName);
    }

    @Override
    public boolean equals(Object o) {
        return reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return reflectionHashCode(this);
    }
}
