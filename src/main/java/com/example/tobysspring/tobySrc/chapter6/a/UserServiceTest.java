package com.example.tobysspring.tobySrc.chapter6.a;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.PlatformTransactionManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.example.tobysspring.tobySrc.chapter5.b.service.UserService.MIN_LOGOUT_FOR_SILVER;
import static com.example.tobysspring.tobySrc.chapter5.b.service.UserService.MIN_RECOMMEND_FOR_GOLD;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DaoFactory.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceTest {
    @Autowired
    private UserService userService;
    @Autowired
    private UserServiceImpl userServiceImpl;
    @Autowired
    private UserDao userDao;
    @Autowired
    private PlatformTransactionManager transactionManager;
    @Autowired
    private MailSender mailSender;
    List<User> users;

    @BeforeAll
    public void setUp() {
        users = Arrays.asList(
                new User("bumjin", "박범진", "p1", null, Level.BASIC, MIN_LOGOUT_FOR_SILVER - 1, 0),
                new User("joytouch", "강명성", "p2", null, Level.BASIC, MIN_LOGOUT_FOR_SILVER, 0),
                new User("erwins", "신승한", "p3", null, Level.SILVER, 60, MIN_RECOMMEND_FOR_GOLD - 1),
                new User("madnite1", "이상호", "p4", null, Level.SILVER, 60, MIN_RECOMMEND_FOR_GOLD),
                new User("green", "오민규", "p5", null, Level.GOLD, 100, Integer.MAX_VALUE)
        );
    }

    @Test
    public void upgradeLevels() throws SQLException {
        UserServiceImpl userServiceImpl = new UserServiceImpl();

        MockUserDao mockUserDao = new MockUserDao(this.users);
        userServiceImpl.setUserDao(mockUserDao);
        MockMailSender mockMailSender = new MockMailSender();
        userServiceImpl.setMailSender(mockMailSender);

        userServiceImpl.upgradeLevels();

        List<User> updated = mockUserDao.getUpdated();
        assertEquals(updated.size(), 2);
        checkUserAndLevel(updated.get(0), "joytouch", Level.SILVER);
        checkUserAndLevel(updated.get(1), "madnite1", Level.GOLD);

        List<String> request = mockMailSender.getRequests();
        assertEquals(request.size(), 2);
        assertEquals(request.get(0), users.get(1).getEmail());
        assertEquals(request.get(1), users.get(3).getEmail());
    }

    private void checkUserAndLevel(User updated, String expectedId, Level expectedLevel) {
        assertEquals(updated.getId(), expectedId);
        assertEquals(updated.getLevel(), expectedLevel);
    }

    private void checkLevel(User user, Level expectedLevel) {
        User userUpdate = userDao.get(user.getId());
        assertEquals(userUpdate.getLevel(), expectedLevel);
    }

    private void checkLevelUpgraded(User user, boolean upgraded) {
        User userUpdate = userDao.get(user.getId());
        if (upgraded) {
            assertEquals(userUpdate.getLevel(), user.getLevel().nextLevel());
        }
        else {
            assertEquals(userUpdate.getLevel(), user.getLevel());
        }
    }

    @Test
    public void add() {
        userDao.deleteAll();

        User userWithLevel = users.get(4);
        User userWithoutLevel = users.get(0);
        userWithoutLevel.setLevel(null);

        userService.add(userWithLevel);
        userService.add(userWithoutLevel);

        User userWithLevelRead = userDao.get(userWithLevel.getId());
        User userWithoutLevelRead = userDao.get(userWithoutLevel.getId());

        assertEquals(userWithLevelRead.getLevel(), userWithLevel.getLevel());
        assertEquals(userWithoutLevelRead.getLevel(), Level.BASIC);
    }
    
    @Test
    public void upgradeAllOrNothing() {
        TestUserService testUserService = new TestUserService(users.get(3).getId(), this.userDao);
        testUserService.setUserDao(userDao);
        testUserService.setMailSender(mailSender);

        UserServiceTx txUserService = new UserServiceTx();
        txUserService.setTransactionManager(transactionManager);
        txUserService.setUserService(testUserService);

        userDao.deleteAll();
        for (User user : users) {
            userDao.add(user);
        }

        try {
            txUserService.upgradeLevels();
            fail("TestUserServiceException expected");
        } catch (TestUserServiceException e) {

        }

        checkLevelUpgraded(users.get(1), false);
    }

    static class TestUserService extends UserServiceImpl {
        private final String id;

        public TestUserService(String id, UserDao userDao) {
            super.setUserDao(userDao);
            this.id = id;
        }

        @Override
        protected void upgradeLevel(User user) {
            if (user.getId().equals(this.id)) {
                throw new TestUserServiceException();
            }
            super.upgradeLevel(user);
        }
    }

    static class MockMailSender implements MailSender {
        private final List<String> requests = new ArrayList<>();
        public List<String> getRequests() {
            return requests;
        }

        @Override
        public void send(SimpleMailMessage mailMessage) throws MailException {
            requests.add(Objects.requireNonNull(mailMessage.getTo())[0]);
        }

        @Override
        public void send(SimpleMailMessage... mailMessages) throws MailException {
        }
    }

    static class MockUserDao implements UserDao {
        private final List<User> users;
        private final List<User> updated = new ArrayList<>();

        private MockUserDao(List<User> users) {
            this.users = users;
        }

        public List<User> getUpdated() {
            return this.updated;
        }

        @Override
        public void add(User user) {
            throw new UnsupportedOperationException();
        }

        @Override
        public User get(String id) {
            throw new UnsupportedOperationException();
        }

        @Override
        public List<User> getAll() {
            return this.users;
        }

        @Override
        public void deleteAll() {
            throw new UnsupportedOperationException();
        }

        @Override
        public int getCount() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void update(User user) {
            updated.add(user);
        }
    }

    static class TestUserServiceException extends RuntimeException {
    }
}
