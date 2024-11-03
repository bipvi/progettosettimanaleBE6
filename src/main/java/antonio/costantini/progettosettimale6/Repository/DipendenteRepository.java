package antonio.costantini.progettosettimale6.Repository;

import antonio.costantini.progettosettimale6.entities.Dipendente;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DipendenteRepository extends JpaRepository<Dipendente, Integer> {
    Optional<Dipendente> findByEmail(String email);
    Optional<Dipendente> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(@NotEmpty(message = "Il campo email non può essere vuioto!") @Email(message = "L'email deve essere del tipo user@example.com") String email);
}
