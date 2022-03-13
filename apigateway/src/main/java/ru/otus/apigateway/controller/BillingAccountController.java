package ru.otus.apigateway.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.otus.apigateway.constants.Constants;
import ru.otus.apigateway.converter.Converter;
import ru.otus.apigateway.model.backend.Customer;
import ru.otus.apigateway.model.view.*;
import ru.otus.apigateway.service.api.BillingAccountService;
import ru.otus.apigateway.service.api.CustomerService;
import ru.otus.apigateway.service.api.OwnerService;
import ru.otus.apigateway.service.api.UserService;
import ru.otus.apigateway.validationgroup.New;

import java.util.Collection;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/api/billing-accounts")
public class BillingAccountController {

    private final BillingAccountService billingAccountService;
    private final CustomerService customerService;
    private final OwnerService ownerService;
    private final UserService userService;
    private final Converter<CustomerViewModel, Customer> toCustomerConverter;

    @PreAuthorize("hasAnyAuthority('owner', 'customer')")
    @PostMapping
    public ResponseEntity<BillingAccountViewModel> saveBillingAccount(@Validated(New.class) @RequestBody BillingAccountViewModel billingAccountViewModel) {
        Collection<? extends GrantedAuthority> grantedAuthorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();

        UserViewModel currentUser = userService.getCurrentUserByLogin();
        boolean isOwner = grantedAuthorities.stream().map(GrantedAuthority::getAuthority).anyMatch("owner"::equals);
        if (isOwner) {
            CustomerViewModel customer = customerService.getCustomerByUserId(currentUser.getId());
            customer.setBillingAccount(billingAccountViewModel);
            return ResponseEntity.ok(billingAccountService.saveCustomerBillingAccount(customer).getBillingAccount());
        } else {
            OwnerViewModel owner = ownerService.getOwnerByUserId(currentUser.getId());
            owner.setBillingAccount(billingAccountViewModel);
            return ResponseEntity.ok(billingAccountService.saveOwnerBillingAccount(owner).getBillingAccount());
        }
    }

    @PreAuthorize("hasAnyAuthority('owner', 'customer')")
    @PutMapping(value = "/money/add")
    public ResponseEntity<?> addMoneyOnBillingAccount(@RequestBody Long value) {
        Collection<? extends GrantedAuthority> grantedAuthorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();

        boolean isOwner = grantedAuthorities.stream().map(GrantedAuthority::getAuthority).anyMatch("owner"::equals);

        UserViewModel currentUser = userService.getCurrentUserByLogin();
        if (isOwner) {
            Optional<OwnerViewModel> owner = ownerService.getOwnerById(currentUser.getId());
            BillingAccountViewModel billingAccount = owner.get().getBillingAccount();
            if (billingAccount == null || value <= 0) {
                return ResponseEntity.badRequest().build();
            }
            billingAccount.setBalance((int) (billingAccount.getBalance() + value));
            billingAccountService.saveBillingAccount(billingAccount);
        } else {
            CustomerViewModel customerViewModel = customerService.getCustomerByUserId(currentUser.getId());
            BillingAccountViewModel billingAccount = customerViewModel.getBillingAccount();
            if (billingAccount == null || value <= 0) {
                return ResponseEntity.badRequest().build();
            }
            billingAccount.setBalance((int) (billingAccount.getBalance() + value));
            billingAccountService.saveBillingAccount(billingAccount);
            StatusViewModel status = customerViewModel.getStatus();
            if (status.getName().equals("blocked") && billingAccount.getBalance() > Constants.THRESHOLD) {
                status.setId(1L);
                Customer customer = toCustomerConverter.convert(customerViewModel);
                customerService.updateCustomer(customer);
            }
        }

        return ResponseEntity.ok().build();
    }
}
