package com.github.richardjwild.blather.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository {

    private List<User> users = new ArrayList<>();

    public Optional<User> find(String name) {
        User toFind = new User(name);
        return users.stream()
                .filter(user -> user.equals(toFind))
                .findFirst();
    }

    public void save(User user) {
        users.add(user);
    }
}
