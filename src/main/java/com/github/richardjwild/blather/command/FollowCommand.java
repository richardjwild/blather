package com.github.richardjwild.blather.command;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;

public class FollowCommand implements Command {

    private final String follower;
    private final String subject;

    public FollowCommand(String follower, String subject) {
        this.follower = follower;
        this.subject = subject;
    }

    @Override
    public void execute() {

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
