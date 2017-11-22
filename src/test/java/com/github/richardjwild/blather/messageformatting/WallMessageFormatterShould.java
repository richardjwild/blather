package com.github.richardjwild.blather.messageformatting;

import com.github.richardjwild.blather.datatransfer.Message;
import com.github.richardjwild.blather.datatransfer.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WallMessageFormatterShould {

    @Mock
    ReadMessageFormatter readMessageFormatter;

    @Test
    public void format_a_message_with_recipient_name() {
        User recipient = new User("recipient");
        Message message = new Message(recipient, null, null);
        when(readMessageFormatter.format(message)).thenReturn("message");
        WallMessageFormatter wallMessageFormatter = new WallMessageFormatter(readMessageFormatter);

        String formattedMessage = wallMessageFormatter.format(message);

        assertThat(formattedMessage).isEqualTo("recipient - message");
    }

}
