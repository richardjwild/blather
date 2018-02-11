package com.github.richardjwild.blather.command.factory;

import com.github.richardjwild.blather.command.Command;
import com.github.richardjwild.blather.command.ReadCommand;
import com.github.richardjwild.blather.message.MessageRepository;
import com.github.richardjwild.blather.user.UserRepository;
import com.github.richardjwild.blather.io.Output;
import com.github.richardjwild.blather.time.TimestampFormatter;
import com.github.richardjwild.blather.parsing.ParsedInput;

public class ReadCommandFactory implements CommandFactory {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final TimestampFormatter timestampFormatter;
    private final Output output;

    public ReadCommandFactory(
            MessageRepository messageRepository,
            UserRepository userRepository,
            TimestampFormatter timestampFormatter,
            Output output)
    {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.timestampFormatter = timestampFormatter;
        this.output = output;
    }

    @Override
    public Command makeCommandFor(ParsedInput input) {
        return new ReadCommand(
                input.readCommandSubject(),
                messageRepository,
                userRepository,
                timestampFormatter,
                output);
    }
}
