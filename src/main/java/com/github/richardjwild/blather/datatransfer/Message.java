package com.github.richardjwild.blather.datatransfer;

import java.time.Instant;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;

public class Message {

    private final User recipient;
    private final String text;
    private final Instant timestamp;

    public Message(User recipient, String text, Instant timestamp) {
        this.recipient = recipient;
        this.text = text;
        this.timestamp = timestamp;
    }

    User recipient() {
        return recipient;
    }

    public String recipientName() {
        return recipient.name();
    }

    public String text() {
        return text;
    }

    public Instant timestamp() {
        return timestamp;
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
