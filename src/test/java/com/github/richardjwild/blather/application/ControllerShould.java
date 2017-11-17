package com.github.richardjwild.blather.application;

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class ControllerShould {

    private final Controller controller = new Controller();

    @Test
    public void initially_return_application_state_running() {
        assertThat(controller.applicationState()).isEqualTo(State.RUNNING);
    }

    @Test
    public void return_application_state_stopped_after_stop_command_issued() {
        controller.stop();

        assertThat(controller.applicationState()).isEqualTo(State.STOPPED);
    }

}