package com.github.richardjwild.blather;

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class AppControllerShould {

    private final AppController appController = new AppController();

    @Test
    public void initially_return_application_state_running() {
        assertThat(appController.applicationState()).isEqualTo(ApplicationState.RUNNING);
    }

    @Test
    public void return_application_state_stopped_after_stop_command_issued() {
        appController.stop();

        assertThat(appController.applicationState()).isEqualTo(ApplicationState.STOPPED);
    }

}