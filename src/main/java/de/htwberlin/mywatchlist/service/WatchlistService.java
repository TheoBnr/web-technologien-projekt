
package de.htwberlin.mywatchlist.service;

import de.htwberlin.mywatchlist.model.WatchlistEntry;
import de.htwberlin.mywatchlist.repository.WatchlistRepository;
import org.springframework.stereotype.Service;

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
}
