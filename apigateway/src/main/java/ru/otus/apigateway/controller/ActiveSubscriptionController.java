package ru.otus.apigateway.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.apigateway.model.view.ActiveSubscriptionViewModel;
import ru.otus.apigateway.model.view.CustomerViewModel;
import ru.otus.apigateway.model.view.ListWrapper;
import ru.otus.apigateway.service.api.ActiveSubscriptionService;
import ru.otus.apigateway.service.api.CustomerService;
import ru.otus.apigateway.service.api.UserService;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/active-subscriptions")
public class ActiveSubscriptionController {

    private final ActiveSubscriptionService activeSubscriptionService;
    private final CustomerService customerService;
    private final UserService userService;

    @PostMapping
    public List<ActiveSubscriptionViewModel> saveActiveSubscription(@Valid @RequestBody ListWrapper<ActiveSubscriptionViewModel> activeSubscriptionModels) {
        CustomerViewModel customer = customerService.getCustomerByUserId(userService.getCurrentUserByLogin().getId());
        for (ActiveSubscriptionViewModel subscription : activeSubscriptionModels.getListWrapper()) {
            subscription.setLastEditDate(System.currentTimeMillis());
            subscription.setCustomerId(customer.getId());
        }
        return activeSubscriptionService.saveActiveSubscriptions(activeSubscriptionModels.getListWrapper());
    }

    @GetMapping(value = "/customers")
    public Iterable<ActiveSubscriptionViewModel> getAllActiveSubscriptionsByCustomerId() {
        CustomerViewModel customer = customerService.getCustomerByUserId(userService.getCurrentUserByLogin().getId());
        return activeSubscriptionService.getASByCustomerId(customer.getId());
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteActiveSubscription(@PathVariable(name = "id") Long id) {
        CustomerViewModel customer = customerService.getCustomerByUserId(userService.getCurrentUserByLogin().getId());
        ActiveSubscriptionViewModel activeSubscription = activeSubscriptionService.getActiveSubscriptionById(id);
        if (activeSubscription.getCustomerId().equals(customer.getId())) {
            activeSubscriptionService.deleteActiveSubscriptionById(activeSubscription.getId());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
