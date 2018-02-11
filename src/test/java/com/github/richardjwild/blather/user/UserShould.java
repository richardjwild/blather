package com.github.richardjwild.blather.user;

import org.junit.Test;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.fest.assertions.Assertions.assertThat;

public class UserShould {

    @Test
    public void follow_any_given_user_only_once() {
        User follower = new User("follower");
        User following = new User("following");

        follower.follow(following);
        follower.follow(following);

        List<User> usersFollowing = follower.following().collect(toList());
        assertThat(usersFollowing).hasSize(1);
        assertThat(usersFollowing).containsExactly(following);
    }
}