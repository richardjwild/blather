package com.github.richardjwild.blather.command;

import com.github.richardjwild.blather.datatransfer.User;

public class CommandFactory {
    public Command makeQuitCommand() {
        return new QuitCommand();
    }

    public Command makePostCommand(User recipient, String message) {
        return new PostCommand(recipient, message);
    }

    public Command makeReadCommand(User subject) {
        return new ReadCommand(subject);
    }

    public Command makeFollowCommand(User user, User subject) {
        return null;
    }

    public Command makeWallCommand(User user, User subject) {
        return null;
    }
}
