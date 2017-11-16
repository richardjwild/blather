package com.github.richardjwild.blather.command;

import com.github.richardjwild.blather.datatransfer.Message;
import com.github.richardjwild.blather.datatransfer.MessageRepository;
import com.github.richardjwild.blather.datatransfer.User;
import com.github.richardjwild.blather.time.Clock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.Instant;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class PostCommandShould {

    private static final User RECIPIENT = new User();
    private static final String MESSAGE_TEXT = "message text";
    private static final Instant TIMESTAMP = Instant.now();

    @Mock
    private MessageRepository messageRepository;

    @Mock
    private Clock clock;

    @Test
    public void post_a_new_message_to_the_repository() {
        when(clock.now()).thenReturn(TIMESTAMP);
        PostCommand postCommand = new PostCommand(RECIPIENT, MESSAGE_TEXT, messageRepository, clock);

        postCommand.execute();

        Message expectedMessage = new Message(RECIPIENT, MESSAGE_TEXT, TIMESTAMP);
        verify(messageRepository).postMessage(expectedMessage);
    }

}