package com.github.richardjwild.blather.command;

import com.github.richardjwild.blather.AppController;
import com.github.richardjwild.blather.datatransfer.MessageRepository;
import com.github.richardjwild.blather.datatransfer.User;
import com.github.richardjwild.blather.time.Clock;

public class CommandFactory {

    private final AppController appController;
    private MessageRepository messageRepository;
    private Clock clock;

    public CommandFactory(AppController appController, MessageRepository messageRepository, Clock clock) {
        this.appController = appController;
        this.messageRepository = messageRepository;
        this.clock = clock;
    }

    public Command makeQuitCommand() {
        return new QuitCommand(appController);
    }

    public Command makePostCommand(User recipient, String message) {
        return new PostCommand(recipient, message, messageRepository, clock);
    }

    public Command makeReadCommand(User subject) {
        return new ReadCommand(subject);
    }

    public Command makeFollowCommand(User follower, User subject) {
        return new FollowCommand(follower, subject);
    }

    public Command makeWallCommand(User subject) {
        return new WallCommand(subject);
    }
}
