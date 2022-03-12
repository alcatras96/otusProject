package ru.otus.backend.service.api;

import ru.otus.backend.db.entity.ActiveSubscription;

import java.util.List;
import java.util.Optional;

public interface ActiveSubscriptionService {

    Optional<ActiveSubscription> getActiveSubscriptionById(Long id);

    Iterable<ActiveSubscription> getActiveSubscriptionsByCustomerId(Long id);

    Iterable<ActiveSubscription> getAllActiveSubscriptions();

    Iterable<ActiveSubscription> addActiveSubscriptions(List<ActiveSubscription> activeSubscriptions);

    Iterable<ActiveSubscription> saveActiveSubscriptions(Iterable<ActiveSubscription> activeSubscriptions);

    ActiveSubscription saveActiveSubscription(ActiveSubscription activeSubscription);

    void deleteActiveSubscriptionById(Long id);

    void deleteActiveSubscriptionsByCustomerId(Long customerId);

    void deleteActiveSubscriptions(Iterable<ActiveSubscription> activeSubscriptions);
}
