package com.betacom.crudeliaDeAnimal;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.betacom.crudeliaDeAnimal.controller.CarrelloProdottoController;
import com.betacom.crudeliaDeAnimal.dto.CarrelloDTO;
import com.betacom.crudeliaDeAnimal.dto.ProdottoDTO;
import com.betacom.crudeliaDeAnimal.models.Carrello;
import com.betacom.crudeliaDeAnimal.models.CarrelloProdotto;
import com.betacom.crudeliaDeAnimal.models.Prodotto;
import com.betacom.crudeliaDeAnimal.models.Utente;
import com.betacom.crudeliaDeAnimal.repositories.ICarrelloProdottoRepository;
import com.betacom.crudeliaDeAnimal.repositories.ICarrelloRepository;
import com.betacom.crudeliaDeAnimal.repositories.IProdottoRepository;
import com.betacom.crudeliaDeAnimal.repositories.IUtenteRepository;
import com.betacom.crudeliaDeAnimal.requests.CarrelloProdottoReq;
import com.betacom.crudeliaDeAnimal.response.ResponseObject;
import com.betacom.crudeliaDeAnimal.services.interfaces.IMessaggioServices;
import com.betacom.crudeliaDeAnimal.utils.Roles;

import lombok.extern.log4j.Log4j2;

@Log4j2

@SpringBootTest

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CarrelloProdottoControllerTest {
	
	@Autowired
	private CarrelloProdottoController carProCon;
	
	@Autowired
	private IUtenteRepository utenteRepo;
	
	@Autowired
	private IProdottoRepository proRepo;
	
	@Autowired
	private ICarrelloRepository carrelloRepo;
	
	@Autowired
	private ICarrelloProdottoRepository carelloProdottoRepo;
	
	@Autowired
	private IMessaggioServices messServ;

	
	@Test
	@Order(1)
	
	public void createProdottoTest() {
		
		log.debug("create prodotto");
		
		Utente utente = utenteRepo.findById(1).get();
		
		// utente trovato
		Prodotto pro = new Prodotto();
		
		pro.setNomeProdotto("Mangime per Pesci Tropicali");
		
		pro.setDescrizione("Alimento completo per pesci tropicali, ricco di nutrienti essenziali");
		
		pro.setPrezzo(new BigDecimal("40.20"));
		
		pro.setTipoAnimale("Pesce");
		
		pro.setCategoria("Alimentazione");
		
		pro.setQuantitaDisponibile(40);
		
		pro.setImmagineUrl("nino.png");
		
		pro = proRepo.save(pro);
		
		ProdottoDTO prodottoDTO = ProdottoDTO.builder()
				.id(pro.getId())
				.nomeProdotto(pro.getNomeProdotto())
				.descrizione(pro.getDescrizione())
				.prezzo(pro.getPrezzo())
				.categoria(pro.getCategoria())
				.tipoAnimale(pro.getTipoAnimale())
				.quantitaDisponibile(pro.getQuantitaDisponibile())
				.immagineUrl(pro.getImmagineUrl())
				.build();
		
		
		CarrelloProdottoReq req = new CarrelloProdottoReq();
		req.setProdotto(prodottoDTO);
		req.setQuantitaRicheste(2);
		
		ResponseObject<CarrelloDTO> r = carProCon.createProdotto(utente.getId(), req);
		
		Assertions.assertThat(r.getRc()).isEqualTo(true);
	}
	
	
	@Test
	@Order(2)
	public void checkProdottoIsPresentTest() {
		
		log.debug("check prodotto");
		
		// utente esistente con carrello e prodotto già presente ---
		Utente utente = utenteRepo.findById(1).get();
		
		Optional<Carrello> car = carrelloRepo.findByUtenteId(utente.getId());
		
		Optional<Prodotto> pro = proRepo.findByNomeProdotto("Mangime per Pesci Tropicali");
		
		int quantitaIniziale = carelloProdottoRepo.findByCarrelloIdAndProdottoId(car.get().getId(), pro.get().getId())
				.map(CarrelloProdotto::getQuantitaRichieste).orElse(0);
		
		log.info("Quantità iniziale per '{}': {}", pro.get().getNomeProdotto(), quantitaIniziale);
		
		CarrelloProdottoReq req = new CarrelloProdottoReq();
		
		req.setProdotto(ProdottoDTO.builder().id(pro.get().getId()).build());
		
		req.setQuantitaRicheste(10);
		
		
		ResponseObject<CarrelloDTO> r = carProCon.createProdotto(utente.getId(), req);
		
		Assertions.assertThat(r.getRc()).isTrue();
		
		CarrelloProdotto cp = carelloProdottoRepo.findByCarrelloIdAndProdottoId(car.get().getId(), pro.get().getId())
				.orElseThrow();
		
		Assertions.assertThat(cp.getQuantitaRichieste()).isEqualTo(quantitaIniziale + 10);
		
		log.info("Quantità finale per '{}': {}", pro.get().getNomeProdotto(), cp.getQuantitaRichieste());
		
		// Caso utente inesistente
		
		int idUtenteNonEsistente = 9999; 
		r = carProCon.createProdotto(idUtenteNonEsistente, req);
		
		Assertions.assertThat(r.getRc()).isEqualTo(false);
		
		Assertions.assertThat(r.getMsg()).isEqualTo("USER_NOT_FOUND");
		
		log.info("Messaggio errore utente inesistente: {}", r.getMsg());
		
		// utente esistente senza carrello e prodotto già presente ---
		
		utente = utenteRepo.findById(2).get();
		
		log.info("utente nome" + utente.getNome());
		
		CarrelloProdottoReq req3 = new CarrelloProdottoReq();
		
		req3.setProdotto(ProdottoDTO.builder().id(pro.get().getId()).build());
		
		req3.setQuantitaRicheste(7);
		
		r = carProCon.createProdotto(utente.getId(), req3);
		
		Assertions.assertThat(r.getRc()).isTrue();
		
		Optional<Carrello> carNuovo = carrelloRepo.findByUtenteId(utente.getId());
		
		CarrelloProdotto cp2 = carelloProdottoRepo
				.findByCarrelloIdAndProdottoId(carNuovo.get().getId(), pro.get().getId()).orElseThrow();
		
		Assertions.assertThat(cp2.getQuantitaRichieste()).isEqualTo(7);
		
		log.info("Nuovo carrello per utente con id '{}' creato con prodotto '{}' quantità {}", utente.getId(),
				pro.get().getNomeProdotto(), cp2.getQuantitaRichieste());
	}
	@Test
	@Order(3)
	
	public void updateProdottoTest() {
		
		log.debug("Update prodotto");
		
		Utente ut = new Utente();
		ut.setNome("Salah");
		ut.setCognome("Shahin");
		ut.setCodiceFiscale("SLAHYSHN63764N");
		ut.setEmail("s@gmail.com");
		ut.setPassword("1234");
		ut.setTelefono("4538273");
		ut.setIndirizzo("via ladje 29");
		ut.setDataRegistrazione(LocalDate.now());
		ut.setRole(Roles.MEDICO);
		
		ut = utenteRepo.save(ut);
		
		Optional<Prodotto> pro = proRepo.findByNomeProdotto("Mangime per Pesci Tropicali");
		
		CarrelloProdottoReq req = new CarrelloProdottoReq();
		
		req.setProdotto(ProdottoDTO.builder().id(pro.get().getId()).build());
		
		req.setQuantitaRicheste(3);
		
		ResponseObject<CarrelloDTO> resp = carProCon.updateProdotto(ut.getId(), req);
		
		Assertions.assertThat(resp.getRc()).isEqualTo(false);
		
		Assertions.assertThat(resp.getMsg().equals(messServ.getMessaggio("CART_NOT_FOUND")));
		
		log.debug("carello ID : " + req.getId() + ut.getId());
		
		// update prodotto
		ut = utenteRepo.findById(2).get();
		
		log.info("utente nome" + ut.getNome());
		
		CarrelloProdottoReq req2 = new CarrelloProdottoReq();
		
		req2.setProdotto(ProdottoDTO.builder().id(pro.get().getId()).build());
		
		req2.setQuantitaRicheste(50);
		
		resp = carProCon.updateProdotto(ut.getId(), req2);
		
		log.info("Carrello per utente con id '{}'  con prodotto '{}' quantità {}", ut.getId(),
				pro.get().getNomeProdotto(), req2.getQuantitaRicheste());
	}
	
	@Test
	@Order(4)
	public void deletProdottoTest() {
		
		log.debug("delet prodotto");
		
		// utente senza carrello → deve dare CART_NOT_FOUND Optional<Utente> ut=
		Optional<Utente> ut =utenteRepo.findById(5);
		
		Optional<Prodotto> pro = proRepo.findByNomeProdotto("Mangime per Pesci Tropicali");
		
		ResponseObject<CarrelloDTO> resp = carProCon.deleteProdotto(ut.get().getId(), pro.get().getId());
		
		Assertions.assertThat(resp.getRc()).isEqualTo(false);
		
		Assertions.assertThat(resp.getMsg().equals("CART_NOT_FOUND"));
		
		log.info("Messaggio utente senza carrello: {}", resp.getMsg());
		
		// utente con carrello e prodotto → eliminazione riuscita
		
		ut = utenteRepo.findById(2);
		
		pro = proRepo.findByNomeProdotto("Mangime per Pesci Tropicali");
		
		Optional<Carrello> car = carrelloRepo.findByUtenteId(ut.get().getId());
		
		Optional<CarrelloProdotto> cpBefore = carelloProdottoRepo.findByCarrelloIdAndProdottoId(car.get().getId(),
				pro.get().getId());
		
		Assertions.assertThat(cpBefore).isPresent();
		
		car.get().getProdotti().forEach(cp -> log.info("Prodotto: '{}', Quantità: {}",
				cp.getProdotto().getNomeProdotto(), cp.getQuantitaRichieste()));
		
		resp = carProCon.deleteProdotto(ut.get().getId(), pro.get().getId());
		
		Assertions.assertThat(resp.getRc()).isEqualTo(true);
		
		log.info("Prodotto '{}' eliminato correttamente dal carrello utente {}", pro.get().getNomeProdotto(),
				ut.get().getId());
	}


}
