package com.github.richardjwild.blather.user;

import org.junit.Test;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.fest.assertions.Assertions.assertThat;

public class UserShould {

    @Test
    public void follow_any_given_user_only_once() {
        User follower = new User("follower");
        User following = new User("following");

        follower.follow(following);
        follower.follow(following);

        Stream<User> wallUsers = follower.wallUsers();

        assertThat(wallUsers.filter(user -> user.equals(following)).count())
                .isEqualTo(1);
    }

    @Test
    public void include_itself_in_its_wall_users() {
        User follower = new User("follower");
        User following = new User("following");

        follower.follow(following);

        List<User> usersFollowing = follower.wallUsers().collect(toList());

        assertThat(usersFollowing).hasSize(2);
        assertThat(usersFollowing).contains(follower);
        assertThat(usersFollowing).contains(following);
    }
}