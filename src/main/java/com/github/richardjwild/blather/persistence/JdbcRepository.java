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

    protected void execute(String dml, String... parameters) {
        CallableStatement cstmt = null;
        try {
            try {
                cstmt = connection.prepareCall(dml);
                for (int i = 0; i < parameters.length; i++)
                    cstmt.setString(i + 1, parameters[i]);
                cstmt.execute();
            } finally {
                if (cstmt != null)
                    cstmt.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
