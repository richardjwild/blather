package com.github.richardjwild.blather.message;

import com.github.richardjwild.blather.user.User;

import java.util.stream.Stream;

public interface MessageRepository {
    void postMessage(User recipient, Message message);

    Stream<Message> allMessagesPostedTo(User recipient);
}
