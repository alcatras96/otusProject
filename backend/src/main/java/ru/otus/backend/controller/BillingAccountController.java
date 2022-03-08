package ru.otus.backend.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.backend.db.entity.BillingAccount;
import ru.otus.backend.db.entity.Customer;
import ru.otus.backend.db.entity.Owner;
import ru.otus.backend.service.api.BillingAccountService;
import ru.otus.backend.service.api.CustomerService;
import ru.otus.backend.service.api.OwnerService;

import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/api/billing_accounts")
public class BillingAccountController {

    private final BillingAccountService billingAccountService;
    private final CustomerService customerService;
    private final OwnerService ownerService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<BillingAccount> getBillingAccountById(@PathVariable(name = "id") Long id) {
        Optional<BillingAccount> billing_accountsOptional = billingAccountService.getBillingAccountById(id);
        return billing_accountsOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
    }

    @GetMapping
    public Iterable<BillingAccount> getAllBillingAccounts() {
        return billingAccountService.getAllBillingAccounts();
    }

    @PutMapping
    public BillingAccount updateBillingAccount(@RequestBody BillingAccount billingAccount) {
        return billingAccountService.saveBillingAccount(billingAccount);
    }

    @PostMapping(value = "/customer")
    public Customer saveCustomerBillingAccount(@RequestBody Customer customer) {
        return customerService.saveWithBillingAccount(customer);
    }

    @PostMapping(value = "/owner")
    public Owner saveOwnerBillingAccount(@RequestBody Owner owner) {
        return ownerService.saveWithBillingAccount(owner);
    }
}
