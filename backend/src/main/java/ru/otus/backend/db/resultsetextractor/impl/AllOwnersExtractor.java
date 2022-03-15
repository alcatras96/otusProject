package ru.otus.backend.db.resultsetextractor.impl;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;
import ru.otus.backend.db.entity.Owner;
import ru.otus.backend.db.resultsetextractor.util.ExtractorUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class AllOwnersExtractor implements ResultSetExtractor<List<Owner>> {

    private final ExtractorUtil extractorUtil;

    @Override
    public List<Owner> extractData(ResultSet rs) throws SQLException {
        var owners = new ArrayList<Owner>();
        while (rs.next()) {
            owners.add(extractorUtil.extractOwner(rs));
        }
        return owners;
    }
}
