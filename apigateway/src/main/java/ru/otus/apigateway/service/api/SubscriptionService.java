package ru.otus.apigateway.service.api;

import ru.otus.apigateway.model.view.Content;
import ru.otus.apigateway.model.view.SubscriptionViewModel;

import java.util.List;

public interface SubscriptionService {

    Content<SubscriptionViewModel> findAll(int page, int size);

    Content<SubscriptionViewModel> findByNameLike(String name, int page, int size);

    Content<SubscriptionViewModel> findByCategoryId(Long id, int page, int size);

    List<SubscriptionViewModel> findByOwnerId(Long id);

    SubscriptionViewModel saveSubscription(SubscriptionViewModel subscription);

    void deleteSubscription(Long id);
}
