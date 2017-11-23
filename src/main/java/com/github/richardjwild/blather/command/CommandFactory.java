package com.github.richardjwild.blather.command;

import com.github.richardjwild.blather.application.Controller;
import com.github.richardjwild.blather.datatransfer.MessageRepository;
import com.github.richardjwild.blather.datatransfer.UserRepository;
import com.github.richardjwild.blather.io.Output;
import com.github.richardjwild.blather.messageformatting.ReadMessageFormatter;
import com.github.richardjwild.blather.messageformatting.WallMessageFormatter;
import com.github.richardjwild.blather.parsing.ParsedInput;
import com.github.richardjwild.blather.time.Clock;

public class CommandFactory {

    private final Controller controller;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final Clock clock;
    private final Output output;
    private final ReadMessageFormatter readMessageFormatter;
    private final WallMessageFormatter wallMessageFormatter;

    public CommandFactory(Controller controller, MessageRepository messageRepository,
                          UserRepository userRepository, Clock clock, ReadMessageFormatter readMessageFormatter,
                          WallMessageFormatter wallMessageFormatter, Output output) {
        this.controller = controller;
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.clock = clock;
        this.readMessageFormatter = readMessageFormatter;
        this.wallMessageFormatter = wallMessageFormatter;
        this.output = output;
    }

    public Command makeCommandFor(ParsedInput input) {
        switch (input.verb()) {
            case POST:
                return postCommand(input.postCommandRecipient(), input.postCommandMessage());
            case READ:
                return readCommand(input.readCommandSubject());
            case FOLLOW:
                return followCommand(input.followCommandActor(), input.followCommandSubject());
            case WALL:
                return wallCommand(input.wallCommandSubject());
            default:
                return quitCommand();
        }
    }

    private Command postCommand(String postCommandRecipient, String postCommandMessage) {
        return new PostCommand(postCommandRecipient, postCommandMessage,
                messageRepository, userRepository, clock);
    }

    private Command readCommand(String readCommandSubject) {
        return new ReadCommand(readCommandSubject, messageRepository, userRepository,
                readMessageFormatter, output);
    }

    private Command followCommand(String followCommandActor, String followCommandSubject) {
        return new FollowCommand(followCommandActor, followCommandSubject, userRepository);
    }

    private Command wallCommand(String wallCommandSubject) {
        return new WallCommand(wallCommandSubject,
                userRepository, messageRepository, wallMessageFormatter, output);
    }

    private QuitCommand quitCommand() {
        return new QuitCommand(controller);
    }
}
