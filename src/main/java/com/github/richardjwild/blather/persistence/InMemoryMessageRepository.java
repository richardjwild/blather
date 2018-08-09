package com.github.richardjwild.blather.persistence;

import com.github.richardjwild.blather.message.Message;
import com.github.richardjwild.blather.message.MessageRepository;
import com.github.richardjwild.blather.user.User;

import java.util.*;
import java.util.stream.Stream;

import static java.util.Optional.ofNullable;

public class InMemoryMessageRepository implements MessageRepository {

    private Map<User, List<Message>> allMessages = new HashMap<>();

    @Override
    public void postMessage(User recipient, Message message) {
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

    @Override
    public Stream<Message> allMessagesPostedTo(User recipient) {
        return messageListFor(recipient)
                .map(Collection::stream)
                .orElseGet(Stream::empty);
    }
}
