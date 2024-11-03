package antonio.costantini.progettosettimale6.payloads;

import jakarta.validation.constraints.Pattern;

public record NewStatoDTO(
        @Pattern(regexp = "IN_PROGRAMMA|COMPLETATO", message = "Lo stato deve essere 'COMPLETATO' o 'IN_PROGRAMMA'") String stato) {}
