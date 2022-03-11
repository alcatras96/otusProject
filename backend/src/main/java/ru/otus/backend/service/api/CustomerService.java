package ru.otus.backend.service.api;

import ru.otus.backend.db.entity.Customer;

import java.util.Optional;

public interface CustomerService {

    Optional<Customer> getCustomerById(Long id);

    Customer saveWithBillingAccount(Customer customer);

    Iterable<Customer> getAllCustomers(int page, int size);

    Customer saveCustomer(Customer customer);

    Customer updateCustomerStatus(Long id, Long statusId);

    void updateCustomerDetails(Customer customer);

    void deleteCustomer(Long id);

    Customer findByUserId(Long id);

}
