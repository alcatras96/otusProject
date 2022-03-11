package ru.otus.apigateway.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.otus.apigateway.model.view.BillingAccountViewModel;
import ru.otus.apigateway.model.view.CustomerViewModel;
import ru.otus.apigateway.model.view.OwnerViewModel;
import ru.otus.apigateway.service.api.BillingAccountService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class BaServiceImpl implements BillingAccountService {

    @Value("${backend.server.url}")
    private String backendServerUrl;

    @Override
    public void createBillingAccount(BillingAccountViewModel billingAccount) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForEntity(backendServerUrl + "/api/billing_accounts/", billingAccount, BillingAccountViewModel.class);
    }

    @Override
    public void saveBillingAccount(BillingAccountViewModel billingAccount) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.put(backendServerUrl + "/api/billing_accounts/", billingAccount);
    }

    @Override
    public List<BillingAccountViewModel> getAllBillingAccounts() {
        RestTemplate restTemplate = new RestTemplate();
        BillingAccountViewModel[] billingAccountViewModelsResponse = restTemplate.getForObject(backendServerUrl + "/api/billing_accounts/", BillingAccountViewModel[].class);
        return billingAccountViewModelsResponse == null ? Collections.emptyList() : Arrays.asList(billingAccountViewModelsResponse);
    }

    @Override
    public BillingAccountViewModel getBillingAccountById(Long id) {
        return null;
    }

    @Override
    public OwnerViewModel saveOwnerBillingAccount(OwnerViewModel owner) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForEntity(backendServerUrl + "/api/billing_accounts/owner", owner, OwnerViewModel.class).getBody();
    }

    @Override
    public OwnerViewModel saveCustomerBillingAccount(CustomerViewModel customer) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForEntity(backendServerUrl + "/api/billing_accounts/customer", customer, OwnerViewModel.class).getBody();
    }
}
