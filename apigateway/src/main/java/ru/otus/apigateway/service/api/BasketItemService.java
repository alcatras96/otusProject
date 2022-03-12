package ru.otus.apigateway.service.api;

import ru.otus.apigateway.model.view.BasketItemViewModel;

import java.util.List;

public interface BasketItemService {

    List<BasketItemViewModel> findByCustomerId(Long id);

    Long getCount(Long id);

    void saveBasketItem(List<BasketItemViewModel> Sb);

    BasketItemViewModel getBasketItemById(Long id);

    void deleteBasketItem(Long id);

    void deleteBasketItemByCustomerId(Long id);
}
