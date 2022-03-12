package ru.otus.backend.db.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("owners")
public class Owner {

    @Id
    private Long id;
    private String name;
    private Long userId;
    private Long billingAccountId;

    @Transient
    private User user;
    @Transient
    private BillingAccount billingAccount;

    public Owner(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
