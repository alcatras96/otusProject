package ru.otus.apigateway.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.otus.apigateway.model.view.BillingAccountViewModel;
import ru.otus.apigateway.model.view.CustomerViewModel;
import ru.otus.apigateway.model.view.OwnerViewModel;
import ru.otus.apigateway.service.api.BillingAccountService;
import ru.otus.apigateway.service.api.CustomerService;
import ru.otus.apigateway.service.api.OwnerService;
import ru.otus.apigateway.service.api.UserDataService;
import ru.otus.apigateway.transfer.Exist;
import ru.otus.apigateway.transfer.New;

@AllArgsConstructor
@RestController
@RequestMapping("/api/billing-accounts")
public class BillingAccountController {

    private final BillingAccountService billingAccountService;
    private final CustomerService customerService;
    private final OwnerService ownerService;
    private final UserDataService userDataService;

    @PreAuthorize("hasAnyAuthority('owner', 'customer')")
    @PutMapping
    public ResponseEntity<BillingAccountViewModel> saveBillingAccount(@Validated(Exist.class) @RequestBody BillingAccountViewModel billingAccount) {
        billingAccountService.saveBillingAccount(billingAccount);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyAuthority('customer')")
    @PostMapping(value = "/customers")
    public ResponseEntity<BillingAccountViewModel> saveCustomerBillingAccount(@Validated(New.class) @RequestBody BillingAccountViewModel billingAccountViewModel) {
        CustomerViewModel customer = customerService.getCustomerByUserId(
                userDataService.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName()).getId()
        );
        customer.setBillingAccount(billingAccountViewModel);
        return ResponseEntity.ok(billingAccountService.saveCustomerBillingAccount(customer).getBillingAccount());
    }

    @PreAuthorize("hasAnyAuthority('owner')")
    @PostMapping(value = "/owners")
    public ResponseEntity<BillingAccountViewModel> saveOwnerBillingAccount(@Validated(New.class) @RequestBody BillingAccountViewModel billingAccountViewModel) {
        OwnerViewModel owner = ownerService.getOwnerByUserId(
                userDataService.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName()).getId()
        );
        owner.setBillingAccount(billingAccountViewModel);
        return ResponseEntity.ok(billingAccountService.saveOwnerBillingAccount(owner).getBillingAccount());
    }
}
