package com.github.richardjwild.blather.command.factory;

import com.github.richardjwild.blather.application.Controller;
import com.github.richardjwild.blather.command.Command;
import com.github.richardjwild.blather.command.QuitCommand;
import com.github.richardjwild.blather.parsing.ParsedInput;

public class QuitCommandFactory implements CommandFactory {

    private final Controller controller;

    public QuitCommandFactory(Controller controller) {
        this.controller = controller;
    }

    @Override
    public Command makeCommandFor(ParsedInput input) {
        return new QuitCommand(controller);
    }
}
