package ru.otus.backend.db.mapper.impl;

import org.springframework.stereotype.Component;
import ru.otus.backend.db.entity.BillingAccount;
import ru.otus.backend.db.mapper.AbstractRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class BillingAccountRowMapper extends AbstractRowMapper<BillingAccount> {

    @Override
    public BillingAccount mapRow(ResultSet rs, String columnPrefix) throws SQLException {
        long billing_account_id = rs.getLong(getColumnNameWithPrefix("id", columnPrefix));
        if (billing_account_id == 0) {
            return null;
        }

        return new BillingAccount(billing_account_id,
                rs.getString(getColumnNameWithPrefix("number", columnPrefix)),
                rs.getInt(getColumnNameWithPrefix("cvv", columnPrefix)),
                rs.getInt(getColumnNameWithPrefix("balance", columnPrefix)));
    }

    @Override
    protected String getDefaultColumnPrefix() {
        return "billing_account_";
    }
}
