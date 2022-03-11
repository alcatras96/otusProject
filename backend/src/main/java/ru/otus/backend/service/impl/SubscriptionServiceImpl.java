package ru.otus.backend.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import ru.otus.backend.db.entity.Subscription;
import ru.otus.backend.db.repository.SubscriptionRepository;
import ru.otus.backend.service.api.SubscriptionService;

import java.util.Optional;

@AllArgsConstructor
@Component
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository repository;

    @Override
    public Iterable<Subscription> findAllWithSorting(int page, int size, Sort sort) {
        return repository.findAll(PageRequest.of(page, size, sort));
    }

    @Override
    public Optional<Subscription> getSubscriptionById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Iterable<Subscription> findByNameContaining(String name, int page, int size) {
        return repository.findByNameContaining(name, PageRequest.of(page, size));
    }

    @Override
    public Iterable<Subscription> getByOwnerId(Long id) {
        return repository.findByOwnerId(id);
    }

    @Override
    public Iterable<Subscription> getByCategoryId(Long id, int page, int size) {
        return repository.findByCategoryId(id, PageRequest.of(page, size));
    }

    @Override
    public Subscription saveSubscription(Subscription subscription) {
        return repository.save(subscription);
    }

    @Override
    public void deleteSubscription(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteSubscriptionsByOwnerId(Long ownerId) {
        repository.deleteAll(getByOwnerId(ownerId));
    }
}
