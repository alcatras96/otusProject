package ru.otus.apigateway.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.apigateway.constants.Constants;
import ru.otus.apigateway.service.api.SubtractionService;

@RestController
@PreAuthorize("hasAnyAuthority('admin')")
@RequestMapping("/api/subtraction")
@AllArgsConstructor
public class SubtractionController {

    private final SubtractionService subtractionService;

    @RequestMapping(value = "/threshold/{value}", method = RequestMethod.PUT)
    public ResponseEntity<?> editThreshold(@PathVariable(name = "value") Integer threshold) {
        if (threshold < 0) {
            Constants.THRESHOLD = threshold;
            subtractionService.editThreshold(Constants.THRESHOLD);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @RequestMapping(value = "/threshold")
    public ResponseEntity<Integer> getThreshold() {
            return ResponseEntity.ok(Constants.THRESHOLD);
    }

}
