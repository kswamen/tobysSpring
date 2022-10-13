package com.example.tobysspring.tobySrc.chapter7.a;

import com.example.tobysspring.tobySrc.chapter7.a.exception.DuplicateUserIdException;
import com.example.tobysspring.tobySrc.chapter7.a.service.SqlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDaoJdbc implements UserDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private SqlService sqlService;

    private final RowMapper<User> userMapper =
            (rs, rowNum) -> {
                User user = new User();
                user.setId(rs.getString("id"));
                user.setName(rs.getString("name"));
                user.setPassword(rs.getString("password"));
                user.setLevel(Level.valueOf(rs.getInt("level")));
                user.setLogin(rs.getInt("login"));
                user.setRecommend(rs.getInt("recommend"));
                return user;
            };


    public void add(final User user) throws DuplicateUserIdException {
        try {
            this.jdbcTemplate.update(
                    this.sqlService.getSql("userAdd"),
                    user.getId(), user.getName(), user.getPassword(),
                    user.getLevel().intValue(), user.getLogin(), user.getRecommend());
        } catch (DataAccessException e) {
            throw new DuplicateUserIdException(e);
        }
    }

    public User get(String id) {
        return this.jdbcTemplate.queryForObject(this.sqlService.getSql("userGet"),
                new Object[] {id},
                this.userMapper
        );
    }

    public List<User> getAll() {
        return this.jdbcTemplate.query(this.sqlService.getSql("userGetAll"),
                this.userMapper
        );
    }

    public void deleteAll() {
        this.jdbcTemplate.update(
                con -> con.prepareStatement(this.sqlService.getSql("userDeleteAll"))
        );
    }

    public int getCount() {
        return this.jdbcTemplate.queryForObject(this.sqlService.getSql("userGetCount"), Integer.class);
    }

    @Override
    public void update(User user) {
        this.jdbcTemplate.update(
                this.sqlService.getSql("userUpdate"), user.getName(), user.getPassword(),
                user.getLevel().intValue(), user.getLogin(), user.getRecommend(), user.getId()
        );
    }
}

