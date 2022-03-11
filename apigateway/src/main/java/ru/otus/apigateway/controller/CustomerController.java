package ru.otus.apigateway.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.otus.apigateway.constants.Constants;
import ru.otus.apigateway.model.view.BillingAccountViewModel;
import ru.otus.apigateway.model.view.Content;
import ru.otus.apigateway.model.view.CustomerViewModel;
import ru.otus.apigateway.service.api.BillingAccountService;
import ru.otus.apigateway.service.api.CustomerDataService;
import ru.otus.apigateway.service.api.UserDataService;
import ru.otus.apigateway.transfer.Exist;
import ru.otus.apigateway.transfer.New;

@RestController
@RequestMapping("/api/customers")
@AllArgsConstructor
public class CustomerController {

    private final CustomerDataService customerDataService;
    private final UserDataService userDataService;
    private final PasswordEncoder passwordEncoder;
    private final BillingAccountService billingAccountService;

    @PreAuthorize("hasAnyAuthority('admin')")
    @RequestMapping(params = {"page", "size"})
    public ResponseEntity<Content<CustomerViewModel>> getAllCustomers(@RequestParam("page") int page, @RequestParam("size") int size) {
        return ResponseEntity.ok(customerDataService.getAll(page, size));
    }

    @PostMapping
    public ResponseEntity<CustomerViewModel> saveCustomer(@Validated(New.class) @RequestBody CustomerViewModel customer) {
        customer.getUser().setPassword(passwordEncoder.encode(customer.getUser().getPassword()));
        return ResponseEntity.ok(customerDataService.saveCustomer(customer));
    }


    @PutMapping
    public ResponseEntity<CustomerViewModel> saveEditedCustomer(@Validated(Exist.class) @RequestBody CustomerViewModel customer) {
        customerDataService.saveEditedCustomer(customer);
        return ResponseEntity.ok().build();
    }

//    @Validated(Exist.class) on customer
    @PreAuthorize("hasAnyAuthority('admin')")
    @PutMapping(value = "/details")
    public ResponseEntity<CustomerViewModel> updateCustomerDetails(@RequestBody CustomerViewModel customer) {
        customerDataService.updateCustomerDetails(customer);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyAuthority('admin')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<CustomerViewModel> getCustomerById(@PathVariable(name = "id") Long id) {
        CustomerViewModel customer = customerDataService.getCustomerById(id);
        if (customer != null) {
            return ResponseEntity.ok(customer);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasAnyAuthority('admin', 'customer')")
    @GetMapping(value = "/user/")
    public ResponseEntity<CustomerViewModel> getCustomerByUserId() {
        CustomerViewModel customer = customerDataService.getCustomerByUserId(Long.valueOf(userDataService.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName()).getId()));
        if (customer != null) {
            return ResponseEntity.ok(customer);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasAnyAuthority('admin')")
    @DeleteMapping(value = "/{id}")
    public void deleteCustomer(@PathVariable String id) {
        customerDataService.deleteCustomer(Long.valueOf(id));
    }

    @PreAuthorize("hasAnyAuthority('customer')")
    @PutMapping(value = "/ba/{value}")
    public ResponseEntity<BillingAccountViewModel> saveEditedBa(@PathVariable(name = "value") int value) {
        CustomerViewModel customer = customerDataService.getCustomerByUserId(Long.valueOf(userDataService.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName()).getId()));
        if (customer.getBillingAccount() == null || value <= 0) {
            return ResponseEntity.badRequest().build();
        }
        customer.getBillingAccount().setBalance(customer.getBillingAccount().getBalance() + value);
        billingAccountService.saveBillingAccount(customer.getBillingAccount());
        if (customer.getStatus().getName().equals("blocked") && customer.getBillingAccount().getBalance() > Constants.THRESHOLD) {//Проверяем пополнил ли кастомер кошелек и если закрыл долг то делаем valid
            customer.getStatus().setId(1);
            customerDataService.saveEditedCustomer(customer);
        }

        return ResponseEntity.ok().build();
    }
}
