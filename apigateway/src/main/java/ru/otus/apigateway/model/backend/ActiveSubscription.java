package ru.otus.apigateway.model.backend;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ActiveSubscription {

    private Long id;
    private Long lastEditDate;
    private Integer quantity;
    private Long customerId;
    private Long subscriptionId;
}
