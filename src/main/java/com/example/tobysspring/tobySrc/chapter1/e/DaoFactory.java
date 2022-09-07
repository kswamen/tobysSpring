package com.example.tobysspring.tobySrc.chapter1.e;

public class DaoFactory {
    public UserDao userDao() {
        return new UserDao(connectionMaker());
    }


    public ConnectionMaker connectionMaker() {
        return new DConnectionMaker();
    }
}
