package de.htwberlin.mywatchlist.controller;

import de.htwberlin.mywatchlist.model.WatchlistEntry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class WatchlistController {

    @GetMapping("/watchlist")
    public List<WatchlistEntry> getWatchlist() {
        return List.of(
                new WatchlistEntry(
                        1L, "Breaking Bad", "Serie",
                        "Abgeschlossen", 10
                ),
                new WatchlistEntry(
                        2L, "Interstellar", "Film",
                        "Abgeschlossen", 9
                ),
                new WatchlistEntry(
                        3L, "Dark", "Serie",
                        "Geplant", null
                )
        );
    }
}