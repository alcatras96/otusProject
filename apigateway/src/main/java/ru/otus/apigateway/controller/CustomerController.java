package ru.otus.apigateway.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.otus.apigateway.model.view.Content;
import ru.otus.apigateway.model.view.CustomerViewModel;
import ru.otus.apigateway.service.api.CustomerService;
import ru.otus.apigateway.service.api.UserService;
import ru.otus.apigateway.validationgroup.New;

@RestController
@RequestMapping("/api/customers")
@AllArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @PreAuthorize("hasAnyAuthority('admin')")
    @RequestMapping(params = {"page", "size"})
    public Content<CustomerViewModel> getAllCustomers(@RequestParam("page") int page, @RequestParam("size") int size) {
        return customerService.getAll(page, size);
    }

    @PostMapping
    public CustomerViewModel saveCustomer(@Validated(New.class) @RequestBody CustomerViewModel customer) {
        customer.getUser().setPassword(passwordEncoder.encode(customer.getUser().getPassword()));
        return customerService.saveCustomer(customer);
    }

    //    @Validated(Exist.class) on customer
    @PreAuthorize("hasAnyAuthority('admin')")
    @PutMapping(value = "/details")
    public void updateCustomerDetails(@RequestBody CustomerViewModel customer) {
        customerService.updateCustomerDetails(customer);
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
        CustomerViewModel customer = customerService.getCustomerByUserId(userService.getCurrentUserByLogin().getId());
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
}
