package com.github.richardjwild.blather.command;

import com.github.richardjwild.blather.datatransfer.Message;
import com.github.richardjwild.blather.datatransfer.MessageRepository;
import com.github.richardjwild.blather.datatransfer.User;
import com.github.richardjwild.blather.datatransfer.UserRepository;
import com.github.richardjwild.blather.io.Output;
import com.github.richardjwild.blather.messageformatting.TimestampFormatter;

import java.util.Optional;

import static java.util.Comparator.comparing;
import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;

public class ReadCommand implements Command {

    private String recipientUserName;
    private MessageRepository messageRepository;
    private UserRepository userRepository;
    private TimestampFormatter timestampFormatter;
    private Output output;

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
                .sorted(comparing(Message::timestamp))
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
