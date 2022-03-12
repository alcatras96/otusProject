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
@RequestMapping("/api/basket-items")
public class BasketItemController {

    private final BasketItemService basketItemService;

    @GetMapping(value = "/count/customers/{id}")
    public Long getCount(@PathVariable(name = "id") Long id) {
        return basketItemService.getCountByCustomerId(id);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<BasketItem> getBasketItemById(@PathVariable(name = "id") Long id) {
        Optional<BasketItem> sb = basketItemService.getBasketItemById(id);
        return sb.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
    }

    @PostMapping
    public void saveBasketItems(@RequestBody List<BasketItem> basketItems) {
        basketItemService.saveBasketItems(basketItems);
    }

    @GetMapping(value = "/customers/{id}")
    public ResponseEntity<Iterable<BasketItem>> getBasketItemsByCustomerId(@PathVariable(name = "id") Long id) {
        Iterable<BasketItem> basketItems = basketItemService.findByCustomerId(id);
        if (basketItems != null) {
            return ResponseEntity.ok(basketItems);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping(value = "/{id}")
    public void deleteBasketItemById(@PathVariable(name = "id") Long id) {
        basketItemService.deleteBasketItem(id);
    }

    @DeleteMapping(value = "/customerS/{id}")
    public void deleteAllBasketItemsByCustomerId(@PathVariable(name = "id") Long id) {
        basketItemService.deleteAllBasketItemsByCustomerId(id);
    }
}
