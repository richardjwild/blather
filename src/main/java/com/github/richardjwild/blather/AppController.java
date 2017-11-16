package com.github.richardjwild.blather;

import static com.github.richardjwild.blather.ApplicationState.RUNNING;
import static com.github.richardjwild.blather.ApplicationState.STOPPED;

public class AppController {

    private ApplicationState applicationState = RUNNING;

    public ApplicationState applicationState() {
        return applicationState;
    }

    public void stop() {
        applicationState = STOPPED;
    }
}
