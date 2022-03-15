package ru.otus.backend.db.mapper.impl;

import org.springframework.stereotype.Component;
import ru.otus.backend.db.entity.BasketItem;
import ru.otus.backend.db.mapper.AbstractRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class BasketItemRowMapper extends AbstractRowMapper<BasketItem> {

    @Override
    public BasketItem mapRow(ResultSet rs, String columnPrefix) throws SQLException {
        return new BasketItem(rs.getLong(getColumnNameWithPrefix("id", columnPrefix)),
                rs.getInt(getColumnNameWithPrefix("quantity", columnPrefix)),
                rs.getLong(getColumnNameWithPrefix("customer_id", columnPrefix)));
    }

    @Override
    protected String getDefaultColumnPrefix() {
        return "basket_item_";
    }
}
