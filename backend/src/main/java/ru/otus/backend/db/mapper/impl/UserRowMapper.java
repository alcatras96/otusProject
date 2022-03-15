package ru.otus.backend.db.mapper.impl;

import org.springframework.stereotype.Component;
import ru.otus.backend.db.entity.User;
import ru.otus.backend.db.mapper.AbstractRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserRowMapper extends AbstractRowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, String columnPrefix) throws SQLException {
        return new User(rs.getLong(getColumnNameWithPrefix("id", columnPrefix)),
                rs.getString(getColumnNameWithPrefix("login", columnPrefix)),
                rs.getString(getColumnNameWithPrefix("email", columnPrefix)),
                rs.getString(getColumnNameWithPrefix("password", columnPrefix)));
    }

    @Override
    protected String getDefaultColumnPrefix() {
        return "user_";
    }
}
