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
import com.betacom.crudeliaDeAnimal.models.Utente;
import com.betacom.crudeliaDeAnimal.repositories.ICarrelloProdottoRepository;
import com.betacom.crudeliaDeAnimal.repositories.ICarrelloRepository;
import com.betacom.crudeliaDeAnimal.repositories.IUtenteRepository;
import com.betacom.crudeliaDeAnimal.services.interfaces.ICarrelloServices;
import com.betacom.crudeliaDeAnimal.services.interfaces.IMessaggioServices;
import com.betacom.crudeliaDeAnimal.utils.StatoOrdine;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CarrelloImpl implements ICarrelloServices {


	private ICarrelloRepository carrelloRepo;
	private ICarrelloProdottoRepository carrelloProRepo;
	private IUtenteRepository utenteRepo;
	

	private IMessaggioServices msgS;

	public CarrelloImpl(ICarrelloRepository carrelloRepo, ICarrelloProdottoRepository carrelloProRepo,
			IUtenteRepository utenteRepo, IMessaggioServices msgS)
			 {
		super();
		this.carrelloRepo = carrelloRepo;
		this.carrelloProRepo = carrelloProRepo;
		this.utenteRepo = utenteRepo;
	
		this.msgS = msgS;
	
	}

	@Override
	public CarrelloDTO getCarrelloByUtente(Integer idUtente, boolean createIfMissing) throws CrudeliaException {

		Utente utente = utenteRepo.findById(idUtente)
				.orElseThrow(() -> new CrudeliaException(msgS.getMessaggio("USER_NOT_FOUND")));

		Optional<Carrello> carrelloOpt = carrelloRepo.findByUtenteId(idUtente);

		Carrello carrello;

		if (carrelloOpt.isPresent()) {
			carrello = carrelloOpt.get();
		} else {
			if (createIfMissing) {
				// ✅ Crea solo se esplicitamente richiesto
				carrello = new Carrello();
				carrello.setUtente(utente);
				carrello.setProdotti(new ArrayList<>());
				carrello.setStatoOrdine(StatoOrdine.NUOVO);
				carrelloRepo.save(carrello);
			} else {
				// ✅ Se non devo creare, lancio eccezione
				throw new CrudeliaException(msgS.getMessaggio("CART_NOT_FOUND"));
			}
		}

		return toCarrelloDTO(carrello);
	}

	@Override
	public void emptyCarrello(Integer idUtente) throws CrudeliaException {

		CarrelloDTO carrelloDTO = getCarrelloByUtente(idUtente, false);

		Carrello carrello = carrelloRepo.findById(carrelloDTO.getId())
				.orElseThrow(() -> new CrudeliaException(msgS.getMessaggio("CART_NOT_FOUND")));

		carrello.getProdotti().forEach(carrelloProRepo::delete);
		carrello.getProdotti().clear();
		carrelloRepo.save(carrello);

	}

	public CarrelloDTO toCarrelloDTO(Carrello carrello) {
		List<CarrelloProdottoDTO> prodottiDTO = carrello.getProdotti().stream().map(p -> CarrelloProdottoDTO.builder()
				.id(p.getId())
				.prodotto(ProdottoDTO.builder().id(p.getProdotto().getId())
						.nomeProdotto(p.getProdotto().getNomeProdotto()).descrizione(p.getProdotto().getDescrizione())
						.categoria(p.getProdotto().getCategoria()).tipoAnimale(p.getProdotto().getTipoAnimale())
						.quantitaDisponibile(p.getProdotto().getQuantitaDisponibile())
						.prezzo(p.getProdotto().getPrezzo()).immagineUrl(p.getProdotto().getImmagineUrl()).build()

				).quantitaRicheste(p.getQuantitaRichieste()).build()

		).collect(Collectors.toList());

		return CarrelloDTO.builder().id(carrello.getId()).idUtente(carrello.getUtente().getId()).prodotti(prodottiDTO)
				.build();
	}

}
