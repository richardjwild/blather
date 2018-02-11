package com.github.richardjwild.blather.command;

import com.github.richardjwild.blather.command.factory.WallCommandFactory;
import com.github.richardjwild.blather.message.Message;
import com.github.richardjwild.blather.message.MessageRepository;
import com.github.richardjwild.blather.parsing.ParsedInput;
import com.github.richardjwild.blather.user.User;
import com.github.richardjwild.blather.user.UserRepository;
import com.github.richardjwild.blather.io.Output;
import com.github.richardjwild.blather.time.TimestampFormatter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

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
    private TimestampFormatter timestampFormatter;

    @Mock
    private Message firstPost, aliceFirstPost, jillHelloWorld,
            aliceTwoSecondsAgo, aliceRightNow, jillOneSecondAgo,
            bobRightNow;

    @Mock
    private ParsedInput parsedInput;

    @InjectMocks
    private WallCommandFactory factory;

    @Test
    public void print_all_messages_posted_to_one_followed_user() {
        User bob = new User("Bob");
        User alice = new User("Alice");

        bob.follow(alice);

        when(userRepository.find("Bob")).thenReturn(Optional.of(bob));
        when(messageRepository.allMessagesPostedTo(bob)).thenReturn(Stream.empty());
        when(messageRepository.allMessagesPostedTo(alice)).thenReturn(stream(firstPost));
        when(firstPost.formatWall(timestampFormatter)).thenReturn("alice first post message");

        Command wallCommand = makeCommandForSubject("Bob");
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
        when(messageRepository.allMessagesPostedTo(bob)).thenReturn(Stream.empty());
        when(messageRepository.allMessagesPostedTo(alice)).thenReturn(stream(aliceFirstPost));
        when(messageRepository.allMessagesPostedTo(jill)).thenReturn(stream(jillHelloWorld));
        when(aliceFirstPost.formatWall(timestampFormatter)).thenReturn("alice first post message");
        when(jillHelloWorld.formatWall(timestampFormatter)).thenReturn("jill hello world message");

        messageOrderIs(aliceFirstPost, jillHelloWorld);

        Command wallCommand = makeCommandForSubject("Bob");
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
        when(messageRepository.allMessagesPostedTo(bob)).thenReturn(Stream.empty());
        when(messageRepository.allMessagesPostedTo(alice)).thenReturn(stream(aliceTwoSecondsAgo, aliceRightNow));
        when(messageRepository.allMessagesPostedTo(jill)).thenReturn(stream(jillOneSecondAgo));

        when(aliceTwoSecondsAgo.formatWall(timestampFormatter)).thenReturn("alice two seconds ago");
        when(jillOneSecondAgo.formatWall(timestampFormatter)).thenReturn("jill one second ago");
        when(aliceRightNow.formatWall(timestampFormatter)).thenReturn("alice right now");

        messageOrderIs(aliceTwoSecondsAgo, jillOneSecondAgo, aliceRightNow);

        Command wallCommand = makeCommandForSubject("Bob");
        wallCommand.execute();

        InOrder inOrder = inOrder(output);
        inOrder.verify(output).writeLine("alice two seconds ago");
        inOrder.verify(output).writeLine("jill one second ago");
        inOrder.verify(output).writeLine("alice right now");
    }

    @Test
    public void also_print_the_followers_own_messages() {
        User bob = new User("Bob");
        User alice = new User("Alice");

        bob.follow(alice);

        when(userRepository.find("Bob")).thenReturn(Optional.of(bob));
        when(messageRepository.allMessagesPostedTo(alice)).thenReturn(stream(aliceTwoSecondsAgo));
        when(messageRepository.allMessagesPostedTo(bob)).thenReturn(stream(bobRightNow));

        when(aliceTwoSecondsAgo.formatWall(timestampFormatter)).thenReturn("alice two seconds ago");
        when(bobRightNow.formatWall(timestampFormatter)).thenReturn("bob right now");

        messageOrderIs(aliceTwoSecondsAgo, bobRightNow);
        Command wallCommand = makeCommandForSubject("Bob");
        wallCommand.execute();

        InOrder inOrder = inOrder(output);
        inOrder.verify(output).writeLine("alice two seconds ago");
        inOrder.verify(output).writeLine("bob right now");
    }

    private Command makeCommandForSubject(String subjectName) {
        when(parsedInput.wallCommandSubject()).thenReturn(subjectName);
        return factory.makeCommandFor(parsedInput);
    }

    private Stream<Message> stream(Message... messages) {
        return Arrays.stream(messages);
    }

    private void messageOrderIs(Message... orderOfMessages) {
        for (int i=0; i<(orderOfMessages.length - 1); i++) {
            Message earlier = orderOfMessages[i];
            Message later = orderOfMessages[i+1];
            when(earlier.compareTo(later)).thenReturn(-1);
            when(later.compareTo(earlier)).thenReturn(1);
        }
    }
}