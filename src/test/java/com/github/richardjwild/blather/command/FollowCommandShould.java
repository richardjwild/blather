package com.github.richardjwild.blather.command;

import com.github.richardjwild.blather.command.factory.FollowCommandFactory;
import com.github.richardjwild.blather.parsing.ParsedInput;
import com.github.richardjwild.blather.user.User;
import com.github.richardjwild.blather.user.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class FollowCommandShould {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ParsedInput parsedInput;

    @Captor
    private ArgumentCaptor<User> userCaptor;

    @InjectMocks
    private FollowCommandFactory factory;

    @Test
    public void add_a_user_to_the_subjects_follow_list() {
        User follower = new User("follower");
        User following = new User("following");
        when(userRepository.find("follower")).thenReturn(Optional.of(follower));
        when(userRepository.find("following")).thenReturn(Optional.of(following));
        Command followCommand = makeFollowCommand("follower", "following");

        followCommand.execute();

        verify(userRepository).save(follower);
        List<User> wallUsers = follower.wallUsers().collect(toList());
        assertThat(wallUsers).hasSize(2);
        assertThat(wallUsers).contains(following);
    }

    @Test
    public void create_follower_if_it_doesnt_already_exist() {
        User follower = new User("follower");
        User following = new User("following");
        when(userRepository.find("follower")).thenReturn(Optional.empty());
        when(userRepository.find("following")).thenReturn(Optional.of(following));
        Command followCommand = makeFollowCommand("follower", "following");

        followCommand.execute();

        verify(userRepository).save(userCaptor.capture());
        User actualUser = userCaptor.getValue();
        assertThat(actualUser).isEqualTo(follower);
        assertThat(actualUser).isNotSameAs(follower);
    }
    
    @Test
    public void not_allow_a_user_to_follow_themselves() {
        User follower = mock(User.class);
        when(userRepository.find("follower")).thenReturn(Optional.of(follower));
        Command followCommand = makeFollowCommand("follower", "follower");

        followCommand.execute();
        verify(follower, never()).follow(follower);
        verify(userRepository, never()).save(follower);
    }

    private Command makeFollowCommand(String follower, String following) {
        when(parsedInput.followCommandActor()).thenReturn(follower);
        when(parsedInput.followCommandSubject()).thenReturn(following);
        return factory.makeCommandFor(parsedInput);
    }
}