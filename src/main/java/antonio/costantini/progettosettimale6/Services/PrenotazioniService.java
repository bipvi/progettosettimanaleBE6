package antonio.costantini.progettosettimale6.Services;

import antonio.costantini.progettosettimale6.Repository.PrenotazioneRepository;
import antonio.costantini.progettosettimale6.entities.Dipendente;
import antonio.costantini.progettosettimale6.entities.Prenotazione;
import antonio.costantini.progettosettimale6.entities.Viaggio;
import antonio.costantini.progettosettimale6.exceptions.BadRequestException;
import antonio.costantini.progettosettimale6.exceptions.NotFoundException;
import antonio.costantini.progettosettimale6.payloads.NewPrenotazioneDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PrenotazioniService {
    @Autowired
    private PrenotazioneRepository prenotazioneRepository;
    @Autowired
    private DipendenteService dipendenteService;
    @Autowired
    private ViaggioService viaggioService;

    public Page<Prenotazione> findAllPrenotazioni(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return prenotazioneRepository.findAll(pageable);
    }

    public Prenotazione savePrenotazione(NewPrenotazioneDTO body) {
        Dipendente dipendente = this.dipendenteService.findById(body.dipendenteId());
        Viaggio viaggio = this.viaggioService.findViaggioByID(body.viaggioId());
        if (prenotazioneRepository.existsByViaggio(viaggio))
            throw new BadRequestException("Viaggio già assegnato");
        if (prenotazioneRepository.checkIfEmployeeIsNotAvailable(dipendente, viaggio.getData()))
            throw new BadRequestException("Dipendente non reperibile per questa data");
        Prenotazione prenotazione = new Prenotazione(dipendente, viaggio);
        if (body.preferenze() != null) prenotazione.setPreferenze(body.preferenze());
        else prenotazione.setPreferenze("N/D");
        return prenotazioneRepository.save(prenotazione);
    }

    public Prenotazione getPrenotazioneById(UUID id) {
        return prenotazioneRepository.findById(id).orElseThrow(() -> new NotFoundException("Prenotazione non trovato"));
    }

    public void findAndDelete (UUID id){
        Prenotazione prenotazione = getPrenotazioneById(id);
        prenotazioneRepository.delete(prenotazione);
    }

    public Prenotazione findAndUpdate(UUID id, NewPrenotazioneDTO body){
        Dipendente dipendente = this.dipendenteService.findById(body.dipendenteId());
        Viaggio viaggio = this.viaggioService.findViaggioByID(body.viaggioId());
        if (prenotazioneRepository.existsByViaggio(viaggio))
            throw new BadRequestException("Viaggio già assegnato");
        if (prenotazioneRepository.checkIfEmployeeIsNotAvailable(dipendente, viaggio.getData()))
            throw new BadRequestException("Dipendente non reperibile per questa data");
        Prenotazione prenotazione = this.getPrenotazioneById(id);
        if (body.preferenze() != null) prenotazione.setPreferenze(body.preferenze());
        else prenotazione.setPreferenze("N/D");
        prenotazione.setData_richiesta(viaggio.getData());
        prenotazione.setDipendente(dipendente);
        return prenotazioneRepository.save(prenotazione);
    }
}
