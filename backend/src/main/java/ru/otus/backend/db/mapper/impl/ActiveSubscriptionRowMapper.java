package ru.otus.backend.db.mapper.impl;

import org.springframework.stereotype.Component;
import ru.otus.backend.db.entity.ActiveSubscription;
import ru.otus.backend.db.mapper.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ActiveSubscriptionRowMapper implements RowMapper<ActiveSubscription> {

    @Override
    public ActiveSubscription mapRow(ResultSet rs) throws SQLException {
        return ActiveSubscription.builder()
                .id(rs.getLong("active_subscription_id"))
                .customerId(rs.getLong("active_subscription_customer_id"))
                .lastEditDate(rs.getLong("active_subscription_last_edit_date"))
                .quantity(rs.getInt("active_subscription_quantity"))
                .build();
    }
}
