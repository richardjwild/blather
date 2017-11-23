package com.github.richardjwild.blather;

import com.github.richardjwild.blather.application.EventLoop;
import com.github.richardjwild.blather.io.Output;

public class Blather {

    private static final String WELCOME_MESSAGE = "Welcome to Blather";
    private static final String GOODBYE_MESSAGE = "Bye!";

    public static void main(String[] args) {
        Blather blather = ApplicationBuilder.build();
        blather.runApplication();
    }

    private final EventLoop eventLoop;
    private Output output;

    Blather(EventLoop eventLoop, Output output) {
        this.eventLoop = eventLoop;
        this.output = output;
    }

    void runApplication() {
        output.writeLine(WELCOME_MESSAGE);
        eventLoop.start();
        output.writeLine(GOODBYE_MESSAGE);
    }
}
