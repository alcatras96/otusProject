package ru.otus.apigateway.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.apigateway.model.view.UserViewModel;
import ru.otus.apigateway.service.api.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasAnyAuthority('admin')")
    @RequestMapping
    public List<UserViewModel> getAllUsers() {
        return userService.getAll();
    }

    @PreAuthorize("hasAnyAuthority('admin')")
    @GetMapping(value = "/{id}")
    public UserViewModel getUserById(@PathVariable(name = "id") Long id) {
        return userService.getUserById(id);
    }

    @PreAuthorize("hasAnyAuthority('admin', 'owner', 'customer')")
    @GetMapping(value = "login")
    public UserViewModel getUserByLogin() {
        return userService.getUserByLogin(SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
