package ru.otus.apigateway.model.backend;

import lombok.Builder;
import lombok.Data;
import ru.otus.apigateway.model.view.SubscriptionViewModel;

@Data
@Builder
public class BasketItem {

    private Long id;
    private Integer quantity;
    private Long customerId;
    private Long subscriptionId;

    private SubscriptionViewModel subscription;
}
