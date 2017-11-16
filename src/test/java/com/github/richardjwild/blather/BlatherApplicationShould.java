package com.github.richardjwild.blather;

import com.github.richardjwild.blather.command.CommandFactory;
import com.github.richardjwild.blather.datatransfer.MessageRepository;
import com.github.richardjwild.blather.parsing.CommandReader;
import com.github.richardjwild.blather.io.Input;
import com.github.richardjwild.blather.parsing.InputParser;
import com.github.richardjwild.blather.io.Output;
import com.github.richardjwild.blather.datatransfer.UserRepository;
import com.github.richardjwild.blather.time.Clock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BlatherApplicationShould {

    @Mock
    private Input input;

    @Mock
    private Output output;

    private Blather blather;

    @Before
    public void initialize() {
        UserRepository userRepository = new UserRepository();
        InputParser inputParser = new InputParser(userRepository);
        AppController appController = new AppController();
        MessageRepository messageRepository = new MessageRepository();
        Clock clock = new Clock();
        CommandFactory commandFactory = new CommandFactory(appController, messageRepository, clock);
        CommandReader commandReader = new CommandReader(input, inputParser, commandFactory);
        blather = new Blather(appController, commandReader, output);
    }

    @Test
    public void display_a_users_posted_messages() {
        when(input.readLine())
                .thenReturn("Alice -> My first message")
                .thenReturn("Alice")
                .thenReturn("quit");

        blather.runApplication();

        InOrder inOrder = inOrder(output);
        inOrder.verify(output).writeLine("Welcome to Blather");
        inOrder.verify(output).writeLine("My first message (0 seconds ago)");
        inOrder.verify(output).writeLine("Bye!");
    }
}
