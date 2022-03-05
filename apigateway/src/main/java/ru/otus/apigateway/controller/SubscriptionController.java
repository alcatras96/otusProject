package ru.otus.apigateway.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.otus.apigateway.model.view.Content;
import ru.otus.apigateway.model.view.OwnerViewModel;
import ru.otus.apigateway.model.view.SubscriptionViewModel;
import ru.otus.apigateway.service.api.OwnerDataService;
import ru.otus.apigateway.service.api.SubscriptionDataService;
import ru.otus.apigateway.service.api.UserDataService;

import java.util.List;

@RestController
@RequestMapping("/api/subscriptions")
@AllArgsConstructor
public class SubscriptionController {

    private final SubscriptionDataService subscriptionDataService;
    private final OwnerDataService ownerDataService;
    private final UserDataService userDataService;

    @RequestMapping(params = {"page", "size"})
    public ResponseEntity<Content> findAll(@RequestParam("page") int page, @RequestParam("size") int size) {
        return ResponseEntity.ok(subscriptionDataService.findAll(page, size));
    }

    @RequestMapping(value = "/category/{id}", params = {"page", "size"})
    public ResponseEntity<Content> findByCategoryId(@PathVariable("id") Long id, @RequestParam("page") int page, @RequestParam("size") int size) {
        return ResponseEntity.ok(subscriptionDataService.findByCategoryId(id, page, size));
    }

    @RequestMapping(value = "/search", params = {"name", "page", "size"})
    public ResponseEntity<Content> findByNameLike(@RequestParam("name") String name, @RequestParam("page") int page, @RequestParam("size") int size) {
        return ResponseEntity.ok(subscriptionDataService.findByNameLike(name, page, size));
    }

    @RequestMapping(value = "/owner/{ownerId}")
    public ResponseEntity<List<SubscriptionViewModel>> getSubscriptionsByOwnerId(@PathVariable(name = "ownerId") String id) {
        return ResponseEntity.ok(subscriptionDataService.findByOwnerId(Long.valueOf(id)));
    }

    @PreAuthorize("hasAnyAuthority('owner')")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<SubscriptionViewModel> saveSubscription(@RequestBody SubscriptionViewModel subscription) {
        OwnerViewModel owner = ownerDataService.getOwnerByUserId(Long.valueOf(userDataService.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName()).getId()));
        if (owner.getBillingAccount() == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(subscriptionDataService.saveSubscription(subscription));
    }

    @PreAuthorize("hasAnyAuthority('owner')")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteCustomer(@PathVariable String id) {
        subscriptionDataService.deleteSubscription(Long.valueOf(id));
    }

}
