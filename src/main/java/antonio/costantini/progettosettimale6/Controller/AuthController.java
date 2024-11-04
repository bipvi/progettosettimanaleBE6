package antonio.costantini.progettosettimale6.Controller;

import antonio.costantini.progettosettimale6.Services.AuthService;
import antonio.costantini.progettosettimale6.Services.DipendenteService;
import antonio.costantini.progettosettimale6.entities.Dipendente;
import antonio.costantini.progettosettimale6.payloads.DipendenteLoginDTO;
import antonio.costantini.progettosettimale6.payloads.DipendenteLoginResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private DipendenteService dipendenteService;

    @PostMapping("/login")
    public DipendenteLoginResponseDTO login (@RequestBody DipendenteLoginDTO body{
        return new DipendenteLoginResponseDTO(this.authService.checkCredentialsAndGenerateToken(body));
    }

    @PostMapping("/")
}
