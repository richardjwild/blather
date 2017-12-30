package com.github.richardjwild.blather.command;

import com.github.richardjwild.blather.datatransfer.Message;
import com.github.richardjwild.blather.datatransfer.MessageRepository;
import com.github.richardjwild.blather.datatransfer.User;
import com.github.richardjwild.blather.datatransfer.UserRepository;
import com.github.richardjwild.blather.io.Output;
import com.github.richardjwild.blather.messageformatting.ReadMessageFormatter;

import static java.util.Comparator.comparing;
import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;

public class ReadCommand implements Command {

    private final String recipientUserName;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final ReadMessageFormatter messageFormatter;
    private final Output output;

    ReadCommand(String recipientUserName, MessageRepository messageRepository, UserRepository userRepository,
                ReadMessageFormatter messageFormatter, Output output) {
        this.recipientUserName = recipientUserName;
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.messageFormatter = messageFormatter;
        this.output = output;
    }

    @Override
    public void execute() {
        userRepository.find(recipientUserName).ifPresent(this::printAllMessagesPostedToRecipient);
    }

    private void printAllMessagesPostedToRecipient(User recipient) {
        messageRepository.allMessagesPostedTo(recipient)
                .sorted(comparing(Message::timestamp))
                .map(messageFormatter::format)
                .forEach(output::writeLine);
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
