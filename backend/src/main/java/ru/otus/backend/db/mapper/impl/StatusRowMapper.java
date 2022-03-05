package ru.otus.backend.db.mapper.impl;

import org.springframework.stereotype.Component;
import ru.otus.backend.db.entity.Status;
import ru.otus.backend.db.mapper.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class StatusRowMapper implements RowMapper<Status> {

    @Override
    public Status mapRow(ResultSet rs) throws SQLException {
        return new Status(rs.getString("status_name"));
    }
}
