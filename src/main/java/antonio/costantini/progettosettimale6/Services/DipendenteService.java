package antonio.costantini.progettosettimale6.Services;

import antonio.costantini.progettosettimale6.Repository.DipendenteRepository;
import antonio.costantini.progettosettimale6.Repository.ViaggioRepository;
import antonio.costantini.progettosettimale6.entities.Dipendente;
import antonio.costantini.progettosettimale6.entities.Viaggio;
import antonio.costantini.progettosettimale6.exceptions.BadRequestException;
import antonio.costantini.progettosettimale6.exceptions.NotFoundException;
import antonio.costantini.progettosettimale6.payloads.NewDipendenteDTO;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class DipendenteService {
    @Autowired
    private DipendenteRepository dipendenteRepository;
    @Autowired
    private ViaggioRepository viaggioRepository;
    @Autowired
    private Cloudinary cloudinary;

    public Dipendente save(NewDipendenteDTO body) {
        Dipendente d = new Dipendente(body.username(), body.name(), body.cognome(), body.email());
        return dipendenteRepository.save(d);
    }

    public Page<Dipendente> findAll(int page, int size, String sortBy) {
        if (size >= 100) size = 100;
        return dipendenteRepository.findAll(PageRequest.of(page, size, Sort.by(sortBy)));
    }

    public Dipendente findById(int id) {
        return dipendenteRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public Dipendente findDipendenteAndUpdate(int id, NewDipendenteDTO body) {
        Dipendente found = findById(id);

        if (!found.getEmail().equals(body.email())) {
            this.dipendenteRepository.findByEmail(body.email()).ifPresent(
                    d -> {
                        throw new BadRequestException("L'email " + body.email() + " è già in uso");
                    }
            );
        }

        found.setCognome(body.cognome());
        found.setNome(body.name());
        found.setEmail(body.email());
        found.setUsername(body.username());
        this.dipendenteRepository.save(found);
        return found;
    }

    public void findAndDelete(int id) {
        Optional<Viaggio> v = this.viaggioRepository.getViaggiosByDipendente_Id(id);
        if(v.isPresent()) {
            this.viaggioRepository.deleteById(v.get().getId());
        }

        dipendenteRepository.deleteById(id);
    }

    public Dipendente uploadImg(int id, MultipartFile file) {
        String url = null;
        try {
            url = (String) cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        } catch (IOException e) {
            throw new BadRequestException("Ci sono stati problemi con l'upload del file!");
        }
        Dipendente d = this.findById(id);
        d.setImg_profilo(url);
        return this.dipendenteRepository.save(d);
    }
}
