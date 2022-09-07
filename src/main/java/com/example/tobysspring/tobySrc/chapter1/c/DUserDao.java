package com.example.tobysspring.tobySrc.chapter1.c;

import java.sql.Connection;
import java.sql.SQLException;

public class DUserDao extends UserDao {
    public Connection getConnection() throws ClassNotFoundException,
            SQLException {
        // D사 DB connection 생성 코드
        return null;
    }
}
