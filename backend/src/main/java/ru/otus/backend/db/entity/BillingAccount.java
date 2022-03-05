package ru.otus.backend.db.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@Table("billing_accounts")
public class BillingAccount {

    @Id
    private Long id;
    private String number;
    private Integer cvv;
    private Integer balance;
}
