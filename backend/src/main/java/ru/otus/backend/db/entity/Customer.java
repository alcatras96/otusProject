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
@Table("customers")
public class Customer {

    @Id
    private Long id;
    private String name;
    private String address;
    private Long userId;
    private Long billingAccountId;
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
