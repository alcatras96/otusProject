package ru.otus.apigateway.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.otus.apigateway.model.view.Content;
import ru.otus.apigateway.model.view.OwnerViewModel;
import ru.otus.apigateway.service.api.OwnerService;
import ru.otus.apigateway.service.api.UserService;
import ru.otus.apigateway.validationgroup.Exist;
import ru.otus.apigateway.validationgroup.New;

import java.util.Optional;

@RestController
@RequestMapping("/api/owners")
@AllArgsConstructor
public class OwnerController {

    private final OwnerService ownerService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @PreAuthorize("hasAnyAuthority('admin')")
    @GetMapping(params = {"page", "size"})
    public Content<OwnerViewModel> getAllOwners(@RequestParam("page") int page, @RequestParam("size") int size) {
        return ownerService.getAll(page, size);
    }

    @PostMapping
    public OwnerViewModel saveOwner(@Validated(New.class) @RequestBody OwnerViewModel owner) {
        owner.getUser().setPassword(passwordEncoder.encode(owner.getUser().getPassword()));
        return ownerService.saveOwner(owner);
    }

    @PreAuthorize("hasAnyAuthority('admin')")
    @PutMapping
    public void saveEditedOwner(@Validated(Exist.class) @RequestBody OwnerViewModel owner) {
        ownerService.saveEditedOwner(owner);
    }

    @PreAuthorize("hasAnyAuthority('admin')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<OwnerViewModel> getOwnerById(@PathVariable(name = "id") Long id) {
        Optional<OwnerViewModel> owner = ownerService.getOwnerById(id);
        return owner.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasAnyAuthority('admin', 'owner')")
    @GetMapping(value = "/users")
    public ResponseEntity<OwnerViewModel> getOwnerByUserId() {
        OwnerViewModel owner = ownerService.getOwnerByUserId(userService.getCurrentUserByLogin().getId());
        if (owner != null) {
            return ResponseEntity.ok(owner);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasAnyAuthority('admin')")
    @DeleteMapping(value = "/{id}")
    public void deleteOwner(@PathVariable String id) {
        ownerService.deleteOwner(Long.valueOf(id));
    }

    //    @Validated(Exist.class) on owner
    @PreAuthorize("hasAnyAuthority('admin')")
    @PutMapping(value = "/details")
    public void updateOwnerDetails(@RequestBody OwnerViewModel owner) {
        ownerService.updateOwnerDetails(owner);
    }
}
