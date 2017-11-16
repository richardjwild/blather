package com.github.richardjwild.blather.command;

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class CommandFactoryShould {

    @Test
    public void make_a_quit_command() {
        CommandFactory commandFactory = new CommandFactory();

        Command command = commandFactory.makeQuitCommand();

        assertThat(command).isInstanceOf(QuitCommand.class);
    }

}