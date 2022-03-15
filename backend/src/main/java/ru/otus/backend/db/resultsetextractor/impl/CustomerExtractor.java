package ru.otus.backend.db.resultsetextractor.impl;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;
import ru.otus.backend.db.entity.Customer;
import ru.otus.backend.db.resultsetextractor.util.ExtractorUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Component
@AllArgsConstructor
public class CustomerExtractor implements ResultSetExtractor<Optional<Customer>> {

    private final ExtractorUtil extractorUtil;

    @Override
    public Optional<Customer> extractData(ResultSet rs) throws SQLException {
        Customer customer = null;
        if (rs.next()) {
            customer = extractorUtil.extractCustomer(rs);
        }
        return Optional.ofNullable(customer);
    }
}
