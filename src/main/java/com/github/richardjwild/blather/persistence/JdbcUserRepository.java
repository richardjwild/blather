package com.github.richardjwild.blather.persistence;

import com.github.richardjwild.blather.user.User;
import com.github.richardjwild.blather.user.UserRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcUserRepository extends JdbcRepository implements UserRepository {

    public JdbcUserRepository(Connection connection) {
        super(connection);
    }

    @Override
    public Optional<User> find(String name) {
        String sql = String.format("SELECT name FROM users WHERE name='%s'", name);
        try (
                PreparedStatement pstmt = connection.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                User user = new User(rs.getString(1));
                following(name).forEach(following -> user.follow(new User(following)));
                return Optional.of(user);
            }
            else
                return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private List<String> following(String name) {
        String sql = String.format("SELECT following_name FROM followings " +
                "WHERE follower_name='%s'", name);
        List<String> following = new ArrayList<>();
        try (PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                following.add(rs.getString(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        return following;
    }

    @Override
    public void save(User user) {
        if (!find(user.name()).isPresent()) {
            execute(String.format("INSERT INTO users (name) VALUES ('%s')", user.name()));
        }
        execute(String.format("DELETE followings WHERE follower_name = '%s'", user.name()));
        user.following().forEach(following -> {
            String sql = String.format("INSERT INTO followings (follower_name, following_name) " +
                            "VALUES ('%s','%s')",
                    user.name(), following.name());
            execute(sql);
        });
    }

}
