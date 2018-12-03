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
        execute("INSERT INTO messages (user_name, text, \"timestamp\") VALUES (?,?,SYSDATE)",
                recipient.name(), message.text());
    }

    @Override
    public Stream<Message> allMessagesPostedTo(User recipient) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            try {
                pstmt = connection.prepareStatement(
                        "SELECT text, \"timestamp\" FROM messages WHERE user_name = ?");
                pstmt.setString(1, recipient.name());
                rs = pstmt.executeQuery();
                List<Message> messages = new ArrayList<>();
                while (rs.next()) {
                    messages.add(new Message(recipient, rs.getString(1), rs.getTimestamp(2).toInstant()));
                }
                return messages.stream();
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

}