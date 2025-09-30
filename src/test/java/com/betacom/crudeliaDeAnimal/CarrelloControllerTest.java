
package com.betacom.crudeliaDeAnimal;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.betacom.crudeliaDeAnimal.controller.CarrelloController;
import com.betacom.crudeliaDeAnimal.dto.CarrelloDTO;
import com.betacom.crudeliaDeAnimal.dto.CarrelloProdottoDTO;
import com.betacom.crudeliaDeAnimal.dto.CarrelloRespDTO;
import com.betacom.crudeliaDeAnimal.exception.CrudeliaException;
import com.betacom.crudeliaDeAnimal.models.Carrello;
import com.betacom.crudeliaDeAnimal.models.CarrelloProdotto;
import com.betacom.crudeliaDeAnimal.models.Prodotto;
import com.betacom.crudeliaDeAnimal.models.Utente;
import com.betacom.crudeliaDeAnimal.repositories.ICarrelloProdottoRepository;
import com.betacom.crudeliaDeAnimal.repositories.ICarrelloRepository;
import com.betacom.crudeliaDeAnimal.repositories.IProdottoRepository;
import com.betacom.crudeliaDeAnimal.repositories.IUtenteRepository;
import com.betacom.crudeliaDeAnimal.response.ResponseBase;
import com.betacom.crudeliaDeAnimal.services.implementations.CarrelloImpl;
import com.betacom.crudeliaDeAnimal.services.interfaces.ICarrelloServices;
import com.betacom.crudeliaDeAnimal.services.interfaces.IMessaggioServices;
import com.betacom.crudeliaDeAnimal.utils.Roles;
import com.betacom.crudeliaDeAnimal.utils.StatoOrdine;

import lombok.extern.log4j.Log4j2;

@Log4j2

@SpringBootTest

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CarrelloControllerTest {

	@Autowired
	private CarrelloController carrelloCon;

	@Autowired
	private IUtenteRepository utenteRepo;

	@Autowired
	private ICarrelloRepository carrelloRepo;

	@Autowired
	private IMessaggioServices messServ;
	
	@Autowired
	private  IProdottoRepository proRepo;
	
	@Autowired
	private ICarrelloProdottoRepository carProRepo;
	
	@Autowired
	private ICarrelloServices carSer;

	

	@Test

	@Order(1)
	public void utenteConCarrelloTest() throws CrudeliaException {

		log.debug("Utente con carrello già presente");

		Utente utente = utenteRepo.findById(1).get();

		Carrello car = new Carrello();

		car.setUtente(utente);

		car.setStatoOrdine(StatoOrdine.NUOVO);

		carrelloRepo.save(car);

		ResponseBase resp = carrelloCon.getCarrello(utente.getId());

		Assertions.assertThat(resp.getRc()).isEqualTo(true);

		Assertions.assertThat(resp.getMsg());

	}

	@Test

	@Order(2)
	public void utenteSenzaCarrelloTest() throws CrudeliaException {

		log.debug("Test: Creazione del carrello per un nuovo utente");

		Utente nuovoUtente = new Utente();
		nuovoUtente.setNome("Luca");
		nuovoUtente.setCognome("Bianchi");
		nuovoUtente.setEmail("luca.bianchi@test.com");
		nuovoUtente.setCodiceFiscale("LCBNCH99A01H501X");
		nuovoUtente.setTelefono("3337654321");
		nuovoUtente.setIndirizzo("Via Milano 2");
		nuovoUtente.setPassword("password");
		nuovoUtente.setRole(Roles.USER);
		nuovoUtente.setDataRegistrazione(LocalDate.now());
		nuovoUtente = utenteRepo.save(nuovoUtente);

		Assertions.assertThat(carrelloRepo.findByUtenteId(nuovoUtente.getId())).isNotPresent();

		ResponseBase resp = carrelloCon.getCarrello(nuovoUtente.getId());
		

		Assertions.assertThat(resp.getRc()).isTrue();
		Assertions.assertThat(carrelloRepo.findByUtenteId(nuovoUtente.getId())).isPresent();
		Assertions.assertThat(resp.getMsg());

	}

	@Test

	@Order(3)
	public void getCarrelloTestError()throws CrudeliaException {

		Integer idUtente=9387;
		
	    ResponseBase resp = carrelloCon.getCarrello(idUtente);

	    Assertions.assertThat(resp.getRc()).isFalse();
	    
	    Assertions.assertThat(resp.getMsg()).isEqualTo("USER_NOT_FOUND");

	
	
	}
	
	@Test

	@Order(4)
	public void emptyCarrelloTest() {

		log.debug("Tester per CarrelloController.emptyCarrello");

		// 1. Utente senza carrello → CART_NOT_FOUND

		Utente utSenzaCarrello = new Utente();
		utSenzaCarrello.setNome("Salah");
		utSenzaCarrello.setCognome("Fawzi");
		utSenzaCarrello.setEmail("s.fawzi@test.com");
		utSenzaCarrello.setCodiceFiscale("SEHSDHEUDH87HSDH");
		utSenzaCarrello.setTelefono("334492849457");
		utSenzaCarrello.setIndirizzo("Via napoli 10");
		utSenzaCarrello.setPassword("password12345");
		utSenzaCarrello.setRole(Roles.USER);
		utSenzaCarrello.setDataRegistrazione(LocalDate.now());
		utSenzaCarrello = utenteRepo.save(utSenzaCarrello);

		Assertions.assertThat(carrelloRepo.findByUtenteId(utSenzaCarrello.getId())).isNotPresent();

		ResponseBase resp = carrelloCon.emptyCarrello(utSenzaCarrello.getId());

		Assertions.assertThat(resp.getRc()).isEqualTo(false);

		Assertions.assertThat(resp.getMsg()).isEqualTo(messServ.getMessaggio("CART_NOT_FOUND"));


		// Utente con carrello e prodotti

		Utente utConProdotti = utenteRepo.findById(1).get();

		Optional<Carrello> carOpt = carrelloRepo.findByUtenteId(utConProdotti.getId());

		Assertions.assertThat(carOpt).isPresent();

		Carrello car = carOpt.get();

		List<CarrelloProdotto> prodottiCarrello = car.getProdotti();

		log.debug("Prodotti prima dello svuotamento: {}", prodottiCarrello);

		resp = carrelloCon.emptyCarrello(utConProdotti.getId());

		Assertions.assertThat(resp.getRc()).isTrue();
		Assertions.assertThat(resp.getMsg()).isEqualTo("Carrello svuotato con successo!");

		log.info("Carrello dell'utente {} svuotato correttamente", utConProdotti.getId());

		log.debug(" Utente inesistente");

		// Utente inesistente

		Integer idUtente = 54654;

		Assertions.assertThat(utenteRepo.findById(idUtente)).isNotPresent();

		resp = carrelloCon.emptyCarrello(idUtente);

		Assertions.assertThat(resp.getRc()).isFalse();

		Assertions.assertThat(resp.getMsg()).isEqualTo("USER_NOT_FOUND");

	}
	
	



	
	
	
	
}
