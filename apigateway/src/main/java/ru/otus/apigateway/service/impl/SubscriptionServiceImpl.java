package ru.otus.apigateway.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.otus.apigateway.converter.Converter;
import ru.otus.apigateway.model.backend.Subscription;
import ru.otus.apigateway.model.view.Content;
import ru.otus.apigateway.model.view.SubscriptionViewModel;
import ru.otus.apigateway.service.api.SubscriptionService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    private final static String BACKEND_CONTROLLER_URL_PREFIX = "/api/subscriptions";

    private final String backendServerUrl;
    private final Converter<SubscriptionViewModel, Subscription> toSubscriptionConverter;

    public SubscriptionServiceImpl(@Value("${backend.server.url}") String backendServerUrl,
                                   Converter<SubscriptionViewModel, Subscription> toSubscriptionConverter) {
        this.backendServerUrl = backendServerUrl;
        this.toSubscriptionConverter = toSubscriptionConverter;
    }

    @Override
    public Content<SubscriptionViewModel> findAll(int page, int size) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(backendServerUrl + BACKEND_CONTROLLER_URL_PREFIX + "?page=" + page + "&size=" + size, Content.class);
    }

    @Override
    public Content<SubscriptionViewModel> findByNameLike(String name, int page, int size) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(backendServerUrl + BACKEND_CONTROLLER_URL_PREFIX + "/search?name=" + name + "&page=" + page + "&size=" + size, Content.class);
    }

    @Override
    public Content<SubscriptionViewModel> findByCategoryId(Long id, int page, int size) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(backendServerUrl + BACKEND_CONTROLLER_URL_PREFIX + "/categories/" + id + "?page=" + page + "&size=" + size, Content.class);
    }

    @Override
    public List<SubscriptionViewModel> findByOwnerId(Long id) {
        RestTemplate restTemplate = new RestTemplate();
        SubscriptionViewModel[] subscriptions = restTemplate.getForObject(backendServerUrl + BACKEND_CONTROLLER_URL_PREFIX + "/owners/" + id, SubscriptionViewModel[].class);
        return subscriptions == null ? Collections.emptyList() : Arrays.asList(subscriptions);
    }

    @Override
    public SubscriptionViewModel saveSubscription(SubscriptionViewModel subscription) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForEntity(backendServerUrl + BACKEND_CONTROLLER_URL_PREFIX, toSubscriptionConverter.convert(subscription), SubscriptionViewModel.class).getBody();
    }

    @Override
    public void deleteSubscription(Long id) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(backendServerUrl + BACKEND_CONTROLLER_URL_PREFIX + "/" + id);
    }

}
