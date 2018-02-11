package com.github.richardjwild.blather.command;

import com.github.richardjwild.blather.user.User;
import com.github.richardjwild.blather.user.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FollowCommandShould {

    @Mock
    private UserRepository userRepository;

    @Captor
    private ArgumentCaptor<User> userCaptor;

    @Test
    public void add_a_user_to_the_subjects_follow_list() {
        User follower = new User("follower");
        User following = new User("following");
        when(userRepository.find("follower")).thenReturn(Optional.of(follower));
        when(userRepository.find("following")).thenReturn(Optional.of(following));
        FollowCommand followCommand = new FollowCommand("follower", "following", userRepository);

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
        FollowCommand followCommand = new FollowCommand("follower", "following", userRepository);

        followCommand.execute();

        verify(userRepository).save(userCaptor.capture());
        User actualUser = userCaptor.getValue();
        assertThat(actualUser).isEqualTo(follower);
        assertThat(actualUser).isNotSameAs(follower);
    }

}