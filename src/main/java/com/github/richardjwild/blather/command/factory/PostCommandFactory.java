package com.github.richardjwild.blather.command.factory;

import com.github.richardjwild.blather.command.Command;
import com.github.richardjwild.blather.command.PostCommand;
import com.github.richardjwild.blather.message.MessageRepository;
import com.github.richardjwild.blather.user.UserRepository;
import com.github.richardjwild.blather.parsing.ParsedInput;
import com.github.richardjwild.blather.time.Clock;

public class PostCommandFactory implements CommandFactory {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final Clock clock;

    public PostCommandFactory(
            MessageRepository messageRepository,
            UserRepository userRepository,
            Clock clock)
    {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.clock = clock;
    }

    @Override
    public Command makeCommandFor(ParsedInput input) {
        return new PostCommand(
                input.postCommandRecipient(),
                input.postCommandMessage(),
                messageRepository,
                userRepository,
                clock);
    }
}
