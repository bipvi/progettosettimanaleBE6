package antonio.costantini.progettosettimale6.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
@Data
@Entity
public class Viaggio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String destinazione;
    private LocalDate data;
    @Enumerated(EnumType.STRING)
    private Stato stato;
}
