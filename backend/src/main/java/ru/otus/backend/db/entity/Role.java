package ru.otus.backend.db.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@Table("roles")
public class Role {

    @Id
    private Long id;
    private String name;
}
