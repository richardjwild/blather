package com.github.richardjwild.blather.command;

import com.github.richardjwild.blather.application.Controller;
import com.github.richardjwild.blather.datatransfer.MessageRepository;
import com.github.richardjwild.blather.datatransfer.UserRepository;
import com.github.richardjwild.blather.io.Output;
import com.github.richardjwild.blather.messageformatting.MessageFormatter;
import com.github.richardjwild.blather.time.Clock;
import com.github.richardjwild.blather.messageformatting.TimestampFormatter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.Assertions.assertThat;

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
    private MessageFormatter readMessageFormatter;

    @Mock
    private MessageFormatter wallMessageFormatter;

    @Before
    public void initialize() {
        commandFactory = new CommandFactory(controller, messageRepository, userRepository, clock,
                readMessageFormatter, wallMessageFormatter, output);
    }

    @Test
    public void make_a_quit_command() {
        QuitCommand expectedCommand = new QuitCommand(controller);

        Command command = commandFactory.makeQuitCommand();

        assertThat(command).isEqualTo(expectedCommand);
    }

    @Test
    public void make_a_post_command() {
        PostCommand expectedCommand = new PostCommand(RECIPIENT, MESSAGE, messageRepository, userRepository,
                clock);

        Command command = commandFactory.makePostCommand(RECIPIENT, MESSAGE);

        assertThat(command).isEqualTo(expectedCommand);
    }

    @Test
    public void make_a_read_command() {
        ReadCommand expectedCommand = new ReadCommand(SUBJECT, messageRepository, userRepository,
                readMessageFormatter, output);

        Command command = commandFactory.makeReadCommand(SUBJECT);

        assertThat(command).isEqualTo(expectedCommand);
    }

    @Test
    public void make_a_follow_command() {
        FollowCommand expectedCommand = new FollowCommand(FOLLOWER, SUBJECT, userRepository);

        Command command = commandFactory.makeFollowCommand(FOLLOWER, SUBJECT);

        assertThat(command).isEqualTo(expectedCommand);
    }

    @Test
    public void make_a_wall_command() {
        WallCommand expectedCommand = new WallCommand(SUBJECT, userRepository, messageRepository,
                wallMessageFormatter, output);

        Command command = commandFactory.makeWallCommand(SUBJECT);

        assertThat(command).isEqualTo(expectedCommand);
    }
}