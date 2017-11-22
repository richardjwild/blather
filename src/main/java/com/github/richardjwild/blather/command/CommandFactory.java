package com.github.richardjwild.blather.command;

import com.github.richardjwild.blather.application.Controller;
import com.github.richardjwild.blather.datatransfer.MessageRepository;
import com.github.richardjwild.blather.datatransfer.UserRepository;
import com.github.richardjwild.blather.io.Output;
import com.github.richardjwild.blather.messageformatting.MessageFormatter;
import com.github.richardjwild.blather.time.Clock;
import com.github.richardjwild.blather.messageformatting.TimestampFormatter;

public class CommandFactory {

    private final Controller controller;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final Clock clock;
    private final Output output;
    private final MessageFormatter readMessageFormatter;
    private final MessageFormatter wallMessageFormatter;

    public CommandFactory(Controller controller, MessageRepository messageRepository,
                          UserRepository userRepository, Clock clock, MessageFormatter readMessageFormatter,
                          MessageFormatter wallMessageFormatter, Output output) {
        this.controller = controller;
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.clock = clock;
        this.readMessageFormatter = readMessageFormatter;
        this.wallMessageFormatter = wallMessageFormatter;
        this.output = output;
    }

    public Command makeQuitCommand() {
        return new QuitCommand(controller);
    }

    public Command makePostCommand(String recipient, String message) {
        return new PostCommand(recipient, message, messageRepository, userRepository, clock);
    }

    public Command makeReadCommand(String subject) {
        return new ReadCommand(subject, messageRepository, userRepository, readMessageFormatter, output);
    }

    public Command makeFollowCommand(String follower, String subject) {
        return new FollowCommand(follower, subject, userRepository);
    }

    public Command makeWallCommand(String subject) {
        return new WallCommand(subject, userRepository, messageRepository, wallMessageFormatter, output);
    }
}
