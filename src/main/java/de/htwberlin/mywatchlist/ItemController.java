package de.htwberlin.mywatchlist;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class ItemController {

    @GetMapping("/items")
    public List<Item> getItems() {
        return List.of(
                new Item(1, "Beispiel A"),
                new Item(2, "Beispiel B")
        );
    }

    public record Item(int id, String name) {}
}