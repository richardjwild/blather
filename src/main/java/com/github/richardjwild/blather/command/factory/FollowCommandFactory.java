package com.github.richardjwild.blather.command.factory;

import com.github.richardjwild.blather.command.Command;
import com.github.richardjwild.blather.command.FollowCommand;
import com.github.richardjwild.blather.user.UserRepository;
import com.github.richardjwild.blather.parsing.ParsedInput;

public class FollowCommandFactory implements CommandFactory {

    private final UserRepository userRepository;

    public FollowCommandFactory(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Command makeCommandFor(ParsedInput input) {
        return new FollowCommand(
                input.followCommandActor(), input.followCommandSubject(), userRepository);
    }
}
