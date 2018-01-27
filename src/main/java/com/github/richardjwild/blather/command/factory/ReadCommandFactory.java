package com.github.richardjwild.blather.command.factory;

import com.github.richardjwild.blather.command.Command;
import com.github.richardjwild.blather.command.ReadCommand;
import com.github.richardjwild.blather.datatransfer.MessageRepository;
import com.github.richardjwild.blather.datatransfer.UserRepository;
import com.github.richardjwild.blather.io.Output;
import com.github.richardjwild.blather.messageformatting.ReadMessageFormatter;
import com.github.richardjwild.blather.parsing.ParsedInput;

public class ReadCommandFactory implements CommandFactory {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final ReadMessageFormatter readMessageFormatter;
    private final Output output;

    public ReadCommandFactory(
            MessageRepository messageRepository,
            UserRepository userRepository,
            ReadMessageFormatter readMessageFormatter,
            Output output) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.readMessageFormatter = readMessageFormatter;
        this.output = output;
    }

    @Override
    public Command makeCommandFor(ParsedInput input) {
        return new ReadCommand(
                input.readCommandSubject(), messageRepository, userRepository, readMessageFormatter, output);
    }
}
