package ru.otus.apigateway.converter.impl;

import org.springframework.stereotype.Component;
import ru.otus.apigateway.converter.Converter;
import ru.otus.apigateway.model.backend.Subscription;
import ru.otus.apigateway.model.view.SubscriptionViewModel;

@Component
public class SubscriptionViewModelToSubscriptionConverter implements Converter<SubscriptionViewModel, Subscription> {

    @Override
    public Subscription convert(SubscriptionViewModel subscriptionViewModel) {
        return Subscription.builder()
                .id(subscriptionViewModel.getId())
                .description(subscriptionViewModel.getDescription())
                .imageUrl(subscriptionViewModel.getImageUrl())
                .name(subscriptionViewModel.getName())
                .price(subscriptionViewModel.getPrice())
                .ownerId(subscriptionViewModel.getOwnerId())
                .categoryId(subscriptionViewModel.getCategory().getId())
                .build();
    }
}
