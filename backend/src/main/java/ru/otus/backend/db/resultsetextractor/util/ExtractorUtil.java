package ru.otus.backend.db.resultsetextractor.util;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.backend.db.entity.*;
import ru.otus.backend.db.mapper.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@AllArgsConstructor
public class ExtractorUtil {

    private final RowMapper<Owner> ownerRowMapper;
    private final RowMapper<Customer> customerRowMapper;
    private final RowMapper<BillingAccount> billingAccountRowMapper;
    private final RowMapper<User> userRowMapper;
    private final RowMapper<Role> roleRowMapper;

    public Owner extractOwner(ResultSet rs) throws SQLException {
        Owner owner = ownerRowMapper.mapRow(rs);
        owner.setBillingAccount(billingAccountRowMapper.mapRow(rs));
        owner.setUser(extractUser(rs));
        return owner;
    }

    public Customer extractCustomer(ResultSet rs) throws SQLException {
        Customer customer = customerRowMapper.mapRow(rs);
        customer.setBillingAccount(billingAccountRowMapper.mapRow(rs));
        customer.setUser(extractUser(rs));
        customer.setStatus(Status.valueOf(rs.getString("status_name")));
        return customer;
    }

    public User extractUser(ResultSet rs) throws SQLException {
        User user = userRowMapper.mapRow(rs);
        user.setRole(roleRowMapper.mapRow(rs));
        return user;
    }
}
