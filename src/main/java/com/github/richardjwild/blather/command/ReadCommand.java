package com.github.richardjwild.blather.command;

import com.github.richardjwild.blather.datatransfer.Message;
import com.github.richardjwild.blather.datatransfer.MessageRepository;
import com.github.richardjwild.blather.datatransfer.User;
import com.github.richardjwild.blather.io.Output;

import java.util.List;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;

public class ReadCommand implements Command {

    private final User subject;
    private final MessageRepository messageRepository;
    private final Output output;

    public ReadCommand(User subject, MessageRepository messageRepository, Output output) {
        this.subject = subject;
        this.messageRepository = messageRepository;
        this.output = output;
    }

    @Override
    public void execute() {
        List<Message> messages = messageRepository.allMessagesPostedTo(subject);
        messages.stream().forEach(message -> output.writeLine(message.text));
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
