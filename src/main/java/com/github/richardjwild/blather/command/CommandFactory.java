package com.github.richardjwild.blather.command;

import com.github.richardjwild.blather.application.Controller;
import com.github.richardjwild.blather.datatransfer.MessageRepository;
import com.github.richardjwild.blather.datatransfer.User;
import com.github.richardjwild.blather.io.Output;
import com.github.richardjwild.blather.time.Clock;
import com.github.richardjwild.blather.time.TimestampFormatter;

public class CommandFactory {

    private final Controller controller;
    private MessageRepository messageRepository;
    private Clock clock;
    private Output output;
    private TimestampFormatter timestampFormatter;

    public CommandFactory(Controller controller, MessageRepository messageRepository, Clock clock, TimestampFormatter timestampFormatter, Output output) {
        this.controller = controller;
        this.messageRepository = messageRepository;
        this.clock = clock;
        this.timestampFormatter = timestampFormatter;
        this.output = output;
    }

    public Command makeQuitCommand() {
        return new QuitCommand(controller);
    }

    public Command makePostCommand(User recipient, String message) {
        return new PostCommand(recipient, message, messageRepository, clock);
    }

    public Command makeReadCommand(User subject) {
        return new ReadCommand(subject, messageRepository, timestampFormatter, output);
    }

    public Command makeFollowCommand(User follower, User subject) {
        return new FollowCommand(follower, subject);
    }

    public Command makeWallCommand(User subject) {
        return new WallCommand(subject);
    }
}
