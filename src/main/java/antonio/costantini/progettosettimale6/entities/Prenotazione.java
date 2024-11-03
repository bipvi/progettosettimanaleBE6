package antonio.costantini.progettosettimale6.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "prenotazioni")
public class Prenotazione {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID prenotazioni_id;
    @Column(nullable = false)
    private LocalDate data_richiesta;
    @Column(nullable = false)
    private String preferenze;
    @OneToOne
    @JoinColumn(name = "viaggi_id", nullable = false)
    @Setter(AccessLevel.NONE)
    private Viaggio viaggio;
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Dipendente dipendente;

    public Prenotazione(LocalDate data_richiesta, Dipendente dipendente, Viaggio viaggio) {
        this.data_richiesta = data_richiesta;
        this.dipendente = dipendente;
        this.viaggio = viaggio;
    }

    public Prenotazione(Viaggio viaggio) {
        this.viaggio = viaggio;
    }

    public Prenotazione(Viaggio viaggio, Dipendente dipendente) {
        this.viaggio = viaggio;
        this.dipendente = dipendente;
    }
}
