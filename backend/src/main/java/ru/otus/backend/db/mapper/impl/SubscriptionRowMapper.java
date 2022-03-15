package ru.otus.backend.db.mapper.impl;

import org.springframework.stereotype.Component;
import ru.otus.backend.db.entity.Subscription;
import ru.otus.backend.db.mapper.AbstractRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class SubscriptionRowMapper extends AbstractRowMapper<Subscription> {

    @Override
    public Subscription mapRow(ResultSet rs, String columnPrefix) throws SQLException {
        return Subscription.builder()
                .id(rs.getLong(getColumnNameWithPrefix("id", columnPrefix)))
                .price(rs.getInt(getColumnNameWithPrefix("price", columnPrefix)))
                .name(rs.getString(getColumnNameWithPrefix("name", columnPrefix)))
                .imageUrl(rs.getString(getColumnNameWithPrefix("image_url", columnPrefix)))
                .description(rs.getString(getColumnNameWithPrefix("description", columnPrefix)))
                .build();
    }

    @Override
    protected String getDefaultColumnPrefix() {
        return "subscription_";
    }
}
