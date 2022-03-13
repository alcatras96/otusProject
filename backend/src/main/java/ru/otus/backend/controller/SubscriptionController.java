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
        return subscriptionById.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(params = {"page", "size"})
    public Iterable<Subscription> findSubscriptions(@RequestParam("page") Integer page, @RequestParam("size") Integer size,
                                                    @RequestParam(value = "sort", required = false) String sort,
                                                    @RequestParam(value = "direction", required = false) String direction) {
        Sort sortingOptions;
        if (sort != null && direction != null && (direction.equals("asc") || direction.equals("desc"))) {
            sortingOptions = Sort.by(Sort.Direction.fromString(direction), sort);
        } else {
            sortingOptions = Sort.unsorted();
        }
        return subscriptionService.findAllWithSorting(page, size, sortingOptions);
    }

    @GetMapping(value = "/owners/{ownerId}")
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
    public void deleteSubscription(@PathVariable(name = "id") Long id) {
        subscriptionService.deleteSubscription(id);
    }

    @GetMapping(value = "/search", params = {"name", "page", "size"})
    public Iterable<Subscription> findByNameLike(@RequestParam("name") String name, @RequestParam("page") Integer page,
                                                 @RequestParam("size") Integer size) {
        return subscriptionService.findByNameContaining(name, page, size);
    }

    @GetMapping(value = "/categories/{id}", params = {"page", "size"})
    public Iterable<Subscription> findByCategoryId(@PathVariable("id") Long id, @RequestParam("page") Integer page,
                                                   @RequestParam("size") Integer size) {
        return subscriptionService.getByCategoryId(id, page, size);
    }
}
