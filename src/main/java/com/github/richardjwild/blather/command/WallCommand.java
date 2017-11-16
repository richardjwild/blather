package com.github.richardjwild.blather.command;

import com.github.richardjwild.blather.datatransfer.User;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;

public class WallCommand implements Command {

    private final User subject;

    public WallCommand(User subject) {
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
