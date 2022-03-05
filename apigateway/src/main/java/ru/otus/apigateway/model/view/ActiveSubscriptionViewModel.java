package ru.otus.apigateway.model.view;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Data
public class ActiveSubscriptionViewModel {

    @Null
    private Long id;
    @Null
    private Long lastEditDate;
    @Null
    private Long customerId;
    @NotNull
    @Valid
    private SubscriptionViewModel subscription;
    @NotNull
    @Min(value = 1)
    private Integer quantity;
}
