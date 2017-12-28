package com.github.richardjwild.blather.command;

import com.github.richardjwild.blather.datatransfer.Message;
import com.github.richardjwild.blather.datatransfer.MessageRepository;
import com.github.richardjwild.blather.datatransfer.User;
import com.github.richardjwild.blather.datatransfer.UserRepository;
import com.github.richardjwild.blather.io.Output;
import com.github.richardjwild.blather.messageformatting.WallMessageFormatter;
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

    @Mock
    private Output output;

    @Mock
    private UserRepository userRepository;

    @Mock
    private MessageRepository messageRepository;

    @Mock
    private WallMessageFormatter wallMessageFormatter;

    @Test
    public void print_all_messages_posted_to_one_followed_user() {
        User bob = new User("Bob");
        User alice = new User("Alice");
        bob.follow(alice);
        when(userRepository.find("Bob")).thenReturn(Optional.of(bob));
        Message firstPost = new Message(alice, null, null);
        when(messageRepository.allMessagesPostedTo(alice)).thenReturn(stream(firstPost));
        when(wallMessageFormatter.format(firstPost)).thenReturn("alice first post message");

        WallCommand wallCommand = new WallCommand("Bob", userRepository, messageRepository,
                wallMessageFormatter, output);
        wallCommand.execute();

        verify(output).writeLine("alice first post message");
    }

    @Test
    public void print_all_messages_posted_to_multiple_followed_users() {
        Instant timestamp = Instant.now();
        User bob = new User("Bob");
        User alice = new User("Alice");
        User jill = new User("Jill");
        bob.follow(alice);
        bob.follow(jill);
        when(userRepository.find("Bob")).thenReturn(Optional.of(bob));
        Message aliceFirstPost = new Message(alice, null, timestamp);
        when(messageRepository.allMessagesPostedTo(alice)).thenReturn(stream(aliceFirstPost));
        Message jillHelloWorld = new Message(jill, null, timestamp);
        when(messageRepository.allMessagesPostedTo(jill)).thenReturn(stream(jillHelloWorld));
        when(wallMessageFormatter.format(aliceFirstPost)).thenReturn("alice first post message");
        when(wallMessageFormatter.format(jillHelloWorld)).thenReturn("jill hello world message");

        WallCommand wallCommand = new WallCommand("Bob", userRepository, messageRepository,
                wallMessageFormatter, output);
        wallCommand.execute();

        InOrder inOrder = inOrder(output);
        inOrder.verify(output).writeLine("alice first post message");
        inOrder.verify(output).writeLine("jill hello world message");
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

        when(userRepository.find("Bob")).thenReturn(Optional.of(bob));
        Message aliceTwoSecondsAgo = new Message(alice, "First post!", twoSecondsAgo);
        Message aliceRightNow = new Message(alice, "This is my second post", now);
        when(messageRepository.allMessagesPostedTo(alice)).thenReturn(stream(aliceTwoSecondsAgo, aliceRightNow));
        Message jillOneSecondAgo = new Message(jill, "Hello world!", oneSecondAgo);
        when(messageRepository.allMessagesPostedTo(jill)).thenReturn(stream(jillOneSecondAgo));

        when(wallMessageFormatter.format(aliceTwoSecondsAgo)).thenReturn("alice two seconds ago");
        when(wallMessageFormatter.format(jillOneSecondAgo)).thenReturn("jill one second ago");
        when(wallMessageFormatter.format(aliceRightNow)).thenReturn("alice right now");

        WallCommand wallCommand = new WallCommand("Bob", userRepository, messageRepository,
                wallMessageFormatter, output);
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