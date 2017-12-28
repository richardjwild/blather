package com.github.richardjwild.blather.datatransfer;

import java.util.HashSet;
import java.util.Set;

public class User {

    private final String name;
    private final Set<User> usersFollowing = new HashSet<>();

    public User(String name) {
        this.name = name;
    }

    String name() {
        return name;
    }

    public Set<User> following() {
        return usersFollowing;
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
