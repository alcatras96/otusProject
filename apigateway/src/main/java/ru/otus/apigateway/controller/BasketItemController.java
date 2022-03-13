package ru.otus.apigateway.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.otus.apigateway.model.view.BasketItemViewModel;
import ru.otus.apigateway.model.view.CustomerViewModel;
import ru.otus.apigateway.model.view.ListWrapper;
import ru.otus.apigateway.service.api.BasketItemService;
import ru.otus.apigateway.service.api.CustomerService;
import ru.otus.apigateway.service.api.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/basket-items")
@AllArgsConstructor
public class BasketItemController {

    private final BasketItemService basketItemService;
    private final CustomerService customerService;
    private final UserService userService;

    @PreAuthorize("hasAnyAuthority('customer')")
    @PostMapping
    public ResponseEntity<?> saveBasketItems(@Valid @RequestBody ListWrapper<BasketItemViewModel> basketItemWrapper) {
        CustomerViewModel customer = customerService.getCustomerByUserId(userService.getCurrentUserByLogin().getId());
        if (customer.getId().equals(basketItemWrapper.getListWrapper().get(0).getCustomerId())) {
            basketItemService.saveBasketItem(basketItemWrapper.getListWrapper());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasAnyAuthority('admin')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<BasketItemViewModel> getBasketItemById(@PathVariable(name = "id") Long id) {
        BasketItemViewModel basketItem = basketItemService.getBasketItemById(id);
        if (basketItem != null) {
            return ResponseEntity.ok(basketItem);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasAnyAuthority('customer')")
    @GetMapping(value = "/count")
    public Long getCount() {
        CustomerViewModel customer = customerService.getCustomerByUserId(userService.getCurrentUserByLogin().getId());
        return basketItemService.getCount(customer.getId());
    }

    @PreAuthorize("hasAnyAuthority('customer')")
    @GetMapping(value = "/customers")
    public ResponseEntity<List<BasketItemViewModel>> getBasketItemsByCustomerId() {
        CustomerViewModel customer = customerService.getCustomerByUserId(userService.getCurrentUserByLogin().getId());
        List<BasketItemViewModel> basketItems = basketItemService.findByCustomerId(customer.getId());
        if (basketItems != null) {
            return ResponseEntity.ok(basketItems);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasAnyAuthority('customer')")
    @DeleteMapping(value = "/{id}")
    public void deleteBasketItem(@PathVariable Long id) {
        CustomerViewModel customer = customerService.getCustomerByUserId(userService.getCurrentUserByLogin().getId());
        BasketItemViewModel basketItem = basketItemService.getBasketItemById(id);
        if (basketItem.getCustomerId().equals(customer.getId())) {
            basketItemService.deleteBasketItem(id);
        }
    }

    @PreAuthorize("hasAnyAuthority('customer')")
    @DeleteMapping(value = "/customers")
    public void deleteBasketItemsByCustomerId() {
        CustomerViewModel customer = customerService.getCustomerByUserId(userService.getCurrentUserByLogin().getId());
        basketItemService.deleteBasketItemsByCustomerId(customer.getId());
    }
}
