package ru.otus.backend.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.backend.db.entity.Status;
import ru.otus.backend.service.api.StatusService;

import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/api/subscription_statuses")
public class StatusController {

    private final StatusService statusService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<Status> getSubscriptionStatusById(@PathVariable(name = "id") Long id) {
        Optional<Status> subscriptionStatusById = statusService.getStatusById(id);
        return subscriptionStatusById.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
    }

    @GetMapping
    public Iterable<Status> getAllSubscriptionStatuses() {
        return statusService.getAllStatuses();
    }
}
