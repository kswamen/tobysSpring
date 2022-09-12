package com.example.tobysspring.tobySrc.chapter5.d;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

public class toby {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
        UserDaoJdbc dao = context.getBean("userDao", UserDaoJdbc.class);

        User user = new User();
        user.setId("abcd");
        user.setName("김석원");
        user.setPassword("1234");

        dao.add(user);
        System.out.println(user.getId() + " 등록 성공");

        User user2 = dao.get(user.getId());
        System.out.println(user2.getName());
        System.out.println(user.getPassword());

        System.out.println(user2.getId() + "  조회 성공");
    }
}
