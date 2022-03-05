package ru.otus.backend.db.resultsetextractor.impl;

import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;
import ru.otus.backend.db.entity.ActiveSubscription;
import ru.otus.backend.db.entity.Subscription;
import ru.otus.backend.db.mapper.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class ActiveSubscriptionsByCustomerIdExtractor implements ResultSetExtractor<List<ActiveSubscription>> {

    private final RowMapper<ActiveSubscription> activeSubscriptionRowMapper;
    private final RowMapper<Subscription> subscriptionRowMapper;

    @Override
    public List<ActiveSubscription> extractData(ResultSet rs) throws SQLException, DataAccessException {
        var activeSubscriptions = new ArrayList<ActiveSubscription>();
        while (rs.next()) {
            ActiveSubscription activeSubscription = activeSubscriptionRowMapper.mapRow(rs);
            activeSubscription.setSubscription(subscriptionRowMapper.mapRow(rs));
            activeSubscriptions.add(activeSubscription);
        }
        return activeSubscriptions;
    }
}
