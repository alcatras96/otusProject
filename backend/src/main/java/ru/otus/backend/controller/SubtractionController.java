package ru.otus.backend.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.backend.constants.Constants;

@AllArgsConstructor
@RestController
@RequestMapping("/api/subtraction")
public class SubtractionController {

    @PutMapping(value = "/threshold")
    public ResponseEntity<Integer> editThreshold(@RequestBody Integer threshold) {
        if (threshold < 0) {
            Constants.THRESHOLD = threshold;
            return ResponseEntity.ok(Constants.THRESHOLD);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(value = "/threshold")
    public Integer getThreshold() {
        return Constants.THRESHOLD;
    }
}
