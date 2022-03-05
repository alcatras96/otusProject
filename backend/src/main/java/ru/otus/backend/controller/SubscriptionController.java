package ru.otus.backend.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.backend.db.entity.Subscription;
import ru.otus.backend.service.api.SubscriptionService;

import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<Subscription> getSubscriptionById(@PathVariable(name = "id") Long id) {
        Optional<Subscription> subscriptionById = subscriptionService.getSubscriptionById(id);
        return subscriptionById.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
    }

    @GetMapping(params = {"page", "size"})
    public Iterable<Subscription> findSubscriptions(@RequestParam("page") int page, @RequestParam("size") int size, @RequestParam(value = "sort", required = false) String sort, @RequestParam(value = "direction", required = false) String direction) {
        if (sort != null && direction != null && (direction.equals("asc") || direction.equals("desc"))) {
            return subscriptionService.findAllWithSorting(page, size, Sort.by(Sort.Direction.fromString(direction), sort));
        } else {
            return subscriptionService.findAllWithSorting(page, size, Sort.unsorted());
        }
    }

    @GetMapping(value = "/owner/{ownerId}")
    public Iterable<Subscription> findByOwnerId(@PathVariable(name = "ownerId") Long id) {
        return subscriptionService.getByOwnerId(id);
    }

    @PostMapping
    public Subscription saveSubscription(@RequestBody Subscription subscription) {
        return subscriptionService.saveSubscription(subscription);
    }

    @PutMapping
    public Subscription saveEditedSubscription(@RequestBody Subscription subscription) {
        return subscriptionService.saveSubscription(subscription);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteSubscription(@PathVariable(name = "id") Long id) {
        subscriptionService.deleteSubscription(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping(value = "/search", params = {"name", "page", "size"})
    public Iterable<Subscription> findByNameLike(@RequestParam("name") String name, @RequestParam("page") int page, @RequestParam("size") int size) {
        return subscriptionService.findByNameContaining(name, page, size);
    }

    @GetMapping(value = "/category/{id}", params = {"page", "size"})
    public Iterable<Subscription> findByCategoryId(@PathVariable("id") Long id, @RequestParam("page") int page, @RequestParam("size") int size) {
        return subscriptionService.getByCategoryId(id, page, size);
    }
}
