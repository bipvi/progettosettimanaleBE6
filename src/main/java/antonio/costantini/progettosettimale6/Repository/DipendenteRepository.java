package antonio.costantini.progettosettimale6.Repository;

import antonio.costantini.progettosettimale6.entities.Dipendente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DipendenteRepository extends JpaRepository<Dipendente, Integer> {
    Optional<Dipendente> findByEmail(String email);
}
