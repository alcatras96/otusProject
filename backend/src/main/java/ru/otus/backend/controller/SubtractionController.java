package ru.otus.backend.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.backend.constants.Constants;

@AllArgsConstructor
@RestController
@RequestMapping("/api/subtraction")
public class SubtractionController {

    @PutMapping(value = "/threshold/{value}")
    public ResponseEntity<Integer> editThreshold(@PathVariable(name = "value") Integer threshold) {
        if (threshold < 0) {
            Constants.THRESHOLD = threshold;
            return ResponseEntity.ok(Constants.THRESHOLD);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

}
