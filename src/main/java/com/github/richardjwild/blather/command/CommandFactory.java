package com.github.richardjwild.blather.command;

import com.github.richardjwild.blather.AppController;
import com.github.richardjwild.blather.datatransfer.User;

public class CommandFactory {

    private final AppController appController;

    public CommandFactory(AppController appController) {
        this.appController = appController;
    }

    public Command makeQuitCommand() {
        return new QuitCommand(appController);
    }

    public Command makePostCommand(User recipient, String message) {
        return new PostCommand(recipient, message);
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
