package ru.otus.apigateway.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.otus.apigateway.facade.api.BillingAccountFacade;
import ru.otus.apigateway.model.view.BillingAccountViewModel;
import ru.otus.apigateway.validationgroup.New;

@AllArgsConstructor
@RestController
@RequestMapping("/api/billing-accounts")
public class BillingAccountController {

    private final BillingAccountFacade billingAccountFacade;

    @PreAuthorize("hasAnyAuthority('owner', 'customer')")
    @PostMapping
    public BillingAccountViewModel saveBillingAccount(@Validated(New.class) @RequestBody BillingAccountViewModel billingAccountViewModel) {
        return billingAccountFacade.saveBillingAccount(billingAccountViewModel);
    }

    @PreAuthorize("hasAnyAuthority('owner', 'customer')")
    @PutMapping(value = "/money/add")
    public void addMoneyOnBillingAccount(@RequestBody Long value) {
        billingAccountFacade.addMoneyOnBillingAccount(value);
    }
}
