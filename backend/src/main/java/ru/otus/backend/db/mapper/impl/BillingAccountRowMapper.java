package ru.otus.backend.db.mapper.impl;

import org.springframework.stereotype.Component;
import ru.otus.backend.db.entity.BillingAccount;
import ru.otus.backend.db.mapper.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class BillingAccountRowMapper implements RowMapper<BillingAccount> {

    @Override
    public BillingAccount mapRow(ResultSet rs) throws SQLException {
        long billing_account_id = rs.getLong("billing_account_id");
        if (billing_account_id == 0) {
            return null;
        }

        return new BillingAccount(billing_account_id,
                rs.getString("billing_account_number"),
                rs.getInt("billing_account_cvv"),
                rs.getInt("billing_account_balance"));
    }
}
