package ru.otus.backend.db.resultsetextractor.impl;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;
import ru.otus.backend.db.entity.*;
import ru.otus.backend.db.mapper.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Component
@AllArgsConstructor
public class CustomerExtractor implements ResultSetExtractor<Optional<Customer>> {

    private final RowMapper<Customer> customerRowMapper;
    private final RowMapper<BillingAccount> billingAccountRowMapper;
    private final RowMapper<User> userRowMapper;
    private final RowMapper<Status> statusRowMapper;
    private final RowMapper<Role> roleRowMapper;

    @Override
    public Optional<Customer> extractData(ResultSet rs) throws SQLException {
        Customer customer = null;
        if (rs.next()) {
            customer = extractCustomer(rs);
        }
        return Optional.ofNullable(customer);
    }

    private Customer extractCustomer(ResultSet rs) throws SQLException {
        Customer customer = customerRowMapper.mapRow(rs);
        customer.setBillingAccount(billingAccountRowMapper.mapRow(rs));

        User user = userRowMapper.mapRow(rs);
        user.setRole(roleRowMapper.mapRow(rs));
        customer.setUser(user);
        customer.setStatus(statusRowMapper.mapRow(rs));
        return customer;
    }
}
