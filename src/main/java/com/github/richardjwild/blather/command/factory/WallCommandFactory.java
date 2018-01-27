package com.github.richardjwild.blather.command.factory;

import com.github.richardjwild.blather.command.Command;
import com.github.richardjwild.blather.command.WallCommand;
import com.github.richardjwild.blather.datatransfer.MessageRepository;
import com.github.richardjwild.blather.datatransfer.UserRepository;
import com.github.richardjwild.blather.io.Output;
import com.github.richardjwild.blather.messageformatting.WallMessageFormatter;
import com.github.richardjwild.blather.parsing.ParsedInput;

public class WallCommandFactory implements CommandFactory {

    private final UserRepository userRepository;
    private final MessageRepository messageRepository;
    private final WallMessageFormatter wallMessageFormatter;
    private final Output output;

    public WallCommandFactory(
            UserRepository userRepository,
            MessageRepository messageRepository,
            WallMessageFormatter wallMessageFormatter,
            Output output) {
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
        this.wallMessageFormatter = wallMessageFormatter;
        this.output = output;
    }

    @Override
    public Command makeCommandFor(ParsedInput input) {
        return new WallCommand(
                input.wallCommandSubject(), userRepository, messageRepository, wallMessageFormatter, output);
    }
}
