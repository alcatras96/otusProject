package ru.otus.backend.db.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class AbstractRowMapper<T> implements RowMapper<T> {

    protected abstract String getDefaultColumnPrefix();

    protected String getColumnNameWithPrefix(String columnName, String prefix) {
        return prefix + columnName;
    }

    @Override
    public T mapRow(ResultSet rs) throws SQLException {
        return mapRow(rs, getDefaultColumnPrefix());
    }
}
