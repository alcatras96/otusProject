package ru.otus.apigateway.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.apigateway.service.api.UserDataService;
import ru.otus.apigateway.model.view.UserViewModel;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

    private final UserDataService userDataService;

    @PreAuthorize("hasAnyAuthority('admin')")
    @RequestMapping
    public ResponseEntity<List<UserViewModel>> getAllUsers() {
        return ResponseEntity.ok(userDataService.getAll());
    }

    @PreAuthorize("hasAnyAuthority('admin')")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<UserViewModel> getUserById(@PathVariable(name = "id") Long id) {
        Optional<UserViewModel> user = userDataService.getUserById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasAnyAuthority('admin', 'owner', 'customer')")//Берется из контекста
    @RequestMapping(value = "userLogin/", method = RequestMethod.GET)
    public ResponseEntity<UserViewModel> getUser() {
        UserViewModel currentUser = userDataService.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName());
        if (currentUser != null) {
            return ResponseEntity.ok(currentUser);
        } else {
            return ResponseEntity.notFound().build();
        }

    }
}
