package com.github.richardjwild.blather.command;

import com.github.richardjwild.blather.io.Output;
import com.github.richardjwild.blather.message.MessageRepository;
import com.github.richardjwild.blather.time.TimestampFormatter;
import com.github.richardjwild.blather.user.User;
import com.github.richardjwild.blather.user.UserRepository;

import java.util.stream.Stream;

public class WallCommand implements Command {

    private final Output output;
    private final Stream<String> formattedMessages;

    public WallCommand(
            String followerUserName,
            UserRepository userRepository,
            MessageRepository messageRepository,
            TimestampFormatter timestampFormatter,
            Output output)
    {
        this.output = output;

        Stream<User> wallUsers = userRepository.find(followerUserName)
                .map(User::wallUsers)
                .orElseGet(Stream::empty);

        formattedMessages = wallUsers
                .flatMap(messageRepository::allMessagesPostedTo)
                .sorted()
                .map(message -> message.formatWall(timestampFormatter));
    }

    @Override
    public void execute() {
        formattedMessages.forEach(output::writeLine);
    }
}
