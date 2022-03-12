package ru.otus.backend.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.backend.db.entity.Customer;
import ru.otus.backend.service.api.CustomerService;

import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable(name = "id") Long id) {
        Optional<Customer> customer = customerService.getCustomerById(id);
        return customer.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
    }

    @GetMapping(params = {"page", "size"})
    public Iterable<Customer> getAllCustomers(@RequestParam("page") int page, @RequestParam("size") int size) {
        return customerService.getAllCustomers(page, size);
    }

    @PostMapping
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerService.createCustomer(customer);
    }

    @PutMapping(value = "/details")
    public void saveOwnerDetails(@RequestBody Customer customer) {
        customerService.updateCustomerDetails(customer);
    }

    @PutMapping
    public Customer updateCustomer(@RequestBody Customer customer) {
        return customerService.updateCustomer(customer);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteCustomer(@PathVariable(name = "id") Long id) {
        customerService.deleteCustomer(id);
    }

    @GetMapping(value = "/users/{id}")
    public ResponseEntity<Customer> getCustomerByUserId(@PathVariable(name = "id") Long id) {
        Customer customer = customerService.findByUserId(id);
        if (customer != null) {
            return ResponseEntity.ok(customer);
        } else {
            return ResponseEntity.noContent().build();
        }
    }
}
