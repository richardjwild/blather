package com.github.richardjwild.blather;

import com.github.richardjwild.blather.command.Command;
import com.github.richardjwild.blather.command.CommandFactory;
import com.github.richardjwild.blather.datatransfer.UserRepository;
import com.github.richardjwild.blather.io.ConsoleInput;
import com.github.richardjwild.blather.io.ConsoleOutput;
import com.github.richardjwild.blather.io.Input;
import com.github.richardjwild.blather.io.Output;
import com.github.richardjwild.blather.parsing.CommandReader;
import com.github.richardjwild.blather.parsing.InputParser;

import static com.github.richardjwild.blather.ApplicationState.RUNNING;

public class Blather {

    private final AppController appController;
    private final CommandReader commandReader;

    public Blather(AppController appController, CommandReader commandReader) {
        this.appController = appController;
        this.commandReader = commandReader;
    }

    void eventLoop() {
        do {
            Command command = commandReader.readNextCommand();
            command.execute();
        } while (appIsRunning());
    }

    private boolean appIsRunning() {
        return appController.applicationState() == RUNNING;
    }

    public static void main(String[] args) {
        UserRepository userRepository = new UserRepository();
        InputParser inputParser = new InputParser(userRepository);
        AppController appController = new AppController();
        CommandFactory commandFactory = new CommandFactory(appController);
        Input input = new ConsoleInput();
        Output output = new ConsoleOutput();
        CommandReader commandReader = new CommandReader(input, inputParser, commandFactory);
        Blather blather = new Blather(appController, commandReader);
        output.writeLine("Welcome to Blather");
        blather.eventLoop();
    }
}
