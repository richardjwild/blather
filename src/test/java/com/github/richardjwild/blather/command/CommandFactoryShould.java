package com.github.richardjwild.blather.command;

import com.github.richardjwild.blather.application.Controller;
import com.github.richardjwild.blather.datatransfer.MessageRepository;
import com.github.richardjwild.blather.datatransfer.UserRepository;
import com.github.richardjwild.blather.io.Output;
import com.github.richardjwild.blather.messageformatting.ReadMessageFormatter;
import com.github.richardjwild.blather.messageformatting.WallMessageFormatter;
import com.github.richardjwild.blather.parsing.BlatherVerb;
import com.github.richardjwild.blather.parsing.ParsedInput;
import com.github.richardjwild.blather.time.Clock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static com.github.richardjwild.blather.parsing.BlatherVerb.QUIT;
import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CommandFactoryShould {

    private static final String RECIPIENT = "recipient";
    private static final String SUBJECT = "subject";
    private static final String FOLLOWER = "follower";
    private static final String MESSAGE = "a message";

    private CommandFactory commandFactory;

    @Mock
    private Controller controller;

    @Mock
    private MessageRepository messageRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private Clock clock;

    @Mock
    private Output output;

    @Mock
    private ReadMessageFormatter readMessageFormatter;

    @Mock
    private WallMessageFormatter wallMessageFormatter;

    @Mock
    private ParsedInput parsedInput;

    @Before
    public void initialize() {
        commandFactory = new CommandFactory(controller, messageRepository, userRepository, clock,
                readMessageFormatter, wallMessageFormatter, output);
    }

    @Test
    public void make_a_quit_command() {
        when(parsedInput.verb()).thenReturn(QUIT);

        Command command = commandFactory.makeCommandFor(parsedInput);

        QuitCommand expectedCommand = new QuitCommand(controller);
        assertThat(command).isEqualTo(expectedCommand);
    }

    @Test
    public void make_a_post_command() {
        when(parsedInput.verb()).thenReturn(BlatherVerb.POST);
        when(parsedInput.postCommandRecipient()).thenReturn(RECIPIENT);
        when(parsedInput.postCommandMessage()).thenReturn(MESSAGE);

        Command command = commandFactory.makeCommandFor(parsedInput);

        PostCommand expectedCommand = new PostCommand(RECIPIENT, MESSAGE, messageRepository, userRepository,
                clock);
        assertThat(command).isEqualTo(expectedCommand);
    }

    @Test
    public void make_a_read_command() {
        when(parsedInput.verb()).thenReturn(BlatherVerb.READ);
        when(parsedInput.readCommandSubject()).thenReturn(SUBJECT);

        Command command = commandFactory.makeCommandFor(parsedInput);

        ReadCommand expectedCommand = new ReadCommand(SUBJECT, messageRepository, userRepository,
                readMessageFormatter, output);
        assertThat(command).isEqualTo(expectedCommand);
    }

    @Test
    public void make_a_follow_command() {
        when(parsedInput.verb()).thenReturn(BlatherVerb.FOLLOW);
        when(parsedInput.followCommandActor()).thenReturn(FOLLOWER);
        when(parsedInput.followCommandSubject()).thenReturn(SUBJECT);

        Command command = commandFactory.makeCommandFor(parsedInput);

        FollowCommand expectedCommand = new FollowCommand(FOLLOWER, SUBJECT, userRepository);
        assertThat(command).isEqualTo(expectedCommand);
    }

    @Test
    public void make_a_wall_command() {
        when(parsedInput.verb()).thenReturn(BlatherVerb.WALL);
        when(parsedInput.wallCommandSubject()).thenReturn(SUBJECT);

        Command command = commandFactory.makeCommandFor(parsedInput);

        WallCommand expectedCommand = new WallCommand(SUBJECT, userRepository, messageRepository,
                wallMessageFormatter, output);
        assertThat(command).isEqualTo(expectedCommand);
    }
}