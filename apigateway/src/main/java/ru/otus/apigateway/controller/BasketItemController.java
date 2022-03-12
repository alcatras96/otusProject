package ru.otus.apigateway.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.otus.apigateway.model.view.BasketItemViewModel;
import ru.otus.apigateway.model.view.CustomerViewModel;
import ru.otus.apigateway.model.view.ListWrapper;
import ru.otus.apigateway.service.api.BasketItemService;
import ru.otus.apigateway.service.api.CustomerService;
import ru.otus.apigateway.service.api.UserDataService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/basket-items")
@AllArgsConstructor
public class BasketItemController {

    private final BasketItemService basketItemService;
    private final CustomerService customerService;
    private final UserDataService userDataService;

    @PreAuthorize("hasAnyAuthority('customer')")
    @PostMapping
    public ResponseEntity<?> saveBasketItems(@Valid @RequestBody ListWrapper<BasketItemViewModel> basketItemWrapper) {
        CustomerViewModel customer = customerService.getCustomerByUserId(userDataService.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName()).getId());
        if (customer.getId().equals(basketItemWrapper.getListWrapper().get(0).getCustomerId())) {
            basketItemService.saveBasketItem(basketItemWrapper.getListWrapper());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasAnyAuthority('admin')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<BasketItemViewModel> getBasketItemById(@PathVariable(name = "id") Long id) {
        BasketItemViewModel sb = basketItemService.getBasketItemById(id);
        if (sb != null) {
            return ResponseEntity.ok(sb);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasAnyAuthority('customer')")
    @GetMapping(value = "/count")
    public ResponseEntity<Long> getCount() {
        CustomerViewModel customer = customerService.getCustomerByUserId(userDataService.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName()).getId());
        return ResponseEntity.ok(basketItemService.getCount(customer.getId()));
    }

    @PreAuthorize("hasAnyAuthority('customer')")
    @GetMapping(value = "/customer")
    public ResponseEntity<List<BasketItemViewModel>> getBasketItemsByCustomerId() {
        CustomerViewModel customer = customerService.getCustomerByUserId(userDataService.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName()).getId());
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
        CustomerViewModel customer = customerService.getCustomerByUserId(userDataService.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName()).getId());
        BasketItemViewModel basketItem = basketItemService.getBasketItemById(id);
        if (basketItem.getCustomerId().equals(customer.getId())) {
            basketItemService.deleteBasketItem(id);
        }
    }

    @PreAuthorize("hasAnyAuthority('customer')")
    @DeleteMapping(value = "/customer")
    public void deleteBasketItemByCustomerId() {
        CustomerViewModel customer = customerService.getCustomerByUserId(userDataService.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName()).getId());
        basketItemService.deleteBasketItemByCustomerId(customer.getId());
    }
}
