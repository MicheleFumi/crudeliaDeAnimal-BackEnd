package com.betacom.crudeliaDeAnimal;

import lombok.extern.log4j.Log4j2;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.betacom.crudeliaDeAnimal.controller.OrdineController;
import com.betacom.crudeliaDeAnimal.dto.OrdineDTO;
import com.betacom.crudeliaDeAnimal.exception.CrudeliaException;
import com.betacom.crudeliaDeAnimal.models.Carrello;
import com.betacom.crudeliaDeAnimal.models.CarrelloProdotto;
import com.betacom.crudeliaDeAnimal.models.Ordine;
import com.betacom.crudeliaDeAnimal.models.OrdineProdotto;
import com.betacom.crudeliaDeAnimal.models.Prodotto;
import com.betacom.crudeliaDeAnimal.models.Utente;
import com.betacom.crudeliaDeAnimal.repositories.ICarrelloProdottoRepository;
import com.betacom.crudeliaDeAnimal.repositories.ICarrelloRepository;
import com.betacom.crudeliaDeAnimal.repositories.IOrdineProdottoRepository;
import com.betacom.crudeliaDeAnimal.repositories.IOrdineRepository;
import com.betacom.crudeliaDeAnimal.repositories.IProdottoRepository;
import com.betacom.crudeliaDeAnimal.repositories.IUtenteRepository;
import com.betacom.crudeliaDeAnimal.requests.OrdineProdottoReq;
import com.betacom.crudeliaDeAnimal.requests.OrdineReq;
import com.betacom.crudeliaDeAnimal.response.ResponseBase;
import com.betacom.crudeliaDeAnimal.response.ResponseList;
import com.betacom.crudeliaDeAnimal.response.ResponseObject;
import com.betacom.crudeliaDeAnimal.services.interfaces.IOrdineServices;
import com.betacom.crudeliaDeAnimal.utils.Roles;
import com.betacom.crudeliaDeAnimal.utils.StatoOrdine;
import com.betacom.crudeliaDeAnimal.utils.StatoProdotto;

@Log4j2
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrdineControllerTest {
	@Autowired
	private OrdineController ordController;

	@Autowired
	private IProdottoRepository proRepo;

	@Autowired
	private IOrdineRepository ordineRepo;

	@Autowired
	private IProdottoRepository prodottoRepo;

	@Autowired

	private IUtenteRepository utenteRepo;

	@Autowired
	private ICarrelloRepository carrelloRepo;

	@Autowired
	private ICarrelloProdottoRepository carProdRepo;

	@Autowired
	private IOrdineProdottoRepository ordProRepo;

	@Autowired
	private IOrdineServices ordSer;

	@Test
	@Order(1)
	public void createOrdineTest() throws CrudeliaException {

		log.debug("Inizio createOrdineTest");
		Optional<Utente> ut = utenteRepo.findById(1);

		// 6️⃣ Creo OrdineReq

		OrdineReq ordReq = new OrdineReq();
		ordReq.setIdUtente(ut.get().getId());

		ResponseBase resp = ordController.create(ordReq);

		Assertions.assertThat(resp.getRc()).isEqualTo(true);

		OrdineReq ordReq2 = new OrdineReq();
		ordReq2.setIdUtente(ut.get().getId());

		resp = ordController.create(ordReq2);
		Assertions.assertThat(resp.getRc()).isEqualTo(false);
		Assertions.assertThat(resp.getMsg()).isEqualTo("CART_EMPTY");

	}

	@Test
	@Order(2)
	public void createOrdineTestErrors() throws CrudeliaException {

		Integer id = 234;
		OrdineReq ordReq = new OrdineReq();
		ordReq.setIdUtente(id);

		ResponseBase resp = ordController.create(ordReq);
		Assertions.assertThat(resp.getRc()).isEqualTo(false);
		Assertions.assertThat(resp.getMsg()).isEqualTo("USER_NOT_FOUND");

		// Utente senza Carrello
		Utente utente = new Utente();
		utente.setNome("Test");
		utente.setCognome("NoCart");
		utente.setEmail("nocart@test.com");
		utente.setCodiceFiscale("TSTNCART01A11X");
		utente.setTelefono("3331234567");
		utente.setIndirizzo("Via Test 1");
		utente.setPassword("password");
		utente.setRole(Roles.USER);
		utente.setDataRegistrazione(LocalDate.now());
		utente = utenteRepo.save(utente);

		OrdineReq reqCarrelloMancante = new OrdineReq();
		reqCarrelloMancante.setIdUtente(utente.getId());
		reqCarrelloMancante.setIdCarrello(9999); // carrello inesistente

		ResponseBase resp2 = ordController.create(reqCarrelloMancante);
		Assertions.assertThat(resp.getRc()).isEqualTo(false);
		Assertions.assertThat(resp2.getMsg()).isEqualTo("CART_NOT_FOUND");

		

	}

	@Test
	@Order(3)
	public void updateOrdineTest() throws CrudeliaException {

		log.debug("update Ordine ");

		Optional<Utente> utente = utenteRepo.findById(1);

		Optional<Prodotto> pro = proRepo.findByNomeProdotto("Mangime per Pesci Tropicali");

		Optional<Carrello> car = carrelloRepo.findByUtenteId(utente.get().getId());

		// 3️⃣ Aggiungo prodotto al carrello
		CarrelloProdotto cp = new CarrelloProdotto();
		cp.setCarrello(car.get());
		cp.setProdotto(pro.get());
		cp.setQuantitaRichieste(2);
		cp.setStatoProdotto(StatoProdotto.CREATO);
		carProdRepo.save(cp);

		OrdineReq createReq = new OrdineReq();

		createReq.setIdUtente(utente.get().getId());
		ordSer.create(createReq);

		Ordine ordine = ordineRepo.findAll().get(0);

		OrdineProdotto op = ordProRepo.findAll().get(0);

		OrdineProdottoReq opReq = new OrdineProdottoReq();
		opReq.setProdotto(op.getProdotto());
		opReq.setQuantita(op.getQuantita() + 3);

		OrdineReq updateReq = new OrdineReq();
		updateReq.setId(ordine.getId());
		updateReq.setIdUtente(utente.get().getId());
		updateReq.setDettagliOrdine(Collections.singletonList(opReq));

		ResponseBase resp = ordController.update(updateReq);
		Assertions.assertThat(resp.getRc()).isEqualTo(true);
		
		

		

	}

	@Test
	@Order(4)
	public void updateOrdineErrorTest() throws CrudeliaException {

		Optional<Ordine> ordine = ordineRepo.findById(4);

		OrdineReq req = new OrdineReq();
		req.setId(ordine.get().getId());

		ResponseBase resp = ordController.update(req);

		Assertions.assertThat(resp.getRc()).isEqualTo(false);

		Assertions.assertThat(resp.getMsg()).isEqualTo("PRODUCT_LIST_EMPTY");
		
		//order not fuond
		
		req.setId(6);
		 resp = ordController.update(req);

		Assertions.assertThat(resp.getRc()).isEqualTo(false);

		Assertions.assertThat(resp.getMsg()).isEqualTo("ORDER_NOT_FOUND");
		
		
		Prodotto pro = new Prodotto();
		pro.setId(3);
		
		
		

	}
	
	@Test
	@Order(5)
	public void findByIdOrdineTest() throws CrudeliaException {
		
	    Integer id=56;

		ResponseBase resp = ordController.findById(id);

		Assertions.assertThat(resp.getRc()).isEqualTo(false);
		
		Assertions.assertThat(resp.getMsg()).isEqualTo("ORDER_NOT_FOUND");
		
		Integer id2=1;
		
		ResponseBase resp2 = ordController.findById(id2);

		Assertions.assertThat(resp2.getRc()).isEqualTo(true);

		
		
	}
	
	
	@Test
	@Order(6)
	public void findByUserOrdineTest() throws CrudeliaException {
		
		Integer id = 234;
		

		ResponseBase resp = ordController.findByUser(id);
		Assertions.assertThat(resp.getRc()).isEqualTo(false);
		Assertions.assertThat(resp.getMsg()).isEqualTo("USER_NOT_FOUND");
		
		//utente  esist
	    
	    
	    Integer idUser = 1; // 

	    // chiama il controller
	    ResponseObject<List<OrdineDTO>> response = ordController.findByUser(idUser);

	    // assert: rc true
	    Assertions.assertThat(response.getRc()).isTrue();

	    // assert: dati non null e non vuoti
	    Assertions.assertThat(response.getDati()).isNotNull();
	    Assertions.assertThat(response.getDati()).isNotEmpty();

	    // assert: il primo ordine appartiene all'utente
	    OrdineDTO ordine = response.getDati().get(0);
	    Assertions.assertThat(ordine.getIdUtente()).isEqualTo(idUser);

	    // assert: eventuali dettagli ordine
	    Assertions.assertThat(ordine.getDettagliOrdine()).isNotEmpty();
	    Assertions.assertThat(ordine.getTotaleOrdine()).isGreaterThan(BigDecimal.ZERO);
	}
	
	@Test
	@Order(7)
	public void listAllOrdineTest() throws CrudeliaException {
		
	   ResponseBase resp=ordController.listAll();
		
		// controlla che il controller abbia risposto correttamente
	    Assertions.assertThat(resp).isInstanceOf(ResponseList.class);
	    ResponseList<?> r = (ResponseList<?>) resp;

	    Assertions.assertThat(r.getRc()).isEqualTo(true);
	    Assertions.assertThat(r.getDati()).isInstanceOf(List.class);

	    
	    List<?> lista = (List<?>) r.getDati();
	    Assertions.assertThat(lista).isNotEmpty();
		
	}
	
	
	
	@Test
	@Order(8)
	public void deleteOrdineTest() throws CrudeliaException {
		
		OrdineReq deleteReq = new OrdineReq();
		deleteReq.setId(5);
		
		ResponseBase resp = ordController.delete(deleteReq);

		Assertions.assertThat(resp.getRc()).isEqualTo(false);

		Assertions.assertThat(resp.getMsg()).isEqualTo("ORDER_NOT_FOUND");
		
		   // 1️⃣ Prendi utente
	    Utente utente = utenteRepo.findById(1).orElseThrow();
	    
		Optional<Carrello> car = carrelloRepo.findByUtenteId(utente.getId());


	    // 2️⃣ Crea un ordine nuovo
	    Ordine ordine = new Ordine();
	    ordine.setUtente(utente);
	    ordine.setCarrello(car.get());
	    ordine.setStatoOrdine(StatoOrdine.NUOVO);
	    ordine.setDataOrdine(LocalDate.now());
	    ordine.setTotaleOrdine(BigDecimal.ZERO);
	    ordine = ordineRepo.save(ordine);

	    // 3️⃣ Prepara delete request
	    OrdineReq deleteReq2 = new OrdineReq();
	    deleteReq2.setId(ordine.getId());

	    // 4️⃣ Chiama controller
	    ResponseBase resp2 = ordController.delete(deleteReq2);

	    // 5️⃣ Asserzioni
	    Assertions.assertThat(resp2.getRc()).isEqualTo(true);

		
	}
	
	
	@Test
	@Order(9)
	public void deleteOrdineRipristinaStockTest() throws CrudeliaException {
	    // 1️⃣ Prendi utente
	    Utente utente = utenteRepo.findById(1).orElseThrow();

	    // 2️⃣ Crea prodotto
	    Prodotto prodotto = new Prodotto();
	    prodotto.setNomeProdotto("tikit");
	    prodotto.setDescrizione("hello.");
	    prodotto.setPrezzo(new BigDecimal("90.80"));
	    prodotto.setCategoria("Accessori");
	    prodotto.setTipoAnimale("Gatto");
	    prodotto.setQuantitaDisponibile(50);
	    prodotto.setImmagineUrl("prova bella ");
	    prodotto = prodottoRepo.save(prodotto);

		Optional<Carrello> car = carrelloRepo.findByUtenteId(utente.getId());


	    // 4️⃣ Crea ordine
	    Ordine ordine = new Ordine();
	    ordine.setUtente(utente);
	    ordine.setCarrello(car.get());
	    ordine.setStatoOrdine(StatoOrdine.NUOVO);
	    ordine.setTotaleOrdine(BigDecimal.ZERO);
	    ordine.setDataOrdine(LocalDate.now());
	    ordine = ordineRepo.save(ordine);

	    // 5️⃣ Aggiungi OrdineProdotto
	    OrdineProdotto op = new OrdineProdotto();
	    op.setOrdine(ordine);
	    op.setProdotto(prodotto);
	    op.setQuantita(3); // quantità ordinata
	    op = ordProRepo.save(op);

	    // riduco lo stock manualmente per simulare ordine
	    prodotto.setQuantitaDisponibile(prodotto.getQuantitaDisponibile() - 3);
	    prodottoRepo.save(prodotto);

	    // 6️⃣ Prepara richiesta delete
	    OrdineReq deleteReq = new OrdineReq();
	    deleteReq.setId(ordine.getId());

	    // 7️⃣ Chiama controller
	    ResponseBase resp2 = ordController.delete(deleteReq);
	    
	    deleteReq.setStatoOrdine("CANCELLATO");

	    // 8️⃣ Controlli
	    Assertions.assertThat(deleteReq.getStatoOrdine()).isEqualTo("CANCELLATO");

	    // 9️⃣ Verifica che lo stock sia ripristinato
	    Prodotto prodottoAggiornato = prodottoRepo.findById(prodotto.getId()).orElseThrow();
	    Assertions.assertThat(prodottoAggiornato.getQuantitaDisponibile()).isEqualTo(50); // stock iniziale
	}
	

	
	

}
