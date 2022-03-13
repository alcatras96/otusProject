package ru.otus.apigateway.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.otus.apigateway.converter.Converter;
import ru.otus.apigateway.model.backend.ActiveSubscription;
import ru.otus.apigateway.model.view.ActiveSubscriptionViewModel;
import ru.otus.apigateway.service.api.ActiveSubscriptionService;

import java.util.List;

@Service
public class ActiveSubscriptionServiceImpl implements ActiveSubscriptionService {

    private final static String BACKEND_CONTROLLER_URL_PREFIX = "/api/active-subscriptions";

    private final String backendServerUrl;
    private final Converter<ActiveSubscriptionViewModel, ActiveSubscription> toActiveSubscriptionConverter;

    public ActiveSubscriptionServiceImpl(@Value("${backend.server.url}") String backendServerUrl,
                                         Converter<ActiveSubscriptionViewModel, ActiveSubscription> toActiveSubscriptionConverter) {
        this.backendServerUrl = backendServerUrl;
        this.toActiveSubscriptionConverter = toActiveSubscriptionConverter;
    }

    @Override
    public List<ActiveSubscriptionViewModel> saveActiveSubscriptions(List<ActiveSubscriptionViewModel> activeSubscriptionViewModel) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(backendServerUrl + BACKEND_CONTROLLER_URL_PREFIX, toActiveSubscriptionConverter.convert(activeSubscriptionViewModel), List.class);
    }

    @Override
    public Iterable<ActiveSubscriptionViewModel> getASByCustomerId(Long id) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(backendServerUrl + BACKEND_CONTROLLER_URL_PREFIX + "/customers/" + id, Iterable.class);
    }

    @Override
    public ActiveSubscriptionViewModel getActiveSubscriptionById(Long id) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(backendServerUrl + BACKEND_CONTROLLER_URL_PREFIX + "/" + id, ActiveSubscriptionViewModel.class);
    }

    @Override
    public void deleteActiveSubscriptionById(Long id) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(backendServerUrl + BACKEND_CONTROLLER_URL_PREFIX + "/" + id);
    }
}
