package ru.otus.backend.db.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("basket_items")
public class BasketItem {

    @Id
    private Long id;
    private Integer quantity;
    private Long customerId;
    private Long subscriptionId;

    @Transient
    private Subscription subscription;

    public BasketItem(Long id, Integer quantity,  Long customerId) {
        this.id = id;
        this.quantity = quantity;
        this.customerId = customerId;
    }

    public Long getSubscriptionId() {
        return subscription != null ? subscription.getId() : subscriptionId;
    }
}
