package ru.otus.apigateway.converter.impl;

import org.springframework.stereotype.Component;
import ru.otus.apigateway.converter.Converter;
import ru.otus.apigateway.model.backend.Customer;
import ru.otus.apigateway.model.view.CustomerViewModel;

@Component
public class CustomerViewModelToCustomerConverter implements Converter<CustomerViewModel, Customer> {

    @Override
    public Customer convert(CustomerViewModel customerViewModel) {
        return Customer.builder()
                .id(customerViewModel.getId())
                .address(customerViewModel.getAddress())
                .billingAccountId(customerViewModel.getBillingAccount().getId())
                .name(customerViewModel.getName())
                .statusId(customerViewModel.getStatus().getId())
                .userId(customerViewModel.getUser().getId())
                .build();
    }
}
