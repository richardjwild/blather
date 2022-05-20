package com.github.richardjwild.blather.command;

import com.github.richardjwild.blather.io.Output;
import com.github.richardjwild.blather.message.Message;
import com.github.richardjwild.blather.message.MessageRepository;
import com.github.richardjwild.blather.time.TimestampFormatter;
import com.github.richardjwild.blather.user.UserRepository;

import java.util.stream.Stream;

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
}
