package antonio.costantini.progettosettimale6.Services;

import antonio.costantini.progettosettimale6.entities.Dipendente;
import antonio.costantini.progettosettimale6.exceptions.UnauthorizedException;
import antonio.costantini.progettosettimale6.payloads.DipendenteLoginDTO;
import antonio.costantini.progettosettimale6.tools.JWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private DipendenteService dipendenteService;

    @Autowired
    private JWT jwt;

    public String checkCredentialsAndGenerateToken(DipendenteLoginDTO body) {
        Dipendente found = this.dipendenteService.findByEmail(body.email());
        if(found.getPassword().equals(body.password())) {
            return jwt.generateToken(found);
        } else throw new UnauthorizedException("Credenziali errate");
    }
}
