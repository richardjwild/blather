package com.github.richardjwild.blather.command;

import com.github.richardjwild.blather.user.User;

public class CommandFactory {
    public Command makeQuitCommand() {
        return new QuitCommand();
    }

    public Command makePostCommand(User user, User recipient, String message) {
        return null;
    }

    public Command makeReadCommand(User user, User subject) {
        return null;
    }

    public Command makeFollowCommand(User user, User subject) {
        return null;
    }

    public Command makeWallCommand(User user, User subject) {
        return null;
    }
}
