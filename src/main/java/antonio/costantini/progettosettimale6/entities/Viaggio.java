package antonio.costantini.progettosettimale6.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;


@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "viaggi")
public class Viaggio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private int id;
    @Column(nullable = false)
    private String destinazione;
    @Column(nullable = false)
    private LocalDate data;
    @Enumerated(EnumType.STRING)
    private Stato stato;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "viaggio")
    @JsonIgnore
    private Prenotazione prenotazione;

    public Viaggio(String destinazione) {
        this.destinazione = destinazione;
        this.stato = Stato.IN_PROGRAMMA;
    }

    public Viaggio(String destinazione, LocalDate data, Stato stato, Prenotazione prenotazione) {
        this.destinazione = destinazione;
        this.data = data;
        this.stato = stato;
        this.prenotazione = prenotazione;
    }

    public Viaggio(String destinazione, LocalDate data, Stato stato) {
        this.destinazione = destinazione;
        this.data = data;
        this.stato = stato;
    }

    public Viaggio(String destinazione, LocalDate data) {
        this.destinazione = destinazione;
        this.data = data;
    }
}
