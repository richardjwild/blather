package com.github.richardjwild.blather.datatransfer;

import java.time.Instant;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;

public class Message {

    public final User recipient;
    public final String text;
    public final Instant timestamp;

    public Message(User recipient, String text, Instant timestamp) {
        this.recipient = recipient;
        this.text = text;
        this.timestamp = timestamp;
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
