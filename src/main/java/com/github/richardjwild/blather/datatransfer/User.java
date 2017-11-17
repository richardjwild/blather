package com.github.richardjwild.blather.datatransfer;

public class User {

    private final String name;

    public User(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
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
