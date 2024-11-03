package antonio.costantini.progettosettimale6.Services;

import antonio.costantini.progettosettimale6.Repository.DipendenteRepository;
import antonio.costantini.progettosettimale6.Repository.ViaggioRepository;
import antonio.costantini.progettosettimale6.entities.Dipendente;
import antonio.costantini.progettosettimale6.entities.Stato;
import antonio.costantini.progettosettimale6.entities.Viaggio;
import antonio.costantini.progettosettimale6.exceptions.BadRequestException;
import antonio.costantini.progettosettimale6.exceptions.NotFoundException;
import antonio.costantini.progettosettimale6.payloads.NewViaggioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ViaggioService {
    @Autowired
    private ViaggioRepository viaggioRepository;
    @Autowired
    private DipendenteService dipendenteService;

    public Viaggio save(NewViaggioDTO body) {
        Dipendente dipendente = this.dipendenteService.findById(body.dipendenteId());
            this.viaggioRepository.getViaggioByDipendente_IdAndData(dipendente.getId(), body.data()).ifPresent(
                v -> {
                    throw new BadRequestException("Il dipendente " + dipendente.getId() + " già in viaggio");
                });
            Viaggio viaggio = new Viaggio(body.destinazione(), body.data(), body.stato(), dipendente);
            return this.viaggioRepository.save(viaggio);
    }


    public Page<Viaggio> getViaggi(int page, int size, String sortBy){
        if (size >= 50 ) size = 50;
        Pageable pageable = PageRequest.of(page,size, Sort.by(sortBy));
        return this.viaggioRepository.findAll(pageable);
    }

    public Viaggio findViaggioByID(int id){
        return this.viaggioRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public Viaggio getViaggioAndUpdate(int id, NewViaggioDTO body){
        Dipendente d = this.dipendenteService.findById(body.dipendenteId());
        Viaggio found = this.findViaggioByID(id);

        if(!found.getData().isEqual(body.data()) && !found.getDipendente().equals(d)){
            this.viaggioRepository.getViaggioByDipendente_IdAndData(d.getId(), body.data()).ifPresent(
                    v -> {
                        throw new BadRequestException("Il dipendente " + d.getEmail() + " già in viaggio");
                    }
            );
        }

        found.setData(body.data());
        found.setDipendente(d);
        found.setStato(body.stato());
        found.setDestinazione(body.destinazione());
        return this.viaggioRepository.save(found);
    }

    public Stato UpdateState(int id, Stato stato){
        Viaggio found = this.viaggioRepository.findById(id).orElseThrow();
        if (found.getStato().equals(stato)) {
            throw new BadRequestException("Lo stato del viaggio: " + found.getId() + " è già in " + stato);
        }
        found.setStato(stato);
        this.viaggioRepository.save(found);
        return stato;
    }

    public void findAndDeleteViaggio(int id){
        Viaggio found = this.findViaggioByID(id);
        this.viaggioRepository.delete(found);
    }

}
