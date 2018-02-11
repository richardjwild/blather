package com.github.richardjwild.blather.command.factory;

import com.github.richardjwild.blather.command.Command;
import com.github.richardjwild.blather.command.WallCommand;
import com.github.richardjwild.blather.message.MessageRepository;
import com.github.richardjwild.blather.user.UserRepository;
import com.github.richardjwild.blather.io.Output;
import com.github.richardjwild.blather.time.TimestampFormatter;
import com.github.richardjwild.blather.parsing.ParsedInput;

public class WallCommandFactory implements CommandFactory {

    private final UserRepository userRepository;
    private final MessageRepository messageRepository;
    private final TimestampFormatter timestampFormatter;
    private final Output output;

    public WallCommandFactory(
            UserRepository userRepository,
            MessageRepository messageRepository,
            TimestampFormatter timestampFormatter,
            Output output)
    {
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
        this.timestampFormatter = timestampFormatter;
        this.output = output;
    }

    @Override
    public Command makeCommandFor(ParsedInput input) {
        return new WallCommand(
                input.wallCommandSubject(),
                userRepository,
                messageRepository,
                timestampFormatter,
                output);
    }
}
