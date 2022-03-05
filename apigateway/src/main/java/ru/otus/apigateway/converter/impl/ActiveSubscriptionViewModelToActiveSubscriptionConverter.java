package ru.otus.apigateway.converter.impl;

import org.springframework.stereotype.Component;
import ru.otus.apigateway.converter.Converter;
import ru.otus.apigateway.model.backend.ActiveSubscription;
import ru.otus.apigateway.model.view.ActiveSubscriptionViewModel;

@Component
public class ActiveSubscriptionViewModelToActiveSubscriptionConverter implements Converter<ActiveSubscriptionViewModel, ActiveSubscription> {

    @Override
    public ActiveSubscription convert(ActiveSubscriptionViewModel activeSubscriptionViewModel) {
        return ActiveSubscription.builder()
                .id(activeSubscriptionViewModel.getId())
                .lastEditDate(activeSubscriptionViewModel.getLastEditDate())
                .customerId(activeSubscriptionViewModel.getCustomerId())
                .quantity(activeSubscriptionViewModel.getQuantity())
                .subscriptionId(activeSubscriptionViewModel.getSubscription().getId())
                .build();
    }
}
