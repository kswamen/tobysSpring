package com.example.tobysspring.tobySrc.chapter2.b;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;


import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;

@Configuration
public class DaoFactory {
    @Bean
    public UserDao userDao() throws ClassNotFoundException {
        return new UserDao(dataSource());
    }

    @Bean
    public ConnectionMaker connectionMaker() {
        return new DConnectionMaker();
    }

    @Bean
    public DataSource dataSource() throws ClassNotFoundException {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();

        dataSource.setDriverClass((Class<? extends Driver>) Class.forName("com.mysql.jdbc.Driver"));
        dataSource.setUrl("jdbc:mysql://localhost:3306/toby");
        dataSource.setUsername("root");
        dataSource.setPassword("root");

        return dataSource;
    }
}
