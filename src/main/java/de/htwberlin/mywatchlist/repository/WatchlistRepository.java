package de.htwberlin.mywatchlist.repository;

import de.htwberlin.mywatchlist.model.WatchlistEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WatchlistRepository
        extends JpaRepository<WatchlistEntry, Long> {
}
