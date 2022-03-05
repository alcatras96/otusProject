package ru.otus.backend.db.mapper.impl;

import org.springframework.stereotype.Component;
import ru.otus.backend.db.entity.Subscription;
import ru.otus.backend.db.mapper.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class SubscriptionRowMapper implements RowMapper<Subscription> {

    @Override
    public Subscription mapRow(ResultSet rs) throws SQLException {
        return Subscription.builder()
                .id(rs.getLong("subscription_id"))
                .price(rs.getInt("subscription_price"))
                .name(rs.getString("subscription_name"))
                .imageUrl(rs.getString("subscription_image_url"))
                .description(rs.getString("subscription_description"))
                .build();
    }
}
