package com.example.tobysspring.tobySrc.chapter7.a;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

class TestUserServiceImpl extends UserServiceImpl {
    private final String id = "madnite1";

    public TestUserServiceImpl(UserDao userDao) {
        super.setUserDao(userDao);
    }

    @Override
    protected void upgradeLevel(User user) {
        if (user.getId().equals(this.id)) {
            throw new UserServiceTest.TestUserServiceException();
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