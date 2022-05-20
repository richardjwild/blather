package com.github.richardjwild.blather.command;

import com.github.richardjwild.blather.application.Controller;

public class QuitCommand implements Command {

    private final Controller controller;

    public QuitCommand(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void execute() {
        controller.stop();
    }
}
