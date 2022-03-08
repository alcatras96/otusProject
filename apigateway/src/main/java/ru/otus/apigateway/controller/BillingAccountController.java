package ru.otus.apigateway.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.apigateway.model.view.BillingAccountViewModel;
import ru.otus.apigateway.model.view.CustomerViewModel;
import ru.otus.apigateway.model.view.OwnerViewModel;
import ru.otus.apigateway.service.api.BillingAccountService;
import ru.otus.apigateway.service.api.CustomerDataService;
import ru.otus.apigateway.service.api.OwnerDataService;
import ru.otus.apigateway.service.api.UserDataService;
import ru.otus.apigateway.transfer.Exist;
import ru.otus.apigateway.transfer.New;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/billing_accounts")
public class BillingAccountController {

    private final BillingAccountService billingAccountService;
    private final CustomerDataService customerDataService;
    private final OwnerDataService ownerDataService;
    private final UserDataService userDataService;

    @PreAuthorize("hasAnyAuthority('admin')")
    @RequestMapping
    public ResponseEntity<List<BillingAccountViewModel>> getAllBillingAccounts() {
        return ResponseEntity.ok(billingAccountService.getAllBillingAccounts());
    }

    @PreAuthorize("hasAnyAuthority('owner', 'customer')")
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<BillingAccountViewModel> saveBillingAccount(@Validated(Exist.class) @RequestBody BillingAccountViewModel billingAccount) {
        billingAccountService.saveBillingAccount(billingAccount);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyAuthority('customer')")
    @RequestMapping(value = "/customer", method = RequestMethod.POST)
    public ResponseEntity<BillingAccountViewModel> saveCustomerBillingAccount(@Validated(New.class) @RequestBody BillingAccountViewModel billingAccountViewModel) {
        CustomerViewModel customer = customerDataService.getCustomerByUserId(
                Long.valueOf(userDataService.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName()).getId())
        );
        customer.setBillingAccount(billingAccountViewModel);
        return ResponseEntity.ok(billingAccountService.saveCustomerBillingAccount(customer).getBillingAccount());
    }

    @PreAuthorize("hasAnyAuthority('owner')")
    @RequestMapping(value = "/owner", method = RequestMethod.POST)
    public ResponseEntity<BillingAccountViewModel> saveOwnerBillingAccount(@Validated(New.class) @RequestBody BillingAccountViewModel billingAccountViewModel) {
        OwnerViewModel owner = ownerDataService.getOwnerByUserId(
                Long.valueOf(userDataService.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName()).getId())
        );
        owner.setBillingAccount(billingAccountViewModel);
        return ResponseEntity.ok(billingAccountService.saveOwnerBillingAccount(owner).getBillingAccount());
    }
}
