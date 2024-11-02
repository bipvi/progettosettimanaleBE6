package antonio.costantini.progettosettimale6.Services;

import antonio.costantini.progettosettimale6.Repository.DipendenteRepository;
import antonio.costantini.progettosettimale6.Repository.ViaggioRepository;
import antonio.costantini.progettosettimale6.entities.Viaggio;
import antonio.costantini.progettosettimale6.payloads.NewViaggioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ViaggioService {
    @Autowired
    private ViaggioRepository viaggioRepository;
    @Autowired
    private DipendenteRepository dipendenteRepository;

    public Viaggio save(Viaggio viaggio) {
        return viaggioRepository.save(viaggio);
    }

    public Page<Viaggio> getViaggi(int page, int size, String sortBy){
        if (size == 50 ) size = 50;
        Pageable pageable = PageRequest.of(page,size, Sort.by(sortBy));
        return this.viaggioRepository.findAll(pageable);
    }

    public Optional<Viaggio> getViaggioById(int id){
        return this.viaggioRepository.findById(id);
    }

    public Optional<Viaggio> getViaggioAndUpdate(int id, NewViaggioDTO viaggio){
        Optional<Viaggio> viaggio = this.viaggioRepository.findById(id);
        this.viaggioRepository.save(viaggio.get());
    }

}
