package com.github.richardjwild.blather.command;

import com.github.richardjwild.blather.datatransfer.Message;
import com.github.richardjwild.blather.datatransfer.MessageRepository;
import com.github.richardjwild.blather.datatransfer.User;
import com.github.richardjwild.blather.datatransfer.UserRepository;
import com.github.richardjwild.blather.io.Output;
import com.github.richardjwild.blather.time.TimestampFormatter;

import java.util.Optional;

import static java.util.Comparator.comparing;
import static org.apache.commons.lang3.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang3.builder.HashCodeBuilder.reflectionHashCode;

public class WallCommand implements Command {

    private String followerUserName;
    private UserRepository userRepository;
    private MessageRepository messageRepository;
    private TimestampFormatter timestampFormatter;
    private Output output;

    public WallCommand(
            String followerUserName,
            UserRepository userRepository,
            MessageRepository messageRepository,
            TimestampFormatter timestampFormatter,
            Output output)
    {
        this.followerUserName = followerUserName;
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
        this.timestampFormatter = timestampFormatter;
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
                .map(this::format)
                .forEach(output::writeLine);
    }

    private String format(Message message) {
        return message.formatWall(timestampFormatter);
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
