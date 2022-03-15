package ru.otus.backend.db.mapper.impl;

import org.springframework.stereotype.Component;
import ru.otus.backend.db.entity.Category;
import ru.otus.backend.db.mapper.AbstractRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CategoryRowMapper extends AbstractRowMapper<Category> {

    @Override
    public Category mapRow(ResultSet rs, String columnPrefix) throws SQLException {
        return new Category(rs.getLong(getColumnNameWithPrefix("id", columnPrefix)),
                rs.getString(getColumnNameWithPrefix("name", columnPrefix)));
    }

    @Override
    protected String getDefaultColumnPrefix() {
        return "category_";
    }
}
