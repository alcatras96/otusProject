package ru.otus.apigateway.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.otus.apigateway.model.view.Content;
import ru.otus.apigateway.model.view.OwnerViewModel;
import ru.otus.apigateway.service.api.OwnerDataService;
import ru.otus.apigateway.service.api.UserDataService;
import ru.otus.apigateway.transfer.Exist;
import ru.otus.apigateway.transfer.New;

import java.util.Optional;

@RestController
@RequestMapping("/api/owners")
@AllArgsConstructor
public class OwnerController {

    private final OwnerDataService ownerDataService;
    private final UserDataService userDataService;
    private final PasswordEncoder passwordEncoder;

    @PreAuthorize("hasAnyAuthority('admin')")
    @GetMapping(params = {"page", "size"})
    public ResponseEntity<Content<OwnerViewModel>> getAllOwners(@RequestParam("page") int page, @RequestParam("size") int size) {
        return ResponseEntity.ok(ownerDataService.getAll(page, size));
    }


    @PostMapping
    public ResponseEntity<OwnerViewModel> saveOwner(@Validated(New.class) @RequestBody OwnerViewModel owner) {
        owner.getUser().setPassword(passwordEncoder.encode(owner.getUser().getPassword()));
        return ResponseEntity.ok(ownerDataService.saveOwner(owner));
    }

    @PreAuthorize("hasAnyAuthority('admin')")
    @PutMapping
    public ResponseEntity<OwnerViewModel> saveEditedOwner(@Validated(Exist.class) @RequestBody OwnerViewModel owner) {
        ownerDataService.saveEditedOwner(owner);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyAuthority('admin')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<OwnerViewModel> getOwnerById(@PathVariable(name = "id") Long id) {
        Optional<OwnerViewModel> owner = ownerDataService.getOwnerById(id);
        return owner.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasAnyAuthority('admin', 'owner')")
    @GetMapping(value = "/user/")
    public ResponseEntity<OwnerViewModel> getOwnerByUserId() {
        OwnerViewModel owner = ownerDataService.getOwnerByUserId(Long.valueOf(userDataService.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName()).getId()));
        if (owner != null) {
            return ResponseEntity.ok(owner);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasAnyAuthority('admin')")
    @DeleteMapping(value = "/{id}")
    public void deleteOwner(@PathVariable String id) {
        ownerDataService.deleteOwner(Long.valueOf(id));
    }

    @PreAuthorize("hasAnyAuthority('admin')")
    //    @Validated(Exist.class) on owner
    @PutMapping(value = "/details")
    public ResponseEntity<OwnerViewModel> updateOwnerDetails(@RequestBody OwnerViewModel owner) {
        ownerDataService.updateOwnerDetails(owner);
        return ResponseEntity.ok().build();
    }
}
