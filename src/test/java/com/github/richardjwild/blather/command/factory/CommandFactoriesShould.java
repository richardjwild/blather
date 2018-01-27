package com.github.richardjwild.blather.command.factory;

import com.github.richardjwild.blather.parsing.Verb;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(JUnitParamsRunner.class)
@SuppressWarnings("unused")
public class CommandFactoriesShould {

    @Mock
    private FollowCommandFactory followCommandFactory;

    @Mock
    private PostCommandFactory postCommandFactory;

    @Mock
    private QuitCommandFactory quitCommandFactory;

    @Mock
    private ReadCommandFactory readCommandFactory;

    @Mock
    private WallCommandFactory wallCommandFactory;

    @InjectMocks
    private CommandFactories commandFactories;

    @Before
    public void initialize() {
        initMocks(this);
    }

    @Test
    @Parameters({
            "FOLLOW | com.github.richardjwild.blather.command.factory.FollowCommandFactory",
            "READ | com.github.richardjwild.blather.command.factory.ReadCommandFactory",
            "WALL | com.github.richardjwild.blather.command.factory.WallCommandFactory",
            "POST | com.github.richardjwild.blather.command.factory.PostCommandFactory",
            "QUIT | com.github.richardjwild.blather.command.factory.QuitCommandFactory"
    })
    public void return_correct_factory_for_verb(Verb givenVerb, Class<? extends CommandFactory> expectedFactoryClass) {
        CommandFactory commandFactory = commandFactories.factoryFor(givenVerb);

        assertThat(commandFactory).isInstanceOf(expectedFactoryClass);
    }
}