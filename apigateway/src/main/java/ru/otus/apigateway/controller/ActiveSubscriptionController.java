package ru.otus.apigateway.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.otus.apigateway.model.view.ActiveSubscriptionViewModel;
import ru.otus.apigateway.model.view.CustomerViewModel;
import ru.otus.apigateway.model.view.ListWrapper;
import ru.otus.apigateway.service.api.ActiveSubscriptionService;
import ru.otus.apigateway.service.api.CustomerService;
import ru.otus.apigateway.service.api.UserDataService;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/active-subscriptions")
public class ActiveSubscriptionController {

    private final ActiveSubscriptionService activeSubscriptionService;
    private final CustomerService customerService;
    private final UserDataService userDataService;

    @PostMapping
    public ResponseEntity<List<ActiveSubscriptionViewModel>> saveActiveSubscription(@Valid @RequestBody ListWrapper<ActiveSubscriptionViewModel> activeSubscriptionModels) {
        CustomerViewModel customer = customerService.getCustomerByUserId(userDataService.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName()).getId());
        for (ActiveSubscriptionViewModel subscription : activeSubscriptionModels.getListWrapper()) {
            subscription.setLastEditDate(System.currentTimeMillis());
            subscription.setCustomerId(customer.getId());
        }
        return ResponseEntity.ok(activeSubscriptionService.saveActiveSubscriptions(activeSubscriptionModels.getListWrapper()));
    }

    @GetMapping(value = "/customers")
    public ResponseEntity<Iterable<ActiveSubscriptionViewModel>> getAllActiveSubscriptionsByCustomerId() {
        CustomerViewModel customer = customerService.getCustomerByUserId(userDataService.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName()).getId());
        return ResponseEntity.ok(activeSubscriptionService.getASByCustomerId(customer.getId()));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteActiveSubscription(@PathVariable(name = "id") Long id) {
        CustomerViewModel customer = customerService.getCustomerByUserId(userDataService.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName()).getId());
        ActiveSubscriptionViewModel activeSubscription = activeSubscriptionService.getActiveSubscriptionById(id);
        if (activeSubscription.getCustomerId().equals(customer.getId())) {
            activeSubscriptionService.deleteActiveSubscriptionById(activeSubscription.getId());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
