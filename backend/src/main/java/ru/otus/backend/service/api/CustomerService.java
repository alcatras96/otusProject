package ru.otus.backend.service.api;

import ru.otus.backend.db.entity.Customer;

import java.util.Optional;

public interface CustomerService {

    Optional<Customer> getCustomerById(Long id);

    Iterable<Customer> getCustomersById(Iterable<Long> ids);

    Customer saveWithBillingAccount(Customer customer);

    Iterable<Customer> getAllCustomers(int page, int size);

    Customer createCustomer(Customer customer);

    Customer updateCustomer(Customer customer);

    Iterable<Customer> saveCustomers(Iterable<Customer> customers);

    void updateCustomerDetails(Customer customer);

    void deleteCustomer(Long id);

    Optional<Customer> findByUserId(Long id);

}
