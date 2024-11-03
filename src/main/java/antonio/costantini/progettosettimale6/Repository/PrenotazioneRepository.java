package antonio.costantini.progettosettimale6.Repository;

import antonio.costantini.progettosettimale6.entities.Dipendente;
import antonio.costantini.progettosettimale6.entities.Prenotazione;
import antonio.costantini.progettosettimale6.entities.Viaggio;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PrenotazioneRepository extends JpaRepository<Prenotazione, UUID> {
    boolean existsByViaggio(Viaggio viaggio);
    Page<Prenotazione> findByDipendente(Dipendente dipendente, Pageable pageable);
    Optional<Prenotazione> findByDipendente(Dipendente dipendente);
    boolean existsByDipendente(Dipendente found);
    @Query("SELECT CASE WHEN COUNT(d) > 0 THEN true ELSE false END FROM Prenotazione d WHERE d.dipendente = :employee AND d" +
            ".viaggio.data = :date")
    boolean checkIfEmployeeIsNotAvailable(Dipendente employee, LocalDate date);
}
