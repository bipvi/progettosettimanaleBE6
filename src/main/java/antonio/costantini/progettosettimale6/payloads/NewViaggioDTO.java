package antonio.costantini.progettosettimale6.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

public record NewViaggioDTO (
        @NotEmpty
        @Size(min = 1, message = "Il campo destionazione deve contenere almeno 1 carattere")
        String descrizione,
        @NotEmpty
        LocalDate data,
        @NotEmpty@
){}
