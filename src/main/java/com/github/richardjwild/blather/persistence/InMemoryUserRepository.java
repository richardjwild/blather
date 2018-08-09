package com.github.richardjwild.blather.persistence;

import com.github.richardjwild.blather.user.User;
import com.github.richardjwild.blather.user.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryUserRepository implements UserRepository {

    private List<User> users = new ArrayList<>();

    @Override
    public Optional<User> find(String name) {
        User toFind = new User(name);
        return users.stream()
                .filter(user -> user.equals(toFind))
                .findFirst();
    }

    @Override
    public void save(User user) {
        users.add(user);
    }
}
