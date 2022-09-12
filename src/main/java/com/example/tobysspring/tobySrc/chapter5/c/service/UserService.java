package com.example.tobysspring.tobySrc.chapter5.c.service;

import com.example.tobysspring.tobySrc.chapter5.c.Level;
import com.example.tobysspring.tobySrc.chapter5.c.User;
import com.example.tobysspring.tobySrc.chapter5.c.UserDao;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserService {
    public static final int MIN_LOGOUT_FOR_SILVER = 50;
    public static final int MIN_RECOMMEND_FOR_GOLD = 30;
    UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public UserService() {

    }
//    public void upgradeLevels() {
//        List<User> users = userDao.getAll();
//        for (User user : users) {
//            Boolean changed = null;
//            if (user.getLevel() == Level.BASIC && user.getLogin() >= 50) {
//                user.setLevel(Level.SILVER);
//                changed = true;
//            } else if (user.getLevel() == Level.SILVER && user.getRecommend() >= 30) {
//                user.setLevel(Level.GOLD);
//                changed = true;
//            } else if (user.getLevel() == Level.GOLD) {
//                changed = false;
//            } else {
//                changed = false;
//            }
//
//            if(changed) {
//                userDao.update(user);
//            }
//        }
//    }

    private boolean canUpgradeLevel(User user) {
        Level currentLevel = user.getLevel();
        switch (currentLevel) {
            case BASIC:
                return (user.getLogin() >= MIN_LOGOUT_FOR_SILVER);
            case SILVER:
                return (user.getRecommend() >= MIN_RECOMMEND_FOR_GOLD);
            case GOLD:
                return false;
            default:
                throw new IllegalArgumentException("unknown level: " + currentLevel);
        }
    }

    protected void upgradeLevel(User user) {
        user.upgradeLevel();
        userDao.update(user);
    }

    public void upgradeLevels() {
        List<User> users = userDao.getAll();
        for (User user : users) {
            if (canUpgradeLevel(user)) {
                upgradeLevel(user);
            }
        }
    }

    public void add(User user) {
        if (user.getLevel() == null) {
            user.setLevel(Level.BASIC);
        }
        userDao.add(user);
    }

}
