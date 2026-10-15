
package de.htwberlin.mywatchlist.controller;

import de.htwberlin.mywatchlist.model.WatchlistEntry;
import de.htwberlin.mywatchlist.service.WatchlistService;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@CrossOrigin(origins = {
        "http://localhost:5173",
        "https://mywatchlist-frontend.onrender.com"
})
public class WatchlistController {

    private final WatchlistService service;

    public WatchlistController(WatchlistService service) {
        this.service = service;
    }

    // Alle Einträge abrufen
    @GetMapping("/watchlist")
    public List<WatchlistEntry> getWatchlist() {
        return service.getAllEntries();
    }

    // Neuen Eintrag speichern
    @PostMapping("/watchlist")
    public ResponseEntity<WatchlistEntry> addEntry(
            @RequestBody WatchlistEntry entry) {

        WatchlistEntry savedEntry = service.addEntry(entry);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedEntry);
    }

    // Bestehenden Eintrag bearbeiten
    @PutMapping("/watchlist/{id}")
    public ResponseEntity<WatchlistEntry> updateEntry(
            @PathVariable Long id,
            @RequestBody WatchlistEntry entry) {

        WatchlistEntry updatedEntry =
                service.updateEntry(id, entry);

        return ResponseEntity.ok(updatedEntry);
    }

    // Eintrag löschen
    @DeleteMapping("/watchlist/{id}")
    public ResponseEntity<Void> deleteEntry(
            @PathVariable Long id) {

        service.deleteEntry(id);

        return ResponseEntity.noContent().build();
    }

}