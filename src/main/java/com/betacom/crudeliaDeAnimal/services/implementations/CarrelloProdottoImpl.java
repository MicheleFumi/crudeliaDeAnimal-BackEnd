package com.betacom.crudeliaDeAnimal.services.implementations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.betacom.crudeliaDeAnimal.dto.CarrelloDTO;
import com.betacom.crudeliaDeAnimal.dto.CarrelloProdottoDTO;
import com.betacom.crudeliaDeAnimal.dto.ProdottoDTO;
import com.betacom.crudeliaDeAnimal.exception.CrudeliaException;
import com.betacom.crudeliaDeAnimal.models.Carrello;
import com.betacom.crudeliaDeAnimal.models.CarrelloProdotto;
import com.betacom.crudeliaDeAnimal.models.Prodotto;
import com.betacom.crudeliaDeAnimal.models.Utente;
import com.betacom.crudeliaDeAnimal.repositories.ICarrelloProdottoRepository;
import com.betacom.crudeliaDeAnimal.repositories.ICarrelloRepository;
import com.betacom.crudeliaDeAnimal.repositories.IProdottoRepository;
import com.betacom.crudeliaDeAnimal.repositories.IUtenteRepository;
import com.betacom.crudeliaDeAnimal.requests.CarrelloProdottoReq;
import com.betacom.crudeliaDeAnimal.services.interfaces.ICarrelloProdottoServices;
import com.betacom.crudeliaDeAnimal.services.interfaces.ICarrelloServices;
import com.betacom.crudeliaDeAnimal.services.interfaces.IMessaggioServices;
import com.betacom.crudeliaDeAnimal.utils.StatoOrdine;
import com.betacom.crudeliaDeAnimal.utils.StatoProdotto;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CarrelloProdottoImpl implements ICarrelloProdottoServices {
	
	private ICarrelloProdottoRepository carrelloProRepo;
	private ICarrelloRepository carrelloRepo;
	private IProdottoRepository prodottoRepo;
	private IUtenteRepository utenteRepo;
	private ICarrelloServices carrelloServices;

	private IMessaggioServices msgS;



	
	public CarrelloProdottoImpl(ICarrelloProdottoRepository carrelloProRepo, ICarrelloRepository carrelloRepo,
			IProdottoRepository prodottoRepo, IUtenteRepository utenteRepo, ICarrelloServices carrelloServices,
			IMessaggioServices msgS) {
		super();
		this.carrelloProRepo = carrelloProRepo;
		this.carrelloRepo = carrelloRepo;
		this.prodottoRepo = prodottoRepo;
		this.utenteRepo = utenteRepo;
		this.carrelloServices = carrelloServices;
		this.msgS = msgS;
	}

	@Override
	public CarrelloDTO createProdotto(Integer idUtente, CarrelloProdottoReq  prodottoReq) throws CrudeliaException {
		
		  // Recupero carrello o lo creo se non esiste
	    Utente utente = utenteRepo.findById(idUtente)
	            .orElseThrow(() -> new CrudeliaException(msgS.getMessaggio("USER_NOT_FOUND")));

	    Carrello carrello = carrelloRepo.findByUtenteId(idUtente)
	            .orElseGet(() -> {
	                Carrello c = new Carrello();
	                c.setUtente(utente);
	                c.setProdotti(new ArrayList<>());
	                c.setStatoOrdine(StatoOrdine.NUOVO);
	                return carrelloRepo.save(c);
	            });
 
	    // Controllo prodotto
	    Prodotto prodotto = prodottoRepo.findById(prodottoReq.getProdotto().getId())
	            .orElseThrow(() -> new CrudeliaException(msgS.getMessaggio("PRODUCT_NOT_FOUND")));

	    // Controllo se il prodotto è già presente nel carrello
	    Optional<CarrelloProdotto> carrelloProdOpt = carrello.getProdotti().stream()
	            .filter(p -> p.getProdotto().getId().equals(prodotto.getId()))
	            .findFirst();

	    if (carrelloProdOpt.isPresent()) {
	        // Aggiorno quantità esistente
	        CarrelloProdotto cp = carrelloProdOpt.get();
	        cp.setQuantitaRichieste(cp.getQuantitaRichieste() +prodottoReq.getQuantitaRicheste());
	        carrelloProRepo.save(cp);
	    } else {
	        // Creo nuovo CarrelloProdotto
	    	
	        CarrelloProdotto cp = new CarrelloProdotto();
	        cp.setProdotto(prodotto);
	        cp.setQuantitaRichieste(prodottoReq.getQuantitaRicheste());
	        cp.setCarrello(carrello);
	        cp.setStatoProdotto(StatoProdotto.CREATO);
	        carrello.getProdotti().add(cp);
	        carrelloProRepo.save(cp);
	    }

	    // Salvo carrello aggiornato
	    carrelloRepo.save(carrello);

	    return toCarrelloDTO(carrello);
	}

	@Override
	public CarrelloDTO updateProdotto(Integer idUtente, CarrelloProdottoReq  prodottoReq) throws CrudeliaException {
		 CarrelloDTO carrelloDTO = carrelloServices.getCarrelloByUtente(idUtente,false);
		 
	        Carrello carrello = carrelloRepo.findById(carrelloDTO.getId())
	                .orElseThrow(() -> new CrudeliaException(msgS.getMessaggio("CART_NOT_FOUND")));

	        CarrelloProdotto cp = carrello.getProdotti().stream()
	                .filter(p -> p.getProdotto().getId().equals(prodottoReq.getProdotto().getId()))
	                .findFirst()
	                .orElseThrow(() -> new CrudeliaException(msgS.getMessaggio("PRODUCT_NOT_IN_CART")));

	        cp.setQuantitaRichieste(prodottoReq.getQuantitaRicheste());
	        cp.setStatoProdotto(StatoProdotto.MODIFICATO);
	        carrelloProRepo.save(cp);

	        return toCarrelloDTO(carrello);
	}



	@Transactional
	@Override
	public CarrelloDTO deleteProdotto(Integer idUtente, Integer idProdotto) throws CrudeliaException {
		CarrelloDTO carrelloDTO = carrelloServices.getCarrelloByUtente(idUtente , false);
        Carrello carrello = carrelloRepo.findById(carrelloDTO.getId())
                .orElseThrow(() -> new CrudeliaException(msgS.getMessaggio("CART_NOT_FOUND")));

        CarrelloProdotto cp = carrello.getProdotti().stream()
                .filter(p -> p.getProdotto().getId().equals(idProdotto))
                .findFirst()
                .orElseThrow(() -> new CrudeliaException(msgS.getMessaggio("PRODUCT_NOT_IN_CART")));

        carrello.getProdotti().remove(cp);
        cp.setStatoProdotto(StatoProdotto.CANCELLATO);
        carrelloProRepo.delete(cp);

        return toCarrelloDTO(carrello);
	}
	
	
	private CarrelloDTO toCarrelloDTO(Carrello carrello) {
        List<CarrelloProdottoDTO> prodottiDTO = carrello.getProdotti().stream()
        		.map(p-> CarrelloProdottoDTO.builder()
        				.id(p.getId())
        				.prodotto(ProdottoDTO.builder()
        						.id(p.getProdotto().getId())
        						.nomeProdotto(p.getProdotto().getNomeProdotto())
        						.descrizione(p.getProdotto().getDescrizione())
        						.categoria(p.getProdotto().getCategoria())
        						.tipoAnimale(p.getProdotto().getTipoAnimale())
        						.quantitaDisponibile(p.getProdotto().getQuantitaDisponibile())
        						.prezzo(p.getProdotto().getPrezzo())
        						.immagineUrl(p.getProdotto().getImmagineUrl())
        						.build()
        						
        						).quantitaRicheste(p.getQuantitaRichieste())
        				.build()
        				
        				).collect(Collectors.toList());

        return CarrelloDTO.builder()
                .id(carrello.getId())
                .idUtente(carrello.getUtente().getId())
                .prodotti(prodottiDTO)
                .build();
    }



}
