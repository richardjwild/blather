package com.github.richardjwild.blather.command;

import com.github.richardjwild.blather.message.Message;
import com.github.richardjwild.blather.message.MessageRepository;
import com.github.richardjwild.blather.datatransfer.User;
import com.github.richardjwild.blather.datatransfer.UserRepository;
import com.github.richardjwild.blather.io.Output;
import com.github.richardjwild.blather.time.TimestampFormatter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ReadCommandShould {

    @Mock
    private Output output;

    @Mock
    private MessageRepository messageRepository;

    @Mock
    private Message earlierMessage, laterMessage;

    @Mock
    private TimestampFormatter timestampFormatter;

    @Mock
    private UserRepository userRepository;

    @Before
    public void initialize() {
        when(earlierMessage.compareTo(laterMessage)).thenReturn(-1);
        when(laterMessage.compareTo(earlierMessage)).thenReturn(1);
    }

    @Test
    public void print_all_messages_posted_to_a_specified_user_in_date_order() {
        User user = new User("user");
        when(userRepository.find("user")).thenReturn(Optional.of(user));
        when(messageRepository.allMessagesPostedTo(user)).thenReturn(Stream.of(laterMessage, earlierMessage));
        when(earlierMessage.formatRead(timestampFormatter)).thenReturn("earlier message formatted");
        when(laterMessage.formatRead(timestampFormatter)).thenReturn("later message formatted");

        ReadCommand command = readCommandForUserName("user");
        command.execute();

        InOrder inOrder = inOrder(output);
        inOrder.verify(output).writeLine("earlier message formatted");
        inOrder.verify(output).writeLine("later message formatted");
    }

    @Test
    public void print_nothing_if_user_not_found() {
        when(userRepository.find("user")).thenReturn(Optional.empty());
        ReadCommand command = readCommandForUserName("user");

        command.execute();

        verify(output, never()).writeLine(anyString());
    }

    private ReadCommand readCommandForUserName(String userName) {
        return new ReadCommand(
                userName,
                messageRepository,
                userRepository,
                timestampFormatter,
                output);
    }
}
