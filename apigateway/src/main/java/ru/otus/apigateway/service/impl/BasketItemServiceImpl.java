package ru.otus.apigateway.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.otus.apigateway.converter.Converter;
import ru.otus.apigateway.model.backend.BasketItem;
import ru.otus.apigateway.service.api.BasketItemDataService;
import ru.otus.apigateway.model.view.BasketItemViewModel;

import java.util.List;

@Service
public class BasketItemServiceImpl implements BasketItemDataService {

    @Value("${backend.server.url}")
    private String backendServerUrl;

    @Autowired
    private Converter<BasketItemViewModel, BasketItem> toBasketItemConverter;

    @Override
    public List<BasketItemViewModel> findByCustomerId(Long id) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(backendServerUrl + "/api/basket_item/customer/" + id, List.class);
    }

    @Override
    public Long getCount(Long id) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(backendServerUrl + "/api/basket_item/count/" + id, Long.class);
    }

    @Override
    public void saveBasketItem(List<BasketItemViewModel> basketItems) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForEntity(backendServerUrl + "/api/basket_item", toBasketItemConverter.convert(basketItems), List.class);
    }

    @Override
    public BasketItemViewModel getBasketItemById(Long id) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(backendServerUrl + "/api/basket_item/" + id, BasketItemViewModel.class);
    }

    @Override
    public void deleteBasketItem(Long id) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(backendServerUrl + "/api/basket_item/" + id);
    }

    @Override
    public void deleteBasketItemByCustomerId(Long id) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(backendServerUrl + "/api/basket_item/customer/" + id);
    }


}
