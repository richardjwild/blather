package com.github.richardjwild.blather.command;

import com.github.richardjwild.blather.application.Controller;
import com.github.richardjwild.blather.datatransfer.MessageRepository;
import com.github.richardjwild.blather.datatransfer.UserRepository;
import com.github.richardjwild.blather.io.Output;
import com.github.richardjwild.blather.time.Clock;
import com.github.richardjwild.blather.time.TimestampFormatter;

public class CommandFactory {

    private final Controller controller;
    private MessageRepository messageRepository;
    private UserRepository userRepository;
    private Clock clock;
    private Output output;
    private TimestampFormatter timestampFormatter;

    public CommandFactory(Controller controller, MessageRepository messageRepository, UserRepository userRepository, Clock clock, TimestampFormatter timestampFormatter, Output output) {
        this.controller = controller;
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.clock = clock;
        this.timestampFormatter = timestampFormatter;
        this.output = output;
    }

    public Command makeQuitCommand() {
        return new QuitCommand(controller);
    }

    public Command makePostCommand(String recipient, String message) {
        return new PostCommand(recipient, message, messageRepository, userRepository, clock);
    }

    public Command makeReadCommand(String subject) {
        return new ReadCommand(subject, messageRepository, userRepository, timestampFormatter, output);
    }

    public Command makeFollowCommand(String follower, String subject) {
        return new FollowCommand(follower, subject);
    }

    public Command makeWallCommand(String subject) {
        return new WallCommand(subject);
    }
}
