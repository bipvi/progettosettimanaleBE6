package antonio.costantini.progettosettimale6.Repository;

import antonio.costantini.progettosettimale6.entities.Viaggio;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ViaggioRepository extends JpaRepository<Viaggio, Integer> {
}
