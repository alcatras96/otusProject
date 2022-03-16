package ru.otus.backend.service.api;

import ru.otus.backend.db.entity.ActiveSubscription;

import java.util.List;
import java.util.Optional;

public interface ActiveSubscriptionService {

    Iterable<ActiveSubscription> getActiveSubscriptionsByCustomerId(Long id);

    Optional<ActiveSubscription> getActiveSubscriptionById(Long id);

    Iterable<ActiveSubscription> getAllActiveSubscriptions();

    Iterable<ActiveSubscription> addActiveSubscriptions(List<ActiveSubscription> activeSubscriptions);

    Iterable<ActiveSubscription> saveActiveSubscriptions(Iterable<ActiveSubscription> activeSubscriptions);

    void saveSubscriptionQuantityAndLastEditDate(Iterable<ActiveSubscription> activeSubscriptions);

    List<ActiveSubscription> findAllForRecalculation();

    void deleteActiveSubscriptionById(Long id);

    void deleteActiveSubscriptionsByCustomerId(Long customerId);

    void deleteActiveSubscriptions(Iterable<ActiveSubscription> activeSubscriptions);
}
