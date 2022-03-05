package ru.otus.apigateway.converter.impl;

import org.springframework.stereotype.Component;
import ru.otus.apigateway.converter.Converter;
import ru.otus.apigateway.model.backend.BasketItem;
import ru.otus.apigateway.model.view.BasketItemViewModel;

@Component
public class BasketItemViewModelToBasketItemConverter implements Converter<BasketItemViewModel, BasketItem> {

    @Override
    public BasketItem convert(BasketItemViewModel basketItemViewModel) {
        return BasketItem.builder()
                .customerId(basketItemViewModel.getCustomerId())
                .subscriptionId(basketItemViewModel.getSubscription().getId())
                .quantity(basketItemViewModel.getQuantity())
                .build();
    }
}
