package ru.otus.apigateway.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.otus.apigateway.model.view.ActiveSubscriptionViewModel;
import ru.otus.apigateway.model.view.CustomerViewModel;
import ru.otus.apigateway.model.view.ListWrapper;
import ru.otus.apigateway.service.api.ActiveSubscriptionDataService;
import ru.otus.apigateway.service.api.CustomerDataService;
import ru.otus.apigateway.service.api.UserDataService;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/active_subscription")
public class ActiveSubscriptionController {

    private final ActiveSubscriptionDataService activeSubscriptionDataService;
    private final CustomerDataService customerDataService;
    private final UserDataService userDataService;

    @PostMapping
    public ResponseEntity<List<ActiveSubscriptionViewModel>> saveActiveSubscription(@Valid @RequestBody ListWrapper<ActiveSubscriptionViewModel> activeSubscriptionModels) {
        CustomerViewModel customer = customerDataService.getCustomerByUserId(Long.valueOf(userDataService.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName()).getId()));
        for (ActiveSubscriptionViewModel subscription : activeSubscriptionModels.getListWrapper()) {
            subscription.setLastEditDate(System.currentTimeMillis());
            subscription.setCustomerId(customer.getId());
        }
        return ResponseEntity.ok(activeSubscriptionDataService.saveActiveSubscriptions(activeSubscriptionModels.getListWrapper()));
    }

    @GetMapping(value = "/customer")
    public ResponseEntity<Iterable<ActiveSubscriptionViewModel>> getAllASByCustomerId() {
        CustomerViewModel customer = customerDataService.getCustomerByUserId(Long.valueOf(userDataService.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName()).getId()));
        return ResponseEntity.ok(activeSubscriptionDataService.getASByCustomerId(customer.getId()));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteActiveSubscription(@PathVariable(name = "id") Long id) {
        CustomerViewModel customer = customerDataService.getCustomerByUserId(Long.valueOf(userDataService.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName()).getId()));
        ActiveSubscriptionViewModel activeSubscription = activeSubscriptionDataService.getActiveSubscriptionById(id);
        if (activeSubscription.getCustomerId().equals(customer.getId())) {
            activeSubscriptionDataService.deleteActiveSubscriptionById(activeSubscription.getId());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
