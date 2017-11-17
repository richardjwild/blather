package com.github.richardjwild.blather.application;

import com.github.richardjwild.blather.command.Command;
import com.github.richardjwild.blather.parsing.CommandReader;

import static com.github.richardjwild.blather.application.State.RUNNING;

public class EventLoop {

    private final CommandReader commandReader;
    private final Controller controller;

    public EventLoop(CommandReader commandReader, Controller controller) {
        this.commandReader = commandReader;
        this.controller = controller;
    }

    public void start() {
        do {
            Command command = commandReader.readNextCommand();
            command.execute();
        } while (appIsRunning());
    }

    private boolean appIsRunning() {
        return controller.applicationState() == RUNNING;
    }
}
