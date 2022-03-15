package ru.otus.backend.db.resultsetextractor.impl;

import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;
import ru.otus.backend.db.entity.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class AllActiveSubscriptionsForRecalculationExtractor implements ResultSetExtractor<List<ActiveSubscription>> {

    @Override
    public List<ActiveSubscription> extractData(ResultSet rs) throws SQLException, DataAccessException {
        var activeSubscriptions = new ArrayList<ActiveSubscription>();
        while (rs.next()) {
            ActiveSubscription activeSubscription = getActiveSubscription(rs);
            activeSubscription.setCustomer(getCustomer(rs));
            activeSubscription.setSubscription(getSubscription(rs));

            activeSubscriptions.add(activeSubscription);
        }
        return activeSubscriptions;
    }

    private Subscription getSubscription(ResultSet rs) throws SQLException {
        return Subscription.builder()
                .price(rs.getInt("subscription_price"))
                .owner(getOwner(rs))
                .build();
    }

    private Owner getOwner(ResultSet rs) throws SQLException {
        return Owner.builder()
                .billingAccount(getOwnerBillingAccount(rs))
                .build();
    }

    private BillingAccount getOwnerBillingAccount(ResultSet rs) throws SQLException {
        return BillingAccount.builder()
                .id(rs.getLong("owner_billing_account_id"))
                .balance(rs.getInt("owner_billing_account_balance"))
                .build();
    }

    private Customer getCustomer(ResultSet rs) throws SQLException {
        return Customer.builder()
                .id(rs.getLong("customer_id"))
                .statusId(rs.getLong("customer_status_id"))
                .billingAccount(getCustomerBillingAccount(rs))
                .build();
    }

    private BillingAccount getCustomerBillingAccount(ResultSet rs) throws SQLException {
        return BillingAccount.builder()
                .id(rs.getLong("customer_billing_account_id"))
                .balance(rs.getInt("customer_billing_account_balance"))
                .build();
    }

    private ActiveSubscription getActiveSubscription(ResultSet rs) throws SQLException {
        return ActiveSubscription.builder()
                .id(rs.getLong("active_subscription_id"))
                .quantity(rs.getInt("active_subscription_quantity"))
                .lastEditDate(rs.getLong("active_subscription_last_edit_date"))
                .build();
    }
}
