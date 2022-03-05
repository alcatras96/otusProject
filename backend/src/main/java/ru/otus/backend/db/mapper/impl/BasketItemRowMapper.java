package ru.otus.backend.db.mapper.impl;

import org.springframework.stereotype.Component;
import ru.otus.backend.db.entity.BasketItem;
import ru.otus.backend.db.mapper.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class BasketItemRowMapper implements RowMapper<BasketItem> {

    @Override
    public BasketItem mapRow(ResultSet rs) throws SQLException {
        return new BasketItem(rs.getLong("basket_item_id"),
                rs.getInt("basket_item_quantity"),
                rs.getLong("basket_item_customer_id"));
    }
}
