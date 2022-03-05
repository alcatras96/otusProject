package ru.otus.backend.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private Long userId;
    @JsonIgnore
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
