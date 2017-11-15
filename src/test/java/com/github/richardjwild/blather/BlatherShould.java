package com.github.richardjwild.blather;

import com.github.richardjwild.blather.command.Command;
import com.github.richardjwild.blather.io.CommandReader;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BlatherShould {

    @Mock
    private CommandReader commandReader;

    @Mock
    private Command command;

    private Blather blather;

    @Before
    public void initialize() {
        blather = new Blather(commandReader);
    }

    @Test
    public void execute_the_next_command() {
        when(commandReader.readNextCommand()).thenReturn(command);

        blather.eventLoop();

        verify(command).execute();
    }

}
