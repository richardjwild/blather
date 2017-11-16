package com.github.richardjwild.blather.datatransfer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Optional.ofNullable;

public class MessageRepository {

    private Map<User, List<Message>> messages = new HashMap<>();

    public void postMessage(Message message) {
        List<Message> messagesReceived = allMessagesPostedTo(message.recipient);
        if (messagesReceived.isEmpty())
            messages.put(message.recipient, messagesReceived);
        messagesReceived.add(message);
    }

    public List<Message> allMessagesPostedTo(User recipient) {
        return ofNullable(messages.get(recipient)).orElse(new ArrayList<>());
    }
}
