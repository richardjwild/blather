package com.github.richardjwild.blather.parsing;

import com.github.richardjwild.blather.command.Command;
import com.github.richardjwild.blather.command.CommandFactory;
import com.github.richardjwild.blather.io.Input;
import com.github.richardjwild.blather.datatransfer.User;

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
        User actor = inputParser.commandActor(inputLine);
        User recipient = inputParser.postCommandRecipient(inputLine);
        String message = inputParser.postCommandMessage(inputLine);
        return commandFactory.makePostCommand(actor, recipient, message);
    }

    private Command makeReadCommand(String inputLine) {
        User actor = inputParser.commandActor(inputLine);
        User subject = inputParser.readCommandSubject(inputLine);
        return commandFactory.makeReadCommand(actor, subject);
    }

    private Command makeFollowCommand(String inputLine) {
        User actor = inputParser.commandActor(inputLine);
        User subject = inputParser.followCommandSubject(inputLine);
        return commandFactory.makeFollowCommand(actor, subject);
    }

    private Command makeWallCommand(String inputLine) {
        User actor = inputParser.commandActor(inputLine);
        User subject = inputParser.wallCommandSubject(inputLine);
        return commandFactory.makeWallCommand(actor, subject);
    }
}
