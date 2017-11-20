package com.github.richardjwild.blather.parsing;

import com.github.richardjwild.blather.command.Command;
import com.github.richardjwild.blather.command.CommandFactory;
import com.github.richardjwild.blather.io.Input;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CommandReaderShould {

    private static final String INPUT_LINE = "input line of text";
    private static final String MESSAGE = "message";
    private static final String RECIPIENT = "recipient";
    private static final String SUBJECT = "subject";
    private static final String FOLLOWER = "follower";

    @Mock
    private CommandFactory commandFactory;

    @Mock
    private Input input;

    @Mock
    private InputParser inputParser;

    @Mock
    private Command command;

    @Mock
    private ParsedInput parsedInput;

    private CommandReader commandReader;

    @Before
    public void initialize() {
        commandReader = new CommandReader(input, inputParser, commandFactory);
        when(input.readLine()).thenReturn(INPUT_LINE);
        when(inputParser.parse(INPUT_LINE)).thenReturn(parsedInput);
    }

    @Test
    public void read_a_quit_command() {
        when(parsedInput.verb()).thenReturn(BlatherVerb.QUIT);
        when(commandFactory.makeQuitCommand()).thenReturn(command);

        Command actualCommand = commandReader.readNextCommand();

        assertThat(actualCommand).isSameAs(command);
    }

    @Test
    public void read_a_post_command() {
        when(parsedInput.verb()).thenReturn(BlatherVerb.POST);
        when(parsedInput.postCommandRecipient()).thenReturn(RECIPIENT);
        when(parsedInput.postCommandMessage()).thenReturn(MESSAGE);
        when(commandFactory.makePostCommand(RECIPIENT, MESSAGE)).thenReturn(command);

        Command actualCommand = commandReader.readNextCommand();

        assertThat(actualCommand).isSameAs(command);
    }

    @Test
    public void read_a_read_command() {
        when(parsedInput.verb()).thenReturn(BlatherVerb.READ);
        when(parsedInput.readCommandSubject()).thenReturn(SUBJECT);
        when(commandFactory.makeReadCommand(SUBJECT)).thenReturn(command);

        Command actualCommand = commandReader.readNextCommand();

        assertThat(actualCommand).isSameAs(command);
    }

    @Test
    public void read_a_follow_command() {
        when(parsedInput.verb()).thenReturn(BlatherVerb.FOLLOW);
        when(parsedInput.followCommandActor()).thenReturn(FOLLOWER);
        when(parsedInput.followCommandSubject()).thenReturn(SUBJECT);
        when(commandFactory.makeFollowCommand(FOLLOWER, SUBJECT)).thenReturn(command);

        Command actualCommand = commandReader.readNextCommand();

        assertThat(actualCommand).isSameAs(command);
    }

    @Test
    public void read_a_wall_command() {
        when(parsedInput.verb()).thenReturn(BlatherVerb.WALL);
        when(parsedInput.wallCommandSubject()).thenReturn(SUBJECT);
        when(commandFactory.makeWallCommand(SUBJECT)).thenReturn(command);

        Command actualCommand = commandReader.readNextCommand();

        assertThat(actualCommand).isSameAs(command);
    }

}