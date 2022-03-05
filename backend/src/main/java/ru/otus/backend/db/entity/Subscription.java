package ru.otus.backend.db.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table("subscriptions")
public class Subscription {

    @Id
    private Long id;
    private Integer price;
    private String name;
    private String imageUrl;
    private String description;
    private Long ownerId;
    private Long categoryId;

    @Transient
    private Owner owner;
    @Transient
    private Category category;
}


