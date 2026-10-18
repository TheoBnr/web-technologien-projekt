
package de.htwberlin.mywatchlist.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record WatchlistEntryRequest(

        @NotBlank(message = "Der Titel darf nicht leer sein.")
        @Size(
                max = 200,
                message = "Der Titel darf maximal 200 Zeichen enthalten."
        )
        String title,

        @NotBlank(message = "Der Typ muss angegeben werden.")
        @Pattern(
                regexp = "Film|Serie",
                message = "Der Typ muss Film oder Serie sein."
        )
        String type,

        @NotBlank(message = "Der Status muss angegeben werden.")
        @Pattern(
                regexp = "Geplant|Am Schauen|Abgeschlossen",
                message = "Ungültiger Status. Erlaubt sind Geplant, Am Schauen oder Abgeschlossen."
        )
        String status,

        @Min(value = 1, message = "Die Bewertung muss mindestens 1 sein.")
        @Max(value = 10, message = "Die Bewertung darf maximal 10 sein.")
        Integer rating

) {}
