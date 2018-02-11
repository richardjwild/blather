package com.github.richardjwild.blather.command.factory;

import com.github.richardjwild.blather.command.Command;
import com.github.richardjwild.blather.command.PostCommand;
import com.github.richardjwild.blather.message.MessageRepository;
import com.github.richardjwild.blather.user.UserRepository;
import com.github.richardjwild.blather.parsing.ParsedInput;
import com.github.richardjwild.blather.time.Clock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static com.github.richardjwild.blather.parsing.Verb.POST;
import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PostCommandFactoryShould {

    private static final String RECIPIENT = "a recipient";
    private static final String MESSAGE = "a message";

    @Mock
    private ParsedInput parsedInput;

    @Mock
    private MessageRepository messageRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private Clock clock;

    @Test
    public void make_a_post_command() {
        CommandFactory postCommandFactory = new PostCommandFactory(messageRepository, userRepository, clock);
        when(parsedInput.verb()).thenReturn(POST);
        when(parsedInput.postCommandRecipient()).thenReturn(RECIPIENT);
        when(parsedInput.postCommandMessage()).thenReturn(MESSAGE);

        Command command = postCommandFactory.makeCommandFor(parsedInput);

        PostCommand expectedCommand = new PostCommand(
                RECIPIENT, MESSAGE, messageRepository, userRepository, clock);
        assertThat(command).isEqualTo(expectedCommand);
    }
}
