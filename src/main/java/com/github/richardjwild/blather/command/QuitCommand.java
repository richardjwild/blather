package com.github.richardjwild.blather.command;

import com.github.richardjwild.blather.AppController;

import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;

public class QuitCommand implements Command {

    private final AppController appController;

    public QuitCommand(AppController appController) {
        this.appController = appController;
    }

    @Override
    public void execute() {
        appController.stop();
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
