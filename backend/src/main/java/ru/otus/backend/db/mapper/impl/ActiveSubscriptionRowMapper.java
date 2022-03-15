package ru.otus.backend.db.mapper.impl;

import org.springframework.stereotype.Component;
import ru.otus.backend.db.entity.ActiveSubscription;
import ru.otus.backend.db.mapper.AbstractRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ActiveSubscriptionRowMapper extends AbstractRowMapper<ActiveSubscription> {

    @Override
    public ActiveSubscription mapRow(ResultSet rs, String columnPrefix) throws SQLException {
        return ActiveSubscription.builder()
                .id(rs.getLong(getColumnNameWithPrefix("id", columnPrefix)))
                .customerId(rs.getLong(getColumnNameWithPrefix("customer_id", columnPrefix)))
                .lastEditDate(rs.getLong(getColumnNameWithPrefix("last_edit_date", columnPrefix)))
                .quantity(rs.getInt(getColumnNameWithPrefix("quantity", columnPrefix)))
                .build();
    }

    @Override
    protected String getDefaultColumnPrefix() {
        return "active_subscription_";
    }
}
