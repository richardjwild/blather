package com.github.richardjwild.blather.command;

import com.github.richardjwild.blather.datatransfer.User;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class CommandFactoryShould {

    private static final User RECIPIENT = new User();
    private static final User SUBJECT = new User();
    private static final String MESSAGE = "a message";

    private final CommandFactory commandFactory = new CommandFactory();

    @Test
    public void make_a_quit_command() {
        Command command = commandFactory.makeQuitCommand();

        assertThat(command).isInstanceOf(QuitCommand.class);
    }

    @Test
    public void make_a_post_command() {
        PostCommand expectedCommand = new PostCommand(RECIPIENT, MESSAGE);

        Command command = commandFactory.makePostCommand(RECIPIENT, MESSAGE);

        assertThat(command).isEqualTo(expectedCommand);
    }

    @Test
    public void make_a_read_command() {
        ReadCommand expectedCommand = new ReadCommand(SUBJECT);

        Command command = commandFactory.makeReadCommand(SUBJECT);

        assertThat(command).isEqualTo(expectedCommand);
    }

}