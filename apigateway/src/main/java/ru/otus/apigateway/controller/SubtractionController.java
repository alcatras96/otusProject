package ru.otus.apigateway.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.otus.apigateway.constants.Constants;
import ru.otus.apigateway.service.api.SubtractionService;

@RestController
@PreAuthorize("hasAnyAuthority('admin')")
@RequestMapping("/api/subtraction")
@AllArgsConstructor
public class SubtractionController {

    private final SubtractionService subtractionService;

    @PutMapping(value = "/threshold")
    public ResponseEntity<?> editThreshold(@RequestBody Integer threshold) {
        if (threshold < 0) {
            Constants.THRESHOLD = threshold;
            subtractionService.editThreshold(Constants.THRESHOLD);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(value = "/threshold")
    public Integer getThreshold() {
        Integer threshold = subtractionService.getThreshold();
        Constants.THRESHOLD = threshold;
        return threshold;
    }
}
