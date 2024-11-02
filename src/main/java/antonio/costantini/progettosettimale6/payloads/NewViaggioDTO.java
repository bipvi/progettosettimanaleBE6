package antonio.costantini.progettosettimale6.payloads;

import antonio.costantini.progettosettimale6.entities.Dipendente;
import antonio.costantini.progettosettimale6.entities.Stato;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;

public record NewViaggioDTO (
        @NotEmpty
        @Size(min = 1, message = "Il campo destionazione deve contenere almeno 1 carattere")
        String destinazione,
        @NotEmpty
        @Pattern(regexp = "^((2000|2400|2800|(19|2[0-9])(0[48]|[2468][048]|[13579][26]))-02-29)$"
                + "|^(((19|2[0-9])[0-9]{2})-02-(0[1-9]|1[0-9]|2[0-8]))$"
                + "|^(((19|2[0-9])[0-9]{2})-(0[13578]|10|12)-(0[1-9]|[12][0-9]|3[01]))$"
                + "|^(((19|2[0-9])[0-9]{2})-(0[469]|11)-(0[1-9]|[12][0-9]|30))$")
        LocalDate data,
        @NotEmpty
        @Pattern(regexp = "/(?:^|(?<= ))(IN_PROGRAMMA| COMPLETATO)")
        Stato stato,
        @NotEmpty
        @Min(1)
        int dipendenteId
){}
