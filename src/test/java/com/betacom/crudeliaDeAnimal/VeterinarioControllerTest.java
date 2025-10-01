package com.betacom.crudeliaDeAnimal;

import com.betacom.crudeliaDeAnimal.controller.VeterinarioController;
import com.betacom.crudeliaDeAnimal.dto.VeterinarioDTO;
import com.betacom.crudeliaDeAnimal.exception.CrudeliaException;
import com.betacom.crudeliaDeAnimal.models.Utente;
import com.betacom.crudeliaDeAnimal.repositories.IUtenteRepository;
import com.betacom.crudeliaDeAnimal.requests.VeterinarioReq;
import com.betacom.crudeliaDeAnimal.response.ResponseBase;
import com.betacom.crudeliaDeAnimal.response.ResponseList;
import com.betacom.crudeliaDeAnimal.response.ResponseObject;
import com.betacom.crudeliaDeAnimal.services.interfaces.IVeterinarioServices;
import com.betacom.crudeliaDeAnimal.utils.Roles;

import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@Log4j2
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class VeterinarioControllerTest {
	@Autowired
	private VeterinarioController vetC;
	@Autowired
	private IVeterinarioServices IvetS;

	@Autowired
	private IUtenteRepository utenteRepo;

	@Test
	@Order(1)
	public void createVeterinarioTest() throws CrudeliaException {
		log.debug("create Veterinario!");

		Utente ut = new Utente();

		ut.setNome("Luigi");
		ut.setCognome("Verdi");
		ut.setCodiceFiscale("RSSMRA85T10A462S");
		ut.setEmail("mario.rosi@examples.com");
		ut.setIndirizzo("Via Milano 22, Roma");
		ut.setPassword("hashedpassword2");
		ut.setTelefono("3311224567");
		ut.setDataRegistrazione(LocalDate.now());
		ut.setRole(Roles.MEDICO);

		utenteRepo.save(ut);

		VeterinarioReq req = new VeterinarioReq();
		req.setNome("Clinica del sole");
		req.setTipostrutture("CLINICA");
		req.setIndirizzo("via di prova 12");
		req.setTelefono("01234566767676767");
		req.setEmail("email@e.co");
		req.setOrariApertura("H24");
		req.setServiziVO("VISITE_GENERALI");
		req.setRegione("Veneto");
		req.setCap("35133");
		req.setProvincia("PD");
		req.setUtente(ut);

		ResponseBase r = vetC.create(req);
		Assertions.assertThat(r.getRc()).isEqualTo(true);

		req.setNome("Ospedale della luna");
		req.setTipostrutture("OSPEDALE");
		req.setIndirizzo("via di prova 13");
		req.setTelefono("01234566763676767");
		req.setEmail("email@e.con");
		req.setOrariApertura("H2");
		req.setServiziVO("VISITE_GENERALI");
		req.setRegione("Veneto");
		req.setCap("35133");
		req.setProvincia("PD");
		req.setUtente(ut);

		r = vetC.create(req);
		Assertions.assertThat(r.getRc()).isEqualTo(true);
	}

	@Test
	@Order(2)
	public void createVeterinarioErroreTest() throws CrudeliaException {
		
		
		log.debug("create Veterinario Errore!");
		
		Optional<Utente> ut= utenteRepo.findByCodiceFiscale("RSSMRA85T10A462S");
		
		VeterinarioReq req = new VeterinarioReq();
		req.setNome("Mario");
		req.setTipostrutture("CIVIVIVSOPSODI");
		req.setIndirizzo("via di prova 12");
		req.setTelefono("01234566767676767");
		req.setEmail("email@e.co");
		req.setOrariApertura("H24");
		req.setServiziVO("VISITE_GENERALI");
		req.setRegione("Veneto");
		req.setCap("35133");
		req.setProvincia("PD");
		req.setUtente(ut.get());

		ResponseBase r = vetC.create(req);

		Assertions.assertThat(r.getRc()).isEqualTo(false);
		Assertions.assertThat(r.getMsg()).isEqualTo("CLINIC _HOSPITAL_INESISTENTE");
		
		
		req.setNome("Mario");
		req.setTipostrutture("CLINICA");
		req.setIndirizzo("via di prova 12");
		req.setTelefono("01234566767676767");
		req.setEmail("email@e.co");
		req.setOrariApertura("H24");
		req.setServiziVO("adjkdahajdh");
		req.setRegione("Veneto");
		req.setCap("35133");
		req.setProvincia("PD");

		r = vetC.create(req);
		Assertions.assertThat(r.getRc()).isEqualTo(false);
		Assertions.assertThat(r.getMsg()).isEqualTo("CLINIC _HOSPITAL_INESISTENTE");

		req.setNome("Clinica del sole");
		req.setTipostrutture("CLINICA");
		req.setIndirizzo("via di prova 12");
		req.setTelefono("01234566767676767");
		req.setEmail("email@e.co");
		req.setOrariApertura("H24");
		req.setServiziVO("adjkdahajdh");
		req.setRegione("Veneto");
		req.setCap("35133");
		req.setProvincia("PD");

		r = vetC.create(req);
		Assertions.assertThat(r.getRc()).isEqualTo(false);
		Assertions.assertThat(r.getMsg()).isEqualTo("CLINIC_NOT_FOUND");

		req.setNome("Ospedale della luna");
		req.setTipostrutture("OSPEDALE");
		req.setIndirizzo("via di prova 12");
		req.setTelefono("01234566767676767");
		req.setEmail("email@e.co");
		req.setOrariApertura("H24");
		req.setServiziVO("adjkdahajdh");
		req.setRegione("Veneto");
		req.setCap("35133");
		req.setProvincia("PD");

		r = vetC.create(req);
		Assertions.assertThat(r.getRc()).isEqualTo(false);
		Assertions.assertThat(r.getMsg()).isEqualTo("HOSPITAL_NOT_FOUND");
		
		
		//utente non trovato :
		
		Utente utente= new Utente();
		utente.setId(77777);
		
		
		req.setNome("Clinica della luna");
		req.setTipostrutture("Clinica");
		req.setIndirizzo("via di prova 12");
		req.setTelefono("01234566767676767");
		req.setEmail("email@e.co");
		req.setOrariApertura("H24");
		req.setServiziVO("adjkdahajdh");
		req.setRegione("Liguria");
		req.setCap("35133");
		req.setProvincia("PD");
		req.setUtente(utente);

		r = vetC.create(req);
		Assertions.assertThat(r.getRc()).isEqualTo(false);
		Assertions.assertThat(r.getMsg()).isEqualTo("USER_NOT_FOUND");
	}

	@Test
	@Order(3)
	public void findAllVeterinariControllerTest() throws CrudeliaException {
		ResponseBase risp = vetC.listAll();
		ResponseList<VeterinarioDTO> r = (ResponseList<VeterinarioDTO>) risp;
		Assertions.assertThat(risp.getRc()).isTrue();
		Assertions.assertThat(r.getDati()).isInstanceOf(List.class);

		List<?> lista = (List<?>) r.getDati();
		Assertions.assertThat(lista).isNotEmpty();
	}

	@Test
	@Order(4)
	public void updateVeterinarioTest() throws CrudeliaException {
		VeterinarioReq req = new VeterinarioReq();
		req.setId(1);
		req.setNome("Clinica del sole");
		req.setTipostrutture("CLINICA");
		req.setIndirizzo("via di prova 144");
		req.setTelefono("01234566767676767");
		req.setEmail("email@e.com");
		req.setOrariApertura("H24");
		req.setServiziVO("VISITE_GENERALI");
		req.setRegione("Veneto");
		req.setCap("35133");
		req.setProvincia("PD");
		ResponseBase r = vetC.update(req);

	}

	@Test
	@Order(5)
	public void updateVeterinarioErroreTest() throws CrudeliaException {
		VeterinarioReq req = new VeterinarioReq();
		req.setId(100);
		req.setNome("Clinica del sole");
		req.setTipostrutture("CLINICA");
		req.setIndirizzo("via di prova 144");
		req.setTelefono("01234566767676767");
		req.setEmail("email@e.com");
		req.setOrariApertura("H24");
		req.setServiziVO("VISITE_GENERALI");
		req.setRegione("Veneto");
		req.setCap("35133");
		req.setProvincia("PD");

		ResponseBase r = vetC.update(req);
		Assertions.assertThat(r.getRc()).isEqualTo(false);
		Assertions.assertThat(r.getMsg()).isEqualTo("CLINIC _HOSPITAL_INESISTENTE");

	}

	@Test
	@Order(6)
	public void deleteVeterinarioErroreTest() throws CrudeliaException {
		VeterinarioReq req = new VeterinarioReq();
		req.setId(250);
		req.setNome("Ospedale della luna");
		req.setTipostrutture("OSPEDALE");
		req.setIndirizzo("via di prova 13");
		req.setTelefono("01234566763676767");
		req.setEmail("email@e.con");
		req.setOrariApertura("H2");
		req.setServiziVO("VISITE_GENERALI");
		req.setRegione("Veneto");
		req.setCap("35133");
		req.setProvincia("PD");

		ResponseBase r = vetC.remove(req);

		Assertions.assertThat(r.getRc()).isEqualTo(false);
		Assertions.assertThat(r.getMsg()).isEqualTo("CLINIC _HOSPITAL_INESISTENTE");

	}

	@Test
	@Order(7)
	public void findByIdVeterinarioTest() throws CrudeliaException {
		Integer id = 1;
		ResponseObject<VeterinarioDTO> r = vetC.listById(id);

		Assertions.assertThat(r.getDati()).isNotNull();
		Assertions.assertThat(r.getRc()).isEqualTo(true);

	}

	@Test
	@Order(8)
	public void findByIdVeterinarioErroreTest() throws CrudeliaException {
		ResponseObject<VeterinarioDTO> r = vetC.listById(31313);

		Assertions.assertThat(r.getMsg()).isEqualTo("CLINIC _HOSPITAL_INESISTENTE");
		Assertions.assertThat(r.getRc()).isEqualTo(false);
	}

	@Test
	@Order(9)
	public void findByCapVeterinarioTest() throws CrudeliaException {
		Integer id = 1;
		ResponseList<VeterinarioDTO> r = vetC.listByCap("35133");

		Assertions.assertThat(r.getDati()).isNotNull();
		Assertions.assertThat(r.getRc()).isEqualTo(true);

	}

	@Test
	@Order(10)
	public void findByCapVeterinarioErroreTest() throws CrudeliaException {
		ResponseList<VeterinarioDTO> r = vetC.listByCap("P");

		Assertions.assertThat(r.getMsg()).isEqualTo("CLINIC _HOSPITAL_INESISTENTE");

	}

	@Test
	@Order(11)
	public void findByRegioneVeterinarioTest() throws CrudeliaException {

		ResponseList<VeterinarioDTO> r = vetC.listByRegione("Veneto");

		Assertions.assertThat(r.getDati()).isNotNull();
		Assertions.assertThat(r.getRc()).isEqualTo(true);

	}

	@Test
	@Order(12)
	public void findByRegioneVeterinarioErroreTest() throws CrudeliaException {
		ResponseList<VeterinarioDTO> r = vetC.listByRegione("P");

		Assertions.assertThat(r.getMsg()).isEqualTo("CLINIC _HOSPITAL_INESISTENTE");

	}

	@Test
	@Order(13)
	public void findByProvinciaVeterinarioTest() throws CrudeliaException {

		ResponseList<VeterinarioDTO> r = vetC.listByProvincia("PD");

		Assertions.assertThat(r.getDati()).isNotNull();
		Assertions.assertThat(r.getRc()).isEqualTo(true);

	}

	@Test
	@Order(14)
	public void findByProvinciaVeterinarioErroreTest() throws CrudeliaException {
		ResponseList<VeterinarioDTO> r = vetC.listByProvincia("P");

		Assertions.assertThat(r.getMsg()).isEqualTo("CLINIC _HOSPITAL_INESISTENTE");

	}

	@Test
	@Order(15)
	public void deleteVeterinarioTest() throws CrudeliaException {
		VeterinarioReq req = new VeterinarioReq();
		req.setId(2);
		req.setNome("Ospedale della luna");
		req.setTipostrutture("OSPEDALE");
		req.setIndirizzo("via di prova 13");
		req.setTelefono("01234566763676767");
		req.setEmail("email@e.con");
		req.setOrariApertura("H2");
		req.setServiziVO("VISITE_GENERALI");
		req.setRegione("Veneto");
		req.setCap("35133");
		req.setProvincia("PD");

		ResponseBase r = vetC.remove(req);
		Assertions.assertThat(r.getRc()).isEqualTo(true);

	}

}