package ru.otus.backend.db.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface RowMapper<T> {

    T mapRow(ResultSet rs) throws SQLException;

    T mapRow(ResultSet rs, String columnPrefix) throws SQLException;
}
