package antonio.costantini.progettosettimale6.Repository;

import antonio.costantini.progettosettimale6.entities.Dipendente;
import antonio.costantini.progettosettimale6.entities.Viaggio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ViaggioRepository extends JpaRepository<Viaggio, Integer> {

    Optional<Viaggio> getViaggioByDipendente_IdAndData(int dipendente_id, LocalDate data);

    Optional<Viaggio> getViaggiosByDipendente_Id(int id);
}
