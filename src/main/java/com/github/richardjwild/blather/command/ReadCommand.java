package com.github.richardjwild.blather.command;

import com.github.richardjwild.blather.datatransfer.Message;
import com.github.richardjwild.blather.datatransfer.MessageRepository;
import com.github.richardjwild.blather.datatransfer.User;
import com.github.richardjwild.blather.datatransfer.UserRepository;
import com.github.richardjwild.blather.io.Output;
import com.github.richardjwild.blather.time.TimestampFormatter;

import java.util.StringJoiner;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;

public class ReadCommand implements Command {

    private final String subject;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final TimestampFormatter timestampFormatter;
    private final Output output;

    ReadCommand(String subject,
                MessageRepository messageRepository,
                UserRepository userRepository,
                TimestampFormatter timestampFormatter,
                Output output) {
        this.subject = subject;
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.timestampFormatter = timestampFormatter;
        this.output = output;
    }

    @Override
    public void execute() {
        userRepository.find(subject).ifPresent(this::printAllMessagesPostedToUser);
    }

    private void printAllMessagesPostedToUser(User user) {
        messageRepository.allMessagesPostedTo(user).stream()
                .map(this::formatWithTimestamp)
                .forEach(output::writeLine);
    }

    private String formatWithTimestamp(Message message) {
        return new StringJoiner(" ")
                .add(message.text)
                .add(timestampFormatter.format(message.timestamp))
                .toString();
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
