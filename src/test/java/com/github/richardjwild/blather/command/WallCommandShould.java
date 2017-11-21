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

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static java.util.Optional.of;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class WallCommandShould {

    @Mock
    private Output output;

    @Mock
    private UserRepository userRepository;

    @Mock
    private MessageRepository messageRepository;

    @Mock
    private TimestampFormatter timestampFormatter;

    @Test
    public void print_all_messages_posted_to_one_followed_user() {
        Instant now = Instant.now();
        User bob = new User("Bob");
        User alice = new User("Alice");
        bob.follow(alice);
        when(userRepository.find("Bob")).thenReturn(of(bob));
        when(messageRepository.allMessagesPostedTo(alice)).thenReturn(singletonList(new Message(alice, "First post!", now)));
        when(timestampFormatter.format(now)).thenReturn("(TIMESTAMP)");

        WallCommand wallCommand = new WallCommand("Bob", userRepository, messageRepository, timestampFormatter, output);
        wallCommand.execute();

        verify(output).writeLine("Alice - First post! (TIMESTAMP)");
    }

    @Test
    public void print_all_messages_posted_to_multiple_followed_users() {
        Instant now = Instant.now();
        User bob = new User("Bob");
        User alice = new User("Alice");
        User jill = new User("Jill");
        bob.follow(alice);
        bob.follow(jill);
        when(userRepository.find("Bob")).thenReturn(of(bob));
        when(messageRepository.allMessagesPostedTo(alice)).thenReturn(singletonList(new Message(alice, "First post!", now)));
        when(messageRepository.allMessagesPostedTo(jill)).thenReturn(singletonList(new Message(jill, "Hello world!", now)));
        when(timestampFormatter.format(now)).thenReturn("(TIMESTAMP)");

        WallCommand wallCommand = new WallCommand("Bob", userRepository, messageRepository, timestampFormatter, output);
        wallCommand.execute();

        InOrder inOrder = inOrder(output);
        inOrder.verify(output).writeLine("Alice - First post! (TIMESTAMP)");
        inOrder.verify(output).writeLine("Jill - Hello world! (TIMESTAMP)");
    }

    @Test
    public void print_messages_in_date_order() {
        Instant now = Instant.now();
        Instant oneSecondAgo = now.minusSeconds(1);
        Instant twoSecondsAgo = now.minusSeconds(2);

        User bob = new User("Bob");
        User alice = new User("Alice");
        User jill = new User("Jill");

        bob.follow(alice);
        bob.follow(jill);

        when(userRepository.find("Bob")).thenReturn(of(bob));
        when(messageRepository.allMessagesPostedTo(alice)).thenReturn(asList(
                new Message(alice, "First post!", twoSecondsAgo),
                new Message(alice, "This is my second post", now)));
        when(messageRepository.allMessagesPostedTo(jill)).thenReturn(singletonList(
                new Message(jill, "Hello world!", oneSecondAgo)));

        when(timestampFormatter.format(twoSecondsAgo)).thenReturn("(TWO_SECONDS_AGO)");
        when(timestampFormatter.format(oneSecondAgo)).thenReturn("(ONE_SECOND_AGO)");
        when(timestampFormatter.format(now)).thenReturn("(NOW)");

        WallCommand wallCommand = new WallCommand("Bob", userRepository, messageRepository, timestampFormatter, output);
        wallCommand.execute();

        InOrder inOrder = inOrder(output);
        inOrder.verify(output).writeLine("Alice - First post! (TWO_SECONDS_AGO)");
        inOrder.verify(output).writeLine("Jill - Hello world! (ONE_SECOND_AGO)");
        inOrder.verify(output).writeLine("Alice - This is my second post (NOW)");
    }

}