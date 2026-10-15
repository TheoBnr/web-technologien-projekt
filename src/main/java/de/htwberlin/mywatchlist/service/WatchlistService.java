
package de.htwberlin.mywatchlist.service;

import de.htwberlin.mywatchlist.model.WatchlistEntry;
import de.htwberlin.mywatchlist.repository.WatchlistRepository;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class WatchlistService {

    private final WatchlistRepository repository;

    public WatchlistService(WatchlistRepository repository) {
        this.repository = repository;
    }

    public List<WatchlistEntry> getAllEntries() {
        return repository.findAll();
    }

    public WatchlistEntry addEntry(WatchlistEntry entry) {
        entry.setId(null);
        return repository.save(entry);
    }

    public WatchlistEntry updateEntry(
            Long id, WatchlistEntry updatedEntry) {

        WatchlistEntry existingEntry = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Eintrag nicht gefunden"
                ));

        existingEntry.setTitle(updatedEntry.getTitle());
        existingEntry.setType(updatedEntry.getType());
        existingEntry.setStatus(updatedEntry.getStatus());
        existingEntry.setRating(updatedEntry.getRating());

        return repository.save(existingEntry);
    }

    public void deleteEntry(Long id) {
        WatchlistEntry entry = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Eintrag nicht gefunden"
                ));

        repository.delete(entry);
    }
}
