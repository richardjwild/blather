package com.github.richardjwild.blather;

import com.github.richardjwild.blather.command.CommandFactory;
import com.github.richardjwild.blather.parsing.CommandReader;
import com.github.richardjwild.blather.io.Input;
import com.github.richardjwild.blather.parsing.InputParser;
import com.github.richardjwild.blather.io.Output;
import com.github.richardjwild.blather.datatransfer.UserRepository;
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
        CommandFactory commandFactory = new CommandFactory();
        CommandReader commandReader = new CommandReader(input, inputParser, commandFactory);
        AppController appController = new AppController();
        blather = new Blather(appController, commandReader);
    }

    @Test
    public void display_a_users_posted_messages() {
        when(input.readLine())
                .thenReturn("Alice -> My first message")
                .thenReturn("Alice")
                .thenReturn("quit");

        blather.eventLoop();

        InOrder inOrder = inOrder(output);
        inOrder.verify(output).writeLine("My first message (0 seconds ago)");
        inOrder.verify(output).writeLine("Bye!");
    }
}
