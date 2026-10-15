package de.htwberlin.mywatchlist.service;

import de.htwberlin.mywatchlist.model.WatchlistEntry;
import de.htwberlin.mywatchlist.repository.WatchlistRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WatchlistServiceTest {

    @Mock
    private WatchlistRepository repository;

    @InjectMocks
    private WatchlistService service;

    @Test
    void getAllEntries_returnsEntriesFromRepository() {
        WatchlistEntry interstellar = new WatchlistEntry(1L, "Interstellar", "Film", "Geplant", null);
        WatchlistEntry dark = new WatchlistEntry(2L, "Dark", "Serie", "Abgeschlossen", 9);
        when(repository.findAll()).thenReturn(List.of(interstellar, dark));

        List<WatchlistEntry> result = service.getAllEntries();

        assertEquals(2, result.size());
        assertEquals("Interstellar", result.get(0).getTitle());
        assertEquals("Dark", result.get(1).getTitle());
        verify(repository).findAll();
    }

    @Test
    void addEntry_clearsSuppliedIdAndSavesEntry() {
        WatchlistEntry newEntry = new WatchlistEntry(999L, "Inception", "Film", "Geplant", null);
        when(repository.save(any(WatchlistEntry.class))).thenAnswer(invocation -> {
            WatchlistEntry entryToSave = invocation.getArgument(0);
            // Eine neue ID muss von der Datenbank kommen, nicht vom Frontend.
            assertNull(entryToSave.getId());
            entryToSave.setId(3L); // simuliert die von PostgreSQL erzeugte ID
            return entryToSave;
        });

        WatchlistEntry result = service.addEntry(newEntry);

        assertEquals(Long.valueOf(3L), result.getId());
        assertEquals("Inception", result.getTitle());
        verify(repository).save(newEntry);
    }

    @Test
    void updateEntry_changesFieldsOfExistingEntry() {
        WatchlistEntry stored = new WatchlistEntry(1L, "Interstellar", "Film", "Geplant", null);
        WatchlistEntry changes = new WatchlistEntry(999L, "Interstellar", "Film", "Abgeschlossen", 10);
        when(repository.findById(1L)).thenReturn(Optional.of(stored));
        when(repository.save(stored)).thenReturn(stored);

        WatchlistEntry result = service.updateEntry(1L, changes);

        assertEquals(Long.valueOf(1L), result.getId()); // bestehende Datenbank-ID bleibt unverändert
        assertEquals("Abgeschlossen", result.getStatus());
        assertEquals(Integer.valueOf(10), result.getRating());
        verify(repository).save(stored);
    }

    @Test
    void updateEntry_returns404WhenEntryDoesNotExist() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> service.updateEntry(99L, new WatchlistEntry(null, "Test", "Film", "Geplant", null))
        );

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        verify(repository, never()).save(any());
    }

    @Test
    void deleteEntry_deletesExistingEntry() {
        WatchlistEntry existing = new WatchlistEntry(1L, "Interstellar", "Film", "Geplant", null);
        when(repository.findById(1L)).thenReturn(Optional.of(existing));

        service.deleteEntry(1L);

        verify(repository).delete(existing);
    }

    @Test
    void deleteEntry_returns404WhenEntryDoesNotExist() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> service.deleteEntry(99L)
        );

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        verify(repository, never()).delete(any());
    }
}
