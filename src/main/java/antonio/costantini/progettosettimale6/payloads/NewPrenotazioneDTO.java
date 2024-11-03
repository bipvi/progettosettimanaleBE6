package antonio.costantini.progettosettimale6.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public record NewPrenotazioneDTO(
        String preferenze,
        @NotEmpty(message = "Il campo dipendenteId non può essere vuoto")
        int dipendentiId,
        @NotEmpty(message = "Devi inserire un viaggio")
        int viaggioId,
        @NotEmpty
        @Pattern(regexp = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$", message = "Il campo deve essere una data nel formato 'yyyy-mm-dd'")
        LocalDate data
) {
}
