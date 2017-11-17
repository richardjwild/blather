package com.github.richardjwild.blather.application;

import static com.github.richardjwild.blather.application.State.RUNNING;
import static com.github.richardjwild.blather.application.State.STOPPED;

public class Controller {

    private State state = RUNNING;

    public State applicationState() {
        return state;
    }

    public void stop() {
        state = STOPPED;
    }
}
