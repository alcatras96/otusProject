package ru.otus.backend.db.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@AllArgsConstructor
@Table("billing_accounts")
public class BillingAccount {

    @Id
    private Long id;
    private String number;
    private Integer cvv;
    @EqualsAndHashCode.Exclude
    private Integer balance;
}
