package com.github.richardjwild.blather.command;

import com.github.richardjwild.blather.user.User;

public class CommandFactory {
    public Command makeQuitCommand() {
        return null;
    }

    public Command makePostCommand(User recipient, String message) {
        return null;
    }

    public Command makeReadCommand(User subject) {
        return null;
    }

    public Command makeFollowCommand(User subject) {
        return null;
    }

    public Command makeWallCommand(User subject) {
        return null;
    }
}
