package com.example.tobysspring.tobySrc.chapter3.g;

import com.example.tobysspring.tobySrc.chapter3.g.strategy.StatementStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;


    private final RowMapper<User> userMapper =
            (rs, rowNum) -> {
                User user = new User();
                user.setId(rs.getString("id"));
                user.setName(rs.getString("name"));
                user.setPassword(rs.getString("password"));
                return user;
            };


    public void add(final User user) {
        this.jdbcTemplate.update("insert into users(id, name, password) values(?, ?, ?)",
                user.getId(), user.getName(), user.getPassword());
    }

    public User get(String id) throws ClassNotFoundException, SQLException {
        return this.jdbcTemplate.queryForObject("select * from users where id = ?",
                new Object[] {id},
                this.userMapper
        );
    }

    public List<User> getAll() {
        return this.jdbcTemplate.query("select * from users order by id",
                this.userMapper
        );
    }

    public void deleteAll() {
        this.jdbcTemplate.update(
                con -> con.prepareStatement("delete from users")
        );
    }

    public int getCount() throws SQLException {
        return this.jdbcTemplate.queryForObject("select count(*) from users", Integer.class);
    }
}

