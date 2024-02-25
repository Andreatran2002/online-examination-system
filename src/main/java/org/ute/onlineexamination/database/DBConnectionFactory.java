package org.ute.onlineexamination.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionFactory {
    private static final String DATABASE_URL = "jdbc:mysql://localhost:6033/swing_demo?serverTimezone=UTC";
    private static final String DATABASE_USER_NAME = "root";
    private static final String DATABASE_PASSWORD = "admin";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL, DATABASE_USER_NAME, DATABASE_PASSWORD);
    }
}