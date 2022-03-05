package ru.otus.apigateway.model.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BasketItemViewModel {

    @Null
    private Long id;
    @NotNull
    private Long customerId;
    @NotNull
    @Valid
    private SubscriptionViewModel subscription;
    @NotNull
    @Min(value = 1)
    private Integer quantity;
}
