package com.github.richardjwild.blather.command.factory;

import com.github.richardjwild.blather.application.Controller;
import com.github.richardjwild.blather.command.*;
import com.github.richardjwild.blather.datatransfer.MessageRepository;
import com.github.richardjwild.blather.datatransfer.UserRepository;
import com.github.richardjwild.blather.io.Output;
import com.github.richardjwild.blather.messageformatting.ReadMessageFormatter;
import com.github.richardjwild.blather.messageformatting.WallMessageFormatter;
import com.github.richardjwild.blather.parsing.Verb;
import com.github.richardjwild.blather.parsing.ParsedInput;
import com.github.richardjwild.blather.time.Clock;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static com.github.richardjwild.blather.parsing.Verb.*;

public class CommandFactory {

    private Map<Verb, Function<ParsedInput, Command>> builders = new HashMap<>();

    public CommandFactory(Controller controller, MessageRepository messageRepository,
                          UserRepository userRepository, Clock clock, ReadMessageFormatter readMessageFormatter,
                          WallMessageFormatter wallMessageFormatter, Output output) {

        builders.put(POST, input -> new PostCommand(input.postCommandRecipient(), input.postCommandMessage(),
                messageRepository, userRepository, clock));

        builders.put(READ, input -> new ReadCommand(input.readCommandSubject(),
                messageRepository, userRepository, readMessageFormatter, output));

        builders.put(FOLLOW, input -> new FollowCommand(input.followCommandActor(), input.followCommandSubject(),
                userRepository));

        builders.put(WALL, input -> new WallCommand(input.wallCommandSubject(),
                userRepository, messageRepository, wallMessageFormatter, output));

        builders.put(QUIT, input -> new QuitCommand(controller));
    }

    public Command makeCommandFor(ParsedInput input) {
        Verb verb = input.verb();
        return commandBuilderFor(verb).apply(input);
    }

    private Function<ParsedInput, Command> commandBuilderFor(Verb verb) {
        return builders.get(verb);
    }
}
