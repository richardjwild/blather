package com.github.richardjwild.blather.datatransfer;

import org.junit.Test;

import java.time.Instant;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

public class MessageRepositoryShould {

    private static final User RECIPIENT_1 = new User("recipient1");
    private static final User RECIPIENT_2 = new User("recipient2");
    private static final String TEXT_1 = "message text 1";
    private static final String TEXT_2 = "message text 2";
    private static final String TEXT_3 = "message text 3";
    private static final Instant TIMESTAMP_1 = Instant.now();
    private static final Instant TIMESTAMP_2 = TIMESTAMP_1.plusMillis(1);
    private static final Instant TIMESTAMP_3 = TIMESTAMP_2.plusMillis(1);
    private static final Message MESSAGE_1_FOR_RECIPIENT_1 = new Message(RECIPIENT_1, TEXT_1, TIMESTAMP_1);
    private static final Message MESSAGE_2_FOR_RECEIPIENT_1 = new Message(RECIPIENT_1, TEXT_2, TIMESTAMP_2);
    private static final Message MESSAGE_1_FOR_RECIPIENT_2 = new Message(RECIPIENT_2, TEXT_3, TIMESTAMP_3);

    private final MessageRepository messageRepository = new MessageRepository();

    @Test
    public void store_all_messages_posted_to_a_recipient() {
        messageRepository.postMessage(MESSAGE_1_FOR_RECIPIENT_1);
        messageRepository.postMessage(MESSAGE_2_FOR_RECEIPIENT_1);

        List<Message> actualMessages = messageRepository.allMessagesPostedTo(RECIPIENT_1);

        assertThat(actualMessages).containsExactly(MESSAGE_1_FOR_RECIPIENT_1, MESSAGE_2_FOR_RECEIPIENT_1);
    }

    @Test
    public void retrieve_messages_posted_to_a_recipient() {
        messageRepository.postMessage(MESSAGE_1_FOR_RECIPIENT_1);
        messageRepository.postMessage(MESSAGE_1_FOR_RECIPIENT_2);

        List<Message> messagesForRecipient1 = messageRepository.allMessagesPostedTo(RECIPIENT_1);
        List<Message> messagesForRecipient2 = messageRepository.allMessagesPostedTo(RECIPIENT_2);

        assertThat(messagesForRecipient1).containsExactly(MESSAGE_1_FOR_RECIPIENT_1);
        assertThat(messagesForRecipient2).containsExactly(MESSAGE_1_FOR_RECIPIENT_2);
    }

    @Test
    public void return_empty_collection_when_no_messages_posted_to_recipient() {
        List<Message> actualMessages = messageRepository.allMessagesPostedTo(RECIPIENT_1);

        assertThat(actualMessages).isEmpty();
    }
}