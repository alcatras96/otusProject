package ru.otus.backend.service.api;

import ru.otus.backend.db.entity.BasketItem;

import java.util.List;
import java.util.Optional;

public interface BasketItemService {
    Long getCountByCustomerId(Long id);

    Iterable<BasketItem> findByCustomerId(Long id);

    void saveBasketItems(List<BasketItem> Sb);

    Optional<BasketItem> getBasketItemById(Long id);

    void deleteBasketItem(Long id);

    void deleteAllBasketItemsByCustomerId(Long id);
}
