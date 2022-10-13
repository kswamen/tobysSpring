package com.example.tobysspring.tobySrc.chapter7.a;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserService {
    void add(User user);

    User get(String id);
    List<User> getAll();

    void deleteAll();

    void update(User user);

    void upgradeLevels();
}
