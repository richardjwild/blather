package com.github.richardjwild.blather.io;

import com.github.richardjwild.blather.command.Command;
import com.github.richardjwild.blather.command.CommandFactory;
import com.github.richardjwild.blather.command.PostCommand;
import com.github.richardjwild.blather.command.QuitCommand;
import com.github.richardjwild.blather.user.User;
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
    private static final User RECIPIENT = new User();
    private static final String MESSAGE = "message";

    @Mock
    private CommandFactory commandFactory;

    @Mock
    private Input input;

    @Mock
    private InputParser inputParser;

    private CommandReader commandReader;

    @Before
    public void initialize() {
        commandReader = new CommandReader(input, inputParser, commandFactory);
        when(input.readLine()).thenReturn(INPUT_LINE);
    }

    @Test
    public void read_a_quit_command() {
        when(inputParser.readVerb(INPUT_LINE)).thenReturn(BlatherVerb.QUIT);
        Command quitCommand = new QuitCommand();
        when(commandFactory.makeQuitCommand()).thenReturn(quitCommand);

        Command actualCommand = commandReader.readNextCommand();

        assertThat(actualCommand).isSameAs(quitCommand);
    }

    @Test
    public void read_a_post_command() {
        when(inputParser.readVerb(INPUT_LINE)).thenReturn(BlatherVerb.POST);
        when(inputParser.readRecipient(INPUT_LINE)).thenReturn(RECIPIENT);
        when(inputParser.readMessage(INPUT_LINE)).thenReturn(MESSAGE);
        PostCommand postCommand = new PostCommand();
        when(commandFactory.makePostCommand(RECIPIENT, MESSAGE)).thenReturn(postCommand);

        Command actualCommand = commandReader.readNextCommand();

        assertThat(actualCommand).isSameAs(postCommand);
    }

}