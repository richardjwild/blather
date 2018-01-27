package com.github.richardjwild.blather.parsing;

import com.github.richardjwild.blather.command.Command;
import com.github.richardjwild.blather.command.factory.CommandFactories;
import com.github.richardjwild.blather.command.factory.CommandFactory;
import com.github.richardjwild.blather.io.Input;

public class CommandReader {

    private final Input input;
    private final InputParser inputParser;
    private final CommandFactories commandFactories;

    public CommandReader(Input input, InputParser inputParser, CommandFactories commandFactories) {
        this.input = input;
        this.inputParser = inputParser;
        this.commandFactories = commandFactories;
    }

    public Command readNextCommand() {
        String inputLine = input.readLine();
        ParsedInput parsedInput = inputParser.parse(inputLine);
        CommandFactory commandFactory = commandFactories.factoryFor(parsedInput.verb());
        return commandFactory.makeCommandFor(parsedInput);
    }
}
