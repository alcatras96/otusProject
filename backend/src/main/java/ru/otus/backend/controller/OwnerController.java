package ru.otus.backend.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.backend.db.entity.Owner;
import ru.otus.backend.service.api.OwnerService;

import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/api/owners")
public class OwnerController {

    private final OwnerService ownerService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<Owner> getOwnerById(@PathVariable(name = "id") Long id) {
        Optional<Owner> ownerById = ownerService.getOwnerById(id);
        return ownerById.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
    }

    @GetMapping(params = {"page", "size"})
    public Iterable<Owner> getAllOwners(@RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {
        return ownerService.getAllOwners(page, size);
    }

    @PostMapping
    public Owner saveOwner(@RequestBody Owner owner) {
        return ownerService.saveOwner(owner);
    }

    @PutMapping(value = "/details")
    public void saveOwnerDetails(@RequestBody Owner owner) {
        ownerService.updateOwnerDetails(owner);
    }

    @PutMapping
    public Owner saveEditedOwner(@RequestBody Owner owner) {
        return ownerService.saveOwner(owner);
    }

    //todo return ok
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteOwner(@PathVariable(name = "id") Long id) {
        ownerService.deleteOwner(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/user/{id}")
    public ResponseEntity<Owner> getOwnerByUserId(@PathVariable(name = "id") Long id) {
        Owner owner = ownerService.findByUserId(id);
        if (owner != null) {
            return ResponseEntity.ok(owner);
        } else {
            return ResponseEntity.noContent().build();
        }
    }
}
