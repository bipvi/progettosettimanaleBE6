package antonio.costantini.progettosettimale6.Controller;

import antonio.costantini.progettosettimale6.Services.AuthService;
import antonio.costantini.progettosettimale6.Services.DipendenteService;
import antonio.costantini.progettosettimale6.entities.Dipendente;
import antonio.costantini.progettosettimale6.exceptions.BadRequestException;
import antonio.costantini.progettosettimale6.payloads.DipendenteLoginDTO;
import antonio.costantini.progettosettimale6.payloads.DipendenteLoginResponseDTO;
import antonio.costantini.progettosettimale6.payloads.NewDipendenteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private DipendenteService dipendenteService;

    @PostMapping("/login")
    public DipendenteLoginResponseDTO login (@RequestBody DipendenteLoginDTO body){
        return new DipendenteLoginResponseDTO(this.authService.checkCredentialsAndGenerateToken(body));
    }

    @GetMapping
    public Page<Dipendente> findAll(@RequestParam (defaultValue = "0") int page,
                                    @RequestParam (defaultValue = "10") int size,
                                    @RequestParam (defaultValue = "email") String sortBy) {
        return this.dipendenteService.findAll(page,size,sortBy);
    }


    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Dipendente save (@RequestBody @Validated NewDipendenteDTO newDipendenteDTO, BindingResult result){
        if(result.hasErrors()){
            String error = result.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).collect(Collectors.joining(", "));
            throw new BadRequestException("Ci sono stati errori nel payload! " + error);
        }
        return this.dipendenteService.save(newDipendenteDTO);
    }
}
