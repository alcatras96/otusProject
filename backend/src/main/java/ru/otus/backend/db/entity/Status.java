package ru.otus.backend.db.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("statuses")
@AllArgsConstructor
@NoArgsConstructor
public class Status {
    @Id
    private Long id;
    private String name;

    public Status(String name) {
        this.name = name;
    }
}
