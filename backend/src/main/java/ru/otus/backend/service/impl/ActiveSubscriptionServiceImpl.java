package ru.otus.backend.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.backend.db.entity.ActiveSubscription;
import ru.otus.backend.db.repository.ActiveSubscriptionRepository;
import ru.otus.backend.service.api.ActiveSubscriptionService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@AllArgsConstructor
@Component
public class ActiveSubscriptionServiceImpl implements ActiveSubscriptionService {

    private final ActiveSubscriptionRepository repository;

    @Override
    public Optional<ActiveSubscription> getActiveSubscriptionById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Iterable<ActiveSubscription> getActiveSubscriptionsByCustomerId(Long id) {
        return repository.findByCustomerId(id);
    }

    @Override
    public Iterable<ActiveSubscription> getAllActiveSubscriptions() {
        return repository.findAll();
    }

    @Transactional
    @Override
    public Iterable<ActiveSubscription> addActiveSubscriptions(List<ActiveSubscription> activeSubscriptionsToSave) {
        List<ActiveSubscription> sameItems = new ArrayList<>();

        List<ActiveSubscription> currentActiveSubscriptions = repository.findByCustomerId(activeSubscriptionsToSave.get(0).getCustomerId());
        for (ActiveSubscription currentActiveSubscription : currentActiveSubscriptions) {
            for (ActiveSubscription activeSubscriptionToSave : activeSubscriptionsToSave) {
                if (Objects.equals(currentActiveSubscription.getCustomerId(), activeSubscriptionToSave.getCustomerId())
                        && Objects.equals(currentActiveSubscription.getSubscriptionId(), activeSubscriptionToSave.getSubscriptionId())) {
                    currentActiveSubscription.setQuantity(currentActiveSubscription.getQuantity() + activeSubscriptionToSave.getQuantity());
                    repository.save(currentActiveSubscription);

                    sameItems.add(activeSubscriptionToSave);
                }
            }
        }
        activeSubscriptionsToSave.removeAll(sameItems);

        return repository.saveAll(activeSubscriptionsToSave);
    }

    @Override
    public Iterable<ActiveSubscription> saveActiveSubscriptions(Iterable<ActiveSubscription> activeSubscriptions) {
        return repository.saveAll(activeSubscriptions);
    }

    @Override
    public ActiveSubscription saveActiveSubscription(ActiveSubscription activeSubscription) {
        return repository.save(activeSubscription);
    }

    @Override
    public void deleteActiveSubscriptionById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteActiveSubscriptionsByCustomerId(Long customerId) {
        repository.deleteAll(getActiveSubscriptionsByCustomerId(customerId));
    }

    @Override
    public void deleteActiveSubscriptions(Iterable<ActiveSubscription> activeSubscriptions) {
        repository.deleteAll(activeSubscriptions);
    }
}
