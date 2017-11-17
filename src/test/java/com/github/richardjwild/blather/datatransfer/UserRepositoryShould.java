package com.github.richardjwild.blather.datatransfer;

import org.junit.Test;

import java.util.Optional;

import static org.fest.assertions.Assertions.assertThat;

public class UserRepositoryShould {

    private UserRepository userRepository = new UserRepository();

    @Test
    public void return_empty_when_user_not_found() {
        Optional<User> result = userRepository.find("will_not_be_found");

        assertThat(result.isPresent()).isFalse();
    }

    @Test
    public void return_stored_user_when_user_is_found() {
        String userName = "will_be_found";
        User expectedUser = new User(userName);
        userRepository.save(expectedUser);

        Optional<User> actualUser = userRepository.find(userName);

        assertThat(actualUser.isPresent()).isTrue();
        assertThat(actualUser.get()).isSameAs(expectedUser);
    }

    @Test
    public void not_store_duplicate_users_when_the_same_user_saved_twice() {
        String userName = "user_name";
        User user = new User(userName);
        userRepository.save(user);
        User userWithSameName = new User(userName);

        userRepository.save(userWithSameName);

        Optional<User> actualUser = userRepository.find(userName);
        assertThat(actualUser.get()).isSameAs(user);
        assertThat(actualUser.get()).isNotSameAs(userWithSameName);
    }
}