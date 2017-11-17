package com.github.richardjwild.blather.parsing;

import com.github.richardjwild.blather.command.Command;
import com.github.richardjwild.blather.command.CommandFactory;
import com.github.richardjwild.blather.io.Input;

public class CommandReader {

    private final Input input;
    private final InputParser inputParser;
    private final CommandFactory commandFactory;

    public CommandReader(Input input, InputParser inputParser, CommandFactory commandFactory) {
        this.input = input;
        this.inputParser = inputParser;
        this.commandFactory = commandFactory;
    }

    public Command readNextCommand() {
        String inputLine = input.readLine();
        return buildCommandFrom(inputLine);
    }

    private Command buildCommandFrom(String inputLine) {
        switch (inputParser.verb(inputLine)) {
            case POST:
                return makePostCommand(inputLine);
            case READ:
                return makeReadCommand(inputLine);
            case FOLLOW:
                return makeFollowCommand(inputLine);
            case WALL:
                return makeWallCommand(inputLine);
            default:
                return commandFactory.makeQuitCommand();
        }
    }

    private Command makePostCommand(String inputLine) {
        String recipient = inputParser.postCommandRecipient(inputLine);
        String message = inputParser.postCommandMessage(inputLine);
        return commandFactory.makePostCommand(recipient, message);
    }

    private Command makeReadCommand(String inputLine) {
        String subject = inputParser.readCommandSubject(inputLine);
        return commandFactory.makeReadCommand(subject);
    }

    private Command makeFollowCommand(String inputLine) {
        String follower = inputParser.followCommandActor(inputLine);
        String subject = inputParser.followCommandSubject(inputLine);
        return commandFactory.makeFollowCommand(follower, subject);
    }

    private Command makeWallCommand(String inputLine) {
        String subject = inputParser.wallCommandSubject(inputLine);
        return commandFactory.makeWallCommand(subject);
    }
}
