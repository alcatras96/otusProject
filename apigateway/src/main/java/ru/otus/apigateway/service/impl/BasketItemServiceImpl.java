package ru.otus.apigateway.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.otus.apigateway.converter.Converter;
import ru.otus.apigateway.model.backend.BasketItem;
import ru.otus.apigateway.model.view.BasketItemViewModel;
import ru.otus.apigateway.service.api.BasketItemService;

import java.util.List;

@Service
public class BasketItemServiceImpl implements BasketItemService {

    private final static String BACKEND_CONTROLLER_URL_PREFIX = "/api/basket-items";

    private final String backendServerUrl;

    private final Converter<BasketItemViewModel, BasketItem> toBasketItemConverter;

    public BasketItemServiceImpl(@Value("${backend.server.url}") String backendServerUrl,
                                 Converter<BasketItemViewModel, BasketItem> toBasketItemConverter) {
        this.backendServerUrl = backendServerUrl;
        this.toBasketItemConverter = toBasketItemConverter;
    }

    @Override
    public List<BasketItemViewModel> findByCustomerId(Long id) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(backendServerUrl + BACKEND_CONTROLLER_URL_PREFIX + "/customers/" + id, List.class);
    }

    @Override
    public Long getCount(Long id) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(backendServerUrl + BACKEND_CONTROLLER_URL_PREFIX + "/count/customers/" + id, Long.class);
    }

    @Override
    public void saveBasketItem(List<BasketItemViewModel> basketItems) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForEntity(backendServerUrl + BACKEND_CONTROLLER_URL_PREFIX, toBasketItemConverter.convert(basketItems), List.class);
    }

    @Override
    public BasketItemViewModel getBasketItemById(Long id) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(backendServerUrl + BACKEND_CONTROLLER_URL_PREFIX + "/" + id, BasketItemViewModel.class);
    }

    @Override
    public void deleteBasketItem(Long id) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(backendServerUrl + BACKEND_CONTROLLER_URL_PREFIX + "/" + id);
    }

    @Override
    public void deleteBasketItemByCustomerId(Long id) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(backendServerUrl + BACKEND_CONTROLLER_URL_PREFIX + "/customer/" + id);
    }


}
