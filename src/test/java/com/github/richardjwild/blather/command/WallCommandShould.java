package com.github.richardjwild.blather.command;

import com.github.richardjwild.blather.datatransfer.Message;
import com.github.richardjwild.blather.datatransfer.MessageRepository;
import com.github.richardjwild.blather.datatransfer.User;
import com.github.richardjwild.blather.datatransfer.UserRepository;
import com.github.richardjwild.blather.io.Output;
import com.github.richardjwild.blather.messageformatting.TimestampFormatter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.Instant;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class WallCommandShould {

    private static final Instant AN_INSTANT_IN_TIME = Instant.now();

    @Mock
    private Output output;

    @Mock
    private UserRepository userRepository;

    @Mock
    private MessageRepository messageRepository;

    @Mock
    private TimestampFormatter timestampFormatter;

    @Mock
    private Message firstPost, aliceFirstPost, jillHelloWorld,
            aliceTwoSecondsAgo, aliceRightNow, jillOneSecondAgo;

    @Test
    public void print_all_messages_posted_to_one_followed_user() {
        User bob = new User("Bob");
        User alice = new User("Alice");
        bob.follow(alice);
        when(userRepository.find("Bob")).thenReturn(Optional.of(bob));
        when(messageRepository.allMessagesPostedTo(alice)).thenReturn(stream(firstPost));
        when(firstPost.timestamp()).thenReturn(AN_INSTANT_IN_TIME);
        when(firstPost.formatWall(timestampFormatter)).thenReturn("alice first post message");

        WallCommand wallCommand = new WallCommand("Bob", userRepository, messageRepository,
                timestampFormatter, output);
        wallCommand.execute();

        verify(output).writeLine("alice first post message");
    }

    @Test
    public void print_all_messages_posted_to_multiple_followed_users() {
        User bob = new User("Bob");
        User alice = new User("Alice");
        User jill = new User("Jill");
        bob.follow(alice);
        bob.follow(jill);
        when(userRepository.find("Bob")).thenReturn(Optional.of(bob));
        when(messageRepository.allMessagesPostedTo(alice)).thenReturn(stream(aliceFirstPost));
        when(messageRepository.allMessagesPostedTo(jill)).thenReturn(stream(jillHelloWorld));
        when(aliceFirstPost.timestamp()).thenReturn(AN_INSTANT_IN_TIME);
        when(aliceFirstPost.formatWall(timestampFormatter)).thenReturn("alice first post message");
        when(jillHelloWorld.timestamp()).thenReturn(AN_INSTANT_IN_TIME.plusSeconds(1));
        when(jillHelloWorld.formatWall(timestampFormatter)).thenReturn("jill hello world message");

        WallCommand wallCommand = new WallCommand("Bob", userRepository, messageRepository,
                timestampFormatter, output);
        wallCommand.execute();

        InOrder inOrder = inOrder(output);
        inOrder.verify(output).writeLine("alice first post message");
        inOrder.verify(output).writeLine("jill hello world message");
    }

    @Test
    public void print_messages_in_date_order() {
        User bob = new User("Bob");
        User alice = new User("Alice");
        User jill = new User("Jill");

        bob.follow(alice);
        bob.follow(jill);

        when(userRepository.find("Bob")).thenReturn(Optional.of(bob));
        when(messageRepository.allMessagesPostedTo(alice)).thenReturn(stream(aliceTwoSecondsAgo, aliceRightNow));
        when(messageRepository.allMessagesPostedTo(jill)).thenReturn(stream(jillOneSecondAgo));

        when(aliceTwoSecondsAgo.timestamp()).thenReturn(AN_INSTANT_IN_TIME.minusSeconds(2));
        when(aliceTwoSecondsAgo.formatWall(timestampFormatter)).thenReturn("alice two seconds ago");
        when(jillOneSecondAgo.timestamp()).thenReturn(AN_INSTANT_IN_TIME.minusSeconds(1));
        when(jillOneSecondAgo.formatWall(timestampFormatter)).thenReturn("jill one second ago");
        when(aliceRightNow.timestamp()).thenReturn(AN_INSTANT_IN_TIME);
        when(aliceRightNow.formatWall(timestampFormatter)).thenReturn("alice right now");

        WallCommand wallCommand = new WallCommand("Bob", userRepository, messageRepository,
                timestampFormatter, output);
        wallCommand.execute();

        InOrder inOrder = inOrder(output);
        inOrder.verify(output).writeLine("alice two seconds ago");
        inOrder.verify(output).writeLine("jill one second ago");
        inOrder.verify(output).writeLine("alice right now");
    }

    private Stream<Message> stream(Message... messages) {
        return Arrays.stream(messages);
    }

}