package com.github.richardjwild.blather.command.factory;

import com.github.richardjwild.blather.parsing.Verb;

import java.util.HashMap;
import java.util.Map;

import static com.github.richardjwild.blather.parsing.Verb.*;

public class CommandFactories {

    private Map<Verb, CommandFactory> factories = new HashMap<>();

    public CommandFactories(
            FollowCommandFactory followCommandFactory,
            PostCommandFactory postCommandFactory,
            QuitCommandFactory quitCommandFactory,
            ReadCommandFactory readCommandFactory,
            WallCommandFactory wallCommandFactory)
    {
        factories.put(FOLLOW, followCommandFactory);
        factories.put(POST, postCommandFactory);
        factories.put(QUIT, quitCommandFactory);
        factories.put(READ, readCommandFactory);
        factories.put(WALL, wallCommandFactory);
    }

    public CommandFactory factoryFor(Verb verb) {
        return factories.get(verb);
    }
}
