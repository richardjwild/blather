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
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            try {
                pstmt = connection.prepareStatement("SELECT name FROM users WHERE name=?");
                pstmt.setString(1, name);
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    User user = new User(rs.getString(1));
                    following(name).forEach(following -> user.follow(new User(following)));
                    return Optional.of(user);
                } else
                    return Optional.empty();
            } finally {
                if (rs != null)
                    rs.close();
                if (pstmt != null)
                    pstmt.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private List<String> following(String name) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            try {
                pstmt = connection.prepareStatement(
                        "SELECT following_name FROM followings WHERE follower_name=?");
                pstmt.setString(1, name);
                rs = pstmt.executeQuery();
                List<String> following = new ArrayList<>();
                while (rs.next()) {
                    following.add(rs.getString(1));
                }
                return following;
            } finally {
                if (rs != null)
                    rs.close();
                if (pstmt != null)
                    pstmt.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public void save(User user) {
        if (!find(user.name()).isPresent()) {
            execute("INSERT INTO users (name) VALUES (?)", user.name());
        }
        execute("DELETE followings WHERE follower_name = ?", user.name());
        user.following().forEach(following ->
                execute("INSERT INTO followings (follower_name, following_name) VALUES (?,?)",
                        user.name(), following.name()));
    }

}
