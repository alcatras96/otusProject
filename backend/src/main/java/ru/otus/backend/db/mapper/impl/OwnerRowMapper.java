package ru.otus.backend.db.mapper.impl;

import org.springframework.stereotype.Component;
import ru.otus.backend.db.entity.Owner;
import ru.otus.backend.db.mapper.AbstractRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class OwnerRowMapper extends AbstractRowMapper<Owner> {

    @Override
    public Owner mapRow(ResultSet rs, String columnPrefix) throws SQLException {
        return new Owner(rs.getLong(getColumnNameWithPrefix("id", columnPrefix)),
                rs.getString(getColumnNameWithPrefix("name", columnPrefix)));
    }

    @Override
    protected String getDefaultColumnPrefix() {
        return "owner_";
    }
}
