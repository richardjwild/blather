package com.github.richardjwild.blather.command;

import com.github.richardjwild.blather.datatransfer.Message;
import com.github.richardjwild.blather.datatransfer.MessageRepository;
import com.github.richardjwild.blather.datatransfer.User;
import com.github.richardjwild.blather.datatransfer.UserRepository;
import com.github.richardjwild.blather.io.Output;
import com.github.richardjwild.blather.messageformatting.ReadMessageFormatter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.Instant;

import static java.util.Arrays.asList;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.mockito.Matchers.startsWith;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ReadCommandShould {

    private static final User USER = new User("user");

    @Mock
    private Output output;

    @Mock
    private MessageRepository messageRepository;

    @Mock
    private ReadMessageFormatter messageFormatter;

    @Mock
    private UserRepository userRepository;

    @Test
    public void print_all_messages_posted_to_a_specified_user_in_date_order() {
        Instant timestamp = Instant.now();
        Message message1 = new Message(USER, null, timestamp);
        Message message2 = new Message(USER, null, timestamp.plusSeconds(1));
        when(userRepository.find("user")).thenReturn(of(USER));
        when(messageRepository.allMessagesPostedTo(USER)).thenReturn(asList(message1, message2));
        when(messageFormatter.format(message1)).thenReturn("message one");
        when(messageFormatter.format(message2)).thenReturn("message two");
        ReadCommand command = new ReadCommand("user", messageRepository, userRepository, messageFormatter, output);

        command.execute();

        InOrder inOrder = inOrder(output);
        inOrder.verify(output).writeLine(startsWith("message one"));
        inOrder.verify(output).writeLine(startsWith("message two"));
    }

    @Test
    public void print_nothing_if_user_not_found() {
        when(userRepository.find("user")).thenReturn(empty());
        ReadCommand command = new ReadCommand("user", messageRepository, userRepository, messageFormatter, output);

        command.execute();

        verify(output, never()).writeLine(anyString());
    }

}