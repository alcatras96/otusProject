package ru.otus.backend.db.mapper.impl;

import org.springframework.stereotype.Component;
import ru.otus.backend.db.entity.Status;
import ru.otus.backend.db.mapper.AbstractRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class StatusRowMapper extends AbstractRowMapper<Status> {

    @Override
    public Status mapRow(ResultSet rs, String columnPrefix) throws SQLException {
        return new Status(rs.getString(getColumnNameWithPrefix("name", columnPrefix)));
    }

    @Override
    protected String getDefaultColumnPrefix() {
        return "status_";
    }
}
