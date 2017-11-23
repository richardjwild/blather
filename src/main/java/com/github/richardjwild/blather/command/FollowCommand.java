package com.github.richardjwild.blather.command;

import com.github.richardjwild.blather.datatransfer.User;
import com.github.richardjwild.blather.datatransfer.UserRepository;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;

public class FollowCommand implements Command {

    private final String followerUserName;
    private final String followingUserName;
    private final UserRepository userRepository;

    FollowCommand(String followerUserName, String followingUserName, UserRepository userRepository) {
        this.followerUserName = followerUserName;
        this.followingUserName = followingUserName;
        this.userRepository = userRepository;
    }

    @Override
    public void execute() {
        User follower = getOrCreateFollower();
        followSubject(follower);
    }

    private User getOrCreateFollower() {
        return userRepository.find(followerUserName).orElseGet(this::createFollower);
    }

    private User createFollower() {
        return new User(followerUserName);
    }

    private void followSubject(User follower) {
        userRepository.find(followingUserName).ifPresent(following -> addRelationship(follower, following));
    }

    private void addRelationship(User follower, User following) {
        follower.follow(following);
        userRepository.save(follower);
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
