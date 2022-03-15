package ru.otus.backend.db.resultsetextractor.impl;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;
import ru.otus.backend.db.entity.Owner;
import ru.otus.backend.db.resultsetextractor.util.ExtractorUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Component
@AllArgsConstructor
public class OwnerExtractor implements ResultSetExtractor<Optional<Owner>> {

    private final ExtractorUtil extractorUtil;

    @Override
    public Optional<Owner> extractData(ResultSet rs) throws SQLException {
        Owner owner = null;
        if (rs.next()) {
            owner = extractorUtil.extractOwner(rs);
        }
        return Optional.ofNullable(owner);
    }
}
