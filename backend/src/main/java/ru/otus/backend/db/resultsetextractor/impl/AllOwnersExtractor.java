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
import java.util.ArrayList;
import java.util.List;

@Component
public class AllOwnersExtractor extends OwnerExtractorBase implements ResultSetExtractor<List<Owner>> {

    public AllOwnersExtractor(RowMapper<Owner> ownerRowMapper, RowMapper<BillingAccount> billingAccountRowMapper,
                              RowMapper<User> userRowMapper, RowMapper<Role> roleRowMapper) {
        super(ownerRowMapper, billingAccountRowMapper, userRowMapper, roleRowMapper);
    }

    @Override
    public List<Owner> extractData(ResultSet rs) throws SQLException {
        var owners = new ArrayList<Owner>();
        while (rs.next()) {
            owners.add(extractOwner(rs));
        }
        return owners;
    }
}
