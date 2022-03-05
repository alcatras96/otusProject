package ru.otus.backend.db.resultsetextractor.impl;

import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;
import ru.otus.backend.db.entity.Role;
import ru.otus.backend.db.entity.User;
import ru.otus.backend.db.mapper.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@AllArgsConstructor
public class UserByLoginExtractor implements ResultSetExtractor<User> {

    private final RowMapper<User> userRowMapper;
    private final RowMapper<Role> roleRowMapper;

    @Override
    public User extractData(ResultSet rs) throws SQLException, DataAccessException {
        User user = null;
        if (rs.next()) {
            user = userRowMapper.mapRow(rs);
            user.setRole(roleRowMapper.mapRow(rs));
        }
        return user;
    }
}
