package ru.otus.backend.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.backend.db.entity.BasketItem;
import ru.otus.backend.db.repository.BasketItemRepository;
import ru.otus.backend.service.api.BasketItemService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@AllArgsConstructor
@Component
public class BasketItemServiceImpl implements BasketItemService {

    private final BasketItemRepository repository;

    @Override
    public Long getCountByCustomerId(Long customerId) {
        return repository.countByCustomerId(customerId);
    }

    @Override
    public Iterable<BasketItem> findByCustomerId(Long id) {
        return repository.findByCustomerId(id);
    }

    @Transactional
    @Override
    public void saveBasketItems(List<BasketItem> basketItemsFromRequest) {
        List<BasketItem> basketItemsToSave = new ArrayList<>();
        List<BasketItem> sameItems = new ArrayList<>();
        List<BasketItem> basketItemsFromDatabase = (List<BasketItem>) repository.findByCustomerId(basketItemsFromRequest.get(0).getCustomerId());


        for (BasketItem basketItemFromDatabase : basketItemsFromDatabase) {
            for (BasketItem basketItemFromRequest : basketItemsFromRequest) {
                if (Objects.equals(basketItemFromRequest.getSubscriptionId(), basketItemFromDatabase.getSubscriptionId())
                        && Objects.equals(basketItemFromRequest.getCustomerId(), basketItemFromDatabase.getCustomerId())) {
                    basketItemFromDatabase.setQuantity(basketItemFromDatabase.getQuantity() + basketItemFromRequest.getQuantity());
                    basketItemsToSave.add(basketItemFromDatabase);
                    sameItems.add(basketItemFromRequest);
                }
            }
        }
        basketItemsFromRequest.removeAll(sameItems);
        basketItemsToSave.addAll(basketItemsFromRequest);
        repository.saveAll(basketItemsToSave);
    }

    @Override
    public Optional<BasketItem> getBasketItemById(Long id) {
        return repository.findById(id);
    }

    @Override
    public void deleteBasketItem(Long id) {
        repository.deleteById(id);
    }

    @Transactional
    @Override
    public void deleteAllBasketItemsByCustomerId(Long id) {
        repository.deleteBasketItemsByCustomerId(id);
    }
}
