package ru.otus.apigateway.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.otus.apigateway.model.view.Content;
import ru.otus.apigateway.model.view.CustomerViewModel;
import ru.otus.apigateway.service.api.CustomerDataService;

@Service
public class CustomerServiceImpl implements CustomerDataService {

    @Value("${backend.server.url}")
    private String backendServerUrl;

    @Override
    public Content<CustomerViewModel> getAll(int page, int size) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(backendServerUrl + "/api/customers?page=" + page + "&size=" + size, Content.class);
    }

    @Override
    public CustomerViewModel getCustomerById(Long id) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(backendServerUrl + "/api/customers/" + id, CustomerViewModel.class);
    }

    @Override
    public CustomerViewModel getCustomerByUserId(Long id) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(backendServerUrl + "/api/customers/user/" + id, CustomerViewModel.class);
    }


    @Override
    public CustomerViewModel saveCustomer(CustomerViewModel customer) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForEntity(backendServerUrl + "/api/customers", customer, CustomerViewModel.class).getBody();

    }

    @Override
    public void saveEditedCustomer(CustomerViewModel customer) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.put(backendServerUrl + "/api/customers", customer, CustomerViewModel.class);
    }

    @Override
    public void deleteCustomer(Long id) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(backendServerUrl + "/api/customers/" + id);
    }

    @Override
    public CustomerViewModel saveCustomerBa(CustomerViewModel customer) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForEntity(backendServerUrl + "/api/customers/ba", customer, CustomerViewModel.class).getBody();
    }

}
