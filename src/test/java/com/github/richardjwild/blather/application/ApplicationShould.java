package com.github.richardjwild.blather.application;

import com.github.richardjwild.blather.io.Output;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.inOrder;

@RunWith(MockitoJUnitRunner.class)
public class ApplicationShould {

    @Mock
    private Output output;

    @Mock
    private EventLoop eventLoop;

    private Application application;

    @Before
    public void initialize() {
        application = new Application(eventLoop, output);
    }

    @Test
    public void print_a_welcome_message_then_start_the_application_then_print_an_exit_message() {
        application.run();

        InOrder inOrder = inOrder(output, eventLoop);
        inOrder.verify(output).writeLine("Welcome to Blather");
        inOrder.verify(eventLoop).start();
        inOrder.verify(output).writeLine("Bye!");
    }
}
