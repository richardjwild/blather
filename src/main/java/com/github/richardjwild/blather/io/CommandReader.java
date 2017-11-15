package com.github.richardjwild.blather.io;

import com.github.richardjwild.blather.command.Command;
import com.github.richardjwild.blather.command.CommandFactory;

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
        BlatherVerb verb = inputParser.readVerb(inputLine);
        return commandFactory.makeQuitCommand();
    }
}
