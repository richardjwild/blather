package com.github.richardjwild.blather.command.factory;

import com.github.richardjwild.blather.command.Command;
import com.github.richardjwild.blather.command.WallCommand;
import com.github.richardjwild.blather.message.MessageRepository;
import com.github.richardjwild.blather.user.UserRepository;
import com.github.richardjwild.blather.io.Output;
import com.github.richardjwild.blather.time.TimestampFormatter;
import com.github.richardjwild.blather.parsing.ParsedInput;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static com.github.richardjwild.blather.parsing.Verb.WALL;
import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WallCommandFactoryShould {

    private static final String SUBJECT = "a subject";

    @Mock
    private ParsedInput parsedInput;

    @Mock
    private UserRepository userRepository;

    @Mock
    private MessageRepository messageRepository;

    @Mock
    private TimestampFormatter timestampFormatter;

    @Mock
    private Output output;

    @Test
    public void make_a_wall_command() {
        CommandFactory commandFactories = new WallCommandFactory(
                userRepository, messageRepository, timestampFormatter, output);
        when(parsedInput.verb()).thenReturn(WALL);
        when(parsedInput.wallCommandSubject()).thenReturn(SUBJECT);

        Command command = commandFactories.makeCommandFor(parsedInput);

        WallCommand expectedCommand = new WallCommand(SUBJECT, userRepository, messageRepository,
                timestampFormatter, output);
        assertThat(command).isEqualTo(expectedCommand);
    }
}
