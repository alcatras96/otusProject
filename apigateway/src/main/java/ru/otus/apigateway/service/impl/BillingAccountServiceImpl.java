package ru.otus.apigateway.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.otus.apigateway.model.view.BillingAccountViewModel;
import ru.otus.apigateway.model.view.CustomerViewModel;
import ru.otus.apigateway.model.view.OwnerViewModel;
import ru.otus.apigateway.service.api.BillingAccountService;

@Service
public class BillingAccountServiceImpl implements BillingAccountService {

    private final static String BACKEND_CONTROLLER_URL_PREFIX = "/api/billing-accounts";

    private final String backendServerUrl;

    public BillingAccountServiceImpl(@Value("${backend.server.url}") String backendServerUrl) {
        this.backendServerUrl = backendServerUrl;
    }

    @Override
    public void saveBillingAccount(BillingAccountViewModel billingAccount) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.put(backendServerUrl + BACKEND_CONTROLLER_URL_PREFIX, billingAccount);
    }

    @Override
    public OwnerViewModel saveOwnerBillingAccount(OwnerViewModel owner) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForEntity(backendServerUrl + BACKEND_CONTROLLER_URL_PREFIX + "/owners", owner, OwnerViewModel.class).getBody();
    }

    @Override
    public OwnerViewModel saveCustomerBillingAccount(CustomerViewModel customer) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForEntity(backendServerUrl + BACKEND_CONTROLLER_URL_PREFIX + "/customers", customer, OwnerViewModel.class).getBody();
    }
}
