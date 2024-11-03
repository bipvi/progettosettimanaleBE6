package antonio.costantini.progettosettimale6.Controller;

//             1. GET http://localhost:3001/viaggi
//             2. POST http://localhost:3001/viaggi (+ req.body) --> 201
//             3. GET http://localhost:3001/viaggi/{viaggiId}
//             4. PUT http://localhost:3001/viaggi/{viaggiId} (+ req.body)
//             5. DELETE http://localhost:3001/viaggi/{viaggiId} --> 204

import antonio.costantini.progettosettimale6.Repository.ViaggioRepository;
import antonio.costantini.progettosettimale6.Services.ViaggioService;
import antonio.costantini.progettosettimale6.entities.Stato;
import antonio.costantini.progettosettimale6.entities.Viaggio;
import antonio.costantini.progettosettimale6.exceptions.BadRequestException;
import antonio.costantini.progettosettimale6.payloads.NewViaggioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/viaggi")
public class ViaggioController {
    @Autowired
    ViaggioService viaggioService;
//   1. GET http://localhost:3001/viaggi
    @GetMapping
    public Page<Viaggio> findAll(@RequestParam (defaultValue = "0")int page,
                                 @RequestParam (defaultValue = "15")int size,
                                 @RequestParam (defaultValue = "destinazione") String sortBy) {
        return this.viaggioService.getViaggi(page, size, sortBy);
    }
//    2. POST http://localhost:3001/viaggi (+ req.body) --> 201
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Viaggio save(@RequestBody NewViaggioDTO body, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            String message = bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(" - "));
            throw new BadRequestException(message);
        }
        return this.viaggioService.save(body);
    }
//      3. GET http://localhost:3001/viaggi/{viaggiId}
    @GetMapping("/{viaggiId}")
    public Viaggio findById(@PathVariable int viaggiId) {
        return this.viaggioService.findViaggioByID(viaggiId);
    }
//      4. PUT http://localhost:3001/viaggi/{viaggiId} (+ req.body)
    @PutMapping("/{viaggiId}")
    public Viaggio findAndUpdate(@PathVariable int viaggiId, @RequestBody @Validated NewViaggioDTO body, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String mess = bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(" - "));
            throw new BadRequestException(mess);
        }
        return this.viaggioService.getViaggioAndUpdate(viaggiId, body);
    }
//   5. DELETE http://localhost:3001/viaggi/{viaggiId} --> 204
    @DeleteMapping("/{viaggiId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int viaggiId) {
        this.viaggioService.findAndDeleteViaggio(viaggiId);
    }

    @PatchMapping("/{viaggiId}/stato")
    public Stato updateState(@PathVariable("viaggiId") int id,
                             @RequestParam("stato") String stato) {
        Stato s = Stato.valueOf(stato);
        return this.viaggioService.UpdateState(id, s);
    }
}
