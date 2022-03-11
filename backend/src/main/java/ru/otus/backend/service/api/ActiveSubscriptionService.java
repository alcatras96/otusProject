package ru.otus.backend.service.api;

import ru.otus.backend.db.entity.ActiveSubscription;

import java.util.List;
import java.util.Optional;

public interface ActiveSubscriptionService {

    Optional<ActiveSubscription> getActiveSubscriptionById(Long id);

    Iterable<ActiveSubscription> getActiveSubscriptionsByCustomerId(Long id);

    Iterable<ActiveSubscription> getAllActiveSubscriptions();

    Iterable<ActiveSubscription> saveActiveSubscriptions(List<ActiveSubscription> activeSubscription);

    ActiveSubscription saveActiveSubscription(ActiveSubscription activeSubscription);

    void deleteActiveSubscriptionById(Long id);

    void deleteActiveSubscriptionsByCustomerId(Long customerId);
}
