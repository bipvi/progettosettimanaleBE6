package antonio.costantini.progettosettimale6.Controller;

import antonio.costantini.progettosettimale6.Services.PrenotazioniService;
import antonio.costantini.progettosettimale6.entities.Prenotazione;
import antonio.costantini.progettosettimale6.exceptions.BadRequestException;
import antonio.costantini.progettosettimale6.payloads.NewPrenotazioneDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/prenotazioni")
public class PrenotazioniController {
    @Autowired
    private PrenotazioniService prenotazioniService;

    @GetMapping
    public Page<Prenotazione> getPrenotazioni(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "10") int size,
                                              @RequestParam(defaultValue = "viaggio.data") String sortBy) {
        return prenotazioniService.findAllPrenotazioni(page, size, sortBy);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Prenotazione savePrenotazione(@RequestBody NewPrenotazioneDTO body, BindingResult bindingResult) {
        System.out.println(body.dipendentiId() + body.preferenze() + body.viaggioId() + body.data());
        if (bindingResult.hasErrors()) {
            String mess = bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(" - "));
            throw new BadRequestException(mess);
        }
        return prenotazioniService.savePrenotazione(body);
    }

    @GetMapping("/{prenotazioniId}")
    public Prenotazione getPrenotazione(@PathVariable int prenotazioniId) { return prenotazioniService.getPrenotazioneById(prenotazioniId);}

    @DeleteMapping("/{prenotazioniId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePrenotazione(@PathVariable int prenotazioniId) {prenotazioniService.findAndDelete(prenotazioniId);}
}
