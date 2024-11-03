package antonio.costantini.progettosettimale6.Services;

import antonio.costantini.progettosettimale6.Repository.DipendenteRepository;
import antonio.costantini.progettosettimale6.Repository.ViaggioRepository;
import antonio.costantini.progettosettimale6.entities.Dipendente;
import antonio.costantini.progettosettimale6.entities.Prenotazione;
import antonio.costantini.progettosettimale6.entities.Stato;
import antonio.costantini.progettosettimale6.entities.Viaggio;
import antonio.costantini.progettosettimale6.exceptions.BadRequestException;
import antonio.costantini.progettosettimale6.exceptions.NotFoundException;
import antonio.costantini.progettosettimale6.payloads.NewStatoDTO;
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

    public Viaggio save(NewViaggioDTO body) {
            Viaggio viaggio = new Viaggio(body.destinazione(), body.data());
            viaggio.setStato(Stato.valueOf(body.stato().stato()));
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
        Viaggio found = findViaggioByID(id);
        found.setData(body.data());
        found.setDestinazione(body.destinazione());
        return this.viaggioRepository.save(found);
    }

    public Stato UpdateState(int id, String stato){
        Viaggio found = this.viaggioRepository.findById(id).orElseThrow();
        if (found.getStato().equals(Stato.valueOf(stato))) {
            throw new BadRequestException("Lo stato del viaggio: " + found.getId() + " è già in " + stato);
        }
        found.setStato( Stato.valueOf(stato));
        this.viaggioRepository.save(found);
        return Stato.valueOf(stato);
    }

    public void findAndDeleteViaggio(int id){
        Viaggio found = this.findViaggioByID(id);
        this.viaggioRepository.delete(found);
    }

}
