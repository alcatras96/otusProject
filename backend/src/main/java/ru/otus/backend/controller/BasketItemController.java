package ru.otus.backend.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.backend.db.entity.BasketItem;
import ru.otus.backend.service.api.BasketItemService;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/api/basket_item")
public class BasketItemController {

    private final BasketItemService basketItemService;

    @GetMapping(value = "/count/{id}")
    public ResponseEntity<Long> getCount(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(basketItemService.getCountByCustomerId(id));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<BasketItem> getBasketItemById(@PathVariable(name = "id") Long id) {
        Optional<BasketItem> sb = basketItemService.getBasketItemById(id);
        return sb.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
    }

    @PostMapping
    public ResponseEntity<?> saveSb(@RequestBody List<BasketItem> Sb) {
        basketItemService.saveBasketItems(Sb);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/customer/{id}")
    public ResponseEntity<Iterable<BasketItem>> getBasketItemsByCustomerId(@PathVariable(name = "id") Long id) {
        Iterable<BasketItem> basketItems = basketItemService.findByCustomerId(id);
        if (basketItems != null) {
            return ResponseEntity.ok(basketItems);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteBasketItemById(@PathVariable(name = "id") Long id) {
        basketItemService.deleteBasketItem(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/customer/{id}")
    public ResponseEntity<?> deleteAllBasketItemsByCustomerId(@PathVariable(name = "id") Long id) {
        basketItemService.deleteAllBasketItemsByCustomerId(id);
        return ResponseEntity.noContent().build();
    }
}
