package antonio.costantini.progettosettimale6.Controller;

import antonio.costantini.progettosettimale6.Repository.DipendenteRepository;
import antonio.costantini.progettosettimale6.Services.DipendenteService;
import antonio.costantini.progettosettimale6.entities.Dipendente;
import antonio.costantini.progettosettimale6.exceptions.BadRequestException;
import antonio.costantini.progettosettimale6.payloads.NewDipendenteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/dipendenti")
public class DipendenteController {
    @Autowired
    DipendenteService dipendenteService;

    @GetMapping
    public Page<Dipendente> findAll(@RequestParam (defaultValue = "0") int page,
                                    @RequestParam (defaultValue = "10") int size,
                                    @RequestParam (defaultValue = "email") String sortBy) {
        return this.dipendenteService.findAll(page,size,sortBy);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Dipendente save(@RequestBody NewDipendenteDTO body, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String error = bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(" - "));
            throw new BadRequestException(error);
        }
        return this.dipendenteService.save(body);
    }

    @GetMapping("/{dipendenteId}")
    public Dipendente findById(@PathVariable("dipendenteId") int dipendenteId) {
        return this.dipendenteService.findById(dipendenteId);
    }

    @PutMapping("/{dipendenteId}")
    public Dipendente findAndUpdate(@PathVariable("dipendenteId") int dipendenteId, @RequestBody NewDipendenteDTO body, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String messasge = bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(" - "));
            throw new BadRequestException(messasge);
        }
        return this.dipendenteService.findDipendenteAndUpdate(dipendenteId, body);
    }

    @DeleteMapping("/{dipenteId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("dipenteId") int dipendenteId) {
        this.dipendenteService.findAndDelete(dipendenteId);
    }

    @PatchMapping("/{dipenteId}/img")
    public Dipendente uploadImage(@PathVariable("dipenteId") int dipendenteId, @RequestParam("img") MultipartFile file) {
        return this.dipendenteService.uploadImg(dipendenteId, file);
    }
}
