package com.github.richardjwild.blather.messageformatting;

import com.github.richardjwild.blather.datatransfer.Message;
import com.github.richardjwild.blather.datatransfer.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.Instant;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReadMessageFormatterShould {

    @Mock
    TimestampFormatter timestampFormatter;

    @Test
    public void format_a_message_with_timestamp() {
        Instant timestamp = Instant.now();
        User recipient = new User("recipient");
        Message message = new Message(recipient, "message text", timestamp);
        when(timestampFormatter.formatTimestamp(timestamp)).thenReturn("(TIMESTAMP)");
        ReadMessageFormatter readMessageFormatter = new ReadMessageFormatter(timestampFormatter);

        String formattedMessage = readMessageFormatter.format(message);

        assertThat(formattedMessage).isEqualTo("message text (TIMESTAMP)");
    }

}
