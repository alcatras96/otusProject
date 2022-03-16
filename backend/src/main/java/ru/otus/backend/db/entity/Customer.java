package ru.otus.backend.db.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

import static java.util.Optional.ofNullable;

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
    private Status status;

    @Transient
    private User user;
    @Transient
    private BillingAccount billingAccount;

    public Customer(Long id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public Long getUserId() {
        return ofNullable(userId).or(() -> ofNullable(user).map(User::getId)).orElse(null);
    }

    public void setUser(User user) {
        this.userId = ofNullable(user).map(User::getId).orElse(null);
        this.user = user;
    }

    public void setBillingAccount(BillingAccount billingAccount) {
        this.billingAccountId = ofNullable(billingAccount).map(BillingAccount::getId).orElse(null);
        this.billingAccount = billingAccount;
    }
}
