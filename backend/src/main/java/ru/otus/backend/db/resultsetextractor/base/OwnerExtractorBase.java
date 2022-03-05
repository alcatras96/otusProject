package ru.otus.backend.db.resultsetextractor.base;

import lombok.AllArgsConstructor;
import ru.otus.backend.db.entity.BillingAccount;
import ru.otus.backend.db.entity.Owner;
import ru.otus.backend.db.entity.Role;
import ru.otus.backend.db.entity.User;
import ru.otus.backend.db.mapper.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@AllArgsConstructor
public abstract class OwnerExtractorBase {

    protected final RowMapper<Owner> ownerRowMapper;
    protected final RowMapper<BillingAccount> billingAccountRowMapper;
    protected final RowMapper<User> userRowMapper;
    protected final RowMapper<Role> roleRowMapper;

    protected Owner extractOwner(ResultSet rs) throws SQLException {
        Owner owner = ownerRowMapper.mapRow(rs);
        owner.setBillingAccount(billingAccountRowMapper.mapRow(rs));
        User user = userRowMapper.mapRow(rs);
        user.setRole(roleRowMapper.mapRow(rs));
        owner.setUser(user);
        return owner;
    }
}
