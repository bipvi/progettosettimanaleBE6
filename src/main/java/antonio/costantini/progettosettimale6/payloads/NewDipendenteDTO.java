package antonio.costantini.progettosettimale6.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record NewDipendenteDTO (
    @NotEmpty(message = "Il nome è obbligatorio!")
    @Size(min = 2, max = 30, message = "il nome deve essere compreso tra 2 e 30 caratteri!")
    String nome,
    @NotEmpty(message = "Lo username è obbligatorio!")
    @Size(min = 3, max = 30, message = "L' username deve essere compreso tra 3 e 30 caratteri!")
    String username,
    @NotEmpty(message = "Il cognome è obbligatorio!")
    @Size(max = 30, message = "Il cognome deve essere minore di 30 caratteri")
    String cognome,
    @NotEmpty(message = "Il campo email non può essere vuioto!")
    @Email(message = "L'email deve essere del tipo user@example.com")
    String email,
    @NotEmpty@Size(min = 6, max=20, message = "La password deve essere maggiore di 6 caratteri e minore di 20 caratteri")
    String password
    ){}

