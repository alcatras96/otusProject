package ru.otus.apigateway.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.otus.apigateway.model.view.Content;
import ru.otus.apigateway.model.view.CustomerViewModel;
import ru.otus.apigateway.service.api.CustomerService;
import ru.otus.apigateway.service.api.UserDataService;
import ru.otus.apigateway.transfer.New;

@RestController
@RequestMapping("/api/customers")
@AllArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final UserDataService userDataService;
    private final PasswordEncoder passwordEncoder;

    @PreAuthorize("hasAnyAuthority('admin')")
    @RequestMapping(params = {"page", "size"})
    public ResponseEntity<Content<CustomerViewModel>> getAllCustomers(@RequestParam("page") int page, @RequestParam("size") int size) {
        return ResponseEntity.ok(customerService.getAll(page, size));
    }

    @PostMapping
    public ResponseEntity<CustomerViewModel> saveCustomer(@Validated(New.class) @RequestBody CustomerViewModel customer) {
        customer.getUser().setPassword(passwordEncoder.encode(customer.getUser().getPassword()));
        return ResponseEntity.ok(customerService.saveCustomer(customer));
    }

    //    @Validated(Exist.class) on customer
    @PreAuthorize("hasAnyAuthority('admin')")
    @PutMapping(value = "/details")
    public ResponseEntity<CustomerViewModel> updateCustomerDetails(@RequestBody CustomerViewModel customer) {
        customerService.updateCustomerDetails(customer);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyAuthority('admin')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<CustomerViewModel> getCustomerById(@PathVariable(name = "id") Long id) {
        CustomerViewModel customer = customerService.getCustomerById(id);
        if (customer != null) {
            return ResponseEntity.ok(customer);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasAnyAuthority('admin', 'customer')")
    @GetMapping(value = "/users")
    public ResponseEntity<CustomerViewModel> getCustomerByUserId() {
        CustomerViewModel customer = customerService.getCustomerByUserId(userDataService.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName()).getId());
        if (customer != null) {
            return ResponseEntity.ok(customer);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasAnyAuthority('admin')")
    @DeleteMapping(value = "/{id}")
    public void deleteCustomer(@PathVariable String id) {
        customerService.deleteCustomer(Long.valueOf(id));
    }

//    @PreAuthorize("hasAnyAuthority('customer')")
//    @PutMapping(value = "/ba/{value}")
//    public ResponseEntity<BillingAccountViewModel> addMoneyOnBillingAccount(@PathVariable(name = "value") int value) {
//        CustomerViewModel customerViewModel = customerDataService.getCustomerByUserId(userDataService.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName()).getId());
//        BillingAccountViewModel billingAccount = customerViewModel.getBillingAccount();
//        if (billingAccount == null || value <= 0) {
//            return ResponseEntity.badRequest().build();
//        }
//        billingAccount.setBalance(billingAccount.getBalance() + value);
//        billingAccountService.saveBillingAccount(billingAccount);
//        StatusViewModel status = customerViewModel.getStatus();
//        if (status.getName().equals("blocked") && billingAccount.getBalance() > Constants.THRESHOLD) {
//            status.setId(1L);
//            Customer customer = toCustomerConverter.convert(customerViewModel);
//            customerDataService.updateCustomer(customer);
//        }
//
//        return ResponseEntity.ok().build();
//    }
}
