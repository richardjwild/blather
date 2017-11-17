package com.github.richardjwild.blather.parsing;

import org.junit.Before;
import org.junit.Test;

import static com.github.richardjwild.blather.parsing.BlatherVerb.*;
import static org.fest.assertions.Assertions.assertThat;

public class InputParserShould {

    private InputParser inputParser;

    @Before
    public void initialize() {
        inputParser = new InputParser();
    }

    @Test
    public void reads_a_read_command_verb() {
        BlatherVerb verb = inputParser.verb("user");

        assertThat(verb).isEqualTo(BlatherVerb.READ);
    }

    @Test
    public void read_a_read_command_subject() {
        String actualSubject = inputParser.readCommandSubject("user");

        assertThat(actualSubject).isEqualTo("user");
    }

    @Test
    public void read_a_follow_command_verb() {
        BlatherVerb verb = inputParser.verb("user follows subject");

        assertThat(verb).isEqualTo(FOLLOW);
    }

    @Test
    public void read_a_follow_command_actor() {
        String actualUser = inputParser.followCommandActor("user follows subject");

        assertThat(actualUser).isEqualTo("user");
    }

    @Test
    public void read_a_follow_command_subject() {
        String actualSubject = inputParser.followCommandSubject("user follows subject");

        assertThat(actualSubject).isEqualTo("subject");
    }

    @Test
    public void read_a_post_command_verb() {
        BlatherVerb verb = inputParser.verb("recipient -> message");

        assertThat(verb).isEqualTo(POST);
    }

    @Test
    public void read_a_post_command_recipient() {
        String actualRecipient = inputParser.postCommandRecipient("recipient -> a message");

        assertThat(actualRecipient).isEqualTo("recipient");
    }

    @Test
    public void read_a_post_command_message() {
        String actualMessage = inputParser.postCommandMessage("recipient -> a message");

        assertThat(actualMessage).isEqualTo("a message");
    }

    @Test
    public void read_a_wall_command_verb() {
        BlatherVerb verb = inputParser.verb("subject wall");

        assertThat(verb).isEqualTo(WALL);
    }

    @Test
    public void read_a_wall_command_subject() {
        String actualSubject = inputParser.wallCommandSubject("subject wall");

        assertThat(actualSubject).isEqualTo("subject");
    }

    @Test
    public void read_a_quit_command() {
        BlatherVerb verb = inputParser.verb("quit");

        assertThat(verb).isEqualTo(QUIT);
    }
}