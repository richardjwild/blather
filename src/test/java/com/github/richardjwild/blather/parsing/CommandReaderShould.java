package com.github.richardjwild.blather.parsing;

import com.github.richardjwild.blather.command.Command;
import com.github.richardjwild.blather.command.CommandFactory;
import com.github.richardjwild.blather.io.Input;
import com.github.richardjwild.blather.datatransfer.User;
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
    private static final User USER = new User("user");
    private static final User RECIPIENT = new User("recipient");
    private static final User SUBJECT = new User("subject");
    private static final User FOLLOWER = new User("follower");

    @Mock
    private CommandFactory commandFactory;

    @Mock
    private Input input;

    @Mock
    private InputParser inputParser;

    @Mock
    private Command command;

    private CommandReader commandReader;

    @Before
    public void initialize() {
        commandReader = new CommandReader(input, inputParser, commandFactory);
        when(input.readLine()).thenReturn(INPUT_LINE);
    }

    @Test
    public void read_a_quit_command() {
        when(inputParser.verb(INPUT_LINE)).thenReturn(BlatherVerb.QUIT);
        when(commandFactory.makeQuitCommand()).thenReturn(command);

        Command actualCommand = commandReader.readNextCommand();

        assertThat(actualCommand).isSameAs(command);
    }

    @Test
    public void read_a_post_command() {
        when(inputParser.verb(INPUT_LINE)).thenReturn(BlatherVerb.POST);
        when(inputParser.postCommandRecipient(INPUT_LINE)).thenReturn(RECIPIENT);
        when(inputParser.postCommandMessage(INPUT_LINE)).thenReturn(MESSAGE);
        when(commandFactory.makePostCommand(RECIPIENT, MESSAGE)).thenReturn(command);

        Command actualCommand = commandReader.readNextCommand();

        assertThat(actualCommand).isSameAs(command);
    }

    @Test
    public void read_a_read_command() {
        when(inputParser.verb(INPUT_LINE)).thenReturn(BlatherVerb.READ);
        when(inputParser.readCommandSubject(INPUT_LINE)).thenReturn(SUBJECT);
        when(commandFactory.makeReadCommand(SUBJECT)).thenReturn(command);

        Command actualCommand = commandReader.readNextCommand();

        assertThat(actualCommand).isSameAs(command);
    }

    @Test
    public void read_a_follow_command() {
        when(inputParser.verb(INPUT_LINE)).thenReturn(BlatherVerb.FOLLOW);
        when(inputParser.followCommandActor(INPUT_LINE)).thenReturn(FOLLOWER);
        when(inputParser.followCommandSubject(INPUT_LINE)).thenReturn(SUBJECT);
        when(commandFactory.makeFollowCommand(FOLLOWER, SUBJECT)).thenReturn(command);

        Command actualCommand = commandReader.readNextCommand();

        assertThat(actualCommand).isSameAs(command);
    }

    @Test
    public void read_a_wall_command() {
        when(inputParser.verb(INPUT_LINE)).thenReturn(BlatherVerb.WALL);
        when(inputParser.wallCommandSubject(INPUT_LINE)).thenReturn(SUBJECT);
        when(commandFactory.makeWallCommand(SUBJECT)).thenReturn(command);

        Command actualCommand = commandReader.readNextCommand();

        assertThat(actualCommand).isSameAs(command);
    }

}