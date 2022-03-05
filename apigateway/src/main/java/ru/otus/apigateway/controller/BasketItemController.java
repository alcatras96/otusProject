package ru.otus.apigateway.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.otus.apigateway.service.api.BasketItemDataService;
import ru.otus.apigateway.service.api.CustomerDataService;
import ru.otus.apigateway.service.api.UserDataService;
import ru.otus.apigateway.model.view.BasketItemViewModel;
import ru.otus.apigateway.model.view.CustomerViewModel;
import ru.otus.apigateway.model.view.ListWrapper;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/basket_item")
@AllArgsConstructor
public class BasketItemController {

    private final BasketItemDataService basketItemDataService;
    private final CustomerDataService customerDataService;
    private final UserDataService userDataService;

    @PreAuthorize("hasAnyAuthority('customer')")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> saveBasketItem(@Valid @RequestBody ListWrapper<BasketItemViewModel> basketItemWrapper) {
        CustomerViewModel customer = customerDataService.getCustomerByUserId(Long.valueOf(userDataService.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName()).getId()));
        if (customer.getId().equals(basketItemWrapper.getListWrapper().get(0).getCustomerId())) {
            basketItemDataService.saveBasketItem(basketItemWrapper.getListWrapper());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasAnyAuthority('admin')")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<BasketItemViewModel> getBasketItemById(@PathVariable(name = "id") Long id) {
        BasketItemViewModel sb = basketItemDataService.getBasketItemById(id);
        if (sb != null) {
            return ResponseEntity.ok(sb);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasAnyAuthority('customer')")
    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public ResponseEntity<Long> getCount() {
        CustomerViewModel customer = customerDataService.getCustomerByUserId(Long.valueOf(userDataService.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName()).getId()));
        return ResponseEntity.ok(basketItemDataService.getCount(customer.getId()));
    }

    @PreAuthorize("hasAnyAuthority('customer')")
    @RequestMapping(value = "/customer", method = RequestMethod.GET)
    public ResponseEntity<List<BasketItemViewModel>> getBasketItemsByCustomerId() {
        CustomerViewModel customer = customerDataService.getCustomerByUserId(Long.valueOf(userDataService.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName()).getId()));
        List<BasketItemViewModel> basketItems = basketItemDataService.findByCustomerId(customer.getId());
        if (basketItems != null) {
            return ResponseEntity.ok(basketItems);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasAnyAuthority('customer')")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteBasketItem(@PathVariable Long id) {
        CustomerViewModel customer = customerDataService.getCustomerByUserId(Long.valueOf(userDataService.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName()).getId()));
        BasketItemViewModel basketItem = basketItemDataService.getBasketItemById(id);
        if (basketItem.getCustomerId().equals(customer.getId())) {//КАСТОМЕР МОЖЕТ УДАЛЯТЬ ТОЛЬКО СВОИ ПОДПИСКИ В КОРЗИНЕ
            basketItemDataService.deleteBasketItem(id);
        }
    }

    @PreAuthorize("hasAnyAuthority('customer')")
    @RequestMapping(value = "/customer", method = RequestMethod.DELETE)
    public void deleteBasketItemByCustomerId() {
        CustomerViewModel customer = customerDataService.getCustomerByUserId(Long.valueOf(userDataService.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName()).getId()));
        basketItemDataService.deleteBasketItemByCustomerId(customer.getId());
    }
}
