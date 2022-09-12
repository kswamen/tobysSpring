package com.example.tobysspring.tobySrc.chapter5.b.test;

import com.example.tobysspring.tobySrc.chapter5.b.DaoFactory;
import com.example.tobysspring.tobySrc.chapter5.b.Level;
import com.example.tobysspring.tobySrc.chapter5.b.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DaoFactory.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserTest {
    User user;

    @BeforeAll
    public void setUp() {
        user = new User();
    }

    @Test
    public void upgradeLevel() {
        Level[] levels = Level.values();
        for (Level level : levels) {
            if (level.nextLevel() == null) {
                continue;
            }
            user.setLevel(level);
            user.upgradeLevel();
            assertEquals(user.getLevel(), level.nextLevel());
        }
    }

    @Test
    public void cannotUpgradeLevel() {
        Level[] levels = Level.values();
        for (Level level : levels) {
            if (level.nextLevel() != null) {
                continue;
            }
            user.setLevel(level);
            assertThrows(IllegalStateException.class, () -> user.upgradeLevel());
        }
    }
}
