package com.github.richardjwild.blather.command;

import com.github.richardjwild.blather.message.Message;
import com.github.richardjwild.blather.message.MessageRepository;
import com.github.richardjwild.blather.user.User;
import com.github.richardjwild.blather.user.UserRepository;
import com.github.richardjwild.blather.io.Output;
import com.github.richardjwild.blather.time.TimestampFormatter;

import java.util.Optional;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;

public class ReadCommand implements Command {

    private final String recipientUserName;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final TimestampFormatter timestampFormatter;
    private final Output output;

    public ReadCommand(
            String recipientUserName,
            MessageRepository messageRepository,
            UserRepository userRepository,
            TimestampFormatter timestampFormatter,
            Output output)
    {
        this.recipientUserName = recipientUserName;
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.timestampFormatter = timestampFormatter;
        this.output = output;
    }

    @Override
    public void execute() {
        findRecipient().ifPresent(this::printAllMessagesPostedToRecipient);
    }

    private Optional<User> findRecipient() {
        return userRepository.find(recipientUserName);
    }

    private void printAllMessagesPostedToRecipient(User recipient) {
        messageRepository.allMessagesPostedTo(recipient)
                .sorted()
                .map(this::format)
                .forEach(output::writeLine);
    }

    private String format(Message message) {
        return message.formatRead(timestampFormatter);
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
