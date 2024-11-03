package antonio.costantini.progettosettimale6.payloads;

import antonio.costantini.progettosettimale6.entities.Dipendente;
import antonio.costantini.progettosettimale6.entities.Stato;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;

public record NewViaggioDTO (
        @NotEmpty
        @Size(min = 1, message = "Il campo destionazione deve contenere almeno 1 carattere")
        String destinazione,
        @NotEmpty
        @Pattern(regexp = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$", message = "Il campo deve essere una data nel formato 'yyyy-mm-dd'")
        LocalDate data,
        @Pattern(regexp = "IN_PROGRAMMA|COMPLETATO", message = "Lo stato deve essere 'COMPLETATO' o 'IN_PROGRAMMA'") String stato){}
