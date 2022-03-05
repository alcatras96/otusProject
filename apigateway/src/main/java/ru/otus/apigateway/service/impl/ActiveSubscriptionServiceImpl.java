package ru.otus.apigateway.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import ru.otus.apigateway.converter.Converter;
import ru.otus.apigateway.model.backend.ActiveSubscription;
import ru.otus.apigateway.model.view.ActiveSubscriptionViewModel;
import ru.otus.apigateway.service.api.ActiveSubscriptionDataService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ActiveSubscriptionServiceImpl implements ActiveSubscriptionDataService {

    @Value("${backend.server.url}")
    private String backendServerUrl;

    @Autowired
    private Converter<ActiveSubscriptionViewModel, ActiveSubscription> toActiveSubscriptionConverter;

    @Override
    public List<ActiveSubscriptionViewModel> saveActiveSubscriptions(List<ActiveSubscriptionViewModel> activeSubscriptionViewModel) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(backendServerUrl + "/api/active_subscription", toActiveSubscriptionConverter.convert(activeSubscriptionViewModel), List.class);
    }

    @Override
    public Iterable<ActiveSubscriptionViewModel> getASByCustomerId(Long id) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(backendServerUrl + "/api/active_subscription/customer/" + id, Iterable.class);
    }

    @Override
    public ActiveSubscriptionViewModel getActiveSubscriptionById(Long id) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(backendServerUrl + "/api/active_subscription/" + id, ActiveSubscriptionViewModel.class);
    }

    @Override
    public void deleteActiveSubscriptionById(Long id) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(backendServerUrl + "/api/active_subscription/" + id);
    }
}
