package com.github.richardjwild.blather.command;

import com.github.richardjwild.blather.datatransfer.Message;
import com.github.richardjwild.blather.datatransfer.MessageRepository;
import com.github.richardjwild.blather.datatransfer.User;
import com.github.richardjwild.blather.time.Clock;

import java.time.Instant;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;

public class PostCommand implements Command {

    private final User recipient;
    private final String messageText;
    private final MessageRepository messageRepository;
    private final Clock clock;

    PostCommand(User recipient, String messageText, MessageRepository messageRepository, Clock clock) {
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageRepository = messageRepository;
        this.clock = clock;
    }

    @Override
    public void execute() {
        Instant timestamp = clock.now();
        Message message = buildMessage(timestamp);
        messageRepository.postMessage(message);
    }

    private Message buildMessage(Instant timestamp) {
        return new Message(recipient, messageText, timestamp);
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
