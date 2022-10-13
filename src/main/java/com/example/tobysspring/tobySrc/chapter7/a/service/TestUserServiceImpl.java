package com.example.tobysspring.tobySrc.chapter7.a.service;

import com.example.tobysspring.tobySrc.chapter7.a.User;
import com.example.tobysspring.tobySrc.chapter7.a.UserDao;
import com.example.tobysspring.tobySrc.chapter7.a.service.UserServiceImpl;
import com.example.tobysspring.tobySrc.chapter7.a.service.UserServiceTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class TestUserServiceImpl extends UserServiceImpl {
    private final String id = "madnite1";

    public TestUserServiceImpl(UserDao userDao) {
        super.setUserDao(userDao);
    }

    @Override
    protected void upgradeLevel(User user) {
        if (user.getId().equals(this.id)) {
            throw new TestUserServiceException();
        }
        super.upgradeLevel(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAll() {
        for (User user : super.getAll()) {
            super.update(user);
        }
        return null;
    }
}