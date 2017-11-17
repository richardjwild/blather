package com.github.richardjwild.blather.datatransfer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageRepository {

    private Map<User, List<Message>> allMessages = new HashMap<>();

    public void postMessage(Message message) {
        List<Message> messagesForRecipient = getOrCreateMessageListFor(message.recipient);
        messagesForRecipient.add(message);
    }

    private List<Message> getOrCreateMessageListFor(User recipient) {
        if (allMessages.containsKey(recipient))
            return allMessages.get(recipient);
        return createMessageListFor(recipient);
    }

    private List<Message> createMessageListFor(User recipient) {
        List<Message> emptyList = new ArrayList<>();
        allMessages.put(recipient, emptyList);
        return emptyList;
    }

    public List<Message> allMessagesPostedTo(User recipient) {
        List<Message> messagesForRecipient = getOrCreateMessageListFor(recipient);
        return messagesForRecipient;
    }
}
