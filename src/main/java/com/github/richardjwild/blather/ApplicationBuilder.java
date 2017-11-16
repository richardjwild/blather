package com.github.richardjwild.blather;

import com.github.richardjwild.blather.command.CommandFactory;
import com.github.richardjwild.blather.datatransfer.MessageRepository;
import com.github.richardjwild.blather.datatransfer.UserRepository;
import com.github.richardjwild.blather.io.ConsoleInput;
import com.github.richardjwild.blather.io.ConsoleOutput;
import com.github.richardjwild.blather.io.Input;
import com.github.richardjwild.blather.io.Output;
import com.github.richardjwild.blather.parsing.CommandReader;
import com.github.richardjwild.blather.parsing.InputParser;
import com.github.richardjwild.blather.time.Clock;
import com.github.richardjwild.blather.time.TimestampFormatter;

public class ApplicationBuilder {

    static Blather build() {
        UserRepository userRepository = new UserRepository();
        InputParser inputParser = new InputParser(userRepository);
        AppController appController = new AppController();
        MessageRepository messageRepository = new MessageRepository();
        Clock clock = new Clock();
        Input input = new ConsoleInput();
        Output output = new ConsoleOutput();
        TimestampFormatter timestampFormatter = new TimestampFormatter(clock);
        CommandFactory commandFactory = new CommandFactory(appController, messageRepository, clock, timestampFormatter, output);
        CommandReader commandReader = new CommandReader(input, inputParser, commandFactory);
        return new Blather(appController, commandReader, output);
    }
}
