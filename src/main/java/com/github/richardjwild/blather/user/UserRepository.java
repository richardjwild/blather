package com.github.richardjwild.blather.user;

import java.util.Optional;

public interface UserRepository {
    Optional<User> find(String name);

    void save(User user);
}
