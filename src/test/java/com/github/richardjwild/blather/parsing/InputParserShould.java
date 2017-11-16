package com.github.richardjwild.blather.parsing;

import com.github.richardjwild.blather.datatransfer.UserRepository;
import com.github.richardjwild.blather.datatransfer.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static com.github.richardjwild.blather.parsing.BlatherVerb.*;
import static java.lang.String.format;
import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class InputParserShould {

    private static final User
            USER = new User(),
            RECIPIENT = new User(),
            SUBJECT = new User(),
            DUMMY_USER = new User();

    @Mock
    private UserRepository userRepository;
    private InputParser inputParser;

    @Before
    public void initialize() {
        inputParser = new InputParser(userRepository);
        when(userRepository.findByName(any())).thenReturn(DUMMY_USER);
    }

    @Test
    public void reads_a_read_command_verb() {
        String subject = "subject_to_read";
        String readCommand = format("%s", subject);

        BlatherVerb verb = inputParser.verb(readCommand);

        assertThat(verb).isEqualTo(BlatherVerb.READ);
    }

    @Test
    public void read_a_read_command_subject() {
        String subject = "subject_to_read";
        String readCommand = format("%s", subject);
        when(userRepository.findByName(subject)).thenReturn(SUBJECT);

        User actualSubject = inputParser.readCommandSubject(readCommand);

        assertThat(actualSubject).isSameAs(SUBJECT);
    }

    @Test
    public void read_a_follow_command_verb() {
        String followCommand = "user follows subject";

        BlatherVerb verb = inputParser.verb(followCommand);

        assertThat(verb).isEqualTo(FOLLOW);
    }

    @Test
    public void read_a_follow_command_actor() {
        String user = "a_user";
        String followCommand = format("%s follows subject", user);
        when(userRepository.findByName(user)).thenReturn(USER);

        User actualUser = inputParser.followCommandActor(followCommand);

        assertThat(actualUser).isSameAs(USER);
    }

    @Test
    public void read_a_follow_command_subject() {
        String subject = "a_subject";
        String followCommand = format("user follows %s", subject);
        when(userRepository.findByName(subject)).thenReturn(SUBJECT);

        User actualSubject = inputParser.followCommandSubject(followCommand);

        assertThat(actualSubject).isSameAs(SUBJECT);
    }

    @Test
    public void read_a_post_command_verb() {
        String postCommand = "recipient -> message";

        BlatherVerb verb = inputParser.verb(postCommand);

        assertThat(verb).isEqualTo(POST);
    }

    @Test
    public void read_a_post_command_recipient() {
        String recipient = "a_recipient";
        String postCommand = format("%s -> message goes here", recipient);
        when(userRepository.findByName(recipient)).thenReturn(RECIPIENT);

        User actualRecipient = inputParser.postCommandRecipient(postCommand);

        assertThat(actualRecipient).isSameAs(RECIPIENT);
    }

    @Test
    public void read_a_post_command_message() {
        String message = "this is a message";
        String postCommand = format("recipient -> %s", message);

        String actualMessage = inputParser.postCommandMessage(postCommand);

        assertThat(actualMessage).isEqualTo(message);
    }

    @Test
    public void read_a_wall_command_verb() {
        String wallCommand = "subject wall";

        BlatherVerb verb = inputParser.verb(wallCommand);

        assertThat(verb).isEqualTo(WALL);
    }

    @Test
    public void read_a_wall_command_subject() {
        String subject = "a_subject";
        String wallCommand = format("%s wall", subject);
        when(userRepository.findByName(subject)).thenReturn(SUBJECT);

        User actualSubject = inputParser.wallCommandSubject(wallCommand);

        assertThat(actualSubject).isSameAs(SUBJECT);
    }

    @Test
    public void read_a_quit_command() {
        BlatherVerb verb = inputParser.verb("quit");

        assertThat(verb).isEqualTo(QUIT);
    }
}