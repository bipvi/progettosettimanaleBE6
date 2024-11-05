package antonio.costantini.progettosettimale6.Services;

import antonio.costantini.progettosettimale6.entities.Dipendente;
import antonio.costantini.progettosettimale6.exceptions.UnauthorizedException;
import antonio.costantini.progettosettimale6.payloads.DipendenteLoginDTO;
import antonio.costantini.progettosettimale6.tools.JWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private DipendenteService dipendenteService;

    @Autowired
    private PasswordEncoder bcrypt;

    @Autowired
    private JWT jwt;

    public String checkCredentialsAndGenerateToken(DipendenteLoginDTO body) {
        Dipendente found = this.dipendenteService.findByEmail(body.email());
        System.out.println(found);
        if(bcrypt.matches(body.password(), found.getPassword())) {
            return jwt.generateToken(found);
        } else throw new UnauthorizedException("Credenziali errate");
    }
}
