package com.developersstack.medex.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    //1 rule(private static class reference)
    private static final DbConnection dbConnection = null;
    private final Connection connection;

    //2 rule constructor private
    private DbConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/medex",
                "root",
                "1234"
        );
    }

    // rule 3 get instance method
    public static DbConnection getInstance() throws SQLException, ClassNotFoundException {
        return dbConnection == null ? new DbConnection() : dbConnection;
    }

    public Connection getConnection() {
        return connection;
    }
}
