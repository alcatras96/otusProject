package ru.otus.apigateway.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.otus.apigateway.model.view.Content;
import ru.otus.apigateway.model.view.OwnerViewModel;
import ru.otus.apigateway.model.view.SubscriptionViewModel;
import ru.otus.apigateway.service.api.OwnerService;
import ru.otus.apigateway.service.api.SubscriptionService;
import ru.otus.apigateway.service.api.UserDataService;

import java.util.List;

@RestController
@RequestMapping("/api/subscriptions")
@AllArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;
    private final OwnerService ownerService;
    private final UserDataService userDataService;

    @GetMapping(params = {"page", "size"})
    public Content<SubscriptionViewModel> findAll(@RequestParam("page") int page, @RequestParam("size") int size) {
        return subscriptionService.findAll(page, size);
    }

    @GetMapping(value = "/categories/{id}", params = {"page", "size"})
    public Content<SubscriptionViewModel> findByCategoryId(@PathVariable("id") Long id, @RequestParam("page") int page, @RequestParam("size") int size) {
        return subscriptionService.findByCategoryId(id, page, size);
    }

    @GetMapping(value = "/search", params = {"name", "page", "size"})
    public Content<SubscriptionViewModel> findByNameLike(@RequestParam("name") String name, @RequestParam("page") int page, @RequestParam("size") int size) {
        return subscriptionService.findByNameLike(name, page, size);
    }

    @GetMapping(value = "/owners/{id}")
    public List<SubscriptionViewModel> getSubscriptionsByOwnerId(@PathVariable(name = "id") String id) {
        return subscriptionService.findByOwnerId(Long.valueOf(id));
    }

    @PreAuthorize("hasAnyAuthority('owner')")
    @PostMapping
    public ResponseEntity<SubscriptionViewModel> saveSubscription(@RequestBody SubscriptionViewModel subscription) {
        OwnerViewModel owner = ownerService.getOwnerByUserId(userDataService.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName()).getId());
        if (owner.getBillingAccount() == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(subscriptionService.saveSubscription(subscription));
    }

    @PreAuthorize("hasAnyAuthority('owner')")
    @DeleteMapping(value = "/{id}")
    public void deleteCustomer(@PathVariable String id) {
        subscriptionService.deleteSubscription(Long.valueOf(id));
    }
}
