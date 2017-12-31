package com.github.richardjwild.blather.application;

import com.github.richardjwild.blather.command.factory.CommandFactory;
import com.github.richardjwild.blather.datatransfer.MessageRepository;
import com.github.richardjwild.blather.datatransfer.UserRepository;
import com.github.richardjwild.blather.io.Input;
import com.github.richardjwild.blather.io.Output;
import com.github.richardjwild.blather.messageformatting.ReadMessageFormatter;
import com.github.richardjwild.blather.messageformatting.WallMessageFormatter;
import com.github.richardjwild.blather.parsing.CommandReader;
import com.github.richardjwild.blather.parsing.InputParser;
import com.github.richardjwild.blather.time.Clock;
import com.github.richardjwild.blather.messageformatting.TimestampFormatter;

public class ApplicationBuilder {

    public static Application build(Input input, Output output, Clock clock) {
        UserRepository userRepository = new UserRepository();
        InputParser inputParser = new InputParser();
        Controller controller = new Controller();
        MessageRepository messageRepository = new MessageRepository();
        TimestampFormatter timestampFormatter = new TimestampFormatter(clock);
        ReadMessageFormatter readMessageFormatter = new ReadMessageFormatter(timestampFormatter);
        WallMessageFormatter wallMessageFormatter = new WallMessageFormatter(readMessageFormatter);
        CommandFactory commandFactory = new CommandFactory(controller, messageRepository, userRepository, clock,
                readMessageFormatter, wallMessageFormatter, output);
        CommandReader commandReader = new CommandReader(input, inputParser, commandFactory);
        EventLoop eventLoop = new EventLoop(commandReader, controller);
        return new Application(eventLoop, output);
    }
}
