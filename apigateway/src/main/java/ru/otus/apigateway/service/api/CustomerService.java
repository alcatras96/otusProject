package ru.otus.apigateway.service.api;

import ru.otus.apigateway.model.view.Content;
import ru.otus.apigateway.model.view.CustomerViewModel;

public interface CustomerService {
    Content<CustomerViewModel> getAll(int page, int size);

    CustomerViewModel getCustomerById(Long id);

    CustomerViewModel getCustomerByUserId(Long id);

    CustomerViewModel saveCustomer(CustomerViewModel customer);

    void updateCustomerDetails(CustomerViewModel customer);

    void deleteCustomer(Long id);
}
