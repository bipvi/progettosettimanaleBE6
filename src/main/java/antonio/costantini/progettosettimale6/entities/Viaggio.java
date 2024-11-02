package antonio.costantini.progettosettimale6.entities;

import jakarta.persistence.*;
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
    @ManyToOne
    @JoinColumn(name = "dipendenteId",nullable = false)
    private Dipendente dipendente;

    public Viaggio(String destinazione, LocalDate data, Stato stato, Dipendente dipendente) {
        this.destinazione = destinazione;
        this.data = data;
        this.stato = stato;
        this.dipendente = dipendente;
    }
}
