package com.example.tobysspring.tobySrc.chapter1.d;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SimpleConnectionMaker {
    public Connection makeNewConnection() throws ClassNotFoundException,
            SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection c = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/toby", "root", "root"
        );
        return c;
    }
}
