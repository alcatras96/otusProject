package ru.otus.apigateway.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.apigateway.service.api.RoleDataService;
import ru.otus.apigateway.model.view.RoleViewModel;

import java.util.List;

@RestController
@PreAuthorize("hasAnyAuthority('admin')")
@RequestMapping("/api/roles")
@AllArgsConstructor
public class RoleController {

    private final RoleDataService roleDataService;

    @RequestMapping
    public ResponseEntity<List<RoleViewModel>> getAllRoles() {
        return ResponseEntity.ok(roleDataService.getAll());
    }

}