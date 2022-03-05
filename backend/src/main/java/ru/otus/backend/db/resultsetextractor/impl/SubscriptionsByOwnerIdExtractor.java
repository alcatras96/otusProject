package ru.otus.backend.db.resultsetextractor.impl;

import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;
import ru.otus.backend.db.entity.Category;
import ru.otus.backend.db.entity.Subscription;
import ru.otus.backend.db.mapper.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class SubscriptionsByOwnerIdExtractor implements ResultSetExtractor<List<Subscription>> {

    private final RowMapper<Subscription> subscriptionRowMapper;
    private final RowMapper<Category> categoryRowMapper;

    @Override
    public List<Subscription> extractData(ResultSet rs) throws SQLException, DataAccessException {
        var subscriptions = new ArrayList<Subscription>();
        while (rs.next()) {
            Subscription subscription = subscriptionRowMapper.mapRow(rs);
            subscription.setOwnerId(rs.getLong("owner_id"));
            subscription.setCategory(categoryRowMapper.mapRow(rs));

            subscriptions.add(subscription);
        }
        return subscriptions;
    }
}
