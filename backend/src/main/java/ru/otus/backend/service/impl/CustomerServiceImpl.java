package ru.otus.backend.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.backend.db.entity.Customer;
import ru.otus.backend.db.entity.User;
import ru.otus.backend.db.repository.CustomerRepository;
import ru.otus.backend.service.api.BillingAccountService;
import ru.otus.backend.service.api.CustomerService;
import ru.otus.backend.service.api.StatusService;
import ru.otus.backend.service.api.UserService;

import java.util.Optional;

@AllArgsConstructor
@Component
public class CustomerServiceImpl implements CustomerService {

    private final UserService userService;
    private final CustomerRepository customerRepository;
    private final StatusService statusService;
    private final BillingAccountService billingAccountService;

    @Override
    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    @Override
    public Iterable<Customer> getAllCustomers(int page, int size) {
        return customerRepository.findAll(PageRequest.of(page, size));
    }

    @Transactional
    @Override
    public Customer saveCustomer(Customer customer) {
        customer.setStatusId(1L);
        User user = customer.getUser();
        user.setRoleId(user.getRole().getId());
        user = userService.saveUser(user);
        customer.setUserId(user.getId());
        return customerRepository.save(customer);
    }

    @Override
    public Customer saveEditedCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public void deleteCustomer(Long id) {
        Customer deletedCustomer = getCustomerById(id).get();
        Long UserId = deletedCustomer.getUser().getId();
        if (deletedCustomer.getBillingAccount() != null) {
            billingAccountService.deleteBa(deletedCustomer.getBillingAccount());
        }
        userService.deleteUser(UserId);
    }

    @Override
    public Customer findByUserId(Long id) {
        return customerRepository.findByUserId(id);
    }
}
