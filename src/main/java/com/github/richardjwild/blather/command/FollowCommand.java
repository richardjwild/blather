package com.github.richardjwild.blather.command;

import com.github.richardjwild.blather.datatransfer.User;
import com.github.richardjwild.blather.datatransfer.UserRepository;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;

public class FollowCommand implements Command {

    private final String follower;
    private final String following;
    private final UserRepository userRepository;

    FollowCommand(String follower, String following, UserRepository userRepository) {
        this.follower = follower;
        this.following = following;
        this.userRepository = userRepository;
    }

    @Override
    public void execute() {
        userRepository.find(this.follower).ifPresent(this::followSubject);
    }

    private void followSubject(User follower) {
        userRepository.find(this.following).ifPresent(following -> addRelationship(follower, following));
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
