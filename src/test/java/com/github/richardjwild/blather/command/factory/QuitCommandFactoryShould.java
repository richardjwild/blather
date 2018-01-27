package com.github.richardjwild.blather.command.factory;

import com.github.richardjwild.blather.application.Controller;
import com.github.richardjwild.blather.command.Command;
import com.github.richardjwild.blather.command.QuitCommand;
import com.github.richardjwild.blather.parsing.ParsedInput;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static com.github.richardjwild.blather.parsing.Verb.QUIT;
import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class QuitCommandFactoryShould {

    @Mock
    private ParsedInput parsedInput;

    @Mock
    private Controller controller;

    @Test
    public void make_a_quit_command() {
        CommandFactory quitCommandFactory = new QuitCommandFactory(controller);
        when(parsedInput.verb()).thenReturn(QUIT);

        Command command = quitCommandFactory.makeCommandFor(parsedInput);

        QuitCommand expectedCommand = new QuitCommand(controller);
        assertThat(command).isEqualTo(expectedCommand);
    }
}
