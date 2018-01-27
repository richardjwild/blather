package com.github.richardjwild.blather.command.factory;

import com.github.richardjwild.blather.command.Command;
import com.github.richardjwild.blather.command.FollowCommand;
import com.github.richardjwild.blather.datatransfer.UserRepository;
import com.github.richardjwild.blather.parsing.ParsedInput;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static com.github.richardjwild.blather.parsing.Verb.FOLLOW;
import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FollowCommandFactoryShould {

    private static final String SUBJECT = "a subject";
    private static final String FOLLOWER = "a follower";

    @Mock
    private ParsedInput parsedInput;

    @Mock
    private UserRepository userRepository;

    @Test
    public void make_a_follow_command() {
        CommandFactory followCommandFactory = new FollowCommandFactory(userRepository);
        when(parsedInput.verb()).thenReturn(FOLLOW);
        when(parsedInput.followCommandActor()).thenReturn(FOLLOWER);
        when(parsedInput.followCommandSubject()).thenReturn(SUBJECT);

        Command command = followCommandFactory.makeCommandFor(parsedInput);

        FollowCommand expectedCommand = new FollowCommand(FOLLOWER, SUBJECT, userRepository);
        assertThat(command).isEqualTo(expectedCommand);
    }
}
