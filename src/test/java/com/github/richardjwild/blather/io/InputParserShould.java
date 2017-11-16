package com.github.richardjwild.blather.io;

import com.github.richardjwild.blather.repo.UserRepository;
import com.github.richardjwild.blather.user.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static com.github.richardjwild.blather.io.BlatherVerb.FOLLOW;
import static java.lang.String.format;
import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class InputParserShould {

    private static final User USER = new User(), SUBJECT = new User(), DUMMY_USER = new User();

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
        String subject = "nameofperson";
        String readCommand = format("%s", subject);

        BlatherVerb verb = inputParser.readVerb(readCommand);

        assertThat(verb).isEqualTo(BlatherVerb.READ);
    }

    @Test
    public void read_a_read_command_subject() {
        String subject = "nameofperson";
        String readCommand = format("%s", subject);
        when(userRepository.findByName(subject)).thenReturn(SUBJECT);

        User actualSubject = inputParser.readSubject(readCommand);

        assertThat(actualSubject).isSameAs(SUBJECT);
    }

    @Test
    public void read_a_follow_command_verb() {
        String user = "user";
        String subject = "subject";
        String followCommand = format("%s follows %s", user, subject);
        when(userRepository.findByName(user)).thenReturn(USER);
        when(userRepository.findByName(subject)).thenReturn(SUBJECT);

        BlatherVerb verb = inputParser.readVerb(followCommand);

        assertThat(verb).isEqualTo(FOLLOW);
    }

}