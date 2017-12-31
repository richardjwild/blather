package com.github.richardjwild.blather.parsing;

import org.junit.Before;
import org.junit.Test;

import static com.github.richardjwild.blather.parsing.Verb.*;
import static org.fest.assertions.Assertions.assertThat;

public class InputParserShould {

    private InputParser inputParser;

    @Before
    public void initialize() {
        inputParser = new InputParser();
    }

    @Test
    public void read_a_read_command_verb() {
        ParsedInput parsedInput = inputParser.parse("user");

        assertThat(parsedInput.verb()).isEqualTo(READ);
    }

    @Test
    public void read_a_read_command_subject() {
        ParsedInput parsedInput = inputParser.parse("user");

        assertThat(parsedInput.readCommandSubject()).isEqualTo("user");
    }

    @Test
    public void read_a_follow_command_verb() {
        ParsedInput parsedInput = inputParser.parse("user follows subject");

        assertThat(parsedInput.verb()).isEqualTo(FOLLOW);
    }

    @Test
    public void read_a_follow_command_actor() {
        ParsedInput parsedInput = inputParser.parse("user follows subject");

        assertThat(parsedInput.followCommandActor()).isEqualTo("user");
    }

    @Test
    public void read_a_follow_command_subject() {
        ParsedInput parsedInput = inputParser.parse("user follows subject");

        assertThat(parsedInput.followCommandSubject()).isEqualTo("subject");
    }

    @Test
    public void read_a_post_command_verb() {
        ParsedInput parsedInput = inputParser.parse("recipient -> message");

        assertThat(parsedInput.verb()).isEqualTo(POST);
    }

    @Test
    public void read_a_post_command_recipient() {
        ParsedInput parsedInput = inputParser.parse("recipient -> a message");

        assertThat(parsedInput.postCommandRecipient()).isEqualTo("recipient");
    }

    @Test
    public void read_a_post_command_message() {
        ParsedInput parsedInput = inputParser.parse("recipient -> a message");

        assertThat(parsedInput.postCommandMessage()).isEqualTo("a message");
    }

    @Test
    public void read_a_wall_command_verb() {
        ParsedInput parsedInput = inputParser.parse("subject wall");

        assertThat(parsedInput.verb()).isEqualTo(WALL);
    }

    @Test
    public void read_a_wall_command_subject() {
        ParsedInput parsedInput = inputParser.parse("subject wall");

        assertThat(parsedInput.wallCommandSubject()).isEqualTo("subject");
    }

    @Test
    public void read_a_quit_command() {
        ParsedInput parsedInput = inputParser.parse("quit");

        assertThat(parsedInput.verb()).isEqualTo(QUIT);
    }
}