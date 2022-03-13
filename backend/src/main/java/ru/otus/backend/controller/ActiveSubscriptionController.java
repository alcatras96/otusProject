package ru.otus.backend.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.backend.db.entity.ActiveSubscription;
import ru.otus.backend.service.api.ActiveSubscriptionService;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/active-subscriptions")
public class ActiveSubscriptionController {

    private final ActiveSubscriptionService activeSubscriptionService;

    @GetMapping(value = "/customers/{id}")
    public Iterable<ActiveSubscription> getActiveSubscriptionsByCustomerId(@PathVariable(name = "id") Long id) {
        return activeSubscriptionService.getActiveSubscriptionsByCustomerId(id);
    }

    @PostMapping
    public Iterable<ActiveSubscription> saveActiveSubscriptions(@RequestBody List<ActiveSubscription> activeSubscriptions) {
        return activeSubscriptionService.addActiveSubscriptions(activeSubscriptions);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteActiveSubscription(@PathVariable(name = "id") Long id) {
        activeSubscriptionService.deleteActiveSubscriptionById(id);
    }
}
