package com.github.richardjwild.blather.datatransfer;

import org.junit.Test;

import java.time.Instant;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

public class MessageRepositoryShould {

    private static final User RECIPIENT = new User();
    private static final String TEXT_1 = "message text 1";
    private static final String TEXT_2 = "message text 2";
    private static final Instant TIMESTAMP_1 = Instant.now();
    private static final Instant TIMESTAMP_2 = TIMESTAMP_1.plusMillis(1);
    public static final Message MESSAGE_1 = new Message(RECIPIENT, TEXT_1, TIMESTAMP_1);
    public static final Message MESSAGE_2 = new Message(RECIPIENT, TEXT_2, TIMESTAMP_2);

    @Test
    public void store_all_messages_posted_to_a_recipient() {
        MessageRepository messageRepository = new MessageRepository();
        messageRepository.postMessage(MESSAGE_1);
        messageRepository.postMessage(MESSAGE_2);

        List<Message> actualMessages = messageRepository.allMessagesPostedTo(RECIPIENT);

        assertThat(actualMessages).containsExactly(MESSAGE_1, MESSAGE_2);
    }

}