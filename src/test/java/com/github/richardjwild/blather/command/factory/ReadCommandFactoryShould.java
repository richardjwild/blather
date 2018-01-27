package com.github.richardjwild.blather.command.factory;

import com.github.richardjwild.blather.command.Command;
import com.github.richardjwild.blather.command.ReadCommand;
import com.github.richardjwild.blather.datatransfer.MessageRepository;
import com.github.richardjwild.blather.datatransfer.UserRepository;
import com.github.richardjwild.blather.io.Output;
import com.github.richardjwild.blather.messageformatting.ReadMessageFormatter;
import com.github.richardjwild.blather.parsing.ParsedInput;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static com.github.richardjwild.blather.parsing.Verb.READ;
import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReadCommandFactoryShould {

    private static final String SUBJECT = "a subject";

    @Mock
    private ParsedInput parsedInput;

    @Mock
    private MessageRepository messageRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ReadMessageFormatter readMessageFormatter;

    @Mock
    private Output output;

    @Test
    public void make_a_read_command() {
        CommandFactory readCommandFactory = new ReadCommandFactory(
                messageRepository, userRepository, readMessageFormatter, output);
        when(parsedInput.verb()).thenReturn(READ);
        when(parsedInput.readCommandSubject()).thenReturn(SUBJECT);

        Command command = readCommandFactory.makeCommandFor(parsedInput);

        ReadCommand expectedCommand = new ReadCommand(SUBJECT, messageRepository, userRepository,
                readMessageFormatter, output);
        assertThat(command).isEqualTo(expectedCommand);
    }
}
