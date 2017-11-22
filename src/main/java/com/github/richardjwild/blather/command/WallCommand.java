package com.github.richardjwild.blather.command;

import com.github.richardjwild.blather.datatransfer.Message;
import com.github.richardjwild.blather.datatransfer.MessageRepository;
import com.github.richardjwild.blather.datatransfer.User;
import com.github.richardjwild.blather.datatransfer.UserRepository;
import com.github.richardjwild.blather.io.Output;
import com.github.richardjwild.blather.messageformatting.MessageFormatter;

import java.util.Collection;
import java.util.List;

import static java.util.Comparator.comparing;
import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;

public class WallCommand implements Command {

    private final String follower;
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;
    private final MessageFormatter messageFormatter;
    private final Output output;

    WallCommand(String follower, UserRepository userRepository, MessageRepository messageRepository, MessageFormatter messageFormatter, Output output) {
        this.follower = follower;
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
        this.messageFormatter = messageFormatter;
        this.output = output;
    }

    @Override
    public void execute() {
        userRepository.find(follower).ifPresent(this::printAllMessagesPostedToFollowees);
    }

    private void printAllMessagesPostedToFollowees(User follower) {
        follower.following().stream()
                .map(messageRepository::allMessagesPostedTo)
                .flatMap(Collection::stream)
                .sorted(comparing(message -> message.timestamp))
                .map(messageFormatter::format)
                .forEach(output::writeLine);
    }

    @Override
    public boolean equals(Object o) {
        return reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return reflectionHashCode(this);
    }
}
