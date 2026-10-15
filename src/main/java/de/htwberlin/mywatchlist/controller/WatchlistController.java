package de.htwberlin.mywatchlist.controller;

import de.htwberlin.mywatchlist.model.WatchlistEntry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import java.util.List;

@RestController
@CrossOrigin(origins = {
        "http://localhost:5173",
        "https://mywatchlist-frontend.onrender.com"
})
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