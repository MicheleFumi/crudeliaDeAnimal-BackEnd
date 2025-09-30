package com.betacom.crudeliaDeAnimal;

import lombok.extern.log4j.Log4j2;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.betacom.crudeliaDeAnimal.controller.OrdineProdottoController;
import com.betacom.crudeliaDeAnimal.dto.OrdineProdottoDTO;
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
import com.betacom.crudeliaDeAnimal.requests.ProdottoReq;
import com.betacom.crudeliaDeAnimal.response.ResponseBase;
import com.betacom.crudeliaDeAnimal.response.ResponseList;
import com.betacom.crudeliaDeAnimal.response.ResponseObject;
import com.betacom.crudeliaDeAnimal.utils.StatoOrdine;
import com.betacom.crudeliaDeAnimal.utils.StatoProdotto;

@Log4j2
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrdineProdottoControllerTest {
	
	
	@Autowired
	private OrdineProdottoController ordProController;
	
	
	
	@Autowired
    private IProdottoRepository iproRep;
	
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
	 
	
	
	
	@Test
    @Order(1)
    public void createOrdineProdottoTest() throws CrudeliaException {
		
        log.debug("create ordineProdotto!");
        
        
        //  Utente esistente

        Optional<Utente> ut=utenteRepo.findById(2);
        
        
        Assertions.assertThat(ut).isPresent();
        
        log.info("Nome Utente : " + ut.get().getNome());

        //  Carrello dell’utente

		Optional<Carrello> car = carrelloRepo.findByUtenteId(ut.get().getId());
		
	    Assertions.assertThat(car).isPresent();

        log.info("ID Carrello  : " + car.get().getId());
        

        // Prodotto esistente

		Optional<Prodotto> pro = prodottoRepo.findByNomeProdotto("Mangime per Pesci Tropicali");
		
	    Assertions.assertThat(pro).isPresent();
	    
	    
	    Prodotto prodotto = pro.get();
	    
	    
	    log.debug("Nome Prodotto : " + prodotto.getNomeProdotto() + "Prezzo: " + prodotto.getPrezzo() + " Quantita Disp" + prodotto.getQuantitaDisponibile());

        
	    
	    // Inserisco un prodotto nel carrello

	    
	    CarrelloProdotto carProdotto = new CarrelloProdotto();
	    
	    carProdotto.setCarrello(car.get());
	    
	    carProdotto.setProdotto(prodotto);
	    
	    carProdotto.setQuantitaRichieste(15);
	    
	    carProdotto.setStatoProdotto(StatoProdotto.CREATO);
	    
	    carProdotto = carProdRepo.save(carProdotto);
	    

        // Creo un ordine

	    Ordine ordine = new Ordine();
	    ordine.setUtente(ut.get());
	    ordine.setDataOrdine(LocalDate.now());
	    ordine.setCarrello(car.get());
	    ordine.setStatoOrdine(StatoOrdine.ORDINATO);
	   
	 // Calcolo totale reale
	    
	    BigDecimal totale = prodotto.getPrezzo()
	        .multiply(BigDecimal.valueOf(carProdotto.getQuantitaRichieste()));
	    
	    ordine.setTotaleOrdine(totale);
	    
	    ordine = ordineRepo.save(ordine); 
	    

	    log.debug("ID ORDER : " + ordine.getId() +  " totale : "+ordine.getTotaleOrdine());
	    
        // Creo OrdineProdotto

		OrdineProdottoReq req=new OrdineProdottoReq();
		req.setIdOrdine(ordine.getId());
		req.setProdotto(pro.get());
		req.setQuantita(carProdotto.getQuantitaRichieste());
		
	
		
       ResponseBase resp= ordProController.create(req);
       
		Assertions.assertThat(resp.getRc()).isEqualTo(true);

	

		
    }
	
	
	@Test
    @Order(2)
    public void createSecondoOrdineProdottoTest() throws CrudeliaException {
		
		log.debug("create secondo ordineProdotto!");

	    // Recupera un utente esistente (o creane uno nuovo)
	    Optional<Utente> ut = utenteRepo.findById(2);
	    Assertions.assertThat(ut).isPresent();
	    log.info("Nome Utente per il secondo ordine: " + ut.get().getNome());
	    
	    // Recupera un altro prodotto (o lo stesso, a seconda del test)
	    Optional<Prodotto> pro = prodottoRepo.findByNomeProdotto("Mangime per Pesci Tropicali");
	    Assertions.assertThat(pro).isPresent();
	    
	    log.debug("Nome Prodotto : " + pro.get().getNomeProdotto() + " , Prezzo: " + pro.get().getPrezzo() + " , Quantita Disp : " + pro.get().getQuantitaDisponibile());


	    // Crea un nuovo carrello per l'utente (o usa un altro carrello se l'utente ne ha più di uno)
	    Optional<Carrello> car = carrelloRepo.findByUtenteId(ut.get().getId());
	    Assertions.assertThat(car).isPresent();
	    
	    Prodotto prodotto = pro.get();
	    log.debug("Prodotto per il secondo ordine: " + prodotto.getNomeProdotto());

	    // Inserisci un altro prodotto nel carrello
	    CarrelloProdotto carProdotto = new CarrelloProdotto();
	    carProdotto.setCarrello(car.get());
	    carProdotto.setProdotto(prodotto);
	    carProdotto.setQuantitaRichieste(10);
	    carProdotto.setStatoProdotto(StatoProdotto.CREATO);
	    carProdotto = carProdRepo.save(carProdotto);

	    // Crea il secondo ordine
	    Ordine ordine = new Ordine();
	    ordine.setUtente(ut.get());
	    ordine.setDataOrdine(LocalDate.now());
	    ordine.setCarrello(car.get());
	    ordine.setStatoOrdine(StatoOrdine.ORDINATO);
	    BigDecimal totale = prodotto.getPrezzo().multiply(BigDecimal.valueOf(carProdotto.getQuantitaRichieste()));
	    ordine.setTotaleOrdine(totale);
	    ordine = ordineRepo.save(ordine);

	    log.debug("ID Secondo Ordine : " + ordine.getId());

	    // Crea OrdineProdotto per il secondo ordine
	    OrdineProdottoReq req = new OrdineProdottoReq();
	    req.setIdOrdine(ordine.getId());
	    req.setProdotto(pro.get());
	    req.setQuantita(carProdotto.getQuantitaRichieste());

	    ResponseBase resp = ordProController.create(req);
	    Assertions.assertThat(resp.getRc()).isTrue();
	    log.debug("Secondo OrdineProdotto creato con successo.");
	}
	
	
  
	
	@Test
    @Order(3)
    public void createOrdineProdottoTestErrors() throws CrudeliaException {
		
		Prodotto pro = new Prodotto();
		pro.setId(5264);
	    
	    OrdineProdottoReq req = new OrdineProdottoReq();
	    req.setIdOrdine(7);
	    req.setQuantita(5);
	    req.setProdotto(pro);
	    
	    ResponseBase resp= ordProController.create(req);

		Assertions.assertThat(resp.getRc()).isEqualTo(false);
        Assertions.assertThat(resp.getMsg()).isEqualTo("ORDER_NOT_FOUND");

        //-------------------------------------------------------------------------

        
        
        // Caso quantità non valida
        req.getProdotto().setId(1); // prodotto esistente
        req.setQuantita(0); // quantità invalida
        resp = ordProController.create(req);
        Assertions.assertThat(resp.getRc()).isEqualTo(false);
        Assertions.assertThat(resp.getMsg()).isEqualTo("ORDER_ITEM_NOT_AVAILABLE");

        //-------------------------------------------------------------------------

        // Prodotto inesistente
        
               
		
		  OrdineProdottoReq reqProdottoInesistente = new OrdineProdottoReq();
		  
		  reqProdottoInesistente.setIdOrdine(1); reqProdottoInesistente.setQuantita(5);
		  
		  Prodotto prodottoInesistente = new Prodotto();
		  prodottoInesistente.setId(888);
		  reqProdottoInesistente.setProdotto(prodottoInesistente);
		  
		  ResponseBase resp4 = ordProController.create(reqProdottoInesistente);
		  Assertions.assertThat(resp4.getRc()).isEqualTo(false);
		  Assertions.assertThat(resp4.getMsg()).isEqualTo("PRODUCT_NOT_FOUND");
		  
		  
		//  Prodotto = null
		    OrdineProdottoReq reqProdottoNullo = new OrdineProdottoReq();
		    reqProdottoNullo.setIdOrdine(1);
		    reqProdottoNullo.setQuantita(5);
		    reqProdottoNullo.setProdotto(null);

		    ResponseBase resp2 = ordProController.create(reqProdottoNullo);
		    Assertions.assertThat(resp2.getRc()).isEqualTo(false);
		    Assertions.assertThat(resp2.getMsg()).isEqualTo("PRODUCT_NOT_FOUND");
		 


	}
	
	
	@Test
    @Order(4)
    public void updateOrdineProdottoTest() throws CrudeliaException {
        log.debug("update ordineProdotto!");
        
        OrdineProdottoReq req=new OrdineProdottoReq();
        req.setId(1);
        req.setQuantita(10);
        
        ResponseBase r = ordProController.update(req);
        Assertions.assertThat(r.getRc()).isEqualTo(true);
        
        
        
        
       // quatita zero 
        req.setId(2);
        req.setQuantita(0);
        r = ordProController.update(req);
        Assertions.assertThat(r.getRc()).isEqualTo(false);
	    Assertions.assertThat(r.getMsg()).isEqualTo("QUANTITY_GREATER_THAN_ZERO");


	    //ORDINE NON TROVATO
	    req.setId(265);
	    r = ordProController.update(req);
        Assertions.assertThat(r.getRc()).isEqualTo(false);
	    Assertions.assertThat(r.getMsg()).isEqualTo("ORDER_NOT_FOUND");

        
	}
	

	@Test
    @Order(5)
    public void findByIdOrdineProdottoTest() throws CrudeliaException {
		
        log.debug("findById ordineProdotto!");
        
	
        
        Optional<OrdineProdotto> ordPro= ordProRepo.findById(2);
        
        log.debug("orderprodotto detales" + ordPro.get().getQuantita());
        
        ResponseObject<OrdineProdottoDTO> resp= ordProController.listById(ordPro.get().getId());
        
        Assertions.assertThat(resp.getRc()).isTrue();
        
        
        
        ResponseObject<OrdineProdottoDTO> resp2= ordProController.listById(2345);
        Assertions.assertThat(resp2.getRc()).isEqualTo(false);
	    Assertions.assertThat(resp2.getMsg()).isEqualTo("ORDER_NOT_FOUND");

        


	    	    

	}
	
	@Test
    @Order(6)
    public void listAllOrdineProdottoTest() throws CrudeliaException {
        log.debug("listAll ordineProdotto!");
        
	ResponseBase resp= ordProController.listAll();
		
		// controlla che il controller abbia risposto correttamente
	    Assertions.assertThat(resp).isInstanceOf(ResponseList.class);
	    ResponseList<?> r = (ResponseList<?>) resp;

	    Assertions.assertThat(r.getRc()).isEqualTo(true);
	    Assertions.assertThat(r.getDati()).isInstanceOf(List.class);

	    
	    List<?> lista = (List<?>) r.getDati();
	    Assertions.assertThat(lista).isNotEmpty();
        
	}
	
	@Test
    @Order(7)
    public void removeOrdineProdottoTest() throws CrudeliaException {
        log.debug("remove ordineProdotto!");
        
       Optional<OrdineProdotto> ordPro= ordProRepo.findById(1);
        
        log.debug("orderprodotto detales" + ordPro.get().getQuantita());
        
        OrdineProdottoReq req = new OrdineProdottoReq();
        req.setId(ordPro.get().getId());
        
        log.debug("Elimino l'entità con ID: " + req.getIdOrdine() );

        ResponseBase  resp= ordProController.remove(req);
        
        Assertions.assertThat(resp.getRc()).isEqualTo(true);
        
        
       req.setId(343);
        
        log.debug("Elimino l'entità con ID: " + req.getIdOrdine() );

        ResponseBase  resp2= ordProController.remove(req);
        
        Assertions.assertThat(resp2.getRc()).isEqualTo(false);
	    Assertions.assertThat(resp2.getMsg()).isEqualTo("ORDER_NOT_FOUND");

        
	}
	
	
	
	
}
