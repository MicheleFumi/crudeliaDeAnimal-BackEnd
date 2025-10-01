package com.betacom.crudeliaDeAnimal.services.implementations;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.betacom.crudeliaDeAnimal.dto.UtenteDTO;
import com.betacom.crudeliaDeAnimal.models.Utente;
import com.betacom.crudeliaDeAnimal.repositories.IUtenteRepository;
import org.springframework.stereotype.Service;

import com.betacom.crudeliaDeAnimal.dto.VeterinarioDTO;
import com.betacom.crudeliaDeAnimal.exception.CrudeliaException;
import com.betacom.crudeliaDeAnimal.models.Veterinario;
import com.betacom.crudeliaDeAnimal.repositories.IVeterinarioRepository;
import com.betacom.crudeliaDeAnimal.requests.VeterinarioReq;
import com.betacom.crudeliaDeAnimal.services.interfaces.IMessaggioServices;
import com.betacom.crudeliaDeAnimal.services.interfaces.IVeterinarioServices;
import com.betacom.crudeliaDeAnimal.utils.ServizioVeterinarioOspedale;
import com.betacom.crudeliaDeAnimal.utils.StruttureSanitarie;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class VeterinarioImpl implements IVeterinarioServices{

  private final IVeterinarioRepository veterinarioRepo;
  private final IMessaggioServices  msgS;
  private final IUtenteRepository utenteRepository;

  public VeterinarioImpl(
    IVeterinarioRepository veterinarioRepo,
    IMessaggioServices msgS,
    IUtenteRepository utenteRepository
  ) {
    super();
    this.veterinarioRepo = veterinarioRepo;
    this.msgS = msgS;
    this.utenteRepository = utenteRepository;
  }

  @Override
  public void create(VeterinarioReq req) throws CrudeliaException {
    Optional<Utente> utenteR=utenteRepository.findById(req.getUtente().getId());
    if(utenteR.isEmpty())
      throw new CrudeliaException(msgS.getMessaggio("USER_NOT_FOUND"));

    log.debug("Create:" + req);
    Optional<Veterinario> vet =veterinarioRepo.findByNome(req.getNome().trim());
    if(vet.isPresent() && vet.get().getNome().startsWith("Cli"))
      throw new CrudeliaException(msgS.getMessaggio("CLINIC_NOT_FOUND"));
    if(vet.isPresent() && vet.get().getNome().startsWith("Osp"))
      throw new CrudeliaException(msgS.getMessaggio("HOSPITAL_NOT_FOUND"));

    Veterinario veterinario=new Veterinario();
    veterinario.setNome(req.getNome());
    try {
      veterinario.setTipostrutture(StruttureSanitarie.valueOf(req.getTipostrutture()));
    } catch (IllegalArgumentException e) {
      throw new CrudeliaException(msgS.getMessaggio("CLINIC _HOSPITAL_INESISTENTE"));

    }
    veterinario.setUtente(req.getUtente());
    veterinario.setEmail(req.getEmail());
    veterinario.setTelefono(req.getTelefono());
    veterinario.setUtente(utenteR.get());
    veterinario.setIndirizzo(req.getIndirizzo());
    veterinario.setRegione(req.getRegione());
    veterinario.setProvincia(req.getProvincia());
    veterinario.setCap(req.getCap());
    veterinario.setOrariApertura(req.getOrariApertura());
    try {
      veterinario.setServiziVO(ServizioVeterinarioOspedale.valueOf(req.getServiziVO()));
    } catch (IllegalArgumentException e) {
      throw new CrudeliaException(msgS.getMessaggio("CLINIC _HOSPITAL_INESISTENTE"));

    }


    veterinarioRepo.save(veterinario);

  }

  @Override
  public void update(VeterinarioReq req) throws CrudeliaException {

    log.debug("update :" + req);

    Optional<Veterinario> vet =veterinarioRepo.findById(req.getId());

    if(vet.isEmpty())
      throw new CrudeliaException(msgS.getMessaggio("CLINIC _HOSPITAL_INESISTENTE"));

    if(req.getNome() !=null)
      vet.get().setNome(req.getNome());

    if(req.getEmail()!= null)
      vet.get().setEmail(req.getEmail());

    if(req.getIndirizzo()!= null)
      vet.get().setIndirizzo(req.getIndirizzo());

    if(req.getRegione()!= null)
      vet.get().setRegione(req.getRegione());

    if(req.getProvincia()!= null)
      vet.get().setProvincia(req.getProvincia());

    if(req.getCap()!= null)
      vet.get().setCap(req.getCap());

    if(req.getTelefono()!= null)
      vet.get().setTelefono(req.getTelefono());


    if(req.getTipostrutture()!= null)
      vet.get().setTipostrutture(StruttureSanitarie.valueOf(req.getTipostrutture()));

    if(req.getServiziVO()!= null)
      vet.get().setServiziVO(ServizioVeterinarioOspedale.valueOf(req.getServiziVO()));


    if(req.getOrariApertura()!= null)
      vet.get().setOrariApertura(req.getOrariApertura());

    // Handle Utente update, which is likely just a check/re-association
    if (req.getUtente() != null && req.getUtente().getId() != null) {
      Optional<Utente> utenteR = utenteRepository.findById(req.getUtente().getId());
      if (utenteR.isPresent()) {
        vet.get().setUtente(utenteR.get());
      } else {
        throw new CrudeliaException(msgS.getMessaggio("USER_NOT_FOUND"));
      }
    }


    veterinarioRepo.save(vet.get());
  }

  @Override
  public VeterinarioDTO delete(VeterinarioReq req) throws CrudeliaException {

    log.debug("remove:" + req);

    Optional<Veterinario> vet =veterinarioRepo.findById(req.getId());

    if(vet.isEmpty())
      throw new CrudeliaException(msgS.getMessaggio("CLINIC _HOSPITAL_INESISTENTE"));

    veterinarioRepo.delete(vet.get());

    return VeterinarioDTO.builder()
      .id(vet.get().getId())
      .nome(vet.get().getNome())
      .telefono(vet.get().getTelefono())
      .email(vet.get().getEmail())
      .indirizzo(vet.get().getIndirizzo())
      .regione(vet.get().getRegione())
      .provincia(vet.get().getProvincia())
      .cap(vet.get().getCap())
      .tipostruttura(vet.get().getTipostrutture())
      .serviziVO(vet.get().getServiziVO())
      .orariApertura(vet.get().getOrariApertura())
      .build();
  }

  @Override
  public List<VeterinarioDTO> listAll() {

    log.debug("listAll");

    List<Veterinario> lV =veterinarioRepo.findAll();

    return lV.stream()
      .map(vet-> VeterinarioDTO.builder()
        .id(vet.getId())
        .nome(vet.getNome())
        .telefono(vet.getTelefono())
        .email(vet.getEmail())
        .indirizzo(vet.getIndirizzo())
        .cap(vet.getCap())
        .provincia(vet.getProvincia())
        .regione(vet.getRegione())
        .tipostruttura(vet.getTipostrutture())
        .serviziVO(vet.getServiziVO())
        .orariApertura(vet.getOrariApertura())
        .build()
      ).collect(Collectors.toList());


  }

  @Override
  public VeterinarioDTO findById(Integer id) throws CrudeliaException {

    log.debug("findById:" + id);

    Optional<Veterinario> vet =veterinarioRepo.findById(id);
    if(vet.isEmpty())
      throw new CrudeliaException(msgS.getMessaggio("CLINIC _HOSPITAL_INESISTENTE"));

    return VeterinarioDTO.builder()
      .id(vet.get().getId())
      .nome(vet.get().getNome())
      .telefono(vet.get().getTelefono())
      .email(vet.get().getEmail())
      .indirizzo(vet.get().getIndirizzo())
      .provincia(vet.get().getProvincia())
      .regione(vet.get().getRegione())
      .cap(vet.get().getCap())
      .tipostruttura(vet.get().getTipostrutture())
      .serviziVO(vet.get().getServiziVO())
      .orariApertura(vet.get().getOrariApertura())
      .build();
  }


  @Override
  public List<VeterinarioDTO> findByCap(String cap) throws CrudeliaException {

    log.debug("findByCap:" + cap);

    List<Veterinario> lvet =veterinarioRepo.findByCap(cap);

    if(lvet.isEmpty())
      throw new CrudeliaException(msgS.getMessaggio("CLINIC _HOSPITAL_INESISTENTE"));

    return lvet.stream()
      .map(vet-> VeterinarioDTO.builder()
        .id(vet.getId())
        .nome(vet.getNome())
        .telefono(vet.getTelefono())
        .email(vet.getEmail())
        .indirizzo(vet.getIndirizzo())
        .cap(vet.getCap())
        .provincia(vet.getProvincia())
        .regione(vet.getRegione())
        .tipostruttura(vet.getTipostrutture())
        .serviziVO(vet.getServiziVO())
        .orariApertura(vet.getOrariApertura())
        .build()
      ).collect(Collectors.toList());


  }

  @Override
  public List<VeterinarioDTO> findByProvincia(String provincia) throws CrudeliaException {

    log.debug("findByprovicina:" + provincia);

    List<Veterinario> lvet =veterinarioRepo.findByProvincia(provincia);

    if(lvet.isEmpty())
      throw new CrudeliaException(msgS.getMessaggio("CLINIC _HOSPITAL_INESISTENTE"));

    return lvet.stream()
      .map(vet-> VeterinarioDTO.builder()
        .id(vet.getId())
        .nome(vet.getNome())
        .telefono(vet.getTelefono())
        .email(vet.getEmail())
        .indirizzo(vet.getIndirizzo())
        .cap(vet.getCap())
        .provincia(vet.getProvincia())
        .regione(vet.getRegione())
        .tipostruttura(vet.getTipostrutture())
        .serviziVO(vet.getServiziVO())
        .orariApertura(vet.getOrariApertura())
        .build()
      ).collect(Collectors.toList());
  }

  @Override
  public List<VeterinarioDTO>findByRegione(String regione) throws CrudeliaException {

    log.debug("findByregione:" + regione);

    List<Veterinario> lvet =veterinarioRepo.findByRegione(regione);

    if(lvet.isEmpty())
      throw new CrudeliaException(msgS.getMessaggio("CLINIC _HOSPITAL_INESISTENTE"));

    return lvet.stream()
      .map(vet-> VeterinarioDTO.builder()
        .id(vet.getId())
        .nome(vet.getNome())
        .telefono(vet.getTelefono())
        .email(vet.getEmail())
        .indirizzo(vet.getIndirizzo())
        .cap(vet.getCap())
        .provincia(vet.getProvincia())
        .regione(vet.getRegione())
        .tipostruttura(vet.getTipostrutture())
        .serviziVO(vet.getServiziVO())
        .orariApertura(vet.getOrariApertura())
        .build()
      ).collect(Collectors.toList());
  }

  @Override
  public List<VeterinarioDTO> findByIdUtente(Integer id) throws CrudeliaException {
    log.debug("findByregione:" + "utente id:" + id);

    List<Veterinario> lvet =veterinarioRepo.findByUtente_id(id);

    if(lvet.isEmpty())
      throw new CrudeliaException(msgS.getMessaggio("CLINIC _HOSPITAL_INESISTENTE"));

    return lvet.stream()
      .map(vet-> VeterinarioDTO.builder()
        .id(vet.getId())
        .nome(vet.getNome())
        .telefono(vet.getTelefono())
        .email(vet.getEmail())
        .indirizzo(vet.getIndirizzo())
        .cap(vet.getCap())
        .provincia(vet.getProvincia())
        .regione(vet.getRegione())
        .tipostruttura(vet.getTipostrutture())
        .serviziVO(vet.getServiziVO())
        .orariApertura(vet.getOrariApertura())
        .build()
      ).collect(Collectors.toList());
  }

}
