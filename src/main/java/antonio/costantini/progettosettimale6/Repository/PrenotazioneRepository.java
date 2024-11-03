package antonio.costantini.progettosettimale6.Repository;

import antonio.costantini.progettosettimale6.entities.Dipendente;
import antonio.costantini.progettosettimale6.entities.Prenotazione;
import antonio.costantini.progettosettimale6.entities.Viaggio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Integer> {
    boolean existsByViaggio(Viaggio viaggio);
    Page<Prenotazione> findByDipendente(Dipendente dipendente, Pageable pageable);
    @Query("SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END FROM Prenotazione b WHERE b.dipendente = :dipendente AND b" +
            ".viaggio.data = :data")
    boolean checkIfEmployeeIsNotAvailable(Dipendente dipendente, LocalDate data);
    Optional<Prenotazione> findByDipendente(Dipendente dipendente);
    boolean existsByDipendente(Dipendente found);
}
