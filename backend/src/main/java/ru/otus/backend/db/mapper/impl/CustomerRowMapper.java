package ru.otus.backend.db.mapper.impl;

import org.springframework.stereotype.Component;
import ru.otus.backend.db.entity.Customer;
import ru.otus.backend.db.mapper.AbstractRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CustomerRowMapper extends AbstractRowMapper<Customer> {

    @Override
    public Customer mapRow(ResultSet rs, String columnPrefix) throws SQLException {
        return new Customer(rs.getLong(getColumnNameWithPrefix("id", columnPrefix)),
                rs.getString(getColumnNameWithPrefix("name", columnPrefix)),
                rs.getString(getColumnNameWithPrefix("address", columnPrefix)));
    }

    @Override
    protected String getDefaultColumnPrefix() {
        return "customer_";
    }
}
