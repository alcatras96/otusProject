package ru.otus.apigateway.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.otus.apigateway.model.backend.Customer;
import ru.otus.apigateway.model.view.Content;
import ru.otus.apigateway.model.view.CustomerViewModel;
import ru.otus.apigateway.service.api.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final static String BACKEND_CONTROLLER_URL_PREFIX = "/api/customers";

    private final String backendServerUrl;
    private final CacheManager cacheManager;

    public CustomerServiceImpl(@Value("${backend.server.url}") String backendServerUrl, CacheManager cacheManager) {
        this.backendServerUrl = backendServerUrl;
        this.cacheManager = cacheManager;
    }

    @Override
    public Content<CustomerViewModel> getAll(int page, int size) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(backendServerUrl + BACKEND_CONTROLLER_URL_PREFIX + "?page=" + page + "&size=" + size, Content.class);
    }

    @Override
    public CustomerViewModel getCustomerById(Long id) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(backendServerUrl + BACKEND_CONTROLLER_URL_PREFIX + "/" + id, CustomerViewModel.class);
    }

    @Override
    public CustomerViewModel getCustomerByUserId(Long id) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(backendServerUrl + BACKEND_CONTROLLER_URL_PREFIX + "/users/" + id, CustomerViewModel.class);
    }

    @Override
    public CustomerViewModel saveCustomer(CustomerViewModel customer) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForEntity(backendServerUrl + BACKEND_CONTROLLER_URL_PREFIX, customer, CustomerViewModel.class).getBody();
    }

    @Override
    public void updateCustomer(Customer customer) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.put(backendServerUrl + BACKEND_CONTROLLER_URL_PREFIX, customer, CustomerViewModel.class);
    }

    @Override
    public void deleteCustomer(Long id) {
        CustomerViewModel customer = getCustomerById(id);
        Cache usersCache = cacheManager.getCache("usersCache");
        if (usersCache != null) {
            usersCache.evict(customer.getUser().getLogin());
        }

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(backendServerUrl + BACKEND_CONTROLLER_URL_PREFIX + "/" + id);
    }

    @Override
    public void updateCustomerDetails(CustomerViewModel customer) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.put(backendServerUrl + BACKEND_CONTROLLER_URL_PREFIX + "/details", customer);
    }
}
