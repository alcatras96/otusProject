package ru.otus.backend.db.resultsetextractor.impl;

import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;
import ru.otus.backend.db.entity.BasketItem;
import ru.otus.backend.db.entity.Subscription;
import ru.otus.backend.db.mapper.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class BasketItemsByCustomerIdExtractor implements ResultSetExtractor<List<BasketItem>> {

    private final RowMapper<BasketItem> basketItemRowMapper;
    private final RowMapper<Subscription> subscriptionRowMapper;

    @Override
    public List<BasketItem> extractData(ResultSet rs) throws SQLException, DataAccessException {
        var basketItems = new ArrayList<BasketItem>();
        while (rs.next()) {
            BasketItem basketItem = basketItemRowMapper.mapRow(rs);
            basketItem.setSubscription(subscriptionRowMapper.mapRow(rs));
            basketItems.add(basketItem);
        }
        return basketItems;
    }
}
