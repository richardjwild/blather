package com.github.richardjwild.blather.persistence;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class JdbcRepository {
    protected final Connection connection;

    public JdbcRepository(Connection connection) {
        this.connection = connection;
    }

    protected void execute(String sql) {
        try (
                CallableStatement cstmt = connection.prepareCall(sql)) {
            cstmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
