package com.betacom.crudeliaDeAnimal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.h2.engine.Role;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.betacom.crudeliaDeAnimal.controller.AnimaleController;
import com.betacom.crudeliaDeAnimal.controller.UtenteController;
import com.betacom.crudeliaDeAnimal.dto.AnimaleDTO;
import com.betacom.crudeliaDeAnimal.exception.CrudeliaException;
import com.betacom.crudeliaDeAnimal.models.Utente;
import com.betacom.crudeliaDeAnimal.repositories.IUtenteRepository;
import com.betacom.crudeliaDeAnimal.requests.AnimaleReq;
import com.betacom.crudeliaDeAnimal.response.ResponseBase;
import com.betacom.crudeliaDeAnimal.response.ResponseList;
import com.betacom.crudeliaDeAnimal.response.ResponseObject;
import com.betacom.crudeliaDeAnimal.utils.Roles;

import lombok.extern.log4j.Log4j2;

@Log4j2
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AnimaleControllerTest {

	@Autowired
	private AnimaleController animalCont;

	@Autowired
	private IUtenteRepository utenteRepo;

	@Test
	@Order(1)
	public void createAnimaleTest() throws CrudeliaException {
		log.debug("create Animale!");

		Utente utente = new Utente();
		utente.setNome("Ahmed");
		utente.setCognome("Yosruy");
		utente.setEmail("y.Ahmed@test.com");
		utente.setCodiceFiscale("AHMYSR99A01H501X");
		utente.setTelefono("3331234567");
		utente.setIndirizzo("Via Roma 1");
		utente.setPassword("password");
		utente.setRole(Roles.USER);
		utente.setDataRegistrazione(LocalDate.now());
		utente = utenteRepo.save(utente);

		AnimaleReq req = new AnimaleReq();
		req.setNomeAnimale("Fuffy");
		req.setTipo("Cane");
		req.setRazza("Labrador");
		req.setNoteMediche("Nessuna");
		req.setUtente(utente);

		ResponseBase r = animalCont.create(req);
		Assertions.assertThat(r.getRc()).isEqualTo(true);

		AnimaleReq req2 = new AnimaleReq();

		req2.setNomeAnimale("LOllo");
		req2.setTipo("Pesce");
		req2.setRazza("nino");
		req2.setNoteMediche("febre");
		req2.setUtente(utente);

		r = animalCont.create(req2);
		Assertions.assertThat(r.getRc()).isEqualTo(true);

	}

	@Test
	@Order(2)
	public void createAnimaleErroreTest() throws CrudeliaException {

		log.debug("create Animale  Errore!");

		AnimaleReq req = new AnimaleReq();

		req.setNomeAnimale("Wikwiki");
		req.setTipo("Gatto");
		req.setRazza("nownow");
		req.setNoteMediche("Nessuna");

		Utente utente = new Utente();
		utente.setId(9999);
		req.setUtente(utente);

		ResponseBase r = animalCont.create(req);
		Assertions.assertThat(r.getRc()).isEqualTo(false);
		Assertions.assertThat(r.getMsg()).isEqualTo("USER_NOT_FOUND");

	}

	@Test
	@Order(3)
	public void updateAnimaleControllerTest() throws CrudeliaException {

		Utente utente = utenteRepo.findById(1).get();

		AnimaleReq req = new AnimaleReq();

		req.setId(1);
		req.setNomeAnimale("Fuffy");
		req.setTipo("Cane");
		req.setRazza("Labrador");
		req.setNoteMediche("Ha febbre");
		req.setUtente(utente);

		ResponseBase r = animalCont.update(req);

		Assertions.assertThat(r.getRc()).isEqualTo(true);

		AnimaleReq req2 = new AnimaleReq();

		req2.setId(4);
		req2.setNomeAnimale("WIKIK");
		req2.setTipo("GATTO");
		req2.setRazza("nownow");
		req2.setNoteMediche("nessun");

		r = animalCont.update(req2);
		Assertions.assertThat(r.getRc()).isEqualTo(false);
		Assertions.assertThat(r.getMsg()).isEqualTo("ANIMAL_NOT_FOUND");

		AnimaleReq req3 = new AnimaleReq();
		req3.setId(1);
		req3.setNomeAnimale("Leo");

		Utente fakeUser = new Utente();
		fakeUser.setId(9999);
		req3.setUtente(fakeUser);

		r = animalCont.update(req3);
		Assertions.assertThat(r.getRc()).isEqualTo(false);
		Assertions.assertThat(r.getMsg()).isEqualTo("USER_NOT_FOUND");

	}

	@Test
	@Order(4)
	public void listAllAnimaleControllerTest() throws CrudeliaException {

		ResponseBase resp = animalCont.listAll();

		Assertions.assertThat(resp).isInstanceOf(ResponseList.class);
		ResponseList<?> r = (ResponseList<?>) resp;

		Assertions.assertThat(r.getRc()).isEqualTo(true);
		Assertions.assertThat(r.getDati()).isInstanceOf(List.class);

		List<?> lista = (List<?>) r.getDati();
		Assertions.assertThat(lista).isNotEmpty();

	}

	@Test
	@Order(5)
	public void findByIdAnimaleControllerTest() throws CrudeliaException {

		Integer id = 1;

		ResponseObject<AnimaleDTO> resp = animalCont.findById(id);

		Assertions.assertThat(resp.getRc()).isEqualTo(true);

		Assertions.assertThat(resp.getDati()).isNotNull();

		Assertions.assertThat(resp.getDati().getId()).isEqualTo(id);

		id = 3;
		resp = animalCont.findById(id);
		Assertions.assertThat(resp.getRc()).isEqualTo(false);
		Assertions.assertThat(resp.getDati()).isNull();
		Assertions.assertThat(resp.getMsg()).isEqualTo("ANIMAL_NOT_FOUND");

	}

	@Test
	@Order(6)
	public void deleteAnimaleControllerTest() throws CrudeliaException {

		AnimaleReq req = new AnimaleReq();

		req.setId(99);
		req.setNomeAnimale("WIKIK");
		req.setTipo("GATTO");
		req.setRazza("nownow");
		req.setNoteMediche("nessun");

		ResponseBase r = animalCont.delete(req);
		Assertions.assertThat(r.getRc()).isEqualTo(false);
		Assertions.assertThat(r.getMsg()).isEqualTo("ANIMAL_NOT_FOUND");

		Utente utente = utenteRepo.findById(1).get();
		req.setId(1);
		req.setNomeAnimale("Fuffy");
		req.setTipo("Cane");
		req.setRazza("Labrador");
		req.setNoteMediche("Nessuna");
		req.setUtente(utente);

		r = animalCont.delete(req);
		Assertions.assertThat(r.getRc()).isEqualTo(true);

	}

	@Test
	@Order(7)
	public void emptyListAnimaleControllerTest() throws CrudeliaException {

		AnimaleReq req = new AnimaleReq();

		Utente utente = utenteRepo.findById(1).get();

		req.setId(2);
		req.setNomeAnimale("LOllo");
		req.setTipo("Pesce");
		req.setRazza("nino");
		req.setNoteMediche("febre");
		req.setUtente(utente);

		ResponseBase r = animalCont.delete(req);
		Assertions.assertThat(r.getRc()).isEqualTo(true);
		
		 r = animalCont.listAll();
		Assertions.assertThat(r).isInstanceOf(ResponseList.class);
		ResponseList<?> rList = (ResponseList<?>) r;

		Assertions.assertThat(rList.getRc()).isEqualTo(false);
		Assertions.assertThat(r.getMsg()).isEqualTo("ANIMAL_NOT_FOUND");
	}

}
