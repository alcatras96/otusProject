package ru.otus.backend.service.impl;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.backend.db.entity.BillingAccount;
import ru.otus.backend.db.entity.Customer;
import ru.otus.backend.db.entity.User;
import ru.otus.backend.db.repository.CustomerRepository;
import ru.otus.backend.service.api.ActiveSubscriptionService;
import ru.otus.backend.service.api.BillingAccountService;
import ru.otus.backend.service.api.CustomerService;
import ru.otus.backend.service.api.UserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class CustomerServiceImpl implements CustomerService {

    private final UserService userService;
    private final CustomerRepository customerRepository;
    private final BillingAccountService billingAccountService;
    private final ActiveSubscriptionService activeSubscriptionService;

    @Override
    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    @Transactional
    @Override
    public Customer saveWithBillingAccount(Customer customer) {
        BillingAccount billingAccount = customer.getBillingAccount();
        billingAccountService.saveBillingAccount(billingAccount);
        customer.setBillingAccountId(billingAccount.getId());
        return createCustomer(customer);
    }

    @Override
    public Iterable<Customer> getAllCustomers(int page, int size) {
        Page<Customer> customers = customerRepository.findAll(PageRequest.of(page, size));

        List<Long> userIds = customers.getContent().stream().map(Customer::getUserId).collect(Collectors.toList());
        List<User> users = Lists.newArrayList(userService.getAllUsersById(userIds));

        customers.forEach(customer -> {
            User user = users.stream()
                    .filter(usr -> usr.getId().equals(customer.getUserId()))
                    .findAny()
                    .orElseThrow();

            customer.setUser(user);
        });

        return customers;
    }

    @Transactional
    @Override
    public Customer createCustomer(Customer customer) {
        customer.setStatusId(1L);
        User user = customer.getUser();
        user.setRoleId(user.getRole().getId());
        user = userService.saveUser(user);
        customer.setUserId(user.getId());
        return customerRepository.save(customer);
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Iterable<Customer> saveCustomers(Iterable<Customer> customers) {
        return customerRepository.saveAll(customers);
    }

    @Override
    public void saveCustomersStatus(Iterable<Customer> customers) {
        customers.forEach(customer -> customerRepository.saveCustomerStatus(customer.getId(), customer.getStatusId()));
    }

    @Override
    public void updateCustomerDetails(Customer customer) {
        Customer customerForUpdate = customerRepository.findById(customer.getId()).orElseThrow();
        customerForUpdate.setName(customer.getName());
        customerForUpdate.setAddress(customer.getAddress());
        customerRepository.save(customerForUpdate);

        userService.updateUserDetails(customer.getUser());
    }

    @Override
    public void deleteCustomer(Long id) {
        Customer customerForDelete = getCustomerById(id).orElseThrow();
        Long UserId = customerForDelete.getUserId();
        Long billingAccountId = customerForDelete.getBillingAccountId();
        activeSubscriptionService.deleteActiveSubscriptionsByCustomerId(id);

        customerRepository.delete(customerForDelete);
        userService.deleteUser(UserId);
        if (billingAccountId != null) {
            billingAccountService.deleteBillingAccountById(billingAccountId);
        }
    }

    @Override
    public Optional<Customer> findByUserId(Long id) {
        return customerRepository.findByUserId(id);
    }
}
