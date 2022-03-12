package ru.otus.apigateway.service.api;

import ru.otus.apigateway.model.view.ActiveSubscriptionViewModel;

import java.util.List;

public interface ActiveSubscriptionService {
    List<ActiveSubscriptionViewModel> saveActiveSubscriptions(List<ActiveSubscriptionViewModel> activeSubscriptionViewModel);

    Iterable<ActiveSubscriptionViewModel> getASByCustomerId(Long id);

    ActiveSubscriptionViewModel getActiveSubscriptionById(Long id);

    void deleteActiveSubscriptionById(Long id);
}
