package com.github.richardjwild.blather.datatransfer;

import java.util.*;

import static java.util.Optional.ofNullable;

public class MessageRepository {

    private Map<User, List<Message>> allMessages = new HashMap<>();

    public void postMessage(Message message) {
        User recipient = message.recipient;
        List<Message> receivedMessages = getOrCreateMessageListFor(recipient);
        receivedMessages.add(message);
    }

    private List<Message> getOrCreateMessageListFor(User recipient) {
        return messageListFor(recipient).orElseGet(() -> createMessageListFor(recipient));
    }

    private Optional<List<Message>> messageListFor(User recipient) {
        List<Message> messageList = allMessages.get(recipient);
        return ofNullable(messageList);
    }

    private List<Message> createMessageListFor(User recipient) {
        List<Message> emptyList = new ArrayList<>();
        allMessages.put(recipient, emptyList);
        return emptyList;
    }

    public List<Message> allMessagesPostedTo(User recipient) {
        return messageListFor(recipient).orElseGet(Collections::emptyList);
    }
}
