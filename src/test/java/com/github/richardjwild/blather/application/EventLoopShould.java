package com.github.richardjwild.blather.application;

import com.github.richardjwild.blather.command.Command;
import com.github.richardjwild.blather.parsing.CommandReader;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EventLoopShould {

    @Mock
    private CommandReader commandReader;

    @Mock
    private Command command1, command2, command3;

    @Mock
    private Controller controller;

    private EventLoop eventLoop;

    @Before
    public void initialize() {
        eventLoop = new EventLoop(commandReader, controller);
    }

    @Test
    public void execute_commands_until_it_receives_the_quit_command() {
        when(commandReader.readNextCommand())
                .thenReturn(command1)
                .thenReturn(command2)
                .thenReturn(command3);
        when(controller.applicationState())
                .thenReturn(State.RUNNING)
                .thenReturn(State.RUNNING)
                .thenReturn(State.STOPPED);

        eventLoop.start();

        InOrder inOrder = inOrder(command1, command2, command3);
        inOrder.verify(command1).execute();
        inOrder.verify(command2).execute();
        inOrder.verify(command3).execute();
    }
}