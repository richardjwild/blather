package com.github.richardjwild.blather.command;

import com.github.richardjwild.blather.datatransfer.Message;
import com.github.richardjwild.blather.datatransfer.MessageRepository;
import com.github.richardjwild.blather.datatransfer.User;
import com.github.richardjwild.blather.datatransfer.UserRepository;
import com.github.richardjwild.blather.io.Output;
import com.github.richardjwild.blather.time.TimestampFormatter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.Instant;

import static java.time.Instant.now;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.mockito.Matchers.endsWith;
import static org.mockito.Matchers.startsWith;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ReadCommandShould {

    private static final User USER = new User("user");
    private static final String FORMATTED_TIMESTAMP = "message timestamp";

    @Mock
    private Output output;

    @Mock
    private MessageRepository messageRepository;

    @Mock
    private TimestampFormatter timestampFormatter;

    @Mock
    private UserRepository userRepository;

    @Test
    public void print_all_messages_posted_to_a_specified_user() {
        Message message1 = new Message(USER, "message one", now());
        Message message2 = new Message(USER, "message two", now());
        when(userRepository.find("user")).thenReturn(of(USER));
        when(messageRepository.allMessagesPostedTo(USER)).thenReturn(asList(message1, message2));
        ReadCommand command = new ReadCommand("user", messageRepository, userRepository, timestampFormatter, output);

        command.execute();

        InOrder inOrder = inOrder(output);
        inOrder.verify(output).writeLine(startsWith("message one"));
        inOrder.verify(output).writeLine(startsWith("message two"));
    }

    @Test
    public void affix_messages_with_a_timestamp() {
        Instant timestamp = now();
        Message message = new Message(USER, "message", timestamp);
        when(userRepository.find("user")).thenReturn(of(USER));
        when(messageRepository.allMessagesPostedTo(USER)).thenReturn(singletonList(message));
        when(timestampFormatter.format(timestamp)).thenReturn(FORMATTED_TIMESTAMP);
        ReadCommand command = new ReadCommand("user", messageRepository, userRepository, timestampFormatter, output);

        command.execute();

        verify(output).writeLine(endsWith(" " + FORMATTED_TIMESTAMP));
    }

    @Test
    public void print_nothing_if_user_not_found() {
        when(userRepository.find("user")).thenReturn(empty());
        ReadCommand command = new ReadCommand("user", messageRepository, userRepository, timestampFormatter, output);

        command.execute();

        verify(output, never()).writeLine(anyString());
    }

}