package com.github.richardjwild.blather.command;

import com.github.richardjwild.blather.datatransfer.Message;
import com.github.richardjwild.blather.datatransfer.MessageRepository;
import com.github.richardjwild.blather.datatransfer.User;
import com.github.richardjwild.blather.datatransfer.UserRepository;
import com.github.richardjwild.blather.time.Clock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.Instant;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class PostCommandShould {

    private static final String RECIPIENT_NAME = "name_of_person";
    private static final User RECIPIENT = new User("name_of_person");
    private static final String MESSAGE_TEXT = "message text";
    private static final Instant TIMESTAMP = Instant.now();

    @Mock
    private MessageRepository messageRepository;

    @Mock
    private Clock clock;

    @Mock
    private UserRepository userRepository;

    private PostCommand postCommand;

    @Before
    public void initialize() {
        postCommand = new PostCommand(RECIPIENT_NAME, MESSAGE_TEXT, messageRepository, userRepository, clock);
        when(clock.now()).thenReturn(TIMESTAMP);
    }

    @Test
    public void post_a_message_for_an_existing_user() {
        when(userRepository.find(RECIPIENT_NAME)).thenReturn(of(RECIPIENT));

        postCommand.execute();

        Message expectedMessage = new Message(RECIPIENT, MESSAGE_TEXT, TIMESTAMP);
        verify(messageRepository).postMessage(expectedMessage);
        verify(userRepository, never()).save(RECIPIENT);
    }

    @Test
    public void post_a_message_for_a_new_user() {
        when(userRepository.find(RECIPIENT_NAME)).thenReturn(empty());

        postCommand.execute();

        User expectedNewUser = new User(RECIPIENT_NAME);
        Message expectedMessage = new Message(expectedNewUser, MESSAGE_TEXT, TIMESTAMP);
        verify(messageRepository).postMessage(expectedMessage);
        verify(userRepository).save(expectedNewUser);
    }

}