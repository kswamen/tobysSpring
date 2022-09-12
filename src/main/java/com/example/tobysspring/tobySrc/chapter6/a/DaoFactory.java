package com.example.tobysspring.tobySrc.chapter6.a;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.mail.MailSender;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.sql.Driver;

@Configuration
public class DaoFactory {
    @Bean
    public UserDao userDaoJdbc() {
        return new UserDaoJdbc();
    }

    @Bean
    public UserService userService() {
        return new UserServiceTx();
    }

    @Bean
    public UserServiceImpl userServiceImpl() {
        return new UserServiceImpl();
    }

    @Bean
    public JdbcContext jdbcContext() {
        JdbcContext jdbcContext = new JdbcContext();
        jdbcContext.setDataSource(dataSource());
        return jdbcContext;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource());
        return jdbcTemplate;
    }

    @Bean
    public ConnectionMaker connectionMaker() {
        return new DConnectionMaker();
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    public DataSource dataSource() {
        try {
            SimpleDriverDataSource dataSource = new SimpleDriverDataSource();

            dataSource.setDriverClass((Class<? extends Driver>) Class.forName("com.mysql.jdbc.Driver"));
            dataSource.setUrl("jdbc:mysql://localhost:3306/toby");
            dataSource.setUsername("root");
            dataSource.setPassword("root");

            return dataSource;
        } catch (Exception e) {
            System.out.println("Class not found Exception");
            e.printStackTrace();
        }
        return null;
    }

    @Bean
    public MailSender mailSender() {
        return new DummyMailSender();
    }
}
