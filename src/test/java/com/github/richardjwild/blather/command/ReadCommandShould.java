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
import java.util.Optional;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ReadCommandShould {

    private static final String TEXT = "this test does not depend on this text";
    private static final Instant AN_INSTANT_IN_TIME = Instant.now();
    private static final Instant ANOTHER_INSTANT_IN_TIME = AN_INSTANT_IN_TIME.plusSeconds(1);

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
        User user = new User("user");
        Message message1 = new Message(user, TEXT, AN_INSTANT_IN_TIME),
                message2 = new Message(user, TEXT, ANOTHER_INSTANT_IN_TIME);

        when(userRepository.find("user")).thenReturn(Optional.of(user));
        when(messageRepository.allMessagesPostedTo(user)).thenReturn(Stream.of(message1, message2));
        when(messageFormatter.format(message1)).thenReturn("message 1 formatted");
        when(messageFormatter.format(message2)).thenReturn("message 2 formatted");

        ReadCommand command = readCommandForUserName("user");
        command.execute();

        InOrder inOrder = inOrder(output);
        inOrder.verify(output).writeLine("message 1 formatted");
        inOrder.verify(output).writeLine("message 2 formatted");
    }

    @Test
    public void print_nothing_if_user_not_found() {
        when(userRepository.find("user")).thenReturn(Optional.empty());
        ReadCommand command = readCommandForUserName("user");

        command.execute();

        verify(output, never()).writeLine(anyString());
    }

    private ReadCommand readCommandForUserName(String userName) {
        return new ReadCommand(userName, messageRepository, userRepository, messageFormatter, output);
    }
}
