package com.github.richardjwild.blather;

import com.github.richardjwild.blather.command.Command;
import com.github.richardjwild.blather.io.Output;
import com.github.richardjwild.blather.parsing.CommandReader;

import static com.github.richardjwild.blather.ApplicationState.RUNNING;

public class Blather {

    public static final String WELCOME_MESSAGE = "Welcome to Blather";

    public static void main(String[] args) {
        Blather blather = ApplicationBuilder.build();
        blather.runApplication();
    }

    private final AppController appController;
    private final CommandReader commandReader;
    private Output output;

    public Blather(AppController appController, CommandReader commandReader, Output output) {
        this.appController = appController;
        this.commandReader = commandReader;
        this.output = output;
    }

    void runApplication() {
        printWelcomeMessage();
        eventLoop();
        printGoodbyeMessage();
    }

    private void printWelcomeMessage() {
        output.writeLine(WELCOME_MESSAGE);
    }

    private void eventLoop() {
        do {
            Command command = commandReader.readNextCommand();
            command.execute();
        } while (appIsRunning());
    }

    private void printGoodbyeMessage() {
        output.writeLine("Bye!");
    }

    private boolean appIsRunning() {
        return appController.applicationState() == RUNNING;
    }
}
