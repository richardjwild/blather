package com.github.richardjwild.blather.persistence;

import com.github.richardjwild.blather.message.Message;
import com.github.richardjwild.blather.message.MessageRepository;
import com.github.richardjwild.blather.user.User;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class JdbcMessageRepository extends JdbcRepository implements MessageRepository {

    public JdbcMessageRepository(Connection connection) {
        super(connection);
    }

    @Override
    public void postMessage(User recipient, Message message) {
        execute(String.format("INSERT INTO messages (user_name, text, \"timestamp\") " +
                "VALUES ('%s','%s',SYSDATE)", recipient.name(), message.text()));
    }

    @Override
    public Stream<Message> allMessagesPostedTo(User recipient) {
        String sql = String.format("SELECT text, \"timestamp\" FROM messages " +
                "WHERE user_name = '%s'", recipient.name());
        List<Message> messages = new ArrayList<>();
        try (
                PreparedStatement pstmt = connection.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()
        ) {
            while (rs.next()) {
                messages.add(new Message(recipient, rs.getString(1), rs.getTimestamp(2).toInstant()));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        return messages.stream();
    }

}
