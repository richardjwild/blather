package com.github.richardjwild.blather.datatransfer;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class User {

    private final String name;
    private final Set<User> usersFollowing = new HashSet<>();

    public User(String name) {
        this.name = name;
    }

    public String name() {
        return name;
    }

    public Stream<User> following() {
        return usersFollowing.stream();
    }

    public void follow(User following) {
        usersFollowing.add(following);
    }

    @Override
    public boolean equals(Object o) {
        return o != null
                && (o instanceof User)
                && ((User) o).name.equals(this.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
