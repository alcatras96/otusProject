package ru.otus.backend.db.mapper.impl;

import org.springframework.stereotype.Component;
import ru.otus.backend.db.entity.Role;
import ru.otus.backend.db.mapper.AbstractRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class RoleRowMapper extends AbstractRowMapper<Role> {

    @Override
    public Role mapRow(ResultSet rs, String columnPrefix) throws SQLException {
        return new Role(rs.getLong(getColumnNameWithPrefix("id", columnPrefix)),
                rs.getString(getColumnNameWithPrefix("name", columnPrefix)));
    }

    @Override
    protected String getDefaultColumnPrefix() {
        return "role_";
    }
}
