package com.betacom.crudeliaDeAnimal.services.implementations;

import com.betacom.crudeliaDeAnimal.dto.SignInDTO;
import com.betacom.crudeliaDeAnimal.dto.UtenteDTO;
import com.betacom.crudeliaDeAnimal.exception.CrudeliaException;
import com.betacom.crudeliaDeAnimal.models.Utente;
import com.betacom.crudeliaDeAnimal.repositories.IUtenteRepository;
import com.betacom.crudeliaDeAnimal.requests.SignInReq;
import com.betacom.crudeliaDeAnimal.requests.UtenteReq;
import com.betacom.crudeliaDeAnimal.response.ResponseBase;
import com.betacom.crudeliaDeAnimal.services.interfaces.IMessaggioServices;
import com.betacom.crudeliaDeAnimal.services.interfaces.IUtenteServices;
import com.betacom.crudeliaDeAnimal.utils.Roles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UtenteImpl implements IUtenteServices {
    private static final Logger log = LogManager.getLogger(UtenteImpl.class);
    private IUtenteRepository iUtenteRepository;
	private IMessaggioServices  msgS;


    public UtenteImpl(IUtenteRepository iUtenteRepository,IMessaggioServices  msgS) {
        this.iUtenteRepository = iUtenteRepository;
        this.msgS =msgS;
    }

    @Override
    public void create(UtenteReq req) throws CrudeliaException {
        Optional<Utente> byCF = iUtenteRepository.findByCodiceFiscale(req.getCodiceFiscale());
        if (byCF.isPresent()) {
            throw new CrudeliaException(msgS.getMessaggio("USER_DUPLICATE_CodiceFiscale"));
        }

        Optional<Utente> byEmail = iUtenteRepository.findByEmail(req.getEmail());
        if (byEmail.isPresent()) {
            throw new CrudeliaException(msgS.getMessaggio("USER_DUPLICATE_EMAIL"));
        }
        Utente ut = new Utente();
        ut.setNome(req.getNome());
        ut.setCognome(req.getCognome());
        ut.setEmail(req.getEmail());
        ut.setRole(Roles.valueOf(req.getRole().toUpperCase()));
        ut.setIndirizzo(req.getIndirizzo());
        ut.setDataRegistrazione(LocalDate.now());
        ut.setCodiceFiscale(req.getCodiceFiscale());
        ut.setPassword(req.getPassword());
        ut.setTelefono(req.getTelefono());
        iUtenteRepository.save(ut);
    }

    @Override
    public void update(UtenteReq req) throws CrudeliaException {
        Optional<Utente> u = iUtenteRepository.findById(req.getId());
        if (u.isEmpty())
            throw new CrudeliaException("Utente non esistente");

        u.get().setNome(req.getNome());
        u.get().setCognome(req.getCognome());
        u.get().setTelefono(req.getTelefono());
        u.get().setIndirizzo(req.getIndirizzo());
        u.get().setPassword(req.getPassword());
        u.get().setCodiceFiscale(req.getCodiceFiscale());
        u.get().setEmail(req.getEmail());
        u.get().setRole(Roles.valueOf(req.getRole()));
        iUtenteRepository.save(u.get());
    }

    @Override
    public void delete(UtenteReq req) throws CrudeliaException {
        Optional<Utente> u = iUtenteRepository.findById(req.getId());
        if (u.isEmpty())
            throw new CrudeliaException("Utente non esistente");

        iUtenteRepository.delete(u.get());
    }

    @Override
    public List<UtenteDTO> listAll() {
        List<Utente> lU = iUtenteRepository.findAll();
       return lU.stream().map(r -> UtenteDTO.builder()
               .nome(r.getNome())
               .id(r.getId())
               .codiceFiscale(r.getCodiceFiscale())
               .cognome(r.getCognome())
               .dataRegistrazione(r.getDataRegistrazione())
               .indirizzo(r.getIndirizzo())
               .role(r.getRole())
               .password(r.getPassword())
               .telefono(r.getTelefono())
               .email(r.getEmail())
               .build()).toList();
    }

    @Override
    public UtenteDTO findById(Integer id) throws CrudeliaException {
        Optional<Utente> u = iUtenteRepository.findById(id);

        if (u.isEmpty()){
            throw new CrudeliaException("Utente non esistente");
        }
        return UtenteDTO.builder()
                .email(u.get().getEmail())
                .id(u.get().getId())
                .nome(u.get().getNome())
                .cognome(u.get().getCognome())
                .codiceFiscale(u.get().getCodiceFiscale())
                .dataRegistrazione(u.get().getDataRegistrazione())
                .telefono(u.get().getTelefono())
                .role(u.get().getRole())
                .indirizzo(u.get().getIndirizzo())
                .password(u.get().getPassword())
                .build();
    }

    @Override
    public SignInDTO signIn(SignInReq req) throws CrudeliaException {
        log.debug("Sign in:" + req);
        SignInDTO r = new SignInDTO();
        Optional<Utente> u = iUtenteRepository.findByEmailAndPassword(req.getEmail(), req.getPassword());
        if (!u.isEmpty()){
            r.setRole(String.valueOf(u.get().getRole()));
            r.setLogged(true);
            r.setId(u.get().getId());
        }else {
            r.setLogged(false);
        }
        return r;
    }
}
