package com.betacom.crudeliaDeAnimal.services.implementations;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.betacom.crudeliaDeAnimal.dto.AnimaleDTO;
import com.betacom.crudeliaDeAnimal.dto.UtenteDTO;
import com.betacom.crudeliaDeAnimal.exception.CrudeliaException;
import com.betacom.crudeliaDeAnimal.models.Animale;
import com.betacom.crudeliaDeAnimal.models.Utente;
import com.betacom.crudeliaDeAnimal.repositories.IAnimaleRepository;
import com.betacom.crudeliaDeAnimal.repositories.IUtenteRepository;
import com.betacom.crudeliaDeAnimal.requests.AnimaleReq;
import com.betacom.crudeliaDeAnimal.services.interfaces.IAnimaleServices;

import com.betacom.crudeliaDeAnimal.services.interfaces.IMessaggioServices;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AnimaleImpl implements  IAnimaleServices{
	private  IAnimaleRepository animaleRepo;
    private  IUtenteRepository utenteRepo;
	private IMessaggioServices  msgS;

	public AnimaleImpl(IAnimaleRepository animaleRepo, IUtenteRepository utenteRepo ,IMessaggioServices  msgS) {
		super();
		this.animaleRepo = animaleRepo;
		this.utenteRepo = utenteRepo;
		this.msgS=msgS;

	}

	@Override
	public AnimaleDTO findById(Integer id) throws CrudeliaException {

        log.debug("findById: {}", id);

		
		Optional<Animale> anim = animaleRepo.findById(id);
		
		  if (anim.isEmpty())
	throw new CrudeliaException(msgS.getMessaggio("ANIMAL_NOT_FOUND"));


		  return AnimaleDTO.builder()
					.id(anim.get().getId())
					.nomeAnimale(anim.get().getNomeAnimale())
					.tipo(anim.get().getTipo())
					.razza(anim.get().getRazza())
					.noteMediche(anim.get().getNoteMediche())
					.utente(UtenteDTO.builder()
							.id(anim.get().getUtente().getId())

							.cognome(anim.get().getUtente().getCognome())
							.nome(anim.get().getUtente().getNome())
							.codiceFiscale(anim.get().getUtente().getCodiceFiscale())
							.telefono(anim.get().getUtente().getTelefono())
							.email(anim.get().getUtente().getEmail())
							.indirizzo(anim.get().getUtente().getIndirizzo())
							.password(anim.get().getUtente().getPassword())
							.dataRegistrazione(anim.get().getUtente().getDataRegistrazione())
							.role(anim.get().getUtente().getRole())
							.build()	
							)
					.build()
					;
	}

	@Override
	public void create(AnimaleReq req) throws CrudeliaException {

		
		 log.debug("Create Animale: " + req);
         Optional<Utente> utenteR=utenteRepo.findById(req.getUtente().getId());
         if(utenteR.isEmpty())
	   			throw new CrudeliaException(msgS.getMessaggio("USER_NOT_FOUND"));

         
         Animale animale = new Animale();
         animale.setUtente(utenteR.get());
         animale.setNomeAnimale(req.getNomeAnimale());
         animale.setTipo(req.getTipo());
         animale.setRazza(req.getRazza());
         animale.setNoteMediche(req.getNoteMediche());
         
         animaleRepo.save(animale);
	
	}

	@Override
	public void update(AnimaleReq req) throws CrudeliaException {

        log.debug("Update Animale: " + req);
		
        Optional<Animale> anim=animaleRepo.findById(req.getId());
        if(anim.isEmpty())
   			throw new CrudeliaException(msgS.getMessaggio("ANIMAL_NOT_FOUND"));

        if (req.getNomeAnimale() != null && !req.getNomeAnimale().isBlank()) {
        	anim.get().setNomeAnimale(req.getNomeAnimale());
        }
        if (req.getTipo() != null) {
        	anim.get().setTipo(req.getTipo());
        }
        if (req.getRazza() != null) {
        	anim.get().setRazza(req.getRazza());
        }
        if (req.getNoteMediche() != null) {
        	anim.get().setNoteMediche(req.getNoteMediche());
        }
        
        if (req.getUtente().getId() != null) {
            Utente utente = utenteRepo.findById(req.getUtente().getId())
                    .orElseThrow(() -> new CrudeliaException(msgS.getMessaggio("USER_NOT_FOUND")));

            anim.get().setUtente(utente);
        }
        
        animaleRepo.save(anim.get());
        
	}

	@Override
	public AnimaleDTO delete(AnimaleReq req) throws CrudeliaException {

		log.debug("remove:" + req);

		
        Optional<Animale> anim=animaleRepo.findById(req.getId());
        
        if(anim.isEmpty())

   			throw new CrudeliaException(msgS.getMessaggio("ANIMAL_NOT_FOUND"));

        
        animaleRepo.delete(anim.get());
        

		return AnimaleDTO.builder()
				.id(anim.get().getId())
				.nomeAnimale(anim.get().getNomeAnimale())
				.tipo(anim.get().getTipo())
				.razza(anim.get().getRazza())
				.noteMediche(anim.get().getNoteMediche())
				.utente(UtenteDTO.builder()
						.id(anim.get().getUtente().getId())

						.cognome(anim.get().getUtente().getCognome())
						.nome(anim.get().getUtente().getNome())
						.codiceFiscale(anim.get().getUtente().getCodiceFiscale())
						.telefono(anim.get().getUtente().getTelefono())
						.email(anim.get().getUtente().getEmail())
						.indirizzo(anim.get().getUtente().getIndirizzo())
						.password(anim.get().getUtente().getPassword())
						.dataRegistrazione(anim.get().getUtente().getDataRegistrazione())

						.role(anim.get().getUtente().getRole())
						.build()	
						)
				.build()
				;
	}

	@Override
	public List<AnimaleDTO> listAll() throws CrudeliaException {
		
        log.debug("List All Animali");
        
        List<Animale> lAnim = animaleRepo.findAll();
        
        if(lAnim.isEmpty())
   			 throw new CrudeliaException(msgS.getMessaggio("ANIMAL_NOT_FOUND"));
        
		return lAnim.stream()
				.map(aNi -> AnimaleDTO.builder()
						.id(aNi.getId())
						.nomeAnimale(aNi.getNomeAnimale())
						.tipo(aNi.getTipo())
						.razza(aNi.getRazza())
						.noteMediche(aNi.getNoteMediche())
						.utente(UtenteDTO.builder()
								.id(aNi.getUtente().getId())

								.cognome(aNi.getUtente().getCognome())
								.nome(aNi.getUtente().getNome())
								.codiceFiscale(aNi.getUtente().getCodiceFiscale())
								.telefono(aNi.getUtente().getTelefono())
								.email(aNi.getUtente().getEmail())
								.indirizzo(aNi.getUtente().getIndirizzo())
								.password(aNi.getUtente().getPassword())
								.dataRegistrazione(aNi.getUtente().getDataRegistrazione())
								.role(aNi.getUtente().getRole())
								.build()								
								)
						.build()
						
						).collect(Collectors.toList());
	}

    @Override
    public List<AnimaleDTO> findByUserId(Integer id) throws CrudeliaException {
        utenteRepo.findById(id)
                .orElseThrow(() -> new CrudeliaException("Utente con id " + id + " non trovato"));

        List<Animale> animali = animaleRepo.findByUtenteId(id);

        return animali.stream()
                .map(a -> AnimaleDTO.builder()
                        .id(a.getId())
                        .utente(UtenteDTO.builder()
                                .id(a.getUtente().getId())
                                .build()
                        )
                        .nomeAnimale(a.getNomeAnimale())
                        .tipo(a.getTipo())
                        .razza(a.getRazza())
                        .noteMediche(a.getNoteMediche())
                        .build())
                .toList();

    }


}
