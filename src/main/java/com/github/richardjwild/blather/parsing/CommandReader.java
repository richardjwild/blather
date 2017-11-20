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
        ParsedInput parsedInput = inputParser.parse(inputLine);
        return buildCommandFrom(parsedInput);
    }

    private Command buildCommandFrom(ParsedInput parsedInput) {
        switch (parsedInput.verb()) {
            case POST:
                return makePostCommand(parsedInput);
            case READ:
                return makeReadCommand(parsedInput);
            case FOLLOW:
                return makeFollowCommand(parsedInput);
            case WALL:
                return makeWallCommand(parsedInput);
            default:
                return commandFactory.makeQuitCommand();
        }
    }

    private Command makePostCommand(ParsedInput parsedInput) {
        String recipient = parsedInput.postCommandRecipient();
        String message = parsedInput.postCommandMessage();
        return commandFactory.makePostCommand(recipient, message);
    }

    private Command makeReadCommand(ParsedInput parsedInput) {
        String subject = parsedInput.readCommandSubject();
        return commandFactory.makeReadCommand(subject);
    }

    private Command makeFollowCommand(ParsedInput parsedInput) {
        String follower = parsedInput.followCommandActor();
        String subject = parsedInput.followCommandSubject();
        return commandFactory.makeFollowCommand(follower, subject);
    }

    private Command makeWallCommand(ParsedInput parsedInput) {
        String subject = parsedInput.wallCommandSubject();
        return commandFactory.makeWallCommand(subject);
    }
}
