package ru.otus.apigateway.controller;

import lombok.AllArgsConstructor;
import ru.otus.apigateway.model.view.BillingAccountViewModel;
import ru.otus.apigateway.service.api.BillingAccountService;
import ru.otus.apigateway.transfer.Exist;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/billing_accounts")
public class BillingAccountController {

    private final BillingAccountService billingAccountService;

    @PreAuthorize("hasAnyAuthority('admin')")
    @RequestMapping
    public ResponseEntity<List<BillingAccountViewModel>> getAllBillingAccounts() {
        return ResponseEntity.ok(billingAccountService.getAllBillingAccounts());
    }

    @PreAuthorize("hasAnyAuthority('owner', 'customer')")
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<BillingAccountViewModel> saveEditedBa(@Validated(Exist.class) @RequestBody BillingAccountViewModel billingAccount) {
        billingAccountService.saveBillingAccount(billingAccount);
        return ResponseEntity.ok().build();
    }
}
