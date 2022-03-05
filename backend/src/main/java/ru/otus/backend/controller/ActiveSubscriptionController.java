package ru.otus.backend.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.backend.db.entity.ActiveSubscription;
import ru.otus.backend.service.api.ActiveSubscriptionService;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/api/active_subscription")
public class ActiveSubscriptionController {

    private final ActiveSubscriptionService activeSubscriptionService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<ActiveSubscription> getActiveSubscriptionById(@PathVariable(name = "id") Long id) {
        Optional<ActiveSubscription> activeSubscription = activeSubscriptionService.getActiveSubscriptionById(id);
        return activeSubscription
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    @GetMapping(value = "/customer/{id}")
    public ResponseEntity<Iterable<ActiveSubscription>> getActiveSubscriptionsByCustomerId(@PathVariable(name = "id") Long id) {
        Iterable<ActiveSubscription> activeSubscriptions = activeSubscriptionService.getActiveSubscriptionsByCustomerId(id);
        if (activeSubscriptions != null) {
            return ResponseEntity.ok(activeSubscriptions);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping
    public Iterable<ActiveSubscription> getAllActiveSubscriptions() {
        return activeSubscriptionService.getAllActiveSubscriptions();
    }

    @PostMapping
    public Iterable<ActiveSubscription> saveActiveSubscription(@RequestBody List<ActiveSubscription> activeSubscription) {
        return activeSubscriptionService.saveActiveSubscriptions(activeSubscription);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteActiveSubscription(@PathVariable(name = "id") Long id) {
        activeSubscriptionService.deleteActiveSubscriptionById(id);
        return ResponseEntity.noContent().build();
    }
}
