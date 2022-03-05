package ru.otus.backend.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("customers")
public class Customer {

    @Id
    private Long id;
    private String name;
    private String address;
    @JsonIgnore
    private Long userId;
    @JsonIgnore
    private Long billingAccountId;
    @JsonIgnore
    private Long statusId;

    @Transient
    private User user;
    @Transient
    private BillingAccount billingAccount;
    @Transient
    private Status status;

    public Customer(Long id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }
}
