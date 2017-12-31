package com.github.richardjwild.blather.parsing;

import com.github.richardjwild.blather.command.Command;
import com.github.richardjwild.blather.command.factory.CommandFactory;
import com.github.richardjwild.blather.io.Input;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CommandReaderShould {

    private static final String INPUT_LINE = "input line of text";

    @Mock
    private CommandFactory commandFactory;

    @Mock
    private Input input;

    @Mock
    private InputParser inputParser;

    @Mock
    private Command expectedCommand;

    @Mock
    private ParsedInput parsedInput;

    @Test
    public void read_a_command() {
        CommandReader commandReader = new CommandReader(input, inputParser, commandFactory);
        when(input.readLine()).thenReturn(INPUT_LINE);
        when(inputParser.parse(INPUT_LINE)).thenReturn(parsedInput);
        when(commandFactory.makeCommandFor(parsedInput)).thenReturn(expectedCommand);

        Command actualCommand = commandReader.readNextCommand();

        assertThat(actualCommand).isSameAs(expectedCommand);
    }
}