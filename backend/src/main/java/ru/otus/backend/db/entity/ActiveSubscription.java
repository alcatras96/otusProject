package ru.otus.backend.db.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("active_subscriptions")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ActiveSubscription {

    @Id
    private Long id;
    private Long lastEditDate;
    private Integer quantity;
    private Long customerId;
    private Long subscriptionId;

    @Transient
    private Subscription subscription;

    public Long getSubscriptionId() {
        return subscription != null ? subscription.getId() : subscriptionId;
    }
}

