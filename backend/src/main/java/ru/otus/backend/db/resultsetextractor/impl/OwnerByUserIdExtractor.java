package ru.otus.backend.db.resultsetextractor.impl;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;
import ru.otus.backend.db.entity.BillingAccount;
import ru.otus.backend.db.entity.Owner;
import ru.otus.backend.db.entity.Role;
import ru.otus.backend.db.entity.User;
import ru.otus.backend.db.mapper.RowMapper;
import ru.otus.backend.db.resultsetextractor.base.OwnerExtractorBase;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class OwnerByUserIdExtractor extends OwnerExtractorBase implements ResultSetExtractor<Owner> {

    public OwnerByUserIdExtractor(RowMapper<Owner> ownerRowMapper,
                                  RowMapper<BillingAccount> billingAccountRowMapper,
                                  RowMapper<User> userRowMapper,
                                  RowMapper<Role> roleRowMapper) {
        super(ownerRowMapper, billingAccountRowMapper, userRowMapper, roleRowMapper);
    }

    @Override
    public Owner extractData(ResultSet rs) throws SQLException {
        Owner owner = null;
        if (rs.next()) {
            owner = extractOwner(rs);
        }
        return owner;
    }
}
