package ru.otus.apigateway.facade.impl;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.otus.apigateway.constants.Constants;
import ru.otus.apigateway.converter.Converter;
import ru.otus.apigateway.facade.api.BillingAccountFacade;
import ru.otus.apigateway.model.backend.Customer;
import ru.otus.apigateway.model.view.*;
import ru.otus.apigateway.service.api.BillingAccountService;
import ru.otus.apigateway.service.api.CustomerService;
import ru.otus.apigateway.service.api.OwnerService;
import ru.otus.apigateway.service.api.UserService;

import java.util.Collection;

@Service
@AllArgsConstructor
public class BillingAccountFacadeImpl implements BillingAccountFacade {

    private final UserService userService;
    private final BillingAccountService billingAccountService;
    private final CustomerService customerService;
    private final OwnerService ownerService;
    private final Converter<CustomerViewModel, Customer> toCustomerConverter;

    @Override
    public BillingAccountViewModel saveBillingAccount(BillingAccountViewModel billingAccountViewModel) {
        Long currentUserId = userService.getCurrentUserByLogin().getId();
        if (isCurrentUserIsOwner()) {
            OwnerViewModel owner = ownerService.getOwnerByUserId(currentUserId);
            owner.setBillingAccount(billingAccountViewModel);
            return billingAccountService.saveOwnerBillingAccount(owner).getBillingAccount();
        } else {
            CustomerViewModel customer = customerService.getCustomerByUserId(currentUserId);
            customer.setBillingAccount(billingAccountViewModel);
            return billingAccountService.saveCustomerBillingAccount(customer).getBillingAccount();
        }
    }

    @Override
    public void addMoneyOnBillingAccount(Long value) {
        UserViewModel currentUser = userService.getCurrentUserByLogin();
        BillingAccountViewModel billingAccount;
        if (isCurrentUserIsOwner()) {
            OwnerViewModel owner = ownerService.getOwnerById(currentUser.getId());
            billingAccount = owner.getBillingAccount();
        } else {
            CustomerViewModel customerViewModel = customerService.getCustomerByUserId(currentUser.getId());
            billingAccount = customerViewModel.getBillingAccount();
            validateCustomerStatus(customerViewModel);
        }
        billingAccount.setBalance(billingAccount.getBalance() + value);
        billingAccountService.saveBillingAccount(billingAccount);
    }

    private void validateCustomerStatus(CustomerViewModel customerViewModel) {
        if (customerViewModel.getStatus() == Status.BLOCKED && customerViewModel.getBillingAccount().getBalance() > Constants.THRESHOLD) {
            customerViewModel.setStatus(Status.VALID);
            Customer customer = toCustomerConverter.convert(customerViewModel);
            customerService.updateCustomer(customer);
        }
    }

    private boolean isCurrentUserIsOwner() {
        Collection<? extends GrantedAuthority> grantedAuthorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        return grantedAuthorities.stream().map(GrantedAuthority::getAuthority).anyMatch("owner"::equals);
    }
}
