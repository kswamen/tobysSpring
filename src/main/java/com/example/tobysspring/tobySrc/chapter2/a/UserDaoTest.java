package com.example.tobysspring.tobySrc.chapter2.a;

import com.example.tobysspring.tobySrc.chapter2.a.DaoFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserDaoTest {
    @Autowired
    UserDao dao;

    @Test
    public void addAndGet() throws SQLException, ClassNotFoundException {
        ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
        UserDao dao = context.getBean("userDao", UserDao.class);

        User user = new User();
        user.setId("abcd");
        user.setName("김석원");
        user.setPassword("1234");
        User user2 = dao.get("abcd");

        assertEquals(user2.getName(), user.getName());
        assertEquals(user2.getPassword(), user.getPassword());
    }
}
