package com.github.richardjwild.blather.command;

import com.github.richardjwild.blather.datatransfer.Message;
import com.github.richardjwild.blather.datatransfer.MessageRepository;
import com.github.richardjwild.blather.datatransfer.User;
import com.github.richardjwild.blather.datatransfer.UserRepository;
import com.github.richardjwild.blather.io.Output;
import com.github.richardjwild.blather.messageformatting.WallMessageFormatter;

import java.util.Optional;

import static java.util.Comparator.comparing;
import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;

public class WallCommand implements Command {

    private final String followerUserName;
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;
    private final WallMessageFormatter messageFormatter;
    private final Output output;

    public WallCommand(
            String followerUserName,
            UserRepository userRepository,
            MessageRepository messageRepository,
            WallMessageFormatter messageFormatter,
            Output output) {
        this.followerUserName = followerUserName;
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
        this.messageFormatter = messageFormatter;
        this.output = output;
    }

    @Override
    public void execute() {
        findFollower().ifPresent(this::printWallMessages);
    }

    private Optional<User> findFollower() {
        return userRepository.find(followerUserName);
    }

    private void printWallMessages(User follower) {
        follower.following()
                .flatMap(messageRepository::allMessagesPostedTo)
                .sorted(comparing(Message::timestamp))
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
