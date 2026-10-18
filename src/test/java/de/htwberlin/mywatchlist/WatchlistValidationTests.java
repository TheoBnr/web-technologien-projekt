
package de.htwberlin.mywatchlist;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class WatchlistValidationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldRejectEmptyTitle() throws Exception {
        String json = """
                {
                    "title": "",
                    "type": "Movie",
                    "status": "Planned",
                    "rating": 8
                }
                """;

        mockMvc.perform(post("/watchlist")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldRejectRatingAboveTen() throws Exception {
        String json = """
                {
                    "title": "Interstellar",
                    "type": "Movie",
                    "status": "Planned",
                    "rating": 11
                }
                """;

        mockMvc.perform(post("/watchlist")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldRejectInvalidUpdate() throws Exception {
        String json = """
                {
                    "title": "Dark",
                    "type": "Series",
                    "status": "Completed",
                    "rating": 0
                }
                """;

        mockMvc.perform(put("/watchlist/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldRejectUnknownType() throws Exception {
        String json = """
            {
                "title": "Interstellar",
                "type": "Buch",
                "status": "Geplant",
                "rating": 8
            }
            """;

        mockMvc.perform(post("/watchlist")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldAcceptValidEntry() throws Exception {
        String json = """
            {
                "title": "Interstellar",
                "type": "Film",
                "status": "Geplant",
                "rating": 8
            }
            """;

        mockMvc.perform(post("/watchlist")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Interstellar"))
                .andExpect(jsonPath("$.type").value("Film"));
    }

    @Test
    void shouldRejectUnknownStatus() throws Exception {
        String json = """
            {
                "title": "Interstellar",
                "type": "Film",
                "status": "Pausiert",
                "rating": 8
            }
            """;

        mockMvc.perform(post("/watchlist")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldAcceptWatchingStatus() throws Exception {
        String json = """
            {
                "title": "Breaking Bad",
                "type": "Serie",
                "status": "Am Schauen",
                "rating": null
            }
            """;

        mockMvc.perform(post("/watchlist")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Breaking Bad"))
                .andExpect(jsonPath("$.status").value("Am Schauen"));
    }

    @Test
    void shouldReturnDetailedValidationErrors() throws Exception {
        String json = """
            {
                "title": "",
                "type": "Buch",
                "status": "Pausiert",
                "rating": 11
            }
            """;

        mockMvc.perform(post("/watchlist")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message")
                        .value("Ungültige Eingaben."))
                .andExpect(jsonPath("$.errors.title")
                        .value("Der Titel darf nicht leer sein."))
                .andExpect(jsonPath("$.errors.type")
                        .value("Der Typ muss Film oder Serie sein."))
                .andExpect(jsonPath("$.errors.status")
                        .exists())
                .andExpect(jsonPath("$.errors.rating")
                        .value("Die Bewertung darf maximal 10 sein."));
    }

    @Test
    void shouldRejectTitleLongerThan200Characters() throws Exception {
        String longTitle = "A".repeat(201);

        String json = """
            {
                "title": "%s",
                "type": "Film",
                "status": "Geplant",
                "rating": null
            }
            """.formatted(longTitle);

        mockMvc.perform(post("/watchlist")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.title")
                        .value("Der Titel darf maximal 200 Zeichen enthalten."));
    }

    @Test
    void shouldAcceptTitleWithExactly200Characters() throws Exception {
        String validTitle = "A".repeat(200);

        String json = """
            {
                "title": "%s",
                "type": "Film",
                "status": "Geplant",
                "rating": null
            }
            """.formatted(validTitle);

        mockMvc.perform(post("/watchlist")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value(validTitle));
    }

}
