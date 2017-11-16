package com.github.richardjwild.blather.command;

import com.github.richardjwild.blather.datatransfer.User;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;

public class PostCommand implements Command {

    private final User recipient;
    private final String message;

    public PostCommand(User recipient, String message) {
        this.recipient = recipient;
        this.message = message;
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
