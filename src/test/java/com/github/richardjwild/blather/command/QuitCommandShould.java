package com.github.richardjwild.blather.command;

import com.github.richardjwild.blather.application.Controller;
import com.github.richardjwild.blather.command.factory.QuitCommandFactory;
import com.github.richardjwild.blather.parsing.ParsedInput;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class QuitCommandShould {

    @Mock
    private Controller controller;

    @Mock
    private ParsedInput parsedInput;

    @InjectMocks
    private QuitCommandFactory factory;

    @Test
    public void stop_the_application() {
        Command command = factory.makeCommandFor(parsedInput);

        command.execute();

        verify(controller).stop();
    }
}