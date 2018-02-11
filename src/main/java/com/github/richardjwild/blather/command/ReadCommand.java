package com.github.richardjwild.blather.command;

import com.github.richardjwild.blather.message.Message;
import com.github.richardjwild.blather.message.MessageRepository;
import com.github.richardjwild.blather.user.UserRepository;
import com.github.richardjwild.blather.io.Output;
import com.github.richardjwild.blather.time.TimestampFormatter;

import java.util.stream.Stream;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;

public class ReadCommand implements Command {

    private final Output output;
    private final Stream<String> formattedMessages;

    public ReadCommand(
            String recipientUserName,
            MessageRepository messageRepository,
            UserRepository userRepository,
            TimestampFormatter timestampFormatter,
            Output output)
    {
        this.output = output;

        Stream<Message> messages = userRepository.find(recipientUserName)
                .map(messageRepository::allMessagesPostedTo)
                .orElseGet(Stream::empty);

        formattedMessages = messages
                .sorted()
                .map(message -> message.formatRead(timestampFormatter));
    }

    @Override
    public void execute() {
        formattedMessages.forEach(output::writeLine);
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
