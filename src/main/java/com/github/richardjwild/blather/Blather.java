package com.github.richardjwild.blather;

import com.github.richardjwild.blather.command.Command;
import com.github.richardjwild.blather.io.CommandReader;

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
        System.out.println("Welcome to Blather");
    }
}
