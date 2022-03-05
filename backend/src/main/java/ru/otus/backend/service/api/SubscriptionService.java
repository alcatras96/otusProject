package ru.otus.backend.service.api;

import org.springframework.data.domain.Sort;
import ru.otus.backend.db.entity.Subscription;

import java.util.Optional;

public interface SubscriptionService {
    Iterable<Subscription> findAllWithSorting(int page, int size, Sort sort);

    Optional<Subscription> getSubscriptionById(Long id);

    Iterable<Subscription> findByNameContaining(String name, int page, int size);

    Iterable<Subscription> getByOwnerId(Long id);

    Iterable<Subscription> getByCategoryId(Long id, int page, int size);

    Subscription saveSubscription(Subscription subscription);

    void deleteSubscription(Long id);
}
