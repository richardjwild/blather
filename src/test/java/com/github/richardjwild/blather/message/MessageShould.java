package com.github.richardjwild.blather.message;

import com.github.richardjwild.blather.user.User;
import com.github.richardjwild.blather.time.TimestampFormatter;
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
        when(timestampFormatter.format(TIMESTAMP)).thenReturn("(AN_INSTANT_IN_TIME)");
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

    @Test
    public void be_comparatively_greater_than_a_message_with_earlier_timestamp() {
        Message earlier = new Message(RECIPIENT, "message text", TIMESTAMP.minusSeconds(1));

        assertThat(message.compareTo(earlier)).isEqualTo(1);
    }

    @Test
    public void be_comparatively_lesser_than_a_message_with_later_timestamp() {
        Message later = new Message(RECIPIENT, "message text", TIMESTAMP.plusSeconds(1));

        assertThat(message.compareTo(later)).isEqualTo(-1);
    }

    @Test
    public void be_comparatively_equal_to_a_message_with_the_same_timestamp() {
        Message simultaneous = new Message(RECIPIENT, "message text", TIMESTAMP);

        assertThat(message.compareTo(simultaneous)).isEqualTo(0);
    }
}