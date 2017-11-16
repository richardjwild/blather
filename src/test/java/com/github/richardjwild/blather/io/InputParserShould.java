package com.github.richardjwild.blather.io;

import com.github.richardjwild.blather.repo.UserRepository;
import com.github.richardjwild.blather.user.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class InputParserShould {

    private static final User USER = new User();

    @Mock
    private UserRepository userRepository;
    private InputParser inputParser;

    @Before
    public void initialize() {
        inputParser = new InputParser(userRepository);
    }

    @Test
    public void read_a_read_command_subject() {
        String expectedSubject = "nameofperson";
        String inputLine = String.format("%s", expectedSubject);
        when(userRepository.findByName(expectedSubject)).thenReturn(USER);

        User actualSubject = inputParser.readSubject(inputLine);

        assertThat(actualSubject).isSameAs(USER);
    }

}