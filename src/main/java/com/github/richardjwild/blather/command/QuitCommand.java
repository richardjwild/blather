package com.github.richardjwild.blather.command;

import com.github.richardjwild.blather.application.Controller;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;

public class QuitCommand implements Command {

    private final Controller controller;

    public QuitCommand(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void execute() {
        controller.stop();
    }

    @Override
    public boolean equals(Object o) {
        return reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return reflectionHashCode(this);
    }
}
