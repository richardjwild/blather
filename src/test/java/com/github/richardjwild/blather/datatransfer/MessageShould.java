package com.github.richardjwild.blather.datatransfer;

import com.github.richardjwild.blather.messageformatting.TimestampFormatter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.Instant;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MessageShould {

    private static final Instant TIMESTAMP = Instant.now();
    private static final User RECIPIENT = new User("recipient");

    @Mock
    private TimestampFormatter timestampFormatter;

    private Message message;

    @Before
    public void initialize() {
        when(timestampFormatter.formatTimestamp(TIMESTAMP)).thenReturn("(AN_INSTANT_IN_TIME)");
        message = new Message(RECIPIENT, "message text", TIMESTAMP);
    }

    @Test
    public void format_for_a_read_command() {
        String formattedMessage = message.formatRead(timestampFormatter);

        assertThat(formattedMessage).isEqualTo("message text (AN_INSTANT_IN_TIME)");
    }

    @Test
    public void format_a_message_with_recipient_name() {
        String formattedMessage = message.formatWall(timestampFormatter);

        assertThat(formattedMessage).isEqualTo("recipient - message text (AN_INSTANT_IN_TIME)");
    }
}