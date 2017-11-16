package com.github.richardjwild.blather.command;

import com.github.richardjwild.blather.AppController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class QuitCommandShould {

    @Mock
    private AppController appController;

    @Test
    public void stop_the_application() {
        QuitCommand command = new QuitCommand(appController);

        command.execute();

        verify(appController).stop();
    }
}